package com.f.configure;

import com.f.base.Auth;
import com.f.configure.properties.TokenConfigure;
import com.f.helper.TokenHelper;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * User: bvsoo
 * Date: 2018/6/28
 * Time: 下午4:48
 */
@Configuration
public class SwaggerConfigurer {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Admin Server api")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
    @Autowired
    private TokenConfigure tokenConfigure;
    @Bean
    public Docket createRestApi() throws UnsupportedEncodingException {
        String token = Auth.JWT_TOKEN_PREFIX + Jwts.builder()
                .claim(Auth.USER_ID_KEY, 1L).claim(Auth.USER_ACCOUNT_KEY, "admin")
                .setSubject("1")
                .setExpiration(null)
                .signWith(SignatureAlgorithm.forName(tokenConfigure.getAlgorithm()), TokenHelper.getSecret(tokenConfigure.getSecret()))
                .compact();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(Auth.JWT_HEADER_KEY).defaultValue(token).description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> pars = Lists.newArrayList();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars).apiInfo(apiInfo());
    }

}
