package com.zero.dao;

import com.zero.common.model.UserInfo;

/**
 * UserInfoMapper数据库操作接口类
 **/
public interface UserInfoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    UserInfo selectByPrimaryKey(Long id);

    /**
     * 删除（根据主键ID删除）
     **/
    int deleteByPrimaryKey(Long id);

    /**
     * 添加
     **/
    int insert(UserInfo record);

    /**
     * 添加 （匹配有值的字段）
     **/
    int insertSelective(UserInfo record);

    /**
     * 修改 （匹配有值的字段）
     **/
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * 修改（根据主键ID修改）
     **/
    int updateByPrimaryKey(UserInfo record);

    /**
     * @param username
     * @return
     */
    UserInfo findByUsername(String username);
}