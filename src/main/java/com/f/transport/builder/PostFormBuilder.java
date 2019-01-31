package com.f.transport.builder;

import com.f.enums.RequestMethod;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-16 15:32
 */
@Component
public class PostFormBuilder implements MethodBuilder {

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.POST_FORM;
    }

    @Override
    public HttpRequestBase build(String url, LinkedHashMap<String, Object> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, DEFAULT_ENCODING));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return post;
    }
}
