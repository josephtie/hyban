package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.Iterator;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.parametrages.entity.Role;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRoleRepository;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("utilisateurRoleService")
public class UtilisateurRoleServiceImpl implements UtilisateurRoleService {
	
	@Autowired UtilisateurRoleRepository utilisateurRoleRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UtilisateurRole save(UtilisateurRole utilisateurRole) {
		// TODO Auto-generated method stub
		return utilisateurRoleRepository.save(utilisateurRole);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		UtilisateurRole utilisateurRole = utilisateurRoleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(utilisateurRole == null)
			return false;
		utilisateurRoleRepository.delete(utilisateurRole);
		return true;
	}

	@Override
	public UtilisateurRole findUtilisateurRole(Long id) {
		// TODO Auto-generated method stub
		return utilisateurRoleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public List<UtilisateurRole> findByRole(Role role) {
		// TODO Auto-generated method stub
		return utilisateurRoleRepository.findByRole(role);
	}

	@Override
	public List<UtilisateurRole> findByUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		return utilisateurRoleRepository.findByUtilisateurUsername(utilisateur.getUsername());
	}

	@Override
	public List<UtilisateurRole> findUtilisateurRoles() {
		// TODO Auto-generated method stub
		return utilisateurRoleRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) utilisateurRoleRepository.count();
	}

	@Override
	public String getRolesCSV(List<UtilisateurRole> utilisateurRoles) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for (Iterator<UtilisateurRole> iter = utilisateurRoles.iterator(); iter.hasNext(); ) {
		   sb.append(iter.next().getRole().getAuthority());
		   if (iter.hasNext()) {
			   sb.append(',');
		   }
		}
		return sb.toString();
	}

}
