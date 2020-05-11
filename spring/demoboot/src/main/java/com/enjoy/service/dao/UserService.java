package com.enjoy.service.dao;

import com.enjoy.model.User;

import java.util.List;

public interface UserService {

	List<User> selectAll();

	User selectById(int id);
	
	int delete(int id);

	int add(User user);
	
	int update(User user);

	int addRolesForUser(int userId, int[] roles);
	
	int removeRolesForUser(int userId, int[] roles);
	
}
