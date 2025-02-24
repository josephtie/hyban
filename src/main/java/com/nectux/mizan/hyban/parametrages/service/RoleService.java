package com.nectux.mizan.hyban.parametrages.service;

import com.nectux.mizan.hyban.parametrages.entity.Role;

public interface RoleService {
	
	public Role save(Role role);
	
	public Boolean delete(Long id);
	
	public Role findRole(Long id);
	
	public Role findByWorded(String worded);
	
	public java.util.List<Role> findRoles();
	
	public int count();

}
