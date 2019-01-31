package com.f.mvc.mapper;

import com.f.core.MyMapper;
import com.f.mvc.entity.AccountBank;
import com.f.mvc.entity.Bank;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface BankMapper extends MyMapper<Bank> {
}