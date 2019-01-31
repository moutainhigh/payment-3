package com.f.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-02 上午10:41
 **/
//@Component
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = this.getRequestBody(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username = jsonObject.getString(SPRING_SECURITY_FORM_USERNAME_KEY);
            String password = jsonObject.getString(SPRING_SECURITY_FORM_PASSWORD_KEY);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);

            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    private JSONObject getRequestBody(HttpServletRequest request) throws IOException {
        String str = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        JSONObject jsonObject = JSONObject.parseObject(str);
        return jsonObject;
    }
}