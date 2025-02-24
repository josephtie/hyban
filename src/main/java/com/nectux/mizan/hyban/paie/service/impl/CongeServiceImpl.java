package com.nectux.mizan.hyban.paie.service.impl;

import com.nectux.mizan.hyban.paie.dto.CongeDTO;
import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.Conge;
import com.nectux.mizan.hyban.paie.entity.Gratification;
import com.nectux.mizan.hyban.paie.repository.CongeRepository;
import com.nectux.mizan.hyban.paie.repository.GratificationRepository;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.paie.service.CongeService;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.Mois;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.repository.PlanningCongeRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.DifferenceDate;
import com.nectux.mizan.hyban.utils.ProvisionConge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("congeService")
public class CongeServiceImpl implements CongeService {
	
	private static final Logger logger = LogManager.getLogger(CongeServiceImpl.class);
	
	@Autowired private CongeRepository congeRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private BulletinPaieService bulletinPaieService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PlanningCongeRepository planningCongeRepository;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private GratificationRepository gratificationRepository;
	
	List<Conge> congeList;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Conge save(Conge conge) {
		// TODO Auto-generated method stub
		conge = congeRepository.save(conge);
	
		return conge;
	}

	@Override
	public CongeDTO genererBulletinConge(Long id) throws Exception {
		// TODO Auto-generated method stub
		CongeDTO congeDTO = new CongeDTO();
		
		Conge conge = new Conge();
		PlanningConge planningConge;
		Double salaireMoyenMensuelle = 0.0;
		Double	salaireMoyenMensuelleNew = 0.0;
		Double indemniteRepresentationMoyenMensuelle = 0.0;
		Date dateRetourDernierConge = new Date();
		Date dateDepartConge = new Date();
		List<Conge> oldCongeList = new ArrayList<Conge>();
	    PeriodePaie maperiode=periodePaieService.findPeriodeactive();
		ContratPersonnel contratPersonnel = contratPersonnelRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));

		
		oldCongeList = congeRepository.findTop1ByContratPersonnel(contratPersonnel);
		if(oldCongeList != null && oldCongeList.isEmpty()) // Si le resultat est null :: 
			// Alors on cherche la date d'arrive du personnel
			dateRetourDernierConge = contratPersonnel.getPersonnel().getDateRetourcge();
		else
			dateRetourDernierConge = contratPersonnel.getPersonnel().getDateRetourcge();
		// On cherche la date de depart en conge du personnel dans le calendrier de planification des conges
		dateDepartConge = new Date();
		planningConge = planningCongeRepository.findByContratPersonnelAndStatut(contratPersonnel,true);
		if(planningConge == null) // Si le resultat est null, alors on choisit la date du jour
			dateDepartConge = new Date();
		else
			dateDepartConge = planningConge.getDateDepart();
		// calcul du salaire moyen mensuelle des 12 dernier mois
		salaireMoyenMensuelle = bulletinPaieService.salaireMoyenMensuel(contratPersonnel);
		// calcul de l'indemnite de representation moyen mensuelle
		indemniteRepresentationMoyenMensuelle = bulletinPaieService.indemniteMoyenMensuel(contratPersonnel);
		
		ProvisionConge provisionConge = new ProvisionConge(contratPersonnel.getPersonnel().getNomComplet(), 
															contratPersonnel.getPersonnel().getNombrePart(), dateRetourDernierConge, dateDepartConge, 
															salaireMoyenMensuelle, indemniteRepresentationMoyenMensuelle,contratPersonnel);
		
		BulletinPaie bulls=new BulletinPaie();
		bulls=bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(maperiode, contratPersonnel.getPersonnel());
		conge = congeRepository.save(new Conge(dateDepartConge, DateManager.addingDate(dateDepartConge,bulls.getTempsOfpresence()),(double)provisionConge.getC(),
				provisionConge.getD(), salaireMoyenMensuelle, indemniteRepresentationMoyenMensuelle, 
				bulls.getTempsOfpresence(), provisionConge.getH(), provisionConge.getI(), 
				provisionConge.getJ(), provisionConge.getK(), provisionConge.getL(), 
				provisionConge.getM(), provisionConge.getN(), provisionConge.getO(), 
				provisionConge.getP(), provisionConge.getQ(), provisionConge.getR(),
				provisionConge.getITS(), provisionConge.getCN(), provisionConge.getIGR(),provisionConge.getCNPS(),
				provisionConge.getTotalRetenueFiscale(), provisionConge.getAllocationCongeNet(),
				contratPersonnel, periodePaieRepository.findByClotureFalse()));
		
		conge.setTempsOfpresence(bulls.getTempsOfpresence());
		conge.setMoisOfpresence(bulls.getMoisOfpresence());
		
		 congeRepository.save(conge);
		 
		 bulls.setCongeAc(true);
		 bulletinPaieService.save(bulls);
		//maj personnel nbre de jours du et montant du
		
		//int nbrejourdureel=countnbreJrdu(dateRetourDernierConge,maperiode.getDatefin(),contratPersonnel);
		
		Personnel personnel=new  Personnel();
		personnel=contratPersonnel.getPersonnel();
		///personnel.setDateRetourcge(DateManager.addingDate(dateDepartConge,bulls.getTempsOfpresence()+1));
		personnel.setNombreJourdu(bulls.getTempsOfpresence()+personnel.getNombreJourdu());
		personnel.setMtcongedu(conge.getAllocationCongeNet()+personnel.getMtcongedu());
		personnelRepository.save(personnel);
		
		PlanningConge pc = planningCongeRepository.findByContratPersonnelAndStatut(contratPersonnel,true);
		pc.setStatut(false);
		pc = planningCongeRepository.save(pc);
		
		
		PlanningConge plConge = new PlanningConge();
		plConge.setStatut(true);
		String sdate = null;
		plConge.setDateDepart(DateManager.addingMonth((DateManager.addingDate(dateDepartConge,bulls.getTempsOfpresence()+1)), 12));
		
			System.out.println("********************jourARR))))))"+sdate);
			sdate=DateManager.dateToString(plConge.getDateDepart(), "dd/MM/yyyy");
			String jour = sdate.substring(0, 2); 
		     String mois= sdate.substring(3, 5); 
			String annee = sdate.substring(6, 10);
			System.out.println("****************jour  MOIS annnee)))))))))))))))))))))"+jour+"MOIS"+mois+"annee"+annee);
			
		   // Date dDate=Utils.stringToDate(sdate, "dd/MM/yyyy");

			List<PeriodePaie> listper=periodePaieRepository.findByAnneeAnnee(annee);
			System.out.println("****************************************************oooooooooooooo"+listper.size());
			PeriodePaie perPaiev=new PeriodePaie();
			for(PeriodePaie perPaie : listper){
				if(perPaie.getMois().getId()==Long.valueOf(mois)){
				 perPaiev=perPaie;				
					System.out.println("****************************************************ooooooooooooooMois"+perPaie.getMois().getId());		 
				}
			}
			plConge.setPeriodePaie(perPaiev);
		//	plConge.setDateDepart(DateManager.addingMonth(conge.getDateRetour(), 12));
		    plConge.setContratPersonnel(contratPersonnel);
		
		plConge = planningCongeRepository.save(plConge);
		
		congeDTO.setRow(conge);
		return congeDTO;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Conge exercice = congeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));

		if(exercice == null)
			return false;
		congeRepository.delete(exercice);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) congeRepository.count();
	}

	@Override
	public int countnbreJrdu(Date dateRetourDernierConge,Date dateDepartConge,ContratPersonnel Contratp) {
		// TODO Auto-generated method stub
		
		int tps=ProvisionConge.calculerTempsPresence(dateRetourDernierConge,dateDepartConge);
		int rf=(int) (tps*2.2*1.25);
		 Double[]ancienete= calculAnciennete(Contratp.getCategorie().getSalaireDeBase(),Contratp.getPersonnel().getDateArrivee());
		 	double newancienete;
	    	if(Contratp.getAncienneteInitial()!=0) {
	    		 newancienete=ancienete[1] +Contratp.getAncienneteInitial();
	    	}else{
	    		newancienete=ancienete[1];
	    	}
	    	double anc=(int)newancienete ;
	    	
	     int jourSuppAnc=0; int jourSuppDam = 0;int jourSuppMed = 0;
	     
		 if(anc>5 && anc<=10)  jourSuppAnc=1;
		 if(anc>10 && anc<=15) jourSuppAnc=2;
		 if(anc>15 && anc<=20) jourSuppAnc=3;
		 if(anc>20 && anc<=25) jourSuppAnc=5;
		 if(anc>25 && anc<=30) jourSuppAnc=7;
		 if(anc>30) jourSuppAnc=8;
		 
		 Double age= DifferenceDate.valAge(new Date(), Contratp.getPersonnel().getDateNaissance());
		 if(Contratp.getPersonnel().getSexe().equals("Feminin") && age<=21 && Contratp.getPersonnel().getNombrEnfant()>0){
			 jourSuppDam=2*Contratp.getPersonnel().getNombrEnfant();
		 }
		 if(Contratp.getPersonnel().getSexe().equals("Feminin") && age>21 && Contratp.getPersonnel().getNombrEnfant()>0){
			 
			 if(Contratp.getPersonnel().getNombrEnfant()>=4)jourSuppDam=2*1;
			 if(Contratp.getPersonnel().getNombrEnfant()>=5)jourSuppDam=2*2;
			 if(Contratp.getPersonnel().getNombrEnfant()>=6)jourSuppDam=2*3;
			 if(Contratp.getPersonnel().getNombrEnfant()>=7)jourSuppDam=2*4;
			 if(Contratp.getPersonnel().getNombrEnfant()>=8)jourSuppDam=2*5;
			 if(Contratp.getPersonnel().getNombrEnfant()>=9)jourSuppDam=2*6;
		 }
		 
		 if(Contratp.getPersonnel().getSituationMedaille()==1 ){
			 jourSuppMed=1;
		 } 
		 int rfp=(int) (jourSuppAnc+jourSuppDam+jourSuppMed);
		return (int) rfp+rf;
	}

	
	
	
	@Override
	@SuppressWarnings("unused")
	public CongeDTO loadCongeProvisional(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		CongeDTO congeDTO = new CongeDTO();
		
		Conge conge;
		PlanningConge planningConge;
		Double salaireMoyenMensuelle = 0.0;
		Double salaireMoyenMensuelleNew= 0.0;
		Double gratificationAdd= 0.0;
		Double indemniteRepresentationMoyenMensuelle = 0.0;
		Date dateRetourDernierConge = new Date();
		Date dateDepartConge = new Date();
		List<Conge> oldCongeList = new ArrayList<Conge>();
		List<BulletinPaie> bulletinPaieList = new ArrayList<BulletinPaie>();
	   PeriodePaie	maperiode=periodePaieService.findPeriodeactive();
		congeList = new ArrayList<Conge>();
		
		// On recupere la liste du personnel
		List<ContratPersonnel> contratPersonnelList = contratPersonnelRepository.findByStatutTrue();
		for(ContratPersonnel contratPersonnel : contratPersonnelList){
			// On verifie si le conge est consome(contratPersonnel,DateManager.addingDate(new Date(),1) );
			planningConge = planningCongeRepository.findByContratPersonnelAndDateDepartBetween(contratPersonnel,maperiode.getDatedeb(), maperiode.getDatefin());
			//planningConge = planningCongeRepository.findByPeriodePaie(maperiode);
			if(planningConge != null && planningConge.getStatut()){
				// On recherche le dernier conge du personnel dans la table des conges
				conge = new Conge();
				oldCongeList = congeRepository.findTop1ByContratPersonnel(contratPersonnel);
				if(oldCongeList == null && oldCongeList.isEmpty()) // Si le resultat est null ::
					// Alors on cherche la date d'arrive du personnel$^= 
					dateRetourDernierConge = contratPersonnel.getPersonnel().getDateArrivee();
				else
					dateRetourDernierConge = oldCongeList.get(0).getDateRetour();
				// On cherche la date de depart en conge du personnel dans le calendrier de planification des conges
				dateDepartConge = new Date();
				if(planningConge == null) // Si le resultat est null, alors on choisit la date du jour
					dateDepartConge = new Date();
				else
					dateDepartConge = planningConge.getDateDepart();
				// calcul du salaire moyen mensuelle des 12 dernier mois
				salaireMoyenMensuelle = bulletinPaieService.salaireMoyenMensuel(contratPersonnel);
				// ajout d'eventuel gratification au cours de ladite periode
				List<Gratification>  MylistGratif=gratificationRepository.findByContratPersonnelAndPeriodePaieDatedebBetween(contratPersonnel,dateRetourDernierConge,dateDepartConge);
				if(MylistGratif.size()>0){
				for(Gratification gartifPaie : MylistGratif){
					gratificationAdd = gartifPaie.getNetPayer()  + gratificationAdd;
				}
				Gratification gartifPaieh=	MylistGratif.get(MylistGratif.size() - 1);
				}
				salaireMoyenMensuelleNew=salaireMoyenMensuelle+gratificationAdd;
				// calcul de l'indemnite de representation moyen mensuelle
				indemniteRepresentationMoyenMensuelle = bulletinPaieService.indemniteMoyenMensuel(contratPersonnel);
				
				ProvisionConge provisionConge = new ProvisionConge(contratPersonnel.getPersonnel().getNomComplet(), 
																	contratPersonnel.getPersonnel().getNombrePart(), dateRetourDernierConge, dateDepartConge, 
																	salaireMoyenMensuelleNew, indemniteRepresentationMoyenMensuelle,contratPersonnel);
				
				congeList.add(new Conge(dateDepartConge, DateManager.addingDate(dateDepartConge, provisionConge.getG()),(double)provisionConge.getC(), 
																				provisionConge.getD(), salaireMoyenMensuelleNew, indemniteRepresentationMoyenMensuelle, 
																				provisionConge.getG(), provisionConge.getH(), provisionConge.getI(), 
																				provisionConge.getJ(), provisionConge.getK(), provisionConge.getL(), 
																				provisionConge.getM(), provisionConge.getN(), provisionConge.getO(), 
																				provisionConge.getP(), provisionConge.getQ(), provisionConge.getR(), 
																				provisionConge.getITS(), provisionConge.getCN(), provisionConge.getIGR(), provisionConge.getCNPS(), 
																				provisionConge.getTotalRetenueFiscale(),  provisionConge.getAllocationCongeNet(),
																				contratPersonnel, periodePaieRepository.findByClotureFalse()));
			}
		}
		congeDTO.setRows(congeList);
		congeDTO.setTotal((long) congeList.size());
		return congeDTO;
	}

	@Override
	public CongeDTO loadCongeProvisional(Pageable pageable, String search) {
		return null;
	}

	/*@Override
	public List<Conge> loadLivrePaieConge() {
		// TODO Auto-generated method stub
		CongeDTO congeDTO = new CongeDTO();
		List<Conge> page = congeRepository.findAll();
		congeDTO.setRows(page.getContent());
		congeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONGES CHARGES AVEC SUCCES").toString());
		return page;
	}*/

	@Override
	public Conge findconge(Long id) {
		// TODO Auto-generated method stub
		return congeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));
	}

	@Override
	public List<Conge> loadLivrePaieConge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Conge> loadLivrePaieCongePeriode(Long id) {
		// TODO Auto-generated method stub
		return congeRepository.findByPeriodePaieId(id);
	}

	@Override
	public List<Conge> rechercherByAgenceMoisAnnee( Mois mois,Exercice annee) {
		List<Conge> listConge = new ArrayList<Conge>();
		try{
			listConge = congeRepository.rechercherParAgenceAnneeMois( mois.getId(), annee.getId());
			//log.info(listConge.size() + " conge(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			//log.error("une erreur a ete detectee lors de la recherche des conges");
		}
		return listConge;
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

	
	/*@Override
	public List<Conge> loadLivrePaieCongePeriode(Long id) {
		// TODO Auto-generated method stub
		List<Conge> page = congeRepository.findByPeriodePaieId(id);
		congeDTO.setRows(page.getContent());
		congeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONGES CHARGES AVEC SUCCES").toString());
		return page;
	}*/

	
}



/*package net.iconseils.rhpaiecgeci.paie.service.impl;

import CongeRepository;
import GratificationRepository;
import BulletinPaieService;
import CongeService;
import PlanningConge;
import PeriodePaieRepository;
import PlanningCongeRepository;
import ContratPersonnel;
import ContratPersonnelRepository;
import DateManager;
import ProvisionConge;
import CongeDTO;
import BulletinPaie;
import Conge;
import Gratification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("congeService")
public class CongeServiceImpl implements CongeService {
	
	private static final Logger logger = Logger.getLogger(CongeServiceImpl.class);
	
	@Autowired private CongeRepository congeRepository;
	@Autowired private BulletinPaieService bulletinPaieService;
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PlanningCongeRepository planningCongeRepository;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private GratificationRepository gratificationRepository;
	List<Conge> congeList;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Conge save(Conge conge) {
		// TODO Auto-generated method stub
		conge = congeRepository.save(conge);
	
		return conge;
	}

	@Override
	public CongeDTO genererBulletinConge(Long id) throws Exception {
		// TODO Auto-generated method stub
		CongeDTO congeDTO = new CongeDTO();
		
		Conge conge = new Conge();
		PlanningConge planningConge;
		Double salaireMoyenMensuelle = 0.0;
		Double indemniteRepresentationMoyenMensuelle = 0.0;
		Date dateRetourDernierConge = new Date();
		Date dateDepartConge = new Date();
		List<Conge> oldCongeList = new ArrayList<Conge>();
		
		ContratPersonnel contratPersonnel = contratPersonnelRepository.findOne(id);
		
		oldCongeList = congeRepository.findTop1ByContratPersonnel(contratPersonnel);
		if(oldCongeList != null && oldCongeList.isEmpty()) // Si le resultat est null :: 
			// Alors on cherche la date d'arrive du personnel
			dateRetourDernierConge = contratPersonnel.getPersonnel().getDateArrivee();
		else
			dateRetourDernierConge = oldCongeList.get(0).getDateRetour();
		// On cherche la date de depart en conge du personnel dans le calendrier de planification des conges
		dateDepartConge = new Date();
		planningConge = planningCongeRepository.findByContratPersonnel(contratPersonnel);
		if(planningConge == null) // Si le resultat est null, alors on choisit la date du jour
			dateDepartConge = new Date();
		else
			dateDepartConge = planningConge.getDateDepart();
		// calcul du salaire moyen mensuelle des 12 dernier mois
		salaireMoyenMensuelle = bulletinPaieService.salaireMoyenMensuel(contratPersonnel);
		// calcul de l'indemnite de representation moyen mensuelle
		indemniteRepresentationMoyenMensuelle = bulletinPaieService.indemniteMoyenMensuel(contratPersonnel);
		
		ProvisionConge provisionConge = new ProvisionConge(contratPersonnel.getPersonnel().getNomComplet(), 
															contratPersonnel.getPersonnel().getNombrePart(), dateRetourDernierConge, dateDepartConge, 
															salaireMoyenMensuelle, indemniteRepresentationMoyenMensuelle,contratPersonnel);
		
		conge = congeRepository.save(new Conge(dateDepartConge, DateManager.addingDate(dateDepartConge, provisionConge.getG()),(double)provisionConge.getC(), 
				provisionConge.getD(), salaireMoyenMensuelle, indemniteRepresentationMoyenMensuelle, 
				provisionConge.getG(), provisionConge.getH(), provisionConge.getI(), 
				provisionConge.getJ(), provisionConge.getK(), provisionConge.getL(), 
				provisionConge.getM(), provisionConge.getN(), provisionConge.getO(), 
				provisionConge.getP(), provisionConge.getQ(), provisionConge.getR(),
				provisionConge.getITS(), provisionConge.getCN(), provisionConge.getIGR(),provisionConge.getCNPS(),
				provisionConge.getTotalRetenueFiscale(), provisionConge.getAllocationCongeNet(),
				contratPersonnel, periodePaieRepository.findByClotureFalse()));
		
		PlanningConge pc = planningCongeRepository.findByContratPersonnel(contratPersonnel);
		pc.setStatut(false);
		pc = planningCongeRepository.save(pc);
		
		congeDTO.setRow(conge);
		return congeDTO;
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Conge exercice = congeRepository.findOne(id);
		if(exercice == null)
			return false;
		congeRepository.delete(id);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) congeRepository.count();
	}

	@Override
	@SuppressWarnings("unused")
	public CongeDTO loadCongeProvisional(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		CongeDTO congeDTO = new CongeDTO();
		
		Conge conge;
		PlanningConge planningConge;
		Double salaireMoyenMensuelle = 0.0;
		Double salaireMoyenMensuelleNew= 0.0;
		Double gratificationAdd= 0.0;
		Double indemniteRepresentationMoyenMensuelle = 0.0;
		Date dateRetourDernierConge = new Date();
		Date dateDepartConge = new Date();
		List<Conge> oldCongeList = new ArrayList<Conge>();
		List<BulletinPaie> bulletinPaieList = new ArrayList<BulletinPaie>();
		
		congeList = new ArrayList<Conge>();
		
		// On recupere la liste du personnel
		List<ContratPersonnel> contratPersonnelList = contratPersonnelRepository.findByStatutTrue();
		for(ContratPersonnel contratPersonnel : contratPersonnelList){
			// On verifie si le conge est consome
			planningConge = planningCongeRepository.findByContratPersonnelAndDateDepartBefore(contratPersonnel,DateManager.addingDate(new Date(),1) );
			if(planningConge != null && planningConge.getStatut()){
				// On recherche le dernier conge du personnel dans la table des conges
				conge = new Conge();
				oldCongeList = congeRepository.findTop1ByContratPersonnel(contratPersonnel);
				if(oldCongeList != null && oldCongeList.isEmpty()) // Si le resultat est null :: 
					// Alors on cherche la date d'arrive du personnel$^= 
					dateRetourDernierConge = contratPersonnel.getPersonnel().getDateArrivee();
				else
					dateRetourDernierConge = oldCongeList.get(0).getDateRetour();
				// On cherche la date de depart en conge du personnel dans le calendrier de planification des conges
				dateDepartConge = new Date();
				if(planningConge == null) // Si le resultat est null, alors on choisit la date du jour
					dateDepartConge = new Date();
				else
					dateDepartConge = planningConge.getDateDepart();
				// calcul du salaire moyen mensuelle des 12 dernier mois
				salaireMoyenMensuelle = bulletinPaieService.salaireMoyenMensuel(contratPersonnel);
				// ajout d'eventuel gratification au cours de ladite periode
				List<Gratification>  MylistGratif=gratificationRepository.findByContratPersonnelAndPeriodePaieDatedebBetween(contratPersonnel,dateRetourDernierConge,dateDepartConge);
				if(MylistGratif.size()>0){
				for(Gratification gartifPaie : MylistGratif){
					gratificationAdd = gartifPaie.getNetPayer()  + gratificationAdd;
				}
				Gratification gartifPaieh=	MylistGratif.get(MylistGratif.size() - 1);
				}
				salaireMoyenMensuelleNew=salaireMoyenMensuelle+gratificationAdd;
				// calcul de l'indemnite de representation moyen mensuelle
				indemniteRepresentationMoyenMensuelle = bulletinPaieService.indemniteMoyenMensuel(contratPersonnel);
				
				ProvisionConge provisionConge = new ProvisionConge(contratPersonnel.getPersonnel().getNomComplet(), 
																	contratPersonnel.getPersonnel().getNombrePart(), dateRetourDernierConge, dateDepartConge, 
																	salaireMoyenMensuelleNew, indemniteRepresentationMoyenMensuelle,contratPersonnel);
				
				congeList.add(new Conge(dateDepartConge, DateManager.addingDate(dateDepartConge, provisionConge.getG()),(double)provisionConge.getC(), 
																				provisionConge.getD(), salaireMoyenMensuelleNew, indemniteRepresentationMoyenMensuelle, 
																				provisionConge.getG(), provisionConge.getH(), provisionConge.getI(), 
																				provisionConge.getJ(), provisionConge.getK(), provisionConge.getL(), 
																				provisionConge.getM(), provisionConge.getN(), provisionConge.getO(), 
																				provisionConge.getP(), provisionConge.getQ(), provisionConge.getR(), 
																				provisionConge.getITS(), provisionConge.getCN(), provisionConge.getIGR(), provisionConge.getCNPS(), 
																				provisionConge.getTotalRetenueFiscale(),  provisionConge.getAllocationCongeNet(),
																				contratPersonnel, periodePaieRepository.findByClotureFalse()));
			}
		}
		congeDTO.setRows(congeList);
		congeDTO.setTotal((long) congeList.size());
		return congeDTO;
	}

	@Override
	public CongeDTO loadCongeProvisional(Pageable pageable, String search) {
		return null;
	}

	@Override
	public List<Conge> loadLivrePaieConge() {
		// TODO Auto-generated method stub
		//CongeDTO congeDTO = new CongeDTO();
		List<Conge> page = congeRepository.findAll();
		//congeDTO.setRows(page.getContent());
		//congeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONGES CHARGES AVEC SUCCES").toString());
		return page;
	}

	@Override
	public Conge findconge(Long id) {
		// TODO Auto-generated method stub
		return congeRepository.findOne(id);
	}

	@Override
	public List<Conge> loadLivrePaieCongePeriode(Long id) {
		// TODO Auto-generated method stub
		List<Conge> page = congeRepository.findByPeriodePaieId(id);
		//congeDTO.setRows(page.getContent());
		//congeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> CONGES CHARGES AVEC SUCCES").toString());
		return page;
	}

	
}
*/