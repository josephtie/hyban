package com.nectux.mizan.hyban.paie.repository;


import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.paie.entity.Gratification;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GratificationRepository extends CrudRepository<Gratification, Long> {
	
	public List<Gratification> findAll();
	
	public Page<Gratification> findAll(Pageable pageable);

	public List<Gratification> findByPeriodePaieId(Long id);
	
	public List<Gratification> findByPeriodePaieIdAndContratPersonnelId(Long idperiod,Long idctrat);

	public List<Gratification> findByContratPersonnelAndPeriodePaieDatedebBetween(ContratPersonnel ctratpersonnel, Date datedep, Date datefin);
	
	
	public Page<Gratification> findByContratPersonnelPersonnelNomIgnoreCaseContaining(Pageable pageable,String search);
	
	public List<Gratification> findByPeriodePaieIdAndContratPersonnelPersonnelBanquekId(Long idperiode,Long idbanque);
	
	/*public final static String find_load_Gratification = "select p from Gratification p, ContratPersonnel u  " + 
            " where p.contratPersonnel.id = u.id  and p.contratPersonnel.personnel.nom like =: idPerson" ;

	@Query(find_load_Gratification)
	public List<Gratification> find_load_Gratification(@Param("idPerson") String idPerson);*/
	
	public final static String find_Virmtgratif_Banque = "select p from Gratification p, ContratPersonnel u ,Personnel m " + 
            " where p.contratPersonnel.id = u.id  and u.personnel.id=m.id " +
            " and  p.periodePaie.id = :idperiode and m.banquek.id = :idBanque";

	@Query(find_Virmtgratif_Banque)
	public List<Gratification>  findVirmtgratifBanque( @Param("idperiode") Long idperiode,@Param("idBanque") Long idBanque);
	
}
