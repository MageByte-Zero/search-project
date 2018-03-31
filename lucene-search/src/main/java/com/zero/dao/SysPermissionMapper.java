package com.zero.dao;


import com.zero.common.model.SysPermission;

import java.util.List;


/**
 * 
 * SysPermissionMapper数据库操作接口类
 * 
 **/
public interface SysPermissionMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	SysPermission selectByPrimaryKey(Long id);

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
	int insert(SysPermission record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(SysPermission record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(SysPermission record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(SysPermission record);

	/**
	 * /**
	 * 根据roleId获取权限列表
	 * @param roleId
	 * @return
	 */
	List<SysPermission> findByRoleId(Long roleId);
}