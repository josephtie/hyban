package com.nectux.mizan.hyban.parametrages.repository;

import java.util.List;
import java.util.Optional;

//import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import org.springframework.data.repository.query.Param;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

	public List<Utilisateur> findAll();

	public Page<Utilisateur> findAll(Pageable pageable);

	public Utilisateur findByEmail(String email);


	public Utilisateur findByEmailAndPassword(String email, String motDePasse);

	//public List<Utilisateur> findByNomCompletLikeORAdresseLikeOREmailLike(String name, String address, String email);

	//public Page<Utilisateur> findByNomCompletLikeORAdresseLikeOREmailLike(Pageable pageable, String name, String address, String email);


	@Query("SELECT u FROM Utilisateur u WHERE " +
			"u.nomComplet LIKE %:nomComplet% OR " +

			"u.email LIKE %:email%")
	Page<Utilisateur> findByNomCompletLikeORAdresseLikeOREmailLike(Pageable pageable,
														   @Param("nomComplet") String nomComplet,

														   @Param("email") String email);


		Optional<Utilisateur> findByUsername(String username); // ✅ Retourner Optional

}
