package com.nectux.mizan.hyban.paie.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.paie.entity.Gratification;
import com.nectux.mizan.hyban.paie.dto.GratificationDTO;
import com.nectux.mizan.hyban.paie.repository.BulletinPaieRepository;
import com.nectux.mizan.hyban.paie.repository.GratificationRepository;
import com.nectux.mizan.hyban.paie.service.GratificationService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.repository.SocieteRepository;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.utils.LivreDePaieGratification2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("gratificationService")
public class GratificationServiceImpl implements GratificationService {
	
	private static final Logger logger = LogManager.getLogger(GratificationServiceImpl.class);
	
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private SocieteRepository societeRepository;
	@Autowired private BulletinPaieRepository bulletinPaieRepository;
	@Autowired private GratificationRepository gratificationRepository;
	@Autowired private PeriodePaieRepository periodePaieRepository;

	List<Gratification> gratificationList;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Gratification save(Gratification gratification) {
		// TODO Auto-generated method stub
		gratification = gratificationRepository.save(gratification);
	
		return gratification;
	}

/*	@Override
	@Transactional(rollbackFor = Exception.class)
	public GratificationDTO savedto(Long id, String annee) {
		// TODO Auto-generated method stub
		GratificationDTO exerciceDTO = new GratificationDTO();
		try{
			Echelonnement exercice = new Echelonnement();
		
			if(id != null)
				exercice = gratificationRepository.findOne(id);
			exercice.setAnnee(annee);
			exercice=gratificationRepository.save(exercice);
			exerciceDTO.setRow(exercice);;
			exerciceDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(exercice.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			exerciceDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT UTILISATEUR [NOM : ").append(annee).toString());
			ex.getStackTrace();
		}
		return exerciceDTO;
	}
*/
	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		Gratification gratification = gratificationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));

	
		gratificationRepository.delete(gratification);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) gratificationRepository.count();
	}

	@Override
	public GratificationDTO chargerFichierExcel(String matricule, String nomComplet, int nombrePart, int ancienete,
			Double salaireBase, Double sursalaire, Double indemniteLogement, Double avance, Double alios,
			Double carec) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public GratificationDTO loadGratificationProvisional(Pageable pageable) {
		// TODO Auto-generated method stub
		GratificationDTO gratificationDTO = new GratificationDTO();
		gratificationList = new ArrayList<Gratification>();
		List<ContratPersonnel> contratPersonnelList = contratPersonnelRepository.findByStatutTrue();
		LivreDePaieGratification2 lpg = null;
		Societe mysociete=null;
		List<Societe> malist=societeRepository.findAll();
		if(malist.size()>0) mysociete = malist.get(0);

			String typegratif = mysociete.getGratifica();
			Double gratifca = mysociete.getTxgratif();

		   if(gratifca==0d)gratifca=1d;


		PeriodePaie periodePaie = periodePaieRepository.findByCloture(false);
		for(ContratPersonnel contratPersonnel : contratPersonnelList){
			StringBuilder nom = new StringBuilder();
			Double salbase=0d;
			if(typegratif=="SALAIRE CATEGORIEL") {
				lpg = new LivreDePaieGratification2(nom.append(contratPersonnel.getPersonnel().getNom()).append(" ").append(contratPersonnel.getPersonnel().getPrenom()).toString(),
						contratPersonnel.getPersonnel().getNombrePart(), contratPersonnel.getCategorie().getSalaireDeBase() * gratifca / 100, 25000.0, 0.0, contratPersonnel.getPersonnel().getdArrivee(), contratPersonnel.getPersonnel().getSituationMatri(), contratPersonnel.getPersonnel().getNombrEnfant(), contratPersonnel.getFonction().getLibelle());
			}
			if(typegratif=="SALAIRE NET A PAYER") {

				Double pret=0d;Double avance=0d;Double netapayer=0d;Double grati=0d;
				netapayer=bulletinPaieRepository.findByBulletinAndPersonnel(contratPersonnel.getPersonnel().getId(),periodePaieRepository.findByCloture(false).getId()).getNetapayer();
				pret=bulletinPaieRepository.findByBulletinAndPersonnel(contratPersonnel.getPersonnel().getId(),periodePaieRepository.findByCloture(false).getId()).getPretaloes();
				avance=bulletinPaieRepository.findByBulletinAndPersonnel(contratPersonnel.getPersonnel().getId(),periodePaieRepository.findByCloture(false).getId()).getAvanceetacompte();
				grati=netapayer-pret-avance;
				lpg = new LivreDePaieGratification2(nom.append(contratPersonnel.getPersonnel().getNom()).append(" ").append(contratPersonnel.getPersonnel().getPrenom()).toString(),
						contratPersonnel.getPersonnel().getNombrePart(), grati*gratifca/100, 25000.0, 0.0, contratPersonnel.getPersonnel().getdArrivee(), contratPersonnel.getPersonnel().getSituationMatri(), contratPersonnel.getPersonnel().getNombrEnfant(), contratPersonnel.getFonction().getLibelle());

			}

			gratificationList.add(new Gratification((double) lpg.getB(), lpg.getC(), lpg.getK(), lpg.getE(), lpg.getF(), lpg.getG(), lpg.getI(), 
					lpg.getN(), lpg.getO(), lpg.getP(), lpg.getR(), lpg.getS(), lpg.getT(), lpg.getU(), lpg.getW(), lpg.getD(), 
					lpg.getL(), lpg.getH(), lpg.getJ(), lpg.getM(), lpg.getQ(), lpg.getV(), lpg.getX(), lpg.getY(), contratPersonnel, periodePaie));
			/*lpg = new LivreDePaieGratification2(nom.append(contratPersonnel.getPersonnel().getNom()).append(" ").append(contratPersonnel.getPersonnel().getPrenom()).toString(), 
					contratPersonnel.getPersonnel().getNombrePart(), gratificationBase, contratPersonnel.getIndemniteTransport(), indemniteFinCarriere, contratPersonnel.getPersonnel().getdArrivee(), contratPersonnel.getPersonnel().getSituationMatri(), contratPersonnel.getPersonnel().getNombrEnfant(), contratPersonnel.getFonction().getLibelle());*/
		}

		gratificationDTO.setRows(gratificationList);
		gratificationDTO.setTotal(gratificationList.size());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return gratificationDTO;


	}

	@Override
	public GratificationDTO loadGratificationProvisional(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		/*GratificationDTO utilisateurDTO = new GratificationDTO();
		Page<Echelonnement> page = gratificationRepository.findByAnneeLike(pageable, search);
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());*/
		return null;
	}

	@Override
	public GratificationDTO loadGratification(Pageable pageable) {
		// TODO Auto-generated method stub
		GratificationDTO gratificationDTO = new GratificationDTO();
		Page<Gratification> page = gratificationRepository.findAll(pageable);
		gratificationDTO.setRows(page.getContent());
		gratificationDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return gratificationDTO;
	}

	@Override
	public GratificationDTO loadGratification(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		GratificationDTO gratificationDTO = new GratificationDTO();
		Page<Gratification> page = gratificationRepository.findByContratPersonnelPersonnelNomIgnoreCaseContaining(pageable,search);	
		
		gratificationDTO.setRows(page.getContent());
		gratificationDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return gratificationDTO;
	}

	@Override
	public GratificationDTO generateGratification() {
		// TODO Auto-generated method stub
		GratificationDTO gratificationDTO = new GratificationDTO();
		List<Gratification> gratifList = new ArrayList<Gratification>();
		
		for(Gratification gratification : gratificationList){
			List<Gratification> gratifListExist = new ArrayList<Gratification>();
			gratifListExist=gratificationRepository.findByPeriodePaieIdAndContratPersonnelId(gratification.getPeriodePaie().getId(), gratification.getContratPersonnel().getId());
			if(gratification.getContratPersonnel().getPersonnel().getStatut()&& gratifListExist.size()==0)
				gratifList.add(gratification);
		}
		gratificationRepository.saveAll(gratifList);
		gratificationDTO.setRows(gratifList);
		gratificationDTO.setResult("Gratification genere avec succes");
		return gratificationDTO;
	}

	@Override
	public List<Gratification> findByPeriodePaieAndCTRAT(Long idperiod,Long idctrat) {
		// TODO Auto-generated method stub
		return gratificationRepository.findByPeriodePaieIdAndContratPersonnelId(idperiod, idctrat);
	}

	@Override
	public List<Gratification> findByPeriodePaie(Long id) {
		// TODO Auto-generated method stub
		return gratificationRepository.findByPeriodePaieId(id);
	}
	
	
	@Override
	public Gratification findGratification(Long id) {
		// TODO Auto-generated method stub
		return gratificationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));
	}

	@Override
	public List<Gratification> findAllBulletinByvirementforBanque(Long idPeriodePaie, Long idBanque) {
		// TODO Auto-generated method stub
		return gratificationRepository.findVirmtgratifBanque(idPeriodePaie,idBanque);
	}

	/*@Override
	public GratificationDTO loadGratificationProvisional1(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		
	}
*/

	
}
