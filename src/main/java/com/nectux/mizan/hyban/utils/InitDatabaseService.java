package com.nectux.mizan.hyban.utils;



import com.nectux.mizan.hyban.paie.entity.Pret;
import com.nectux.mizan.hyban.parametrages.dto.UserDto;
import com.nectux.mizan.hyban.parametrages.entity.*;
import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
import com.nectux.mizan.hyban.parametrages.service.*;
import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.personnel.service.CategorieService;
import com.nectux.mizan.hyban.personnel.service.FonctionService;
import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import com.nectux.mizan.hyban.paie.service.PretService;
//import com.nectux.mizan.hyban.parametrages.repository.RoleRepository;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Nationnalite;
import com.nectux.mizan.hyban.personnel.service.NationnaliteService;
import com.nectux.mizan.hyban.rh.absences.service.AbsencesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InitDatabaseService {

	@Autowired private PretService pretService;
	@Autowired private MoisService moisService;
	@Autowired private AbsencesService absencesService;
	@Autowired private RoleRepository roleRepository;
	@Autowired private FonctionService fonctionService;
	@Autowired private CategorieService categorieService;
	@Autowired private TypeContratService typeContratService;
	@Autowired private TypeServiceService typeServiceService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private NationnaliteService nationaliteService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@Bean
	public ApplicationRunner initData() {
		return args -> {

			if (roleRepository.count() == 0) {
				createRole(RoleName.ADMIN);
				createRole(RoleName.DAF);
				createRole(RoleName.RH);
				createRole(RoleName.PTGE);
			}

			if (nationaliteService.count() == 0) {
				Nationnalite nationalite = new Nationnalite();
				nationalite.setLibelle("IVOIRIENNE");
				nationaliteService.save(nationalite);
			}

			if (absencesService.count() == 0) {
				Absences absence = new Absences();
				absence.setFaute("Conge paye");
				absencesService.saver("Conge paye", "");
			}

			if (typeServiceService.count() == 0) {
				TypeService typeService = new TypeService();
				typeService.setLibelle("DIRECTION");
				typeService = typeServiceService.save(typeService);
			
			/*if(serviceService.count() == 0){
				Service service = new Service();
				service.setLibelle("DIRECTION INFORMATION");
				service.setTypeService(typeService);
				serviceService.save(service);
			}*/

				typeService = new TypeService();
				typeService.setLibelle("DEPARTEMENT");
				typeService = typeServiceService.save(typeService);

				typeService = new TypeService();
				typeService.setLibelle("SERVICE");
				typeService = typeServiceService.save(typeService);
			}

			if (typeContratService.count() == 0) {
				TypeContrat typeContrat = new TypeContrat();
				typeContrat.setLibelle("CDI");
				typeContratService.save(typeContrat);


				TypeContrat typeContrat1 = new TypeContrat();
				typeContrat1.setLibelle("CDD");
				typeContratService.save(typeContrat1);


				TypeContrat typeContrat2 = new TypeContrat();
				typeContrat2.setLibelle("ESSAI");
				typeContratService.save(typeContrat2);

				TypeContrat typeContrat3 = new TypeContrat();
				typeContrat3.setLibelle("CONSULTANCE");
				typeContratService.save(typeContrat3);
			}

			if (fonctionService.count() == 0) {
				Fonction fonction = new Fonction();
				fonction.setLibelle("DEVELOPPEUR");
				fonctionService.save(fonction);
			}

			if (categorieService.count() == 0) {
				Categorie categorie = new Categorie();
				categorie.setLibelle("1A");
				categorie.setSalaireDeBase(Math.rint(60000.0));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("1B");
				categorie.setSalaireDeBase(Math.rint(68885 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("2");
				categorie.setSalaireDeBase(Math.rint(73870 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("3");
				categorie.setSalaireDeBase(Math.rint(76109 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("4");
				categorie.setSalaireDeBase(Math.rint(82243 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("5");
				categorie.setSalaireDeBase(Math.rint(98138 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("6");
				categorie.setSalaireDeBase(Math.rint(106543 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("7A");
				categorie.setSalaireDeBase(Math.rint(106879 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("7B");
				categorie.setSalaireDeBase(Math.rint(112358 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("8A");
				categorie.setSalaireDeBase(Math.rint(112358 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("8B");
				categorie.setSalaireDeBase(Math.rint(114022 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("8C");
				categorie.setSalaireDeBase(Math.rint(114022 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("9A");
				categorie.setSalaireDeBase(Math.rint(115431 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("9B");
				categorie.setSalaireDeBase(Math.rint(129862 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("10A");
				categorie.setSalaireDeBase(Math.rint(136324 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("10B");
				categorie.setSalaireDeBase(Math.rint(152684 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);

				categorie = new Categorie();
				categorie.setLibelle("10C");
				categorie.setSalaireDeBase(Math.rint(171770 * 1.09));
				categorie.setIndemniteLogement(0.0);
				categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("11"); categorie.setSalaireDeBase(Math.rint(190857 * 1.09)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);*/
//			Categorie categorie = new Categorie();
//			categorie.setLibelle("1A"); categorie.setSalaireDeBase(Math.rint(37000.0)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("1B"); categorie.setSalaireDeBase(Math.rint(68885 )); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("2"); categorie.setSalaireDeBase(Math.rint(73870)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("3"); categorie.setSalaireDeBase(Math.rint(76109 )); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("4"); categorie.setSalaireDeBase(Math.rint(82243 )); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("5"); categorie.setSalaireDeBase(Math.rint(98138 )); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("6"); categorie.setSalaireDeBase(Math.rint(106543)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("7A"); categorie.setSalaireDeBase(Math.rint(106879)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("7B"); categorie.setSalaireDeBase(Math.rint(112358)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("8A"); categorie.setSalaireDeBase(Math.rint(112358)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("8B"); categorie.setSalaireDeBase(Math.rint(114022)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("8C"); categorie.setSalaireDeBase(Math.rint(114022)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("9A"); categorie.setSalaireDeBase(Math.rint(115431)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("9B"); categorie.setSalaireDeBase(Math.rint(129862)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("10A"); categorie.setSalaireDeBase(Math.rint(136324)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("10B"); categorie.setSalaireDeBase(Math.rint(152684)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("10C"); categorie.setSalaireDeBase(Math.rint(171770)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
//
//			categorie = new Categorie();
//			categorie.setLibelle("11"); categorie.setSalaireDeBase(Math.rint(190857)); categorie.setIndemniteLogement(0.0);
//			categorieService.save(categorie);
			}


			if (utilisateurService.count() == 0) {
				UserDto utilisateur=new UserDto();//UtilisateurRole utilisateurRole=new UtilisateurRole();
				utilisateur.setPassword("admin001");
				utilisateur.setEmail("joseph.tie@gmail.com");
				utilisateur.setNomComplet("Joseph Tie");
				utilisateur.setUsername("foumoassa");
				//RoleName name= Role.("ROLE_ADMIN");
				utilisateur.setRoleName(RoleName.ADMIN);
				//utilisateur=utilisateurService.save(utilisateur);roleRepository.findById(1L).orElseThrow())
				//utilisateurRole.setUtilisateur(utilisateur);
			//	utilisateurRole.setRole(utilisateur.getRole());
			//	utilisateurRoleService.save(utilisateurRole);
                utilisateurService.createUtilisateur(utilisateur);
			//	utilisateurService.save(null, 5, "Joseph Tie", "07/10/1953", "07853840", "sandya", "joseph.tie@gmail.com", "admin001");
			}
			if (moisService.count() == 0) {
				Mois mois1 = new Mois();
				mois1.setId(1L);
				mois1.setMois("JANVIER");
				mois1 = moisService.save(mois1);

				Mois mois2 = new Mois();
				mois2.setId(2L);
				mois2.setMois("FEVRIER");
				mois2 = moisService.save(mois2);

				Mois mois3 = new Mois();
				mois3.setId(3L);
				mois3.setMois("MARS");
				mois3 = moisService.save(mois3);

				Mois mois4 = new Mois();
				mois4.setId(4L);
				mois4.setMois("AVRIL");
				mois4 = moisService.save(mois4);

				Mois mois5 = new Mois();
				mois5.setId(5L);
				mois5.setMois("MAI");
				mois5 = moisService.save(mois5);

				Mois mois6 = new Mois();
				mois6.setId(6L);
				mois6.setMois("JUIN");
				mois6 = moisService.save(mois6);

				Mois mois7 = new Mois();
				mois7.setId(7L);
				mois7.setMois("JUILLET");
				mois7 = moisService.save(mois7);

				Mois mois8 = new Mois();
				mois8.setId(8L);
				mois8.setMois("AOUT");
				mois8 = moisService.save(mois8);

				Mois mois9 = new Mois();
				mois9.setId(9L);
				mois9.setMois("SEPTEMBRE");
				mois9 = moisService.save(mois9);

				Mois mois10 = new Mois();
				mois10.setId(10L);
				mois10.setMois("OCTOBRE");
				mois10 = moisService.save(mois10);

				Mois mois11 = new Mois();
				mois11.setId(11L);
				mois11.setMois("NOVEMBRE");
				mois11 = moisService.save(mois11);

				Mois mois12 = new Mois();
				mois12.setId(12L);
				mois12.setMois("DECEMBRE");
				mois12 = moisService.save(mois12);
			}

			if (pretService.count() == 0) {
				Pret pret1 = new Pret();
				pret1.setId(1L);
				pret1.setLibelle("PRET");
				pret1 = pretService.save(pret1);

				Pret pret2 = new Pret();
				pret2.setId(2L);
				pret2.setLibelle("AVANCES & ACOMPTES");
				pret2 = pretService.save(pret2);

				Pret pret3 = new Pret();
				pret3.setId(3L);
				pret3.setLibelle("ALIOS");
				pret3 = pretService.save(pret3);
			}
		};

	}
	private void createRole(RoleName roleName) {
		Role role = new Role();
		role.setName(roleName);
		roleRepository.save(role);
	}
}