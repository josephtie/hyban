package com.nectux.mizan.hyban.personnel.repository;


import java.sql.Date;
import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.TypeContrat;
import com.nectux.mizan.hyban.personnel.entity.Categorie;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Personnel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface ContratPersonnelRepository extends CrudRepository<ContratPersonnel, Long> {
	
	public List<ContratPersonnel> findAll();
	
	public ContratPersonnel findByPersonnelId(Long idPers);
	
	//public List<ContratPersonnel> findByPersonnelId(Long idPers);
	
	public ContratPersonnel findByPersonnelMatricule(String mat);
	
	public ContratPersonnel findByPersonnelMatriculeAndStatut(String mat, Boolean val);
	
	public Page<ContratPersonnel> findByStatut(Boolean val, Pageable pageable);
	
	public Page<ContratPersonnel> findByStatutAndPersonnelMatriculeIgnoreCaseContainingOrPersonnelNomIgnoreCaseContaining(Pageable pageable,Boolean val, String matricule,String matricule1);

	public ContratPersonnel findByPersonnelIdAndStatut(Long idlong, Boolean val);
	public List<ContratPersonnel> findByStatut( Boolean val);

	public List<ContratPersonnel> findByPersonnel(Personnel personnel);
	
	public List<ContratPersonnel> findByTypeContrat(TypeContrat typeContrat);
	
	//public ContratPersonnel findByTypeContratIdAndStatutAndPersonnelId(TypeContrat typeContrat);
	
	public List<ContratPersonnel> findByCategorie(Categorie categorie);
	
	public List<ContratPersonnel> findByFonction(Fonction fonction);

	public Page<ContratPersonnel> findByPersonnel(Personnel personnel, Pageable pageable);

	public Page<ContratPersonnel> findByStatut(Pageable pageable, Boolean statut);
	
	public ContratPersonnel findByIdAndStatut(Long idlong, Boolean statut);
	
//	public Page<ContratPersonnel> findByStatut(Pageable pageable, Boolean statut);
	
	public Page<ContratPersonnel> findByTypeContratIdAndDateFinBetween(Pageable pageable,Long Idctr, Date  ddep, Date dfin );
	
	
/*	public final static String findByTypeContratIddate = "select u from ContratPersonnel u" +            
            " where u.dateFin >= :dateDeb  and u.typeContrat.id= :idopcom  ";

	@Query(findByTypeContratIddate)
	public List<ContratPersonnel> RechContratIddateExpired(Pageable pageable,@Param("dateDeb") Date dateDeb,@Param("idopcom") Long idopcom);*/
	
	public Page<ContratPersonnel> findAll(Pageable pageable);

	public List<ContratPersonnel> findByStatutTrue(); 
	
/*	public final static String find_Bulletin_Personnel = "select p from ContratPersonnel p, Personnel u  " + 
            " where p.personnel.id = u.id " +
            " and p.statut= true  "+
            " and u.id = :idPers ";

	@Query(find_Bulletin_Personnel)
	public ContratPersonnel findByContratPersonnelactif(@Param("idPers") Long idPers);*/

	
	//public ContratPersonnel findByPersonnelIdAndStatut(Long idPers,Boolean etat);
	
	public List<ContratPersonnel> findByDateFinBetween(java.util.Date dateDebut, java.util.Date  dateFin);
	public List<ContratPersonnel> findByStatutTrueAndDepartFalseOrderByPersonnelNomAscPersonnelPrenomAsc();
	public Page<ContratPersonnel> findByStatutTrueAndDepartFalseOrderByPersonnelNomAscPersonnelPrenomAsc(Pageable pageable);

	public List<ContratPersonnel> findByStatutTrueAndDepartFalseAndPersonnelNomIgnoreCaseContaining(String search);

  //  List<ContratPersonnel> findByTypeContratIdAndStatutTrue(Long id);
}

