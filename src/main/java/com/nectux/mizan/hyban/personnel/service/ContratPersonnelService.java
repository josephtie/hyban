package com.nectux.mizan.hyban.personnel.service;

import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ContratPersonnelService {
	
	public ContratPersonnel save(ContratPersonnel contratPersonnel);
	
	public ContratPersonnelDTO save(Long id, Long idPersonnel, Long idCategorie, Long idFonction, Long idTypeContrat, String dateDebut, String dateFin, Double netAPayer, Double indemniteLogement, int ancienete, boolean statut, Double sursalaire, Double indemnitetransport, Double indemniterespons, Double indemniterepresent);
	
	public ContratPersonnelDTO endContract(Long id, String dateFin,Boolean depart,String ObservCtrat);
	
	public ContratPersonnelDTO updateContractSursalaire(Long id, Double sursalaire);
	
	
	public Boolean delete(Long id);
	
	public ContratPersonnel findContratPersonnel(Long id);
	
	public ContratPersonnelDTO findContratPersonnelk(Long id);
	
	public List<ContratPersonnel> findByPersonnel(Personnel personnel);
	
	public ContratPersonnel findByPersonnelContratActif(Long idPers);
	
	public List<ContratPersonnel> findByTypeContrat(TypeContrat typeContrat);
	
	public List<ContratPersonnel> findByCategorie(Categorie categorie);
	
	public List<ContratPersonnel> findByFonction(Fonction fonction);
	
	public List<ContratPersonnel> findContratPersonnels();
	
	public List<ContratPersonnel> findExpireContract() throws Exception;
	
	public List<ContratPersonnel> findExpireContract(int delay) throws Exception;
	
	public int count();

	public ContratPersonnelDTO loadContratActif(Pageable pageable);
	
	public ContratPersonnelDTO loadContratExpieredumois(Pageable pageable,Long IdTypctr,String  ddeb,String  dfin );

	public ContratPersonnelDTO loadContratActif(Pageable pageable, String search);

	public ContratPersonnelDTO loadContratByPersonnel(Personnel personnel, Pageable pageable);
	
	

	public ContratPersonnelDTO loadContratByPersonnel(Personnel personnel, Pageable pageable, String search);

	
	public ContratPersonnelDTO loadContratPersonnelActif(Pageable pageable);
	
	public ContratPersonnelDTO loadContratPersonnelActif( Pageable pageable, String search,String search1);


    List<ContratPersonnel> rechercherBytypeContrat(TypeContrat annee);
}
