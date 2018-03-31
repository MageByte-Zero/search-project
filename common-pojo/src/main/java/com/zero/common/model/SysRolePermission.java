package com.zero.common.model;
import java.io.Serializable;


/**
 * 
 * 角色权限关联表
 * 
 **/
public class SysRolePermission implements Serializable {

	/****/
	private Long id;

	/**权限表id**/
	private Long permissionId;

	/**角色表id**/
	private Long roleId;

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

	public void setPermissionId(Long permissionId){
		this.permissionId = permissionId;
	}

	public Long getPermissionId(){
		return this.permissionId;
	}

	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}

	public Long getRoleId(){
		return this.roleId;
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
