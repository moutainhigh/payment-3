package com.f.request.task;
/*
 *@ClassName RequestTask
 *@Author 建国
 *@Date 1/16/19 4:40 PM
 */

import com.f.enums.RequestType;
import com.f.enums.ResponseParseType;
import com.f.transport.ParseResult;
import com.f.transport.TransportService;
import com.f.utils.HttpConnectionPoolUtil;
import com.f.vo.RequestContextVO;
import com.f.vo.RequestParamVo;
import com.f.vo.ResponseParamVo;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
@Log4j2
public abstract class RequestTask implements Runnable{
    @Autowired
    protected TransportService transportService;

    protected List<RequestParamVo> requestParamVos;

    protected List<ResponseParamVo> responseParams;

    protected RequestContextVO requestContextVO;

    protected ResponseParseType responseParseType;

    public RequestTask(){}

    public  RequestTask(List<RequestParamVo> requestParamVos,List<ResponseParamVo> responseParams, RequestContextVO requestContextVO,ResponseParseType responseParseType){
        this.requestParamVos = new ArrayList<>();
        requestParamVos.forEach(vo->{
            this.requestParamVos.add(vo.clone());
        });
        this.requestContextVO = requestContextVO;
        this.responseParams = new ArrayList<>();
        responseParams.forEach(vo->{
            this.responseParams.add(vo.clone());
        });
        this.responseParseType = responseParseType;
    }

    @Override
    public void run() {
        try{
            CloseableHttpClient httpClient = HttpConnectionPoolUtil.createHttpClient();
            HttpRequestBase request = transportService.assembeRequest(this.requestParamVos,this.requestContextVO);
            CloseableHttpResponse response =  httpClient.execute(request);
            String res = EntityUtils.toString(response.getEntity(), "UTF-8");
            ParseResult result = transportService.parse(this.responseParseType,res,this.responseParams);
            dealTask(result);
        } catch (Exception e){
            log.error("RequestTask.class  http request error " +e.getMessage());
        }

    }

    protected abstract void dealTask(ParseResult result)throws Exception;

    public abstract RequestType getType();
}
