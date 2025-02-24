package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.dto.UserDto;
import com.nectux.mizan.hyban.parametrages.dto.UtilisateurDTO;
import com.nectux.mizan.hyban.parametrages.dto.UtilisateurRoleDTO;
import com.nectux.mizan.hyban.parametrages.entity.Role;
import com.nectux.mizan.hyban.parametrages.entity.RoleName;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
//import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
//import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRepository;
import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRepository;
import com.nectux.mizan.hyban.parametrages.repository.UtilisateurRoleRepository;
import com.nectux.mizan.hyban.securite.UserAccountStatus;
import com.nectux.mizan.hyban.utils.SecurityService;
import com.nectux.mizan.hyban.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private static final Logger logger = LogManager.getLogger(UtilisateurServiceImpl.class);
	
	@Autowired private UtilisateurRepository utilisateurRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private UtilisateurRoleRepository utilisateurRoleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Utilisateur save(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		utilisateur = utilisateurRepository.save(utilisateur);
		UtilisateurRole utilisateurRole = new UtilisateurRole();
		//utilisateurRole.setRole(roleRepository.findByLibelle("ROLE_ADMIN"));
		utilisateurRole.setUtilisateur(utilisateur);
		utilisateurRoleRepository.save(utilisateurRole);
		return utilisateur;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UtilisateurRoleDTO save(Long id, int role, String nomcomplet, String dNaissance, String telephone, String adresse, String email) {
		// TODO Auto-generated method stub
		UtilisateurRoleDTO utilisateurRoleDTO = new UtilisateurRoleDTO();
		try{
			Utilisateur utilisateur = new Utilisateur();
			UtilisateurRole utilisateurRole = new UtilisateurRole();
			if(id != null){
				utilisateurRole = utilisateurRoleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				utilisateur = utilisateurRepository.findById(utilisateurRole.getUtilisateur().getId()).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " ));
			}
			utilisateur.setNomComplet(nomcomplet);
			utilisateur.setEmail(email);
			if(id == null){
				String motDePasse = SecurityService.generateWord(8);
				//utilisateur.setBisapp(motDePasse);
				//utilisateur.setMotDePasse(motDePasse);
				String message="";
				message="Votre Inscription a été effectuée avec succes sur BETHEL-PAIE. Nous vous souhaitons la bienvenue sur BETHEL-PAIE, Nous esperons que vous serez satisfait.\r"+
						"Utilisateur:" + email  +"\r" +
						"Mot de passe: "+ motDePasse;

				// Utils.sendEmail(email, "Parametre de connexion BETHEL-PAIE ",message);
				//utilisateur.set(true);
				//utilisateur.setStatut(UserAccountStatus.STATUS_APPROVED.name());
			}
			utilisateur = utilisateurRepository.save(utilisateur);
//			if(role == 1)
//				utilisateurRole.setRole(RoleName.ROLE_ADMIN);
//			if(role == 2)
//				utilisateurRole.setRole(Role.valueOf("ROLE_DAF"));
//			if(role == 3)
//				utilisateurRole.setRole(Role.valueOf("ROLE_RH"));
//			if(role == 4)
//				utilisateurRole.setRole(Role.valueOf("ROLE_PTGE"));
			utilisateurRole.setUtilisateur(utilisateur);
			utilisateurRole = utilisateurRoleRepository.save(utilisateurRole);
			utilisateurRoleDTO.setRow(utilisateurRole);
			utilisateurRoleDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(utilisateur.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			utilisateurRoleDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT UTILISATEUR [NOM : ").append(nomcomplet).append(" - TELEPHONE : ").append(telephone).append(" - ADRESSE : ").append(adresse).append(" - EMAIL : ").append(email).append(" ]").toString());
			ex.getStackTrace();
		}
		return utilisateurRoleDTO;
	}

//	@Override
//	public UtilisateurRoleDTO save(String id, int role, String nomComplet, String dateNaissance, String telephone, String adresse, String email, String motDePasse) {
//		return null;
//	}
//
//	@Override
//	public UtilisateurDTO changePassword(Long id, String ancienMotDePasse, String nouveauMotDePasse) {
//		return null;
//	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UtilisateurRoleDTO save(Long id, int role, String nomcomplet, String dNaissance, String telephone, String adresse, String email, String motDePasse) {
		// TODO Auto-generated method stub
		UtilisateurRoleDTO utilisateurRoleDTO = new UtilisateurRoleDTO();
		try{
			Utilisateur utilisateur = new Utilisateur();
			UtilisateurRole utilisateurRole = new UtilisateurRole();
			if(id != null)
				utilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			utilisateur.setNomComplet(nomcomplet);
//			utilisateur.setDateNaissance(Utils.stringToDate(dNaissance, "dd/MM/yyyy"));
//			utilisateur.setTelephone(telephone);
			utilisateur.setUsername(adresse);
			utilisateur.setEmail(email);
			if(id == null){
				utilisateur.setPassword(motDePasse);
			}
			//utilisateur.setActif(true);
			//utilisateur.setStatut(UserAccountStatus.STATUS_APPROVED.name());
			utilisateur = utilisateurRepository.save(utilisateur);
			if(role == 1){
				Role role1 = new Role();RoleName roleName=RoleName.ADMIN;
				role1.setName(roleName);
				utilisateurRole.setRole(role1);
			}
			if(role == 2){
			Role role2 = new Role();RoleName roleName=RoleName.DAF;
			role2.setName(roleName);
			utilisateurRole.setRole(role2);
			}
			if(role == 3){
				Role role3 = new Role();RoleName roleName=RoleName.RH;
				role3.setName(roleName);
				utilisateurRole.setRole(role3);
			}
			if(role == 4){
				Role role4 = new Role();RoleName roleName=RoleName.PTGE;
				role4.setName(roleName);
				utilisateurRole.setRole(role4);
			}
			utilisateurRole.setUtilisateur(utilisateur);
			utilisateurRole = utilisateurRoleRepository.save(utilisateurRole);
			utilisateurRoleDTO.setRow(utilisateurRole);
			utilisateurRoleDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(utilisateur.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			utilisateurRoleDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT UTILISATEUR [NOM : ").append(nomcomplet).append(" - TELEPHONE : ").append(telephone).append(" - ADRESSE : ").append(adresse).append(" - EMAIL : ").append(email).append(" ]").toString());
			ex.getStackTrace();
		}
		return utilisateurRoleDTO;
	}

	@Override
	public UtilisateurDTO changePassword(Long id, String ancienMotDePasse, String nouveauMotDePasse) {
		// TODO Auto-generated method stub
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
//		if(ancienMotDePasse.equals(utilisateur.getBisapp())){
//			utilisateur.setBisapp(nouveauMotDePasse);
//			utilisateur.setMotDePasse(nouveauMotDePasse);
//
//			utilisateur = utilisateurRepository.save(utilisateur);
//			utilisateurDTO.setRow(utilisateur);
//			utilisateurDTO.setResult("succes");
//		} else{
//			utilisateurDTO.setRow(utilisateur);
//			utilisateurDTO.setResult("erreur_mdp_non_conforme");
//		}
		
		return utilisateurDTO;
	}
	@Transactional
	public Utilisateur createUtilisateur(UserDto utilisateurDto) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setUsername(utilisateurDto.getUsername());
		utilisateur.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
		utilisateur.setEmail(utilisateurDto.getEmail());
		utilisateur.setNomComplet(utilisateurDto.getNomComplet());

		// 🔹 Récupère ou crée le rôle avant de l'affecter à l'utilisateur
		Role role = getOrCreateRole(utilisateurDto.getRoleName());

		UtilisateurRole utilisateurRole = new UtilisateurRole();
		utilisateurRole.setUtilisateur(utilisateur);
		utilisateurRole.setRole(role); // ✅ Assure-toi que le rôle est bien enregistré avant de l'affecter

		utilisateur.getUtilisateurRoles().add(utilisateurRole);

		return utilisateurRepository.save(utilisateur); // ✅ Enregistre l'utilisateur en base
	}
	private Role getOrCreateRole(RoleName roleName) {
		return roleRepository.findByName(roleName)
				.orElseGet(() -> {
					Role newRole = new Role();
					newRole.setName(roleName);
					return roleRepository.save(newRole); // 🔹 On enregistre d'abord le rôle
				});
	}

	@Override
	public UtilisateurDTO forgetPassword(String email) {
		// TODO Auto-generated method stub
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		try{
			Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
			if(utilisateur == null){
				utilisateurDTO.setResult("erreur_email_inexistant");
			} else {
				String motDePasse = SecurityService.generateWord(6);
				utilisateur.setPassword(motDePasse);
				utilisateur = utilisateurRepository.save(utilisateur);
				utilisateurDTO.setRow(utilisateur);
				utilisateurDTO.setResult("succes");
				//utilisateur.setMotDePasse(motDePasse);
				//Utils.sendEmail(email, "Nouveau mot de passe BETHEL-PAIE", motDePasse);
			}
		} catch(Exception ex){
			ex.printStackTrace();
			utilisateurDTO.setResult("erreur_changement_mot_de_passe");
		}
		
		return utilisateurDTO;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		UtilisateurRole ur = utilisateurRoleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		Utilisateur utilisateur = utilisateurRepository.findById(ur.getUtilisateur().getId()).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " ));
		if(utilisateur == null)
			return false;
		List<UtilisateurRole> utilisateurRoles = utilisateurRoleRepository.findByUtilisateurUsername(utilisateur.getUsername());
		for(UtilisateurRole utilisateurRole : utilisateurRoles)
			utilisateurRoleRepository.delete(utilisateurRole);
		   utilisateurRepository.delete(utilisateur);
		return true;
	}

	@Override
	public Utilisateur findUtilisateur(Long id) {
		// TODO Auto-generated method stub
		return utilisateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public Utilisateur findByEmail(String email) {
		// TODO Auto-generated method stub
		return utilisateurRepository.findByEmail(email);
	}

	@Override
	public Utilisateur findByUsername(String email) {
		// TODO Auto-generated method stub
		return utilisateurRepository.findByUsername(email).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " ));
	}


	@Override
	public List<Utilisateur> findUtilisateurs() {
		// TODO Auto-generated method stub
		return utilisateurRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) utilisateurRepository.count();
	}

	@Override
	public Utilisateur authentification(String email, String password) {
		// TODO Auto-generated method stub
		Utilisateur customerAdvisor = utilisateurRepository.findByEmailAndPassword(email, password);
		if(customerAdvisor == null)
			customerAdvisor = new Utilisateur();
		return customerAdvisor;
	}

	@Override
	public UtilisateurRoleDTO loadUtilisateur(Pageable pageable) {
		// TODO Auto-generated method stub
		UtilisateurRoleDTO utilisateurDTO = new UtilisateurRoleDTO();
		Page<UtilisateurRole> page = utilisateurRoleRepository.findAll(pageable);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return utilisateurDTO;
	}

	@Override
	public UtilisateurRoleDTO loadUtilisateur(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		UtilisateurRoleDTO utilisateurDTO = new UtilisateurRoleDTO();
		Page<UtilisateurRole> page = utilisateurRoleRepository.findByNomCompletOrEmail(pageable, search,  search);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return utilisateurDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UtilisateurDTO changeStstus(Long id) {
		// TODO Auto-generated method stub
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
//		if(utilisateur.getActif())
//			utilisateur.setActif(false);
//		else
//			utilisateur.setActif(true);
		utilisateur = utilisateurRepository.save(utilisateur);
		utilisateurDTO.setRow(utilisateur);
		utilisateurDTO.setResult("succes");
		return utilisateurDTO;
	}

}
