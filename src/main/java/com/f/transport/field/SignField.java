package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.enums.SignType;
import com.f.mvc.entity.SignSetting;
import com.f.transport.CallbackContext;
import com.f.transport.ParseResult;
import com.f.transport.RequestContext;
import com.f.transport.sign.SignProcessor;
import com.f.vo.SignSettingVo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : caibi
 * @date : 2019-01-16 16:25
 */
@Component
public class SignField extends AbstractField implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Map<SignType, SignProcessor> maps = new HashMap<>();

    @PostConstruct
    void init(){
        maps.putAll(applicationContext.getBeansOfType(SignProcessor.class).values().stream().collect(Collectors.toMap(SignProcessor::getType, p -> p)));

    }
    @Override
    public FieldType getType() {
        return FieldType.SIGN;
    }

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        SignType signType = (SignType) context.getData().get(FieldType.SIGN);
        SignProcessor processor = maps.get(signType);
        String sign = processor.sign(context.getSignSetting(), context.getKey(), params);
        params.put(key, sign);
        return params;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        result.setSign(value.toString());
    }

    @Override
    public void callback(CallbackContext context, ParseResult result, LinkedHashMap<String, Object> resultParams, String assertValue, String key, Map<String, Object> requestParams) {
        String sign = requestParams.get(key).toString();
        SignSettingVo setting = context.getSignSetting();
        SignProcessor processor = maps.get(setting.getSignType());
        String calc = processor.sign(setting, context.getKey(), resultParams);
        if(!calc.equalsIgnoreCase(sign)){
            throw new RuntimeException("签名不通过");
        }
        convert(result, sign, assertValue);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
