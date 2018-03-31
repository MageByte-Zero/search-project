package com.zero.common.model;
import java.io.Serializable;


/**
 * 
 * 权限表
 * 
 **/
public class SysPermission implements Serializable {

	/****/
	private Long id;

	/**名称**/
	private String name;

	/**资源类型，[menu|button]**/
	private String resourceType;

	/**资源路径**/
	private String url;

	/**权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view**/
	private String permission;

	/**父编号**/
	private Long parentId;

	/**父编号列表**/
	private String parentIds;

	/**是否可用0-否，1-是**/
	private Integer available;

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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setResourceType(String resourceType){
		this.resourceType = resourceType;
	}

	public String getResourceType(){
		return this.resourceType;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return this.url;
	}

	public void setPermission(String permission){
		this.permission = permission;
	}

	public String getPermission(){
		return this.permission;
	}

	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public Long getParentId(){
		return this.parentId;
	}

	public void setParentIds(String parentIds){
		this.parentIds = parentIds;
	}

	public String getParentIds(){
		return this.parentIds;
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
