package com.nectux.mizan.hyban.rh.absences.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.absences.dto.AbsencesDTO;
import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import com.nectux.mizan.hyban.rh.absences.repository.AbsencesRepository;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.rh.absences.service.AbsencesService;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("absencesService")
public class AbsencesServiceImpl implements AbsencesService {
	
	@Autowired private AbsencesRepository absenceRepository;
	//@Autowired private TypeSanctionRepository typeSanctionRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AbsencesDTO save(Long id, String faute, String type,String commentaire) {
		// TODO Auto-generated method stub
		AbsencesDTO absencesDTO = new AbsencesDTO();
		Absences absence;
		listErreur = new ArrayList<Erreur>();
		try{
			if(id == null){
				absence = new Absences();
				absence.setDateCreation(new Date());
			} else {
				absence = absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				absence.setDateModification(new Date());
			}
			absence.setFaute(faute);
			absence.setType(type);
			absence.setCommentaire(commentaire);
			
			if(faute == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la faute est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} 
			
			/*if(idTypeSanction == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le type de absence est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else{
				absence.setTypeSanction(typeSanctionRepository.findOne(idTypeSanction));
	     	}*/
			/*if(id == null){
				if(absenceRepository.findByFaute(absence.getFaute()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("cette absence existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			} else {
				if(absenceRepository.findByIdNotAndFaute(id, absence.getFaute()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non respecte");
					sb.append("cette absence existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
				}
			}*/
			
			if(listErreur.isEmpty()){
				absence = absenceRepository.save(absence);
				sb = new StringBuilder();
				sb.append(" absence enregistree avec succes");
				absencesDTO.setResult(true);
				absencesDTO.setStatus(true);
				absencesDTO.setRow(absence);
				absencesDTO.setRows(null);
				absencesDTO.setMessage(sb.toString());
				absencesDTO.setTotal(0);
				absencesDTO.setErrors(listErreur);
			} else {
				absencesDTO.setResult(false);
				absencesDTO.setStatus(false);
				absencesDTO.setRow(null);
				absencesDTO.setRows(null);
				absencesDTO.setMessage("voir liste erreur");
				absencesDTO.setTotal(0);
				absencesDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesDTO.setResult(false);
			absencesDTO.setStatus(false);
			absencesDTO.setRow(null);
			absencesDTO.setRows(null);
			absencesDTO.setMessage(ex.getMessage());
			absencesDTO.setTotal(0);
			absencesDTO.setErrors(listErreur);
		}
		return absencesDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AbsencesDTO delete(Long id) {
		// TODO Auto-generated method stub
		AbsencesDTO absencesDTO = new AbsencesDTO();
		Absences absence;
		listErreur = new ArrayList<Erreur>();
		try{
			absence = absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(absence == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("absence inexistante");
				sb.append("cette absence est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				absencesDTO.setResult(false);
				absencesDTO.setStatus(false);
				absencesDTO.setRow(null);
				absencesDTO.setRows(null);
				absencesDTO.setMessage("voir liste erreur");
				absencesDTO.setTotal(0);
				absencesDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				absenceRepository.delete(absence);
				sb = new StringBuilder();
				sb.append(" absence supprimee avec succes");
				absencesDTO.setResult(true);
				absencesDTO.setStatus(true);
				absencesDTO.setRow(absence);
				absencesDTO.setRows(null);
				absencesDTO.setMessage(sb.toString());
				absencesDTO.setTotal(0);
				absencesDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesDTO.setResult(false);
			absencesDTO.setStatus(false);
			absencesDTO.setRow(null);
			absencesDTO.setRows(null);
			absencesDTO.setMessage(ex.getMessage());
			absencesDTO.setTotal(0);
			absencesDTO.setErrors(listErreur);
		}
		return absencesDTO;
	}

	@Override
	public AbsencesDTO findAbsence(Long id) {
		// TODO Auto-generated method stub
		AbsencesDTO absencesDTO = new AbsencesDTO();
		Absences absence;
		listErreur = new ArrayList<Erreur>();
		try{
			absence = absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(absence == null){
				absencesDTO.setResult(true);
				absencesDTO.setStatus(true);
				absencesDTO.setRow(absence);
				absencesDTO.setRows(null);
				absencesDTO.setMessage("absence inexistante dans le systeme");
				absencesDTO.setTotal(1);
				absencesDTO.setErrors(listErreur);
			} else {
				absencesDTO.setResult(true);
				absencesDTO.setStatus(true);
				absencesDTO.setRow(absence);
				absencesDTO.setRows(null);
				absencesDTO.setMessage("absence trouvee avec succes");
				absencesDTO.setTotal(1);
				absencesDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesDTO.setResult(false);
			absencesDTO.setStatus(false);
			absencesDTO.setRow(null);
			absencesDTO.setRows(null);
			absencesDTO.setMessage(ex.getMessage());
			absencesDTO.setTotal(0);
			absencesDTO.setErrors(listErreur);
		}
		return absencesDTO;
	}

	@Override
	public AbsencesDTO findAbsences() {
		// TODO Auto-generated method stub
		AbsencesDTO absencesDTO = new AbsencesDTO();
		List<Absences> listSanction = new ArrayList<Absences>();
		listErreur = new ArrayList<Erreur>();
		try{
			listSanction = absenceRepository.findAll();
			if(listSanction == null){
				absencesDTO.setResult(true);
				absencesDTO.setStatus(true);
				absencesDTO.setRow(null);
				absencesDTO.setRows(new ArrayList<Absences>());
				absencesDTO.setMessage("aucune absence trouvee");
				absencesDTO.setTotal(0);
				absencesDTO.setErrors(listErreur);
			} else {
				int i = listSanction.size();
				sb = new StringBuilder();
				sb.append(i).append(" absence(s) trouvee(s) avec succes");
				absencesDTO.setResult(true);
				absencesDTO.setStatus(true);
				absencesDTO.setRow(null);
				absencesDTO.setRows(listSanction);
				absencesDTO.setMessage(sb.toString());
				absencesDTO.setTotal(i);
				absencesDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur = new Erreur();
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			absencesDTO.setResult(false);
			absencesDTO.setStatus(false);
			absencesDTO.setRow(null);
			absencesDTO.setRows(null);
			absencesDTO.setMessage(ex.getMessage());
			absencesDTO.setTotal(0);
			absencesDTO.setErrors(listErreur);
		}
		return absencesDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) absenceRepository.count();
	}

	@Override
	public AbsencesDTO loadAbsences(Pageable pageable) {
		// TODO Auto-generated method stub
		AbsencesDTO absencesDTO = new AbsencesDTO();
		Page<Absences> page = absenceRepository.findAll(pageable);
		absencesDTO.setResult(true);
		absencesDTO.setStatus(true);
		absencesDTO.setRows(page.getContent());
		absencesDTO.setTotal(page.getTotalElements());
		return absencesDTO;
	}

	@Override
	public AbsencesDTO loadAbsences(Pageable pageable,  String faute) {
		// TODO Auto-generated method stub
		AbsencesDTO absencesDTO = new AbsencesDTO();
		Page<Absences> page = absenceRepository.findByFauteContaining(pageable, faute);
		absencesDTO.setResult(true);
		absencesDTO.setStatus(true);
		absencesDTO.setRows(page.getContent());
		absencesDTO.setTotal(page.getTotalElements());
		return absencesDTO;
	}

    @Override
    public List<Absences> getAbsences() {
        return absenceRepository.findAll();
    }

	@Override
	public Absences saver(String faute, String commentaire) {
		// TODO Auto-generated method stub
		Absences absences = new Absences();
		absences.setFaute(faute);
		absences.setCommentaire(commentaire);
		return absenceRepository.save(absences);
	}

}
