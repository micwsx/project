package com.enjoy.dao;

import com.enjoy.model.User;

import java.util.List;
import java.util.Map;

public interface TUser {

	int add(User user);
	
	int addBatch(List<User> users);
	
	int delete(int id);
	
	int deleteBatch(int[] ids);
	
	int update(User user);
	
	// 如果统一修改某些字段，是可以实现的。
	int batchUpdateWithMap(Map map);
	
	User selectById(int id);
	
	List<User> selectAll();
	
	List<User> selectCriteria(User user);
	
	
	
}
