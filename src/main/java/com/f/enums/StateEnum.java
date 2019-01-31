package com.f.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2018-10-11 下午4:30
 **/
@AllArgsConstructor()
public enum StateEnum {
    NORMAL(0, "启用"),
    DISABLED(1, "停用");
    @Getter
    private Integer code;
    @Getter
    private String desc;

    public static boolean isValidcode(Integer code) {
        for (StateEnum stateEnum : StateEnum.values()) {
            if (stateEnum.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
