package com.f.transport.builder;

import com.f.enums.RequestMethod;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-15 18:22
 */
public interface MethodBuilder {
    String DEFAULT_ENCODING = "UTF-8";

    RequestMethod getMethod();

    HttpRequestBase build(String url, LinkedHashMap<String, Object> params);
}
