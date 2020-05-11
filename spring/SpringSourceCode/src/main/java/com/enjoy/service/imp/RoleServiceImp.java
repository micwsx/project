package com.enjoy.service.imp;

import com.enjoy.dao.TRole;
import com.enjoy.model.Role;
import com.enjoy.service.dao.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class RoleServiceImp implements RoleService {

	@Autowired
	private TRole roleMapper;

	@Override
	public List<Role> selectAll() {
		return roleMapper.selectAll();
	}

	@Override
	public List<Role> selectCriteria(Role criteria) {
		return roleMapper.selectCriteria(criteria);
	}

	@Override
	public Role selectById(Integer id) {
		return roleMapper.selectById(id);
	}

	@Override
	public int delete(int id) {
		return roleMapper.delete(id);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public int add(Role role) {
		int result=roleMapper.add(role);
		throw new RuntimeException("error!!!!!");
		//return result;
	}

	@Override
	public int update(Role role) {
		return roleMapper.update(role);
	}
	
	

	

}
