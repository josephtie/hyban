package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import com.nectux.mizan.hyban.paie.dto.DisaDTO;
import com.nectux.mizan.hyban.paie.dto.DisaMensuelDTO;
import com.nectux.mizan.hyban.paie.dto.DisaTrimestrielDTO;
import com.nectux.mizan.hyban.paie.dto.Etat301DTO;
import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.ImprimBulletinPaie;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import com.nectux.mizan.hyban.parametrages.repository.SocieteRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.personnel.entity.Personnel;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.utils.DateManager;
import com.nectux.mizan.hyban.utils.GenericDataSource;
import com.nectux.mizan.hyban.utils.Utils;
import com.nectux.mizan.hyban.paie.dto.FiscaltabDTO;
import com.nectux.mizan.hyban.parametrages.service.ExerciceService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/paie")
public class DisaController {

	
private static final Logger logger = LogManager.getLogger(DisaController.class);
	
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private BulletinPaieService bulletinPaieService;
	@Autowired private ExerciceService exerciceService;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private SocieteRepository societeRepository;
	@Autowired private SocieteService societeService;
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	//MethodsShared methodsShared = new MethodsShared();
	//MethodsShared methodsShared = new MethodsShared();
	@RequestMapping("/etat")
    public String viewLivrepaie(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		//modelMap.addAttribute("user", userService.findByEmail(principal.getName()));
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeSocialPayrool", "active");
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Cotisations sociales");
		 Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		
		return "etats";
	}
	
	
	@RequestMapping("/etatimp")
    public String viewLivrepaieImp(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeITSFDFP", "active");
		//modelMap.addAttribute("profil", utilisateurRoleService.findByUtilisateur(utilisateurService.findByEmail(principal.getName())).get(0).getRole());
		modelMap.addAttribute("user",utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Cotisation fiscales" );
		 Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "etatimp";
	}	
	
	@RequestMapping("/etatimptab")
    public String viewLivrepaieImptab(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeState301", "active");
		modelMap.addAttribute("user",utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Etat 301");
		 Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "etatimptab";
	}
	
	@RequestMapping(value = "/jrIts", method = RequestMethod.GET)	
	public String ImprimerItsTrim(ModelMap modelMap,@RequestParam(value="periode", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) {
			
			String view="itspdf";
			
			Long encours=idmois;
			PeriodePaie periodePaie = new PeriodePaie();
			List<PeriodePaie> listPeriodepaie = new ArrayList<PeriodePaie>();
			try {
				periodePaie = periodePaieService.findPeriodePaie(idmois);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			List<DisaMensuelDTO> listImprimebulletin=  new ArrayList<DisaMensuelDTO>();
			
			if(periodePaie.getId() != null){
				
				Double mensuelTotalBrut= 0d;
				Double mensuelTotalBrut1= 0d;Double mensuelTotalIgr1= 0d;Double mensuelTotalIgr2= 0d;Double mensuelTotalIgr3= 0d;
				Double mensuelTotalBrut2= 0d;Double mensuelTotalCn1= 0d;Double mensuelTotalCn2= 0d;Double mensuelTotalCn3= 0d;
				Double mensuelTotalBrut3= 0d;Double mensuelTotalIs1= 0d;Double mensuelTotalIs2= 0d;Double mensuelTotalIs3= 0d;
			
				
		
					
					//Recuperation des bulletins pour cette exercie et pour le personnel
					List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
					try {
						//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
						//BulletinPaie bull=new BulletinPaie();
						listBulletin= bulletinPaieService.findBulletinByPeriodePaieContract(periodePaie.getId());
						// listBulletin.add(bull);
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(BulletinPaie bulls : listBulletin){
						mensuelTotalBrut1=mensuelTotalBrut1+bulls.getBrutImposable();
						mensuelTotalIgr1=mensuelTotalIgr1+bulls.getIgr();
						mensuelTotalCn1=mensuelTotalCn1+bulls.getCn();
						mensuelTotalIs1=mensuelTotalIs1+bulls.getIts();
					}
				/*	List<BulletinPaie> listBulletin2 = new ArrayList<BulletinPaie>();
					PeriodePaie	periodePaie1=periodePaieService.findPeriodePaie(encours+1L);
					try {
						//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
						//BulletinPaie bull=new BulletinPaie();
						listBulletin2= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie1, true);
						// listBulletin.add(bull);
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(BulletinPaie bulls2 : listBulletin2){
						mensuelTotalBrut2=mensuelTotalBrut2+bulls2.getBrutImposable();
						mensuelTotalIgr2=mensuelTotalIgr2+bulls2.getIgr();
						mensuelTotalCn2=mensuelTotalCn2+bulls2.getCn();
						mensuelTotalIs2=mensuelTotalIs2+bulls2.getIts();
					}
					List<BulletinPaie> listBulletin3 = new ArrayList<BulletinPaie>();
					PeriodePaie	periodePaie2=periodePaieService.findPeriodePaie(encours+2L);
					try {
						//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
						//BulletinPaie bull=new BulletinPaie();
						listBulletin3= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie2, true);
						// listBulletin.add(bull);
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(BulletinPaie bulls3 : listBulletin3){
						mensuelTotalBrut3=mensuelTotalBrut3+bulls3.getBrutImposable();
						mensuelTotalIgr3=mensuelTotalIgr3+bulls3.getIgr();
						mensuelTotalCn3=mensuelTotalCn3+bulls3.getCn();
						mensuelTotalIs3=mensuelTotalIs3+bulls3.getIts();
					}*/
					//System.out.println("####### bull : "+listBulletin.getc.getPrenom()+"  ---- "+listBulletin.toString());
					
								
					Double cumulTotalBrut = mensuelTotalBrut1;
					
			
				
				DisaMensuelDTO disaDTO = new DisaMensuelDTO();
				modelMap.addAttribute("libelle1", " IS ( Regime general seulement).");
				modelMap.addAttribute("cod1lib", "0|1");
				//disaDTO.setCategorie("0 1  IS ( Regime general seulement).");
				//disaDTO.setValtx(null);
				Double isTrim=mensuelTotalIs1;
			//	disaDTO.setCumulCnps(mensuelTotalIs3+mensuelTotalIs2+mensuelTotalIs1);
				modelMap.addAttribute("lib1mt",Utils.formattingAmount(Math.rint(mensuelTotalIs1)));
				//disaDTO.setCumulPfAt(cumulTotalBrut*0.4d/100);
				listImprimebulletin.add(disaDTO);
				
				DisaMensuelDTO disaDTO1 = new DisaMensuelDTO();
				modelMap.addAttribute("libelle2"," CN (Regime general seulement)");
				modelMap.addAttribute("cod2lib", "0|2");
				//disaDTO1.setValtx(0.6d);
				Double cnTrim=mensuelTotalCn1;
				modelMap.addAttribute("lib2mt",Utils.formattingAmount(Math.rint(mensuelTotalCn1)));
				//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
				listImprimebulletin.add(disaDTO1);
				
				DisaMensuelDTO disaDTO2= new DisaMensuelDTO();
				modelMap.addAttribute("libelle3"," IGR (Regime general)");
				modelMap.addAttribute("cod3lib", "0|3");
				//disaDTO1.setValtx(0.6d);
				Double igrTrim=mensuelTotalIgr1;
				modelMap.addAttribute("lib3mt",Utils.formattingAmount(Math.rint(mensuelTotalIgr1)));
				//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
				listImprimebulletin.add(disaDTO2);
				
				DisaMensuelDTO disaDTO3= new DisaMensuelDTO();
				modelMap.addAttribute("libelle4"," IGR (Regime agricole)");
				modelMap.addAttribute("cod4lib", "0|4");
				//disaDTO1.setValtx(0.6d);
				//disaDTO3.setCumulCnps(0D);
				//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
				listImprimebulletin.add(disaDTO3);
				
				DisaMensuelDTO disaDTO4= new DisaMensuelDTO();
				modelMap.addAttribute("libelle5"," Total des retenues aux salaries (1+2+3+4)");
				modelMap.addAttribute("cod5lib", "0|5");
				//disaDTO1.setValtx(0.6d);
				modelMap.addAttribute("lib5mt",Utils.formattingAmount(cnTrim+isTrim+igrTrim));
				//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
				listImprimebulletin.add(disaDTO4);
				/*DisaMensuelDTO disaDTO5= new DisaMensuelDTO();
				disaDTO5.setCategorie("B - IMPOTS A LA CHARGE DE L'EMPLOYEUR");
				//disaDTO1.setValtx(0.6d);
			//	disaDTO5.setCumulCnps(disaDTO.getCumulCnps()+disaDTO2.getCumulCnps()+disaDTO1.getCumulCnps());
				//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
				listImprimebulletin.add(disaDTO5);
				
				
				DisaMensuelDTO disaDTO6= new DisaMensuelDTO();
				disaDTO6.setCategorie("(1) EFFECTIFS INSCRITS DANS L'ENTREPRISE");
				String uo="EFFECTIFS";
				disaDTO6.setValmt(uo);;			
				//disaDTO6.setCumulPfAt(0);
				//disaDTO6.setCumulCnps(Double.parseDouble("MONTANTS"));
				listImprimebulletin.add(disaDTO6);*/
				
				DisaMensuelDTO disaDTO7= new DisaMensuelDTO();
				modelMap.addAttribute("libelle6"," Personnel local(Regime general)(1,20%)");
				modelMap.addAttribute("cod6lib", "0|6");
				modelMap.addAttribute("libeff6",listBulletin.size());
				modelMap.addAttribute("libSb6",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
				modelMap.addAttribute("lib6mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*1.2/100)));
				//disaDTO7.setValmt(String.valueOf(listBulletin2.size()));			
			//	disaDTO7.setCumulPfAt(cumulTotalBrut);
				//disaDTO7.setCumulCnps(cumulTotalBrut*1.2/100);
				listImprimebulletin.add(disaDTO7);			
				
				DisaMensuelDTO disaDTO8= new DisaMensuelDTO();
				modelMap.addAttribute("libelle7"," Personnel expatrie(Regime general)(10,40%)");
				modelMap.addAttribute("cod7lib", "0|7");
				//disaDTO8.setValtx((double)listBulletin2.size());			
				//disaDTO8.setCumulPfAt(cumulTotalBrut);
				//disaDTO8.setCumulCnps(cumulTotalBrut*1.2/100);
				listImprimebulletin.add(disaDTO8);			
				
				DisaMensuelDTO disaDTO9= new DisaMensuelDTO();
				modelMap.addAttribute("libelle8"," Regime agricole (1,20%)");
				modelMap.addAttribute("cod8lib", "0|8");
				//disaDTO9.setValtx((double)listBulletin2.size());			
				//disaDTO9.setCumulPfAt(cumulTotalBrut);
			//	disaDTO9.setCumulCnps(cumulTotalBrut*1.2/100);
				listImprimebulletin.add(disaDTO9);			
				
				DisaMensuelDTO disaDT10= new DisaMensuelDTO();
				modelMap.addAttribute("libelle9"," Regime fermage (35,00%)");
				modelMap.addAttribute("cod9lib", "0|9");
			/*	disaDT10.setValmt(String.valueOf(listBulletin2.size()));			
				disaDT10.setCumulPfAt(cumulTotalBrut);
				disaDT10.setCumulCnps(cumulTotalBrut*1.2/100);*/
				listImprimebulletin.add(disaDT10);		
				
				
				DisaMensuelDTO disaDT11= new DisaMensuelDTO();
				modelMap.addAttribute("libelle10"," Ensemble du personnel (7+8+9+10)");	
				modelMap.addAttribute("cod10lib", "1|0");
				modelMap.addAttribute("libeff10",listBulletin.size());
				modelMap.addAttribute("libSb10",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
				modelMap.addAttribute("lib10mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*1.2/100)));
			/*	disaDT11.setValmt(String.valueOf(listBulletin2.size()));			
				disaDT11.setCumulPfAt(cumulTotalBrut);
				disaDT11.setCumulCnps(disaDTO7.getCumulCnps());*/
				listImprimebulletin.add(disaDT11);		
				
							
				DisaMensuelDTO disaDT12= new DisaMensuelDTO();
				modelMap.addAttribute("libelle11"," Montant total de la declaration (05+10)");
				modelMap.addAttribute("cod11lib", "1|1");
				modelMap.addAttribute("lib11mt",Utils.formattingAmount(Math.rint( cnTrim+isTrim+igrTrim+cumulTotalBrut*1.2/100)));
			//	disaDT12.setValtx((double)listBulletin2.size());			
			//	disaDT12.setCumulPfAt(cumulTotalBrut);
				//disaDT12.setCumulCnps(disaDTO7.getCumulCnps()+disaDTO4.getCumulCnps());
				listImprimebulletin.add(disaDT12);
				
			    DisaMensuelDTO disaDT13= new DisaMensuelDTO();
			    modelMap.addAttribute("cod12lib", "1|2");
			    modelMap.addAttribute("libelle12"," Imputation ASDI(*)");		
				//disaDT13.setValtx((double)listBulletin2.size());			
				//disaDT13.setCumulPfAt(cumulTotalBrut);
			//	disaDT13.setCumulCnps(cumulTotalBrut*1.2/100);
				listImprimebulletin.add(disaDT13);
				
				
				 DisaMensuelDTO disaDT14= new DisaMensuelDTO();
				 modelMap.addAttribute("cod13lib", "1|3");
				 modelMap.addAttribute("libelle13"," Imputation prelevement 10%(*).");		
				//disaDT14.setValtx((double)listBulletin2.size());			
				//disaDT14.setCumulPfAt(cumulTotalBrut);
				//disaDT14.setCumulCnps(cumulTotalBrut*1.2/100);
				listImprimebulletin.add(disaDT14);
				
				
				 DisaMensuelDTO disaDT15= new DisaMensuelDTO();
				 modelMap.addAttribute("cod14lib", "1|4");
				 modelMap.addAttribute("libelle14"," Regularisation sur mois precedents.(Etat 301).");		
			//	disaDT15.setValtx((double)listBulletin2.size());			
				//disaDT15.setCumulPfAt(cumulTotalBrut);
			//	disaDT15.setCumulCnps(cumulTotalBrut*1.2/100);
				listImprimebulletin.add(disaDT15);
				
				
				 DisaMensuelDTO disaDT16= new DisaMensuelDTO();
				 modelMap.addAttribute("cod15lib", "1|5");
				 modelMap.addAttribute("libelle15"," Total a acquiter (11 - 12 - 13 + 14)");
				 modelMap.addAttribute("lib15mt",Utils.formattingAmount(Math.rint(cnTrim+isTrim+igrTrim+cumulTotalBrut*1.2/100)));
				//	disaDT16.setValmt(String.valueOf(listBulletin2.size()));			
				//	disaDT16.setCumulPfAt(cumulTotalBrut);
				//	disaDT16.setCumulCnps(disaDT12.getCumulCnps());
					listImprimebulletin.add(disaDT16);
				
				
				
				
				
				
				
				
				
				
		
				 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
		
					JRDataSource jrDatasource = null;
					
					//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
					//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
					GenericDataSource<DisaMensuelDTO> dsStudent =  new GenericDataSource<DisaMensuelDTO>(DisaMensuelDTO.class);
					try {
						jrDatasource = dsStudent.create(null, listImprimebulletin);
						//System.out.println("----------- jr data source "+jrDatasource.toString());
					} catch (JRException e) {
						e.printStackTrace();
					}

					
				
					//System.out.println(" la chemin est ------------ "+request.getSession().getServletContext().getRealPath("/WEB-INF/classes/"));
					
					//modelMap.addAttribute("embleme", request.getSession().getServletContext().getRealPath("/resources/images/embleme.png"));
					//Pour le deploiement
					//modelMap.addAttribute("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/WEB-INF/classes")+"\\");
					//modelMap.addAttribute("SUBREPORT_DIR", "D:\\Projets java\\Workspace Education\\RhPaie\\src\\main\\resources\\");
//					modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
					
				//	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/images/logoCNPS.png"));
					//modelMap.addAttribute("exercice", annee.getAnnee());
					//modelMap.addAttribute("raisonSocial", utilisateurCourant.getAgence().getRaisonSocial());
					//modelMap.addAttribute("agence", utilisateurCourant.getAgenceAffiche());
					modelMap.addAttribute("periodePaie", periodePaie);
					if(periodePaie.getMois().getId()<10L)
						   modelMap.addAttribute("moisk","0|"+ periodePaie.getMois().getId().toString());
						else 
							modelMap.addAttribute("moisk", periodePaie.getMois().getId().toString().substring(0, 1)+"|"+periodePaie.getMois().getId().toString().substring(1));
						String trimeste="";
						if(periodePaie.getMois().getId()<=3)trimeste="1|T";
						if(periodePaie.getMois().getId()>=4 && periodePaie.getMois().getId()<=6 )trimeste="2|T";
						if(periodePaie.getMois().getId()>=7 && periodePaie.getMois().getId()<=9 )trimeste="3|T";
						if(periodePaie.getMois().getId()>=10 && periodePaie.getMois().getId()<=12 )trimeste="4|T";
						modelMap.addAttribute("trimest", trimeste);
						modelMap.addAttribute("annee", periodePaie.getAnnee().getAnnee().substring(2, 4).substring(0, 1)+"|"+periodePaie.getAnnee().getAnnee().substring(2, 4).substring(1));
					modelMap.addAttribute("raisonsoc", "CGECI");
					modelMap.addAttribute("societe", societeRepository.findById(1L));
					String  output = societeRepository.findById(1L).get().getCpteContrib();
					//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
				//	System.out.println(output.length);
					modelMap.addAttribute("1i", output.substring(0,1));
					modelMap.addAttribute("2i", output.substring(1,2));
					//	modelMap.addAttribute("2i", output[1]);
					modelMap.addAttribute("3i", output.substring(2, 3));
					modelMap.addAttribute("4i", output.substring(3, 4));
					modelMap.addAttribute("5i", output.substring(4, 5));
					modelMap.addAttribute("6i", output.substring(5, 6));
					modelMap.addAttribute("7i", output.substring(6, 7));
					modelMap.addAttribute("8i", output.substring(7, 8));
					modelMap.addAttribute("datasource", jrDatasource);
					modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/front-end/images/dgi_logo1.jpg"));
					if(print.equalsIgnoreCase("p"))
						modelMap.addAttribute("format", "pdf");
					if(print.equalsIgnoreCase("e"))
						modelMap.addAttribute("format", "xls");
					
					logger.info("Bulletin individuel imprimer");
			}else{}
			
			return view; //mav;
			
			
		}
	
	@RequestMapping(value = "/JrEtat", method = RequestMethod.GET)
	public String ImprimerEtat301Trim(ModelMap modelMap,@RequestParam(value="annee", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) throws Exception {
			
			String view="etatpdf";
			
	      Exercice annee = new Exercice();
			
			try {
				annee = exerciceService.findExo(idmois);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			List<Etat301DTO> listImprimebulletin=  new ArrayList<Etat301DTO>();
			
			if(annee.getId() != null){
				

			    Double mensuelTotalcn= 0d;
				Double mensuelTotalis= 0d;
				Double mensuelTotaligr= 0d;
				//Double mensuelTotalcnrs= 0d;
				Double mensuelTotaliSPpatronLoc= 0d;
				//Double mensuelTotaligrpatronExp= 0d;
				//Double mensuelTotalBrut3= 0d;
				
				   Double totalmensuelTotalcn= 0d;
					Double totalmensuelTotalis= 0d;
					Double totalmensuelTotaligr= 0d;
					//Double totalmensuelTotalcnrs= 0d;
					Double totalmensuelTotaliSPpatronLoc= 0d;
					//Double totalmensuelTotaligrpatronExp= 0d;
					//Double totalmensuelTotalBrut3= 0d;
				
				
			
				List<PeriodePaie>	periodedelanne=periodePaieService.findPeriodedAnnee(annee.getAnnee());
				int nbmois=0;
				for(int i = 0; i < periodedelanne.size(); i++){
					
					List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
					try {
						//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
						//BulletinPaie bull=new BulletinPaie();
						listBulletin= bulletinPaieService.findBulletinByPeriodePaie(periodedelanne.get(i).getId());
						
						 if(listBulletin.size()>0){
							 Etat301DTO etat301DTO = new Etat301DTO();
							 mensuelTotalcn=bulletinPaieService.MensuelCn(periodedelanne.get(i));
							 totalmensuelTotalcn=mensuelTotalcn+totalmensuelTotalcn;
							 
							 mensuelTotalis=bulletinPaieService.MensuelIs(periodedelanne.get(i));
							 totalmensuelTotalis=mensuelTotalis+totalmensuelTotalis;
							 
							 mensuelTotaligr=bulletinPaieService.MensuelIgr(periodedelanne.get(i));	
							 totalmensuelTotaligr=mensuelTotaligr+totalmensuelTotaligr;
							 
							 mensuelTotaliSPpatronLoc= bulletinPaieService.MensuelIgrPatron(periodedelanne.get(i));
							 totalmensuelTotaliSPpatronLoc=mensuelTotaliSPpatronLoc+totalmensuelTotaliSPpatronLoc;
							 
							 
							 
							if(periodedelanne.size()<10)
							 etat301DTO.setMois("0"+String.valueOf(i+1));
							if(periodedelanne.size()>=10)
							etat301DTO.setMois(String.valueOf(i+1));
							
							Date dte= DateManager.addingDate(periodedelanne.get(i).getDatedeb(), 13);
							 etat301DTO.setDatevers(Utils.dateToString(dte,"dd/MM/yy"));
							
							 etat301DTO.setMontantcumulItsmens(Utils.formattingAmount(mensuelTotalis));						 
							 etat301DTO.setMontantcumulCnmens(Utils.formattingAmount(mensuelTotalcn));
							 etat301DTO.setMontantcumulIgrmens(Utils.formattingAmount(mensuelTotaligr));
							 etat301DTO.setMontantcumulCnrsmens(Utils.formattingAmount(0d));	
							 etat301DTO.setMontantcumulISPatronmensloc(Utils.formattingAmount(mensuelTotaliSPpatronLoc));
							 etat301DTO.setMontantcumulISPatronmensExp(Utils.formattingAmount(0d));
							 etat301DTO.setMontantglobalmois(Utils.formattingAmount(mensuelTotalis+mensuelTotalcn+mensuelTotaligr+mensuelTotaliSPpatronLoc));
						    listImprimebulletin.add(etat301DTO);
							
						 }
						// listBulletin.add(bull);
					} catch (Exception e) {
						e.printStackTrace();
					}
					nbmois=i;
				}
					
				    Etat301DTO etat30AjDTO = new Etat301DTO();
				   // etat30AjDTO.setMois(String.valueOf(nbmois+1));
				    if(listImprimebulletin.size()<10){
						etat30AjDTO.setMois("13");
						}	
					if(listImprimebulletin.size()>=10){
						etat30AjDTO.setMois("13");
				}
					if(listImprimebulletin.size()>=1){
						Date dte= DateManager.addingDate(Utils.stringToDate(listImprimebulletin.get(listImprimebulletin.size()-1).getDatevers(),"dd/MM/yy"), 0);
						etat30AjDTO.setDatevers(Utils.dateToString(dte,"dd/MM/yy"));
						}
			//	if(listImprimebulletin.size()>2){
				//	Date dte= DateManager.addingDate(Utils.stringToDate(listImprimebulletin.get(listImprimebulletin.size()-1).getDatevers(),"dd/MM/yy"), 0);
				//	etat30AjDTO.setDatevers(Utils.dateToString(dte,"dd/MM/yy"));//}
					
					etat30AjDTO.setMontantcumulItsmens("");						 
					etat30AjDTO.setMontantcumulCnmens("");
					etat30AjDTO.setMontantcumulIgrmens("");
					etat30AjDTO.setMontantcumulCnrsmens("");	
					etat30AjDTO.setMontantcumulISPatronmensloc("");
					etat30AjDTO.setMontantcumulISPatronmensExp("");
					etat30AjDTO.setMontantglobalmois("");	
					  listImprimebulletin.add(etat30AjDTO);
				
					Etat301DTO etat3014DTO = new Etat301DTO();
					etat3014DTO.setMois("ajust 14");
				//	if(periodedelanne.size()>=10)
					//etat30AjDTO.setMois(String.valueOf(periodedelanne.size()+1));
					Date dte1= DateManager.addingDate(periodedelanne.get(nbmois).getDatedeb(), 14);
					etat3014DTO.setDatevers("");
					etat3014DTO.setMontantcumulItsmens("");						 
					etat3014DTO.setMontantcumulCnmens("");
					etat3014DTO.setMontantcumulIgrmens("");
					etat3014DTO.setMontantcumulCnrsmens("");	
					etat3014DTO.setMontantcumulISPatronmensloc("");
					etat3014DTO.setMontantcumulISPatronmensExp("");
					etat3014DTO.setMontantglobalmois("");							
					listImprimebulletin.add(etat3014DTO);
					
					
					Etat301DTO etat3015DTO = new Etat301DTO();
					etat3015DTO.setMois("Total");
					if(periodedelanne.size()>=10)
					//etat30AjDTO.setMois(String.valueOf(periodedelanne.size()+1));
				//	Date dte10= DateManager.addingDate(periodedelanne.get(11).getDatedeb(), 14);
					etat3015DTO.setDatevers("");
					etat3015DTO.setMontantcumulItsmens(Utils.formattingAmount(totalmensuelTotalis));						 
					etat3015DTO.setMontantcumulCnmens(Utils.formattingAmount(totalmensuelTotalcn));
					etat3015DTO.setMontantcumulIgrmens(Utils.formattingAmount(totalmensuelTotaligr));
					etat3015DTO.setMontantcumulCnrsmens("");	
					etat3015DTO.setMontantcumulISPatronmensloc(Utils.formattingAmount(totalmensuelTotaliSPpatronLoc));
					etat3015DTO.setMontantcumulISPatronmensExp("");
					etat3015DTO.setMontantglobalmois(Utils.formattingAmount(totalmensuelTotalis+totalmensuelTotalcn+totalmensuelTotaligr+totalmensuelTotaliSPpatronLoc));							
					listImprimebulletin.add(etat3015DTO);
					
					
					
		
				 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
		
					JRDataSource jrDatasource = null;
					
					//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
					//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
					GenericDataSource<Etat301DTO> dsStudent =  new GenericDataSource<Etat301DTO>(Etat301DTO.class);
					try {
						jrDatasource = dsStudent.create(null, listImprimebulletin);
						//System.out.println("----------- jr data source "+jrDatasource.toString());
					} catch (JRException e) {
						e.printStackTrace();
					}
		
					modelMap.addAttribute("societe", societeRepository.findById(1L));
					String  output = societeRepository.findById(1L).get().getCpteContrib();
					//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
				//	System.out.println(output.length);
					modelMap.addAttribute("1i", output.substring(0,1));
					modelMap.addAttribute("2i", output.substring(1,2));
					//	modelMap.addAttribute("2i", output[1]);
					modelMap.addAttribute("3i", output.substring(2, 3));
					modelMap.addAttribute("4i", output.substring(3, 4));
					modelMap.addAttribute("5i", output.substring(4, 5));
					modelMap.addAttribute("6i", output.substring(5, 6));
					modelMap.addAttribute("7i", output.substring(6, 7));
					modelMap.addAttribute("8i", output.substring(7, 8));
					modelMap.addAttribute("datasource", jrDatasource);
					//if(print.equalsIgnoreCase("p"))
						modelMap.addAttribute("format", "pdf");
					//if(print.equalsIgnoreCase("e"))
					//	modelMap.addAttribute("format", "xls");
					modelMap.addAttribute("exercice", annee.getAnnee());
					logger.info("Bulletin individuel imprimer");
			}else{}
			
			return view; //mav;
			
			
		}
		
	@RequestMapping(value = "/Jrtab33", method = RequestMethod.GET)
	public String ImprimerEtattab3Trim(ModelMap modelMap,@RequestParam(value="annee", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) throws Exception {
			
			String view="etab3pdf";
			
			Long encours=idmois;
	      Exercice  annee = new Exercice();
			
			try {
				annee = exerciceService.findExo(idmois);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			List<Etat301DTO> listImprimebulletin=  new ArrayList<Etat301DTO>();
			
			if(annee.getId() != null){
				

			    Double mensuelTotalcn= 0d;
				Double mensuelTotalis= 0d;
				Double mensuelTotaligr= 0d;
				Double mensuelTotalcnrs= 0d;
				Double mensuelTotaliSPpatronLoc= 0d;
				Double mensuelTotaligrpatronExp= 0d;
				Double mensuelTotalBrut3= 0d;
				
				   Double totalmensuelTotalcn= 0d;
					Double totalmensuelTotalis= 0d;
					Double totalmensuelTotaligr= 0d;
					Double totalmensuelTotalcnrs= 0d;
					Double totalmensuelTotaliSPpatronLoc= 0d;
					Double totalmensuelTotaligrpatronExp= 0d;
					Double totalmensuelTotalBrutImpos= 0d;
				
				
			
				List<PeriodePaie>	periodedelanne=periodePaieService.findPeriodedAnnee(annee.getAnnee());
				int nbmois=0;
				//for(int i = 0; i < periodedelanne.size(); i++){
					
					List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
					try {
						//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
						//BulletinPaie bull=new BulletinPaie();
						listBulletin= bulletinPaieService.rechercherBulletinAnnuelglobal(annee.getId());
						
						 if(listBulletin.size()>0){
							// for(int i = 0; i < listBulletin.size(); i++){
							 Etat301DTO etat301DTO = new Etat301DTO();
							 mensuelTotalcn=bulletinPaieService.MensuelCnAnne(annee.getId());
						//	 totalmensuelTotalcn=mensuelTotalcn+totalmensuelTotalcn;
							 
							 mensuelTotalis=bulletinPaieService.MensuelIsAnne(annee.getId());
						//	 totalmensuelTotalis=mensuelTotalis+totalmensuelTotalis;
							 
							 mensuelTotaligr=bulletinPaieService.MensuelIgrAnne(annee.getId());	
							 totalmensuelTotaligr=mensuelTotaligr+totalmensuelTotaligr;
							 
							 mensuelTotaliSPpatronLoc= bulletinPaieService.MensuelIgrPatronAnne(annee.getId());
						//	 totalmensuelTotaliSPpatronLoc=mensuelTotaliSPpatronLoc+totalmensuelTotaliSPpatronLoc;
							 
							 totalmensuelTotalBrutImpos=bulletinPaieService.MensuelBrutImpAnne(annee.getId());
							// totalmensuelTotaliSPpatronLoc=mensuelTotaliSPpatronLoc+totalmensuelTotaliSPpatronLoc;
							 
							 
						/*	if(periodedelanne.size()<10)
							 etat301DTO.setMois("0"+String.valueOf(i+1));
							if(periodedelanne.size()>=10)
							etat301DTO.setMois(String.valueOf(i+1));
							
							Date dte= DateManager.addingDate(periodedelanne.get(i).getDatedeb(), 13);
							 etat301DTO.setDatevers(Utils.dateToString(dte,"dd/MM/yy"));
							
							 etat301DTO.setMontantcumulItsmens(Utils.formattingAmount(mensuelTotalis));						 
							 etat301DTO.setMontantcumulCnmens(Utils.formattingAmount(mensuelTotalcn));
							 etat301DTO.setMontantcumulIgrmens(Utils.formattingAmount(mensuelTotaligr));
							 etat301DTO.setMontantcumulCnrsmens(Utils.formattingAmount(0d));	
							 etat301DTO.setMontantcumulISPatronmensloc(Utils.formattingAmount(mensuelTotaliSPpatronLoc));
							 etat301DTO.setMontantcumulISPatronmensExp(Utils.formattingAmount(0d));
							 etat301DTO.setMontantglobalmois(Utils.formattingAmount(mensuelTotalis+mensuelTotalcn+mensuelTotaligr+mensuelTotaliSPpatronLoc));
						    listImprimebulletin.add(etat301DTO);*/
							// }	
						 }
						// listBulletin.add(bull);
					} catch (Exception e) {
						e.printStackTrace();
					}
				//	nbmois=i;
				//}
					
				    Etat301DTO etat30AjDTO = new Etat301DTO();
				   // etat30AjDTO.setMois(String.valueOf(nbmois+1));
				    if(listImprimebulletin.size()<10){
						etat30AjDTO.setMois("13");
						}	
					if(listImprimebulletin.size()>=10){
						etat30AjDTO.setMois("13");
				}
				
					/*Date dte= DateManager.addingDate(Utils.stringToDate(listImprimebulletin.get(listImprimebulletin.size()-1).getDatevers(),"dd/MM/yy"), 0);
					etat30AjDTO.setDatevers(Utils.dateToString(dte,"dd/MM/yy"));
					
					etat30AjDTO.setMontantcumulItsmens("");						 
					etat30AjDTO.setMontantcumulCnmens("");
					etat30AjDTO.setMontantcumulIgrmens("");
					etat30AjDTO.setMontantcumulCnrsmens("");	
					etat30AjDTO.setMontantcumulISPatronmensloc("");
					etat30AjDTO.setMontantcumulISPatronmensExp("");
					etat30AjDTO.setMontantglobalmois("");	
					  listImprimebulletin.add(etat30AjDTO);*/
				
					Etat301DTO etat3014DTO = new Etat301DTO();
					etat3014DTO.setMois("ajust 14");
				//	if(periodedelanne.size()>=10)
					//etat30AjDTO.setMois(String.valueOf(periodedelanne.size()+1));
					Date dte1= DateManager.addingDate(periodedelanne.get(nbmois).getDatedeb(), 14);
					/*etat3014DTO.setDatevers("");
					etat3014DTO.setMontantcumulItsmens("");						 
					etat3014DTO.setMontantcumulCnmens("");
					etat3014DTO.setMontantcumulIgrmens("");
					etat3014DTO.setMontantcumulCnrsmens("");	
					etat3014DTO.setMontantcumulISPatronmensloc("");
					etat3014DTO.setMontantcumulISPatronmensExp("");
					etat3014DTO.setMontantglobalmois("");*/							
					listImprimebulletin.add(etat3014DTO);
					
					
					Etat301DTO etat3015DTO = new Etat301DTO();
					etat3015DTO.setMois("Total");
					if(periodedelanne.size()>=10)
					//etat30AjDTO.setMois(String.valueOf(periodedelanne.size()+1));
				//	Date dte10= DateManager.addingDate(periodedelanne.get(11).getDatedeb(), 14);
					/*etat3015DTO.setDatevers("");
					etat3015DTO.setMontantcumulItsmens(Utils.formattingAmount(totalmensuelTotalis));						 
					etat3015DTO.setMontantcumulCnmens(Utils.formattingAmount(totalmensuelTotalcn));
					etat3015DTO.setMontantcumulIgrmens(Utils.formattingAmount(totalmensuelTotaligr));
					etat3015DTO.setMontantcumulCnrsmens("");	
					etat3015DTO.setMontantcumulISPatronmensloc(Utils.formattingAmount(totalmensuelTotaliSPpatronLoc));
					etat3015DTO.setMontantcumulISPatronmensExp("");
					etat3015DTO.setMontantglobalmois(Utils.formattingAmount(totalmensuelTotalis+totalmensuelTotalcn+totalmensuelTotaligr+totalmensuelTotaliSPpatronLoc));*/							
					listImprimebulletin.add(etat3015DTO);
					
					
					modelMap.addAttribute("lib1mt",String.valueOf(personnelRepository.count()));
					modelMap.addAttribute("lib2mt", "0");
					//	modelMap.addAttribute("2i", output[1]);
					modelMap.addAttribute("lib3mt",Utils.formattingAmount(totalmensuelTotalBrutImpos));
					modelMap.addAttribute("lib4mt", Utils.formattingAmount(totalmensuelTotalBrutImpos));
					modelMap.addAttribute("lib5mt",Utils.formattingAmount(mensuelTotalis));
					modelMap.addAttribute("lib6mt",Utils.formattingAmount(mensuelTotalcn));
					modelMap.addAttribute("lib7mt", Utils.formattingAmount(mensuelTotaligr));
					modelMap.addAttribute("lib8mt", "0");
					modelMap.addAttribute("lib9mt", Utils.formattingAmount(mensuelTotaligr+mensuelTotalcn+mensuelTotalis));
					modelMap.addAttribute("lib10mt", Utils.formattingAmount(mensuelTotalis));
					modelMap.addAttribute("lib11mt",  Utils.formattingAmount(mensuelTotaligr+mensuelTotalcn+mensuelTotalis+mensuelTotalis));
				//	modelMap.addAttribute("lib1mt", output.substring(6, 7));
		
				 //List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
		
					JRDataSource jrDatasource = null;
					
					//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
					//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
					GenericDataSource<Etat301DTO> dsStudent =  new GenericDataSource<Etat301DTO>(Etat301DTO.class);
					try {
						jrDatasource = dsStudent.create(null, listImprimebulletin);
						//System.out.println("----------- jr data source "+jrDatasource.toString());
					} catch (JRException e) {
						e.printStackTrace();
					}

					
		
					modelMap.addAttribute("societe", societeRepository.findById(1L));
					String  output = societeRepository.findById(1L).get().getCpteContrib();
					//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
				//	System.out.println(output.length);
					modelMap.addAttribute("1i", output.substring(0,1));
					modelMap.addAttribute("2i", output.substring(1,2));
					//	modelMap.addAttribute("2i", output[1]);
					modelMap.addAttribute("3i", output.substring(2, 3));
					modelMap.addAttribute("4i", output.substring(3, 4));
					modelMap.addAttribute("5i", output.substring(4, 5));
					modelMap.addAttribute("6i", output.substring(5, 6));
					modelMap.addAttribute("7i", output.substring(6, 7));
					modelMap.addAttribute("8i", output.substring(7, 8));
					modelMap.addAttribute("datasource", jrDatasource);
					//if(print.equalsIgnoreCase("p"))
						modelMap.addAttribute("format", "pdf");
					//if(print.equalsIgnoreCase("e"))
					//	modelMap.addAttribute("format", "xls");
					modelMap.addAttribute("exercice", annee.getAnnee());
					logger.info("Bulletin individuel imprimer");
			}else{}
			
			return view; //mav;
			
			
		}
@RequestMapping(value = "/Jrtab2", method = RequestMethod.GET)
public String ImprimerEtattab2Trim(ModelMap modelMap,@RequestParam(value="annee", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) throws Exception {
		
		String view="etattabopdf";
		
		

		
		Exercice  annee = new Exercice();
		
		try {
			annee = exerciceService.findExo(idmois);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("####### AnnÃ©e est : "+annee.toString());
		
		List<FiscaltabDTO> listImprimebulletinu=  new ArrayList<FiscaltabDTO>();
		
        if(annee.getId() != null){
        	view="etattabopdf";
			//Recherche de la liste du personnel
		 List<Personnel> listPersonnel = new ArrayList<Personnel>();
			try {
				listPersonnel = personnelRepository.findByStatut(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			Double totalSalaireBrutAnnuelNonPlafon = 0d;
			Double totalSalaireAnnuelSoumisAuPfAt = 0d;
			Double totalSalaireAnnuelSoumisCnps = 0d;
			Double totalmensuelJourTrvail= 0d;
			  Double cumulmensuelTotalcn= 0d;
				Double cumulmensuelTotalis= 0d;
				Double cumulmensuelTotaligr= 0d;
				Double cumulmensuelTotalcnrs= 0d;
				Double cumulmensuelTotalBrutImpcnrs= 0d;

			for(int i = 0; i < listPersonnel.size(); i++){
				//System.out.println("####### personnel est : "+pers.getPrenom());
			
				FiscaltabDTO disaDTO = new FiscaltabDTO();
				disaDTO.setNomcomplet(listPersonnel.get(i).getNom()+" "+listPersonnel.get(i).getPrenom());				
				disaDTO.setNerieninscrire("");
				disaDTO.setEmploiQlte("");
				disaDTO.setCodeEmp(listPersonnel.get(i).getEmplqualite().substring(0, 1)+"|"+listPersonnel.get(i).getEmplqualite().substring(1, 2));
                  disaDTO.setRegimG("G");				
				disaDTO.setSexe(listPersonnel.get(i).getSexe().substring(0, 1));
				disaDTO.setNationalite(listPersonnel.get(i).getNationnalite().getLibelle().substring(0, 1) +"|"+listPersonnel.get(i).getNationnalite().getLibelle().substring(1, 2));
				
				if(listPersonnel.get(i).getNationnalite().getLibelle().equals("IVOIRIENNE"))
					disaDTO.setLocal("L");
				else disaDTO.setLocal("E");
				
				disaDTO.setSituation(listPersonnel.get(i).getSituationMatri().substring(0, 1));
				if(listPersonnel.get(i).getNombrEnfant()>10)disaDTO.setNbrenfant(String.valueOf(listPersonnel.get(i).getNombrEnfant()).substring(0, 1)+"|"+String.valueOf(listPersonnel.get(i).getNombrEnfant()).substring(1, 2));
				else
					disaDTO.setNbrenfant("0"+"|"+listPersonnel.get(i).getNombrEnfant());
			
				disaDTO.setNbrpart(String.valueOf(listPersonnel.get(i).getNombrePart()));
				
			
				disaDTO.setMtbrutavantage(null);
				disaDTO.setMtbrutavantagereel(null);
			
				disaDTO.setMtcnrs("");
				disaDTO.setMtfraisEmp("");
				disaDTO.setDesignation("");
				//Recuperation des bulletins pour cette exercie et pour le personnel
				List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
				try {
					listBulletin = bulletinPaieService.rechercherBulletinAnnuel(annee.getId(),listPersonnel.get(i).getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//System.out.println("####### bull : "+pers.getPrenom()+"  ---- "+listBulletin.toString());
				
				Integer nbMois = 0;
				Integer nbMoisBulletin = 0;
				Double nbJour = 0d;
				Double nbHeure = 0d;
				Double somNetImposable = 0d;
				Double somPrimeTransport = 0d;
				
				Double cumulRetenuePatronal = 0d;
				
				Double cumulTotalbrut = 0d;
				Double cumulSalaireAnnuelSoumisCnps = 0d;
				  Double mensuelTotalcn= 0d;
				  Double mensuelTotalbrut= 0d;
					Double mensuelTotalis= 0d;
					Double mensuelTotaligr= 0d;
					Double mensuelTotalcnrs= 0d;
					Double mensuelJourTrvail= 0d;
					
					  Double totalmensuelTotalcn= 0d;
						Double totalmensuelTotalis= 0d;
						Double totalmensuelTotaligr= 0d;
						Double totalmensuelTotalcnrs= 0d;
						
			if(listBulletin.size()> 0){
				 System.out.println("----------- PERSONNEL*******************************"+listPersonnel.get(i).getNom() +"nbre de bulletin  "+ listBulletin.size());
				for(int k = 0; k < listBulletin.size(); k++){
					
					 mensuelTotalcn=bulletinPaieService.MensuelCn(listBulletin.get(k).getPeriodePaie());
					 totalmensuelTotalcn=mensuelTotalcn+totalmensuelTotalcn;
					
					 
					 
					 mensuelTotalis=bulletinPaieService.MensuelIs(listBulletin.get(k).getPeriodePaie());
					 totalmensuelTotalis=mensuelTotalis+totalmensuelTotalis;
					
					 
					 
					 mensuelTotaligr=bulletinPaieService.MensuelIgr(listBulletin.get(k).getPeriodePaie());	
					 totalmensuelTotaligr=mensuelTotaligr+totalmensuelTotaligr;
					 
					 
					 mensuelTotalbrut=bulletinPaieService.MensuelBrutImposable(listBulletin.get(k).getPeriodePaie());	
					 System.out.println("-----------mensuel total brut *******************************"+mensuelTotalbrut +"PERSONNEL  "+listBulletin.get(k).getContratPersonnel().getPersonnel().getMatricule()+"bulletin bulletin"+listBulletin.get(k).getBrutImposable());
					 cumulTotalbrut=mensuelTotalbrut+cumulTotalbrut;
					
					 
					 
					 mensuelJourTrvail=bulletinPaieService.MensuelBrut(listBulletin.get(k).getPeriodePaie());	
					 totalmensuelJourTrvail=mensuelJourTrvail+totalmensuelJourTrvail;
				}
				
			
				
			 }
			if(String.valueOf(totalmensuelJourTrvail).length()==3 && totalmensuelJourTrvail!=null)
			 disaDTO.setNbrJourdePaiement(String.valueOf(totalmensuelJourTrvail).substring(0, 1)+"|"+ String.valueOf(totalmensuelJourTrvail).substring(1, 2)+"|"+String.valueOf(totalmensuelJourTrvail).substring(2, 3));
			
			//if(String.valueOf(totalmensuelJourTrvail).length()==2)
			//	 disaDTO.setNbrJourdePaiement(String.valueOf("0|"+ String.valueOf(totalmensuelJourTrvail).substring(0, 1)+"|"+String.valueOf(totalmensuelJourTrvail).substring(1, 2)));
			 
			 cumulmensuelTotalis=totalmensuelTotalis+cumulmensuelTotalis;
			 cumulmensuelTotalcn=totalmensuelTotalcn+cumulmensuelTotalcn;
			 cumulmensuelTotalBrutImpcnrs=cumulTotalbrut+cumulmensuelTotalBrutImpcnrs;
			 cumulmensuelTotaligr=totalmensuelTotaligr+cumulmensuelTotaligr;
			 
		//	System.out.println("-----------nb valu impot "+totalmensuelTotalis);
			disaDTO.setMtits(Utils.formattingAmount(totalmensuelTotalis));		
			disaDTO.setMtcn(Utils.formattingAmount(totalmensuelTotalcn));	
			disaDTO.setMtigr(Utils.formattingAmount(totalmensuelTotaligr));
			disaDTO.setMtbrutTotal(Utils.formattingAmount(cumulTotalbrut));
			disaDTO.setMtcumulbrutpers(Utils.formattingAmount(cumulTotalbrut));
			disaDTO.setOrdre("0"+String.valueOf(i+1));
				listImprimebulletinu.add(disaDTO);
			}	
			FiscaltabDTO disaDTO1 = new FiscaltabDTO();
			disaDTO1.setNomcomplet("..................TOTAL..............");				
			disaDTO1.setNerieninscrire("");
			disaDTO1.setEmploiQlte("");
			disaDTO1.setCodeEmp("");
			disaDTO1.setRegimG("");				
			disaDTO1.setSexe("");
			disaDTO1.setNationalite("");		
		   disaDTO1.setLocal("");			
			disaDTO1.setSituation("");			
			disaDTO1.setNbrenfant("");		
			disaDTO1.setNbrpart("");
			
		
			disaDTO1.setMtbrutavantage(null);
			disaDTO1.setMtbrutavantagereel(null);
			System.out.println("-----------Cummul mensuel total brut *******************************"+cumulmensuelTotalBrutImpcnrs);
			disaDTO1.setMtcnrs("");
			disaDTO1.setMtfraisEmp("");
			disaDTO1.setDesignation("");
			disaDTO1.setMtits(Utils.formattingAmount(cumulmensuelTotalis));		
			disaDTO1.setMtcn(Utils.formattingAmount(cumulmensuelTotalcn));	
			disaDTO1.setMtigr(Utils.formattingAmount(cumulmensuelTotaligr));
			disaDTO1.setMtbrutTotal(Utils.formattingAmount(cumulmensuelTotalBrutImpcnrs));
			disaDTO1.setMtcumulbrutpers(Utils.formattingAmount(cumulmensuelTotalBrutImpcnrs));
			listImprimebulletinu.add(disaDTO1);
				JRDataSource jrDatasource = null;
			
			//	System.out.println("-----------nb list bull imprrrr "+listImprimebulletinu.get(0).toString());
				//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
				GenericDataSource<FiscaltabDTO> dsStudent =  new GenericDataSource<FiscaltabDTO>(FiscaltabDTO.class);
				try {
					jrDatasource = dsStudent.create(null, listImprimebulletinu);
					//System.out.println("----------- jr data source "+jrDatasource.toString());
				} catch (JRException e) {
					e.printStackTrace();
				}

				
				modelMap.addAttribute("societe", societeRepository.findById(1L));
				String  output = societeRepository.findById(1L).get().getCpteContrib();
				//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
			//	System.out.println(output.length);
				modelMap.addAttribute("1i", output.substring(0,1));
				modelMap.addAttribute("2i", output.substring(1,2));
				//	modelMap.addAttribute("2i", output[1]);
				modelMap.addAttribute("3i", output.substring(2, 3));
				modelMap.addAttribute("4i", output.substring(3, 4));
				modelMap.addAttribute("5i", output.substring(4, 5));
				modelMap.addAttribute("6i", output.substring(5, 6));
				modelMap.addAttribute("7i", output.substring(6, 7));
				modelMap.addAttribute("8i", output.substring(7, 8));
				 modelMap.addAttribute("exercice", annee.getAnnee());
			     modelMap.addAttribute("raisonSocial", "CGECI");
			 
			     modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/front-end/images/logoCNPS.png"));
			
	
				modelMap.addAttribute("societe", societeRepository.findById(1L));
				modelMap.addAttribute("datasource", jrDatasource);
				modelMap.addAttribute("format", "pdf");
				
				logger.info("Bulletin individuel imprimer");
        }else{
        	
        }
	//modelMap.addAttribute("datasource", jrDatasource);
//modelMap.addAttribute("format", "pdf");
		return view; //mav;
		
		
	}
	
@RequestMapping(value = "/JRCotisationMensuelEmployeur", method = RequestMethod.GET)
public String ImprimerDisaMensuel(ModelMap modelMap,@RequestParam(value="periode", required=true) Long idmois ,@RequestParam(value="tPf", required=false) Double tPf ,@RequestParam(value="tAt", required=false) Double tAt ,@RequestParam(value="tCnps", required=false) Double tCnps ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) {
		
		String view="disaMensuelpdf";
		
		
		PeriodePaie periodePaie = new PeriodePaie();
		List<PeriodePaie> listPeriodepaie = new ArrayList<PeriodePaie>();
		try {
			periodePaie = periodePaieService.findPeriodePaie(idmois);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<DisaMensuelDTO> listImprimebulletin=  new ArrayList<DisaMensuelDTO>();
		
		if(periodePaie.getId() != null){
			
			//Recherche de la liste du personnel
		//	List<Personnel> listPersonnel = new ArrayList<Personnel>();
			//try {
		//		listPersonnel = personnelRepository.findAll();
		//	} catch (Exception e) {
		//		e.printStackTrace();
	//		}
			
			Integer journalierInf3231 = 0;
			Integer journalierSup3231 = 0;
			Integer mensuelInf70000 = 0;
			Integer mensuelSup70000 = 0;
			Integer mensuelSup1647315 = 0;
			
			Double journalierInf3231CNPS = 0d;
			Double journalierSup3231CNPS = 0d;
			Double mensuelInf70000CNPS = 0d;
			Double mensuelSup70000CNPS = 0d;
			Double mensuelSup1647315CNPS = 0d;
			
			Double journalierInf3231AT = 0d;
			Double journalierSup3231AT = 0d;
			Double mensuelInf70000AT = 0d;
			Double mensuelSup70000AT = 0d;
			Double mensuelSup1647315AT = 0d;
			
			Double journalierInf3231PF = 0d;
			Double journalierSup3231PF = 0d;
			Double mensuelInf70000PF = 0d;
			Double mensuelSup70000PF = 0d;
			Double mensuelSup1647315PF = 0d;
			
			
			Double journalierInf3231ATPF = 0d;
			Double journalierSup3231ATPF = 0d;
			Double mensuelInf70000ATPF = 0d;
			Double mensuelSup70000ATPF = 0d;
			Double mensuelSup1647315ATPF = 0d;
			
			Integer nbSalarieNonCnps = 0;
			Integer nbSalarieNonPf = 0;
			
	//	for(Personnel pers : listPersonnel){
				//System.out.println("####### personnel est : "+pers.getPrenom());
				//DisaMensuelDTO disaDTO = new DisaMensuelDTO();
				//disaDTO.setCategorie("Horaires, journaliers et occasionnels infÃ©rieurs ou Ã©gaux Ã  3231 F par iour");
				//disaDTO.setNbSalarie(nbSalarie);
				//disaDTO.setCumulCnps(cumulCnps);
				//disaDTO.setCumulPfAt(cumulPfAt);
				
				
				
				//Recuperation des bulletins pour cette exercie et pour le personnel
				List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//System.out.println("####### bull : "+listBulletin.getc.getPrenom()+"  ---- "+listBulletin.toString());
				
				Integer nbMois = 0;
				Integer nbMoisBulletin = 0;
				Double nbJour = 0d;
				Double nbHeure = 0d;
				Double somNetImposable = 0d;
				
				Double cumulRetenuePatronal = 0d;
				Double monBrutTotal = 0d;

				for(BulletinPaie bulls : listBulletin){
					String typSalarie = bulls.getContratPersonnel().getPersonnel().getTypeSalarie();
					nbMoisBulletin = nbMoisBulletin + 1;
					
					Double salaireBrut = bulls.getBrutImposable();
					monBrutTotal=bulls.getBrutImposable()+monBrutTotal;
					
					//if(listRetenuePersonnel.size()>0){
						//for(RetenuePersonnel retenuPers: listRetenuePersonnel){
							if(bulls.getRetraite()!=null){
								cumulRetenuePatronal = cumulRetenuePatronal + bulls.getBasecnps();
							
								Double baseCnps = bulls.getBasecnps();
								
								if(typSalarie.equals("M")){
									if( salaireBrut <= 70000d){
										mensuelInf70000 = mensuelInf70000 + 1;
									
										if(baseCnps <= 1647315d)
											mensuelInf70000CNPS = mensuelInf70000CNPS + baseCnps;
										else
											mensuelInf70000CNPS = mensuelInf70000CNPS + 1647315F;
										
									}
									else{
										if(salaireBrut <= 1647315d){
											mensuelSup70000 = mensuelSup70000 + 1;
											
											if(baseCnps <= 1647315d)
												mensuelSup70000CNPS = mensuelSup70000CNPS + baseCnps;
											else
												mensuelSup70000CNPS = mensuelSup70000CNPS + 1647315d;
											
										}
										else{
											mensuelSup1647315 = mensuelSup1647315 + 1;
											if(baseCnps <= 1647315d)
												mensuelSup1647315CNPS = mensuelSup1647315CNPS + baseCnps;
											else
												mensuelSup1647315CNPS = mensuelSup1647315CNPS + 1647315d;
											
										}
										
									}
								}
								
								if(typSalarie.equals("J")){
									if( salaireBrut <= 3231d){
										journalierInf3231 = journalierInf3231 + 1;
										
										if(baseCnps <= 1647315d)
											journalierInf3231CNPS = journalierInf3231CNPS + baseCnps;
										else
											journalierInf3231CNPS = journalierInf3231CNPS + 1647315d;
										
									}
									else{
										journalierSup3231 = journalierSup3231 + 1;
										if(baseCnps <= 1647315d)
											journalierSup3231CNPS = journalierSup3231CNPS + baseCnps;
										else
											journalierSup3231CNPS = journalierSup3231CNPS + 1647315d;
										
									}
									
								}
							}
							
							
							
							
							
							if(bulls.getAccidentTravail()!=null){
								cumulRetenuePatronal = cumulRetenuePatronal + bulls.getAccidentTravail();
						
								Double retenuAt = bulls.getAccidentTravail();
								
								if(typSalarie.equals("M")){
									if( salaireBrut <= 70000d){
									
										if(retenuAt <= 70000d)
											mensuelInf70000AT = mensuelInf70000AT + retenuAt;
										else
											mensuelInf70000AT = mensuelInf70000AT + 70000F;
									}
									else{
										if(salaireBrut <= 1647315d){
											
											if(retenuAt <= 70000d)
												mensuelSup70000AT = mensuelSup70000AT + retenuAt;
											else
												mensuelSup70000AT = mensuelSup70000AT + 70000d;
											
										}
										else{
											
											if(retenuAt <= 70000d)
												mensuelSup1647315AT = mensuelSup1647315AT + retenuAt;
											else
												mensuelSup1647315AT = mensuelSup1647315AT + 70000d;
											
										}
										
									}
								}
								
								if(typSalarie.equals("J")){
									if( salaireBrut <= 3231d){
										journalierInf3231 = journalierInf3231 + 1;
										
										if(retenuAt <= 70000d)
											journalierInf3231AT = journalierInf3231AT + retenuAt;
										else
											journalierInf3231AT = journalierInf3231AT + 70000d;
										
									}
									else{
										if(retenuAt <= 70000d)
											journalierSup3231AT = journalierSup3231AT + retenuAt;
										else
											journalierSup3231AT = journalierSup3231AT + 70000d;
										
									}
									
								}
								
								
							}
							
							if(bulls.getPrestationFamiliale()!=null){
								cumulRetenuePatronal = cumulRetenuePatronal + bulls.getPrestationFamiliale();
						
								Double retenuPf = bulls.getPrestationFamiliale();
								

								if(typSalarie.equals("M")){
									if( salaireBrut <= 70000d){
									
										if(retenuPf <= 70000d)
											mensuelInf70000PF = mensuelInf70000PF + retenuPf;
										else
											mensuelInf70000PF = mensuelInf70000PF + 70000d;
										
									}
									else{
										if(salaireBrut <= 1647315d){
											
											if(retenuPf <= 70000d)
												mensuelSup70000PF = mensuelSup70000PF + retenuPf;
											else
												mensuelSup70000PF = mensuelSup70000PF + 70000d;
											
										}
										else{
											if(retenuPf <= 70000F)
												mensuelSup1647315PF = mensuelSup1647315PF + retenuPf;
											else
												mensuelSup1647315PF = mensuelSup1647315PF + 70000d;
											
										}
										
									}
								}
								
								if(typSalarie.equals("J")){
									if( salaireBrut <= 3231d){
										journalierInf3231 = journalierInf3231 + 1;
										
										if(retenuPf <= 70000d)
											journalierInf3231PF = journalierInf3231PF + retenuPf;
										else
											journalierInf3231PF = journalierInf3231PF + 70000d;
										
									}
									else{
										if(retenuPf <= 70000d)
											journalierSup3231PF = journalierSup3231PF + retenuPf;
										else
											journalierSup3231PF = journalierSup3231PF + 70000d;
										
									}
									
								}
								
							
							}
							
							
							journalierInf3231ATPF = journalierInf3231AT + journalierInf3231PF;
							journalierSup3231ATPF = journalierSup3231AT + journalierSup3231PF;
							mensuelInf70000ATPF = mensuelInf70000AT + mensuelInf70000PF;
							mensuelSup70000ATPF = mensuelSup70000AT + mensuelSup70000PF;
							mensuelSup1647315ATPF = mensuelSup1647315AT + mensuelSup1647315PF;
							
							if(journalierInf3231ATPF > 70000d)
								journalierInf3231ATPF = 70000d;
							
							if(journalierSup3231ATPF > 70000d)
								journalierSup3231ATPF = 70000d;
							
							if(mensuelInf70000ATPF > 70000d)
								mensuelInf70000ATPF = 70000d;
							
							if(mensuelSup70000ATPF > 70000d)
								mensuelSup70000ATPF = 70000d;
							
							if(mensuelSup1647315ATPF > 70000d)
								mensuelSup1647315ATPF = 70000d;
							
							
						//}
					//}


						
				}


				DisaMensuelDTO disaDTO = new DisaMensuelDTO();
				String test1="Horaires journaliers et occasionnels inferieurs ou egaux a 3231 F par jour";
				disaDTO.setCategorie(test1);
				disaDTO.setNbSalarie(journalierInf3231);
				disaDTO.setCumulCnps(journalierInf3231CNPS);
				disaDTO.setCumulPfAt(journalierInf3231ATPF);
				listImprimebulletin.add(disaDTO);
				
				DisaMensuelDTO disaDTO1 = new DisaMensuelDTO();
				disaDTO1.setCategorie("Horaires journaliers et occasionnels superieurs ou egaux a  3231 F par jour");
				disaDTO1.setNbSalarie(journalierSup3231);
				disaDTO1.setCumulCnps(journalierSup3231CNPS);
				disaDTO1.setCumulPfAt(journalierSup3231ATPF);
				listImprimebulletin.add(disaDTO1);
				
				DisaMensuelDTO disaDTO2 = new DisaMensuelDTO();
				disaDTO2.setCategorie("Mensuels inferieurs ou egaux a  70 000 F par mois");
			Double[] tab1 = new Double[2];
			   tab1=bulletinPaieService.MensuelBaseCnpsInf(periodePaie);
				disaDTO2.setNbSalarie(tab1[0].intValue());
				disaDTO2.setCumulCnps(tab1[1]);
				disaDTO2.setCumulPfAt(tab1[0].intValue()*tab1[1]);
				listImprimebulletin.add(disaDTO2);
				
				DisaMensuelDTO disaDTO3 = new DisaMensuelDTO();
				disaDTO3.setCategorie("Mensuels superieurs a 70 000 F par mois et inferieurs ou egaux a  1 647 315 F par mois");
				Double[] tab0 = new Double[2];
				tab0=bulletinPaieService.MensuelBaseCnpsSup70000(periodePaie);
				disaDTO3.setNbSalarie(tab0[0].intValue());
				disaDTO3.setCumulCnps(tab0[1]);
				disaDTO3.setCumulPfAt(tab0[0].intValue()*70000d);
				listImprimebulletin.add(disaDTO3);
				
				DisaMensuelDTO disaDTO4 = new DisaMensuelDTO();
				disaDTO4.setCategorie("Mensuels superieurs a  1 647 315 F par mois");
				Double[] tab = new Double[2];
				tab=bulletinPaieService.MensuelBaseCnpsSup(periodePaie);
				disaDTO4.setNbSalarie(tab[0].intValue());
				//mensuelSup1647315CNPS=1647315d;
				disaDTO4.setCumulCnps(tab[1]);
				disaDTO4.setCumulPfAt(tab[0].intValue()*70000d);
				listImprimebulletin.add(disaDTO4);
				
				DisaMensuelDTO disaDTO5 = new DisaMensuelDTO();
				disaDTO5.setCategorie("TOTAL ");
				disaDTO5.setNbSalarie(disaDTO4.getNbSalarie()+disaDTO3.getNbSalarie()+disaDTO2.getNbSalarie());
				disaDTO5.setCumulCnps(tab[1]+tab0[1]+tab1[1]);
				disaDTO5.setCumulPfAt(disaDTO3.getNbSalarie()*70000d+disaDTO4.getNbSalarie()*70000d+disaDTO2.getCumulPfAt());
				listImprimebulletin.add(disaDTO5);
				
				Double cumulCnps = 0d;
				Double cumulAt = 0d;
				Double cumulPf = 0d;
				
				cumulCnps = journalierInf3231CNPS + journalierSup3231CNPS + mensuelInf70000CNPS + mensuelSup70000CNPS + mensuelSup1647315CNPS;
				cumulAt = journalierInf3231AT + journalierSup3231AT + mensuelInf70000AT + mensuelSup70000AT + mensuelSup1647315AT;
				cumulPf	= journalierInf3231PF + journalierSup3231PF + mensuelInf70000PF + mensuelSup70000PF + mensuelSup1647315PF; 	
						
				modelMap.addAttribute("cumulCnps", disaDTO5.getCumulCnps());
				modelMap.addAttribute("cumulPf",disaDTO5.getCumulPfAt());
				modelMap.addAttribute("cumulAt", disaDTO5.getCumulPfAt());
				
			Societe compagnie=	societeRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Societe not found for id " + 1L));
			tAt=compagnie.getTxacctr();
			tPf=compagnie.getTxprest();
			tCnps=compagnie.getTxretraite();
				modelMap.addAttribute("tauxCumulAt", tAt+" % = ");
				modelMap.addAttribute("tauxCumulPf", tPf+" % = ");
				modelMap.addAttribute("tauxCumulCnps", tCnps+" % = ");
				
				
				if(tCnps != null)
					modelMap.addAttribute("resultCumulCnps", Utils.formattingAmount(Math.rint((disaDTO5.getCumulCnps()*tCnps)/100)));
				
				if(tPf != null)
					modelMap.addAttribute("resultCumulPf",Utils.formattingAmount(Math.rint((disaDTO5.getCumulPfAt()*tPf)/100)));
				
				if(tAt != null)
					modelMap.addAttribute("resultCumulAt",Utils.formattingAmount(Math.rint((disaDTO5.getCumulPfAt()*tAt)/100)));
				
				modelMap.addAttribute("SomCumul",Utils.formattingAmount(Math.rint((disaDTO5.getCumulPfAt()*tAt)/100)+Math.rint((disaDTO5.getCumulPfAt()*tPf)/100)+Math.rint((disaDTO5.getCumulCnps()*tCnps)/100)));
				modelMap.addAttribute("dcumulcnps",Utils.formattingAmount(Math.rint(disaDTO5.getCumulCnps())));
				modelMap.addAttribute("dPfAt",Utils.formattingAmount(Math.rint(disaDTO5.getCumulPfAt())));
				String moisk="";
				if(periodePaie.getMois().getId()<10L){
					   modelMap.addAttribute("moisk", periodePaie.getMois().getId().toString());
				          moisk=periodePaie.getMois().getId().toString();}
					else {
						modelMap.addAttribute("moisk", periodePaie.getMois().getId().toString().substring(0, 1)+periodePaie.getMois().getId().toString().substring(1));
						moisk=periodePaie.getMois().getId().toString().substring(0, 1)+periodePaie.getMois().getId().toString().substring(1);}
					String trimeste="";
					if(periodePaie.getMois().getId()<=3)trimeste="1T ";
					if(periodePaie.getMois().getId()>=4 && periodePaie.getMois().getId()<=6 )trimeste="2T ";
					if(periodePaie.getMois().getId()>=7 && periodePaie.getMois().getId()<=9 )trimeste="3T ";
					if(periodePaie.getMois().getId()>=10 && periodePaie.getMois().getId()<=12 )trimeste="4T ";
					modelMap.addAttribute("trimest", trimeste);
					if(periodePaie.getMois().getId()<10L)
				     modelMap.addAttribute("periodePaies",trimeste+"0"+moisk);	
					else
						 modelMap.addAttribute("periodePaies",trimeste+moisk);
					 modelMap.addAttribute("periodePaie",periodePaie);	
				modelMap.addAttribute("societe", societeRepository.findById(1L));
					
					JRDataSource jrDatasource = null;
					
					//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
					//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
					GenericDataSource<DisaMensuelDTO> dsStudent =  new GenericDataSource<DisaMensuelDTO>(DisaMensuelDTO.class);
					try {
						jrDatasource = dsStudent.create(null, listImprimebulletin);
						//System.out.println("----------- jr data source "+jrDatasource.toString());
					} catch (JRException e) {
						e.printStackTrace();
					}

					
				
					
					
					modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/front-end/images/logoCNPS.png"));
				
					modelMap.addAttribute("monBrutTotal",Utils.formattingAmount(monBrutTotal));
				
					
					modelMap.addAttribute("datasource", jrDatasource);
					
					if(print.equalsIgnoreCase("p"))
						modelMap.addAttribute("format", "pdf");
					if(print.equalsIgnoreCase("e"))
						modelMap.addAttribute("format", "xls");
					
					logger.info("Bulletin individuel imprimer");
		}
		
		return view; //mav;
		
		
	}
@RequestMapping(value = "/jrFdfp", method = RequestMethod.GET)	
public String ImprimerFdfpTrim(ModelMap modelMap,@RequestParam(value="periode", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) {
		
		String view="fdfppdf";
		
		Long encours=idmois;
		PeriodePaie periodePaie = new PeriodePaie();
		List<PeriodePaie> listPeriodepaie = new ArrayList<PeriodePaie>();
		try {
			periodePaie = periodePaieService.findPeriodePaie(idmois);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<DisaMensuelDTO> listImprimebulletin=  new ArrayList<DisaMensuelDTO>();
		
		if(periodePaie.getId() != null){
			

			Double mensuelTotalBrut1= 0d;	Double mensuelmasseSalariale= 0d;
			Double mensuelTotalBrut2= 0d;
			Double mensuelTotalBrut3= 0d;
		
			
	
				
				//Recuperation des bulletins pour cette exercie et pour le personnel
				List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin= bulletinPaieService.findBulletinByPeriodePaieContract(periodePaie.getId());
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls : listBulletin){
					mensuelTotalBrut1=mensuelTotalBrut1+bulls.getBrutImposable();
					if(bulls.getContratPersonnel().getTypeContrat().getId()==1L ||bulls.getContratPersonnel().getTypeContrat().getId()==2L )
					      mensuelmasseSalariale=mensuelmasseSalariale+(bulls.getTotalmassesalarial());
				}
				/*List<BulletinPaie> listBulletin2 = new ArrayList<BulletinPaie>();
				PeriodePaie	periodePaie1=periodePaieService.findPeriodePaie(encours+1L);
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin2= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie1, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls2 : listBulletin2){
					mensuelTotalBrut2=mensuelTotalBrut2+bulls2.getTotalbrut();
				}
				List<BulletinPaie> listBulletin3 = new ArrayList<BulletinPaie>();
				PeriodePaie	periodePaie2=periodePaieService.findPeriodePaie(encours+2L);
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin3= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie2, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls3 : listBulletin3){
					mensuelTotalBrut3=mensuelTotalBrut3+bulls3.getTotalbrut();
				}
				//System.out.println("####### bull : "+listBulletin.getc.getPrenom()+"  ---- "+listBulletin.toString());
				
							
				Double cumulTotalBrut = mensuelTotalBrut1+mensuelTotalBrut2+mensuelTotalBrut3;*/
				
		
			
			DisaMensuelDTO disaDTO = new DisaMensuelDTO();
			/*disaDTO.setCategorie("14 TAXE D APPRENTISSAGE");
			disaDTO.setValtx(0.4d);
			disaDTO.setCumulCnps(Math.rint(cumulTotalBrut));
			disaDTO.setCumulPfAt(Math.rint(cumulTotalBrut*0.4d/100));
			
			disaDTO.setMontantcumulCnps(Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			disaDTO.setCumulPfAt(Math.rint(cumulTotalBrut*0.4d/100));
			disaDTO.setMontantcumulPfAt(Utils.formattingAmount(Math.rint(cumulTotalBrut*0.4d/100)));*/
			
			modelMap.addAttribute("libelle1","14 TAXE D APPRENTISSAGE");
			//modelMap.addAttribute("cod1lib", "0 6");
			modelMap.addAttribute("libeff1",0.4d);
			modelMap.addAttribute("libSb1",Utils.formattingAmount(Math.rint(mensuelTotalBrut1)));
			modelMap.addAttribute("lib1mt",Utils.formattingAmount(Math.rint(mensuelTotalBrut1*0.4d/100)));
			
			
			listImprimebulletin.add(disaDTO);
			
			DisaMensuelDTO disaDTO1 = new DisaMensuelDTO();
			/*disaDTO1.setCategorie("15 TAXE A LA FORMATION PROFESSIONNELLE CONTINUE");
			disaDTO1.setValtx(0.6d);
			disaDTO1.setCumulCnps(Math.rint(cumulTotalBrut));
			disaDTO1.setMontantcumulCnps(Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			disaDTO1.setMontantcumulPfAt(Utils.formattingAmount(Math.rint(cumulTotalBrut*0.6d/100)));*/
			
			
			modelMap.addAttribute("libelle2","15 TAXE A LA FORMATION PROFESSIONNELLE CONTINUE part complementaire.....");
			//modelMap.addAttribute("cod2lib", "0 6");
			modelMap.addAttribute("libeff2",0.6d);
			modelMap.addAttribute("libSb2",Utils.formattingAmount(Math.rint(mensuelTotalBrut1)));
			modelMap.addAttribute("lib2mt",Utils.formattingAmount(Math.rint(mensuelTotalBrut1*0.6d/100)));
			listImprimebulletin.add(disaDTO1);
			
	
			 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
	
				JRDataSource jrDatasource = null;
				
				//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
				//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
				GenericDataSource<DisaMensuelDTO> dsStudent =  new GenericDataSource<DisaMensuelDTO>(DisaMensuelDTO.class);
				try {
					jrDatasource = dsStudent.create(null, listImprimebulletin);
					//System.out.println("----------- jr data source "+jrDatasource.toString());
				} catch (JRException e) {
					e.printStackTrace();
				}

				
			
				//System.out.println(" la chemin est ------------ "+request.getSession().getServletContext().getRealPath("/WEB-INF/classes/"));
				
				//modelMap.addAttribute("embleme", request.getSession().getServletContext().getRealPath("/resources/images/embleme.png"));
				//Pour le deploiement
				//modelMap.addAttribute("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/WEB-INF/classes")+"\\");
				//modelMap.addAttribute("SUBREPORT_DIR", "D:\\Projets java\\Workspace Education\\RhPaie\\src\\main\\resources\\");
//				modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
				
			modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/front-end/images/dgi_logo1.jpg"));
				//modelMap.addAttribute("exercice", annee.getAnnee());
				//modelMap.addAttribute("raisonSocial", utilisateurCourant.getAgence().getRaisonSocial());
				//modelMap.addAttribute("agence", utilisateurCourant.getAgenceAffiche());
				modelMap.addAttribute("periodePaie", periodePaie);
				
				modelMap.addAttribute("datasource", jrDatasource);
				if(periodePaie.getMois().getId()<10L)
				   modelMap.addAttribute("moisk","0|"+ periodePaie.getMois().getId().toString());
				else 
					modelMap.addAttribute("moisk", periodePaie.getMois().getId().toString().substring(0, 1)+"|"+periodePaie.getMois().getId().toString().substring(1));
				String trimeste="";
				if(periodePaie.getMois().getId()<=3)trimeste="1|T";
				if(periodePaie.getMois().getId()>=4 && periodePaie.getMois().getId()<=6 )trimeste="2|T";
				if(periodePaie.getMois().getId()>=7 && periodePaie.getMois().getId()<=9 )trimeste="3|T";
				if(periodePaie.getMois().getId()>=10 && periodePaie.getMois().getId()<=12 )trimeste="4|T";
				modelMap.addAttribute("trimest", trimeste);
			//	modelMap.addAttribute("nocpteCont", trimeste);
				modelMap.addAttribute("annee", periodePaie.getAnnee().getAnnee().substring(2, 4).substring(0, 1)+"|"+periodePaie.getAnnee().getAnnee().substring(2, 4).substring(1));
				//	modelMap.addAttribute("raisonsoc", "CGECI");
				modelMap.addAttribute("societe", societeRepository.findById(1L));
				String  output = societeRepository.findById(1L).get().getCpteContrib();
				//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
			//	System.out.println(output.length);
				modelMap.addAttribute("1i", output.substring(0,1));
				modelMap.addAttribute("2i", output.substring(1,2));
				//	modelMap.addAttribute("2i", output[1]);
				modelMap.addAttribute("3i", output.substring(2, 3));
				modelMap.addAttribute("4i", output.substring(3, 4));
				modelMap.addAttribute("5i", output.substring(4, 5));
				modelMap.addAttribute("6i", output.substring(5, 6));
				modelMap.addAttribute("7i", output.substring(6, 7));
				modelMap.addAttribute("8i", output.substring(7, 8));
				modelMap.addAttribute("effectif", listBulletin.size());
				modelMap.addAttribute("libSommt",Utils.formattingAmount((Math.rint(mensuelTotalBrut1*0.4d/100))+Math.rint(mensuelTotalBrut1*0.6d/100)));
				
				
					Double[] tab = new Double[2];
					tab=bulletinPaieService.MasseSalarialdeLexo(periodePaie);
					modelMap.addAttribute("cumulfdp",Utils.formattingAmount(Math.rint(tab[1]*1.2/100)));
				     modelMap.addAttribute("txmassSalariale",Utils.formattingAmount(Math.rint(tab[1]*1.2/100)));
				     modelMap.addAttribute("massSalariale",Utils.formattingAmount(Math.rint(tab[1])));
				
					
				
				
				if(print.equalsIgnoreCase("p"))
					modelMap.addAttribute("format", "pdf");
				if(print.equalsIgnoreCase("e"))
					modelMap.addAttribute("format", "xls");
				
				logger.info("Bulletin individuel imprimer");
		}else{}
		
		return view; //mav;
		
		
	}


/*	
@RequestMapping(value = "/jrFdfp", method = RequestMethod.GET)	
public String ImprimerFdfpTrim(ModelMap modelMap,@RequestParam(value="periode", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) {
		
		String view="fdfppdf";
		
		Long encours=idmois;
		PeriodePaie periodePaie = new PeriodePaie();
		List<PeriodePaie> listPeriodepaie = new ArrayList<PeriodePaie>();
		try {
			periodePaie = periodePaieService.findPeriodePaie(idmois);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<DisaMensuelDTO> listImprimebulletin=  new ArrayList<DisaMensuelDTO>();
		
		if(periodePaie.getId() != null){
			

			Double mensuelTotalBrut1= 0d;
			Double mensuelTotalBrut2= 0d;
			Double mensuelTotalBrut3= 0d;
		
			
	
				
				//Recuperation des bulletins pour cette exercie et pour le personnel
				List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls : listBulletin){
					mensuelTotalBrut1=mensuelTotalBrut1+bulls.getTotalbrut();
				}
				List<BulletinPaie> listBulletin2 = new ArrayList<BulletinPaie>();
				PeriodePaie	periodePaie1=periodePaieService.findPeriodePaie(encours+1L);
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin2= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie1, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls2 : listBulletin2){
					mensuelTotalBrut2=mensuelTotalBrut2+bulls2.getTotalbrut();
				}
				List<BulletinPaie> listBulletin3 = new ArrayList<BulletinPaie>();
				PeriodePaie	periodePaie2=periodePaieService.findPeriodePaie(encours+2L);
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin3= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie2, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls3 : listBulletin3){
					mensuelTotalBrut3=mensuelTotalBrut3+bulls3.getTotalbrut();
				}
				//System.out.println("####### bull : "+listBulletin.getc.getPrenom()+"  ---- "+listBulletin.toString());
				
							
				Double cumulTotalBrut = mensuelTotalBrut1;//+mensuelTotalBrut2+mensuelTotalBrut3
				
		
			
			DisaMensuelDTO disaDTO = new DisaMensuelDTO();
			disaDTO.setCategorie("14 TAXE D APPRENTISSAGE");
			disaDTO.setValtx(0.4d);
			disaDTO.setCumulCnps(Math.rint(cumulTotalBrut));
			disaDTO.setCumulPfAt(Math.rint(cumulTotalBrut*0.4d/100));
			
			disaDTO.setMontantcumulCnps(Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			disaDTO.setCumulPfAt(Math.rint(cumulTotalBrut*0.4d/100));
			disaDTO.setMontantcumulPfAt(Utils.formattingAmount(Math.rint(cumulTotalBrut*0.4d/100)));
			
			modelMap.addAttribute("libelle1","14 TAXE D APPRENTISSAGE");
			//modelMap.addAttribute("cod1lib", "0 6");
			modelMap.addAttribute("libeff1",0.4d);
			modelMap.addAttribute("libSb1",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			modelMap.addAttribute("lib1mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*0.4d/100)));
			
			
			listImprimebulletin.add(disaDTO);
			
			DisaMensuelDTO disaDTO1 = new DisaMensuelDTO();
			disaDTO1.setCategorie("15 TAXE A LA FORMATION PROFESSIONNELLE CONTINUE");
			disaDTO1.setValtx(0.6d);
			disaDTO1.setCumulCnps(Math.rint(cumulTotalBrut));
			disaDTO1.setMontantcumulCnps(Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			disaDTO1.setMontantcumulPfAt(Utils.formattingAmount(Math.rint(cumulTotalBrut*0.6d/100)));
			
			
			modelMap.addAttribute("libelle2","15 TAXE A LA FORMATION PROFESSIONNELLE CONTINUE part complementaire.....");
			//modelMap.addAttribute("cod2lib", "0 6");
			modelMap.addAttribute("libeff2",0.6d);
			modelMap.addAttribute("libSb2",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			modelMap.addAttribute("lib2mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*0.6d/100)));
			listImprimebulletin.add(disaDTO1);
			
	
			 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
	
				JRDataSource jrDatasource = null;
				
				//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
				//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
				GenericDataSource<DisaMensuelDTO> dsStudent =  new GenericDataSource<DisaMensuelDTO>(DisaMensuelDTO.class);
				try {
					jrDatasource = dsStudent.create(null, listImprimebulletin);
					//System.out.println("----------- jr data source "+jrDatasource.toString());
				} catch (JRException e) {
					e.printStackTrace();
				}

				
			
				//System.out.println(" la chemin est ------------ "+request.getSession().getServletContext().getRealPath("/WEB-INF/classes/"));
				
				//modelMap.addAttribute("embleme", request.getSession().getServletContext().getRealPath("/resources/images/embleme.png"));
				//Pour le deploiement
				//modelMap.addAttribute("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/WEB-INF/classes")+"\\");
				//modelMap.addAttribute("SUBREPORT_DIR", "D:\\Projets java\\Workspace Education\\RhPaie\\src\\main\\resources\\");
//				modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
				
			//	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/images/logoCNPS.png"));
				//modelMap.addAttribute("exercice", annee.getAnnee());
				//modelMap.addAttribute("raisonSocial", utilisateurCourant.getAgence().getRaisonSocial());
				//modelMap.addAttribute("agence", utilisateurCourant.getAgenceAffiche());
				modelMap.addAttribute("periodePaie", periodePaie);
				
				modelMap.addAttribute("datasource", jrDatasource);
				if(periodePaie.getMois().getId()<10L)
				   modelMap.addAttribute("moisk","0|"+ periodePaie.getMois().getId().toString());
				else 
					modelMap.addAttribute("moisk", periodePaie.getMois().getId().toString().substring(0, 1)+"|"+periodePaie.getMois().getId().toString().substring(1));
				String trimeste="";
				if(periodePaie.getMois().getId()<=3)trimeste="1|T";
				if(periodePaie.getMois().getId()>=4 && periodePaie.getMois().getId()<=6 )trimeste="2|T";
				if(periodePaie.getMois().getId()>=7 && periodePaie.getMois().getId()<=9 )trimeste="3|T";
				if(periodePaie.getMois().getId()>=10 && periodePaie.getMois().getId()<=12 )trimeste="4|T";
				modelMap.addAttribute("trimest", trimeste);
			//	modelMap.addAttribute("nocpteCont", trimeste);
				modelMap.addAttribute("annee", periodePaie.getAnnee().getAnnee().substring(2, 4).substring(0, 1)+"|"+periodePaie.getAnnee().getAnnee().substring(2, 4).substring(1));
				modelMap.addAttribute("raisonsoc", "CGECI");
				modelMap.addAttribute("societe", societeRepository.findOne(1L));
				String  output = societeRepository.findOne(1L).getCpteContrib(); 
				//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
			//	System.out.println(output.length);
				modelMap.addAttribute("1i", output.substring(0,1));
				modelMap.addAttribute("2i", output.substring(1,2));
				//	modelMap.addAttribute("2i", output[1]);
				modelMap.addAttribute("3i", output.substring(2, 3));
				modelMap.addAttribute("4i", output.substring(3, 4));
				modelMap.addAttribute("5i", output.substring(4, 5));
				modelMap.addAttribute("6i", output.substring(5, 6));
				modelMap.addAttribute("7i", output.substring(6, 7));
				modelMap.addAttribute("8i", output.substring(7, 8));
				if(print.equalsIgnoreCase("p"))
					modelMap.addAttribute("format", "pdf");
				if(print.equalsIgnoreCase("e"))
					modelMap.addAttribute("format", "xls");
				
				logger.info("Bulletin individuel imprimer");
		}else{}
		
		return view; //mav;
		
		
	}
*/
/*@RequestMapping(value = "/jrIts", method = RequestMethod.GET)	
public String ImprimerItsTrim(ModelMap modelMap,@RequestParam(value="periode", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) {
		
		String view="itspdf";
		
		Long encours=idmois;
		PeriodePaie periodePaie = new PeriodePaie();
		List<PeriodePaie> listPeriodepaie = new ArrayList<PeriodePaie>();
		try {
			periodePaie = periodePaieService.findPeriodePaie(idmois);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<DisaMensuelDTO> listImprimebulletin=  new ArrayList<DisaMensuelDTO>();
		
		if(periodePaie.getId() != null){
			
			Double mensuelTotalBrut= 0d;
			Double mensuelTotalBrut1= 0d;Double mensuelTotalIgr1= 0d;Double mensuelTotalIgr2= 0d;Double mensuelTotalIgr3= 0d;
			Double mensuelTotalBrut2= 0d;Double mensuelTotalCn1= 0d;Double mensuelTotalCn2= 0d;Double mensuelTotalCn3= 0d;
			Double mensuelTotalBrut3= 0d;Double mensuelTotalIs1= 0d;Double mensuelTotalIs2= 0d;Double mensuelTotalIs3= 0d;
		
			
	
				
				//Recuperation des bulletins pour cette exercie et pour le personnel
				List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls : listBulletin){
					mensuelTotalBrut1=mensuelTotalBrut1+bulls.getTotalbrut();
					mensuelTotalIgr1=mensuelTotalIgr1+bulls.getIgr();
					mensuelTotalCn1=mensuelTotalCn1+bulls.getCn();
					mensuelTotalIs1=mensuelTotalIs1+bulls.getIts();
				}
				List<BulletinPaie> listBulletin2 = new ArrayList<BulletinPaie>();
				PeriodePaie	periodePaie1=periodePaieService.findPeriodePaie(encours+1L);
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin2= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie1, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls2 : listBulletin2){
					mensuelTotalBrut2=mensuelTotalBrut2+bulls2.getTotalbrut();
					mensuelTotalIgr2=mensuelTotalIgr2+bulls2.getIgr();
					mensuelTotalCn2=mensuelTotalCn2+bulls2.getCn();
					mensuelTotalIs2=mensuelTotalIs2+bulls2.getIts();
				}
				List<BulletinPaie> listBulletin3 = new ArrayList<BulletinPaie>();
				PeriodePaie	periodePaie2=periodePaieService.findPeriodePaie(encours+2L);
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin3= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie2, true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls3 : listBulletin3){
					mensuelTotalBrut3=mensuelTotalBrut3+bulls3.getTotalbrut();
					mensuelTotalIgr3=mensuelTotalIgr3+bulls3.getIgr();
					mensuelTotalCn3=mensuelTotalCn3+bulls3.getCn();
					mensuelTotalIs3=mensuelTotalIs3+bulls3.getIts();
				}
				//System.out.println("####### bull : "+listBulletin.getc.getPrenom()+"  ---- "+listBulletin.toString());
				
							
				Double cumulTotalBrut = mensuelTotalBrut1+mensuelTotalBrut2+mensuelTotalBrut3;
				
		
			
			DisaMensuelDTO disaDTO = new DisaMensuelDTO();
			modelMap.addAttribute("libelle1", " IS ( Regime general seulement).");
			modelMap.addAttribute("cod1lib", "0|1");
			//disaDTO.setCategorie("0 1  IS ( Regime general seulement).");
			//disaDTO.setValtx(null);
			Double isTrim=mensuelTotalIs3+mensuelTotalIs2+mensuelTotalIs1;
		//	disaDTO.setCumulCnps(mensuelTotalIs3+mensuelTotalIs2+mensuelTotalIs1);
			modelMap.addAttribute("lib1mt",Utils.formattingAmount(Math.rint(mensuelTotalIs3+mensuelTotalIs2+mensuelTotalIs1)));
			//disaDTO.setCumulPfAt(cumulTotalBrut*0.4d/100);
			listImprimebulletin.add(disaDTO);
			
			DisaMensuelDTO disaDTO1 = new DisaMensuelDTO();
			modelMap.addAttribute("libelle2"," CN (Regime general seulement)");
			modelMap.addAttribute("cod2lib", "0|2");
			//disaDTO1.setValtx(0.6d);
			Double cnTrim=mensuelTotalCn3+mensuelTotalCn1+mensuelTotalCn2;
			modelMap.addAttribute("lib2mt",Utils.formattingAmount(Math.rint(mensuelTotalCn3+mensuelTotalCn1+mensuelTotalCn2)));
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO1);
			
			DisaMensuelDTO disaDTO2= new DisaMensuelDTO();
			modelMap.addAttribute("libelle3"," IGR (Régime general)");
			modelMap.addAttribute("cod3lib", "0|3");
			//disaDTO1.setValtx(0.6d);
			Double igrTrim=mensuelTotalIgr3+mensuelTotalIgr1+mensuelTotalIgr2;
			modelMap.addAttribute("lib3mt",Utils.formattingAmount(Math.rint(mensuelTotalIgr3+mensuelTotalIgr1+mensuelTotalIgr2)));
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO2);
			
			DisaMensuelDTO disaDTO3= new DisaMensuelDTO();
			modelMap.addAttribute("libelle4"," IGR (Régime agricole)");
			modelMap.addAttribute("cod4lib", "0|4");
			//disaDTO1.setValtx(0.6d);
			//disaDTO3.setCumulCnps(0D);
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO3);
			
			DisaMensuelDTO disaDTO4= new DisaMensuelDTO();
			modelMap.addAttribute("libelle5"," Total des retenues aux salariés (1+2+3+4)");
			modelMap.addAttribute("cod5lib", "0|5");
			//disaDTO1.setValtx(0.6d);
			modelMap.addAttribute("lib5mt",Utils.formattingAmount(cnTrim+isTrim+igrTrim));
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO4);
			DisaMensuelDTO disaDTO5= new DisaMensuelDTO();
			disaDTO5.setCategorie("B - IMPOTS A LA CHARGE DE L'EMPLOYEUR");
			//disaDTO1.setValtx(0.6d);
		//	disaDTO5.setCumulCnps(disaDTO.getCumulCnps()+disaDTO2.getCumulCnps()+disaDTO1.getCumulCnps());
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO5);
			
			
			DisaMensuelDTO disaDTO6= new DisaMensuelDTO();
			disaDTO6.setCategorie("(1) EFFECTIFS INSCRITS DANS L'ENTREPRISE");
			String uo="EFFECTIFS";
			disaDTO6.setValmt(uo);;			
			//disaDTO6.setCumulPfAt(0);
			//disaDTO6.setCumulCnps(Double.parseDouble("MONTANTS"));
			listImprimebulletin.add(disaDTO6);
			
			DisaMensuelDTO disaDTO7= new DisaMensuelDTO();
			modelMap.addAttribute("libelle6"," Personnel local(Regime general)(1,20%)");
			modelMap.addAttribute("cod6lib", "0|6");
			modelMap.addAttribute("libeff6",listBulletin2.size());
			modelMap.addAttribute("libSb6",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			modelMap.addAttribute("lib6mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*1.2/100)));
			//disaDTO7.setValmt(String.valueOf(listBulletin2.size()));			
		//	disaDTO7.setCumulPfAt(cumulTotalBrut);
			//disaDTO7.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDTO7);			
			
			DisaMensuelDTO disaDTO8= new DisaMensuelDTO();
			modelMap.addAttribute("libelle7"," Personnel expatrié(Régime général)(10,40%)");
			modelMap.addAttribute("cod7lib", "0|7");
			//disaDTO8.setValtx((double)listBulletin2.size());			
			//disaDTO8.setCumulPfAt(cumulTotalBrut);
			//disaDTO8.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDTO8);			
			
			DisaMensuelDTO disaDTO9= new DisaMensuelDTO();
			modelMap.addAttribute("libelle8"," Régime agricole (1,20%)");
			modelMap.addAttribute("cod8lib", "0|8");
			//disaDTO9.setValtx((double)listBulletin2.size());			
			//disaDTO9.setCumulPfAt(cumulTotalBrut);
		//	disaDTO9.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDTO9);			
			
			DisaMensuelDTO disaDT10= new DisaMensuelDTO();
			modelMap.addAttribute("libelle9"," Régime fermage (35,00%)");
			modelMap.addAttribute("cod9lib", "0|9");
			disaDT10.setValmt(String.valueOf(listBulletin2.size()));			
			disaDT10.setCumulPfAt(cumulTotalBrut);
			disaDT10.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDT10);		
			
			
			DisaMensuelDTO disaDT11= new DisaMensuelDTO();
			modelMap.addAttribute("libelle10"," Ensemble du personnel (7+8+9+10)");	
			modelMap.addAttribute("cod10lib", "1|0");
			modelMap.addAttribute("libeff10",listBulletin2.size());
			modelMap.addAttribute("libSb10",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			modelMap.addAttribute("lib10mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*1.2/100)));
			disaDT11.setValmt(String.valueOf(listBulletin2.size()));			
			disaDT11.setCumulPfAt(cumulTotalBrut);
			disaDT11.setCumulCnps(disaDTO7.getCumulCnps());
			listImprimebulletin.add(disaDT11);		
			
						
			DisaMensuelDTO disaDT12= new DisaMensuelDTO();
			modelMap.addAttribute("libelle11"," Montant total de la déclaration (05+10)");
			modelMap.addAttribute("cod11lib", "1|1");
			modelMap.addAttribute("lib11mt",Utils.formattingAmount(Math.rint( cnTrim+isTrim+igrTrim+cumulTotalBrut*1.2/100)));
		//	disaDT12.setValtx((double)listBulletin2.size());			
		//	disaDT12.setCumulPfAt(cumulTotalBrut);
			//disaDT12.setCumulCnps(disaDTO7.getCumulCnps()+disaDTO4.getCumulCnps());
			listImprimebulletin.add(disaDT12);
			
		    DisaMensuelDTO disaDT13= new DisaMensuelDTO();
		    modelMap.addAttribute("cod12lib", "1|2");
		    modelMap.addAttribute("libelle12"," Imputation ASDI(*)");		
			//disaDT13.setValtx((double)listBulletin2.size());			
			//disaDT13.setCumulPfAt(cumulTotalBrut);
		//	disaDT13.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDT13);
			
			
			 DisaMensuelDTO disaDT14= new DisaMensuelDTO();
			 modelMap.addAttribute("cod13lib", "1|3");
			 modelMap.addAttribute("libelle13"," Imputation prélèvement 10%(*).");		
			//disaDT14.setValtx((double)listBulletin2.size());			
			//disaDT14.setCumulPfAt(cumulTotalBrut);
			//disaDT14.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDT14);
			
			
			 DisaMensuelDTO disaDT15= new DisaMensuelDTO();
			 modelMap.addAttribute("cod14lib", "1|4");
			 modelMap.addAttribute("libelle14"," Regularisation sur mois precedents.(Etat 301).");		
		//	disaDT15.setValtx((double)listBulletin2.size());			
			//disaDT15.setCumulPfAt(cumulTotalBrut);
		//	disaDT15.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDT15);
			
			
			 DisaMensuelDTO disaDT16= new DisaMensuelDTO();
			 modelMap.addAttribute("cod15lib", "1|5");
			 modelMap.addAttribute("libelle15"," Total à acquiter (11 - 12 - 13 + 14)");
			 modelMap.addAttribute("lib15mt",Utils.formattingAmount(Math.rint(cnTrim+isTrim+igrTrim+cumulTotalBrut*1.2/100)));
			//	disaDT16.setValmt(String.valueOf(listBulletin2.size()));			
			//	disaDT16.setCumulPfAt(cumulTotalBrut);
			//	disaDT16.setCumulCnps(disaDT12.getCumulCnps());
				listImprimebulletin.add(disaDT16);
			
			
			
			
			
			
			
			
			
			
	
			 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
	
				JRDataSource jrDatasource = null;
				
				//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
				//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
				GenericDataSource<DisaMensuelDTO> dsStudent =  new GenericDataSource<DisaMensuelDTO>(DisaMensuelDTO.class);
				try {
					jrDatasource = dsStudent.create(null, listImprimebulletin);
					//System.out.println("----------- jr data source "+jrDatasource.toString());
				} catch (JRException e) {
					e.printStackTrace();
				}

				
			
				//System.out.println(" la chemin est ------------ "+request.getSession().getServletContext().getRealPath("/WEB-INF/classes/"));
				
				//modelMap.addAttribute("embleme", request.getSession().getServletContext().getRealPath("/resources/images/embleme.png"));
				//Pour le deploiement
				//modelMap.addAttribute("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/WEB-INF/classes")+"\\");
				//modelMap.addAttribute("SUBREPORT_DIR", "D:\\Projets java\\Workspace Education\\RhPaie\\src\\main\\resources\\");
//				modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
				
			//	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/images/logoCNPS.png"));
				//modelMap.addAttribute("exercice", annee.getAnnee());
				//modelMap.addAttribute("raisonSocial", utilisateurCourant.getAgence().getRaisonSocial());
				//modelMap.addAttribute("agence", utilisateurCourant.getAgenceAffiche());
				modelMap.addAttribute("periodePaie", periodePaie);
				if(periodePaie.getMois().getId()<10L)
					   modelMap.addAttribute("moisk","0|"+ periodePaie.getMois().getId().toString());
					else 
						modelMap.addAttribute("moisk", periodePaie.getMois().getId().toString().substring(0, 1)+"|"+periodePaie.getMois().getId().toString().substring(1));
					String trimeste="";
					if(periodePaie.getMois().getId()<=3)trimeste="1|T";
					if(periodePaie.getMois().getId()>=4 && periodePaie.getMois().getId()<=6 )trimeste="2|T";
					if(periodePaie.getMois().getId()>=7 && periodePaie.getMois().getId()<=9 )trimeste="3|T";
					if(periodePaie.getMois().getId()>=10 && periodePaie.getMois().getId()<=12 )trimeste="4|T";
					modelMap.addAttribute("trimest", trimeste);
					modelMap.addAttribute("annee", periodePaie.getAnnee().getAnnee().substring(2, 4).substring(0, 1)+"|"+periodePaie.getAnnee().getAnnee().substring(2, 4).substring(1));
				modelMap.addAttribute("raisonsoc", "CGECI");
				modelMap.addAttribute("societe", societeRepository.findOne(1L));
				String  output = societeRepository.findOne(1L).getCpteContrib(); 
				//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
			//	System.out.println(output.length);
				modelMap.addAttribute("1i", output.substring(0,1));
				modelMap.addAttribute("2i", output.substring(1,2));
				//	modelMap.addAttribute("2i", output[1]);
				modelMap.addAttribute("3i", output.substring(2, 3));
				modelMap.addAttribute("4i", output.substring(3, 4));
				modelMap.addAttribute("5i", output.substring(4, 5));
				modelMap.addAttribute("6i", output.substring(5, 6));
				modelMap.addAttribute("7i", output.substring(6, 7));
				modelMap.addAttribute("8i", output.substring(7, 8));
				modelMap.addAttribute("datasource", jrDatasource);
				
				if(print.equalsIgnoreCase("p"))
					modelMap.addAttribute("format", "pdf");
				if(print.equalsIgnoreCase("e"))
					modelMap.addAttribute("format", "xls");
				
				logger.info("Bulletin individuel imprimer");
		}else{}
		
		return view; //mav;
		
		
	}

	*/
	@RequestMapping(value = "/jrItsann", method = RequestMethod.GET)
	
	public String ImprimerItsannTrim(ModelMap modelMap,@RequestParam(value="periode", required=true) Long idmois ,@RequestParam(value="print", required=false) String print, HttpServletRequest request) {
		
		String view="itsannpdf";
		
		Long encours=idmois;
		Exercice periodePaie = new Exercice();
		List<PeriodePaie> listPeriodepaie = new ArrayList<PeriodePaie>();
		try {
			periodePaie = exerciceService.findExo(idmois);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<DisaMensuelDTO> listImprimebulletin=  new ArrayList<DisaMensuelDTO>();
		
		if(periodePaie.getId() != null){
			
			Double mensuelTotalBrut= 0d;
			Double mensuelTotalBrut1= 0d;Double mensuelTotalIgr1= 0d;Double mensuelTotalIgr2= 0d;Double mensuelTotalIgr3= 0d;
			Double mensuelTotalBrut2= 0d;Double mensuelTotalCn1= 0d;Double mensuelTotalCn2= 0d;Double mensuelTotalCn3= 0d;
			Double mensuelTotalBrut3= 0d;Double mensuelTotalIs1= 0d;Double mensuelTotalIs2= 0d;Double mensuelTotalIs3= 0d;
		
			
	
				
				//Recuperation des bulletins pour cette exercie et pour le personnel
				List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
				try {
					//listBulletin = bulletinService.findBulletinByPersonnelAndAnnee(annee, pers);
					//BulletinPaie bull=new BulletinPaie();
					listBulletin= bulletinPaieService.rechercherBulletinAnneeCalculer(periodePaie.getId(), true);
					// listBulletin.add(bull);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(BulletinPaie bulls : listBulletin){
					mensuelTotalBrut1=mensuelTotalBrut1+bulls.getTotalbrut();
					mensuelTotalIgr1=mensuelTotalIgr1+bulls.getIgr();
					mensuelTotalCn1=mensuelTotalCn1+bulls.getCn();
					mensuelTotalIs1=mensuelTotalIs1+bulls.getIts();
				}
			
				
							
				Double cumulTotalBrut = mensuelTotalBrut1+mensuelTotalBrut2+mensuelTotalBrut3;
				
		
			
			DisaMensuelDTO disaDTO = new DisaMensuelDTO();
			modelMap.addAttribute("libelle1", " IS ( Regime general seulement).");
			modelMap.addAttribute("cod1lib", "0|1");
			//disaDTO.setCategorie("0 1  IS ( Regime general seulement).");
			//disaDTO.setValtx(null);
			Double isTrim=mensuelTotalIs1;
		//	disaDTO.setCumulCnps(mensuelTotalIs3+mensuelTotalIs2+mensuelTotalIs1);
			modelMap.addAttribute("lib1mt",Utils.formattingAmount(Math.rint(mensuelTotalIs1)));
			//disaDTO.setCumulPfAt(cumulTotalBrut*0.4d/100);
			listImprimebulletin.add(disaDTO);
			
			DisaMensuelDTO disaDTO1 = new DisaMensuelDTO();
			modelMap.addAttribute("libelle2"," CN (Regime general seulement)");
			modelMap.addAttribute("cod2lib", "0|2");
			//disaDTO1.setValtx(0.6d);
			Double cnTrim=mensuelTotalCn1;
			modelMap.addAttribute("lib2mt",Utils.formattingAmount(Math.rint(mensuelTotalCn1)));
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO1);
			
			DisaMensuelDTO disaDTO2= new DisaMensuelDTO();
			modelMap.addAttribute("libelle3"," IGR (Régime general)");
			modelMap.addAttribute("cod3lib", "0|3");
			//disaDTO1.setValtx(0.6d);
			Double igrTrim=mensuelTotalIgr1;
			modelMap.addAttribute("lib3mt",Utils.formattingAmount(Math.rint(mensuelTotalIgr1)));
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO2);
			
			DisaMensuelDTO disaDTO3= new DisaMensuelDTO();
			modelMap.addAttribute("libelle4"," IGR (Régime agricole)");
			modelMap.addAttribute("cod4lib", "0|4");
			//disaDTO1.setValtx(0.6d);
			//disaDTO3.setCumulCnps(0D);
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO3);
			
			DisaMensuelDTO disaDTO4= new DisaMensuelDTO();
			modelMap.addAttribute("libelle5"," Total des retenues aux salaries (1+2+3+4)");
			modelMap.addAttribute("cod5lib", "0|5");
			//disaDTO1.setValtx(0.6d);
			modelMap.addAttribute("lib5mt",Utils.formattingAmount(cnTrim+isTrim+igrTrim));
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO4);
			/*DisaMensuelDTO disaDTO5= new DisaMensuelDTO();
			disaDTO5.setCategorie("B - IMPOTS A LA CHARGE DE L'EMPLOYEUR");
			//disaDTO1.setValtx(0.6d);
		//	disaDTO5.setCumulCnps(disaDTO.getCumulCnps()+disaDTO2.getCumulCnps()+disaDTO1.getCumulCnps());
			//disaDTO1.setCumulPfAt(cumulTotalBrut*0.6d/100);
			listImprimebulletin.add(disaDTO5);
			
			
			DisaMensuelDTO disaDTO6= new DisaMensuelDTO();
			disaDTO6.setCategorie("(1) EFFECTIFS INSCRITS DANS L'ENTREPRISE");
			String uo="EFFECTIFS";
			disaDTO6.setValmt(uo);;			
			//disaDTO6.setCumulPfAt(0);
			//disaDTO6.setCumulCnps(Double.parseDouble("MONTANTS"));
			listImprimebulletin.add(disaDTO6);*/
			
			DisaMensuelDTO disaDTO7= new DisaMensuelDTO();
			modelMap.addAttribute("libelle6"," Personnel local(Regime general)(1,20%)");
			modelMap.addAttribute("cod6lib", "0|6");
			modelMap.addAttribute("libeff6",listBulletin.size());
			modelMap.addAttribute("libSb6",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			modelMap.addAttribute("lib6mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*1.2/100)));
			//disaDTO7.setValmt(String.valueOf(listBulletin2.size()));			
		//	disaDTO7.setCumulPfAt(cumulTotalBrut);
			//disaDTO7.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDTO7);			
			
			DisaMensuelDTO disaDTO8= new DisaMensuelDTO();
			modelMap.addAttribute("libelle7"," Personnel expatrie(Regime general)(10,40%)");
			modelMap.addAttribute("cod7lib", "0|7");
			//disaDTO8.setValtx((double)listBulletin2.size());			
			//disaDTO8.setCumulPfAt(cumulTotalBrut);
			//disaDTO8.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDTO8);			
			
			DisaMensuelDTO disaDTO9= new DisaMensuelDTO();
			modelMap.addAttribute("libelle8"," Regime agricole (1,20%)");
			modelMap.addAttribute("cod8lib", "0|8");
			//disaDTO9.setValtx((double)listBulletin2.size());			
			//disaDTO9.setCumulPfAt(cumulTotalBrut);
		//	disaDTO9.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDTO9);			
			
			DisaMensuelDTO disaDT10= new DisaMensuelDTO();
			modelMap.addAttribute("libelle9"," Régime fermage (35,00%)");
			modelMap.addAttribute("cod9lib", "0|9");
		/*	disaDT10.setValmt(String.valueOf(listBulletin2.size()));			
			disaDT10.setCumulPfAt(cumulTotalBrut);
			disaDT10.setCumulCnps(cumulTotalBrut*1.2/100);*/
			listImprimebulletin.add(disaDT10);		
			
			
			DisaMensuelDTO disaDT11= new DisaMensuelDTO();
			modelMap.addAttribute("libelle10"," Ensemble du personnel (7+8+9+10)");	
			modelMap.addAttribute("cod10lib", "1|0");
			modelMap.addAttribute("libeff10",listBulletin.size());
			modelMap.addAttribute("libSb10",Utils.formattingAmount(Math.rint(cumulTotalBrut)));
			modelMap.addAttribute("lib10mt",Utils.formattingAmount(Math.rint(cumulTotalBrut*1.2/100)));
		/*	disaDT11.setValmt(String.valueOf(listBulletin2.size()));			
			disaDT11.setCumulPfAt(cumulTotalBrut);
			disaDT11.setCumulCnps(disaDTO7.getCumulCnps());*/
			listImprimebulletin.add(disaDT11);		
			
						
			DisaMensuelDTO disaDT12= new DisaMensuelDTO();
			modelMap.addAttribute("libelle11"," Montant total de la déclaration (05+10)");
			modelMap.addAttribute("cod11lib", "1|1");
			modelMap.addAttribute("lib11mt",Utils.formattingAmount(Math.rint( cnTrim+isTrim+igrTrim+cumulTotalBrut*1.2/100)));
		//	disaDT12.setValtx((double)listBulletin2.size());			
		//	disaDT12.setCumulPfAt(cumulTotalBrut);
			//disaDT12.setCumulCnps(disaDTO7.getCumulCnps()+disaDTO4.getCumulCnps());
			listImprimebulletin.add(disaDT12);
			
		    DisaMensuelDTO disaDT13= new DisaMensuelDTO();
		    modelMap.addAttribute("cod12lib", "1|2");
		    modelMap.addAttribute("libelle12"," Imputation ASDI(*)");		
			//disaDT13.setValtx((double)listBulletin2.size());			
			//disaDT13.setCumulPfAt(cumulTotalBrut);
		//	disaDT13.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDT13);
			
			
			 DisaMensuelDTO disaDT14= new DisaMensuelDTO();
			 modelMap.addAttribute("cod13lib", "1|3");
			 modelMap.addAttribute("libelle13"," Imputation prélèvement 10%(*).");		
			//disaDT14.setValtx((double)listBulletin2.size());			
			//disaDT14.setCumulPfAt(cumulTotalBrut);
			//disaDT14.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDT14);
			
			
			 DisaMensuelDTO disaDT15= new DisaMensuelDTO();
			 modelMap.addAttribute("cod14lib", "1|4");
			 modelMap.addAttribute("libelle14"," Regularisation sur mois precedents.(Etat 301).");		
		//	disaDT15.setValtx((double)listBulletin2.size());			
			//disaDT15.setCumulPfAt(cumulTotalBrut);
		//	disaDT15.setCumulCnps(cumulTotalBrut*1.2/100);
			listImprimebulletin.add(disaDT15);
			
			
			 DisaMensuelDTO disaDT16= new DisaMensuelDTO();
			 modelMap.addAttribute("cod15lib", "1|5");
			 modelMap.addAttribute("libelle15"," Total à acquiter (11 - 12 - 13 + 14)");
			 modelMap.addAttribute("lib15mt",Utils.formattingAmount(Math.rint(cnTrim+isTrim+igrTrim+cumulTotalBrut*1.2/100)));
			//	disaDT16.setValmt(String.valueOf(listBulletin2.size()));			
			//	disaDT16.setCumulPfAt(cumulTotalBrut);
			//	disaDT16.setCumulCnps(disaDT12.getCumulCnps());
				listImprimebulletin.add(disaDT16);
			
			
			
			
			
			
			
			
			
			
	
			 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
	
				JRDataSource jrDatasource = null;
				
				//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
				//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
				GenericDataSource<DisaMensuelDTO> dsStudent =  new GenericDataSource<DisaMensuelDTO>(DisaMensuelDTO.class);
				try {
					jrDatasource = dsStudent.create(null, listImprimebulletin);
					//System.out.println("----------- jr data source "+jrDatasource.toString());
				} catch (JRException e) {
					e.printStackTrace();
				}

				
			
				//System.out.println(" la chemin est ------------ "+request.getSession().getServletContext().getRealPath("/WEB-INF/classes/"));
				
				//modelMap.addAttribute("embleme", request.getSession().getServletContext().getRealPath("/resources/images/embleme.png"));
				//Pour le deploiement
				//modelMap.addAttribute("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/WEB-INF/classes")+"\\");
				//modelMap.addAttribute("SUBREPORT_DIR", "D:\\Projets java\\Workspace Education\\RhPaie\\src\\main\\resources\\");
//				modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
				
			//	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/images/logoCNPS.png"));
				//modelMap.addAttribute("exercice", annee.getAnnee());
				//modelMap.addAttribute("raisonSocial", utilisateurCourant.getAgence().getRaisonSocial());
				//modelMap.addAttribute("agence", utilisateurCourant.getAgenceAffiche());
				modelMap.addAttribute("periodePaie", periodePaie);
				/*if(periodePaie2.getMois().getId()<10L)
					   modelMap.addAttribute("moisk","0|"+ periodePaie2.getMois().getId().toString());
					else 
						modelMap.addAttribute("moisk", periodePaie2.getMois().getId().toString().substring(0, 1)+"|"+periodePaie2.getMois().getId().toString().substring(1));*/
					String trimeste="";
					/*if(periodePaie2.getMois().getId()<=3)trimeste="1|T";
					if(periodePaie2.getMois().getId()>=4 && periodePaie2.getMois().getId()<=6 )trimeste="2|T";
					if(periodePaie2.getMois().getId()>=7 && periodePaie2.getMois().getId()<=9 )trimeste="3|T";
					if(periodePaie2.getMois().getId()>=10 && periodePaie2.getMois().getId()<=12 )*/
						trimeste="4|T";
					modelMap.addAttribute("trimest", trimeste);
					modelMap.addAttribute("annee", periodePaie.getAnnee().substring(2, 4).substring(0, 1)+"|"+periodePaie.getAnnee().substring(2, 4).substring(1));
					modelMap.addAttribute("annee1", periodePaie.getAnnee());
				modelMap.addAttribute("raisonsoc", "CGECI");
				modelMap.addAttribute("societe", societeRepository.findById(1L));
				String  output = societeRepository.findById(1L).get().getCpteContrib();
				//String[] output =  societeRepository.findOne(1L).getCpteContrib().split("w"); 
			//	System.out.println(output.length);
				modelMap.addAttribute("1i", output.substring(0,1));
				modelMap.addAttribute("2i", output.substring(1,2));
				//	modelMap.addAttribute("2i", output[1]);
				modelMap.addAttribute("3i", output.substring(2, 3));
				modelMap.addAttribute("4i", output.substring(3, 4));
				modelMap.addAttribute("5i", output.substring(4, 5));
				modelMap.addAttribute("6i", output.substring(5, 6));
				modelMap.addAttribute("7i", output.substring(6, 7));
				modelMap.addAttribute("8i", output.substring(7, 8));
				modelMap.addAttribute("datasource", jrDatasource);
				
				//if(print.equalsIgnoreCase("p"))
					modelMap.addAttribute("format", "pdf");
			//	if(print.equalsIgnoreCase("e"))
				//	modelMap.addAttribute("format", "xls");
				
				logger.info("Bulletin individuel imprimer");
		}else{}
		
		return view; //mav;
		
		
	}
		
	@RequestMapping(value = "/jrDisaTrimestriel", method = RequestMethod.GET)
public String ImprimerDisaTrimestriel(ModelMap modelMap,@RequestParam(value="periode1", required=true) Long idmois ,@RequestParam(value="tPf1", required=false) Double tPf ,@RequestParam(value="tAt1", required=false) Double tAt ,@RequestParam(value="tCnps1", required=false) Double tCnps ,@RequestParam(value="print1", required=false) String print, HttpServletRequest request)  {
	
		String view="disaTrimestrielpdf";
		Long encours=idmois;
		PeriodePaie periodePaie = new PeriodePaie();
		List<PeriodePaie> listPeriodepaie = new ArrayList<PeriodePaie>();
		try {
			periodePaie = periodePaieService.findPeriodePaie(idmois);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
				
				//1er mois
//////////////////////////////////////////////////////////////////////////////////////
				
				Integer journalierInf3231 = 0;
				Integer journalierSup3231 = 0;
				Integer mensuelInf70000 = 0;
				Integer mensuelSup70000 = 0;
				Integer mensuelSup1647315 = 0;
				
				Double journalierInf3231CNPS = 0d;
				Double journalierSup3231CNPS = 0d;
				Double mensuelInf70000CNPS = 0d;
				Double mensuelSup70000CNPS = 0d;
				Double mensuelSup1647315CNPS = 0d;
				
				Double journalierInf3231AT = 0d;
				Double journalierSup3231AT = 0d;
				Double mensuelInf70000AT = 0d;
				Double mensuelSup70000AT = 0d;
				Double mensuelSup1647315AT = 0d;
				
				Double journalierInf3231PF = 0d;
				Double journalierSup3231PF = 0d;
				Double mensuelInf70000PF = 0d;
				Double mensuelSup70000PF = 0d;
				Double mensuelSup1647315PF = 0d;
				
				
				Double journalierInf3231ATPF = 0d;
				Double journalierSup3231ATPF = 0d;
				Double mensuelInf70000ATPF = 0d;
				Double mensuelSup70000ATPF = 0d;
				Double mensuelSup1647315ATPF = 0d;
				
				Integer nbSalarieNonCnps = 0;
				Integer nbSalarieNonPf = 0;
				
			//	for(Personnel pers : listPersonnel){
					//System.out.println("####### personnel est : "+pers.getPrenom());
					//DisaMensuelDTO disaDTO = new DisaMensuelDTO();
					//disaDTO.setCategorie("Horaires, journaliers et occasionnels infÃ©rieurs ou Ã©gaux Ã  3231 F par iour");
					//disaDTO.setNbSalarie(nbSalarie);
					//disaDTO.setCumulCnps(cumulCnps);
					//disaDTO.setCumulPfAt(cumulPfAt);
					
					//String typSalarie = pers.getTypeSalarie();
					
					//Recuperation des bulletins pour cette exercie et pour le personnel
					List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
					try {
						listBulletin =  bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				//	System.out.println("####### bull : "+pers.getPrenom()+"  ---- "+listBulletin.toString());
					
					Integer nbMois = 0;
					Integer nbMoisBulletin = 0;
					Double nbJour = 0d;
					Double nbHeure = 0d;
					Double somNetImposable = 0d;
					
					Double cumulRetenuePatronal = 0d;
					
					for(BulletinPaie bull : listBulletin){
						String typSalarie = bull.getContratPersonnel().getPersonnel().getTypeSalarie();
						nbMoisBulletin = nbMoisBulletin + 1;
						
						Double salaireBrut = bull.getBrutImposable();
					/*	if(typSalarie.equals("M")){
							if( salaireBrut <= 70000F)
								mensuelInf70000 = mensuelInf70000 + 1;
							else{
								if(salaireBrut <= 1647315F)
									mensuelSup70000 = mensuelSup70000 + 1;
								else
									mensuelSup1647315 = mensuelSup1647315 + 1;
								
							}
						}
						
						if(typSalarie.equals("J")){
							if( salaireBrut <= 3231F)
								journalierInf3231 = journalierInf3231 + 1;
							else
								journalierSup3231 = journalierSup3231 + 1;
								
							
						}
*/
						//Recherche des retenues du bulletin
					/*	List<RetenuePersonnel> listRetenuePersonnel = new ArrayList<RetenuePersonnel>();
						try {
							listRetenuePersonnel = retenuePersonnelService.findActiveRetenuePersonnelByBulletin(bull);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if(listRetenuePersonnel.size()>0){
							for(RetenuePersonnel retenuPers: listRetenuePersonnel){*/
								if(bull.getRetraite()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull.getRetraite();
								
									Double retenuCnps = bull.getRetraite();
									
									if(typSalarie.equals("M")){
										if( salaireBrut <= 70000F){
											mensuelInf70000 = mensuelInf70000 + 1;
										
											if(retenuCnps <= 1647315F)
												mensuelInf70000CNPS = mensuelInf70000CNPS + retenuCnps;
											else
												mensuelInf70000CNPS = mensuelInf70000CNPS + 1647315F;
											
										}
										else{
											if(salaireBrut <= 1647315F){
												mensuelSup70000 = mensuelSup70000 + 1;
												
												if(retenuCnps <= 1647315F)
													mensuelSup70000CNPS = mensuelSup70000CNPS + retenuCnps;
												else
													mensuelSup70000CNPS = mensuelSup70000CNPS + 1647315F;
												
											}
											else{
												mensuelSup1647315 = mensuelSup1647315 + 1;
												if(retenuCnps <= 1647315F)
													mensuelSup1647315CNPS = mensuelSup1647315CNPS + retenuCnps;
												else
													mensuelSup1647315CNPS = mensuelSup1647315CNPS + 1647315F;
												
											}
											
										}
									}
									
									if(typSalarie.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231 = journalierInf3231 + 1;
											
											if(retenuCnps <= 1647315F)
												journalierInf3231CNPS = journalierInf3231CNPS + retenuCnps;
											else
												journalierInf3231CNPS = journalierInf3231CNPS + 1647315F;
											
										}
										else{
											journalierSup3231 = journalierSup3231 + 1;
											if(retenuCnps <= 1647315F)
												journalierSup3231CNPS = journalierSup3231CNPS + retenuCnps;
											else
												journalierSup3231CNPS = journalierSup3231CNPS + 1647315F;
											
										}
										
									}
								}
								
								
								
								
								
								if(bull.getAccidentTravail()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull.getAccidentTravail();
							
									Double retenuAt = bull.getAccidentTravail();
									
									if(typSalarie.equals("M")){
										if( salaireBrut <= 70000F){
										
											if(retenuAt <= 70000F)
												mensuelInf70000AT = mensuelInf70000AT + retenuAt;
											else
												mensuelInf70000AT = mensuelInf70000AT + 70000F;
											
										}
										else{
											if(salaireBrut <= 1647315F){
												
												if(retenuAt <= 70000F)
													mensuelSup70000AT = mensuelSup70000AT + retenuAt;
												else
													mensuelSup70000AT = mensuelSup70000AT + 70000F;
												
											}
											else{
												if(retenuAt <= 70000F)
													mensuelSup1647315AT = mensuelSup1647315AT + retenuAt;
												else
													mensuelSup1647315AT = mensuelSup1647315AT + 70000F;
												
											}
											
										}
									}
									
									if(typSalarie.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231 = journalierInf3231 + 1;
											
											if(retenuAt <= 70000F)
												journalierInf3231AT = journalierInf3231AT + retenuAt;
											else
												journalierInf3231AT = journalierInf3231AT + 70000F;
											
										}
										else{
											if(retenuAt <= 70000F)
												journalierSup3231AT = journalierSup3231AT + retenuAt;
											else
												journalierSup3231AT = journalierSup3231AT + 70000F;
											
										}
										
									}
									
									
								}
								
								if(bull.getPrestationFamiliale()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull.getPrestationFamiliale();
							
									Double retenuPf = bull.getPrestationFamiliale();
									

									if(typSalarie.equals("M")){
										if( salaireBrut <= 70000F){
										
											if(retenuPf <= 70000F)
												mensuelInf70000PF = mensuelInf70000PF + retenuPf;
											else
												mensuelInf70000PF = mensuelInf70000PF + 70000F;
											
										}
										else{
											if(salaireBrut <= 1647315F){
												
												if(retenuPf <= 70000F)
													mensuelSup70000PF = mensuelSup70000PF + retenuPf;
												else
													mensuelSup70000PF = mensuelSup70000PF + 70000F;
												
											}
											else{
												if(retenuPf <= 70000F)
													mensuelSup1647315PF = mensuelSup1647315PF + retenuPf;
												else
													mensuelSup1647315PF = mensuelSup1647315PF + 70000F;
												
											}
											
										}
									}
									
									if(typSalarie.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231 = journalierInf3231 + 1;
											
											if(retenuPf <= 70000F)
												journalierInf3231PF = journalierInf3231PF + retenuPf;
											else
												journalierInf3231PF = journalierInf3231PF + 70000F;
											
										}
										else{
											if(retenuPf <= 70000F)
												journalierSup3231PF = journalierSup3231PF + retenuPf;
											else
												journalierSup3231PF = journalierSup3231PF + 70000F;
											
										}
										
									}
									
								
								}
								
								
								journalierInf3231ATPF = journalierInf3231AT + journalierInf3231PF;
								journalierSup3231ATPF = journalierSup3231AT + journalierSup3231PF;
								mensuelInf70000ATPF = mensuelInf70000AT + mensuelInf70000PF;
								mensuelSup70000ATPF = mensuelSup70000AT + mensuelSup70000PF;
								mensuelSup1647315ATPF = mensuelSup1647315AT + mensuelSup1647315PF;
								
								if(journalierInf3231ATPF > 70000d)
									journalierInf3231ATPF = 70000d;
								
								if(journalierSup3231ATPF > 70000d)
									journalierSup3231ATPF = 70000d;
								
								if(mensuelInf70000ATPF > 70000d)
									mensuelInf70000ATPF = 70000d;
								
								if(mensuelSup70000ATPF > 70000d)
									mensuelSup70000ATPF = 70000d;
								
								if(mensuelSup1647315ATPF > 70000d)
									mensuelSup1647315ATPF = 70000d;
								
								
							//}
						}


							
					

			//	}
//////////////////////////////////////////////////////////////////////////////
				
				//2eme mois
				//////////////////////////////////////////////////////////////////////////////////////

				Integer journalierInf3231Mois2 = 0;
				Integer journalierSup3231Mois2 = 0;
				Integer mensuelInf70000Mois2 = 0;
				Integer mensuelSup70000Mois2 = 0;
				Integer mensuelSup1647315Mois2 = 0;

				Double journalierInf3231CNPSMois2 = 0d;
				Double journalierSup3231CNPSMois2 = 0d;
				Double mensuelInf70000CNPSMois2 = 0d;
				Double mensuelSup70000CNPSMois2 = 0d;
				Double mensuelSup1647315CNPSMois2 = 0d;

				Double journalierInf3231ATMois2 = 0d;
				Double journalierSup3231ATMois2 = 0d;
				Double mensuelInf70000ATMois2 = 0d;
				Double mensuelSup70000ATMois2 = 0d;
				Double mensuelSup1647315ATMois2 = 0d;

				Double journalierInf3231PFMois2 = 0d;
				Double journalierSup3231PFMois2 = 0d;
				Double mensuelInf70000PFMois2 = 0d;
				Double mensuelSup70000PFMois2 = 0d;
				Double mensuelSup1647315PFMois2 = 0d;


				Double journalierInf3231ATPFMois2 = 0d;
				Double journalierSup3231ATPFMois2 = 0d;
				Double mensuelInf70000ATPFMois2 = 0d;
				Double mensuelSup70000ATPFMois2 = 0d;
				Double mensuelSup1647315ATPFMois2 = 0d;

				Integer nbSalarieNonCnpsMois2 = 0;
				Integer nbSalarieNonPfMois2 = 0;

			//	for(Personnel pers : listPersonnelMois2){
					//System.out.println("####### personnel est : "+pers.getPrenom());
					//DisaMensuelDTO disaDTO = new DisaMensuelDTO();
					//disaDTO.setCategorie("Horaires, journaliers et occasionnels infÃ©rieurs ou Ã©gaux Ã  3231 F par iour");
					//disaDTO.setNbSalarie(nbSalarie);
					//disaDTO.setCumulCnps(cumulCnps);
					//disaDTO.setCumulPfAt(cumulPfAt);

				//	String typSalarie = pers.getTypeSalarie();
				PeriodePaie	periodePaie1=periodePaieService.findPeriodePaie(encours+1L);
					//Recuperation des bulletins pour cette exercie et pour le personnel
					List<BulletinPaie> listBulletin2 = new ArrayList<BulletinPaie>();
					try {
						listBulletin2= bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie1, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//System.out.println("####### bull : "+pers.getPrenom()+"  ---- "+listBulletin.toString());

					
					Integer nbMoisBulletin1 = 0;
			
					for(BulletinPaie bull1 : listBulletin2){

						nbMoisBulletin1 = nbMoisBulletin1 + 1;
						String typSalarie = bull1.getContratPersonnel().getPersonnel().getTypeSalarie();
						Double salaireBrut = bull1.getBrutImposable();
				/*		if(typSalarie.equals("M")){
							if( salaireBrut <= 70000F)
								mensuelInf70000Mois2 = mensuelInf70000Mois2 + 1;
							else{
								if(salaireBrut <= 1647315F)
									mensuelSup70000Mois2 = mensuelSup70000Mois2 + 1;
								else
									mensuelSup1647315Mois2 = mensuelSup1647315Mois2 + 1;

							}
						}

						if(typSalarie.equals("J")){
							if( salaireBrut <= 3231F)
								journalierInf3231Mois2 = journalierInf3231Mois2 + 1;
							else
								journalierSup3231Mois2 = journalierSup3231Mois2 + 1;


						}*/

					
						
							
								if(bull1.getRetraite()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull1.getRetraite();

									Double retenuCnps =bull1.getRetraite();

									if(typSalarie.equals("M")){
										if( salaireBrut <= 70000F){
											mensuelInf70000Mois2 = mensuelInf70000Mois2 + 1;

											if(retenuCnps <= 1647315F)
												mensuelInf70000CNPSMois2 = mensuelInf70000CNPSMois2 + retenuCnps;
											else
												mensuelInf70000CNPSMois2 = mensuelInf70000CNPSMois2 + 1647315F;

										}
										else{
											if(salaireBrut <= 1647315F){
												mensuelSup70000Mois2 = mensuelSup70000Mois2 + 1;

												if(retenuCnps <= 1647315F)
													mensuelSup70000CNPSMois2 = mensuelSup70000CNPSMois2 + retenuCnps;
												else
													mensuelSup70000CNPSMois2 = mensuelSup70000CNPSMois2 + 1647315F;

											}
											else{
												mensuelSup1647315Mois2 = mensuelSup1647315Mois2 + 1;
												if(retenuCnps <= 1647315F)
													mensuelSup1647315CNPSMois2 = mensuelSup1647315CNPSMois2 + retenuCnps;
												else
													mensuelSup1647315CNPSMois2 = mensuelSup1647315CNPSMois2 + 1647315F;

											}

										}
									}

									if(typSalarie.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231Mois2 = journalierInf3231Mois2 + 1;

											if(retenuCnps <= 1647315F)
												journalierInf3231CNPSMois2 = journalierInf3231CNPSMois2 + retenuCnps;
											else
												journalierInf3231CNPSMois2 = journalierInf3231CNPSMois2 + 1647315F;

										}
										else{
											journalierSup3231Mois2 = journalierSup3231Mois2 + 1;
											if(retenuCnps <= 1647315F)
												journalierSup3231CNPSMois2 = journalierSup3231CNPSMois2 + retenuCnps;
											else
												journalierSup3231CNPSMois2 = journalierSup3231CNPSMois2 + 1647315F;

										}

									}
								}





								if(bull1.getAccidentTravail()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull1.getAccidentTravail();

									Double retenuAt =bull1.getAccidentTravail();

									if(typSalarie.equals("M")){
										if( salaireBrut <= 70000F){

											if(retenuAt <= 70000F)
												mensuelInf70000ATMois2 = mensuelInf70000ATMois2 + retenuAt;
											else
												mensuelInf70000ATMois2 = mensuelInf70000ATMois2 + 70000F;

										}
										else{
											if(salaireBrut <= 1647315F){

												if(retenuAt <= 70000F)
													mensuelSup70000ATMois2 = mensuelSup70000ATMois2 + retenuAt;
												else
													mensuelSup70000ATMois2 = mensuelSup70000ATMois2 + 70000F;

											}
											else{
												if(retenuAt <= 70000F)
													mensuelSup1647315ATMois2 = mensuelSup1647315ATMois2 + retenuAt;
												else
													mensuelSup1647315ATMois2 = mensuelSup1647315ATMois2 + 70000F;

											}

										}
									}

									if(typSalarie.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231Mois2 = journalierInf3231Mois2 + 1;

											if(retenuAt <= 70000F)
												journalierInf3231ATMois2 = journalierInf3231ATMois2 + retenuAt;
											else
												journalierInf3231ATMois2 = journalierInf3231ATMois2 + 70000F;

										}
										else{
											if(retenuAt <= 70000F)
												journalierSup3231ATMois2 = journalierSup3231ATMois2 + retenuAt;
											else
												journalierSup3231ATMois2 = journalierSup3231ATMois2 + 70000F;

										}

									}


								}

								if(bull1.getPrestationFamiliale()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull1.getPrestationFamiliale();

									Double retenuPf = bull1.getPrestationFamiliale();


									if(typSalarie.equals("M")){
										if( salaireBrut <= 70000F){

											if(retenuPf <= 70000F)
												mensuelInf70000PFMois2 = mensuelInf70000PFMois2 + retenuPf;
											else
												mensuelInf70000PFMois2 = mensuelInf70000PFMois2 + 70000d;

										}
										else{
											if(salaireBrut <= 1647315F){

												if(retenuPf <= 70000F)
													mensuelSup70000PFMois2 = mensuelSup70000PFMois2 + retenuPf;
												else
													mensuelSup70000PFMois2 = mensuelSup70000PFMois2 + 70000F;

											}
											else{
												if(retenuPf <= 70000F)
													mensuelSup1647315PFMois2 = mensuelSup1647315PFMois2 + retenuPf;
												else
													mensuelSup1647315PFMois2 = mensuelSup1647315PFMois2 + 70000F;

											}

										}
									}

									if(typSalarie.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231Mois2 = journalierInf3231Mois2 + 1;

											if(retenuPf <= 70000F)
												journalierInf3231PFMois2 = journalierInf3231PFMois2 + retenuPf;
											else
												journalierInf3231PFMois2 = journalierInf3231PFMois2 + 70000F;

										}
										else{
											if(retenuPf <= 70000F)
												journalierSup3231PFMois2 = journalierSup3231PFMois2 + retenuPf;
											else
												journalierSup3231PFMois2 = journalierSup3231PFMois2 + 70000F;

										}

									}


								}


								journalierInf3231ATPFMois2 = journalierInf3231ATMois2 + journalierInf3231PFMois2;
								journalierSup3231ATPFMois2 = journalierSup3231ATMois2 + journalierSup3231PFMois2;
								mensuelInf70000ATPFMois2 = mensuelInf70000ATMois2 + mensuelInf70000PFMois2;
								mensuelSup70000ATPFMois2 = mensuelSup70000ATMois2 + mensuelSup70000PFMois2;
								mensuelSup1647315ATPFMois2 = mensuelSup1647315ATMois2 + mensuelSup1647315PFMois2;

								if(journalierInf3231ATPFMois2 > 70000d)
									journalierInf3231ATPFMois2 = 70000d;

								if(journalierSup3231ATPFMois2 > 70000d)
									journalierSup3231ATPFMois2 = 70000d;

								if(mensuelInf70000ATPFMois2 > 70000d)
									mensuelInf70000ATPFMois2 = 70000d;

								if(mensuelSup70000ATPFMois2 > 70000d)
									mensuelSup70000ATPFMois2 = 70000d;

								if(mensuelSup1647315ATPFMois2 > 70000d)
									mensuelSup1647315ATPFMois2 = 70000d;


							
						



					}

			//	}
//////////////////////////////////////////////////////////////////////////////////////////

				//3eme mois
//////////////////////////////////////////////////////////////////////////////////////
				
				Integer journalierInf3231Mois3 = 0;
				Integer journalierSup3231Mois3 = 0;
				Integer mensuelInf70000Mois3 = 0;
				Integer mensuelSup70000Mois3 = 0;
				Integer mensuelSup1647315Mois3 = 0;
				
				Double journalierInf3231CNPSMois3 = 0d;
				Double journalierSup3231CNPSMois3 = 0d;
				Double mensuelInf70000CNPSMois3 = 0d;
				Double mensuelSup70000CNPSMois3 = 0d;
				Double mensuelSup1647315CNPSMois3 = 0d;
				
				Double journalierInf3231ATMois3 = 0d;
				Double journalierSup3231ATMois3 = 0d;
				Double mensuelInf70000ATMois3 = 0d;
				Double mensuelSup70000ATMois3 = 0d;
				Double mensuelSup1647315ATMois3 = 0d;
				
				Double journalierInf3231PFMois3 = 0d;
				Double journalierSup3231PFMois3 = 0d;
				Double mensuelInf70000PFMois3 = 0d;
				Double mensuelSup70000PFMois3 = 0d;
				Double mensuelSup1647315PFMois3 = 0d;
				
				
				Double journalierInf3231ATPFMois3 = 0d;
				Double journalierSup3231ATPFMois3 = 0d;
				Double mensuelInf70000ATPFMois3 = 0d;
				Double mensuelSup70000ATPFMois3 = 0d;
				Double mensuelSup1647315ATPFMois3 = 0d;
				
				Integer nbSalarieNonCnpsMois3 = 0;
				Integer nbSalarieNonPfMois3 = 0;
				
			//	for(Personnel pers : listPersonnelMois3){
					//System.out.println("####### personnel est : "+pers.getPrenom());
					//DisaMensuelDTO disaDTO = new DisaMensuelDTO();
					//disaDTO.setCategorie("Horaires, journaliers et occasionnels infÃ©rieurs ou Ã©gaux Ã  3231 F par iour");
					//disaDTO.setNbSalarie(nbSalarie);
					//disaDTO.setCumulCnps(cumulCnps);
					//disaDTO.setCumulPfAt(cumulPfAt);
					
			//		String typSalarie = pers.getTypeSalarie();
					PeriodePaie	periodePaie2=periodePaieService.findPeriodePaie(encours+2L);
					//Recuperation des bulletins pour cette exercie et pour le personnel
					List<BulletinPaie> listBulletin3 = new ArrayList<BulletinPaie>();
					try {
						listBulletin3 = bulletinPaieService.rechercherBulletinMoisCalculer(periodePaie2, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					for(BulletinPaie bull2 : listBulletin3){
						
						nbMoisBulletin = nbMoisBulletin + 1;
						String typSalarie2 = bull2.getContratPersonnel().getPersonnel().getTypeSalarie();
						Double salaireBrut = bull2.getBrutImposable();
				/*		if(typSalarie2.equals("M")){
							if( salaireBrut <= 70000F)
								mensuelInf70000Mois3 = mensuelInf70000Mois3 + 1;
							else{
								if(salaireBrut <= 1647315F)
									mensuelSup70000Mois3 = mensuelSup70000Mois3 + 1;
								else
									mensuelSup1647315Mois3 = mensuelSup1647315Mois3 + 1;
								
							}
						}
						
						if(typSalarie2.equals("J")){
							if( salaireBrut <= 3231F)
								journalierInf3231Mois3 = journalierInf3231Mois3 + 1;
							else
								journalierSup3231Mois3 = journalierSup3231Mois3 + 1;
								
							
						}*/

					
								if(bull2.getRetraite()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull2.getRetraite();
								
									Double retenuCnps = bull2.getRetraite();
									
									if(typSalarie2.equals("M")){
										if( salaireBrut <= 70000F){
											mensuelInf70000Mois3 = mensuelInf70000Mois3 + 1;
										
											if(retenuCnps <= 1647315F)
												mensuelInf70000CNPSMois3 = mensuelInf70000CNPSMois3 + retenuCnps;
											else
												mensuelInf70000CNPSMois3 = mensuelInf70000CNPSMois3 + 1647315F;
											
										}
										else{
											if(salaireBrut <= 1647315F){
												mensuelSup70000Mois3 = mensuelSup70000Mois3 + 1;
												
												if(retenuCnps <= 1647315F)
													mensuelSup70000CNPSMois3 = mensuelSup70000CNPSMois3 + retenuCnps;
												else
													mensuelSup70000CNPSMois3 = mensuelSup70000CNPSMois3 + 1647315F;
												
											}
											else{
												mensuelSup1647315Mois3 = mensuelSup1647315Mois3 + 1;
												if(retenuCnps <= 1647315F)
													mensuelSup1647315CNPSMois3 = mensuelSup1647315CNPSMois3 + retenuCnps;
												else
													mensuelSup1647315CNPSMois3 = mensuelSup1647315CNPSMois3 + 1647315F;
												
											}
											
										}
									}
									
									if(typSalarie2.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231Mois3 = journalierInf3231Mois3 + 1;
											
											if(retenuCnps <= 1647315F)
												journalierInf3231CNPSMois3 = journalierInf3231CNPSMois3 + retenuCnps;
											else
												journalierInf3231CNPSMois3 = journalierInf3231CNPSMois3 + 1647315F;
											
										}
										else{
											journalierSup3231Mois3 = journalierSup3231Mois3 + 1;
											if(retenuCnps <= 1647315F)
												journalierSup3231CNPSMois3 = journalierSup3231CNPSMois3 + retenuCnps;
											else
												journalierSup3231CNPSMois3 = journalierSup3231CNPSMois3 + 1647315F;
											
										}
										
									}
								}
								
								
								
								
								
								if(bull2.getAccidentTravail()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull2.getAccidentTravail();
							
									Double retenuAt = bull2.getAccidentTravail();
									
									if(typSalarie2.equals("M")){
										if( salaireBrut <= 70000F){
										
											if(retenuAt <= 70000F)
												mensuelInf70000ATMois3 = mensuelInf70000ATMois3 + retenuAt;
											else
												mensuelInf70000ATMois3 = mensuelInf70000ATMois3 + 70000F;
											
										}
										else{
											if(salaireBrut <= 1647315F){
												
												if(retenuAt <= 70000F)
													mensuelSup70000ATMois3 = mensuelSup70000ATMois3 + retenuAt;
												else
													mensuelSup70000ATMois3 = mensuelSup70000ATMois3 + 70000F;
												
											}
											else{
												if(retenuAt <= 70000F)
													mensuelSup1647315ATMois3 = mensuelSup1647315ATMois3 + retenuAt;
												else
													mensuelSup1647315ATMois3 = mensuelSup1647315ATMois3 + 70000F;
												
											}
											
										}
									}
									
									if(typSalarie2.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231Mois3 = journalierInf3231Mois3 + 1;
											
											if(retenuAt <= 70000F)
												journalierInf3231ATMois3 = journalierInf3231ATMois3 + retenuAt;
											else
												journalierInf3231ATMois3 = journalierInf3231ATMois3 + 70000F;
											
										}
										else{
											if(retenuAt <= 70000F)
												journalierSup3231ATMois3 = journalierSup3231ATMois3 + retenuAt;
											else
												journalierSup3231ATMois3 = journalierSup3231ATMois3 + 70000F;
											
										}
										
									}
									
									
								}
								
								if(bull2.getPrestationFamiliale()!=null){
									cumulRetenuePatronal = cumulRetenuePatronal + bull2.getPrestationFamiliale();
							
									Double retenuPf = bull2.getPrestationFamiliale();
									

									if(typSalarie2.equals("M")){
										if( salaireBrut <= 70000F){
										
											if(retenuPf <= 70000F)
												mensuelInf70000PFMois3 = mensuelInf70000PFMois3 + retenuPf;
											else
												mensuelInf70000PFMois3 = mensuelInf70000PFMois3 + 70000F;
											
										}
										else{
											if(salaireBrut <= 1647315F){
												
												if(retenuPf <= 70000F)
													mensuelSup70000PFMois3 = mensuelSup70000PFMois3 + retenuPf;
												else
													mensuelSup70000PFMois3 = mensuelSup70000PFMois3 + 70000F;
												
											}
											else{
												if(retenuPf <= 70000F)
													mensuelSup1647315PFMois3 = mensuelSup1647315PFMois3 + retenuPf;
												else
													mensuelSup1647315PFMois3 = mensuelSup1647315PFMois3 + 70000F;
												
											}
											
										}
									}
									
									if(typSalarie2.equals("J")){
										if( salaireBrut <= 3231F){
											journalierInf3231Mois3 = journalierInf3231Mois3 + 1;
											
											if(retenuPf <= 70000F)
												journalierInf3231PFMois3 = journalierInf3231PFMois3 + retenuPf;
											else
												journalierInf3231PFMois3 = journalierInf3231PFMois3 + 70000F;
											
										}
										else{
											if(retenuPf <= 70000F)
												journalierSup3231PFMois3 = journalierSup3231PFMois3 + retenuPf;
											else
												journalierSup3231PFMois3 = journalierSup3231PFMois3 + 70000F;
											
										}
										
									}
									
								
								}
								
								
								journalierInf3231ATPFMois3 = journalierInf3231ATMois3 + journalierInf3231PFMois3;
								journalierSup3231ATPFMois3 = journalierSup3231ATMois3 + journalierSup3231PFMois3;
								mensuelInf70000ATPFMois3 = mensuelInf70000ATMois3 + mensuelInf70000PFMois3;
								mensuelSup70000ATPFMois3 = mensuelSup70000ATMois3 + mensuelSup70000PFMois3;
								mensuelSup1647315ATPFMois3 = mensuelSup1647315ATMois3 + mensuelSup1647315PFMois3;
								
								if(journalierInf3231ATPFMois3 > 70000d)
									journalierInf3231ATPFMois3 = 70000d;
								
								if(journalierSup3231ATPFMois3 > 70000d)
									journalierSup3231ATPFMois3 = 70000d;
								
								if(mensuelInf70000ATPFMois3 > 70000d)
									mensuelInf70000ATPFMois3 = 70000d;
								
								if(mensuelSup70000ATPFMois3 > 70000d)
									mensuelSup70000ATPFMois3 = 70000d;
								
								if(mensuelSup1647315ATPFMois3 > 70000d)
									mensuelSup1647315ATPFMois3 = 70000d;
								
								
						//	}
					

				}
//////////////////////////////////////////////////////////////////////////////
		
				

				List<DisaTrimestrielDTO> listImprimebulletin=  new ArrayList<DisaTrimestrielDTO>();
				
				//if(mois.getId() != null){
					
					DisaTrimestrielDTO disaDTO = new DisaTrimestrielDTO();
					disaDTO.setCategorie("Horaires, journaliers et occasionnels inferieurs ou egaux a  3231 F par jour");
					disaDTO.setNbSalarie1(journalierInf3231);
					disaDTO.setCumulCnps1(journalierInf3231CNPS);
					disaDTO.setCumulPfAt1(journalierInf3231ATPF);
					
					disaDTO.setNbSalarie2(journalierInf3231Mois2);
					disaDTO.setCumulCnps2(journalierInf3231CNPSMois2);
					disaDTO.setCumulPfAt2(journalierInf3231ATPFMois2);
					
					disaDTO.setNbSalarie3(journalierInf3231Mois3);
					disaDTO.setCumulCnps3(journalierInf3231CNPSMois3);
					disaDTO.setCumulPfAt3(journalierInf3231ATPFMois3);
					listImprimebulletin.add(disaDTO);
					
					DisaTrimestrielDTO disaDTO1 = new DisaTrimestrielDTO();
					disaDTO1.setCategorie("Horaires, journaliers et occasionnels superieurs ou egaux a  3231 F par jour");
					disaDTO1.setNbSalarie1(journalierSup3231);
					disaDTO1.setCumulCnps1(journalierSup3231CNPS);
					disaDTO1.setCumulPfAt1(journalierSup3231ATPF);
					
					disaDTO1.setNbSalarie2(journalierSup3231Mois2);
					disaDTO1.setCumulCnps2(journalierSup3231CNPSMois2);
					disaDTO1.setCumulPfAt2(journalierSup3231ATPFMois2);
					
					disaDTO1.setNbSalarie3(journalierSup3231Mois3);
					disaDTO1.setCumulCnps3(journalierSup3231CNPSMois3);
					disaDTO1.setCumulPfAt3(journalierSup3231ATPFMois3);
					listImprimebulletin.add(disaDTO1);
					
					DisaTrimestrielDTO disaDTO2 = new DisaTrimestrielDTO();
					disaDTO2.setCategorie("Mensuels inferieurs ou egaux a  70.000 F par mois");
					disaDTO2.setNbSalarie1(mensuelInf70000);
					disaDTO2.setCumulCnps1(mensuelInf70000CNPS);
					disaDTO2.setCumulPfAt1(mensuelInf70000ATPF);
					
					disaDTO2.setNbSalarie2(mensuelInf70000Mois2);
					disaDTO2.setCumulCnps2(mensuelInf70000CNPSMois2);
					disaDTO2.setCumulPfAt2(mensuelInf70000ATPFMois2);
					
					disaDTO2.setNbSalarie3(mensuelInf70000Mois3);
					disaDTO2.setCumulCnps3(mensuelInf70000CNPSMois3);
					disaDTO2.setCumulPfAt3(mensuelInf70000ATPFMois3);
					listImprimebulletin.add(disaDTO2);
					
					DisaTrimestrielDTO disaDTO3 = new DisaTrimestrielDTO();
					disaDTO3.setCategorie("Mensuels superieurs a  70.000 F par mois et inferieurs ou egaux a  1.647.315 F par mois");
					disaDTO3.setNbSalarie1(mensuelSup70000);
					disaDTO3.setCumulCnps1(mensuelSup70000CNPS);
					disaDTO3.setCumulPfAt1(mensuelSup70000ATPF);
					
					disaDTO3.setNbSalarie2(mensuelSup70000Mois2);
					disaDTO3.setCumulCnps2(mensuelSup70000CNPSMois2);
					disaDTO3.setCumulPfAt2(mensuelSup70000ATPFMois2);
					
					disaDTO3.setNbSalarie3(mensuelSup70000Mois3);
					disaDTO3.setCumulCnps3(mensuelSup70000CNPSMois3);
					disaDTO3.setCumulPfAt3(mensuelSup70000ATPFMois3);
					listImprimebulletin.add(disaDTO3);
					
					DisaTrimestrielDTO disaDTO4 = new DisaTrimestrielDTO();
					disaDTO4.setCategorie("Mensuels superieurs a  1.647.315 F par mois");
					disaDTO4.setNbSalarie1(mensuelSup1647315);
					disaDTO4.setCumulCnps1(mensuelSup1647315CNPS);
					disaDTO4.setCumulPfAt1(mensuelSup1647315ATPF);
					
					disaDTO4.setNbSalarie2(mensuelSup1647315Mois2);
					disaDTO4.setCumulCnps2(mensuelSup1647315CNPSMois2);
					disaDTO4.setCumulPfAt2(mensuelSup1647315ATPFMois2);
					
					disaDTO4.setNbSalarie3(mensuelSup1647315Mois3);
					disaDTO4.setCumulCnps3(mensuelSup1647315CNPSMois3);
					disaDTO4.setCumulPfAt3(mensuelSup1647315ATPFMois3);
					listImprimebulletin.add(disaDTO4);
					
					DisaTrimestrielDTO disaDTO5 = new DisaTrimestrielDTO();
					disaDTO5.setCategorie("TOTAL");
					disaDTO5.setNbSalarie1(journalierInf3231+journalierSup3231+mensuelInf70000+mensuelSup70000+mensuelSup1647315);
					disaDTO5.setCumulCnps1(journalierInf3231CNPS+journalierSup3231CNPS+mensuelInf70000CNPS+mensuelSup70000CNPS+mensuelSup1647315CNPS);
					disaDTO5.setCumulPfAt1(journalierInf3231ATPF+journalierSup3231ATPF+mensuelInf70000ATPF+mensuelSup70000ATPF+mensuelSup1647315ATPF);
					
					disaDTO5.setNbSalarie2(journalierInf3231Mois2+journalierSup3231Mois2+mensuelInf70000Mois2+mensuelSup70000Mois2+mensuelSup1647315Mois2);
					disaDTO5.setCumulCnps2(journalierInf3231CNPSMois2+journalierSup3231CNPSMois2+mensuelInf70000CNPSMois2+mensuelSup70000CNPSMois2+mensuelSup1647315CNPSMois2);
					disaDTO5.setCumulPfAt2(journalierInf3231ATPFMois2+journalierSup3231ATPFMois2+mensuelInf70000ATPFMois2+mensuelSup70000ATPFMois2+mensuelSup1647315ATPFMois2);
					
					disaDTO5.setNbSalarie3(journalierInf3231Mois3+journalierSup3231Mois3+mensuelInf70000Mois3+mensuelSup70000Mois3+mensuelSup1647315Mois3);
					disaDTO5.setCumulCnps3(journalierInf3231CNPSMois3+journalierSup3231CNPSMois3+mensuelInf70000CNPSMois3+mensuelSup70000CNPSMois3+mensuelSup1647315CNPSMois3);
					disaDTO5.setCumulPfAt3(journalierInf3231ATPFMois3+journalierSup3231ATPFMois3+mensuelInf70000ATPFMois3+mensuelSup70000ATPFMois3+mensuelSup1647315ATPFMois3);
					listImprimebulletin.add(disaDTO5);
					
					
					
					Double cumulCnps = 0d;
					Double cumulAt = 0d;
					Double cumulPf = 0d;
					
					Double cumulCnpsMois2 = 0d;
					Double cumulAtMois2 = 0d;
					Double cumulPfMois2 = 0d;
					
					Double cumulCnpsMois3 = 0d;
					Double cumulAtMois3 = 0d;
					Double cumulPfMois3 = 0d;
					
					Double somCumulCnps = 0d;
					Double somCumulAt = 0d;
					Double somCumulPf = 0d;
					
					cumulCnps = journalierInf3231CNPS + journalierSup3231CNPS + mensuelInf70000CNPS + mensuelSup70000CNPS + mensuelSup1647315CNPS;
					cumulAt = journalierInf3231AT + journalierSup3231AT + mensuelInf70000AT + mensuelSup70000AT + mensuelSup1647315AT;
					cumulPf	= journalierInf3231PF + journalierSup3231PF + mensuelInf70000PF + mensuelSup70000PF + mensuelSup1647315PF; 	
					
					cumulCnpsMois2 = journalierInf3231CNPSMois2 + journalierSup3231CNPSMois2 + mensuelInf70000CNPSMois2 + mensuelSup70000CNPSMois2 + mensuelSup1647315CNPSMois2;
					cumulAtMois2 = journalierInf3231ATMois2 + journalierSup3231ATMois2 + mensuelInf70000ATMois2 + mensuelSup70000ATMois2 + mensuelSup1647315ATMois2;
					cumulPfMois2	= journalierInf3231PFMois2 + journalierSup3231PFMois2 + mensuelInf70000PFMois2 + mensuelSup70000PFMois2 + mensuelSup1647315PFMois2; 	
					
					cumulCnpsMois3 = journalierInf3231CNPSMois3 + journalierSup3231CNPSMois3 + mensuelInf70000CNPSMois3 + mensuelSup70000CNPSMois3 + mensuelSup1647315CNPSMois3;
					cumulAtMois3 = journalierInf3231ATMois3 + journalierSup3231ATMois3 + mensuelInf70000ATMois3 + mensuelSup70000ATMois3 + mensuelSup1647315ATMois3;
					cumulPfMois3	= journalierInf3231PFMois3 + journalierSup3231PFMois3 + mensuelInf70000PFMois3 + mensuelSup70000PFMois3 + mensuelSup1647315PFMois3; 	
					
					
					somCumulCnps = cumulCnps + cumulCnpsMois2 + cumulCnpsMois3;
					somCumulAt = cumulAt + cumulAtMois2 + cumulAtMois3;
					somCumulPf = cumulPf + cumulPfMois2 + cumulPfMois3;
					
					modelMap.addAttribute("cumulCnps", somCumulCnps);
					modelMap.addAttribute("cumulPf",somCumulPf);
					modelMap.addAttribute("cumulAt", somCumulAt);
					
				
				Societe compagnie=	societeRepository.findById(1L).get();
				tAt=compagnie.getTxacctr();
				tPf=compagnie.getTxprest();
				tCnps=compagnie.getTxretraite();
					modelMap.addAttribute("tauxCumulAt", tAt+" % = ");
					modelMap.addAttribute("tauxCumulPf", tPf+" % = ");
					modelMap.addAttribute("tauxCumulCnps", tCnps+" % = ");
					
					if(tCnps != null)
						modelMap.addAttribute("resultCumulCnps",Math.rint((somCumulCnps*tAt)/100));
					
					if(tPf != null)
						modelMap.addAttribute("resultCumulPf",Math.rint((somCumulPf*tPf)/100));
					
					if(tAt != null)
						modelMap.addAttribute("resultCumulAt", Math.rint((somCumulAt*tAt)/100));
					
					
				    modelMap.addAttribute("mois1", periodePaie.getMois().getMois()+" "+periodePaie.getAnnee().getAnnee());
				     modelMap.addAttribute("mois2",periodePaie1.getMois().getMois()+" "+periodePaie1.getAnnee().getAnnee());
				     modelMap.addAttribute("mois3",periodePaie2.getMois().getMois()+" "+periodePaie2.getAnnee().getAnnee());
				     
				 	String moisk="";
					if(periodePaie1.getMois().getId()<10L){
						   modelMap.addAttribute("moisk", periodePaie1.getMois().getId().toString());
					          moisk=periodePaie1.getMois().getId().toString();}
						else {
							modelMap.addAttribute("moisk", periodePaie1.getMois().getId().toString().substring(0, 1)+periodePaie1.getMois().getId().toString().substring(1));
							moisk=periodePaie1.getMois().getId().toString().substring(0, 1)+periodePaie1.getMois().getId().toString().substring(1);}
						String trimeste="";
						if(periodePaie1.getMois().getId()<=3)trimeste="1T ";
						if(periodePaie1.getMois().getId()>=4 && periodePaie1.getMois().getId()<=6 )trimeste="2T ";
						if(periodePaie1.getMois().getId()>=7 && periodePaie1.getMois().getId()<=9 )trimeste="3T ";
						if(periodePaie1.getMois().getId()>=10 && periodePaie1.getMois().getId()<=12 )trimeste="4T ";
						modelMap.addAttribute("trimest", trimeste);
						if(periodePaie.getMois().getId()<10L)
						     modelMap.addAttribute("moisPaies",trimeste+"0"+moisk);	
							else
								 modelMap.addAttribute("moisPaies",trimeste+moisk);
					//modelMap.addAttribute("moisPaies",trimeste+moisk);	
								
				
					 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
			
						JRDataSource jrDatasource = null;
						
						//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
						//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
						GenericDataSource<DisaTrimestrielDTO> dsStudent =  new GenericDataSource<DisaTrimestrielDTO>(DisaTrimestrielDTO.class);
						try {
							jrDatasource = dsStudent.create(null, listImprimebulletin);
							//System.out.println("----------- jr data source "+jrDatasource.toString());
						} catch (JRException e) {
							e.printStackTrace();
						}

						
						modelMap.addAttribute("societe", societeRepository.findById(1L).get().getSigle());
						modelMap.addAttribute("periodePaies", periodePaie);
						modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/front-end/images/logoCNPS.png"));
						modelMap.addAttribute("datasource", jrDatasource);
						
						if(print.equalsIgnoreCase("p"))
							modelMap.addAttribute("format", "pdf");
						if(print.equalsIgnoreCase("e"))
							modelMap.addAttribute("format", "xls");
						
						logger.info("Bulletin individuel imprimer");
			//}
			
		
		//}
		
		return view; //mav;
		
		
	}

	
	
	@RequestMapping(value = "/jrDisa", method = RequestMethod.GET)
	public String ImprimerBulletinPersonnelCV(ModelMap modelMap,@RequestParam(value="annee", required=true) Long idCV, HttpServletRequest request) {
		
		String view="disapdf";
		
		Exercice  annee = new Exercice();
		
		try {
			annee = exerciceService.findExo(idCV);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("####### AnnÃ©e est : "+annee.toString());
		
		List<DisaDTO> listImprimebulletin=  new ArrayList<DisaDTO>();
		
		if(annee.getId() != null){
			
			//Recherche de la liste du personnel
		List<Personnel> listPersonnel = new ArrayList<Personnel>();
			try {
				listPersonnel = personnelRepository.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			Double totalSalaireBrutAnnuelNonPlafon = 0d;
			Double totalSalaireAnnuelSoumisAuPfAt = 0d;
			Double totalSalaireAnnuelSoumisCnps = 0d;
			
			for(Personnel pers : listPersonnel){
				//System.out.println("####### personnel est : "+pers.getPrenom());
			
				DisaDTO disaDTO = new DisaDTO();
				disaDTO.setNom(pers.getNom());
				disaDTO.setPrenoms(pers.getPrenom());
				disaDTO.setNumcnps(pers.getNumeroCnps());
				disaDTO.setDateNaiss(pers.getDateNaissance());
				disaDTO.setDateEmb(pers.getDateArrivee());
				
				String typSalarie = pers.getTypeSalarie();
				disaDTO.setTypeSalarie(typSalarie);
				
				//Recuperation des bulletins pour cette exercie et pour le personnel
				List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
				try {
					listBulletin = bulletinPaieService.rechercherBulletinAnnuel(annee.getId(),pers.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//System.out.println("####### bull : "+pers.getPrenom()+"  ---- "+listBulletin.toString());
				
				Integer nbMois = 0;
				Integer nbMoisBulletin = 0;
				Double nbJour = 0d;
				Double nbHeure = 0d;
				Double somNetImposable = 0d;
				Double somPrimeTransport = 0d;
				
				Double cumulRetenuePatronal = 0d;
				
				Double cumulSalaireAnnuelSoumisAuPfAt = 0d;
				Double cumulSalaireAnnuelSoumisCnps = 0d;
				if(listBulletin.size()> 0){
				for(BulletinPaie bull : listBulletin){
					
					
				
					Double netImposableMensuel = bull.getBrutImposable();
					Double primeTranspNonImpo = 0d;
					
					nbMoisBulletin = nbMoisBulletin + 1;
					
					if(typSalarie.equals("M"))
						nbMois = nbMois + 1;
					
					if(typSalarie.equals("J"))
						nbJour = nbJour + bull.getJourTravail();
					
					if(typSalarie.equals("H"))
						nbHeure = nbHeure + bull.getTemptravail();
					
					somNetImposable = somNetImposable + netImposableMensuel;
					
					//Recherche des retenues du bulletin
					/*List<RetenuePersonnel> listRetenuePersonnel = new ArrayList<RetenuePersonnel>();
					try {
						listRetenuePersonnel = retenuePersonnelService.findActiveRetenuePersonnelByBulletin(bull);
					} catch (Exception e) {
						e.printStackTrace();
					}
					*/
					//if(listRetenuePersonnel.size()>0){
						//for(RetenuePersonnel retenuPers: listRetenuePersonnel){
							if(bull.getRetraite()!=null)
								cumulRetenuePatronal = cumulRetenuePatronal + bull.getRetraite();
						
							if(bull.getAccidentTravail()!=null)
								cumulRetenuePatronal = cumulRetenuePatronal + bull.getAccidentTravail();
						
							if(bull.getPrestationFamiliale()!=null)
								cumulRetenuePatronal = cumulRetenuePatronal + bull.getPrestationFamiliale();
						
						//}
					//}
					
					//Recherche des retenues du bulletin
					/*List<PrimePersonnel> listPrimePersonnel = new ArrayList<PrimePersonnel>();
					try {
						listPrimePersonnel = primePersonnelService.findActivePrimePersonnelByBulletin(bull);
					} catch (Exception e) {
						e.printStackTrace();
					}*/
					
					//if(listPrimePersonnel.size()>0){
					//	for(PrimePersonnel primePers: listPrimePersonnel){
							if(bull.getIndemniteTransport()!=null)
								//System.out.println("---------------prime de transport montant non imposabblr---------: "+primePers.getMontantNonImposable());
								somPrimeTransport = somPrimeTransport + bull.getIndemniteTransport();
						
						//}
					//}
					
					Double somBrut =  netImposableMensuel + primeTranspNonImpo;
					if(somBrut <70000F)
						cumulSalaireAnnuelSoumisAuPfAt = cumulSalaireAnnuelSoumisAuPfAt + somBrut;
					else
						cumulSalaireAnnuelSoumisAuPfAt = cumulSalaireAnnuelSoumisAuPfAt + 70000F;
					
					
					if(somBrut <1647315F)
						cumulSalaireAnnuelSoumisCnps = cumulSalaireAnnuelSoumisCnps + (somBrut - primeTranspNonImpo);
					else
						cumulSalaireAnnuelSoumisCnps = cumulSalaireAnnuelSoumisCnps + (1647315F - primeTranspNonImpo);
						
				}
			}
				
				disaDTO.setCotisationEntreprisePfAtCnps(cumulRetenuePatronal);
				
				if(typSalarie.equals("M"))
					disaDTO.setNbJourMoisTravail(Double.valueOf((nbMois)));
				
				if(typSalarie.equals("J"))
					disaDTO.setNbJourMoisTravail(nbJour);
				
				if(typSalarie.equals("H"))
					disaDTO.setNbJourMoisTravail(nbHeure);
				
				Double salaireBrutAnNonPlaf = somNetImposable + somPrimeTransport;
				
				disaDTO.setSalaireBrutAnnuelNonPlafon(salaireBrutAnNonPlaf);
				disaDTO.setNbMoisTravailAvecConge(Double.valueOf((nbMoisBulletin)));
				disaDTO.setSalaireAnnuelSoumisAuPfAt(cumulSalaireAnnuelSoumisAuPfAt);
				disaDTO.setSalaireAnnuelSoumisCnps(cumulSalaireAnnuelSoumisCnps);
				
				
				totalSalaireBrutAnnuelNonPlafon = totalSalaireBrutAnnuelNonPlafon + salaireBrutAnNonPlaf;
				totalSalaireAnnuelSoumisAuPfAt = totalSalaireAnnuelSoumisAuPfAt + cumulSalaireAnnuelSoumisAuPfAt;
				totalSalaireAnnuelSoumisCnps = totalSalaireAnnuelSoumisCnps + cumulSalaireAnnuelSoumisCnps;
				
				listImprimebulletin.add(disaDTO);
			}	
			
			
			
			/*if(bulletin.getPeriodepaie().getDatedeb() != null)
				bulletin.getPeriodepaie().setDdeb(methodsShared.dateToString(bulletin.getPeriodepaie().getDatedeb(), "dd/MM/yyyy"));   
	      	 
			 if(bulletin.getPeriodepaie().getDatefin() != null)
				 bulletin.getPeriodepaie().setDfin(methodsShared.dateToString(bulletin.getPeriodepaie().getDatefin(), "dd/MM/yyyy"));   
	      	 */
			 List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
			 //BulletinController bulletinController = new BulletinController();
//			 listImprimBulletinPaie =  formatBulletin(annee, "etat",  modelMap, 0F);
		
			 

//			 annee.setListImprimBulletinPaie(listImprimBulletinPaie);
				
/*			try {
					//bulletin.setLogo(request.getSession().getServletContext().getRealPath("resources\\"+bulletin.getPersonnel().getDirection().getEntreprise().getLogo()));
					annee.setLogo(request.getSession().getServletContext().getRealPath("resources\\"+annee.getPersonnel().getDirection().getAgence().getLogo()));
					annee.setEntreprise(annee.getPersonnel().getDirection().getEntreprise());
					//bulletin.setContact(bulletin.getPersonnel().getDirection().getEntreprise().getTelephone()+" - "+bulletin.getPersonnel().getDirection().getEntreprise().getTelecopie());
					annee.setContact(annee.getPersonnel().getDirection().getAgence().getTelephone()+" - "+annee.getPersonnel().getDirection().getAgence().getTelecopie());
					
				
	    	} catch (Exception e) {
	    			e.printStackTrace();
	    	}
*/			
				//Chargement des info de l'entreprise
/*				
				try {
					FonctionPersonnel fonctionPersonnel = new FonctionPersonnel();
					fonctionPersonnel = fonctionPersonnelService.findActiveByPersonnel(annee.getPersonnel());
					if(fonctionPersonnel.getId() != null)
						annee.getPersonnel().setFonctionActuel(fonctionPersonnel.getFonction().getFonction());

				} catch (Exception e) {
					e.printStackTrace();
				}
		
				try {
					EmploisPersonnel emploisPersonnel = new EmploisPersonnel();
					emploisPersonnel = emploisPersonnelService.findActiveByPersonnel(annee.getPersonnel());
					if(emploisPersonnel.getId() != null)
						annee.getPersonnel().setEmploisActuel(emploisPersonnel.getEmplois().getEmplois());

				} catch (Exception e) {
					e.printStackTrace();
				}
				listImprimebulletin.add(annee);
		
				view="bulletinpdf";
*/				
				JRDataSource jrDatasource = null;
				
				//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
				//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
				GenericDataSource<DisaDTO> dsStudent =  new GenericDataSource<DisaDTO>(DisaDTO.class);
				try {
					jrDatasource = dsStudent.create(null, listImprimebulletin);
					//System.out.println("----------- jr data source "+jrDatasource.toString());
				} catch (JRException e) {
					e.printStackTrace();
				}

				
			
				//System.out.println(" la chemin est ------------ "+request.getSession().getServletContext().getRealPath("/WEB-INF/classes/"));
				
				//modelMap.addAttribute("embleme", request.getSession().getServletContext().getRealPath("/resources/images/embleme.png"));
				//Pour le deploiement
				//modelMap.addAttribute("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/WEB-INF/classes")+"\\");
				//modelMap.addAttribute("SUBREPORT_DIR", "D:\\Projets java\\Workspace Education\\RhPaie\\src\\main\\resources\\");
//				modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
				
			//	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/images/logoCNPS.png"));
				modelMap.addAttribute("exercice", annee.getAnnee());
			     modelMap.addAttribute("raisonSocial", "CGECI");
			 
			     modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath("resources/front-end/images/logoCNPS.png"));
				modelMap.addAttribute("nbPersonnel", listPersonnel.size());
				modelMap.addAttribute("totalSalaireBrutAnnuelNonPlafon",Utils.formattingAmount(totalSalaireBrutAnnuelNonPlafon));
				modelMap.addAttribute("totalSalaireAnnuelSoumisAuPfAt",Utils.formattingAmount(totalSalaireAnnuelSoumisAuPfAt));
				modelMap.addAttribute("totalSalaireAnnuelSoumisCnps",Utils.formattingAmount(totalSalaireAnnuelSoumisCnps));
				modelMap.addAttribute("societe", societeRepository.findById(1L));
				modelMap.addAttribute("datasource", jrDatasource);
				modelMap.addAttribute("format", "pdf");
				
				logger.info("Bulletin individuel imprimer");
		}
		
		return view; //mav;
		
		
	}
	
}
