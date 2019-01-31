package com.f.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by FFF01 on 2015/7/20.
 */
public class JsonBuilder {

    @SuppressWarnings("unchecked")
    public static <T> T desJson(String jsonStr) throws IOException {
        return (T) new ObjectMapper().readValue(jsonStr, Object.class);
    }

    public static <T> T desJson(String jsonStr, Class<T> t) throws IOException {
        return new ObjectMapper().readValue(jsonStr, t);
    }

    public static String serJson(Object src) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(src);
    }
}
