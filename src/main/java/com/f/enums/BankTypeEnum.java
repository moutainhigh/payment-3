package com.f.enums;

import com.f.vo.BankTypeVo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BankTypeEnum {

    ICBC(102,"中国工商银行","工商"),
    ABC(103,"中国农业银行","农行"),
    BOC(104,"中国银行","中行"),
    CCB(105,"中国建设银行","建设"),
    CEB(303,"中国光大银行","光大"),
    ECITIC(302,"中信银行","中信"),
    HXB(304,"华夏银行","华夏"),
    CGB(306,"广发银行","广发"),
    SDB(307,"平安银行","平安"),
    CMB(308,"招商银行","招商"),
    CIB(309,"兴业银行","兴业"),
    SPDB(310,"上海浦东发展银行","浦发"),
    HSBC(501,"汇丰银行","汇丰"),
    PSBC(403,"中国邮政储蓄银行","邮政"),
    BOCOM(301,"交通银行","交通"),
    BANK_OF_SHANGHAI(9999,"上海银行","上海银行");
    @Getter
    @Setter
    int code;
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    String shortName;
    BankTypeEnum(int code,String name,String shortName){
        this.code = code;
        this.name = name;
        this.shortName = shortName;
    }
    public  static List<String> getBankTypeList(){
        List<String> retList = new ArrayList();
        Arrays.asList(BankTypeEnum.values()).forEach(type->retList.add(type.getName()));
        return retList;
    }

    public static BankTypeEnum getBankTypeByBankCode(int code){
        for(BankTypeEnum type: BankTypeEnum.values()){
            if(code == type.getCode())
                return  type;
        }
        return null;
    }
    public static BankTypeEnum getBankTypeByBankName(String name){
        for(BankTypeEnum type: BankTypeEnum.values()){
            if(name.equals(type.getName()))
                return  type;
        }
        return null;
    }

    public  BankTypeVo transToData(){
        BankTypeVo type = new BankTypeVo();
        type.setName(getName());
        return type;
    }
}
