package com.nectux.mizan.hyban.rh.formation.service.impl;

import com.nectux.mizan.hyban.rh.formation.dto.CabinetFormationDTO;
import com.nectux.mizan.hyban.rh.formation.entity.CabinetFormation;
import com.nectux.mizan.hyban.rh.formation.repository.CabinetFormationRepository;
import com.nectux.mizan.hyban.rh.formation.service.CabinetFormationService;
import com.nectux.mizan.hyban.utils.Erreur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service("cabinetFormationService")
public class CabinetFormationServiceImpl implements CabinetFormationService {
	
	@Autowired
    private CabinetFormationRepository cabinetFormationRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CabinetFormationDTO save(Long id, String nom, String adresse, String email, String telephone) {
		// TODO Auto-generated method stub
		
		CabinetFormationDTO cabinetFormationDTO = new CabinetFormationDTO();
		CabinetFormation cabinetFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			if(id == null){
				cabinetFormation = new CabinetFormation();
				cabinetFormation.setDateCreation(new Date());
			} else {
				cabinetFormation = cabinetFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				cabinetFormation.setDateModification(new Date());
			}
			
			if(nom == null || nom.trim().equals("")){
				sb = new StringBuilder();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le nom est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else {
				cabinetFormation.setNom(nom);
				if(id == null){
					
					if(cabinetFormationRepository.findByNom(nom) != null){
						sb = new StringBuilder();
						erreur.setCode(10);
						erreur.setDescription("contrainte de doublon non respecte");
						sb.append("ce poste existe deja");
						erreur.setMessage(sb.toString());
						listErreur.add(erreur);
					}
				} else {
					if(cabinetFormationRepository.findByIdNotAndNom(id, nom) != null){
						sb = new StringBuilder();
						erreur.setCode(10);
						erreur.setDescription("contrainte de doublon non respecte");
						sb.append("ce cabinet de formation existe deja");
						erreur.setMessage(sb.toString());
						listErreur.add(erreur);
					}
				}
			}
			
			cabinetFormation.setAdresse(adresse);
			cabinetFormation.setEmail(email);
			cabinetFormation.setTelephone(telephone);
			
			if(listErreur.isEmpty()){
				cabinetFormation = cabinetFormationRepository.save(cabinetFormation);
				sb = new StringBuilder();
				sb.append(cabinetFormation.getNom()).append(" enregistre avec succes");
				cabinetFormationDTO.setResult(true);
				cabinetFormationDTO.setStatus(true);
				cabinetFormationDTO.setRow(cabinetFormation);
				cabinetFormationDTO.setRows(null);
				cabinetFormationDTO.setMessage(sb.toString());
				cabinetFormationDTO.setTotal(0);
				cabinetFormationDTO.setErrors(listErreur);
			} else {
				cabinetFormationDTO.setResult(false);
				cabinetFormationDTO.setStatus(false);
				cabinetFormationDTO.setRow(null);
				cabinetFormationDTO.setRows(null);
				cabinetFormationDTO.setMessage("voir liste erreur");
				cabinetFormationDTO.setTotal(0);
				cabinetFormationDTO.setErrors(listErreur);
			}
			
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			cabinetFormationDTO.setResult(false);
			cabinetFormationDTO.setStatus(false);
			cabinetFormationDTO.setRow(null);
			cabinetFormationDTO.setRows(null);
			cabinetFormationDTO.setMessage(ex.getMessage());
			cabinetFormationDTO.setTotal(0);
			cabinetFormationDTO.setErrors(listErreur);
		}
		return cabinetFormationDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CabinetFormationDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		CabinetFormationDTO cabinetFormationDTO = new CabinetFormationDTO();
		CabinetFormation cabinetFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			cabinetFormation = cabinetFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(cabinetFormation == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("poste inexistant");
				sb.append("ce cabinet de formation est inexistant dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				cabinetFormationDTO.setResult(false);
				cabinetFormationDTO.setStatus(false);
				cabinetFormationDTO.setRow(null);
				cabinetFormationDTO.setRows(null);
				cabinetFormationDTO.setMessage("voir liste erreur");
				cabinetFormationDTO.setTotal(0);
				cabinetFormationDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				cabinetFormationRepository.delete(cabinetFormation);
				sb = new StringBuilder();
				sb.append(cabinetFormation.getNom()).append(" supprime avec succes");
				cabinetFormationDTO.setResult(true);
				cabinetFormationDTO.setStatus(true);
				cabinetFormationDTO.setRow(cabinetFormation);
				cabinetFormationDTO.setRows(null);
				cabinetFormationDTO.setMessage(sb.toString());
				cabinetFormationDTO.setTotal(0);
				cabinetFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			cabinetFormationDTO.setResult(false);
			cabinetFormationDTO.setStatus(false);
			cabinetFormationDTO.setRow(null);
			cabinetFormationDTO.setRows(null);
			cabinetFormationDTO.setMessage(ex.getMessage());
			cabinetFormationDTO.setTotal(0);
			cabinetFormationDTO.setErrors(listErreur);
		}
		return cabinetFormationDTO;
	}

	@Override
	public CabinetFormationDTO findCabinetFormation(Long id) {
		// TODO Auto-generated method stub
		
		CabinetFormationDTO cabinetFormationDTO = new CabinetFormationDTO();
		CabinetFormation cabinetFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			cabinetFormation = cabinetFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(cabinetFormation == null){
				cabinetFormationDTO.setResult(true);
				cabinetFormationDTO.setStatus(true);
				cabinetFormationDTO.setRow(cabinetFormation);
				cabinetFormationDTO.setRows(null);
				cabinetFormationDTO.setMessage("carnet de formation inexistant dans le systeme");
				cabinetFormationDTO.setTotal(1);
				cabinetFormationDTO.setErrors(listErreur);
			} else {
				cabinetFormationDTO.setResult(true);
				cabinetFormationDTO.setStatus(true);
				cabinetFormationDTO.setRow(cabinetFormation);
				cabinetFormationDTO.setRows(null);
				cabinetFormationDTO.setMessage("carnet de formation trouve avec succes");
				cabinetFormationDTO.setTotal(1);
				cabinetFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			cabinetFormationDTO.setResult(false);
			cabinetFormationDTO.setStatus(false);
			cabinetFormationDTO.setRow(null);
			cabinetFormationDTO.setRows(null);
			cabinetFormationDTO.setMessage(ex.getMessage());
			cabinetFormationDTO.setTotal(0);
			cabinetFormationDTO.setErrors(listErreur);
		}
		return cabinetFormationDTO;
	}

	@Override
	public CabinetFormationDTO findCabinetFormations() {
		// TODO Auto-generated method stub
		
		CabinetFormationDTO cabinetFormationDTO = new CabinetFormationDTO();
		List<CabinetFormation> listCabinetFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			listCabinetFormation = cabinetFormationRepository.findAll();
			if(listCabinetFormation == null){
				cabinetFormationDTO.setResult(true);
				cabinetFormationDTO.setStatus(true);
				cabinetFormationDTO.setRow(null);
				cabinetFormationDTO.setRows(new ArrayList<CabinetFormation>());
				cabinetFormationDTO.setMessage("aucun cabinet de formation trouve");
				cabinetFormationDTO.setTotal(0);
				cabinetFormationDTO.setErrors(listErreur);
			} else {
				int i = listCabinetFormation.size();
				sb = new StringBuilder();
				sb.append(i).append(" cabinet(s) de formation trouve(s) avec succes");
				cabinetFormationDTO.setResult(true);
				cabinetFormationDTO.setStatus(true);
				cabinetFormationDTO.setRow(null);
				cabinetFormationDTO.setRows(listCabinetFormation);
				cabinetFormationDTO.setMessage(sb.toString());
				cabinetFormationDTO.setTotal(i);
				cabinetFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			cabinetFormationDTO.setResult(false);
			cabinetFormationDTO.setStatus(false);
			cabinetFormationDTO.setRow(null);
			cabinetFormationDTO.setRows(null);
			cabinetFormationDTO.setMessage(ex.getMessage());
			cabinetFormationDTO.setTotal(0);
			cabinetFormationDTO.setErrors(listErreur);
		}
		return cabinetFormationDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) cabinetFormationRepository.count();
	}

	@Override
	public CabinetFormationDTO loadCabinetFormations(Pageable pageable) {
		// TODO Auto-generated method stub
		CabinetFormationDTO cabinetFormationDTO = new CabinetFormationDTO();
		Page<CabinetFormation> page = cabinetFormationRepository.findAll(pageable);
		cabinetFormationDTO.setResult(true);
		cabinetFormationDTO.setStatus(true);
		cabinetFormationDTO.setRows(page.getContent());
		cabinetFormationDTO.setTotal(page.getTotalElements());
		return cabinetFormationDTO;
	}

	@Override
	public CabinetFormationDTO loadCabinetFormations(Pageable pageable, String nom, String adresse, String email, String telephone) {
		// TODO Auto-generated method stub
		CabinetFormationDTO cabinetFormationDTO = new CabinetFormationDTO();
		Page<CabinetFormation> page = cabinetFormationRepository.findByNomContainingOrAdresseContainingOrEmailContainingOrTelephoneContaining(pageable, nom, adresse, email, telephone);
		cabinetFormationDTO.setResult(true);
		cabinetFormationDTO.setStatus(true);
		cabinetFormationDTO.setRows(page.getContent());
		cabinetFormationDTO.setTotal(page.getTotalElements());
		return cabinetFormationDTO;
	}

}
