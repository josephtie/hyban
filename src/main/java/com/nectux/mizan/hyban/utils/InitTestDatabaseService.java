package com.nectux.mizan.hyban.utils;

//import javax.annotation.PostConstruct;

import com.nectux.mizan.hyban.parametrages.entity.Role;
import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import com.nectux.mizan.hyban.parametrages.entity.TypeService;
//import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
import com.nectux.mizan.hyban.parametrages.service.TypeContratService;
import com.nectux.mizan.hyban.parametrages.service.TypeServiceService;
import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Nationnalite;
import com.nectux.mizan.hyban.personnel.entity.Service;
import com.nectux.mizan.hyban.personnel.service.CategorieService;
import com.nectux.mizan.hyban.personnel.service.FonctionService;
import org.springframework.beans.factory.annotation.Autowired;

import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.service.NationnaliteService;
import com.nectux.mizan.hyban.personnel.service.ServiceService;

public class InitTestDatabaseService {

	//@Autowired private RoleRepository roleRepository;
	@Autowired private ServiceService serviceService;
	@Autowired private FonctionService fonctionService;
	@Autowired private CategorieService categorieService;
	@Autowired private TypeContratService typeContratService;
	@Autowired private TypeServiceService typeServiceService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private NationnaliteService nationaliteService;
	
	//@PostConstruct
	public void init() {
//		if(roleRepository.count() == 0){
//			String roleName = "ROLE_ADMIN";
//			Role role = Role.valueOf(roleName);
//			roleRepository.save(role);
//			String roleName1 = "ROLE_DAF";
//			Role role1 = Role.valueOf(roleName1);
//			roleRepository.save(role1);
//			String roleName2 = "ROLE_RH";
//			Role role2 = Role.valueOf(roleName2);
//			roleRepository.save(role2);
//			String roleName3 = "ROLE_PTGE";
//			Role role3 = Role.valueOf(roleName3);
//			//role.setLibelle("ROLE_PTGE");
//			roleRepository.save(role3);
//		}
		
		if(nationaliteService.count() == 0){
			Nationnalite nationalite = new Nationnalite();
			nationalite.setLibelle("IVOIRIEN");
			nationaliteService.save(nationalite);
		}
		
		if(typeServiceService.count() == 0){
			TypeService typeService = new TypeService();
			typeService.setLibelle("DIRECTION");
			typeService = typeServiceService.save(typeService);
			
			if(serviceService.count() == 0){
				Service service = new Service();
				service.setLibelle("DIRECTION INFORMATION");
				service.setTypeService(typeService);
				serviceService.save(service);
			}
		}
		
		if(typeContratService.count() == 0){
			TypeContrat typeContrat = new TypeContrat();
			typeContrat.setLibelle("CDI");
			typeContratService.save(typeContrat);
		}
		
		if(fonctionService.count() == 0){
			Fonction fonction = new Fonction();
			fonction.setLibelle("DEVELOPPEUR");
			fonctionService.save(fonction);
		}
		
		if(categorieService.count() == 0){
			Categorie categorie = new Categorie();
			categorie.setLibelle("7A");
			categorie.setSalaireDeBase(170000.0);
			categorieService.save(categorie);
			
		}
		
		if(utilisateurService.count() == 0){
			utilisateurService.save(null, 1, "Brice-Boris BEDA", "07/10/1953", "07441285", "08 BP 261 Abidjan 08", "brice.beda@gmail.com", "admin001");
		}
	}
}
