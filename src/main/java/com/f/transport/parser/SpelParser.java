package com.f.transport.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.f.enums.ResponseParseType;
import com.f.transport.ResponseContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

/**
 * @author : caibi
 * @date : 2019-01-16 11:29
 */
@Component
public class SpelParser implements ResponseParser<SpelContext> {
    @Override
    public ResponseParseType getType() {
        return ResponseParseType.SPEL;
    }

    @Override
    public SpelContext buildContext(String responseStr) {
        JSONObject jsonObject = JSON.parseObject(responseStr);
        return new SpelContext(jsonObject);
    }

    @Override
    public Object parse(SpelContext context, String script) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(script).getValue(context);
    }
}
