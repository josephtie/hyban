package com.nectux.mizan.hyban.parametrages.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.PlanningConge;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import org.springframework.data.repository.query.Param;

public interface PlanningCongeRepository extends CrudRepository<PlanningConge, Long> {

	public Page<PlanningConge> findAll(Pageable pageable);
	
//	public Page<PlanningConge> findByContratPersonnelPersonnelNomIgnoreCaseContaining(Pageable pageable,String name);

	//public Page<PlanningConge> findByPeriodePaieId(Pageable pageable);

	//public PlanningConge findByContratPersonnel(ContratPersonnel contratPersonnel);
	
	public PlanningConge findByContratPersonnelAndStatut(ContratPersonnel contratPersonnel,boolean stat);
	
	//public PlanningConge findByContratPersonnelAndDateDepartBefore(ContratPersonnel contratPersonnel, Date dateDepart);
	
	public PlanningConge findByContratPersonnelAndDateDepartBetween(ContratPersonnel contratPersonnel, Date dateDepart, Date datefin);
	
	
//	public PlanningConge findByPeriodePaie(PeriodePaie periode);

	public final static String find_by_Agence_AnneeMois = "select e from PlanningConge e, ContratPersonnel u, PeriodePaie m" +
			" where e.contratPersonnel.id = u.id and u.personnel.retraitEffect= false" +
			" and m.mois.id = :idMois and m.annee.id = :idAnnee";

	@Query(find_by_Agence_AnneeMois)
	public List<PlanningConge> rechercherParAgenceAnneeMois( @Param("idMois") Long idMois, @Param("idAnnee") Long idAnnee);

    List<PlanningConge> findByStatutTrueOrStatutFalseOrderByContratPersonnelPersonnelNomAsc();

//	Page<PlanningConge> findByStatutTrueOrStatutFalseAndContratPersonnelStatutTrueAndContratPersonnelDepartFalseAndContratPersonnelPersonnelRetraitEffectFalseAndContratPersonnelPersonnelNomIgnoreCaseContainingOrderByContratPersonnelPersonnelNomAsc(Pageable pageable, String search);
//
//	List<PlanningConge> findByStatutTrueOrStatutFalseAndContratPersonnelDepartFalseAndContratPersonnelPersonnelRetraitEffectFalseAndContratPersonnelPersonnelNomIgnoreCaseContainingOrderByContratPersonnelPersonnelNomAsc();
//
//	List<PlanningConge> findByStatutTrueOrStatutFalseAndContratPersonnelDepartFalseAndContratPersonnelPersonnelRetraitEffectFalseAndContratPersonnelPersonnelNomOrderByContratPersonnelPersonnelNomAsc(String search);

	List<PlanningConge> findByStatutTrueOrStatutFalseAndContratPersonnelDepartFalseAndContratPersonnelPersonnelNomIgnoreCaseContaining(String search);

	@Query("SELECT p FROM PlanningConge p WHERE p.periodePaie.id = :idperiode")
	Page<PlanningConge> findByPeriodePaieId(@Param("idperiode") Long idperiode, Pageable pageable);

    List<PlanningConge> findByStatutTrue();
}
