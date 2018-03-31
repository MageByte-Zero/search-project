package com.zero.dao;

import com.zero.common.model.SysUserRole;

/**
 * 
 * SysUserRoleMapper数据库操作接口类
 * 
 **/
public interface SysUserRoleMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	SysUserRole selectByPrimaryKey(Long id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(SysUserRole record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(SysUserRole record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(SysUserRole record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(SysUserRole record);

}