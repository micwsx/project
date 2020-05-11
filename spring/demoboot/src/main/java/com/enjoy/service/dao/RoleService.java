package com.enjoy.service.dao;

import com.enjoy.model.Role;

import java.util.List;

public interface RoleService {

	List<Role> selectAll();

	List<Role> selectCriteria(Role criteria);
	
	Role selectById(Integer id);

	int delete(int id);

	int add(Role role);
	
	int update(Role role);

}
