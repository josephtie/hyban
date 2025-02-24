package com.nectux.mizan.hyban.rh.formation.service;

import com.nectux.mizan.hyban.rh.formation.dto.FormationPersonnelDTO;
import org.springframework.data.domain.Pageable;

public interface FormationPersonnelService {
	
	public FormationPersonnelDTO save(Long id, Long idFormation, Long idPersonnel);
	
	public FormationPersonnelDTO save(String listPersonnel, int listPersonnelSize, Long idFormation);
	
	public FormationPersonnelDTO delete(Long id);
	
	public FormationPersonnelDTO findFormationPersonnel(Long id);
	
	public FormationPersonnelDTO findFormationPersonnel(String listPersonnel, int listPersonnelSize, Long idFormation);
	
	public FormationPersonnelDTO findFormationPersonnels();
	//public FormationPersonnelDTO findFormationPersonnelsparFormation();

	public int count();
	
	public FormationPersonnelDTO loadFormationPersonnels(Pageable pageable);
	public FormationPersonnelDTO loadFormationPersonnelsduneFormation(Pageable pageable, Long idFormation);
	public FormationPersonnelDTO loadFormationPersonnelsduneFormation(Pageable pageable, Long idFormation, String formationIntitule, String personnelNom, String personnelPrenom);

	public FormationPersonnelDTO loadFormationPersonnels(Pageable pageable, String formationIntitule, String personnelNom, String personnelPrenom);


}
