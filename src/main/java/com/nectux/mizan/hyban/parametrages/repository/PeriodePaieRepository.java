package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PeriodePaieRepository extends CrudRepository<PeriodePaie, Long> {
	
	public List<PeriodePaie> findAll();
	
	public Page<PeriodePaie> findAll(Pageable pageable);
	
	public PeriodePaie findByCloture(Boolean cloture);
	
	//public PeriodePaie findByDateFinBetween(Boolean cloture);
	
	//findByDateFinBetween
	
	public PeriodePaie findByMoisIdAndAnneeAnnee(Long idmois,String annee);
	
	public List<PeriodePaie> findByClotureTrue();
	
	public PeriodePaie findByClotureFalse();
	
	//public PeriodePaie findOne();
	
	public List<PeriodePaie> findByAnneeAnnee(String annee, org.springframework.data.domain.Sort sort);
	
	
	public List<PeriodePaie> findByAnneeAnnee(String annee);
	
	/*public final static String find_Periode_Paie_Agence = "select u from PeriodePaie u " ; 
            

	@Query(find_Periode_Paie_Agence)
	public List<PeriodePaie> findByPeriodepaie(org.springframework.data.domain.Sort sort);
	*/

//	public final static String find_Periode_Agence = "select u from PeriodePaie u, Mois p, Exercice a " +
//            " where u.mois.id = p.id and u.annee.id = a.id " +
//            " and p.id = :idMois and a.id = :idAnnee ";
//
//	@Query(find_Periode_Agence)
//	public List<PeriodePaie> findByPeriodePaie(@Param("idMois") Long idMois, @Param("idAnnee") Long idAnnee);

	
	public PeriodePaie findByAnneeIdAndMoisId(Long idAnnee,Long idMois);
	
	public final static String find_Periode_Ouverte_Agence = "select u from PeriodePaie u " + 
            " where  u.cloture = false " ;
           
	@Query(find_Periode_Ouverte_Agence)
	public PeriodePaie recherchperiodeCloture();
/*	
	public final static String find_Periode_Annee = "select u from PeriodePaie u, Annee a " + 
            " where u.annee.id = a.id and u.delete = false" +
            " and a.id = :idAnnee";

	@Query(find_Periode_Annee)
	public List<PeriodePaie> findByPeriodePaieAnnee(@Param("idAnnee") Long idAnnee);
*/

	public List<PeriodePaie> findByClotureIsNotNull();
	
/*	public List<PeriodePaie> findByClotureFalse1();
	
	public final static String find_Periode_Annee_Agence = "select u from PeriodePaie u, Exercice a " + 
            " where u.annee.id = a.id  " +
            " and a.id = :idAnnee ";

	@Query(find_Periode_Annee_Agence)
	public List<PeriodePaie> findByPeriodePaieAnnee(@Param("idAnnee") Long idAnnee);*/
}
