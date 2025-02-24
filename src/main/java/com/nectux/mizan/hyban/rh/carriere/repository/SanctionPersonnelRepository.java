package com.nectux.mizan.hyban.rh.carriere.repository;

import java.util.Date;
import java.util.List;

import com.nectux.mizan.hyban.rh.carriere.entity.SanctionPersonnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SanctionPersonnelRepository extends CrudRepository<SanctionPersonnel, Long> {
	
	public List<SanctionPersonnel> findAll();
	
	public List<SanctionPersonnel> findBySanctionId(Long idSanction);
	
	public List<SanctionPersonnel> findByPersonnelId(Long idPersonnel);
	
	public SanctionPersonnel findByPersonnelIdAndSanctionIdAndDateDebutAndDateFin(Long idPersonnel, Long idSanction, Date dateDebut, Date dateFin);
	
	public int countByPersonnelIdAndSanctionIdAndDateDebutAndDateFin(Long idPersonnel, Long idPoste, Date dateDebut, Date dateFin);
	
	public Page<SanctionPersonnel> findAll(Pageable pageable);
	
	public Page<SanctionPersonnel> findByPersonnelNomContainingOrPersonnelPrenomContainingOrSanctionFauteContainingOrSanctionTypeSanctionContaining(Pageable pageable, String nom, String prenom, String faute, String typeSanction);
	
	

}
