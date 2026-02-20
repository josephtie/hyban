package com.nectux.mizan.hyban.personnel.specifque.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.personnel.entity.Nationnalite;
import com.nectux.mizan.hyban.personnel.specifque.enums.PieceType;
import com.nectux.mizan.hyban.personnel.specifque.enums.SpecialCategory;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
@SequenceGenerator(name="CGECI_RHPAIE_EMPLOYEE_SEQUENCE", sequenceName="CGECI_RHPAIE_EMPLOYEE_SEQ", initialValue=1, allocationSize=1)
public class Employee extends Auditable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CGECI_RHPAIE_EMPLOYEE_SEQUENCE")
    @Column(unique=true, nullable=false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricule;

    private String nom;
    private String prenom;
    private String nomComplet;
    private String sexe;
    private int situationMatrimoniale;
    private String lieuHabitation;
    private String commentaire;
    private Integer  nombrEnfant;

    @JsonSerialize(using = CustomDateDeserializer.class)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private java.util.Date dateofbrid;

    @Transient
    private String dofbrid;



    @Enumerated(EnumType.STRING)
    private PieceType piece;

    private String  nuperopiece;

    private String  numeroCompte;

    @Enumerated(EnumType.STRING)
    private SpecialCategory categorieSpeciale;

    @ManyToOne
    @JoinColumn(nullable=false)
    private Nationnalite nationnalite;

    private Boolean actif = true;

    private String phoneNumber;

    // getters & setters
    @Transient
    private String situationMatri;


    @Transient
    private String netapayer;

    @Transient
    private String fonction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public SpecialCategory getCategorieSpeciale() {
        return categorieSpeciale;
    }

    public void setCategorieSpeciale(SpecialCategory categorieSpeciale) {
        this.categorieSpeciale = categorieSpeciale;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Date getDateofbrid() {
        return dateofbrid;
    }



    public PieceType getPiece() {
        return piece;
    }

    public void setPiece(PieceType piece) {
        this.piece = piece;
    }

    public String getNuperopiece() {
        return nuperopiece;
    }

    public void setNuperopiece(String nuperopiece) {
        this.nuperopiece = nuperopiece;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getSituationMatrimoniale() {
        return situationMatrimoniale;
    }

    public void setSituationMatrimoniale(int situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public void setDateofbrid(Date dateofbrid) {
        this.dateofbrid = dateofbrid;
    }


    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getLieuHabitation() {
        return lieuHabitation;
    }

    public void setLieuHabitation(String lieuHabitation) {
        this.lieuHabitation = lieuHabitation;
    }

    public String getDofbrid() {
        return dofbrid;
    }

    public void setDofbrid(String dofbrid) {
        this.dofbrid = dofbrid;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Nationnalite getNationnalite() {
        return nationnalite;
    }

    public void setNationnalite(Nationnalite nationnalite) {
        this.nationnalite = nationnalite;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getNombrEnfant() {
        return nombrEnfant;
    }

    public void setNombrEnfant(Integer nombrEnfant) {
        this.nombrEnfant = nombrEnfant;
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

    public String getNetapayer() {
        return netapayer;
    }

    public void setNetapayer(String netapayer) {
        this.netapayer = netapayer;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nomComplet='" + nomComplet + '\'' +
                ", sexe='" + sexe + '\'' +
                ", situationMatrimoniale='" + situationMatrimoniale + '\'' +
                ", lieuHabitation='" + lieuHabitation + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", nombrEnfant=" + nombrEnfant +
                ", dateofbrid=" + dateofbrid +
                ", dofbrid='" + dofbrid + '\'' +
                ", piece=" + piece +
                ", nuperopiece='" + nuperopiece + '\'' +
                ", numeroCompte='" + numeroCompte + '\'' +
                ", categorieSpeciale=" + categorieSpeciale +
                ", nationnalite=" + nationnalite +
                ", actif=" + actif +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
