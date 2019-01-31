package com.f.transport.builder;

import com.f.enums.RequestMethod;
import com.f.utils.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-16 15:15
 */
@Component
public class GetBuilder implements MethodBuilder {
    @Override
    public RequestMethod getMethod() {
        return RequestMethod.GET;
    }

    @Override
    public HttpRequestBase build(String url, LinkedHashMap<String, Object> params) {
        return new HttpGet(url + "?" + HttpUtil.encodeQueryString(params));
    }
}
