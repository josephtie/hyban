package com.nectux.mizan.hyban.personnel.repository;

import com.nectux.mizan.hyban.personnel.entity.Personnel;

import com.nectux.mizan.hyban.personnel.entity.Service;
import com.nectux.mizan.hyban.rh.absences.entity.Absences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface PersonnelRepository extends CrudRepository<Personnel, Long>, JpaSpecificationExecutor<Personnel> {
	
	public List<Personnel> findAll();
	
	public List<Personnel> findByStatut(boolean stat);
	//public java.util.List<Personnel> findByStatutAndRetraitEffect(boolean stat,boolean retr);
	public Personnel findByMatricule(String matricule);

    @Query(
            value = "SELECT * FROM public.cgeci_rhpaie_personnel WHERE matricule = :matricule",
            nativeQuery = true
    )
    Personnel findByMatriculeNative(@Param("matricule") String matricule);


	List<Personnel> findByDateArriveeBetween(Date dateDebut, Date  dateFin);
	public Personnel findByBanquekId(Long id);

	public final static String nb_Personnel_Par_Annee = "select u from Personnel u "+
			" where u.dateArrivee >= :dateDeb and u.dateArrivee <= :dateFin and u.sexe = :sexe";

	@Query(nb_Personnel_Par_Annee)
	public List<Personnel> RechListPersonnelParAnnee( @Param("dateDeb") Date dateDeb, @Param("dateFin") Date dateFin, @Param("sexe") String sexe);
	public Personnel findByNumeroCnps(String numeroCnps);

	public Personnel findByEmail(String email);

	public Page<Personnel> findAll(Pageable pageable); 
	
	public Page<Personnel> findByNomIgnoreCaseContainingOrPrenomIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(Pageable pageable, String matricule, String matricule1, String matricule2);

	public Page<Personnel> findByRetraitEffectFalseAndNomIgnoreCaseContainingOrPrenomIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(Pageable pageable, String matricule, String matricule1, String matricule2);
	//public Page<Personnel> findByRetraitEffectFalseAndNomIgnoreCaseContainingOrPrenomIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(Pageable pageable);

	@Query("SELECT c FROM Personnel c where c.retraitEffect=false GROUP BY c ORDER BY  c.nom ASC,c.prenom ASC ")
	Page<Personnel> chearchOrdreAsc(Pageable pageable);


    @Query("SELECT c FROM Personnel c where c.retraitEffect=false AND c.statut=true GROUP BY c ORDER BY  c.nom ASC,c.prenom ASC ")
    List<Personnel> chearchOrdreAsc();


    @Query("SELECT c FROM Personnel c where c.retraitEffect=false AND c.carec=true GROUP BY c ORDER BY  c.nom ASC,c.prenom ASC ")
    Page<Personnel> chearchContratuelOrdreAsc(Pageable pageable);


    @Query("SELECT c FROM Personnel c where c.retraitEffect=false AND c.statut =true AND c.carec=true GROUP BY c ORDER BY  c.nom ASC,c.prenom ASC ")
    List<Personnel> chearchContratuelOrdreAsc();

	List<Personnel> findByStatutAndRetraitEffect(boolean b, boolean b1);

	public final static String nb_Personnel_Par_Anee = "select u from Personnel u" +
			" where  u.retraitEffect = false" +
			" and u.sexe = :sexe";

	@Query(nb_Personnel_Par_Anee)
	public List<Personnel> RechListPersonnelParAn(@Param("sexe") String sexe);


    Page<Personnel> findByCarec(Boolean carec, Pageable pageable);
    Page<Personnel> findByCarecAndRetraitEffectFalse(Boolean carec, Pageable pageable);
    List<Personnel> findByCarecAndRetraitEffectFalse(Boolean carec);




	List<Personnel> findByServiceLibelle(Service direction);
	List<Personnel> findByServiceLibelle(String direction);


	List<Personnel> findByServiceId(Long byServiceParentId);

    List<Personnel> findByStatutAndRetraitEffectOrderByNomAsc(boolean b, boolean b1);

    List<Personnel> findByRetraitEffectFalse();
    List<Personnel> findByRetraitEffectFalseAndCarecTrue();

	int countByRetraitEffectFalse();

    @Query("SELECT p FROM Personnel p " +
            "WHERE p.retraitEffect = false " +
            "AND p.statut = true " +
            "AND (LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "     OR LOWER(p.prenom) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "     OR LOWER(p.matricule) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Personnel> searchPersonnel(@Param("search") String search, Pageable pageable);

    @Query("SELECT p FROM Personnel p " +
            "WHERE p.retraitEffect = false " +
            "AND p.statut = true " +
            "AND (LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "     OR LOWER(p.prenom) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "     OR LOWER(p.matricule) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Personnel> searchPersonnel(@Param("search") String search);

    @Query("""
SELECT p FROM Personnel p
WHERE (:carec IS NULL OR p.carec = :carec)
AND (
    :search IS NULL OR
    LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR
    LOWER(p.prenom) LIKE LOWER(CONCAT('%', :search, '%')) OR
    LOWER(p.matricule) LIKE LOWER(CONCAT('%', :search, '%'))
)
ORDER BY p.nom ASC
""")
    Page<Personnel> searchWithCarec(
            @Param("carec") Boolean carec,
            @Param("search") String search,
            Pageable pageable);



    @Query(
            value = """
        SELECT DISTINCT p.*
        FROM Personnel p
        JOIN ContratPersonnel c ON c.personnel_id = p.id
        JOIN typeContrat t ON t.id = c.typeContrat_id
        WHERE c.statut = true AND p.carec= true and p.retraitEffect= false
        AND UPPER(t.libelle) = UPPER(:type)
        ORDER BY p.id DESC
    """,
            countQuery = """
        SELECT COUNT(*)
        FROM Personnel p
        JOIN typeContrat c ON c.personnel_id = p.id
        JOIN typeContrat t ON t.id = c.typeContrat_id
        WHERE c.statut = true
        AND UPPER(t.libelle) = UPPER(:type)
    """,
            nativeQuery = true
    )
    Page<Personnel> findByTypeContrat(@Param("type") String type, Pageable pageable);


    @Query(
            value = """
        SELECT p.*
        FROM public.cgeci_rhpaie_personnel p
        WHERE p.carec = true
          AND p.retrait_effect = false
          AND EXISTS (
                SELECT 1
                FROM public.cgeci_rhpaie_contrat_personnel c
                JOIN public.cgeci_rhpaie_type_contrat tc
                     ON c.type_contrat_id = tc.id
                WHERE c.personnel_id = p.id
                  AND c.statut = true
                AND UPPER(tc.libelle) = UPPER(:type)
          )
        """,
            countQuery = """
        SELECT COUNT(*)
        FROM public.cgeci_rhpaie_personnel p
        WHERE p.carec = true
          AND p.retrait_effect = false
          AND EXISTS (
                SELECT 1
                FROM public.cgeci_rhpaie_contrat_personnel c
                JOIN public.cgeci_rhpaie_type_contrat tc
                     ON c.type_contrat_id = tc.id
                WHERE c.personnel_id = p.id
                  AND c.statut = true
                  AND UPPER(tc.libelle) = UPPER(:type)
          )
        """,
            nativeQuery = true
    )
    Page<Personnel> findByTypeContratNative(
            @Param("type") String type,
            Pageable pageable);






    @Query(
            value = """
        SELECT p.*
        FROM public.cgeci_rhpaie_personnel p
        WHERE p.carec = true
          AND p.retrait_effect = false
          AND EXISTS (
                SELECT 1
                FROM public.cgeci_rhpaie_contrat_personnel c
                JOIN public.cgeci_rhpaie_type_contrat tc
                     ON c.type_contrat_id = tc.id
                WHERE c.personnel_id = p.id
                  AND c.statut = true
                  AND UPPER(tc.libelle) = UPPER(:type)
          )
          AND (
                :search1 IS NULL
                OR UPPER(p.nom) LIKE UPPER(CONCAT('%', :search1, '%'))
                OR UPPER(p.prenom) LIKE UPPER(CONCAT('%', :search1, '%'))
                OR UPPER(p.matricule) LIKE UPPER(CONCAT('%', :search1, '%'))
          )
        """,
            countQuery = """
        SELECT COUNT(*)
        FROM public.cgeci_rhpaie_personnel p
        WHERE p.carec = true
          AND p.retrait_effect = false
          AND EXISTS (
                SELECT 1
                FROM public.cgeci_rhpaie_contrat_personnel c
                JOIN public.cgeci_rhpaie_type_contrat tc
                     ON c.type_contrat_id = tc.id
                WHERE c.personnel_id = p.id
                  AND c.statut = true
                  AND UPPER(tc.libelle) = UPPER(:type)
          )
          AND (
                :search1 IS NULL
                OR UPPER(p.nom) LIKE UPPER(CONCAT('%', :search1, '%'))
                OR UPPER(p.prenom) LIKE UPPER(CONCAT('%', :search1, '%'))
                OR UPPER(p.matricule) LIKE UPPER(CONCAT('%', :search1, '%'))
          )
        """,
            nativeQuery = true
    )
    Page<Personnel> searchWithTypeContrat(
            @Param("type") String type,
            @Param("search1") String search1,
            Pageable pageable);



    @Query("SELECT DISTINCT c.personnel.id " +
            "FROM ContratPersonnel c " +
            "WHERE c.statut = true AND UPPER(c.typeContrat.libelle) = UPPER(:type)")
    List<Long> findPersonnelIdsByTypeContrat(@Param("type") String type);

    Page<Personnel> findByIdInAndCarecTrueAndRetraitEffectFalse(List<Long> personnelIdsAvecContrat, Pageable pageable);

    //Page<Personnel> findByIdInAndCarecTrueAndRetraitEffectFalseAndNomIgnoreCaseContainingOrMatriculeIgnoreCaseContaining(List<Long> personnelIdsAvecContrat, String cherch1, Pageable pageable);



    @Query("""
    SELECT p
    FROM Personnel p
    WHERE p.id IN :ids
      AND p.carec = true
      AND p.retraitEffect = false
      AND (
            :search IS NULL
            OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.matricule) LIKE LOWER(CONCAT('%', :search, '%'))
          )
""")
    Page<Personnel> findFilteredByIds(
            @Param("ids") List<Long> ids,
            @Param("search") String search,
            Pageable pageable);


    List<Personnel> findByCarecAndRetraitEffectFalseAndStatutTrue(boolean b);
}


