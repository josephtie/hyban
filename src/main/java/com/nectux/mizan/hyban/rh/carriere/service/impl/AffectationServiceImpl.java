package com.nectux.mizan.hyban.rh.carriere.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.FonctionRepository;
import com.nectux.mizan.hyban.rh.carriere.entity.Affectation;
import com.nectux.mizan.hyban.rh.carriere.entity.Poste;
import com.nectux.mizan.hyban.rh.carriere.entity.Site;
import com.nectux.mizan.hyban.rh.carriere.repository.AffectationRepository;
import com.nectux.mizan.hyban.rh.carriere.repository.SiteWorkRepository;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.rh.carriere.dto.AffectationDTO;
import com.nectux.mizan.hyban.rh.carriere.repository.PosteRepository;
import com.nectux.mizan.hyban.rh.carriere.service.AffectationService;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.Erreur;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service("affectationService")
public class AffectationServiceImpl implements AffectationService {
	
	@Autowired private AffectationRepository affectationRepository;
	@Autowired private SiteWorkRepository siteWorkRepository;
	@Autowired private PersonnelRepository personneRepository;
	@Autowired private FonctionRepository posteRepository;
	
	private StringBuilder sb;
	private Erreur erreur;
	private List<Erreur> listErreur;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AffectationDTO save(Long id, Long idPersonnel, Long idPoste,Long idSite, Boolean present,String dateDebut, String dateFin, String observation) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Affectation affectation;
		listErreur = new ArrayList<Erreur>();
		try{
			if(id == null){
				affectation = new Affectation();
				affectation.setDateCreation(new Date());
			} else {
				affectation = affectationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
				affectation.setDateModification(new Date());
			}
			affectation.setObservation(observation);
			affectation.setStatut(present);
			if (Boolean.TRUE.equals(present)) {
				Affectation ancienne =
						affectationRepository.findActiveAffectationByPersonnel(idPersonnel, id);

				if (ancienne != null) {
					ancienne.setStatut(false);
					ancienne.setDateFin(new Date());
					affectationRepository.save(ancienne);
				}
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
				affectation.setPersonnel(personneRepository.findById(idPersonnel) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPersonnel)));
			
			if(idPoste == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le poste est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				affectation.setPoste(posteRepository.findById(idPoste) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idPoste)));

			if(idSite == null){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("le Site est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else
				affectation.setSite(siteWorkRepository.findById(idSite) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idSite)));



			if(dateDebut == null || dateDebut.trim().equals("")){
				sb = new StringBuilder();
				erreur = new Erreur();
				erreur.setCode(10);
				erreur.setDescription("contrainte d'integrite non null non respectee");
				sb.append("la date de debut est obligatoire");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
			} else 
				affectation.setDateDebut(Utils.stringToDate(dateDebut, "dd/MM/yyyy"));
			
			if(dateFin != null && !dateFin.trim().equals(""))
				affectation.setDateFin(Utils.stringToDate(dateFin, "dd/MM/yyyy"));
			
			if(id == null){
				if(affectationRepository.findByPersonnelIdAndPosteIdAndDateDebutAndDateFin(idPersonnel, idPoste, affectation.getDateDebut(), affectation.getDateFin()) != null){
					sb = new StringBuilder();
					erreur = new Erreur();
					erreur.setCode(10);
					erreur.setDescription("contrainte de doublon non non respectee");
					sb.append("cette affectation existe deja");
					erreur.setMessage(sb.toString());
					listErreur.add(erreur);
					
				}
			} else{
				
			}
			
			if(listErreur.isEmpty()){
				affectation = affectationRepository.save(affectation);
				sb = new StringBuilder();
				sb.append(" affectation enregistree avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				affectationDTO.setResult(false);
				affectationDTO.setStatus(false);
				affectationDTO.setRow(null);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("voir liste erreur");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}




	@Transactional
	public AffectationDTO saveNew(
			Long id,
			Long idPersonnel,
			Long idPoste,
			Long idSite,
			Boolean present,
			String dateDebut,
			String dateFin,
			String observation) {

		AffectationDTO dto = new AffectationDTO();
		List<Erreur> erreurs = new ArrayList<>();

		try {

			// =========================
			// 1. CONTRÔLES RH DE BASE
			// =========================
			if (idPersonnel == null) {
				erreurs.add(new Erreur(10,
						"contrainte d'integrite non respectee",
						"le personnel est obligatoire"));
			}

			if (idPoste == null) {
				erreurs.add(new Erreur(10,
						"contrainte d'integrite non respectee",
						"le poste est obligatoire"));
			}

			if (idSite == null) {
				erreurs.add(new Erreur(10,
						"contrainte d'integrite non respectee",
						"le site est obligatoire"));
			}

			if (dateDebut == null || dateDebut.isBlank()) {
				erreurs.add(new Erreur(10,
						"contrainte d'integrite non respectee",
						"la date de debut est obligatoire"));
			}

			if (!erreurs.isEmpty()) {
				dto.setResult(false);
				dto.setStatus(false);
				dto.setErrors(erreurs);
				dto.setMessage("voir liste erreur");
				return dto;
			}

			// =========================
			// 2. CHARGEMENT OU CRÉATION
			// =========================
			Affectation affectation;

			if (id == null) {
				affectation = new Affectation();
				affectation.setDateCreation(new Date());
			} else {
				affectation = affectationRepository.findById(id)
						.orElseThrow(() -> new EntityNotFoundException("Affectation introuvable"));
				affectation.setDateModification(new Date());
			}

			affectation.setObservation(observation);
			affectation.setStatut(Boolean.TRUE.equals(present));

			affectation.setPersonnel(
					personneRepository.findById(idPersonnel)
							.orElseThrow(() -> new EntityNotFoundException("Personnel introuvable"))
			);

			affectation.setPoste(
					posteRepository.findById(idPoste)
							.orElseThrow(() -> new EntityNotFoundException("Poste introuvable"))
			);

			affectation.setSite(
					siteWorkRepository.findById(idSite)
							.orElseThrow(() -> new EntityNotFoundException("Site introuvable"))
			);

			affectation.setDateDebut(Utils.stringToDate(dateDebut, "dd/MM/yyyy"));

			if (dateFin != null && !dateFin.isBlank()) {
				affectation.setDateFin(Utils.stringToDate(dateFin, "dd/MM/yyyy"));
			}

			// =========================
			// 3. RÈGLE RH MAJEURE
			// =========================
			// Si on crée une affectation ACTIVE
			if (Boolean.TRUE.equals(present)) {

				List<Affectation> actives =
						affectationRepository.findActiveAffectationsByPersonnel(idPersonnel);

				if (!actives.isEmpty()) {

					for (Affectation a : actives) {
						a.setStatut(false);
						a.setDateFin(new Date());
						affectationRepository.save(a);
					}
				}
			}

			// =========================
			// 4. SAUVEGARDE
			// =========================
			affectation = affectationRepository.save(affectation);

			dto.setResult(true);
			dto.setStatus(true);
			dto.setRow(affectation);
			dto.setMessage("Affectation enregistrée avec succès");
			dto.setErrors(Collections.emptyList());

		} catch (Exception ex) {
			ex.printStackTrace();
			dto.setResult(false);
			dto.setStatus(false);
			dto.setMessage(ex.getMessage());
		}

		return dto;
	}


	private Erreur erreur(String message) {
		Erreur e = new Erreur();
		e.setCode(10);
		e.setDescription("Violation règle métier RH");
		e.setMessage(message);
		return e;
	}

	private AffectationDTO dtoErreur(AffectationDTO dto, List<Erreur> erreurs) {
		dto.setResult(false);
		dto.setStatus(false);
		dto.setMessage("Validation RH échouée");
		dto.setErrors(erreurs);
		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AffectationDTO delete(Long id) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			Affectation affectation = affectationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(affectation == null){
				sb = new StringBuilder();
				erreur.setCode(12);
				erreur.setDescription("affectation inexistante");
				sb.append("cette affectation est inexistante dans le systeme");
				erreur.setMessage(sb.toString());
				listErreur.add(erreur);
				
				affectationDTO.setResult(false);
				affectationDTO.setStatus(false);
				affectationDTO.setRow(null);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("voir liste erreur");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} 
			
			if(listErreur.isEmpty()) {
				affectationRepository.delete(affectation);
				sb = new StringBuilder();
				sb.append(" affectation supprimee avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectation(Long id) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Affectation affectation;
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			affectation = affectationRepository.findById(id) .orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			if(affectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("affectation inexistante dans le systeme");
				affectationDTO.setTotal(1);
				affectationDTO.setErrors(listErreur);
			} else {
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(affectation);
				affectationDTO.setRows(null);
				affectationDTO.setMessage("affectation trouvee avec succes");
				affectationDTO.setTotal(1);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectations() {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listAffectation = affectationRepository.findAll();
			if(listAffectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(new ArrayList<Affectation>());
				affectationDTO.setMessage("aucune affectation trouvee");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				int i = listAffectation.size();
				sb = new StringBuilder();
				sb.append(i).append(" affectation(s) trouvee(s) avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(listAffectation);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(i);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectationsByPersonnel(Long idPersonnel) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listAffectation = affectationRepository.findByPersonnelId(idPersonnel);
			if(listAffectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(new ArrayList<Affectation>());
				affectationDTO.setMessage("aucune affectation trouvee");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				int i = listAffectation.size();
				sb = new StringBuilder();
				sb.append(i).append(" affectation(s) trouvee(s) avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(listAffectation);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(i);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public AffectationDTO findAffectationsByPoste(Long idPoste) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		erreur = new Erreur();
		listErreur = new ArrayList<Erreur>();
		try{
			listAffectation = affectationRepository.findByPosteId(idPoste);
			if(listAffectation == null){
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(new ArrayList<Affectation>());
				affectationDTO.setMessage("aucune affectation trouvee");
				affectationDTO.setTotal(0);
				affectationDTO.setErrors(listErreur);
			} else {
				int i = listAffectation.size();
				sb = new StringBuilder();
				sb.append(i).append(" affectation(s) trouvee(s) avec succes");
				affectationDTO.setResult(true);
				affectationDTO.setStatus(true);
				affectationDTO.setRow(null);
				affectationDTO.setRows(listAffectation);
				affectationDTO.setMessage(sb.toString());
				affectationDTO.setTotal(i);
				affectationDTO.setErrors(listErreur);
			}
		} catch(Exception ex){
			erreur.setCode(20);
			erreur.setDescription("exception capturee");
			listErreur.add(erreur);
			ex.printStackTrace();
			affectationDTO.setResult(false);
			affectationDTO.setStatus(false);
			affectationDTO.setRow(null);
			affectationDTO.setRows(null);
			affectationDTO.setMessage(ex.getMessage());
			affectationDTO.setTotal(0);
			affectationDTO.setErrors(listErreur);
		}
		return affectationDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) affectationRepository.count();
	}

	@Override
	public AffectationDTO loadAffectations(Pageable pageable) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Page<Affectation> page = affectationRepository.findAll(pageable);
		affectationDTO.setResult(true);
		affectationDTO.setStatus(true);
		affectationDTO.setRows(page.getContent());
		affectationDTO.setTotal(page.getTotalElements());
		return affectationDTO;
	}

	@Override
	public AffectationDTO loadAffectations(Pageable pageable, String nom, String prenom, String poste) {
		// TODO Auto-generated method stub
		AffectationDTO affectationDTO = new AffectationDTO();
		Page<Affectation> page = affectationRepository.findByPersonnelNomContainingOrPersonnelPrenomContainingOrPosteLibelleContaining(pageable, nom, prenom, poste);
		affectationDTO.setResult(true);
		affectationDTO.setStatus(true);
		affectationDTO.setRows(page.getContent());
		affectationDTO.setTotal(page.getTotalElements());
		return affectationDTO;
	}

}
