package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.Categorie;

import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategorieRepository extends CrudRepository<Categorie, Long> , JpaSpecificationExecutor<Categorie> {
	
	public java.util.List<Categorie> findAll();
	
	public Page<Categorie> findAll(Pageable pageable);

	public Page<Categorie> findByLibelleLike(Pageable pageable, String libelle);

	public Categorie findByLibelle(String libelle);



    @Query(
            value = """
            SELECT *
            FROM public.cgeci_rhpaie_categorie
            WHERE libelle ILIKE :search
            ORDER BY libelle
            LIMIT :limit OFFSET :offset
        """,
            nativeQuery = true
    )
    List<Categorie> searchNative(
            @Param("search") String search,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(
            value = """
            SELECT COUNT(*)
            FROM public.cgeci_rhpaie_categorie
            WHERE libelle ILIKE :search
        """,
            nativeQuery = true
    )
    long countNative(@Param("search") String search);
}
