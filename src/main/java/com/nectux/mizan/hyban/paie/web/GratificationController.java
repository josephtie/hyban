 package com.nectux.mizan.hyban.paie.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import com.nectux.mizan.hyban.paie.dto.GratificationDTO;
import com.nectux.mizan.hyban.paie.entity.Gratification;
import com.nectux.mizan.hyban.paie.entity.ImprimBulletinPaie;
import com.nectux.mizan.hyban.parametrages.entity.*;
import com.nectux.mizan.hyban.parametrages.service.BanqueService;
import com.nectux.mizan.hyban.parametrages.service.CpteVirmtBanqueService;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
//import com.nectux.mizan.hyban.utils.DeploimentUtils;
import com.nectux.mizan.hyban.utils.GenericDataSource;
import com.nectux.mizan.hyban.utils.MethodsShared;
import com.nectux.mizan.hyban.utils.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.nectux.mizan.hyban.paie.service.GratificationService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/gratification")
public class GratificationController {
	
	private static final Logger logger = LogManager.getLogger(GratificationController.class);
	@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@Autowired private UtilisateurService userService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private GratificationService gratificationService;
	@Autowired private BanqueService banqueService;
	@Autowired private SocieteService societeService;
	@Autowired private CpteVirmtBanqueService cpteVirmtBanqueService;
	MethodsShared methodsShared = new MethodsShared();
	private PeriodePaie maperiodePaie;
	@RequestMapping("/livrepaiegratification")
	public String viewLivrePaie(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		modelMap.addAttribute("user", utilisateurService.findByUsername(principal.getName()));
		Utilisateur utilisateur=utilisateurService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

		modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
				.map(utilisateurRole -> utilisateurRole.getRole().getName().name())
				.findFirst().orElse(""));
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeGratificationBook", "active");
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Gratification");
		


	    maperiodePaie = periodePaieService.findPeriodeactive();
	    if(maperiodePaie != null){
	    	modelMap.addAttribute("activeMois", maperiodePaie.getMois().getMois()+" "+ maperiodePaie.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", maperiodePaie.getId());
			 modelMap.addAttribute("periode",  maperiodePaie.getMois().getMois()+" "+ maperiodePaie.getAnnee().getAnnee());
	    }
	    modelMap.addAttribute("listeBanquesEmp", banqueService.getBanquesEntprise());
	    modelMap.addAttribute("listeBanques", banqueService.getBanques());
	    modelMap.addAttribute("listePeriodes", periodePaieService.findPeriodeOuverte());
	    Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "livrepaiegratification";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listgratificationjson", method = RequestMethod.GET)
	public @ResponseBody
    GratificationDTO listGratificationJSON(@RequestParam(value="limit", required=false) Integer limit,
                                           @RequestParam(value="offset", required=false) Integer offset,
                                           @RequestParam(value="search", required=false) String search, Principal principal) {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;

		PageRequest pageRequest = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "id");
		 //PeriodePaie	 maperiode=periodePaieService.findPeriodePaie(idperiod);
		GratificationDTO gratificationDTO = new GratificationDTO();
		if(search == null ||search == "")
			gratificationDTO = gratificationService.loadGratificationProvisional(pageRequest);
		else
			gratificationDTO = gratificationService.loadGratificationProvisional(pageRequest,  search);
		
		return gratificationDTO;
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/generergratification", method = RequestMethod.POST)
	public @ResponseBody GratificationDTO generateGratification() { 
		
		return gratificationService.generateGratification();
	}
	
	@RequestMapping(value="/chargergratificationparperiode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Gratification> chargerGratificationParPeriode(@RequestParam(value="periodepaie", required=true) Long id) {
		
		List<Gratification> gratificationList = new ArrayList<Gratification>();
		try {
			gratificationList = gratificationService.findByPeriodePaie(id);
			logger.info("success");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			logger.info("fail");
		} 
		return gratificationList ;
    }
	
	
	@RequestMapping(value = "/jrGratificationPersonnel", method = RequestMethod.GET)
	public String ImprimerBulletinPersonnelCV(ModelMap modelMap,@RequestParam(value="idbul", required=true) Long idCV, HttpServletRequest request) {
			
			String view="livrepaie";
			List<Gratification> listImprimebulletin=  new ArrayList<Gratification>();
			Gratification bulletin = new Gratification();
			
			try {
				bulletin = gratificationService.findGratification(idCV);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
			List<ImprimBulletinPaie> listImprimBulletinPaieEngt = new ArrayList<ImprimBulletinPaie>();
			List<ImprimBulletinPaie> listImprimBulletinPaieIndem = new ArrayList<ImprimBulletinPaie>();
		
			if(bulletin.getId() != null){
			
				view="bulletinGratificationReport";
				ImprimBulletinPaie imprimBulletinPaieSB = new ImprimBulletinPaie();
				imprimBulletinPaieSB.setLibelle("Gratication de base");
				imprimBulletinPaieSB.setTaux(30D);
				//calcul de la base
			//	if(bulletin.getJourTravail() == 30){
				imprimBulletinPaieSB.setBase(bulletin.getBrutImposable());
				imprimBulletinPaieSB.setGain(bulletin.getBrutImposable());
			//	}else{
				//	imprimBulletinPaie.setBase(arrondi((salaireCategoriel*30)/bulletin.getJourTravail(), nbArrondi));
					
				//	}
				  //    imprimBulletinPaie.setGain(salaireCategoriel);
				 listImprimBulletinPaie.add(imprimBulletinPaieSB);
				
				/*ImprimBulletinPaie imprimBulletinPaieSs = new ImprimBulletinPaie();
				imprimBulletinPaieSs.setLibelle("Sursalaire");
				imprimBulletinPaieSs.setTaux(30D);
					//calcul de la base
				//	if(bulletin.getJourTravail() == 30){
				imprimBulletinPaieSs.setBase(bulletin.getSursalaire());
				imprimBulletinPaieSs.setGain(bulletin.getSursalaire()); 
				listImprimBulletinPaie.add(imprimBulletinPaieSs);	*/	
				
				/*ImprimBulletinPaie imprimBulletinPaiePanc = new ImprimBulletinPaie();
				imprimBulletinPaiePanc.setLibelle("Prime d'anciennete");
				imprimBulletinPaiePanc.setTaux(30D);
			
				imprimBulletinPaiePanc.setBase(bulletin.getPrimeanciennete());
				imprimBulletinPaiePanc.setGain(bulletin.getPrimeanciennete()); 
				listImprimBulletinPaie.add(imprimBulletinPaiePanc);*/
				
				/*ImprimBulletinPaie imprimBulletinPaieLog = new ImprimBulletinPaie();
				imprimBulletinPaieLog.setLibelle("Prime de logement");
				imprimBulletinPaieLog.setTaux(30D);
					//calcul de la base
				//	if(bulletin.getJourTravail() == 30){
				imprimBulletinPaieLog.setBase(bulletin.getIndemnitelogement());
				imprimBulletinPaieLog.setGain(bulletin.getIndemnitelogement()); 
				listImprimBulletinPaie.add(imprimBulletinPaieLog);*/
				
				ImprimBulletinPaie imprimBulletinPaieBrutImpos = new ImprimBulletinPaie();
				imprimBulletinPaieBrutImpos.setLibelle("Salaire Brut Imposable(1)");
				//imprimBulletinPaieLog.setTaux(0D);
					//calcul de la base
				//	if(bulletin.getJourTravail() == 30){
				//imprimBulletinPaieLog.setBase(bulletin.getIndemnitelogement());
				imprimBulletinPaieBrutImpos.setGain(bulletin.getBrutImposable()); 
				listImprimBulletinPaie.add(imprimBulletinPaieBrutImpos);
				
				ImprimBulletinPaie imprimBulletinPaieIts = new ImprimBulletinPaie();
				imprimBulletinPaieIts.setLibelle("Impot sur salaire");
				imprimBulletinPaieIts.setTaux(1.2D);					
				imprimBulletinPaieIts.setBase(bulletin.getBrutImposable());		
				imprimBulletinPaieIts.setRetenue(bulletin.getIts());
				listImprimBulletinPaie.add(imprimBulletinPaieIts);
				
				ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
				imprimBulletinPaieCn.setLibelle("Contribution nationale");
				//imprimBulletinPaieCn.setTaux(1.2D);					
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
				imprimBulletinPaieCn.setRetenue(bulletin.getCn());
				listImprimBulletinPaie.add(imprimBulletinPaieCn);
				
				ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
				imprimBulletinPaieIgr.setLibelle("Impot general sur revenu");
				//imprimBulletinPaieCn.setTaux(1.2D);					
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
				imprimBulletinPaieIgr.setRetenue(bulletin.getIgr());
				listImprimBulletinPaie.add(imprimBulletinPaieIgr);
				
				ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
				imprimBulletinPaieCnps.setLibelle("CNPS ");
				//imprimBulletinPaieCn.setTaux(1.2D);					
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
				imprimBulletinPaieCnps.setRetenue(bulletin.getCnps());
				listImprimBulletinPaie.add(imprimBulletinPaieCnps);
				
				ImprimBulletinPaie imprimBulletinPaieRetfiscsoc = new ImprimBulletinPaie();
				imprimBulletinPaieRetfiscsoc.setLibelle("Total retenues fiscales et sociales(2)");
				//imprimBulletinPaieCn.setTaux(1.2D);					
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
				imprimBulletinPaieRetfiscsoc.setRetenue(bulletin.getCnps()+bulletin.getIgr()+bulletin.getCn()+bulletin.getIts());
				listImprimBulletinPaie.add(imprimBulletinPaieRetfiscsoc);
				
			
				
			/*	
			
				
				ImprimBulletinPaie imprimBulletinPaieRetCarec = new ImprimBulletinPaie();
				imprimBulletinPaieRetCarec.setLibelle("RETENUE POUR CAREC NSIA");
				//imprimBulletinPaieCn.setTaux(1.2D);					
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
				imprimBulletinPaieRetCarec.setRetenue(bulletin.getCarec());
				listImprimBulletinPaieEngt.add(imprimBulletinPaieRetCarec);
				
				
				ImprimBulletinPaie imprimBulletinPaieRetAvset = new ImprimBulletinPaie();
				imprimBulletinPaieRetAvset.setLibelle("AUTRES RETENUES ET AVANCES");
				//imprimBulletinPaieCn.setTaux(1.2D);					
				//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
				imprimBulletinPaieRetAvset.setRetenue(bulletin.getAvanceetacompte()+bulletin.getPretaloes());
				listImprimBulletinPaieEngt.add(imprimBulletinPaieRetAvset);*/
				
				ImprimBulletinPaie imprimBulletinPaieTotAutrRet = new ImprimBulletinPaie();
				imprimBulletinPaieTotAutrRet.setLibelle("TOTAL AUTRES RETENUES(3)");					
				imprimBulletinPaieTotAutrRet.setRetenue(0d);
				listImprimBulletinPaieEngt.add(imprimBulletinPaieTotAutrRet);
				
				ImprimBulletinPaie imprimBulletinPaieTotalR = new ImprimBulletinPaie();
				imprimBulletinPaieTotalR.setLibelle("TOTAL (2+3)");					
				imprimBulletinPaieTotalR.setRetenue(imprimBulletinPaieRetfiscsoc.getRetenue());
				listImprimBulletinPaieEngt.add(imprimBulletinPaieTotalR);
				
				ImprimBulletinPaie imprimBulletinPaieTransport = new ImprimBulletinPaie();
				imprimBulletinPaieTransport.setLibelle("FRAIS DE TRANSPORT");	
				imprimBulletinPaieTransport.setTaux(30D);
				imprimBulletinPaieTransport.setGain(bulletin.getIndemniteTransport());
				listImprimBulletinPaieIndem.add(imprimBulletinPaieTransport);
				
				/*ImprimBulletinPaie imprimBulletinPaieRepres = new ImprimBulletinPaie();
				imprimBulletinPaieRepres.setLibelle("AUTRE INDEMNITES NON IMPOSABLE");	
				imprimBulletinPaieRepres.setTaux(30D);
				imprimBulletinPaieRepres.setGain(bulletin.getIndemniteRepresentation());
				listImprimBulletinPaieIndem.add(imprimBulletinPaieRepres);*/
				
			ImprimBulletinPaie imprimBulletinPaieTOTgainsNonImpos = new ImprimBulletinPaie();
				imprimBulletinPaieTOTgainsNonImpos.setLibelle("TOTAL GAINS NON IMPOSABLES (4)");	
				imprimBulletinPaieTOTgainsNonImpos.setTaux(30D);
				imprimBulletinPaieTOTgainsNonImpos.setGain(bulletin.getIndemniteTransport());
				listImprimBulletinPaieIndem.add(imprimBulletinPaieTOTgainsNonImpos);
				
				ImprimBulletinPaie imprimBulletinPaieTBrutMens= new ImprimBulletinPaie();
				imprimBulletinPaieTBrutMens.setLibelle("SALAIRE BRUT MENSUEL (1+4) )");	
				imprimBulletinPaieTBrutMens.setTaux(30D);
				imprimBulletinPaieTBrutMens.setGain(bulletin.getBrutImposable()+bulletin.getIndemniteTransport());
				listImprimBulletinPaieIndem.add(imprimBulletinPaieTBrutMens);
				
				bulletin.setListImprimBulletinPaie(listImprimBulletinPaie);
				bulletin.setListImprimBulletinPaieEngagement(listImprimBulletinPaieEngt);
				bulletin.setListImprimBulletinPaieIndemniteNonImp(listImprimBulletinPaieIndem);
			JRDataSource jrDatasource = null;
			
			listImprimebulletin.add(bulletin);
			//System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
			//impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
			GenericDataSource<Gratification> dsStudent =  new GenericDataSource<Gratification>(Gratification.class);
			try {
				jrDatasource = dsStudent.create(null, listImprimebulletin);
				//System.out.println("----------- jr data source "+jrDatasource.toString());
			} catch (JRException e) {
				e.printStackTrace();
			}
			//modelMap.addAttribute("logo",request.getSession().getServletContext().getRealPath("resources/front-end/images/logodefis1.png"));
			List<Societe> malist=societeService.findtsmois();
			
		  	modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath(malist.get(0).getUrlLogo()));
			//modelMap.addAttribute("SUBREPORT_DIR", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
			//modelMap.addAttribute("SUBREPORT_DIR1", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
			//modelMap.addAttribute("SUBREPORT_DIR2", DeploimentUtils.RecupSubReportChem(request.getSession().getServletContext().getRealPath(DeploimentUtils.ChemRech())));
			modelMap.addAttribute("datasource", jrDatasource);
			modelMap.addAttribute("format", "pdf");
			}
		
		
		return view; //mav;
		
		
	}
	
	@RequestMapping(value="/imprimerbulletingratification", method = RequestMethod.GET)
    public ModelAndView imprimerBulletinGratification(@RequestParam(value="idgratification", required=true) Long id, ModelAndView modelAndView){
 
        Map<String,Object> parameterMap = new HashMap<String, Object>();
        
        List<Gratification> gratificationList = new ArrayList<Gratification>();
        Gratification gratification = gratificationService.findGratification(id);
        gratificationList.add(gratification);
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(gratificationList);
 
        parameterMap.put("gratification", gratification);
        parameterMap.put("contratPersonnel", gratification.getContratPersonnel());
        /*parameterMap.put("periodePaie", gratification.getPeriodePaie().getMois().getMois());
        parameterMap.put("situationMatrimoniale", gratification.getContratPersonnel().getPersonnel().getSituationMatri());
        parameterMap.put("numeroCNPS", gratification.getContratPersonnel().getPersonnel().getNumeroCnps());*/
        parameterMap.put("datasource", JRdataSource);
 
        //pdfReport bean has ben declared in the jasper-views.xml file
        modelAndView = new ModelAndView("bulettinGratificationReport", parameterMap);
 
        return modelAndView;
	}
	
	@RequestMapping(value={"/postVirementBulletin"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    BulletinPaieDTO imprOrdreVirmt (ModelMap modelMap, @RequestParam(value="banque", required=true) Long banque , @RequestParam(value="banqueEmp", required=true) Long banqueEmp , @RequestParam(value="periodePaieImpressionV", required=true) Long periodeid, HttpServletRequest request){
	
		String view="livrepaie";
		
		BulletinPaieDTO BulletinPaieDTO=new BulletinPaieDTO();
	//	if(printDTO.getVal1() != null && printDTO.getVal2() != null){
			PeriodePaie  periodePaie = periodePaieService.findPeriodePaie(periodeid);
			System.out.println("########## periode : "+periodePaie.getMois().getMois());
			Banque tbanqueAvirmt =banqueService.findBanquesID(banqueEmp);
			Banque tbanque =banqueService.findBanquesID(banque);
			System.out.println("########## banque : "+tbanque.getCodbanq() );
			String codbanqr=tbanqueAvirmt.getCodbanq() ;
		List<Gratification> listbulletin=  new ArrayList<Gratification>();
		
		List<Gratification> listBull = new ArrayList<Gratification>();
	// creer un repertoire
		
	//	String uploadPath =  "resources/user/agence"+File.separator;
		String uploadPath =  request.getSession().getServletContext().getRealPath("") + File.separator + "resources"+File.separator+"gratif"+File.separator;
		
//////////////////////////////////////////////////////////////////////////////////////////////////////
				
		// GESTION PHOTO
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
		//	System.out.println(">>> CHEMIN UPLOAD >>>" + uploadPath);
			uploadDir.mkdirs();
			//System.out.println(">>> CHEMIN Creer >>>" + uploadDir);
		}
	//	File monfichier = new File(uploadDir,"/resources/virement"+File.separator+banque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt");
		String monfichier =tbanque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt";
		File fichier = new File(uploadDir+File.separator+monfichier);
		
		    try{    
		    	//Writer out = new BufferedWriter(new OutputStreamWriter(	new FileOutputStream("fileDir"), "UTF8"));
		    	//"/resources/agence/"+periodePaie.getAgence().getId()+"/"+fichier
		    	CpteVirmtBanque moncpteVirmt=new CpteVirmtBanque();
		    	moncpteVirmt=cpteVirmtBanqueService.findCpteVirmtoFBanques(tbanqueAvirmt);
		    	System.out.println("######################################################### banque compte de virement societe : "+moncpteVirmt.toString() );
		    	 FileWriter fw = new FileWriter(fichier,false);
		    	String SGBCI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+moncpteVirmt.getBank().getCodbanq()+moncpteVirmt.getNumguichCpteVirmt()+moncpteVirmt.getNumcpteCpteVirmt()+moncpteVirmt.getRibCpteVirmt()+moncpteVirmt.getDonneurOrdCpteVirmt()+moncpteVirmt.getCodEtablVirmt();
		   //	 String BICICI="02100001"+methodsShared.dateToString(new Date(), "ddMMyy")+"0060000000000000000                    00153";
		  
		    	// String SIB="02100001"+methodsShared.dateToString(new Date(), "dd/MM/yy")+"008000000000000000                      00153";
		    	// if(Tbanque.getId()==11L)
		    	 fw.write(SGBCI);  // ecrire une ligne dans le fichier resultat.txt
		
		    	 fw.write(System.getProperty("line.separator"));  
		    	 //fw.write("\n");
		    	 System.out.println("88888888888888888888888888888888888888888888888888888888888 gratification  periode"+periodePaie.getId());
		    	 System.out.println("88888888888888888888888888888888888888888888888888888888888 gratification banque "+tbanque.getId());
		    	 listBull= gratificationService.findAllBulletinByvirementforBanque(periodePaie.getId(),tbanque.getId()) ;
		    	/* for (Gratification gratification : listbulletin) {
					if(gratification.getContratPersonnel().getPersonnel().getBanquek().getId()==tbanque.getId())
						listBull.add(gratification);
				  }*/
		    	 
		    	 
		    	 System.out.println("88888888888888888888888888888888888888888888888888888888888 gratification "+listBull.size());
		    	
		    	 Float cumSal=0f;int c=0,ty=0;String numsek="",numsekC="";
		    	 for (int r = 0; r<listBull.size(); r++){
						//This data needs to be written (Object[])
		    		 System.out.println("55555555555555555555555555555555555555555555555555555 debut virement "+listBull.size());
							//Personnel bankpersonnel= listBull.get(r).getContratPersonnel().getPersonnel().getBanquek());
							String codop="",typenr="",datop="",codbanq="",codbanqA="",numguich="",numguichA="",matricul="",matriculA="",nocpte="",nocpteA="",nom="",nomA="",libelop="",libelopA="";Float mtvirem=0f;
							double  mtLen=0d;String mtnet="";String banque1="";
							c=r+2;ty=r+3;
							 if(listBull.get(r).getContratPersonnel().getPersonnel().getBanquek()==null){
								
							 }else{
								 
								 codop="02"; 
								 typenr="2";
								 if(c<10)numsek="0000"+c;
								 if(c>=10 && c<=99)numsek="000"+c;
								 if(c>=100 && c<=999)numsek="00"+c;
								 
								 if(ty<10)numsekC="0000"+ty;
								 if(ty>=10 && ty<=99)numsekC="000"+ty;
								 if(ty>=100 && ty<=999)numsekC="00"+ty;
								
								// if(r>=1000 && r<=9999)numsek="0"+c;	
								 datop=methodsShared.dateToString(new Date(),"ddMMyy");
								/* if(Tbanque.getId()==11L)codbanq="008";
								 if(banque.getId()==4L)codbanq="006";
								 if(banque.getId()==6L)codbanq="155";
								 if(banque.getId()==18L)codbanq="092";
								 if(banque.getId()==8L)codbanq="042";*/
								 int n = 11; // nbre de caracteres
								  codbanq=listBull.get(r).getContratPersonnel().getPersonnel().getBanquek().getCodbanq().substring(2, 5);
								 System.out.println("88888888888888888888888888888888888888888888888888888888888 code de banqe "+codbanq);
								 int leng = listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().length();
								// System.out.println("88888888888888888888888888888888888888888888888888888888888 LONGUEUR NOCPTE "+leng);
								// if(leng>=13 && leng<=16){
								 if(leng>=12)
								 nocpte = listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().toString();
								 else
									 nocpte = listBull.get(r).getContratPersonnel().getPersonnel().getNumeroCompte().toString().substring(leng-n,leng);
							
								 System.out.println("88888888888888888888888888888888888888888888888888888888888 NOCPTE "+nocpte);
								 int nMatr = 7;
								
								 String jp	=""; String matru="";
								 //  StringBuilder	 sb = new StringBuilder();
								    matru=listBull.get(r).getContratPersonnel().getPersonnel().getMatricule().replace("-","");
							        matricul=matru.replace(" ", "").toUpperCase();
							          int lengthMatr=matricul.length(); int diff=0;String blanc=new String();
								 if(lengthMatr==nMatr){
									 jp=matricul;
								      //sb.append(matricul);
									 }
								 else if(lengthMatr>nMatr) {
									 jp=matricul.substring(0,7);
								
								 }else{
									 diff=nMatr-lengthMatr;
									 if(diff==1)blanc="      ";
									 if(diff==2)blanc="     ";
									 if(diff==3)blanc="    ";
									 if(diff==4)blanc="   ";
									 if(diff==5)blanc="  ";
									 if(diff==6)blanc=" ";
									 jp=matricul.concat(blanc);
								 }
								 
								
								 nom=listBull.get(r).getContratPersonnel().getPersonnel().getNom() +" "+listBull.get(r).getContratPersonnel().getPersonnel().getPrenom();
								 int maxNom=24;String nom1="";
								  StringBuilder	 snom = new StringBuilder();
								int tailNom=nom.length();
								 if(tailNom>=maxNom){
								  nom1=nom.substring(0,24).toUpperCase();
								//  snom.append(nom1);
								  }
								 else{
									 //nom1=nom;
									 String vide1n="                        ";
									nom1=nom.toUpperCase().concat(vide1n).substring(0,24);
									   /*for (int f =1; f<=maxNom-snom.length(); f++){
										   snom.insert(f,"");
										 // System.out.println("88888888888888888888888888888888888888888888888888888888888 NOM "+snom);
									   }*/
								 
									 }
							//	 System.out.println("88888888888888888888888888888888888888888888888888888888888 MATRICULE "+jp.length()+"****** :"+jp);
								 System.out.println("88888888888888888888888888888888888888888888888888888888888 MATRICULE "+jp.length()+"****** :"+jp);
								 System.out.println("88888888888888888888888888888888888888888888888888888888888 NOM "+nom1.length()+"****** :"+nom1);
								 int sigleB=17;
								 String uba="";
							     banque1=tbanque.getSigle();
							   //  StringBuilder	 snombk = new StringBuilder();
							     if(banque1.length()>=sigleB){
							         /* jp	=listBull.get(r).getPersonnel().getMatricule().substring(0, 9).replace("-", "");
							          matricul=jp.replace(" ", "");*/
							    	 uba=banque1.substring(0, 17);}
								 else {
									// jp=listBull.get(r).getPersonnel().getMatricule().replace("-", "");
									// matricul=jp.replace(" ", "");
									 String vide1="                 ";
									 uba=banque1.concat(vide1).substring(0, 17);
									
								
								 
									 }
							     System.out.println("88888888888888888888888888888888888888888888888888888888888 Banque sigle "+uba);
							     
							  
							     libelop="VIREMENT SALAIRES"+" "+ periodePaie.getMois().getMois()+" "+periodePaie.getAnnee().getAnnee().substring(2,4);
							
							     
							     int lIBopB=30;							  
							     String opBank="";
							     if(libelop.length()>=lIBopB){
							      
							    	 opBank=libelop.substring(0, lIBopB);}
								 else {
									 opBank=libelop.concat("                              ").substring(0, 30);
									 								 
									 }
							     System.out.println("88888888888888888888888888888888888888888888888888888888888 libelop Banque "+opBank);
							     
							     cumSal=(float) (cumSal+listBull.get(r).getNetPayer());
							      mtLen=listBull.get(r).getNetPayer();
							      String okmt= Utils.formattingAmount(listBull.get(r).getNetPayer());
							      int imt = (int) mtLen;
							     if(mtLen<10f)mtnet=00000000000+String.valueOf(mtLen);
								 if(mtLen>=10d && mtLen<=99d) mtnet="0000000000"+String.valueOf(mtLen);
								 if(mtLen>=100d && mtLen<=999d) mtnet="000000000"+String.valueOf(mtLen);
								 if(mtLen>=1000d && mtLen<=9999d) mtnet="00000000"+String.valueOf(mtLen);
								 if(mtLen>=10000d && mtLen<=99999d) mtnet="0000000"+String.valueOf(mtLen);
								 if(mtLen>=100000d && mtLen<=999999d) mtnet="000000"+String.valueOf(mtLen);
								 if(mtLen>=1000000d && mtLen<=9999999d) mtnet="00000"+String.valueOf(mtLen);
								 if(mtLen>=10000000d && mtLen<=99999999d) mtnet="0000"+String.valueOf(mtLen);
								 if(mtLen>=100000000d && mtLen<=999999999d) mtnet="000"+String.valueOf(mtLen);
								 if(mtLen>=1000000000d && mtLen<=9999999999d) mtnet="00"+String.valueOf(mtLen);
								 if(mtLen>=10000000000d && mtLen<=99999999999d) mtnet="0"+String.valueOf(mtLen);
							  
								
							     fw.write(codop+typenr+numsek+datop+codbanq+nocpte+jp+nom1+uba+opBank+ mtnet.substring(0,12));  // ecrire une ligne dans le fichier resultat.txt
						    	 fw.write(System.getProperty("line.separator"));  
							     
							 }
						}
		    	 String codopC="",typenrC="",datopC="",blanck="";
		    	 codopC="02";typenrC="9";double valnet=cumSal;
		    	
				 blanck="        ";
				 datopC=methodsShared.dateToString(new Date(),"ddMMyy");
				 int imtTT = (int) valnet;
				  fw.write(codopC+typenrC+numsekC+datopC+blanck+imtTT);  // ecrire une ligne dans le fichier resultat.txt
			    	 fw.write(System.getProperty("line.separator"));
		    	  fw.close(); // fermer le fichier a la fin des traitements
		    	
		    	   
		    	}catch(IOException e){    
		    	  
		    	}  
			            String  promotion = null;
						try {
							promotion = "/resources/gratif/"+tbanque.getSigle()+periodePaie.getMois().getMois()+periodePaie.getAnnee().getAnnee()+".txt";
					
						} catch (Exception e) {
							e.printStackTrace();
						}
						//view=promotion;
						modelMap.addAttribute("promotion", promotion);
					
						
				    System.out.println(fichier.getName()+" written successfully on disk.");
			
				    BulletinPaieDTO.setMessage(promotion);
		return BulletinPaieDTO;
	}
}
