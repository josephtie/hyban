package com.nectux.mizan.hyban.parametrages.entity;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;





@Table(name="CGECI_RHPAIE_ROLE")
@SequenceGenerator(name="CGECI_RHPAIE_ROLE_SEQUENCE", sequenceName="CGECI_RHPAIE_ROLE_SEQ", initialValue=1, allocationSize=1)
@Entity
public class Role implements GrantedAuthority { // ✅ Implémentation de GrantedAuthority

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_ROLE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(unique = true, nullable = false)
	private RoleName name; // ✅ Assurez-vous que RoleName est un enum

	public Role() {}

	public Role(RoleName name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleName getName() {
		return name;
	}

	public void setName(RoleName name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return name.name(); // ✅ Retourne le nom de l'autorité pour Spring Security
	}
}


