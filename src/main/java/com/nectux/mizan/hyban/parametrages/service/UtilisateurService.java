package com.nectux.mizan.hyban.parametrages.service;

import com.nectux.mizan.hyban.parametrages.dto.UserDto;
import com.nectux.mizan.hyban.parametrages.dto.UtilisateurDTO;
import com.nectux.mizan.hyban.parametrages.dto.UtilisateurRoleDTO;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import org.springframework.data.domain.Pageable;

public interface UtilisateurService {
	
	public Utilisateur save(Utilisateur utilisateur);
	
	public UtilisateurRoleDTO save(Long id, int role, String nomComplet, String dateNaissance, String telephone, String adresse, String email);
	
	public UtilisateurRoleDTO save(Long id, int role, String nomComplet, String dateNaissance, String telephone, String adresse, String email, String motDePasse);
	
	public UtilisateurDTO changePassword(Long id, String ancienMotDePasse, String nouveauMotDePasse);
	
	public UtilisateurDTO forgetPassword(String email);
	
	public Boolean delete(Long id);
	
	public Utilisateur findUtilisateur(Long id);
	
	public Utilisateur findByEmail(String email);
	public Utilisateur findByUsername(String email);
	
	public java.util.List<Utilisateur> findUtilisateurs();
	
	public int count();
	
	public Utilisateur authentification(String email, String password);
	
	public UtilisateurRoleDTO loadUtilisateur(Pageable pageable);
	
	public UtilisateurRoleDTO loadUtilisateur(Pageable pageable, String search);

	public UtilisateurDTO changeStstus(Long id);

	public Utilisateur createUtilisateur(UserDto utilisateurDto);

}
