package com.nectux.mizan.hyban.personnel.service;

import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import com.nectux.mizan.hyban.personnel.dto.ContratPersonnelDTO;
import com.nectux.mizan.hyban.personnel.dto.PersonnelDTO;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.entity.Service;
import com.nectux.mizan.hyban.utils.PrintLsDTO;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PersonnelService {
	
	public Personnel save(Personnel personnel);
	
	public ContratPersonnelDTO save(Long id, String nom, String prenom, Long nationalite, Long service, Long categorie, Long fonction, Long typeContrat,
                                    String matricule, String sexe, String dateNaissance, String lieuNaissance, String email, String residence, int situationMatrimoniale,
                                    int nombreEnfant, String dateArrivee, String numeroCNPS, String adresse, String dateDebut, String dateFin,
                                    Double salaireNet, Double indemnitelogement, String modePaiement, Long idbanque, String numeroCompte, String numeroGuichet, String rib, int ancienneteInitial, Boolean carec, String typemp, String telephone, int situationMedaill, int situationEmploie, String dateRetourcg, Double indemniteRespons, Double indemniteRepresent, Double indemniteTransport, Double sursalaire);
	
	public PersonnelDTO save(Long id, String nom, String prenom, Long nationalite, Long service, String matricule, String sexe, String dateNaissance, String lieuNaissance, String email,
                             String residence, int situationMatrimoniale, int nombreEnfant, String dateArrivee, String numeroCNPS, String adresse, Boolean statut,
                             String modePaiement, Long idbanque, String numeroCompte, String numeroGuichet, String rib, Boolean carec, String typemp, String telephone, int situationMedaill, int situationEmploie, String dateRetourcg);
	
	public PersonnelDTO save(Long id, int situationMatrimoniale, int nombreEnfant, Boolean statut);

	public PersonnelDTO depart(Long id);
	
	public Boolean delete(Long id);
	
	public Personnel findPersonnel(Long id);

//Integer [] EffectifAnnuel(java.sql.Date dateInDeb, java.sql.Date dateEnDeb);

	public PersonnelDTO findPersonneldto(Long id);
	
	public Personnel findByEmail(String email);
	
	public Personnel findByMatricule(String matricule);
	
	public PersonnelDTO findByMatricules(String matricule);

	public List<Personnel> RechercherListPersonnelParAnnee( Date dateDeb, Date dateFin, String sexe);
	public PersonnelDTO findByNumeroCnpss(String matricule);
	
	public Personnel findByNumeroCnps(String numeroCnps);
	
	public List<Personnel> findPersonnels();
	
	public int count();

//public int EmploySave();
	public PersonnelDTO loadPersonnel(Pageable pageable);
	PersonnelDTO  findAllfilter(Map<String,String> filters, Pageable pageable);
	public PersonnelDTO loadPersonnel(Pageable pageable, String search, String search1, String search2);

	public List<Personnel> RechercherListPersonnelParAnnee( String sexe);
    PrintLsDTO RechercherListPersonnelParAnnee(Long id);



	List<Personnel> RechercherListPersonnelParDirection(Service direction);
}