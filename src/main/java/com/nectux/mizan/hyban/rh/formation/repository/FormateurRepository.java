package com.nectux.mizan.hyban.rh.formation.repository;

import com.nectux.mizan.hyban.rh.formation.entity.Formateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormateurRepository extends CrudRepository<Formateur, Long> {
	
	public List<Formateur> findAll();
	
	public Formateur findByNomCompletAndCiviliteAndSexeAndDateNaissanceAndLieuNaissanceAndFixeAndMobileAndCabinetFormationNom(String nomComplet, int civilite, char sexe, String dateNaissance, String lieuNaissance, String fixe, String mobile, String cabinetFormationNom);
	
	//public int countByNom(String nom);

	public Formateur findByIdNotAndNomCompletAndCiviliteAndSexeAndDateNaissanceAndLieuNaissanceAndFixeAndMobileAndCabinetFormationNom(Long id, String nomComplet, int civilite, char sexe, String dateNaissance, String lieuNaissance, String fixe, String mobile, String cabinetFormationNom);
	
	public Page<Formateur> findAll(Pageable pageable);
	
	//public Page<Formateur> findByNomCompletContainingOrLieuNaissanceContainingOrFixeContainingOrMobileContainingOrCabinetFormationNomContaining(Pageable pageable, String nomComplet, String dateNaissance, String lieuNaissance, String fixe, String mobile, String cabinetFormationNom);

	@Query("SELECT f FROM Formateur f WHERE f.nomComplet = :nomComplet AND f.civilite = :civilite AND f.sexe = :sexe " +
			"AND f.dateNaissance = :dateNaissance AND f.lieuNaissance = :lieuNaissance AND f.fixe = :fixe " +
			"AND f.mobile = :mobile AND f.cabinetFormation.nom = :cabinetFormationNom")
	public Page<Formateur> findByAttributes(Pageable pageable,@Param("nomComplet") String nomComplet,
							   @Param("civilite") String civilite,
							   @Param("sexe") char sexe,
							   @Param("dateNaissance") String dateNaissance,
							   @Param("lieuNaissance") String lieuNaissance,
							   @Param("fixe") String fixe,
							   @Param("mobile") String mobile,
							   @Param("cabinetFormationNom") String cabinetFormationNom);

}
