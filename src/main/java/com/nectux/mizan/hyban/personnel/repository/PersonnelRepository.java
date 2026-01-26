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
	
	@Query("SELECT c FROM Personnel c where c.retraitEffect=false GROUP BY c ORDER BY  c.nom ASC,c.prenom ASC ")
	Page<Personnel> chearchOrdreAsc(Pageable pageable);

	List<Personnel> findByStatutAndRetraitEffect(boolean b, boolean b1);

	public final static String nb_Personnel_Par_Anee = "select u from Personnel u" +
			" where  u.retraitEffect = false" +
			" and u.sexe = :sexe";

	@Query(nb_Personnel_Par_Anee)
	public List<Personnel> RechListPersonnelParAn(@Param("sexe") String sexe);




	List<Personnel> findByServiceLibelle(Service direction);
	List<Personnel> findByServiceLibelle(String direction);


	List<Personnel> findByServiceId(Long byServiceParentId);

    List<Personnel> findByStatutAndRetraitEffectOrderByNomAsc(boolean b, boolean b1);

    List<Personnel> findByRetraitEffectFalse();
    List<Personnel> findByRetraitEffectFalseAndCarecTrue();

	int countByRetraitEffectFalse();
}
