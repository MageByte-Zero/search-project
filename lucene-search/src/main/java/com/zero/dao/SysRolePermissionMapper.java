package com.zero.dao;

import com.zero.common.model.SysRolePermission;

/**
 * 
 * SysRolePermissionMapper数据库操作接口类
 * 
 **/
public interface SysRolePermissionMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	SysRolePermission selectByPrimaryKey(Long id);

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
	int insert(SysRolePermission record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(SysRolePermission record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(SysRolePermission record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(SysRolePermission record);

}