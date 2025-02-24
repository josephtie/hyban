package com.nectux.mizan.hyban.rh.absences.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.paie.entity.TempEffectif;
import com.nectux.mizan.hyban.paie.repository.TempEffectifRepository;
import com.nectux.mizan.hyban.paie.service.TempEffectifService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.personnel.service.PersonnelService;
import com.nectux.mizan.hyban.rh.absences.dto.AbsencesPersonnelDTO;
import com.nectux.mizan.hyban.rh.absences.entity.AbsencesPersonnel;
import com.nectux.mizan.hyban.rh.absences.repository.AbsencesPersonnelRepository;
import com.nectux.mizan.hyban.rh.absences.repository.AbsencesRepository;
import com.nectux.mizan.hyban.rh.absences.service.AbsencesPersonnelService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("absencespersonnelService")
public class AbsencesPersonnelServiceImpl implements AbsencesPersonnelService {
	
	@Autowired private AbsencesRepository absencesRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private PersonnelService personnelService;
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private AbsencesPersonnelRepository absencesPersonnelRepository;
	@Autowired private TempEffectifService  tempEffectifService;
	@Autowired private TempEffectifRepository  tempEffectifRepository;
	@Autowired private PeriodePaieService   periodePaieService;
	@Autowired private ContratPersonnelRepository   contratPersonnelRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;
	private PeriodePaie myperiodePaie;
	
	@SuppressWarnings("null")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AbsencesPersonnelDTO save(Long id, Long idPersonnel, Long idabsence, String dateDebut, String dateFin,Double tempabsence,Double jourabsence, String observation,Boolean justifier,int sanctSall) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		listErreur = new ArrayList<Erreur>();
		myperiodePaie=periodePaieService.findPeriodeactive();
		AbsencesPersonnel absencesPersonnel;
		try{
			if(id == null){
				absencesPersonnel = new AbsencesPersonnel();
				absencesPersonnel.setDateCreation(new Date());
			} else {
				absencesPersonnel = absencesPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				absencesPersonnel.setDateModification(new Date());
			}
			
			
			if(idPersonnel == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le personnel est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				absencesPersonnel.setPersonnel(personnelRepository.findById(idPersonnel).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPersonnel)));
			
			if(idabsence == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("l'absence est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				absencesPersonnel.setAbsences(absencesRepository.findById(idabsence).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idabsence)));
			
			if(dateDebut == null || dateDebut.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la date de debut est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				absencesPersonnel.setDateDebut(DateManager.stringToDate(dateDebut, "dd/MM/yyyy HH:mm"));
			
			if(dateFin == null || dateFin.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la date de fin est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				absencesPersonnel.setDateRet(DateManager.stringToDate(dateFin, "dd/MM/yyyy HH:mm"));
		
				absencesPersonnel.setHeursabsence(tempabsence);
	
				absencesPersonnel.setJoursabsence(jourabsence);
				absencesPersonnel.setObservation(observation);
	
				absencesPersonnel.setStatut(justifier);
			
			
				absencesPersonnel.setSanctionsalaire(sanctSall);			
			if(listErreur.isEmpty()){
				absencesPersonnel = absencesPersonnelRepository.save(absencesPersonnel);
				sb = new StringBuilder();
				sb.append(" absence personnel enregistree avec succes");
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(absencesPersonnel);
				absencesPersonnelDTO.setRows(null);
				absencesPersonnelDTO.setMessage(sb.toString());
				absencesPersonnelDTO.setTotal(0);
				absencesPersonnelDTO.setErrors(listErreur);
				if(absencesPersonnel.getSanctionsalaire()==4){
				         TempEffectif tpeff=new TempEffectif();
				         tpeff=tempEffectifRepository.findByPersonnelAndPeriodePaie(absencesPersonnel.getPersonnel(), myperiodePaie);
				         if(tpeff==null){
				        	 TempEffectif tpeffacc=new TempEffectif();
				        	 tpeffacc.setDatedesaisie(new Date());
				        	 tpeffacc.setHeurspresence(Math.rint(173.33-absencesPersonnel.getHeursabsence()));
				        	 tpeffacc.setJourspresence(Math.rint(30-absencesPersonnel.getJoursabsence()));
				        	 tpeffacc.setPeriodePaie(myperiodePaie);
				        	 tpeffacc.setPersonnel(absencesPersonnel.getPersonnel());
				        	 tpeffacc=tempEffectifRepository.save(tpeffacc);				        	 
				         }else{
				        	 tpeff.setDatedesaisie(new Date());
				        	 tpeff.setHeurspresence(tpeff.getHeurspresence()-absencesPersonnel.getHeursabsence());
				        	 tpeff.setJourspresence(tpeff.getJourspresence()-absencesPersonnel.getJoursabsence());
				        	 tpeff.setPeriodePaie(myperiodePaie);
				        	 tpeff.setPersonnel(absencesPersonnel.getPersonnel());
				        	 tpeff=tempEffectifRepository.save(tpeff);				        	 
				         }
			   }
				if(absencesPersonnel.getSanctionsalaire()==2){
					 ContratPersonnel ctratpersonnellz = new ContratPersonnel();	   
				    	ctratpersonnellz=contratPersonnelRepository.findByPersonnelIdAndStatut(absencesPersonnel.getPersonnel().getId(),true);
					    Personnel personnel=ctratpersonnellz.getPersonnel();
					    personnel.setNombreJourdu(personnel.getNombreJourdu()-absencesPersonnel.getJoursabsence().intValue());
					    personnel=personnelRepository.save(personnel);
				}
			
		
			} else {
				absencesPersonnelDTO.setResult(false);
				absencesPersonnelDTO.setStatus(false);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(null);
				absencesPersonnelDTO.setMessage("voir liste erreur");
				absencesPersonnelDTO.setTotal(0);
				absencesPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesPersonnelDTO.setResult(false);
			absencesPersonnelDTO.setStatus(false);
			absencesPersonnelDTO.setRow(null);
			absencesPersonnelDTO.setRows(null);
			absencesPersonnelDTO.setMessage(ex.getMessage());
			absencesPersonnelDTO.setTotal(0);
			absencesPersonnelDTO.setErrors(listErreur);
		}
		return absencesPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AbsencesPersonnelDTO saver(Long id, Long idPersonnel, Long idabsence, String dateDebut, String dateFin,Double tempabsence,Double jourabsence, String observation,Boolean justifier,int sanctSal) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		listErreur = new ArrayList<Erreur>();
		try{

		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesPersonnelDTO.setResult(false);
			absencesPersonnelDTO.setStatus(false);
			absencesPersonnelDTO.setRow(null);
			absencesPersonnelDTO.setRows(null);
			absencesPersonnelDTO.setMessage(ex.getMessage());
			absencesPersonnelDTO.setTotal(0);
			absencesPersonnelDTO.setErrors(listErreur);
		}
		return absencesPersonnelDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AbsencesPersonnelDTO delete(Long id) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		AbsencesPersonnel absencesPersonnel;
		listErreur = new ArrayList<Erreur>();
		try{
			absencesPersonnel = absencesPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(absencesPersonnel == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("sanction inexistante");
				sb.append("cette sanction est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				absencesPersonnelDTO.setResult(false);
				absencesPersonnelDTO.setStatus(false);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(null);
				absencesPersonnelDTO.setMessage("voir liste erreur");
				absencesPersonnelDTO.setTotal(0);
				absencesPersonnelDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				absencesPersonnelRepository.delete(absencesPersonnel);
				sb = new StringBuilder();
				sb.append(" affectation supprimee avec succes");
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(absencesPersonnel);
				absencesPersonnelDTO.setRows(null);
				absencesPersonnelDTO.setMessage(sb.toString());
				absencesPersonnelDTO.setTotal(0);
				absencesPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesPersonnelDTO.setResult(false);
			absencesPersonnelDTO.setStatus(false);
			absencesPersonnelDTO.setRow(null);
			absencesPersonnelDTO.setRows(null);
			absencesPersonnelDTO.setMessage(ex.getMessage());
			absencesPersonnelDTO.setTotal(0);
			absencesPersonnelDTO.setErrors(listErreur);
		}
		return absencesPersonnelDTO;
	}

	@Override
	public AbsencesPersonnelDTO findAbsencesPersonnel(Long id) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		AbsencesPersonnel absencesPersonnel;
		listErreur = new ArrayList<Erreur>();
		try{
			absencesPersonnel = absencesPersonnelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(absencesPersonnel == null){
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(absencesPersonnel);
				absencesPersonnelDTO.setRows(null);
				absencesPersonnelDTO.setMessage("sanction inexistante dans le systeme");
				absencesPersonnelDTO.setTotal(1);
				absencesPersonnelDTO.setErrors(listErreur);
			} else {
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(absencesPersonnel);
				absencesPersonnelDTO.setRows(null);
				absencesPersonnelDTO.setMessage("sanction trouvee avec succes");
				absencesPersonnelDTO.setTotal(1);
				absencesPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesPersonnelDTO.setResult(false);
			absencesPersonnelDTO.setStatus(false);
			absencesPersonnelDTO.setRow(null);
			absencesPersonnelDTO.setRows(null);
			absencesPersonnelDTO.setMessage(ex.getMessage());
			absencesPersonnelDTO.setTotal(0);
			absencesPersonnelDTO.setErrors(listErreur);
		}
		return absencesPersonnelDTO;
	}

	@Override
	public AbsencesPersonnelDTO findAbsencesPersonnels() {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		List<AbsencesPersonnel> listAbsencesPersonnel = new ArrayList<AbsencesPersonnel>();
		listErreur = new ArrayList<Erreur>();
		try{
			listAbsencesPersonnel = absencesPersonnelRepository.findAll();
			if(listAbsencesPersonnel == null){
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(new ArrayList<AbsencesPersonnel>());
				absencesPersonnelDTO.setMessage("aucune sanction trouvee");
				absencesPersonnelDTO.setTotal(0);
				absencesPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listAbsencesPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" sanction(s) trouvee(s) avec succes");
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(listAbsencesPersonnel);
				absencesPersonnelDTO.setMessage(sb.toString());
				absencesPersonnelDTO.setTotal(i);
				absencesPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesPersonnelDTO.setResult(false);
			absencesPersonnelDTO.setStatus(false);
			absencesPersonnelDTO.setRow(null);
			absencesPersonnelDTO.setRows(null);
			absencesPersonnelDTO.setMessage(ex.getMessage());
			absencesPersonnelDTO.setTotal(0);
			absencesPersonnelDTO.setErrors(listErreur);
		}
		return absencesPersonnelDTO;
	}

	@Override
	public AbsencesPersonnelDTO findAbsencesPersonnelsByAbsence(Long idSanction) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		List<AbsencesPersonnel> listAbsencesPersonnel = new ArrayList<AbsencesPersonnel>();
		listErreur = new ArrayList<Erreur>();
		try{
			listAbsencesPersonnel = absencesPersonnelRepository.findByAbsencesId(idSanction);
			if(listAbsencesPersonnel == null){
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(new ArrayList<AbsencesPersonnel>());
				absencesPersonnelDTO.setMessage("aucune affectation trouvee");
				absencesPersonnelDTO.setTotal(0);
				absencesPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listAbsencesPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" sanction(s) trouvee(s) avec succes");
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(listAbsencesPersonnel);
				absencesPersonnelDTO.setMessage(sb.toString());
				absencesPersonnelDTO.setTotal(i);
				absencesPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesPersonnelDTO.setResult(false);
			absencesPersonnelDTO.setStatus(false);
			absencesPersonnelDTO.setRow(null);
			absencesPersonnelDTO.setRows(null);
			absencesPersonnelDTO.setMessage(ex.getMessage());
			absencesPersonnelDTO.setTotal(0);
			absencesPersonnelDTO.setErrors(listErreur);
		}
		return absencesPersonnelDTO;
	}

	@Override
	public AbsencesPersonnelDTO findAbsencesPersonnelsByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		List<AbsencesPersonnel> listAbsencesPersonnel = new ArrayList<AbsencesPersonnel>();
		listErreur = new ArrayList<Erreur>();
		try{
			listAbsencesPersonnel = absencesPersonnelRepository.findByPersonnelId(idPersonnel);
			if(listAbsencesPersonnel == null){
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(new ArrayList<AbsencesPersonnel>());
				absencesPersonnelDTO.setMessage("aucune affectation trouvee");
				absencesPersonnelDTO.setTotal(0);
				absencesPersonnelDTO.setErrors(listErreur);
			} else {
				int i = listAbsencesPersonnel.size();
				sb = new StringBuilder();
				sb.append(i).append(" sanction(s) trouvee(s) avec succes");
				absencesPersonnelDTO.setResult(true);
				absencesPersonnelDTO.setStatus(true);
				absencesPersonnelDTO.setRow(null);
				absencesPersonnelDTO.setRows(listAbsencesPersonnel);
				absencesPersonnelDTO.setMessage(sb.toString());
				absencesPersonnelDTO.setTotal(i);
				absencesPersonnelDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesPersonnelDTO.setResult(false);
			absencesPersonnelDTO.setStatus(false);
			absencesPersonnelDTO.setRow(null);
			absencesPersonnelDTO.setRows(null);
			absencesPersonnelDTO.setMessage(ex.getMessage());
			absencesPersonnelDTO.setTotal(0);
			absencesPersonnelDTO.setErrors(listErreur);
		}
		return absencesPersonnelDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) absencesPersonnelRepository.count();
	}

	@Override
	public AbsencesPersonnelDTO loadAbsencesPersonnels(Pageable pageable) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		Page<AbsencesPersonnel> page = absencesPersonnelRepository.findAll(pageable);
		absencesPersonnelDTO.setResult(true);
		absencesPersonnelDTO.setStatus(true);
		absencesPersonnelDTO.setRows(page.getContent());
		absencesPersonnelDTO.setTotal(page.getTotalElements());
		return absencesPersonnelDTO;
	}

	@Override
	public AbsencesPersonnelDTO loadAbsencesPersonnels(Pageable pageable, String nom, String prenom, String faute, String typeSanction) {
		// TODO Auto-generated method stub
		AbsencesPersonnelDTO absencesPersonnelDTO = new AbsencesPersonnelDTO();
		Page<AbsencesPersonnel> page = absencesPersonnelRepository.findByPersonnelNomContainingOrPersonnelPrenomContainingOrAbsencesFauteContaining(pageable, nom, prenom, faute, typeSanction);
		absencesPersonnelDTO.setResult(true);
		absencesPersonnelDTO.setStatus(true);
		absencesPersonnelDTO.setRows(page.getContent());
		absencesPersonnelDTO.setTotal(page.getTotalElements());
		return absencesPersonnelDTO;
	}

}
