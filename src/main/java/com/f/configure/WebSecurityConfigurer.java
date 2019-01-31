package com.f.configure;

import com.f.filter.CustomBasicAuthenticationFilter;
import com.f.filter.MyAuthenticationFilter;
import com.f.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * User: bvsoo
 * Date: 2018/9/13
 * Time: 下午2:04
 */
@EnableWebSecurity
public class WebSecurityConfigurer {
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .requestMatchers().antMatchers("/druid/**", "/swagger-ui.html", "/v2/**").and()
                    .authorizeRequests()
                    .anyRequest().hasAnyRole("ADMIN", "ACTUATOR")
                    .and()
                    .sessionManagement()
                    .and()
                    .logout()
                    .and()
                    .httpBasic();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/swagger-resources/configuration/ui",
                    "/swagger-resources",
                    "/swagger-resources/configuration/security",
                    "/webjars/**");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        @Bean
        @Primary
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }


    @Configuration
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserDetailsService userDetailsService;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private CustomAccessDeniedHandler customAccessDeniedHandler;
        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
        @Autowired
        private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
        @Autowired
        private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;
        @Autowired
        private CustomBasicAuthenticationFilter customBasicAuthenticationFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().authorizeRequests()
                    .antMatchers("/user/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(customBasicAuthenticationFilter, MyAuthenticationFilter.class)
                    .addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .logout().logoutUrl("/user/logout").permitAll()
                    .logoutSuccessHandler(customLogoutSuccessHandler);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }


        @Override
        @Bean
        @Primary
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
        @Bean
        @Primary
        public MyAuthenticationFilter myAuthenticationFilter() throws Exception {
            MyAuthenticationFilter filter = new MyAuthenticationFilter();
            filter.setAuthenticationManager(authenticationManagerBean());
            //只有post请求才拦截
            filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login", "POST"));
            filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
            filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
            return filter;
        }
    }

}
