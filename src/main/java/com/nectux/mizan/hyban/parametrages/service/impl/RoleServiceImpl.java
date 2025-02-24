//package com.nectux.mizan.hyban.parametrages.service.impl;
//
//import java.util.List;
//
//import com.nectux.mizan.hyban.parametrages.entity.Role;
//import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
//import com.nectux.mizan.hyban.parametrages.service.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityNotFoundException;
//
//@Transactional
//@Service("roleService")
//public class RoleServiceImpl implements RoleService {
//
//	@Autowired
//    RoleRepository roleRepository;
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public Role save(Role role) {
//		// TODO Auto-generated method stub
//		return roleRepository.save(role);
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public Boolean delete(Long id) {
//		// TODO Auto-generated method stub
//		Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
//		if(role == null)
//			return false;
//		roleRepository.delete(role);
//		return true;
//	}
//
//	@Override
//	public Role findRole(Long id) {
//		// TODO Auto-generated method stub
//		return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
//	}
//
//	@Override
//	public Role findByWorded(String worded) {
//		// TODO Auto-generated method stub
//		return roleRepository.findByLibelle(worded);
//	}
//
//	@Override
//	public List<Role> findRoles() {
//		// TODO Auto-generated method stub
//		return roleRepository.findAll();
//	}
//
//	@Override
//	public int count() {
//		// TODO Auto-generated method stub
//		return (int)roleRepository.count();
//	}
//
//}
