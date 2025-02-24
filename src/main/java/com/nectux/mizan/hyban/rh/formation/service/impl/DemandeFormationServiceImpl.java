package com.nectux.mizan.hyban.rh.formation.service.impl;

import com.nectux.mizan.hyban.parametrages.repository.ExerciceRepository;
import com.nectux.mizan.hyban.personnel.repository.ServiceRepository;
import com.nectux.mizan.hyban.rh.formation.dto.DemandeFormationDTO;
import com.nectux.mizan.hyban.rh.formation.entity.DemandeFormation;
import com.nectux.mizan.hyban.rh.formation.repository.DemandeFormationRepository;
import com.nectux.mizan.hyban.rh.formation.service.DemandeFormationService;
import com.nectux.mizan.hyban.utils.DateManager;
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
@Service("demandeFormationService")
public class DemandeFormationServiceImpl implements DemandeFormationService {
	
	@Autowired
    private ServiceRepository serviceRepository;
	@Autowired
    private DemandeFormationRepository demandeFormationRepository;
	@Autowired
    private ExerciceRepository exerciceRepository;

	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DemandeFormationDTO save(Long id, Long idService,Long idAnnee, String objet, String commentaire, String dateDemande) {
		// TODO Auto-generated method stub
		
		DemandeFormationDTO demandeFormationDTO = new DemandeFormationDTO();
		DemandeFormation demandeFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			if(id == null){
				demandeFormation = new DemandeFormation();
				demandeFormation.setDateCreation(new Date());
			} else {
				demandeFormation = demandeFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				demandeFormation.setDateModification(new Date());
			}
			
			demandeFormation.setEtatDde(1);
			demandeFormation.setObjet(objet);
			demandeFormation.setCommentaire(commentaire);
			demandeFormation.setDateDemande(DateManager.stringToDate(dateDemande, "dd/MM/yyyy"));
			demandeFormation.setService(serviceRepository.findById(idService) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idService)));
			demandeFormation.setExercice(exerciceRepository.findById(idAnnee) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idAnnee)));

			if(listErreur.isEmpty()){
				demandeFormation = demandeFormationRepository.save(demandeFormation);
				sb = new StringBuilder();
				sb.append(demandeFormation.getObjet()).append(" enregistre avec succes");
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(demandeFormation);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage(sb.toString());
				demandeFormationDTO.setTotal(0);
				demandeFormationDTO.setErrors(listErreur);
			} else {
				demandeFormationDTO.setResult(false);
				demandeFormationDTO.setStatus(false);
				demandeFormationDTO.setRow(null);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage("voir liste erreur");
				demandeFormationDTO.setTotal(0);
				demandeFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			demandeFormationDTO.setResult(false);
			demandeFormationDTO.setStatus(false);
			demandeFormationDTO.setRow(null);
			demandeFormationDTO.setRows(null);
			demandeFormationDTO.setMessage(ex.getMessage());
			demandeFormationDTO.setTotal(0);
			demandeFormationDTO.setErrors(listErreur);
		}
		return demandeFormationDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DemandeFormationDTO delete(Long id) {
		// TODO Auto-generated method stub
		
		DemandeFormationDTO demandeFormationDTO = new DemandeFormationDTO();
		DemandeFormation demandeFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			demandeFormation = demandeFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(demandeFormation == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("demande de formation inexistante");
				sb.append("cette demande de formation est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				demandeFormationDTO.setResult(false);
				demandeFormationDTO.setStatus(false);
				demandeFormationDTO.setRow(null);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage("voir liste erreur");
				demandeFormationDTO.setTotal(0);
				demandeFormationDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				demandeFormationRepository.delete(demandeFormation);
				sb = new StringBuilder();
				sb.append(demandeFormation.getObjet()).append(" supprime avec succes");
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(demandeFormation);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage(sb.toString());
				demandeFormationDTO.setTotal(0);
				demandeFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			demandeFormationDTO.setResult(false);
			demandeFormationDTO.setStatus(false);
			demandeFormationDTO.setRow(null);
			demandeFormationDTO.setRows(null);
			demandeFormationDTO.setMessage(ex.getMessage());
			demandeFormationDTO.setTotal(0);
			demandeFormationDTO.setErrors(listErreur);
		}
		return demandeFormationDTO;
	}

	@Override
	public DemandeFormationDTO findDemandeFormation(Long id) {
		// TODO Auto-generated method stub
		
		DemandeFormationDTO demandeFormationDTO = new DemandeFormationDTO();
		DemandeFormation demandeFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{
			demandeFormation = demandeFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(demandeFormation == null){
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(demandeFormation);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage("demande de formation inexistante dans le systeme");
				demandeFormationDTO.setTotal(1);
				demandeFormationDTO.setErrors(listErreur);
			} else {
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(demandeFormation);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage("demande de formation trouve avec succes");
				demandeFormationDTO.setTotal(1);
				demandeFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			demandeFormationDTO.setResult(false);
			demandeFormationDTO.setStatus(false);
			demandeFormationDTO.setRow(null);
			demandeFormationDTO.setRows(null);
			demandeFormationDTO.setMessage(ex.getMessage());
			demandeFormationDTO.setTotal(0);
			demandeFormationDTO.setErrors(listErreur);
		}
		return demandeFormationDTO;
	}
	@Override
	@Transactional
	public DemandeFormationDTO findDemandeFormationValide(Long id, String dateValide,Integer etat) {
		// TODO Auto-generated method stub

		DemandeFormationDTO demandeFormationDTO = new DemandeFormationDTO();
		DemandeFormation demandeFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();

		try{
			demandeFormation = demandeFormationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(demandeFormation == null){
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(demandeFormation);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage("demande de formation inexistante dans le systeme");
				demandeFormationDTO.setTotal(1);
				demandeFormationDTO.setErrors(listErreur);
			} else {
				demandeFormation.setDateValidation(DateManager.stringToDate(dateValide, "dd/MM/yyyy"));
				demandeFormation.setEtatDde(etat);
				demandeFormation=demandeFormationRepository.save(demandeFormation);
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(demandeFormation);
				demandeFormationDTO.setRows(null);
				demandeFormationDTO.setMessage("demande de formation valider avec succes");
				demandeFormationDTO.setTotal(1);
				demandeFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			demandeFormationDTO.setResult(false);
			demandeFormationDTO.setStatus(false);
			demandeFormationDTO.setRow(null);
			demandeFormationDTO.setRows(null);
			demandeFormationDTO.setMessage(ex.getMessage());
			demandeFormationDTO.setTotal(0);
			demandeFormationDTO.setErrors(listErreur);
		}
		return demandeFormationDTO;
	}

	@Override
	public DemandeFormationDTO findDemandeFormations() {
		// TODO Auto-generated method stub
		
		DemandeFormationDTO demandeFormationDTO = new DemandeFormationDTO();
		List<DemandeFormation> listDemandeFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		
		try{ 
			listDemandeFormation = demandeFormationRepository.findAll();
			if(listDemandeFormation == null){
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(null);
				demandeFormationDTO.setRows(new ArrayList<DemandeFormation>());
				demandeFormationDTO.setMessage("aucune demande de formation trouve");
				demandeFormationDTO.setTotal(0);
				demandeFormationDTO.setErrors(listErreur);
			} else {
				int i = listDemandeFormation.size();
				sb = new StringBuilder();
				sb.append(i).append(" demande(s) de formation trouvee(s) avec succes");
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(null);
				demandeFormationDTO.setRows(listDemandeFormation);
				demandeFormationDTO.setMessage(sb.toString());
				demandeFormationDTO.setTotal(i);
				demandeFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			demandeFormationDTO.setResult(false);
			demandeFormationDTO.setStatus(false);
			demandeFormationDTO.setRow(null);
			demandeFormationDTO.setRows(null);
			demandeFormationDTO.setMessage(ex.getMessage());
			demandeFormationDTO.setTotal(0);
			demandeFormationDTO.setErrors(listErreur);
		}
		return demandeFormationDTO;
	}



	@Override
	public DemandeFormationDTO findDemandeFormationsValiid() {
		// TODO Auto-generated method stub

		DemandeFormationDTO demandeFormationDTO = new DemandeFormationDTO();
		List<DemandeFormation> listDemandeFormation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();

		try{
			listDemandeFormation = demandeFormationRepository.findAllByEtatDde(3);
			if(listDemandeFormation == null){
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(null);
				demandeFormationDTO.setRows(new ArrayList<DemandeFormation>());
				demandeFormationDTO.setMessage("aucune demande de formation trouve");
				demandeFormationDTO.setTotal(0);
				demandeFormationDTO.setErrors(listErreur);
			} else {
				int i = listDemandeFormation.size();
				sb = new StringBuilder();
				sb.append(i).append(" demande(s) de formation trouvee(s) avec succes");
				demandeFormationDTO.setResult(true);
				demandeFormationDTO.setStatus(true);
				demandeFormationDTO.setRow(null);
				demandeFormationDTO.setRows(listDemandeFormation);
				demandeFormationDTO.setMessage(sb.toString());
				demandeFormationDTO.setTotal(i);
				demandeFormationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			demandeFormationDTO.setResult(false);
			demandeFormationDTO.setStatus(false);
			demandeFormationDTO.setRow(null);
			demandeFormationDTO.setRows(null);
			demandeFormationDTO.setMessage(ex.getMessage());
			demandeFormationDTO.setTotal(0);
			demandeFormationDTO.setErrors(listErreur);
		}
		return demandeFormationDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) demandeFormationRepository.count();
	}

	@Override
	public DemandeFormationDTO loadDemandeFormations(Pageable pageable) {
		// TODO Auto-generated method stub
		DemandeFormationDTO factureFormationDTO = new DemandeFormationDTO();
		Page<DemandeFormation> page = demandeFormationRepository.findAll(pageable);
		factureFormationDTO.setResult(true);
		factureFormationDTO.setStatus(true);
		factureFormationDTO.setRows(page.getContent());
		factureFormationDTO.setTotal(page.getTotalElements());
		return factureFormationDTO;
	}
	@Override
	public DemandeFormationDTO loadDemandeFormationsValid(Pageable pageable,Integer etat) {
		// TODO Auto-generated method stub
		DemandeFormationDTO factureFormationDTO = new DemandeFormationDTO();
		Page<DemandeFormation> page = demandeFormationRepository.findAllByEtatDde(pageable,1);
		factureFormationDTO.setResult(true);
		factureFormationDTO.setStatus(true);
		factureFormationDTO.setRows(page.getContent());
		factureFormationDTO.setTotal(page.getTotalElements());
		return factureFormationDTO;
	}

	@Override
	public DemandeFormationDTO loadDemandeFormations(Pageable pageable, String objet, String serviceLibelle,			String commentaire) {
		// TODO Auto-generated method stub
		DemandeFormationDTO factureFormationDTO = new DemandeFormationDTO();
		Page<DemandeFormation> page = demandeFormationRepository.findByObjetContainingOrServiceLibelleContainingOrCommentaireContaining(pageable, objet, serviceLibelle, commentaire);
		factureFormationDTO.setResult(true);
		factureFormationDTO.setStatus(true);
		factureFormationDTO.setRows(page.getContent());
		factureFormationDTO.setTotal(page.getTotalElements());
		return factureFormationDTO;
	}
@Override
	public DemandeFormationDTO loadDemandeFormationsValid(Pageable pageable,Integer etat ,String objet, String serviceLibelle,
			String commentaire) {
		// TODO Auto-generated method stub
		DemandeFormationDTO factureFormationDTO = new DemandeFormationDTO();
		Page<DemandeFormation> page = demandeFormationRepository.findByEtatDdeAndObjetContainingOrServiceLibelleContainingOrCommentaireContaining(pageable,etat ,objet, serviceLibelle, commentaire);
		factureFormationDTO.setResult(true);
		factureFormationDTO.setStatus(true);
		factureFormationDTO.setRows(page.getContent());
		factureFormationDTO.setTotal(page.getTotalElements());
		return factureFormationDTO;
	}

}
