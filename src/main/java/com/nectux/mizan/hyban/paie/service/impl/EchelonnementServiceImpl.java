package com.nectux.mizan.hyban.paie.service.impl;

import java.util.List;

import com.nectux.mizan.hyban.paie.dto.EchelonnementDTO;
import com.nectux.mizan.hyban.paie.entity.Echelonnement;
import com.nectux.mizan.hyban.paie.repository.EchelonnementRepository;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.paie.service.EchelonnementService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;

//import javax.persistence.EntityNotFoundException;

@Transactional
@Service("echelonnementService")
public class EchelonnementServiceImpl implements EchelonnementService {
	
	private static final Logger logger = LogManager.getLogger(EchelonnementServiceImpl.class);
	
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private EchelonnementRepository echelonnementRepository;
	@Autowired private PeriodePaieService periodepaieService;
	@Autowired private BulletinPaieService bulletinPaieService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Echelonnement save(Echelonnement echelonnement) {
		// TODO Auto-generated method stub
		echelonnement = echelonnementRepository.save(echelonnement);
	
		return echelonnement;
	}

/*	@Override
	@Transactional(rollbackFor = Exception.class)
	public EchelonnementDTO savedto(Long id, String annee) {
		// TODO Auto-generated method stub
		EchelonnementDTO exerciceDTO = new EchelonnementDTO();
		try{
			Echelonnement exercice = new Echelonnement();
		
			if(id != null)
				exercice = echelonnementRepository.findOne(id);
			exercice.setAnnee(annee);
			exercice=echelonnementRepository.save(exercice);
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
		Echelonnement echelonnement = echelonnementRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));

		
	
		echelonnementRepository.delete(echelonnement);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) echelonnementRepository.count();
	}

	@Override
	public EchelonnementDTO loadEchelonnement(Pageable pageable) {
		// TODO Auto-generated method stub
		EchelonnementDTO echelonnementDTO = new EchelonnementDTO();
		Page<Echelonnement> page = echelonnementRepository.findAll(pageable);
		echelonnementDTO.setRows(page.getContent());
		echelonnementDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return echelonnementDTO;
	}

	@Override
	public EchelonnementDTO loadEchelonnement(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		EchelonnementDTO echelonnementDTO = new EchelonnementDTO();
		Page<Echelonnement> page = echelonnementRepository.findByPretPersonnelPersonnelNomLike(pageable, "%"+search+"%");
		echelonnementDTO.setRows(page.getContent());
		echelonnementDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return echelonnementDTO;
	}

	@Override
	public Echelonnement update(Long id ,Long periodId) {
		// TODO Auto-generated method stub
		Echelonnement echelonnemnt = new Echelonnement();
		echelonnemnt=echelonnementRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));
		String message ="";
		try{
			
			if(echelonnemnt.getId() != null){
				
				//Recherche de la periode encour
				PeriodePaie periodePaieOuverte = new PeriodePaie();
				try {
					//periodePaieOuverte = periodepaieService.findPeriodePaieOuverte();
					periodePaieOuverte = periodepaieService.findPeriodeactive();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				message = "Reechellonnement non effectuee";
				
				Boolean continu = false;
				PeriodePaie periodePaieVerif = new PeriodePaie();
				try {
					periodePaieVerif = periodePaieRepository.findById(periodId) .orElseThrow(() -> new EntityNotFoundException("ContratPersonnel not found for id " + id));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(!periodePaieVerif.equals(null)){
					if(periodePaieVerif.getMois().getId() != null && periodePaieOuverte.getMois().getId() != null){
						if(periodePaieVerif.getId() >= periodePaieOuverte.getId()){
							continu = true;
							
						}else{
							continu = false;
							message = "Le mois  doit etre superieur au mois encours";
						}
					}
				}
				
				if(continu){
					/*Echelonnement echelonnemnt = new Echelonnement();
				
					try {
						pretpersonnelechelleRech = pretpersonnelechelleService.findPretPersonnelEchelle(pretpersonnelechellePost.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}*/
				
					if(echelonnemnt.getId() != null){
						echelonnemnt.setPeriodePaie(periodePaieVerif);
				
						//Update
						 echelonnemnt = echelonnementRepository.save(echelonnemnt);
						
						message = "Reechellonnement effectuee avec succes";
						logger.info("Rechellonnement modifie avec succes");
						
					/*	if(!echelonnemnt.equals(null)){
							
							if(echelonnemnt.getId() !=null){
								
								if(echelonnemnt.getPeriodePaie().getId() != null && periodePaieOuverte.getId() != null){
									
								//if(pretPersonnelEchell.getPeriodepaie().getId() == periodePaieOuverte.getId()){
									
									BulletinPaie bull = new BulletinPaie();
									try {
										bull = bulletinPaieService.findBulletinByPeriodePaieAndPersonnel(periodePaieOuverte, echelonnemnt.getPretPersonnel().getPersonnel());
									} catch (Exception e) {
										e.printStackTrace();
									}
									//System.out.println("jijijijijij le bullllll retouve est   f df d f df "+bull.toString());
									
									if(!bull.equals(null)){
										if(bull.getId() != null){
											if(bull.getCloture() == false){
												bull.setCalculer(false);
												bulletinPaieService.save(bull);
											}
										}
									}
								//}
								}
								
							}
						}*/
					}
				}
				
			
			}
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.error(ex.getStackTrace());
			logger.error("une erreur a ete dectectee lors de l'enregistrement ou de la modification du pret personnel echelle");
		}
		echelonnemnt.setMessage(message);
		return echelonnemnt;
	}

	@Override
	public EchelonnementDTO findechelondupret(Long idpretpersonel) {
		// TODO Auto-generated method stub
		EchelonnementDTO echelonnementDTO = new EchelonnementDTO();
		List<Echelonnement> list = echelonnementRepository.findByPretPersonnelId(idpretpersonel);
		echelonnementDTO.setRows(list);
		echelonnementDTO.setTotal(list.size());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return echelonnementDTO;
	}

	@Override
	public List<Echelonnement> reglerModalite(Long idpers, Long periodId) {
		// TODO Auto-generated method stub
		EchelonnementDTO echelonnementDTO = new EchelonnementDTO();
		List<Echelonnement> list = echelonnementRepository.findByPretPersonnelPersonnelIdAndPeriodePaieIdAndPayeTrue(idpers, periodId);
		echelonnementDTO.setRows(list);
		echelonnementDTO.setTotal(list.size());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return list;
	}

	@Override
	public List<Echelonnement> findModalitedepret(Long idpret) {
		return null;
	}



	
}
