package com.f.transport;

import com.f.enums.FieldType;
import com.f.enums.RequestMethod;
import com.f.enums.ResponseParseType;
import com.f.transport.builder.MethodBuilder;
import com.f.transport.parser.ResponseParser;
import com.f.transport.field.FieldProcessor;
import com.f.vo.CallbackParamVo;
import com.f.vo.RequestParamVo;
import com.f.vo.ResponseParamVo;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : caibi
 * @date : 2019-01-15 16:14
 */
@Service
@Log4j2
public class TransportService implements ApplicationContextAware {
    public final static int CONNECT_TIMEOUT = 16000;
    public final static int READ_TIMEOUT = 16000;
    private Map<FieldType, FieldProcessor> fieldProcessors = new HashMap<>();
    private Map<RequestMethod, MethodBuilder> methodBuilders = new HashMap<>();
    private Map<ResponseParseType, ResponseParser> responseParsers = new HashMap<>();

    private ApplicationContext applicationContext;

    @PostConstruct
    void init(){
        fieldProcessors.putAll(applicationContext.getBeansOfType(FieldProcessor.class).values().stream().collect(Collectors.toMap(FieldProcessor::getType, p -> p)));
        methodBuilders.putAll(applicationContext.getBeansOfType(MethodBuilder.class).values().stream().collect(Collectors.toMap(MethodBuilder::getMethod, p -> p)));
        responseParsers.putAll(applicationContext.getBeansOfType(ResponseParser.class).values().stream().collect(Collectors.toMap(ResponseParser::getType, p -> p)));
    }

    /**
     * 组装请求参数
     * @param params
     * @param context
     * @return
     */
    public HttpRequestBase assembeRequest(List<RequestParamVo> params, RequestContext context){
        LinkedHashMap<String, Object> paramMaps = new LinkedHashMap<>();
        Collections.sort(params);
        for(RequestParamVo paramVo : params){
            FieldProcessor processor = fieldProcessors.get(paramVo.getFieldType());
            paramMaps = processor.assemble(paramMaps, context, paramVo.getFieldKey(), paramVo.getDefaultValue());
        }
        HttpRequestBase result = methodBuilders.get(context.getRequestMethod()).build(context.getUrl(), paramMaps);
        result.setConfig(RequestConfig.custom().setRedirectsEnabled(true).setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build());
        return result;
    }

    /**
     * 解析同步返回数据
     * @param responseType
     * @param responseStr
     * @param params
     * @return
     */
    public ParseResult parse(ResponseParseType responseType, String responseStr, List<ResponseParamVo> params){
        ResponseParser parser = responseParsers.get(responseType);
        ResponseContext context = parser.buildContext(responseStr);
        ParseResult parseResult = new ParseResult();
        for(ResponseParamVo paramVo : params){
            Object result = parser.parse(context, paramVo.getParseScript());
            if(result == null){
                continue;
            }
            FieldProcessor processor = fieldProcessors.get(paramVo.getFieldType());
            processor.convert(parseResult, result, paramVo.getAssertValue());
        }
        return parseResult;
    }

    /**
     * 解析回调
     * @param context
     * @param paramVos
     * @param requestMaps
     * @throws RuntimeException 验签失败
     * @see com.f.transport.field.SignField
     */
    public ParseResult callback(CallbackContext context, List<CallbackParamVo> paramVos, Map<String, Object> requestMaps){
        LinkedHashMap<String, Object> resultMaps = new LinkedHashMap<>();
        Collections.sort(paramVos);
        ParseResult parseResult = new ParseResult();
        for(CallbackParamVo paramVo : paramVos){
            FieldProcessor processor = fieldProcessors.get(paramVo.getFieldType());
            processor.callback(context, parseResult, resultMaps, paramVo.getAssertValue(), paramVo.getFieldKey(), requestMaps);
        }
        return parseResult;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
