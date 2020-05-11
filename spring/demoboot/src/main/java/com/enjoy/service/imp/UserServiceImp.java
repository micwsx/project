package com.enjoy.service.imp;

import com.enjoy.dao.TUser;
import com.enjoy.dao.TUserRole;
import com.enjoy.model.User;
import com.enjoy.service.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
// @Scope(value="Michael")
public class UserServiceImp implements UserService {

	@Autowired
	private TUser userMapper;

	@Autowired
	private TUserRole userRoleMapper;

	@Override
	public List<User> selectAll() {
		return userMapper.selectAll();
	}

	@Transactional
	@Override
	public int delete(int id) {
		int result = userMapper.delete(id);
		if (result == 1) {
			// 删除对应角色
			userRoleMapper.deleteByUserId(id);
			return result;
		}
		return 0;
	}

	@Transactional
	@Override
	public int add(User user) {
		int result= userMapper.add(user);
		return result;
	}

	@Override
	public int update(User user) {
		return userMapper.update(user);
	}

	@Override
	public int addRolesForUser(int userId, int[] roles) {
		return userRoleMapper.addRolesForUser(userId, roles);
	}

	@Override
	public int removeRolesForUser(int userId,int[] roles){
		return userRoleMapper.removeRolesForUser(userId, roles);
	}

	@Override
	public User selectById(int id) {
		return userMapper.selectById(id);
	}

}
