package com.f.transport.parser;

import com.f.enums.ResponseParseType;
import com.f.transport.ResponseContext;

/**
 * @author : caibi
 * @date : 2019-01-16 11:00
 */
public interface ResponseParser<T extends ResponseContext> {

    ResponseParseType getType();

    T buildContext(String responseStr);

    Object parse(T context, String script);
}
