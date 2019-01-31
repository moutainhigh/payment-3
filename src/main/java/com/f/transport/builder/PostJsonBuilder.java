package com.f.transport.builder;

import com.alibaba.fastjson.JSON;
import com.f.enums.RequestMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-16 15:41
 */
@Component
public class PostJsonBuilder implements MethodBuilder {
    @Override
    public RequestMethod getMethod() {
        return RequestMethod.POST_JSON;
    }

    @Override
    public HttpRequestBase build(String url, LinkedHashMap<String, Object> params) {
        String json = JSON.toJSONString(params);
        HttpPost post = new HttpPost(url);
        HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        return post;
    }
}
