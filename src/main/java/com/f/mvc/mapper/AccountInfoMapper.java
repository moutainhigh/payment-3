package com.f.mvc.mapper;

import com.f.core.MyMapper;
import com.f.mvc.entity.AccountBank;
import com.f.mvc.entity.AccountInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AccountInfoMapper extends MyMapper<AccountInfo> {
}