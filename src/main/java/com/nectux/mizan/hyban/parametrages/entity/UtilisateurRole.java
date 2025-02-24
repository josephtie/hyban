package com.nectux.mizan.hyban.parametrages.entity;

import javax.persistence.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component("utilisateurRole")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_UTILISATEUR_ROLE")
@SequenceGenerator(name="CGECI_RHPAIE_UTILISATEUR_ROLE_SEQUENCE", sequenceName="CGECI_RHPAIE_UTILISATEUR_ROLE_SEQ", initialValue=1, allocationSize=1)
public class UtilisateurRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_UTILISATEUR_ROLE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;



		@ManyToOne
		@JoinColumn(name = "utilisateur_id", nullable = false)
		private Utilisateur utilisateur;

		@ManyToOne
		@JoinColumn(name = "role_id", nullable = false)
		private Role role;

		public Utilisateur getUtilisateur() {
			return utilisateur;
		}

		public void setUtilisateur(Utilisateur utilisateur) {
			this.utilisateur = utilisateur;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}




	public UtilisateurRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "UtilsateurRole [id=" + id + ", utilisateur=" + utilisateur + ", role=" + role + "]";
	}

}
