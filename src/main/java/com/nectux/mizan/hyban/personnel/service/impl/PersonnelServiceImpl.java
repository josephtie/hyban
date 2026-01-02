package com.nectux.mizan.hyban.personnel.service.impl;

import java.util.*;

import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.repository.BulletinPaieRepository;
import com.nectux.mizan.hyban.paie.repository.PrimePersonnelRepository;
import com.nectux.mizan.hyban.parametrages.entity.Banque;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.parametrages.repository.BanqueRepository;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.repository.PlanningCongeRepository;
import com.nectux.mizan.hyban.parametrages.repository.TypeContratRepository;
import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.dto.FonctionDTO;
import com.nectux.mizan.hyban.personnel.dto.PersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.entity.Service;
import com.nectux.mizan.hyban.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;



import com.nectux.mizan.hyban.paie.entity.LivreDePaie;
import com.nectux.mizan.hyban.parametrages.repository.ExerciceRepository;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.repository.CategorieRepository;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.FonctionRepository;
import com.nectux.mizan.hyban.personnel.repository.NationnaliteRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.ServiceRepository;
import com.nectux.mizan.hyban.personnel.service.PersonnelService;

import javax.persistence.EntityNotFoundException;


@Transactional
@org.springframework.stereotype.Service("personnelService")
public class PersonnelServiceImpl implements PersonnelService {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonnelServiceImpl.class);

	@Autowired private ServiceRepository serviceRepository;
	@Autowired private ExerciceRepository exerciceRepository;
	@Autowired private FonctionRepository fonctionRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private CategorieRepository categorieRepository;
	@Autowired private TypeContratRepository typeContratRepository;
	@Autowired private NationnaliteRepository nationnaliteRepository;
	@Autowired private BanqueRepository banqueRepository;
	@Autowired private BulletinPaieRepository bulletinPaieRepository;
	@Autowired private PlanningCongeRepository planningCongeRepository;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PrimePersonnelRepository primePersonnelRepository;
	public MethodsShared methodsShared;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Personnel save(Personnel personnel) {
		// TODO Auto-generated method stub
		return personnelRepository.save(personnel);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ContratPersonnelDTO save(Long id, String nom, String prenom, Long nationalite, Long service, Long categorie, Long fonction,
                                    Long typeContrat, String matricule, String sexe, String dateNaissance, String lieuNaissance, String email,
                                    String residence, int situationMatrimoniale, int nombreEnfant, String dateArrivee, String numeroCNPS,
                                    String adresse, String dateDebut, String dateFin, Double salaireNet, Double indemnitelogement, String modePaiement, Long idbanque, String numeroCompte, String numeroGuichet, String rib,
                                    int ancienneteInitial, Boolean carec, String typemp, String telephone, int situationMedaill, int situationEmploie, String dateRetourcg, Double indemniteRespons, Double indemniteRepresent, Double indemniteTransport, Double sursalaire) {
		// TODO Auto-generated method stub
		ContratPersonnelDTO contratPersonnelDTO = new ContratPersonnelDTO();
		Personnel personnel = new Personnel();Personnel personnelmat = new Personnel();Personnel personnelcnps = new Personnel();
		ContratPersonnel contratPersonnel = new ContratPersonnel();
		personnelmat=personnelRepository.findByMatricule(matricule);
		//personnelcnps=personnelRepository.findByNumeroCnps(numeroCNPS);
		try{
			if(personnelmat==null ){

			if(id != null){
				personnel = personnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + id));;}
				
			
			System.out.println("############################################################################# 1 :" + personnel.toString());			
			personnel.setMatricule(matricule);
			personnel.setNom(nom);
			personnel.setPrenom(prenom);
				if(sexe == "")
					personnel.setSexe("Masculin");
				else
				personnel.setSexe(sexe);	
			personnel.setDateNaissance(Utils.stringToDate(dateNaissance, "dd/MM/yyyy"));
			personnel.setLieuNaissance(lieuNaissance);
			personnel.setEmail(email);
			personnel.setResidence(residence);
			if(situationMatrimoniale < 0)
			personnel.setSituationMatrimoniale(2);
			else
			personnel.setSituationMatrimoniale(situationMatrimoniale);
			
			if(nombreEnfant < 0)
			personnel.setNombrEnfant(0);
			else
			personnel.setNombrEnfant(nombreEnfant);
			if(id == null){
			personnel.setDateArrivee(Utils.stringToDate(dateArrivee, "dd/MM/yyyy"));}
			personnel.setDateRetourcge(Utils.stringToDate(dateRetourcg, "dd/MM/yyyy"));
			personnel.setNumeroCnps(numeroCNPS);
			personnel.setAdresse(adresse);
			personnel.setModePaiement(modePaiement);
			Banque banqued=banqueRepository.findById(idbanque).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(banqued==null)
			personnel.setBanquek(null);
			else
			personnel.setBanquek(banqueRepository.findById(idbanque).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id)));
			personnel.setCarec(carec);
			personnel.setTypeSalarie(typemp);
			personnel.setNumeroCompte(numeroCompte);
			personnel.setNumeroGuichet(numeroGuichet);
			personnel.setRib(rib);
			personnel.setService(serviceRepository.findById(service).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + service)));
			personnel.setNationnalite(nationnaliteRepository.findById(nationalite).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + nationalite)));
			personnel.setStatut(true);
			personnel.setRetraitEffect(false);
			//personnel.setUrlPhoto(filename);;
		//	personnel.setCarec(false);
			personnel.setTelephone(telephone);
			personnel.setSituationMedaille(situationMedaill);
			personnel.setSituationEmploie(situationEmploie);
			System.out.println("############################################################################# 2 :" + personnel.toString());
			
			personnel = personnelRepository.save(personnel);
			System.out.println("############################################################################# 3 " + personnel.toString());
			
			contratPersonnel.setPersonnel(personnel);
			contratPersonnel.setCategorie(categorieRepository.findById(categorie).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + categorie)));
			contratPersonnel.setFonction(fonctionRepository.findById(fonction).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + fonction)));

			contratPersonnel.setDateDebut(Utils.stringToDate(dateDebut, "dd/MM/yyyy"));
				if (dateFin != null && !dateFin.trim().isEmpty()) {
					contratPersonnel.setDateFin(Utils.stringToDate(dateFin, "dd/MM/yyyy"));
				} else {
					contratPersonnel.setDateFin(null);
				}


			contratPersonnel.setNetAPayer(salaireNet);
			contratPersonnel.setIndemniteLogement(indemnitelogement);
			contratPersonnel.setTypeContrat(typeContratRepository.findById(typeContrat).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + typeContrat)));
				if(contratPersonnel.getTypeContrat().getId()==1L)
					contratPersonnel.setDateFin(null);
			contratPersonnel.setAncienneteInitial(ancienneteInitial);
			contratPersonnel.setStatut(true);
			contratPersonnel.setDepart(false);
			contratPersonnel.setSoldeCalcule(false);
			contratPersonnel.setIndemniteRepresent(indemniteRepresent);
			contratPersonnel.setIndemniteTransport(indemniteTransport);
			//contratPersonnel.setIndemniteResp(indemniteRespons);
			contratPersonnel.setSursalaire(sursalaire);
			contratPersonnel = contratPersonnelRepository.save(contratPersonnel);
			
			if(contratPersonnel.getTypeContrat().getId()==5L){
				 contratPersonnel.getPersonnel().setStage(false);
				 contratPersonnel.getPersonnel().setFonctionnaire(false);
				 contratPersonnel.getPersonnel().setConsultant(true);

			 }
			
			if(contratPersonnel.getTypeContrat().getId()==8L){
				 contratPersonnel.getPersonnel().setConsultant(false);
				contratPersonnel.getPersonnel().setFonctionnaire(false);
				contratPersonnel.getPersonnel().setStage(true);
			 }

				//if(contratPersonnel.getTypeContrat().getId()==6L){
				//	contratPersonnel.getPersonnel().setConsultant(false);
				//	contratPersonnel.getPersonnel().setFonctionnaire(true);
				//	contratPersonnel.getPersonnel().setStage(false);
				//}
			 personnelRepository.save(contratPersonnel.getPersonnel());
				PlanningConge pc = new PlanningConge();
				pc.setStatut(true);

				Date dateBase = personnel.getDateRetourcge() != null ?
						personnel.getDateRetourcge() : personnel.getDateArrivee();

				pc.setDateDepart(DateManager.addingMonth(dateBase, 12));

				String mois = Utils.dateToString(pc.getDateDepart(), "MM");
				String annee = Utils.dateToString(pc.getDateDepart(), "yyyy");

				List<PeriodePaie> list = periodePaieRepository.findByAnneeAnnee(annee);

				PeriodePaie per = list.stream()
						.filter(x -> x.getMois().getId().equals(Long.valueOf(mois)))
						.findFirst()
						.orElseThrow(() ->
								new IllegalStateException("Aucune période de paie trouvée pour " + mois + "/" + annee)
						);

				pc.setPeriodePaie(per);
				pc.setContratPersonnel(contratPersonnel);

				 planningCongeRepository.save(pc);
			//
//			PlanningConge planningConge = new PlanningConge();
//			planningConge.setStatut(true);
//			if(personnel.getDateRetourcge()!= null ){
//				planningConge.setDateDepart(DateManager.addingMonth(personnel.getDateRetourcge(), 12));
//			//	dateParts = dateRetourcg.split("/");
//			}else{
//				planningConge.setDateDepart(DateManager.addingMonth(personnel.getDateArrivee(), 12));
//				//dateParts = dateArrivee.split("/");
//			}
//			//planningConge.setDateDepart(dateDepart);
//			String sdate = null;
//			if(dateRetourcg!=null){
//
//			     sdate=Utils.dateToString(planningConge.getDateDepart(),"dd/MM/yyyy");
//			}
//			/*else{
//
//			    sdate=dateArrivee;
//			    }*/
//			System.out.println("********************jourRET))))))"+dateRetourcg);
//			System.out.println("********************jourARR))))))"+dateArrivee);
//			System.out.println("********************jourARR))))))"+sdate);
//			String jour = sdate.substring(0, 2);
//		     String mois= sdate.substring(3, 5);
//			String annee = sdate.substring(6, 10);
//			System.out.println("****************jour  MOIS annnee)))))))))))))))))))))"+jour+"MOIS"+mois+"annee"+annee);
//
//		   // Date dDate=Utils.stringToDate(sdate, "dd/MM/yyyy");
//
//			List<PeriodePaie> listper=periodePaieRepository.findByAnneeAnnee(annee);
//			System.out.println("****************************************************oooooooooooooo"+listper.size());
//			PeriodePaie perPaiev=new PeriodePaie();
//			for(PeriodePaie perPaie : listper){
//				if(perPaie.getMois().getId()==Long.valueOf(mois)){
//				 perPaiev=perPaie;
//					System.out.println("****************************************************ooooooooooooooMois"+perPaie.getMois().getId());
//				}
//			}
//
//
//			planningConge.setContratPersonnel(contratPersonnel);
//			planningConge.setPeriodePaie(perPaiev);
//			planningConge = planningCongeRepository.save(planningConge);

			// calcule le sursalaire
		//	 PeriodePaie	periodePaieActif=periodePaieRepository.findByClotureFalse();
		/*	 LivreDePaie livrePaiecalY = new LivreDePaie();
			 
			  livrePaiecalY=calculbullFirst(contratPersonnel, periodePaieActif);			 
			  contratPersonnel.setSursalaire(livrePaiecalY.getSursalaire());*/
			 
			 contratPersonnelRepository.save(contratPersonnel);
			
			contratPersonnelDTO.setRow(contratPersonnel);
			contratPersonnelDTO.setResult("succes");
			}else{
				contratPersonnelDTO.setResult("echec");
			}
		} catch (Exception ex){
			ex.printStackTrace();
			contratPersonnelDTO.setResult("echec");
		}
		return contratPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PersonnelDTO save(Long id, String nom, String prenom, Long nationalite, Long service, String matricule, String sexe, String dateNaissance,
                             String lieuNaissance, String email, String residence, int situationMatrimoniale, int nombreEnfant,
                             String dateArrivee, String numeroCNPS, String adresse, Boolean statut, String modePaiement, Long idbanque, String numeroCompte, String numeroGuichet, String rib, Boolean carec, String typemp, String telephone, int situationMedaill, int situationEmploie, String dateRetourcg) {
		// TODO Auto-generated method stub
		PersonnelDTO personnelDTO = new PersonnelDTO();
		Personnel personnel = new Personnel();
		try{
			if(id != null){
			 personnel = personnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			}
			
			personnel.setNom(nom);
			personnel.setPrenom(prenom);
			personnel.setMatricule(matricule);
			personnel.setSexe(sexe);
			personnel.setDateNaissance(Utils.stringToDate(dateNaissance, "dd/MM/yyyy"));
			personnel.setLieuNaissance(lieuNaissance);
			personnel.setEmail(email);
			personnel.setResidence(residence);
			personnel.setSituationMatrimoniale(situationMatrimoniale);
			personnel.setNombrEnfant(nombreEnfant);
			if(id == null){
			personnel.setDateArrivee(Utils.stringToDate(dateArrivee, "dd/MM/yyyy"));}
			personnel.setDateRetourcge(Utils.stringToDate(dateRetourcg, "dd/MM/yyyy"));
			personnel.setNumeroCnps(numeroCNPS);
			personnel.setService(serviceRepository.findById(service).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + service)));
			personnel.setNationnalite(nationnaliteRepository.findById(nationalite).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + nationalite)));
			personnel.setStatut(statut);
			personnel.setCarec(carec);
			personnel.setTypeSalarie(typemp);
			personnel.setTelephone(telephone);
			personnel.setModePaiement(modePaiement);
			personnel.setBanquek(banqueRepository.findById(idbanque).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idbanque)));
			personnel.setNumeroCompte(numeroCompte);
			personnel.setNumeroCompte(numeroCompte);
			personnel.setNumeroGuichet(numeroGuichet);
			personnel.setRib(rib);
			personnel.setSituationMedaille(situationMedaill);
			personnel.setSituationEmploie(situationEmploie);
			personnel = personnelRepository.save(personnel);
			personnelDTO.setRow(personnel);
			personnelDTO.setResult("succes");
			
			
			
			
		/*	PlanningConge planningConge = new PlanningConge();
			planningConge=planningCongeRepository.findByContratPersonnel(contratPersonnelRepository.findByPersonnelIdAndStatut(personnel.getId(), true));
			if(planningConge==null){
				PlanningConge planningConge1 = new PlanningConge();
				planningConge1.setStatut(true);
				String[] dateParts ;
				if(personnel.getDateRetourcge()!= null ){
					planningConge1.setDateDepart(DateManager.addingMonth(personnel.getDateRetourcge(), 12));
				//	dateParts = dateRetourcg.split("/");
				}else{
					planningConge1.setDateDepart(DateManager.addingMonth(personnel.getDateArrivee(), 12));
					//dateParts = dateArrivee.split("/");
				}
				planningConge1.setContratPersonnel(contratPersonnelRepository.findByPersonnelIdAndStatut(personnel.getId(), true));
				
				String sdate = null;
				if(dateRetourcg!=null){
					
				     sdate=Utils.dateToString(planningConge1.getDateDepart(),"dd/MM/yyyy");
				}
				else{
				  
				    sdate=dateArrivee;
				    }
				System.out.println("********************jourRET))))))"+dateRetourcg);
				System.out.println("********************jourARR))))))"+dateArrivee);
				System.out.println("********************jourARR))))))"+sdate);
				String jour = sdate.substring(0, 2); 
			     String mois= sdate.substring(3, 5); 
				String annee = sdate.substring(6, 10);
				System.out.println("****************jour  MOIS annnee)))))))))))))))))))))"+jour+"MOIS"+mois+"annee"+annee);
				
			   // Date dDate=Utils.stringToDate(sdate, "dd/MM/yyyy");

				java.util.List<PeriodePaie> listper=periodePaieRepository.findByAnneeAnnee(annee);	
				System.out.println("****************************************************oooooooooooooo"+listper.size());
				PeriodePaie perPaiev=new PeriodePaie();
				for(PeriodePaie perPaie : listper){
					if(perPaie.getMois().getId()==Long.valueOf(mois)){
					 perPaiev=perPaie;				
						System.out.println("****************************************************ooooooooooooooMois"+perPaie.getMois().getId());		 
					}
				}
				planningConge1.setPeriodePaie(perPaiev);
				planningConge1 = planningCongeRepository.save(planningConge1);
			
			}*/
			/*else{
			//planningConge.setStatut(true);
				String[] dateParts1 ;
			if(personnel.getDateRetourcge()!= null ){
				planningConge.setDateDepart(DateManager.addingMonth(personnel.getDateRetourcge(), 12));
				//dateParts1 = dateRetourcg.split("/");
			}else{
				planningConge.setDateDepart(DateManager.addingMonth(personnel.getDateArrivee(), 12));
				//dateParts1 = dateArrivee.split("/");
			}
			planningConge.setContratPersonnel(contratPersonnelRepository.findByPersonnelIdAndStatut(personnel.getId(), true));
			String sdate = null;
			if(dateRetourcg!=null){
			     sdate=Utils.dateToString(planningConge.getDateDepart(),"dd/MM/yyyy");
			}
			else{
			  
			    sdate=dateArrivee;
			    }
			System.out.println("********************jourRET))))))"+dateRetourcg);
			System.out.println("********************jourARR))))))"+dateArrivee);
			System.out.println("********************jourARR))))))"+sdate);
			String jour = sdate.substring(0, 2); 
		     String mois= sdate.substring(3, 5); 
			String annee = sdate.substring(6, 10);
			System.out.println("****************jour  MOIS annnee)))))))))))))))))))))"+jour+"MOIS"+mois+"annee"+annee);
			
		   // Date dDate=Utils.stringToDate(sdate, "dd/MM/yyyy");

			java.util.List<PeriodePaie> listper=periodePaieRepository.findByAnneeAnnee(annee);	
			System.out.println("****************************************************oooooooooooooo"+listper.size());
			PeriodePaie perPaiev=new PeriodePaie();
			for(PeriodePaie perPaie : listper){
				if(perPaie.getMois().getId()==Long.valueOf(mois)){
				 perPaiev=perPaie;				
					System.out.println("****************************************************ooooooooooooooMois"+perPaie.getMois().getId());		 
				}
			}
			planningConge.setPeriodePaie(perPaiev);
		//	planningConge.setPeriodePaie(periodePaieRepository.findOne(peroiod.getId()));
			planningConge = planningCongeRepository.save(planningConge);
			}*/
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			personnelDTO.setResult("echec");
		}
		return personnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PersonnelDTO save(Long id, int situationMatrimoniale, int nombreEnfant, Boolean statut) {
		// TODO Auto-generated method stub
		PersonnelDTO personnelDTO = new PersonnelDTO();
		try{
			Personnel personnel = personnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			
			personnel.setSituationMatrimoniale(situationMatrimoniale);
			personnel.setNombrEnfant(nombreEnfant);
			personnel.setStatut(statut);
			personnel = personnelRepository.save(personnel);
			if(statut==false)
			{
				PeriodePaie periodePaie = periodePaieRepository.recherchperiodeCloture();
			    BulletinPaie bull= bulletinPaieRepository.findByBulletinAndPersonnel(personnel.getId(),periodePaie.getId());
				if(bull!=null)
					bulletinPaieRepository.delete(bull);
			}
			personnelDTO.setRow(personnel);
			personnelDTO.setResult("succes");
		} catch (Exception ex){
			ex.printStackTrace();
			personnelDTO.setResult("echec");
		}
		return personnelDTO;
	}

	@Override
	public PersonnelDTO depart(Long id) {
		PersonnelDTO personnelDTO = new PersonnelDTO();
		Personnel personnel = personnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(personnel == null)
			return personnelDTO;
		//personnel.setSituationMatrimoniale(situationMatrimoniale);

		personnel.setRetraitEffect(true);
		personnel.setStatut(false);
		personnel = personnelRepository.save(personnel);
		ContratPersonnel contratPersonnel = contratPersonnelRepository.findTop1ByPersonnelIdAndStatutOrderByDateDebutDesc(personnel.getId(),true);
		contratPersonnel.setDepart(true);
		contratPersonnel.setSoldeCalcule(false);
		contratPersonnel.setStatut(false);
		contratPersonnelRepository.save(contratPersonnel);
		personnelDTO.setResult(true);
		personnelDTO.setStatus(true);
		return personnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Personnel personnel = personnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(personnel == null)
			return false;

		ContratPersonnel contratPersonnel = contratPersonnelRepository.findByPersonnelId(personnel.getId());
		contratPersonnelRepository.delete(contratPersonnel);
		personnelRepository.delete(personnel);
		return true;
	}

	@Override
	public Personnel findPersonnel(Long id) {
		// TODO Auto-generated method stub
		return personnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
	}

	@Override
	public Personnel findByEmail(String email) {
		// TODO Auto-generated method stub
		return personnelRepository.findByEmail(email);
	}

	@Override
	public Personnel findByMatricule(String matricule) {
		// TODO Auto-generated method stub
		return personnelRepository.findByMatricule(matricule);
	}

	@Override
	public Personnel findByNumeroCnps(String numeroCnps) {
		// TODO Auto-generated method stub
		return personnelRepository.findByNumeroCnps(numeroCnps);
	}

	@Override
	public List<Personnel> findPersonnels() {
		// TODO Auto-generated method stub
		return personnelRepository.findAll();
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) personnelRepository.countByRetraitEffectFalse();
	}

	@Override
	public PersonnelDTO loadPersonnel(Pageable pageable) {
		// TODO Auto-generated method stub
		PersonnelDTO personnelDTO = new PersonnelDTO();
		Page<Personnel> page = personnelRepository.chearchOrdreAsc(pageable);
		personnelDTO.setRows(page.getContent());
		personnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
		return personnelDTO;
	}

	@Override
	public PersonnelDTO findAllfilter(Map<String, String> filters, Pageable pageable) {
		PersonnelDTO personnelDTO = new PersonnelDTO();
		Specification<Personnel> specification = GenericSpecifications.fromMap(filters);
		Page<Personnel> page = personnelRepository.findAll(specification, pageable);
		personnelDTO.setResult(true);
		personnelDTO.setStatus(true);
		personnelDTO.setRows(page.getContent());
		personnelDTO.setTotal(page.getTotalElements());
		return personnelDTO;
	}

	@Override
	public PersonnelDTO loadPersonnel(Pageable pageable, String search, String search1, String search2) {
		// TODO Auto-generated method stub
		PersonnelDTO personnelDTO = new PersonnelDTO();
		Page<Personnel> page = personnelRepository.findByRetraitEffectFalseAndNomIgnoreCaseContainingOrPrenomIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(pageable, search,search,search);
		personnelDTO.setRows(page.getContent());
		personnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> PERSONNELS CHARGES AVEC SUCCES").toString());
		return personnelDTO;
	}

	@Override
	public PrintLsDTO RechercherListPersonnelParAnnee(Long id) {
		return null;
	}




	@Override
	public List<Personnel> RechercherListPersonnelParDirection(Service direction) {
		List<Personnel> listPersonnelDir = new ArrayList<Personnel>();
		List<Personnel> listPersonnelDept = new ArrayList<Personnel>();
		List<Personnel> listPersonnelSce = new ArrayList<Personnel>();
		listPersonnelDir = personnelRepository.findByServiceId(direction.getId());
		// liste departements
		List<Service> listDepartement = new ArrayList<Service>();
		try {
			listDepartement = serviceRepository.findByServiceParentId(direction.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (listDepartement.size() >= 1){
			for (Service departement : listDepartement) {
				List<Personnel> listperso = new ArrayList<Personnel>();
				listperso = personnelRepository.findByServiceId(departement.getId());
				if (listperso.size() >= 1)
					listPersonnelDept.addAll(listperso);

				List<Service> listService = new ArrayList<Service>();
				try {
					listService = serviceRepository.findByServiceParentId(departement.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (Service service : listService) {
					List<Personnel> listpersoServ = new ArrayList<Personnel>();
					listpersoServ = personnelRepository.findByServiceId(service.getId());
					if (listpersoServ.size() >= 1)
						listPersonnelSce.addAll(listpersoServ);
				}

			}
		}


		List<Personnel> listpersoRetour = new ArrayList<Personnel>();
		if(listPersonnelDir.size()>0)
		listpersoRetour.addAll(listPersonnelDir);
		System.out.println("***************************************** /direction"+listpersoRetour.size());
		if(listPersonnelDept.size()>0)
					listpersoRetour.addAll(listPersonnelDept);
		System.out.println("***************************************** /departement"+listPersonnelDept.size());
		if(listPersonnelSce.size()>0)
			listpersoRetour.addAll(listPersonnelSce);
		//return (List<Personnel>) personnelRepository.findByServiceLibelle(direction.getLibelle());
		return listpersoRetour;

	}


	@Override
	public List<Personnel> RechercherListPersonnelParAnnee( String sexe) {
		List<Personnel> listPersonnel = new ArrayList<Personnel>();

		System.out.println(" sexe : "+sexe);

		try{
			listPersonnel = personnelRepository.RechListPersonnelParAn( sexe);
			System.out.println("list Personnel imple  : "+listPersonnel.size());
			logger.info(listPersonnel.size() + " Personnel(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			logger.error("une erreur a ete detectee lors de la recherche des Personnel");
		}
		return listPersonnel;
	}
	//	@Override
//	public PrintLsDTO RechercherListPersonnelParAnnee(Long id) {
//		java.util.Date dateAnDeb = null;
//		java.util.Date dateAnFin = null;
//		PrintLsDTO printLsDTO=new PrintLsDTO();
//		Exercice myexo=exerciceRepository.findOne(id);
//
//		String annee1=myexo.getAnnee();
//		Integer anneeEnCours = 2017;
//		System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+anneeEnCours);
//		List<PrintLs> listPrintDTO = new ArrayList<PrintLs>();
//
//		for (int i = 0; i < 5; i++) {
//			String annee = String.valueOf(anneeEnCours);
//		try {
//			String form1="01/01/"+annee;String form2="31/12/"+annee;
//			dateAnDeb =Utils.stringToDate(form1, "dd/MM/yyyy");
//			dateAnFin = Utils.stringToDate(form2, "dd/MM/yyyy");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//			Integer[] tab = new Integer[2];
//			java.sql.Date dateDebE = new java.sql.Date(dateAnDeb.getTime());
//			java.sql.Date dateFinE = new java.sql.Date(dateAnFin.getTime());
//            tab=EffectifAnnuel( dateDebE,  dateFinE);
//			System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmm"+tab[0]);
//			System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmm"+tab[1]);
//			PrintLs  printlso = new PrintLs();
//			printlso.setI1(tab[0].intValue());
//			printlso.setS1(Integer.valueOf(annee).toString());
//			printlso.setTitle1("Homme");
//			printlso.setI2(tab[1].intValue());
//			printlso.setS2(Integer.valueOf(annee).toString());
//			printlso.setTitle2("Femme");
//			anneeEnCours = anneeEnCours - 1;
//			listPrintDTO.add(printlso);
//
//		}
//		printLsDTO.setRows(listPrintDTO);
//		printLsDTO.setResult("success");
//		return printLsDTO;
//	}
//	@Override
//	public Integer [] EffectifAnnuel(java.sql.Date dateInDeb, java.sql.Date dateEnDeb) {		// TODO Auto-generated method stub
//		Integer[] tab = new Integer[2];
//		Integer nbreHom = 0;Integer nbreFam= 0;
//		List<Personnel> bulletinPaieList = personnelRepository.RechListPersonnelParAnnee(dateInDeb,dateEnDeb);
//		for(Personnel personnel : bulletinPaieList){
//			if(personnel.getSexe()=="Masculin")
//				nbreHom = nbreHom  + 1;
//			if(personnel.getSexe()=="Feminin")
//				nbreFam = nbreFam  + 1;
//		}
//			tab[0]=nbreHom;
//		    tab[1]=nbreFam;
//		return tab;
//
//	}
	@Override
	public PersonnelDTO findPersonneldto(Long id) {
		// TODO Auto-generated method stub
		PersonnelDTO personnelDTO = new PersonnelDTO();
		try{
			
			Personnel personnel = personnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		    personnelDTO.setRow(personnel);
		    personnelDTO.setResult("succes");
		} catch (Exception ex){
			ex.printStackTrace();
			personnelDTO.setResult("echec");
		}
		return personnelDTO;
	}

	@Override
	public PersonnelDTO findByMatricules(String matricule) {
		// TODO Auto-generated method stub
		PersonnelDTO personnelDTO = new PersonnelDTO();
	try {
	
		Personnel personnel = personnelRepository.findByMatricule(matricule);
	    personnelDTO.setRow(personnel);
	    personnelDTO.setResult("succes");
	} catch (Exception ex){
		ex.printStackTrace();
		personnelDTO.setResult("echec");
	}
	return personnelDTO;
	}

	@Override
	public PersonnelDTO findByNumeroCnpss(String matricule) {
		// TODO Auto-generated method stub
		PersonnelDTO personnelDTO = new PersonnelDTO();
		try {
		
			Personnel personnel = personnelRepository.findByNumeroCnps(matricule);
		    personnelDTO.setRow(personnel);
		    personnelDTO.setResult("succes");
		} catch (Exception ex){
			ex.printStackTrace();
			personnelDTO.setResult("echec");
		}
		return personnelDTO;
	}


	@Override
	public List<Personnel> RechercherListPersonnelParAnnee(Date dateDeb, Date dateFin, String sexe) {
		List<Personnel> listPersonnel = new ArrayList<Personnel>();
		java.sql.Date dateDebE = new java.sql.Date(dateDeb.getTime());
		java.sql.Date dateFinE = new java.sql.Date(dateFin.getTime());

		System.out.println( "date deb : "+dateDebE+"  date fin : "+dateFinE+" sexe : "+sexe);

		try{
			listPersonnel = personnelRepository.RechListPersonnelParAnnee( dateDebE, dateFinE, sexe);
			System.out.println("list Personnel imple  : "+listPersonnel.size());
			logger.info(listPersonnel.size() + " Personnel(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			logger.error("une erreur a ete detectee lors de la recherche des Personnel");
		}
		return listPersonnel;
	}
	
public LivreDePaie calculbullFirst(ContratPersonnel ctratpersonnellz,PeriodePaie periodePaieActif){
		
	Double[]  ancienete=calculAnciennete(ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getPersonnel().getDateArrivee());
	double newancienete;
	if(ctratpersonnellz.getAncienneteInitial()!=0) {
		 newancienete=ancienete[1] +ctratpersonnellz.getAncienneteInitial();
	}else{
		newancienete=ancienete[1];
	}
	int anc=(int)newancienete;
	 int op=0;
	 if(anc < 2) op=0;		    		 
	 if(anc>=2 && anc<=25) op=anc;
	 if(anc>25) op=25;
	 List<PrimePersonnel> listIndemniteBrut=new ArrayList<PrimePersonnel>();
	 List<PrimePersonnel> listIndemniteNonBrut=new ArrayList<PrimePersonnel>();
	 List<PrimePersonnel> listRetenueMutuelle=new ArrayList<PrimePersonnel>();
	 List<PrimePersonnel> listRetenueSociale=new ArrayList<PrimePersonnel>();
	List<PrimePersonnel> listGainsNet=new ArrayList<PrimePersonnel>();
	 List<PrimePersonnel> listIndemnite  =new ArrayList<PrimePersonnel>();
	 listIndemnite =  primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId());
		if(listIndemnite.size()>0){
			for(PrimePersonnel kprme:listIndemnite){
				 if(kprme.getPrime().getEtatImposition()==1)
				 {
					 listIndemniteBrut.add(kprme);
				 }
				 if(kprme.getPrime().getEtatImposition()==2)
				 {
					 listIndemniteNonBrut.add(kprme);
				 }
				 if(kprme.getPrime().getEtatImposition()==3)
				 {
					 if(kprme.getPrime().getMtExedent()!=null)
					 {listIndemniteNonBrut.add(kprme);
					 listIndemniteNonBrut.add(kprme);}
				 }
				if(kprme.getPrime().getEtatImposition()==4)
				{
					listRetenueMutuelle.add(kprme);
				}
				if(kprme.getPrime().getEtatImposition()==5)
				{
					listGainsNet.add(kprme);
				}
				if(kprme.getPrime().getEtatImposition()==6)
				{
					listRetenueSociale.add(kprme);
				}
			}
			
		}
		LivreDePaie	 livrePaiecalpm = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),5000d, ctratpersonnellz.getIndemniteLogement(),0d, 0d,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
		try { 
		int i=0;
			while (livrePaiecalpm.getNetPayer()!=ctratpersonnellz.getNetAPayer()|| i==3) {		 				
				 Double nouvSursal = 0d;Double nouvDiff= 0d;Double nouvMontantBrutImp= 0d;
				nouvMontantBrutImp=Math.rint(ctratpersonnellz.getNetAPayer()*livrePaiecalpm.getBrutImposable()/livrePaiecalpm.getNetPayer());
				nouvDiff=nouvMontantBrutImp-livrePaiecalpm.getBrutImposable();						
				nouvSursal=nouvDiff+livrePaiecalpm.getSursalaire();						
				livrePaiecalpm = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),nouvSursal, ctratpersonnellz.getIndemniteLogement(), 0d, 0d,ctratpersonnellz,null,periodePaieActif,listIndemniteBrut,listIndemniteNonBrut,listRetenueMutuelle,listGainsNet,listRetenueSociale);
		//	 logger.info("*********************SECOND BULLETIN********************############## SECOND BULLETIN #############-----------"+livrePaiecal.toString());	
		 i++;
			}
		
		 
		} catch (Exception e) {
			System.out.println("FINISH"+ e.getMessage());
		} 
		 return livrePaiecalpm;
	}

public  Double[] calculAnciennete(Double salaireCategoriel, Date dateEntree){
	
	Double[] tab = new Double[5];
	
	Double anciennete = (double) 0;
	
	
	double age = DifferenceDate.valAge(new Date(), dateEntree);
	
	int partieEntiere = (int) age; 
	int partieApresVirg = (int)((age - partieEntiere) * 12); 
	
	
	if(age>=2)
		anciennete = (double) (salaireCategoriel*partieEntiere/100);
	
	tab[0] = anciennete;
	
	
	tab[1] = (double) partieEntiere;
	tab[2] = (double) partieApresVirg;
	

	
	return tab;
}


}
