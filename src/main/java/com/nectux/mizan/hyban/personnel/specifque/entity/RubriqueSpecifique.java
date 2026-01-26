package com.nectux.mizan.hyban.personnel.specifque.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="RUBRIQUE_SPEC")
public class RubriqueSpecifique extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(unique=true, nullable=false)
	private String libelle;



	private Boolean active;

	private Boolean permanent;

	public RubriqueSpecifique() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	





	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}



	public Boolean getPermanent() {
		return permanent;
	}

	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

    @Override
    public String toString() {
        return "RubriqueSpecifique{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", active=" + active +
                ", permanent=" + permanent +
                '}';
    }
}
