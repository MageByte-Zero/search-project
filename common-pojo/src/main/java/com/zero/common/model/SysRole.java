package com.zero.common.model;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 角色表
 * 
 **/
public class SysRole implements Serializable {

	/****/
	private Long id;

	/**角色标识程序中判断使用,如"admin",这个是唯一的:**/
	private String role;

	/**角色描述;角色描述,UI界面显示使用**/
	private String description;

	/**是否可用:0否，1-是。如果不可用将不会添加给用户**/
	private Integer available;

	/****/
	private String createTime;

	/****/
	private String updateTime;

	private List<SysPermission> permissions;

	private List<UserInfo> userInfos;

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

	public List<UserInfo> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return this.role;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setAvailable(Integer available){
		this.available = available;
	}

	public Integer getAvailable(){
		return this.available;
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
