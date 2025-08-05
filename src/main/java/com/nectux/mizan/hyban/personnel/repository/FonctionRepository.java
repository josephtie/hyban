package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.Fonction;

import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface FonctionRepository extends JpaRepository<Fonction, Long> , JpaSpecificationExecutor<Fonction> {
	
	public java.util.List<Fonction> findAll();

	public Fonction findByLibelle(String libelle);

//	public Page<Fonction> findByLibelleOrderByLibelleAsc(Pageable pageable);

	public Page<Fonction> findByLibelleIgnoreCaseContaining(Pageable pageable, String search);

	@Query("SELECT c FROM Fonction c ORDER BY  c.libelle ASC")
	Page<Fonction> chearch(Pageable pageable);

	//Page<Fonction> findByLibelleOrderByLibelleAsc(Pageable pageable);
}

