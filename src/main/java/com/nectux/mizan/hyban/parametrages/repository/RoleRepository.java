package com.nectux.mizan.hyban.parametrages.repository;

import com.nectux.mizan.hyban.parametrages.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.parametrages.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public java.util.List<Role> findAll();

	//public Role findByName(String libelle);
	Optional<Role> findByName(RoleName name);
}
