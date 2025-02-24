package com.nectux.mizan.hyban.parametrages.service;

import com.nectux.mizan.hyban.parametrages.entity.Role;
import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;

public interface UtilisateurRoleService {
	
	public UtilisateurRole save(UtilisateurRole utilisateurRole);
	
	public Boolean delete(Long id);
	
	public UtilisateurRole findUtilisateurRole(Long id);
	
	public java.util.List<UtilisateurRole> findByRole(Role role);
	
	public java.util.List<UtilisateurRole> findByUtilisateur(Utilisateur utilisateur);
	
	public java.util.List<UtilisateurRole> findUtilisateurRoles();
	
	public int count();
	
	public String getRolesCSV(java.util.List<UtilisateurRole> utilisateurRole);

}
