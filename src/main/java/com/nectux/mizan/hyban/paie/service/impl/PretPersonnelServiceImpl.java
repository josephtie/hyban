package com.nectux.mizan.hyban.paie.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.nectux.mizan.hyban.paie.dto.PretPersonnelDTO;
import com.nectux.mizan.hyban.paie.entity.Echelonnement;
import com.nectux.mizan.hyban.paie.entity.MvtConge;
import com.nectux.mizan.hyban.paie.entity.PretPersonnel;
import com.nectux.mizan.hyban.paie.repository.EchelonnementRepository;
import com.nectux.mizan.hyban.paie.repository.PretPersonnelRepository;
import com.nectux.mizan.hyban.paie.repository.PretRepository;
import com.nectux.mizan.hyban.paie.service.EchelonnementService;
import com.nectux.mizan.hyban.paie.service.PretPersonnelService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.utils.DateManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("pretPersonnelService")
public class PretPersonnelServiceImpl implements PretPersonnelService {
	
	private static final Logger logger = LogManager.getLogger(PretPersonnelServiceImpl.class);
	
	//@Autowired private EchelonnementRepository pretPersonnelRepository;
	@Autowired private PretPersonnelRepository pretPersonnelRepository;
	
	@Autowired private PretRepository pretRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository PeriodePaieRepository;
	@Autowired private EchelonnementService echelonnementService;
	@Autowired private EchelonnementRepository echelonnementRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PretPersonnel save(PretPersonnel pretPersonnel) {
		// TODO Auto-generated method stub
		pretPersonnel = pretPersonnelRepository.save(pretPersonnel);
	
		return pretPersonnel;
	}

/*	@Override
	@Transactional(rollbackFor = Exception.class)
	public PretPersonnelDTO savedto(Long id, String annee) {
		// TODO Auto-generated method stub
		PretPersonnelDTO exerciceDTO = new PretPersonnelDTO();
		try{
			PretPersonnel exercice = new PretPersonnel();
		
			if(id != null)
				exercice = pretPersonnelRepository.findOne(id);
			exercice.setAnnee(annee);
			exercice=pretPersonnelRepository.save(exercice);
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
		PretPersonnelDTO pretpersoDTO = new PretPersonnelDTO();
		PretPersonnel pretperso = new PretPersonnel();
		 pretperso = pretPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("PretPersonnel not found for id " + id));

		List<Echelonnement> listregle=echelonnementService.reglerModalite(pretperso.getPersonnel().getId(), pretperso.getPeriode().getId());
		 if(listregle.size()>0){
			 
			 pretpersoDTO.setResult("Impossible de modifier ce pret:reglement en cours");
		 }else{
			 List<Echelonnement> listupregle=echelonnementRepository.findByPretPersonnelId(pretperso.getId());
			 echelonnementRepository.deleteAll(listupregle);
			 pretPersonnelRepository.delete(pretperso);
			 pretpersoDTO.setResult("Suppression effectuee");
				
			
		 }
		
		 return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) pretPersonnelRepository.count();
	}

	@Override
	public PretPersonnelDTO loadPretPersonnel(Pageable pageable) {
		// TODO Auto-generated method stub
		PretPersonnelDTO pretPersonnelDTO = new PretPersonnelDTO();
		Page<PretPersonnel> page = pretPersonnelRepository.findAll(pageable);
		pretPersonnelDTO.setRows(page.getContent());
		pretPersonnelDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return pretPersonnelDTO;
	}

	@Override
	public PretPersonnelDTO loadPretPersonnel(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		PretPersonnelDTO utilisateurDTO = new PretPersonnelDTO();
		Page<PretPersonnel> page = pretPersonnelRepository.findByPersonnelNomLike(pageable, "%"+search+"%");
		utilisateurDTO.setRows(page.getContent());
		utilisateurDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return null;
	}

	@Override
	public PretPersonnelDTO saver(Double montant, Long echelonage,Long idPret, Long idPers, String dEmprunt,Long idPeriodDep) {
		// TODO Auto-generated method stub
		PretPersonnelDTO pretpersoDTO = new PretPersonnelDTO();
		try{
			List<PretPersonnel> personnelpretList = new ArrayList<PretPersonnel>();
			PretPersonnel pretperso = new PretPersonnel();
		
			pretperso.setMontant(montant);
			pretperso.setDateEmprunt(DateManager.stringToDate(dEmprunt,"dd/MM/yyyy"));
			pretperso.setEchelonage(echelonage);
			pretperso.setPeriode(PeriodePaieRepository.findById(idPeriodDep) .orElseThrow(() -> new EntityNotFoundException("PeriodePaie not found for id " + idPeriodDep)));
			pretperso.setPersonnel(personnelRepository.findById(idPers)
					.orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idPers)));

			pretperso.setPret(pretRepository.findById(idPret)
					.orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPret)));
			personnelpretList.add(pretperso);
			pretperso=pretPersonnelRepository.save(pretperso);
			pretpersoDTO.setRow(pretperso);
			pretpersoDTO.setRows(personnelpretList);
			pretpersoDTO.setResult("success");
			
			if(pretperso.getId() != null){
				
				//Calcul du montant en fonction de l'echellonnage
				Double montEchell = (double) 0;
				try {
				montEchell = (pretperso.getMontant())/pretperso.getEchelonage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
				PeriodePaie periodePaieDepart = new PeriodePaie();
				try {
					
					periodePaieDepart = PeriodePaieRepository.findById(idPeriodDep).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idPeriodDep));
				} catch (Exception e) {
					e.printStackTrace();
				}
								//listDoc.add(docu);
			
				Echelonnement pretEchelle = new Echelonnement();
				pretEchelle.setPeriodePaie(periodePaieDepart);
				pretEchelle.setPretPersonnel(pretperso);
				pretEchelle.setPaye(false);
				pretEchelle.setMontant(montEchell);
			
				//Enregistrement
				 pretEchelle= echelonnementService.save(pretEchelle);
				for(int i = 1; i < pretperso.getEchelonage(); i++){

					PeriodePaie periodePaieRech = new PeriodePaie();
					periodePaieRech = PeriodePaieRepository.findById(periodePaieDepart.getId()+i).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idPers));
					//listDoc.add(docu);
				
					Echelonnement pretEcheller = new Echelonnement();
					pretEcheller.setPeriodePaie(periodePaieRech);
					pretEcheller.setPretPersonnel(pretperso);
					pretEcheller.setPaye(false);
					pretEcheller.setMontant(montEchell);
				
					//Enregistrement
					 pretEcheller= echelonnementService.save(pretEcheller);
					
				
				
					
				}

			}
			
			
			logger.info(new StringBuilder().append(">>>>> ").append(pretperso.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			pretpersoDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT PRETPERSONNEL [NOM : ").append(montant).toString());
			ex.getStackTrace();
		}
		return pretpersoDTO;
	}

	@Override
	public PretPersonnelDTO PretPersonneldupersonnel(Long Idpers) {
		// TODO Auto-generated method stub
		PretPersonnelDTO pretPersonnelDTO = new PretPersonnelDTO();
		 List<PretPersonnel> personnelpretList = new ArrayList<PretPersonnel>();
		 Personnel person=personnelRepository.findById(Idpers).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + Idpers));
		 personnelpretList=pretPersonnelRepository.findByPersonnel(person);
		pretPersonnelDTO.setRows(personnelpretList);
	
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return pretPersonnelDTO;
	}

	@Override
	public List<PretPersonnel> listdespretpersonnel() {
		// TODO Auto-generated method stub
		return pretPersonnelRepository.findAll();
	}

	@Override
	public PretPersonnel findpret(Long idPret) {
		// TODO Auto-generated method stub
		return  pretPersonnelRepository.findById(idPret).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idPret));
	}

	@Override
	public PretPersonnelDTO update(Long idPretpers,Double montant, Long echelonage,Long idPret, Long idPers, String dEmprunt, Long idPeriodDep) {
		// TODO Auto-generated method stub
		PretPersonnelDTO pretpersoDTO = new PretPersonnelDTO();
		try {
			 PretPersonnel pretperso = new PretPersonnel();
			 pretperso = pretPersonnelRepository.findById(idPretpers).orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idPretpers));
			List<PretPersonnel> personnelpretList = new ArrayList<PretPersonnel>();
			List<Echelonnement> listregle=echelonnementService.reglerModalite(idPers, pretperso.getPeriode().getId());
			 if(listregle.size()>0){
				 pretpersoDTO.setResult("Impossible de modifier ce pret:reglement en cours");
			 } else{
				
				pretperso.setMontant(montant);
				pretperso.setDateEmprunt(DateManager.stringToDate(dEmprunt,"dd/MM/yyyy"));
				pretperso.setEchelonage(echelonage);
				 pretperso.setPeriode(PeriodePaieRepository.findById(idPeriodDep)
						 .orElseThrow(() -> new EntityNotFoundException("PeriodePaie not found for id " + idPeriodDep)));

				 pretperso.setPersonnel(personnelRepository.findById(idPers)
						 .orElseThrow(() -> new EntityNotFoundException("Personnel not found for id " + idPers)));

				 pretperso.setPret(pretRepository.findById(idPret)
						 .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPret)));

				 personnelpretList.add(pretperso);
				pretperso=pretPersonnelRepository.save(pretperso);
				pretpersoDTO.setRow(pretperso);
				pretpersoDTO.setRows(personnelpretList);
				pretpersoDTO.setResult("success");
				
				if(pretperso.getId() != null){
					
					//Calcul du montant en fonction de l'echellonnage
					Double montEchell =  (double)0;
					try {
					montEchell = (pretperso.getMontant())/pretperso.getEchelonage();
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					PeriodePaie periodePaieDepart = new PeriodePaie();
					try {
						
						periodePaieDepart = PeriodePaieRepository.findById(idPeriodDep) .orElseThrow(() -> new EntityNotFoundException("PeriodePaie not found for id " + idPeriodDep));
					} catch (Exception e) {
						e.printStackTrace();
					}
				    //listDoc.add(docu);
					List<Echelonnement> listupregle=echelonnementRepository.findByPretPersonnelId(pretperso.getId());
					echelonnementRepository.deleteAll(listupregle);
					
					Echelonnement pretEchelle = new Echelonnement();
					pretEchelle.setPeriodePaie(periodePaieDepart);
					pretEchelle.setPretPersonnel(pretperso);
					pretEchelle.setPaye(false);
					pretEchelle.setMontant(montEchell);
				
					 pretEchelle= echelonnementService.save(pretEchelle);
					for(int i = 1; i < pretperso.getEchelonage(); i++){

						PeriodePaie periodePaieRech = new PeriodePaie();
						periodePaieRech = PeriodePaieRepository.findById(periodePaieDepart.getId()+i) .orElseThrow(() -> new EntityNotFoundException("PeriodePaie not found for id " + idPeriodDep));
						//listDoc.add(docu);
					
						Echelonnement pretEcheller = new Echelonnement();
						pretEcheller.setPeriodePaie(periodePaieRech);
						pretEcheller.setPretPersonnel(pretperso);
						pretEcheller.setPaye(false);
						pretEcheller.setMontant(montEchell);
					
						//Enregistrement
						 pretEcheller= echelonnementService.save(pretEcheller);
						
					
					
						
					}

				}
				
			 }	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pretpersoDTO;
	}

	
}
