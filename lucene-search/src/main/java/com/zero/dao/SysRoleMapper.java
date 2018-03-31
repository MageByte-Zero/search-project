package com.zero.dao;


import com.zero.common.model.SysRole;

import java.util.List;


/**
 * 
 * SysRoleMapper数据库操作接口类
 * 
 **/
public interface SysRoleMapper{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	SysRole selectByPrimaryKey(Long id);

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
	int insert(SysRole record);

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective(SysRole record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(SysRole record);

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey(SysRole record);

	/**
	 * 获取角色列表
	 * @param uid
	 * @return
	 */
    List<SysRole> findByUid(Long uid);
}