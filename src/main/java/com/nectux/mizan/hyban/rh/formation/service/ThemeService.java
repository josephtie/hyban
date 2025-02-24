package com.nectux.mizan.hyban.rh.formation.service;

import com.nectux.mizan.hyban.rh.formation.dto.ThemeDTO;
import org.springframework.data.domain.Pageable;

public interface ThemeService {
	
	public ThemeDTO save(Long id, String intitule, String description);
	
	public ThemeDTO delete(Long id);
	
	public ThemeDTO findTheme(Long id);
	
	public ThemeDTO findThemes();
	
	public int count();
	
	public ThemeDTO loadThemes(Pageable pageable);
	
	public ThemeDTO loadThemes(Pageable pageable, String intitule, String description);

}
