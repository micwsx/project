package com.enjoy.dao;

import org.apache.ibatis.annotations.Param;

public interface TUserRole {

	int add(@Param("userId") int userId, @Param("roleId") int roleId);

	int addRolesForUser(@Param(value = "userId") int userId, @Param("array") int[] roles);
	
	int removeRolesForUser(@Param(value = "userId") int userId, @Param("array") int[] roles);
	
	int deleteByUserId(int userId);
	
	int deleteByRoleId(int roleId);
	
}
