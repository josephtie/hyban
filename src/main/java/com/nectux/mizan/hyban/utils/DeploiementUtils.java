
package com.nectux.mizan.hyban.utils;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.ServletContext;
import java.nio.file.Paths;

@Component
public class DeploiementUtils {

	@Autowired
	private ServletContext context;  // Injecte le ServletContext

	@Value("${report.path}")
	private String reportPath;  // Injecte la valeur du chemin définie dans application.properties

	/**
	 * Méthode qui renvoie le chemin de base du répertoire des rapports.
	 * Utilise ServletContext si disponible, sinon la configuration de Spring.
	 */
	public String getCheminBase() {
		// Vérifie si le chemin d'accès est valide en production (ServletContext)
		String chemin = context.getRealPath("/webapps/hyban/reports/");

		// Si le chemin n'est pas trouvé via ServletContext (dans un contexte Spring Boot autonome),
		// utilise le chemin défini dans application.properties.
		if (chemin == null) {
			chemin = reportPath;  // Utilise la valeur de report.path dans application.properties
		}

		return chemin;
	}

	/**
	 * Méthode pour obtenir le chemin d'un sous-rapport relatif à un rapport principal.
	 * @param cheminRelatif chemin relatif du sous-rapport
	 * @return chemin complet du sous-rapport
	 */
	public String getCheminSubReport(String cheminRelatif) {
		return Paths.get(getCheminBase(), cheminRelatif).toString();
	}
}



//
//import java.io.Serializable;
//
//import javax.servlet.ServletContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class DeploimentUtils implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	public static final String SUBREPORT_DIR = "developpement";
//	public static final String chemRechDeveloppement = "/main/resources/reports/";
//	public static final String chemRechDeploiment = "/reports/";
//
//	@Autowired
//	private static ServletContext context;
//
//	public DeploimentUtils() {
//		super();
//	}
//
//	public static String ChemRechDev() {
//        return context.getRealPath(chemRechDeveloppement);
//    }
//
//	public static String ChemRechProd() {
//		return context.getRealPath(chemRechDeploiment);
//	}
//
//	public static String getChem() {
//		if ("developpement".equalsIgnoreCase(SUBREPORT_DIR))
//			return ChemRechDev();
//		else
//			return ChemRechProd();
//	}
//
//
//
//	public static String RecupSubReportChem(String chemRecup){
//		String chem = "";
//
//    	if(SUBREPORT_DIR.equals("developpement")){
//    		chem = SubReportChemDeveloppement(chemRecup);
//    	}
//
//    	if(SUBREPORT_DIR.equals("deploiement"))
//    		chem = SubReportChemDeploiement(chemRecup);
//
//		return  chem;
//	}
//
//	public static String SubReportChemDeveloppement(String chemRecup){
//
//		int indexFin = chemRecup.indexOf("webapp");
//		String chemFinal = chemRecup.substring(0, indexFin);
//		String chemRest = chemRecup.substring(indexFin+6, chemRecup.length());
//
//		return  chemFinal+"resources"+chemRest+"\\";
//	}
//
//    public static String SubReportChemDeploiement(String chemRecup){
//
//		return  chemRecup+"/";
//	}
//
//
//}


/*package net.iconseils.rhpaiecgeci.utils;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

public class DeploimentUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String SUBREPORT_DIR= "developpement";
	public static final String chemRechDeveloppement= "";
	public static final String chemRechDeploiment= "/WEB-INF/classes";


	@Autowired	ServletContext context;
	
	public DeploimentUtils() {
		super();
	}

	public static String ChemRech(){
		String chem = "";
		
    	if(SUBREPORT_DIR.equals("developpement"))
    		chem = chemRechDeveloppement;
    	
    	if(SUBREPORT_DIR.equals("deploiement"))
    		chem = chemRechDeploiment;
    	
		return  chem;
	}
	
    public static String RecupSubReportChem(String chemRecup){
		String chem = "";
		
    	if(SUBREPORT_DIR.equals("developpement")){
    		chem = SubReportChemDeveloppement(chemRecup);
    	}
    	
    	if(SUBREPORT_DIR.equals("deploiement"))
    		chem = SubReportChemDeploiement(chemRecup);
    	
		return  chem;
	}

	public static String SubReportChemDeveloppement(String chemRecup){
		
		int indexFin = chemRecup.indexOf("webapp");
		String chemFinal = chemRecup.substring(0, indexFin);
		
		return  chemFinal+"resources\\";
	}
	
    public static String SubReportChemDeploiement(String chemRecup){
		
		return  chemRecup+"\\";
	}
	

}
*/