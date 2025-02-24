package com.nectux.mizan.hyban.rh.carriere.entity;

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

import com.nectux.mizan.hyban.personnel.entity.Fonction;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Component("promotionPersonnel")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PROMOTION_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_PROMOTION_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_PROMOTION_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class PromotionPersonnel {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PROMOTION_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@Transient
	private MultipartFile fileData;
	
	private String urlDocument;
	
	private String commentaire;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date datePromotion;
	
	@Transient
	private String dPromotion;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateCreation;
	
	@Transient
	private String dCreation;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private java.util.Date dateModification;
	
	@Transient
	private String dModification;
	
	public String getUrlDocument() {
		return urlDocument;
	}

	public void setUrlDocument(String urlDocument) {
		this.urlDocument = urlDocument;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	private Fonction promotion;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Personnel personnel;

	public PromotionPersonnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public java.util.Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.util.Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getdCreation() {
		return DateManager.dateToString(dateCreation, "dd/MM/yyyy HH:mm:ss");
	}

	public void setdCreation(String dCreation) {
		this.dCreation = dCreation;
	}

	public java.util.Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(java.util.Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getdModification() {
		return DateManager.dateToString(dateModification, "dd/MM/yyyy HH:mm:ss");
	}

	public void setdModification(String dModification) {
		this.dModification = dModification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}

	public java.util.Date getDatePromotion() {
		return datePromotion;
	}

	public void setDatePromotion(java.util.Date datePromotion) {
		this.datePromotion = datePromotion;
	}

	public String getdPromotion() {
		return DateManager.dateToString(datePromotion, "dd/MM/yyyy HH:mm:ss");
	}

	public void setdPromotion(String dPromotion) {
		this.dPromotion = dPromotion;
	}

	public Fonction getPromotion() {
		return promotion;
	}

	public void setPromotion(Fonction promotion) {
		this.promotion = promotion;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public String toString() {
		return "PromotionPersonnel [id=" + id + ", fileData=" + fileData + ", datePromotion=" + datePromotion
				+ ", dPromotion=" + dPromotion + ", dateCreation=" + dateCreation + ", dCreation=" + dCreation
				+ ", dateModification=" + dateModification + ", dModification=" + dModification + ", promotion="
				+ promotion + ", personnel=" + personnel + "]";
	}

}
