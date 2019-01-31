package com.f.datasource.aspect;

import com.f.datasource.DataSourceContextHolder;
import com.f.datasource.annotations.DataSource;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description： 数据源切换注解
 * @create 2018-09-27 下午1:27
 **/
@Aspect
@Component
@Order(1)
@Log4j2
public class DataSourceAspect {
    /**
     * 缓存方法注解值,如果值为空，取主数据源
     */
    private static final Map<Method, String> METHOD_CACHE = Maps.newHashMap();

    @Pointcut(("@within(com.f.datasource.annotations.DataSource)||@annotation(com.f.datasource.annotations.DataSource)"))
    protected void datasourceAspect() {

    }

    /**
     * 根据@DataSource的属性值设置不同的dataSourceKey,以供DynamicDataSource
     */
    @Before("datasourceAspect()")
    public void changeDataSourceBeforeMethodExecution(JoinPoint jp) {
        DataSourceContextHolder.setDataSourceKey(determineDatasource(jp));
    }

    /**
     * 设置为上次使用的数据源
     */
    @After("datasourceAspect()")
    public void restoreDataSourceAfterMethodExecution() {
        DataSourceContextHolder.clearDataSourceKey();
    }

    /**
     * @param jp
     * @return
     */
    public String determineDatasource(JoinPoint jp) {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class targetClass = method.getDeclaringClass();
        DataSource dataSource = method.getAnnotation(DataSource.class) != null ? method.getAnnotation(DataSource.class) : (DataSource) targetClass.getAnnotation(DataSource.class);
        //直接从缓存中获取
        if (METHOD_CACHE.containsKey(method)) {
            return METHOD_CACHE.get(method);
        } else {
            if (dataSource != null) {
                METHOD_CACHE.put(method, dataSource.value().getName());
            } else {
                METHOD_CACHE.put(method, DataSourceContextHolder.getDefaultDataSourceKey());
            }
            return METHOD_CACHE.get(method);
        }
    }
}
