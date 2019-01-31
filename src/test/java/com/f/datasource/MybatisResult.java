package com.f.datasource;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.lang.reflect.Field;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2018-10-15 下午7:56
 **/
public class MybatisResult {
    /**
     * 1.用于获取结果集的映射关系
     */
    public static String getResultsStr(Class origin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Results({\n");
        for (Field field : origin.getDeclaredFields()) {
            String property = field.getName();
            //映射关系：对象属性(驼峰)->数据库字段(下划线)
            String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName()).toUpperCase();
            stringBuilder.append(String.format("@Result(property = \"%s\", column = \"%s\"),\n", property, column));
        }
        stringBuilder.append("})");
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        String str="{\"username\":\"admin\",\"password\":\"123456\"}";
        System.out.println("str = " + str);
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println("jsonObject = " + jsonObject.getString("password"));
    }
}
