package com.fixture.dao;

import com.fixture.po.UUserRole;

public interface UUserRoleMapper {
    int insert(UUserRole record);

    int insertSelective(UUserRole record);
}