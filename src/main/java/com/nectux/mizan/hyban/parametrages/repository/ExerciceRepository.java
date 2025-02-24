package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ExerciceRepository extends CrudRepository<Exercice, Long> {
	
	public List<Exercice> findAll();
	
	public Page<Exercice> findAll(Pageable pageable);
	
	public final static String find_Exercice_Ouverte_Agence = "select u from Exercice u " + 
            " where  u.cloture = false " ;
           

	@Query(find_Exercice_Ouverte_Agence)
	public Exercice findByExoCloture();
	
	public final static String find_Exercice_Libelle = "select u from Exercice u " + 
            " where  u.annee =:annee" ;
           

	@Query(find_Exercice_Libelle)
	public Exercice findByExoLibelle(@Param("annee") String annee);
	
	//public Exercice findByEmailAndMotDePasse(String email, String motDePasse);

//	public List<Exercice> findByAnneeLike(String name);
	
	public List<Exercice> findByAnnee(String name);

	public Exercice findByClotureFalse();
	
	public final static String find_Exercice_Recuperer = "select u from Exercice u " + 
    " where  u.cloture = false or u.cloture = true " ;
	@Query(find_Exercice_Recuperer)
	public List<Exercice> findArecuperer();
	
//	public Exercice findByAnnee(String name);

	//public Page<Exercice> findByAnneeLike(Pageable pageable, String name);

}
