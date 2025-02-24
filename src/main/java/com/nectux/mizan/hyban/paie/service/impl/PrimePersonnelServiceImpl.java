package com.nectux.mizan.hyban.paie.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.paie.dto.PrimePersonnelDTO;
import com.nectux.mizan.hyban.parametrages.repository.RubriqueRepository;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.repository.PrimePersonnelRepository;
import com.nectux.mizan.hyban.paie.service.EchelonnementService;
import com.nectux.mizan.hyban.paie.service.PrimePersonnelService;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.utils.Erreur;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("primePersonnelService")
public class PrimePersonnelServiceImpl implements PrimePersonnelService {
	private static final Logger logger = LogManager.getLogger(PrimePersonnelServiceImpl.class);
	//private static final Logger logger = Logger.getLogger(PrimePersonnelServiceImpl.class);
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;
	//@Autowired private EchelonnementRepository pretPersonnelRepository;
	@Autowired private PrimePersonnelRepository primePersonnelRepository;
	
	@Autowired private RubriqueRepository primeRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private PeriodePaieRepository  PeriodePaieRepository;
	@Autowired private EchelonnementService  echelonnementService;
	@Autowired private ContratPersonnelRepository  contratPersonnelRepository;
	@Override
	public PrimePersonnel save(PrimePersonnel primePersonnel) {
		// TODO Auto-generated method stub
		primePersonnel = primePersonnelRepository.save(primePersonnel);
		
		return primePersonnel;
	}
	@Override
	public PrimePersonnel findprimepersonnel(Long idPret) {
		// TODO Auto-generated method stub
		return  primePersonnelRepository.findById(idPret)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPret));
	}
	@Override
	@Transactional
	public PrimePersonnelDTO saver(Long id, Double montant, Integer valeur,
                                   Long idPrime, Long idCtrat, Long idPeriodDep) {
		// TODO Auto-generated method stub
		PrimePersonnelDTO primepersonnelDTO = new PrimePersonnelDTO();
		PrimePersonnel primeperso;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			if(id != null){
				primeperso = primePersonnelRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("PeriodePaie not found for id " + id));
				
			} else {
				primeperso = new PrimePersonnel();
			}
//			if(idPrime==null)
//			primeperso.setPrime(null);
//			else
			primeperso.setPrime(primeRepository.findById(idPrime)
					.orElseThrow(() -> new EntityNotFoundException("PeriodePaie not found for id " + idPeriodDep)));

			primeperso.setPeriode(PeriodePaieRepository.findById(idPeriodDep)  .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPeriodDep)));
//			if(idPers==null)
//				primeperso.setContratPersonnel(null);
//			else
			primeperso.setContratPersonnel(contratPersonnelRepository.findByIdAndStatut(idCtrat, true));

			primeperso.setValeur(valeur);

			if(montant==null)
				primeperso.setMontant(0d);
			else {
				if (primeperso.getPrime().getTaux() != null && primeperso.getValeur() > 0) {
					primeperso.setMontant(primeperso.getValeur() * (montant + (montant * primeperso.getPrime().getTaux() / 100)));
				}else{
					primeperso.setMontant(montant);
				}
			}
			primeperso.setDateSaisie(new Date());		
			
			if(primeperso.getPrime() == null ){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ prime est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
//			if(primeperso.getPrime() != null ){
//				List<PrimePersonnel> listverif=primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(idCtrat,primeperso.getPeriode().getId(),primeperso.getPrime().getId());
//				if(listverif.size()>0){
//				sb = new StringBuilder();
//				erreur.setCode(10);
//				erreur.setDescription("prime dupliquee");
//				sb.append("le champ prime est obligatoire");
//				erreur.setMessage(sb.toString());
//				listErreur.add(erreur);}
//			}
			
			if(primeperso.getPeriode() == null ){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ periode est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(primeperso.getContratPersonnel()== null ){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le champ contrat perso est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			}
			
			if(listErreur.isEmpty()){
				primeperso = primePersonnelRepository.save(primeperso);
				sb = new StringBuilder();
				sb.append(primeperso.getPrime()).append(" enregistre avec succes");
				primepersonnelDTO.setResult(true);
				primepersonnelDTO.setStatus(true);
				primepersonnelDTO.setRow(primeperso);
				primepersonnelDTO.setRows(primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(primeperso.getContratPersonnel().getPersonnel().getId(),primeperso.getPeriode().getId()));
				primepersonnelDTO.setMessage(sb.toString());
				primepersonnelDTO.setTotal(0);
				primepersonnelDTO.setErrors(listErreur);
			} else {
				primepersonnelDTO.setResult(false);
				primepersonnelDTO.setStatus(false);
				primepersonnelDTO.setRow(null);
				primepersonnelDTO.setRows(null);
				primepersonnelDTO.setMessage("voir liste erreur");
				primepersonnelDTO.setTotal(0);
				primepersonnelDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			primepersonnelDTO.setResult(false);
			primepersonnelDTO.setStatus(false);
			primepersonnelDTO.setRow(null);
			primepersonnelDTO.setRows(null);
			primepersonnelDTO.setMessage(ex.getMessage());
			primepersonnelDTO.setTotal(0);
			primepersonnelDTO.setErrors(listErreur);
		}
		return primepersonnelDTO;
	}
	/*@Override
	public PrimePersonnelDTO update(Long idPretpers, Double montant,
			Long echelonage, Long idPret, Long idPers, String dEmprunt,
			Long idPeriodDep) {
		// TODO Auto-generated method stub
		return null;
	}*/
	@Override
	public List<PrimePersonnel> listdesprimepersonnel() {
		// TODO Auto-generated method stub
		return primePersonnelRepository.findAll();
	}
	@Override
	public List<PrimePersonnel> listdesprimepersonnelPeriode(Long idPeriod) {
		// TODO Auto-generated method stub
		return primePersonnelRepository.findByPeriodePaieId(idPeriod);
	}
	@Override
	public List<PrimePersonnel> listdesprimepersonnelPeriodePrime(
			Long idPeriod, Long idPrime) {
		// TODO Auto-generated method stub
		return primePersonnelRepository.findByPrimeIdAndPeriodePaieId(idPrime, idPeriod);
	}

	@Override
	public List<PrimePersonnel> listdesprimepersonnelPeriodePrime(Long idPeriod, Long idPrime, Long idContrat) {
		return primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(idContrat,idPeriod,idPrime);
	}

	@Override
	public List<PrimePersonnel> listPrimeContratpersonnelperiode(Long idPers,
			Long idPeriod, Long idPrime) {
		// TODO Auto-generated method stub
		return primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieIdAndPrimeId(idPers, idPeriod, idPrime);
	}
	@Override
	public PrimePersonnelDTO delete(Long id) {
		// TODO Auto-generated method stub
		PrimePersonnelDTO primepersonnelDTO = new PrimePersonnelDTO();
		PrimePersonnel primeperso =new PrimePersonnel();
		listErreur = new ArrayList<Erreur>();
		try{
			primeperso = primePersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(primeperso == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("sanction inexistante");
				sb.append("cette sanction est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				primepersonnelDTO.setResult(false);
				primepersonnelDTO.setStatus(false);
				primepersonnelDTO.setRow(null);
				primepersonnelDTO.setRows(null);
				primepersonnelDTO.setMessage("voir liste erreur");
				primepersonnelDTO.setTotal(0);
				primepersonnelDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
                assert primeperso != null;
                primePersonnelRepository.delete(primeperso);
				sb = new StringBuilder();
				sb.append(" affectation supprimee avec succes");
				primepersonnelDTO.setResult(true);
				primepersonnelDTO.setStatus(true);
				primepersonnelDTO.setRow(primeperso);
				primepersonnelDTO.setRows(primePersonnelRepository.findByContratPersonnelPersonnelIdAndPeriodePaieId(primeperso.getContratPersonnel().getPersonnel().getId(),primeperso.getPeriode().getId()));
				primepersonnelDTO.setMessage(sb.toString());
				primepersonnelDTO.setTotal(0);
				primepersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			primepersonnelDTO.setResult(false);
			primepersonnelDTO.setStatus(false);
			primepersonnelDTO.setRow(null);
			primepersonnelDTO.setRows(null);
			primepersonnelDTO.setMessage(ex.getMessage());
			primepersonnelDTO.setTotal(0);
			primepersonnelDTO.setErrors(listErreur);
		}
		return primepersonnelDTO;
	}
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) primePersonnelRepository.count();
	}
	
	@Override
	public PrimePersonnelDTO loadPrimePersonnel(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PrimePersonnelDTO loadPrimePersonnel(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PrimePersonnelDTO listdesprimepersonnelMoisEnPrime(Pageable pageable,Long idPeriod,
			Long idPers) {
		PrimePersonnelDTO primepersonnelDTO = new PrimePersonnelDTO();
		Page<PrimePersonnel> page = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieId( pageable,idPers, idPeriod);
		primepersonnelDTO.setRows(page.getContent());
		primepersonnelDTO.setTotal(page.getTotalElements());
		return primepersonnelDTO;
	}

/*	@Override
	@Transactional(rollbackFor = Exception.class)
	public PretPersonnel save(PretPersonnel pretPersonnel) {
		// TODO Auto-generated method stub
		pretPersonnel = pretPersonnelRepository.save(pretPersonnel);
	
		return pretPersonnel;
	}

	@Override
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

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		PretPersonnelDTO	pretpersoDTO = new PretPersonnelDTO();
		PretPersonnel pretperso = new PretPersonnel();
		 pretperso = pretPersonnelRepository.findOne(id);
		List<Echelonnement> listregle=echelonnementService.reglerModalite(pretperso.getPersonnel().getId(), pretperso.getPeriode().getId());
		 if(listregle.size()>0){
			 
			 pretpersoDTO.setResult("Impossible de modifier ce pret:reglement en cours");
		 }else{
			 List<Echelonnement> listupregle=echelonnementRepository.findByPretPersonnelId(pretperso.getId());
			 echelonnementRepository.delete(listupregle);
			 pretPersonnelRepository.delete(id);
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
			pretperso.setPeriode(PeriodePaieRepository.findOne(idPeriodDep));
			pretperso.setPersonnel(personnelRepository.findOne(idPers));
			pretperso.setPret(pretRepository.findOne(idPret));
			personnelpretList.add(pretperso);
			pretperso=pretPersonnelRepository.save(pretperso);
			pretpersoDTO.setRow(pretperso);
			pretpersoDTO.setRows(personnelpretList);
			pretpersoDTO.setResult("success");
			
			if(pretperso.getId() != null){
				
				//Calcul du montant en fonction de l'echellonnage
				Double montEchell = new Double(0);
				try {
				montEchell = (pretperso.getMontant())/pretperso.getEchelonage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
				PeriodePaie periodePaieDepart = new PeriodePaie();
				try {
					
					periodePaieDepart = PeriodePaieRepository.findOne(idPeriodDep);
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
					periodePaieRech = PeriodePaieRepository.findOne(periodePaieDepart.getId()+i);
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
		 Personnel person=personnelRepository.findOne(Idpers);
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
		return  pretPersonnelRepository.findOne(idPret);
	}

	@Override
	public PretPersonnelDTO update(Long idPretpers,Double montant, Long echelonage,Long idPret, Long idPers, String dEmprunt, Long idPeriodDep) {
		// TODO Auto-generated method stub
		PretPersonnelDTO pretpersoDTO = new PretPersonnelDTO();
		try {
			 PretPersonnel pretperso = new PretPersonnel();
			 pretperso = pretPersonnelRepository.findOne(idPretpers);
			List<PretPersonnel> personnelpretList = new ArrayList<PretPersonnel>();
			List<Echelonnement> listregle=echelonnementService.reglerModalite(idPers, pretperso.getPeriode().getId());
			 if(listregle.size()>0){
				 pretpersoDTO.setResult("Impossible de modifier ce pret:reglement en cours");
			 } else{
				
				pretperso.setMontant(montant);
				pretperso.setDateEmprunt(DateManager.stringToDate(dEmprunt,"dd/MM/yyyy"));
				pretperso.setEchelonage(echelonage);
				pretperso.setPeriode(PeriodePaieRepository.findOne(idPeriodDep));
				pretperso.setPersonnel(personnelRepository.findOne(idPers));
				pretperso.setPret(pretRepository.findOne(idPret));
				personnelpretList.add(pretperso);
				pretperso=pretPersonnelRepository.save(pretperso);
				pretpersoDTO.setRow(pretperso);
				pretpersoDTO.setRows(personnelpretList);
				pretpersoDTO.setResult("success");
				
				if(pretperso.getId() != null){
					
					//Calcul du montant en fonction de l'echellonnage
					Double montEchell = new Double(0);
					try {
					montEchell = (pretperso.getMontant())/pretperso.getEchelonage();
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					PeriodePaie periodePaieDepart = new PeriodePaie();
					try {
						
						periodePaieDepart = PeriodePaieRepository.findOne(idPeriodDep);
					} catch (Exception e) {
						e.printStackTrace();
					}
				    //listDoc.add(docu);
					List<Echelonnement> listupregle=echelonnementRepository.findByPretPersonnelId(pretperso.getId());
					echelonnementRepository.delete(listupregle);
					
					Echelonnement pretEchelle = new Echelonnement();
					pretEchelle.setPeriodePaie(periodePaieDepart);
					pretEchelle.setPretPersonnel(pretperso);
					pretEchelle.setPaye(false);
					pretEchelle.setMontant(montEchell);
				
					 pretEchelle= echelonnementService.save(pretEchelle);
					for(int i = 1; i < pretperso.getEchelonage(); i++){

						PeriodePaie periodePaieRech = new PeriodePaie();
						periodePaieRech = PeriodePaieRepository.findOne(periodePaieDepart.getId()+i);
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
	}*/

	
}
