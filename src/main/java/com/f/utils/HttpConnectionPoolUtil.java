package com.f.utils;
/*
 *@ClassName HttpconnectionPoolUtil
 *@Author 建国
 *@Date 1/16/19 5:17 PM
 */

import lombok.extern.log4j.Log4j2;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
public class HttpConnectionPoolUtil {

    private static final int CONNECT_TIMEOUT = 10*1000;// 设置连接建立的超时时间为10s
    private static final int SOCKET_TIMEOUT = 10*1000;
    private static final int MAX_CONN = 200; // 最大连接数
    private static final int Max_PRE_ROUTE =200;
    private static final int MAX_ROUTE = 200;
    private static CloseableHttpClient httpClient; // 发送请求的客户端单例
    private static PoolingHttpClientConnectionManager manager; //连接池管理类
    private static ScheduledExecutorService monitorExecutor;

    private final static byte[] syncLock = new byte[0]; // 相当于线程锁,用于线程安全

    /**
     * 对http请求进行基本设置
     * @param httpRequestBase http请求
     */
    private static void setRequestConfig(HttpRequestBase httpRequestBase){
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        httpRequestBase.setConfig(requestConfig);
    }

    public static CloseableHttpClient getHttpClient(){
        if (httpClient == null){
            //多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
            synchronized (syncLock){
                if (httpClient == null){
                    httpClient = createHttpClient();
                    //开启监控线程,对异常和空闲线程进行关闭
                    monitorExecutor = Executors.newScheduledThreadPool(1);
                    monitorExecutor.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            //关闭异常连接
                            manager.closeExpiredConnections();
                            //关闭5s空闲的连接
                            manager.closeIdleConnections(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                            log.info("close expired and idle for over 5s connection");
                        }
                    }, 5000, 5000, TimeUnit.MILLISECONDS);
                }
            }
        }
        return httpClient;
    }

    /**
     * @return
     */
    public static CloseableHttpClient createHttpClient(){
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();

        manager = new PoolingHttpClientConnectionManager(registry);
        //设置连接参数
        manager.setMaxTotal(MAX_CONN); // 最大连接数
        manager.setDefaultMaxPerRoute(Max_PRE_ROUTE); // 路由最大连接数

        //请求失败时,进行请求重试
        HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 3){
                    //重试超过3次,放弃请求
                    log.error("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException){
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    log.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException){
                    // SSL握手异常
                    log.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException){
                    //超时
                    log.error("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException){
                    // 服务器不可达
                    log.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException){
                    // 连接超时
                    log.error("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException){
                    log.error("SSLException");
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)){
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };

        CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler).build();
        return client;
    }

    /**
     * 关闭连接池
     */
    public static void closeConnectionPool(){
        try {
            httpClient.close();
            manager.close();
            monitorExecutor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String [] args)throws Exception{
        CloseableHttpClient client = getHttpClient();
        //HttpGet get = new HttpGet("http://www.baidu.com");
        HttpUriRequest request = new HttpGet("http://www.baidu.com");
        HttpResponse response = client.execute(request);
        System.err.println(response);
    }

}
