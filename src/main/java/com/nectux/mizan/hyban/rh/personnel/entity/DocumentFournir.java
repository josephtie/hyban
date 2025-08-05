package com.nectux.mizan.hyban.rh.personnel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nectux.mizan.hyban.parametrages.entity.TypeDocument;

@Entity
@Component("documentFournir")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_DOCUMENT_FOURNIR")
@SequenceGenerator(name="CGECI_RHPAIE_DOCUMENT_FOURNIR_SEQUENCE", sequenceName="CGECI_RHPAIE_DOCUMENT_FOURNIR_SEQ", initialValue=1, allocationSize=1)
public class DocumentFournir extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_DOCUMENT_FOURNIR_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Column(unique=false, nullable=true)
	private String numeroPiece;

	@Column(unique=false, nullable=true)
	private String lieuDelivrance;
	
	@Column(unique=false, nullable=true)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dateDelivrance;
	
	@Transient
	private String delivranceDate;
	
	@Column(unique=true, nullable=true)
	private String photo;
	
	@Transient
	private MultipartFile fileData;
	
	@ManyToOne
	@JoinColumn(nullable = false, unique=false)
	private TypeDocument typeDocument;
	
	@ManyToOne
	@JoinColumn(nullable = false, unique=false)
	private Personnel personnel;

	public DocumentFournir() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPiece() {
		return numeroPiece;
	}

	public void setNumeroPiece(String numeroPiece) {
		this.numeroPiece = numeroPiece;
	}

	public String getLieuDelivrance() {
		return lieuDelivrance;
	}

	public void setLieuDelivrance(String lieuDelivrance) {
		this.lieuDelivrance = lieuDelivrance;
	}

	public Date getDateDelivrance() {
		return dateDelivrance;
	}

	public void setDateDelivrance(Date dateDelivrance) {
		this.dateDelivrance = dateDelivrance;
	}

	public String getDelivranceDate() {
		return delivranceDate;
	}

	public void setDelivranceDate(String delivranceDate) {
		this.delivranceDate = delivranceDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	@Override
	public String toString() {
		return "DocumentFournir [id=" + id + ", numeroPiece=" + numeroPiece + ", lieuDelivrance=" + lieuDelivrance
				+ ", dateDelivrance=" + dateDelivrance + ", delivranceDate=" + delivranceDate + ", photo=" + photo
				+ ", fileData=" + fileData + ", typeDocument=" + typeDocument + ", personnel=" + personnel + "]";
	}

}
