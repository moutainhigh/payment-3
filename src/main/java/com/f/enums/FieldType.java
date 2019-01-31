package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-15 15:00
 * 参数类型
 */
public enum FieldType {
    MCH_ID("商户id"),
    PAY_TYPE("支付类型"),
    ORDER_NO("订单号"),
    NOTIFY_URL("回调地址"),
    RETURN_URL("返回地址"),
    SIGN("签名"),
    FIX("固定参数"),
    BANK_PROVINCE("银行省份"),
    BANK_CITY("银行城市"),
    BANK_CODE("银行编码"),
    BANK_NAME("银行名称"),
    ACCOUNT_NAME("开户人"),
    BANK_BRANCH("开户行"),
    PAYEE("收款人账号"),
    PAYEE_NAME("收款人姓名"),
    THIRD_ORDERNO("第三方流水号"),
    YUAN("金额 元"),
    FEN("金额 分"),
    DATE("日期"),
    TIME("时间戳"),
    IP("IP"),
    CODE("CODE"),
    MSG("MSG"),
    TARGET_CONTENT("目标内容"),
    TARGET_URL("目标链接");

    private static Map<Integer, FieldType> maps = new HashMap<>();
    private static Map<Integer, String> ordinal2Desc = new HashMap<>();
    private static Map<String, String> name2Desc = new HashMap<>();

    static {
        for (FieldType type : FieldType.values()) {
            maps.put(type.ordinal(), type);
            ordinal2Desc.put(type.ordinal(), type.description);
            name2Desc.put(type.name(), type.description);
        }
    }

    private String description;

    private FieldType(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    public static FieldType valueOf(int ordinal) {
        FieldType result = maps.get(ordinal);
        return result;
    }

    public static Map<Integer, String> getOrdinal2Desc() {
        return ordinal2Desc;
    }

    public static Map<String, String> getName2Desc() {
        return name2Desc;
    }
}
