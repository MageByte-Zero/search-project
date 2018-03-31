package com.zero.common.model;
import java.io.Serializable;


/**
 * 
 * 用户角色关联表
 * 
 **/
public class SysUserRole implements Serializable {

	/****/
	private Long id;

	/**角色表id**/
	private Long roleId;

	/**用户表id**/
	private Long uid;

	/****/
	private String createTime;

	/****/
	private String updateTime;



	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}

	public Long getRoleId(){
		return this.roleId;
	}

	public void setUid(Long uid){
		this.uid = uid;
	}

	public Long getUid(){
		return this.uid;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateTime(){
		return this.updateTime;
	}

}
