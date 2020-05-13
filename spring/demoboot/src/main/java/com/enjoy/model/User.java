package com.enjoy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

// mongodb操作时@Document注解不是必须的。默认就是类名称
@Document(collection = "users")
public class User {

	@Id
	private Integer id;
	@Field
	private String userName;
	private boolean sex;
	private String remark;
	
	private List<Role> roles;
	
	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}


	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public User() {
	}
	
	
	public User(Integer id, String userName, boolean sex, String remark) {
		this.id = id;
		this.userName = userName;
		this.sex = sex;
		this.remark = remark;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}



	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}



	/**
	 * @return the sex
	 */
	public boolean isSex() {
		return sex;
	}



	/**
	 * @param sex the sex to set
	 */
	public void setSex(boolean sex) {
		this.sex = sex;
	}



	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}



	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", sex=" + sex + ", remark=" + remark + ", roles=" + roles
				+ "]";
	}



	

	
}
