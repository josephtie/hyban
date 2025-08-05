package com.nectux.mizan.hyban.personnel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.parametrages.entity.TypeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component("service")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_SERVICE")
@SequenceGenerator(name="CGECI_RHPAIE_SERVICE_SEQUENCE", sequenceName="CGECI_RHPAIE_SERVICE_SEQ", initialValue=1, allocationSize=1)
public class Service extends Auditable  {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_SERVICE_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String libelle;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private TypeService typeService;
	
	@ManyToOne
	@JoinColumn(nullable=true)
	private Service serviceParent;

	public Service() {
		super();
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

	public TypeService getTypeService() {
		return typeService;
	}

	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	public Service getServiceParent() {
		return serviceParent;
	}

	public void setServiceParent(Service serviceParent) {
		this.serviceParent = serviceParent;
	}	
}
