package com.f.mvc.dao;

import com.f.mvc.entity.SysRole;

import java.util.List;

/**
 * User: bvsoo
 * Date: 2018/6/28
 * Time: 下午2:08
 */
public interface SysRoleDao {
    List<SysRole> findAllSysRole();

    SysRole findSysRoleById(Long id);

    SysRole findSysRoleByRole(String role);

    int addSysRole(SysRole role);
}
