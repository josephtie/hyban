
package com.nectux.mizan.hyban.utils;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

public class DeploimentUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String SUBREPORT_DIR= "developpement";
	public static final String chemRechDeveloppement= "";
	public static final String chemRechDeploiment= "/WEB-INF/classes/";


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
		String chemRest = chemRecup.substring(indexFin+6, chemRecup.length());
		
		return  chemFinal+"resources"+chemRest+"\\";
	}
	
    public static String SubReportChemDeploiement(String chemRecup){
		
		return  chemRecup+"/";
	}
	

}


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