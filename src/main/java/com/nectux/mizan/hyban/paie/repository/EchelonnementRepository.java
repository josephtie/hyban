package com.nectux.mizan.hyban.paie.repository;

import com.nectux.mizan.hyban.paie.entity.Echelonnement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EchelonnementRepository extends CrudRepository<Echelonnement, Long> {
	
	
	
	public java.util.List<Echelonnement> findAll();
	
	public Page<Echelonnement> findAll(Pageable pageable);
	
	public java.util.List<Echelonnement> findByPretPersonnelId(Long idptret);
	
	public java.util.List<Echelonnement> findByPretPersonnelPretId(Long idptret);
	
	public java.util.List<Echelonnement> findByPretPersonnelPersonnelIdAndPeriodePaieIdAndPayeTrue(Long idpers,Long idperiode);
	
	public java.util.List<Echelonnement> findByPretPersonnelPersonnelIdAndPeriodePaieId(Long idpers,Long idperiode);
	
	public Page<Echelonnement> findByMontantLike(Pageable pageable,String search);
	
	public Page<Echelonnement> findByPretPersonnelPersonnelNomLike(Pageable pageable,String search);


    List<Echelonnement> findByPeriodePaieId(Long idPeriode);
}
