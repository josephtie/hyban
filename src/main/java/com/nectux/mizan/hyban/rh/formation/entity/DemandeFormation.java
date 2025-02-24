package com.nectux.mizan.hyban.rh.formation.entity;

import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.personnel.entity.Service;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.DateManager;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Component("demandeFormation")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_DEMANDE_FORMATION")
@SequenceGenerator(name="CGECI_RHPAIE_DEMANDE_FORMATION_SEQUENCE", sequenceName="CGECI_RHPAIE_DEMANDE_FORMATION_SEQ", initialValue=1, allocationSize=1)
public class DemandeFormation {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_DEMANDE_FORMATION_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dateDemande;

	private int etatDde;

	@Transient
	private String dDemande;

	private String objet;

	private String commentaire;

	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dateCreation;

	@Transient
	private String dCreation;

	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dateModification;

	@Transient
	private String dModification;

	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dateValidation;

	@Transient
	private String dValidation;

	@Transient
	private String mEtatDde;

	@ManyToOne
	@JoinColumn(nullable=false)
	private Service service;


	@ManyToOne
	@JoinColumn(nullable=false)
	private Exercice exercice;

	public DemandeFormation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateDemande() {
		return dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	public String getdDemande() {
		return DateManager.dateToString(dateDemande, "dd/MM/yyyy");
	}

	public void setdDemande(String dDemande) {
		this.dDemande = dDemande;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getdCreation() {
		return DateManager.dateToString(dateCreation, "dd/MM/yyyy");
	}

	public void setdCreation(String dCreation) {
		this.dCreation = dCreation;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getdModification() {
		return DateManager.dateToString(dateModification, "dd/MM/yyyy");
	}

	public void setdModification(String dModification) {
		this.dModification = dModification;
	}

	public Date getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}

	public String getdValidation() {
		return dValidation= DateManager.dateToString(dateValidation, "dd/MM/yyyy");
	}

	public void setdValidation(String dValidation) {
		this.dValidation = dValidation;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Exercice getExercice() {
		return exercice;
	}

	public void setExercice(Exercice exercice) {
		this.exercice = exercice;
	}

	public int getEtatDde() {
		return etatDde;
	}

	public void setEtatDde(int etatDde) {
		this.etatDde = etatDde;
	}

	public String getmEtatDde() {
		if(etatDde==1)
			mEtatDde="Saisie";
		if(etatDde==2)
			mEtatDde="Refus&eacute;";
		if(etatDde==3)
			mEtatDde="Valid&eacute;";
		return mEtatDde;
	}

	public void setmEtatDde(String mEtatDde) {
		this.mEtatDde = mEtatDde;
	}

	@Override
	public String toString() {
		return "DemandeFormation{" +
				"id=" + id +
				", dateDemande=" + dateDemande +
				", etatDde=" + etatDde +
				", dDemande='" + dDemande + '\'' +
				", objet='" + objet + '\'' +
				", commentaire='" + commentaire + '\'' +
				", dateCreation=" + dateCreation +
				", dCreation='" + dCreation + '\'' +
				", dateModification=" + dateModification +
				", dModification='" + dModification + '\'' +
				", mEtatDde='" + mEtatDde + '\'' +
				", service=" + service +
				", exercice=" + exercice +
				'}';
	}

}
