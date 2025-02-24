package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.entity.Conge;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CongeRepository extends CrudRepository<Conge, Long> {
	
	public List<Conge> findAll();
	
	public List<Conge> findByPeriodePaieId(Long id);
	
	public Page<Conge> findAll(Pageable pageable);

	public List<Conge> findTop1ByContratPersonnel(ContratPersonnel contratPersonnel);

	public final static String find_by_Agence_AnneeMois = "select e from Conge e, ContratPersonnel u,PeriodePaie  m" +
			" where e.contratPersonnel.id = u.id  and u.personnel.retraitEffect = false" +
			" and m.mois.id = :idMois and m.annee.id= :idAnnee";

	@Query(find_by_Agence_AnneeMois)
	public List<Conge> rechercherParAgenceAnneeMois( @Param("idMois") Long idMois, @Param("idAnnee") Long idAnnee);

}
