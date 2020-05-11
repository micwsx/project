package com.enjoy.dao;

import com.enjoy.model.Role;

import java.util.List;
import java.util.Map;

public interface TRole {

	int add(Role role);

	int addBatch(List<Role> roles);

	int delete(int id);

	int deleteBatch(List<Role> roles);

	int deleteBatch(int[] ids);

	int update(Role role);

	// 如果统一修改某些字段，是可以实现的。
	int batchUpdateWithMap(Map map);

	Role selectById(Integer id);

	List<Role> selectAll();

	List<Role> selectCriteria(Role role);

}
