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
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.parametrages.entity.Banque;

import com.nectux.mizan.hyban.utils.Utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Entity
@Component("personnelnt")
@Scope("prototype")
@Table(name="CGECI_RHPAIE_PERSONNEL")
@SequenceGenerator(name="CGECI_RHPAIE_PERSONNEL_SEQUENCE", sequenceName="CGECI_RHPAIE_PERSONNEL_SEQ", initialValue=1, allocationSize=1)
public class Personnel   extends Auditable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_PERSONNEL_SEQUENCE")
	@Column(unique=true, nullable=false)
	private Long id;
	
	private String nom;
	
	private String prenom;
	
	@Transient
	private String nomComplet;

	//@Column(unique=true)
	private String matricule;
	
	private String sexe;
	
	private String typeSalarie;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateNaissance;
	
	@Transient
	private String dNaissance;
	
	private String lieuNaissance;
	
	@Column(nullable=true)
	private String telephone;
	
	private String email;
	
	@Column(nullable=true)
    private int nombreJourdu=0; 
	
	@Column(nullable=true)
    private Double mtcongedu=0d;
	
	@Column(nullable=true)
	private String residence;
	
	@Column(nullable=true)
	private String modePaiement;
	
	@Column(nullable=true)
	private String banque;
	
	@Column(nullable=true)
	private String numeroGuichet;
	
	@Column(nullable=true)
	private String numeroCompte;
	
	@Column(nullable=true)
	private String rib;
	
	@Column(nullable=true)
	private Double sursalaire;
	
	@Transient
	private String situationMatri;
	
	private int situationMatrimoniale; // 1 pour MARIE, 2 pour CELIBATAIRE,3 DIVORCE, 4 VEUF
	
	private int situationMedaille;
	@Transient
	private String situationMed;
	
	private int situationEmploie;
	
	@Transient
	private String emplqualite;
	
	private int nombrEnfant;
	
	@Transient
	private Float nombrePart;
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateArrivee;

	@Transient
	private String dArrivee;
	
	
	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateRetourcge;
	
	
	@Transient
	private String dRetourconge;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Nationnalite nationnalite;
	
	@ManyToOne
	@JoinColumn(nullable=true)
	private Banque banquek;
	
	
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Service service;
	
	@Column(nullable=true)
	private String numeroCnps;
	
	@Column(nullable=true)
	private String adresse;
	
	private Boolean statut;
	
	private Boolean carec ;
	
	 private Boolean stage;
	 private Boolean fonctionnaire;
	 private Boolean consultant;

     private Boolean retraitEffect;
	
	
	@Transient
	private String statfonct;
	
	@Transient
	private String enCarec;
	
	@Transient
	private String enSommeil;
	
	@Transient
	private org.springframework.web.multipart.MultipartFile fileDataDemande;
	
	private String urlPhoto;
	
	//########### Getters and Setters ############
	
	
	public Personnel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNomComplet() {
		nomComplet = nom + " " + prenom;
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public java.util.Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(java.util.Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	public String getdNaissance() {
		dNaissance = Utils.dateToString(dateNaissance, "dd/MM/yyyy");
		return dNaissance;
	}

	public void setdNaissance(String dNaissance) {
		this.dNaissance = dNaissance;
	}

	public String getLieuNaissance() {
		return lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

	public String getBanque() {
		return banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}

	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public String getNumeroGuichet() {
		return numeroGuichet;
	}

	public void setNumeroGuichet(String numeroGuichet) {
		this.numeroGuichet = numeroGuichet;
	}

	public Banque getBanquek() {
		return banquek;
	}

	public void setBanquek(Banque banquek) {
		this.banquek = banquek;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public String getSituationMatri() {
		if(situationMatrimoniale == 1)
			situationMatri = "MARIE";
		else if(situationMatrimoniale == 2)
			situationMatri = "CELIBATAIRE";
		else if(situationMatrimoniale == 3)
			situationMatri = "DIVORCE";
		else if(situationMatrimoniale == 4)
			situationMatri = "VEUF";
		return situationMatri;
	}

	public void setSituationMatri(String situationMatri) {
		this.situationMatri = situationMatri;
	}

	public int getSituationMatrimoniale() {
		return situationMatrimoniale;
	}

	public void setSituationMatrimoniale(int situationMatrimoniale) {
		this.situationMatrimoniale = situationMatrimoniale;
	}


	
	public int getSituationEmploie() {
		return situationEmploie;
	}

	public void setSituationEmploie(int situationEmploie) {
		this.situationEmploie = situationEmploie;
	}

	public String getEmplqualite() {
		if(situationEmploie == 1)
			emplqualite = "DR";
		else if(situationEmploie == 2)
			emplqualite = "CS";
		else if(situationEmploie == 3)
			emplqualite = "CM";
		else if(situationEmploie == 4)
			emplqualite = "EQ";
		else if(situationEmploie == 5)
			emplqualite = "EN";
		else if(situationEmploie == 6)
			emplqualite = "OQ";
		else if(situationEmploie == 7)
			emplqualite = "ON";
		else if(situationEmploie == 8)
			emplqualite = "A";
		else if(situationEmploie == 0)
			emplqualite = "Aucun";
		return emplqualite;
	}

	public void setEmplqualite(String emplqualite) {
		this.emplqualite = emplqualite;
	}

	public String getSituationMed() {
		if(situationMedaille == 1)
			situationMed = "HONNEUR";
		else if(situationMedaille == 2)
			situationMed = "VERMEILLE";
		else if(situationMedaille == 3)
			situationMed = "ARGENT";
		else if(situationMedaille == 4)
			situationMed = "OR";
		else if(situationMedaille == 0)
			situationMed = "Aucun";
		return situationMed;
	}

	public void setSituationMed(String situationMed) {
		this.situationMed = situationMed;
	}

	public int getSituationMedaille() {
		return situationMedaille;
	}

	public void setSituationMedaille(int situationMedaille) {
		this.situationMedaille = situationMedaille;
	}

	public int getNombrEnfant() {
		return nombrEnfant;
	}

	public void setNombrEnfant(int nombrEnfant) {
		this.nombrEnfant = nombrEnfant;
	}

	public java.util.Date getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(java.util.Date dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public String getdArrivee() {
		dArrivee = Utils.dateToString(dateArrivee, "dd/MM/yyyy");
		return dArrivee;
	}

	public void setdArrivee(String dArrivee) {
		this.dArrivee = dArrivee;
	}

	public java.util.Date getDateRetourcge() {
		return dateRetourcge;
	}

	public void setDateRetourcge(java.util.Date dateRetourcge) {
		this.dateRetourcge = dateRetourcge;
	}

	public String getdRetourconge() {
		return dRetourconge= Utils.dateToString(dateRetourcge,"dd/MM/yyyy");
	}

	public void setdRetourconge(String dRetourconge) {
		this.dRetourconge = dRetourconge;
	}
	public Nationnalite getNationnalite() {
		return nationnalite;
	}

	public void setNationnalite(Nationnalite nationnalite) {
		this.nationnalite = nationnalite;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getNumeroCnps() {
		return numeroCnps;
	}

	public void setNumeroCnps(String numeroCnps) {
		this.numeroCnps = numeroCnps;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public String getEnSommeil() {
		if(statut==null)
			enSommeil = "";
		if(statut)
			enSommeil = "NON";
		else
			enSommeil = "OUI";
		return enSommeil;
	}

	
	public void setEnSommeil(String enSommeil) {
		this.enSommeil = enSommeil;
	}

	public String getEnCarec() {
		if(carec==null)
			enCarec = "";
		
		if(carec=true)
			enCarec = "OUI";
		if(carec=false)
			enCarec = "NON";
		 
		return enCarec;
	}

	public void setEnCarec(String enCarec) {
		this.enCarec = enCarec;
	}

	public Float getNombrePart() {
		 Float nbpart= (float)(0);
		 nombrePart= nbpart;
		//Celiataire (2), Divorcé (3), veuf (4) sans enfant
		if((situationMatrimoniale == 2 || situationMatrimoniale == 3 || situationMatrimoniale == 4 )&& nombrEnfant == 0)
			nombrePart = (float) 1;
		//Celiataire (2), Divorcé (3) avec enfant
		if((situationMatrimoniale == 2 || situationMatrimoniale == 3 )&& nombrEnfant > 0)
			nombrePart = (float) (1.5 + (nombrEnfant * 0.5));
			if(nombrePart>5)
			nombrePart = (float) 5;
			//Marié (1) sans enfant
		if(situationMatrimoniale== 1 && nombrEnfant == 0)
			nombrePart = (float) 2;
		//Marié (1), Veuf (4) avec enfant
		if((situationMatrimoniale == 1 || situationMatrimoniale == 4) && nombrEnfant > 0){
			nombrePart = (float) (2 + (nombrEnfant * 0.5));
					
			if(nombrePart>5)
				nombrePart = (float) 5;
		}		
			
		return nombrePart;
	}

	
	public void setNombrePart(Float nombrePart) {
		this.nombrePart = nombrePart;
	}

	
	public Boolean getCarec() {
		return carec;
	}

	public void setCarec(Boolean carec) {
		this.carec = carec;
	}

	public String getTypeSalarie() {
		return typeSalarie;
	}

	public void setTypeSalarie(String typeSalarie) {
		this.typeSalarie = typeSalarie;
	}

	
	public int getNombreJourdu() {
		return nombreJourdu;
	}

	public void setNombreJourdu(int nombreJourdu) {
		this.nombreJourdu = nombreJourdu;
	}

	public Double getMtcongedu() {
		return mtcongedu;
	}

	public void setMtcongedu(Double mtcongedu) {
		this.mtcongedu = mtcongedu;
	}

	
	
	public Double getSursalaire() {
		return sursalaire;
	}

	public void setSursalaire(Double sursalaire) {
		this.sursalaire = sursalaire;
	}

	public  org.springframework.web.multipart.MultipartFile getFileDataDemande() {
		return fileDataDemande;
	}

	public void setFileDataDemande(
			 org.springframework.web.multipart.MultipartFile fileDataDemande) {
		this.fileDataDemande = fileDataDemande;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public Boolean getStage() {
		return stage;
	}

	public void setStage(Boolean stage) {
		this.stage = stage;
	}

	public Boolean getFonctionnaire() {
		return fonctionnaire;
	}

	public void setFonctionnaire(Boolean fonctionnaire) {
		this.fonctionnaire = fonctionnaire;
	}

	public Boolean getConsultant() {
		return consultant;
	}

	public void setConsultant(Boolean consultant) {
		this.consultant = consultant;
	}

	public String getStatfonct() {
		
		if(carec==true)
			statfonct = "Contractuel";
		else{

			if (stage == null)
				statfonct = " ";
			if (Boolean.TRUE.equals(stage))
				statfonct = "Stage";

			  if( fonctionnaire == null )
				  statfonct = " ";
			  if(Boolean.TRUE.equals(fonctionnaire) )
				statfonct = "Fonctionnaire ";

			if( consultant == null )
				statfonct = " ";
			if(Boolean.TRUE.equals(consultant))
				statfonct = "Consultant ";

		}
		return statfonct;
	}

	public void setStatfonct(String statfonct) {
		this.statfonct = statfonct;
	}

	public Boolean getRetraitEffect() {
		return retraitEffect;
	}

	public void setRetraitEffect(Boolean retraitEffect) {
		this.retraitEffect = retraitEffect;
	}

	@Override
	public String toString() {
		return "Personnel [id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ ", nomComplet=" + nomComplet + ", matricule=" + matricule
				+ ", sexe=" + sexe + ", typeSalarie=" + typeSalarie
				+ ", dateNaissance=" + dateNaissance + ", dNaissance="
				+ dNaissance + ", lieuNaissance=" + lieuNaissance
				+ ", telephone=" + telephone + ", email=" + email
				+ ", nombreJourdu=" + nombreJourdu + ", mtcongedu=" + mtcongedu
				+ ", residence=" + residence + ", modePaiement=" + modePaiement
				+ ", banque=" + banque + ", numeroGuichet=" + numeroGuichet
				+ ", numeroCompte=" + numeroCompte + ", rib=" + rib
				+ ", sursalaire=" + sursalaire + ", situationMatri="
				+ situationMatri + ", situationMatrimoniale="
				+ situationMatrimoniale + ", situationMedaille="
				+ situationMedaille + ", situationMed=" + situationMed
				+ ", situationEmploie=" + situationEmploie + ", emplqualite="
				+ emplqualite + ", nombrEnfant=" + nombrEnfant
				+ ", nombrePart=" + nombrePart + ", dateArrivee=" + dateArrivee
				+ ", dArrivee=" + dArrivee + ", dateRetourcge=" + dateRetourcge
				+ ", dRetourconge=" + dRetourconge + ", nationnalite="
				+ nationnalite + ", banquek=" + banquek + ", service="
				+ service + ", numeroCnps=" + numeroCnps + ", adresse="
				+ adresse + ", statut=" + statut + ", carec=" + carec
				+ ", stage=" + stage + ", retraitEffect=" + retraitEffect
				+ ", statfonct=" + statfonct + ", enCarec=" + enCarec
				+ ", enSommeil=" + enSommeil + ", fileDataDemande="
				+ fileDataDemande + ", urlPhoto=" + urlPhoto + "]";
	}


	
	
}
