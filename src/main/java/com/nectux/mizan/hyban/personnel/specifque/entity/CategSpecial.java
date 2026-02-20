package com.nectux.mizan.hyban.personnel.specifque.entity;

import com.nectux.mizan.hyban.parametrages.entity.RoleName;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialContractType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Table(name="CGECI_RHPAIE_CATEG")
@SequenceGenerator(name="CGECI_RHPAIE_CATEG_SEQUENCE", sequenceName="CGECI_RHPAIE_CATEG_SEQ", initialValue=1, allocationSize=1)
@Entity
public class CategSpecial  { // ✅ Implémentation de GrantedAuthority

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_CATEG_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(unique = true, nullable = false)
	private SpecialContractType name; // ✅ Assurez-vous que RoleName est un enum

	public CategSpecial() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SpecialContractType getName() {
        return name;
    }

    public void setName(SpecialContractType name) {
        this.name = name;
    }
}


