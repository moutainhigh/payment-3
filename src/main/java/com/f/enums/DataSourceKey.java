package com.f.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 数据源常量,此常量必须和配置属性文件中配置的数据源一致，不然会启动报错
 */
@AllArgsConstructor()
public enum DataSourceKey {
    WITHDRAW("withdraw");
    private static final Map<String, DataSourceKey> CACHE = Maps.newHashMap();

    static {
        for (DataSourceKey dataSourceKey : DataSourceKey.values()) {
            CACHE.put(dataSourceKey.name, dataSourceKey);
        }
    }

    @Getter
    private final String name;

    public static boolean isNotDefined(String name) {
        return CACHE.get(name) == null;
    }
}
