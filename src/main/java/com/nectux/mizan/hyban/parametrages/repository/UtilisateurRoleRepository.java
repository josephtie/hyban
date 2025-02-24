package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;

import com.nectux.mizan.hyban.parametrages.entity.UtilisateurRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.nectux.mizan.hyban.parametrages.entity.Role;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import org.springframework.data.repository.query.Param;

public interface UtilisateurRoleRepository extends JpaRepository<UtilisateurRole, Long> {
	
	public List<UtilisateurRole> findAll();
	
	public List<UtilisateurRole> findByRole(Role role);
	
	//public List<UtilisateurRole> findByUtilisateurId(Utilisateur utilisateur);

	public Page<UtilisateurRole> findAll(Pageable pageable);

	@Query("SELECT u FROM UtilisateurRole ur JOIN ur.utilisateur u WHERE " +
			"u.nomComplet LIKE %:nomComplet% OR " +

			"u.email LIKE %:email%")
	Page<UtilisateurRole> findByNomCompletOrEmail(Pageable pageable,
														   @Param("nomComplet") String nomComplet,

														   @Param("email") String email);


	//public Page<UtilisateurRole> findByUtilisateurNomCompletLikeORUtilisateurAdresseLikeORUtilisateurEmailLike(Pageable pageable, String nomComplet, String adresse, String email);

	@Query("SELECT ur FROM UtilisateurRole ur WHERE ur.utilisateur.username = :username")
	List<UtilisateurRole> findByUtilisateurUsername(@Param("username") String username);


}
