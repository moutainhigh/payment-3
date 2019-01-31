package com.f.utils;

import com.sun.org.glassfish.gmbal.NameValue;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtil {

    public final static String CONTENT_TYPE_WWW_FORM_URLENCODED = ContentType.APPLICATION_FORM_URLENCODED.getMimeType(); //"application/x-www-form-urlencoded";
    public final static String CONTENT_TYPE_TEXT_XML = ContentType.TEXT_XML.getMimeType(); //text/xml

    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private final static String DEFAULT_ENCODING = "UTF-8";
    private final static URLCodec URL_CODEC = new URLCodec(DEFAULT_ENCODING);

    private final static int CONNECT_TIMEOUT = 16000;
    private final static int READ_TIMEOUT = 16000;

    // TODO: 16/07/17 closeablehttpclient是线程安全的，可以公用静态的2个，http和https
    public static String execute(CloseableHttpClient client, HttpRequestBase httpRequestBase) {
        RequestConfig requestConfig = httpRequestBase.getConfig();
        if (requestConfig != null) {
            int conTimeout = requestConfig.getConnectTimeout();
            int socketTimeout = requestConfig.getSocketTimeout();
            if (conTimeout == -1) {
                conTimeout = CONNECT_TIMEOUT;
            }
            if (socketTimeout == -1) {
                socketTimeout = READ_TIMEOUT;
            }
            requestConfig = RequestConfig.copy(requestConfig).setConnectTimeout(conTimeout).setSocketTimeout(socketTimeout).build();
            httpRequestBase.setConfig(requestConfig);
        }
        return execute(client, httpRequestBase, true);
    }
    public static String execute(CloseableHttpClient client, HttpRequestBase httpRequestBase, boolean release) {
        try (CloseableHttpResponse response = client.execute(httpRequestBase)) {
            int status = response.getStatusLine().getStatusCode();

            String res = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODING);
            //2xx ok
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                return res;
            } else {
                logger.warn("http request:[{}] not ok, code:{}, response:{}", httpRequestBase.getURI().toString(), status, res);
            }
        } catch (Exception e) {
            logger.warn("http request:[{}] exception:{}", httpRequestBase.getURI().toString(), e.getMessage());
        } finally {
            if (release) {
                try {
                    client.close();
                } catch (IOException ignored) {
                }
            }
        }
        return "";
    }

    public static String execute(HttpRequestBase httpRequestBase, int connectTimeout, int readTimeout) {
        CloseableHttpClient client = HttpClients.createDefault();
        httpRequestBase.setConfig(RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build());
        return execute(client, httpRequestBase);
    }

    public static String executeWithSystemProperties(HttpRequestBase httpRequestBase, int connectTimeout, int readTimeout) {
        CloseableHttpClient client = HttpClients.createSystem();
        httpRequestBase.setConfig(RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build());
        return execute(client, httpRequestBase);
    }

    public static String execute(HttpRequestBase httpRequestBase, final HttpClientConnectionManager httpClientConnectionManager,
                                 final RequestConfig requestConfig) {
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig).build();
        return execute(client, httpRequestBase, false);
    }

    public static String executeWithSSL(HttpRequestBase httpRequestBase, int connectTimeout, int readTimeout) {
        try {
            SSLContext sslcontext = SSLContexts.custom().useSSL().build();
            sslcontext.init(null, new X509TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            httpRequestBase.setConfig(RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build());
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(factory).build();
            return execute(client, httpRequestBase);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return execute(httpRequestBase);
    }

    public static String execute(HttpRequestBase httpRequestBase) {
        return execute(httpRequestBase, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    public static String executeWithSystemProperties(HttpRequestBase httpRequestBase) {
        return executeWithSystemProperties(httpRequestBase, CONNECT_TIMEOUT, READ_TIMEOUT);
    }
    //add by mervin
    public static CloseableHttpResponse executeGetResWithSystemProperties(HttpRequestBase httpRequestBase) {
        CloseableHttpClient client = HttpClients.createSystem();
        httpRequestBase.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build());
        try (CloseableHttpResponse response = client.execute(httpRequestBase)) {
            int status = response.getStatusLine().getStatusCode();

            String res = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODING);
            //2xx ok
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                return response;
            } else {
                logger.warn("http request:[{}] not ok, code:{}, response:{}", httpRequestBase.getURI().toString(), status, res);
            }
        } catch (Exception e) {
            logger.warn("http request:[{}] exception:{}", httpRequestBase.getURI().toString(), e.getMessage());
        } finally {
            if (true) {
                try {
                    client.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    public static String executeWithSSL(HttpRequestBase httpRequestBase) {
        return executeWithSSL(httpRequestBase, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    public static String getRequest(String url) {
        HttpGet get = new HttpGet(url);
        get.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build());
        return execute(get);
    }

    public static String postRequest(String url, String data) {
        HttpPost post = new HttpPost(url);
        post.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build());
        if (data != null) {
            post.setEntity(new StringEntity(data, DEFAULT_ENCODING));
        }
        return execute(post);
    }

    public static String postFormRequest(String url, Map<String ,String> paraMap) {
        HttpPost post = new HttpPost(url);
        post.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build());
        if (paraMap != null) {
            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, String> entry : paraMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                post.setEntity(new UrlEncodedFormEntity(params, DEFAULT_ENCODING));
            } catch (UnsupportedEncodingException e) {
                logger.error("postFormRequest encode error", e);
            }
        }
        return execute(post);
    }

    public static String postRequestWithSSL(String url, String data, Header[] headers) {
        HttpPost post = new HttpPost(url);
        post.setHeaders(headers);
        post.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build());
        if (data != null) {
            post.setEntity(new StringEntity(data, DEFAULT_ENCODING));
        }
        return executeWithSSL(post);
    }

    public static String encodeQueryString(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(urlEncode(entry.getValue().toString()));
        }
        return sb.toString();
    }

    private static String urlEncode(String src) {
        try {
            return URL_CODEC.encode(src, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
}
