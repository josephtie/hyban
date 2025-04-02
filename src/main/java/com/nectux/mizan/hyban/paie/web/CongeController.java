
package com.nectux.mizan.hyban.paie.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nectux.mizan.hyban.parametrages.entity.Utilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nectux.mizan.hyban.paie.dto.CongeDTO;
import com.nectux.mizan.hyban.paie.entity.Conge;
import com.nectux.mizan.hyban.paie.entity.ImprimBulletinPaie;
import com.nectux.mizan.hyban.paie.repository.CongeRepository;
import com.nectux.mizan.hyban.paie.service.CongeService;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.parametrages.service.SocieteService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurRoleService;
import com.nectux.mizan.hyban.parametrages.service.UtilisateurService;
import com.nectux.mizan.hyban.personnel.repository.ContratPersonnelRepository;
import com.nectux.mizan.hyban.personnel.repository.PersonnelRepository;
import com.nectux.mizan.hyban.utils.DeploimentUtils;
import com.nectux.mizan.hyban.utils.DifferenceDate;
import com.nectux.mizan.hyban.utils.GenericDataSource;
import com.nectux.mizan.hyban.utils.ProvisionConge;
import com.nectux.mizan.hyban.utils.Utils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;


@Controller
@RequestMapping("/conge")
public class CongeController {
	
	private static final Logger logger = LogManager.getLogger(CongeController.class);
	//@Autowired private UtilisateurService utilisateurService;
	@Autowired private UtilisateurRoleService utilisateurRoleService;
	@Autowired private UtilisateurService userService;
	@Autowired private CongeService congeService;
	@Autowired private PeriodePaieService periodePaieService;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private CongeRepository congeRepository;
	@Autowired private SocieteService societeService;
	@Autowired private PersonnelRepository personnelRepository;
	private PeriodePaie maperiode;
	@RequestMapping("/provisionconge")
    public String viewLivrePaie(ModelMap modelMap, Principal principal) throws IOException {
		logger.info(">>>>> Utilisateurs");
		
		modelMap.addAttribute("user", userService.findByUsername(principal.getName()));
		Utilisateur utilisateur=userService.findByUsername(principal.getName());
		System.out.println("utilisateur    " +utilisateur.toString());

      modelMap.addAttribute("profil", utilisateur.getUtilisateurRoles().stream()
    .map(utilisateurRole -> utilisateurRole.getRole().getName().name()) 
    .findFirst().orElse(""));
		modelMap.addAttribute("icon", "iconfa-home");
		modelMap.addAttribute("littleTitle", "Paie");
		modelMap.addAttribute("bigTitle", "Provision Cong&eacute;");
		modelMap.addAttribute("activePayroll", "active");
		modelMap.addAttribute("blockPayroll", "block");
		modelMap.addAttribute("activeHolidayPayroll", "active");

	    maperiode=periodePaieService.findPeriodeactive();
	    if(maperiode==null){}
	    else{
	    	modelMap.addAttribute("activeMois", maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
			modelMap.addAttribute("activeMoisId", maperiode.getId());
			modelMap.addAttribute("periode",  maperiode.getMois().getMois()+" "+ maperiode.getAnnee().getAnnee());
	    }
	    Societe mysociete=null;
		  List<Societe> malist=societeService.findtsmois();
		  if(malist.size()>0)
			{	mysociete=malist.get(0);			
			modelMap.addAttribute("urllogo",mysociete.getUrlLogo()); }
		return "livrepaieconge";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/listprovisioncongejson", method = RequestMethod.GET)
	public @ResponseBody CongeDTO getPersonnelListJSON(@RequestParam(value="limit", required=false) Integer limit, 
															@RequestParam(value="offset", required=false) Integer offset, 
															@RequestParam(value="search", required=false) String search, Principal principal) throws Exception {
		
		if(offset == null) offset = 0;
		if(limit == null) limit = 10;
		
		//final PageRequest pageRequest = new PageRequest(offset/10, limit, Direction.DESC, "nom");
		PageRequest page = PageRequest.of(offset / 10, limit, Direction.DESC, "id");
		CongeDTO congeDTO = new CongeDTO();
		if(search == null)
			congeDTO = congeService.loadCongeProvisional(page);
		else
			congeDTO = congeService.loadCongeProvisional(page, search);
		
		return congeDTO;
	}
	

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/genererbulletinconge", method = RequestMethod.POST)
	public @ResponseBody CongeDTO getPersonnelListJSON(@RequestParam(value="id", required=false) Long id) throws Exception {
		
		CongeDTO congeDTO = congeService.genererBulletinConge(id);
		
		return congeDTO;
	}
	
	

	@RequestMapping(value = "/JRBulletinConge", method = RequestMethod.GET)
	public String ImprimerBulletinPersonnelCV(ModelMap modelMap,@RequestParam(value="idbul", required=true) Long idCV, HttpServletRequest request) {
			
		String view="livrepaieconge";
		List<Conge> listImprimebulletin=  new ArrayList<Conge>();
		Conge bullconge = new Conge();
		
		try {
			bullconge = congeService.findconge(idCV);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
		List<ImprimBulletinPaie> listImprimBulletinPaieEngt = new ArrayList<ImprimBulletinPaie>();
		List<ImprimBulletinPaie> listImprimBulletinPaieIndem = new ArrayList<ImprimBulletinPaie>();
	
		if(bullconge.getId() != null){
		
			view="bulletinCongeReport";
			ImprimBulletinPaie imprimBulletinPaieSB = new ImprimBulletinPaie();
			imprimBulletinPaieSB.setLibelle("Conge de base");
			imprimBulletinPaieSB.setTaux(30D);
			//calcul de la base
		//	if(bulletin.getJourTravail() == 30){
			imprimBulletinPaieSB.setBase(bullconge.getBaseImposableAllocationConge());
			imprimBulletinPaieSB.setGain(bullconge.getBaseImposableAllocationConge());
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
			imprimBulletinPaieBrutImpos.setLibelle("Base Imposable Des Allocations Conges(1)");
			//imprimBulletinPaieLog.setTaux(0D);
				//calcul de la base
			//	if(bulletin.getJourTravail() == 30){
			//imprimBulletinPaieLog.setBase(bulletin.getIndemnitelogement());
			imprimBulletinPaieBrutImpos.setGain(bullconge.getBaseImposableAllocationConge()); 
			listImprimBulletinPaie.add(imprimBulletinPaieBrutImpos);
			
			ImprimBulletinPaie imprimBulletinPaieIts = new ImprimBulletinPaie();
			imprimBulletinPaieIts.setLibelle("Impot sur salaire");
			imprimBulletinPaieIts.setTaux(1.2D);					
			imprimBulletinPaieIts.setBase(bullconge.getBaseImposableAllocationConge());		
			imprimBulletinPaieIts.setRetenue(bullconge.getIts());
			listImprimBulletinPaie.add(imprimBulletinPaieIts);
			
			ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
			imprimBulletinPaieCn.setLibelle("Contribution nationale");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieCn.setRetenue(bullconge.getCn());
			listImprimBulletinPaie.add(imprimBulletinPaieCn);
			
			ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
			imprimBulletinPaieIgr.setLibelle("Impot general sur revenu");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieIgr.setRetenue(bullconge.getIgr());
			listImprimBulletinPaie.add(imprimBulletinPaieIgr);
			
			ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
			imprimBulletinPaieCnps.setLibelle("CNPS ");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieCnps.setRetenue(bullconge.getCnps());
			listImprimBulletinPaie.add(imprimBulletinPaieCnps);
			
			ImprimBulletinPaie imprimBulletinPaieRetfiscsoc = new ImprimBulletinPaie();
			imprimBulletinPaieRetfiscsoc.setLibelle("Total retenues fiscales et sociales(2)");
			//imprimBulletinPaieCn.setTaux(1.2D);					
			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());		
			imprimBulletinPaieRetfiscsoc.setRetenue(bullconge.getCnps()+bullconge.getIgr()+bullconge.getCn()+bullconge.getIts());
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
			
			/*ImprimBulletinPaie imprimBulletinPaieTransport = new ImprimBulletinPaie();
			imprimBulletinPaieTransport.setLibelle("FRAIS DE TRANSPORT");	
			imprimBulletinPaieTransport.setTaux(30D);
			imprimBulletinPaieTransport.setGain(bullconge.getIndemniteTransport());
			listImprimBulletinPaieIndem.add(imprimBulletinPaieTransport);*/
			
			ImprimBulletinPaie imprimBulletinPaieRepres = new ImprimBulletinPaie();
			imprimBulletinPaieRepres.setLibelle("AUTRE INDEMNITES NON IMPOSABLE");	
			imprimBulletinPaieRepres.setTaux(30D);
			imprimBulletinPaieRepres.setGain(0d);
			listImprimBulletinPaieIndem.add(imprimBulletinPaieRepres);
			
		    ImprimBulletinPaie imprimBulletinPaieTOTgainsNonImpos = new ImprimBulletinPaie();
			imprimBulletinPaieTOTgainsNonImpos.setLibelle("TOTAL GAINS NON IMPOSABLES (4)");	
			imprimBulletinPaieTOTgainsNonImpos.setTaux(30D);
			imprimBulletinPaieTOTgainsNonImpos.setGain(0d);
			listImprimBulletinPaieIndem.add(imprimBulletinPaieTOTgainsNonImpos);
			
			ImprimBulletinPaie imprimBulletinPaieTBrutMens= new ImprimBulletinPaie();
			imprimBulletinPaieTBrutMens.setLibelle("SALAIRE BRUT MENSUEL (1+4) )");	
			imprimBulletinPaieTBrutMens.setTaux(30D);
			imprimBulletinPaieTBrutMens.setGain(bullconge.getBaseImposableAllocationConge());
			listImprimBulletinPaieIndem.add(imprimBulletinPaieTBrutMens);
			bullconge.setAllocationCongeNet(bullconge.getBaseImposableAllocationConge()-bullconge.getCnps()-bullconge.getIgr()-bullconge.getCn()-bullconge.getIts());
			bullconge.setMontantNetPayer(Utils.formattingAmount(bullconge.getAllocationCongeNet()));
			modelMap.addAttribute("alocnetpayer",Utils.formattingAmount(bullconge.getBaseImposableAllocationConge()-bullconge.getCnps()-bullconge.getIgr()-bullconge.getCn()-bullconge.getIts()));
			bullconge.setListImprimBulletinPaie(listImprimBulletinPaie);
		//	modelMap.addAttribute("anciennete",);
			bullconge.setListImprimBulletinPaieEngagement(listImprimBulletinPaieEngt);
			bullconge.setListImprimBulletinPaieIndemniteNonImp(listImprimBulletinPaieIndem);
		    
		    Date dateRetourDernierConge = null;
			Date dateDepartConge = new Date();
			List<Conge> oldCongeList = new ArrayList<Conge>();
			//oldCongeList = congeRepository.findTop1ByContratPersonnel(bullconge.getContratPersonnel());
			//if(oldCongeList != null && oldCongeList.isEmpty()) // Si le resultat est null :: 
				// Alors on cherche la date d'arrive du personnel
				        if(bullconge.getContratPersonnel().getPersonnel().getDateRetourcge()==null){
				        	dateRetourDernierConge = bullconge.getContratPersonnel().getPersonnel().getDateArrivee();}
				        else{dateRetourDernierConge = bullconge.getContratPersonnel().getPersonnel().getDateRetourcge();}
				       
			//else
			//	dateRetourDernierConge = oldCongeList.get(0).getDateRetour();
			int tps=ProvisionConge.calculerTempsPresence(dateRetourDernierConge,bullconge.getPeriodePaie().getDatefin());
			if(tps<0)tps=tps*-1;
		//	modelMap.addAttribute("congacquis", String.valueOf(bullconge.getTempsPresenceEffectif()));
	//		modelMap.addAttribute("tpsPresence",String.valueOf(Math.rint(bullconge.getTempsPresenceEffectif()/2.75)));	
			int rf=(int) (tps*2.2*1.25);
			bullconge.setNbcongedu(String.valueOf(bullconge.getTempsOfpresence()));
			bullconge.setTpsdepresence(String.valueOf(bullconge.getMoisOfpresence()));
			/*new loi*/
			//Jour ouvrable supplement//
			 Double[]ancienete= calculAnciennete(bullconge.getContratPersonnel().getCategorie().getSalaireDeBase(),bullconge.getContratPersonnel().getPersonnel().getDateArrivee());
			 	double newancienete;
		    	if(bullconge.getContratPersonnel().getAncienneteInitial()!=0) {
		    		 newancienete=ancienete[1] +bullconge.getContratPersonnel().getAncienneteInitial();
		    	}else{
		    		newancienete=ancienete[1];
		    	}
		    	double anc=(int)newancienete ;
		    	
		     int jourSuppAnc=0; int jourSuppDam = 0;int jourSuppMed = 0;
		     
			 if(anc>5 && anc<=10)  jourSuppAnc=1;
			 if(anc>10 && anc<=15) jourSuppAnc=2;
			 if(anc>15 && anc<=20) jourSuppAnc=3;
			 if(anc>20 && anc<=25) jourSuppAnc=5;
			 if(anc>25 && anc<=30) jourSuppAnc=7;
			 if(anc>30) jourSuppAnc=8;
			 
			 Double age=DifferenceDate.valAge(new Date(), bullconge.getContratPersonnel().getPersonnel().getDateNaissance());
			 if(bullconge.getContratPersonnel().getPersonnel().getSexe().equals("Feminin") && age<=21 && bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>0){
				 jourSuppDam=2*bullconge.getContratPersonnel().getPersonnel().getNombrEnfant();
			 }
			 if(bullconge.getContratPersonnel().getPersonnel().getSexe().equals("Feminin") && age>21 && bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>0){
				 
				 if(bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>=4)jourSuppDam=2*1;
				 if(bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>=5)jourSuppDam=2*2;
				 if(bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>=6)jourSuppDam=2*3;
				 if(bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>=7)jourSuppDam=2*4;
				 if(bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>=8)jourSuppDam=2*5;
				 if(bullconge.getContratPersonnel().getPersonnel().getNombrEnfant()>=9)jourSuppDam=2*6;
			 }
			 
			 if(bullconge.getContratPersonnel().getPersonnel().getSituationMedaille()==1 ){
				 jourSuppMed=1;
			 } 
			 int rfp=(int) (jourSuppAnc+jourSuppDam+jourSuppMed);
			 bullconge.setJouradditionel(String.valueOf(jourSuppAnc+jourSuppDam+jourSuppMed));
			//modelMap.addAttribute("congacquis",String.valueOf(tps*2.2*1.25));
			modelMap.addAttribute("jrdu",String.valueOf(rfp+bullconge.getTempsOfpresence()));	
			JRDataSource jrDatasource = null;
		listImprimebulletin.add(bullconge);

		GenericDataSource<Conge> dsStudent =  new GenericDataSource<Conge>(Conge.class);
		try {
			jrDatasource = dsStudent.create(null, listImprimebulletin);
			//System.out.println("----------- jr data source "+jrDatasource.toString());
		} catch (JRException e) {
			e.printStackTrace();
		}
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
	@RequestMapping(value="/chargerlivrepaiecongeperiode", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Conge> chargerCongeParPeriode(@RequestParam(value="periodepaie", required=true) Long id) {
		
		List<Conge> congeList = new ArrayList<Conge>();
		try {
			return congeService.loadLivrePaieCongePeriode(id);
			//logger.info("success");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} 
		return congeList ;
    }
	
public  Double[] calculAnciennete(Double salaireCategoriel, Date dateEntree){
		
		Double[] tab = new Double[5];
		
		Double anciennete = (double) 0;
		
		
		double age = DifferenceDate.valAge(new Date(), dateEntree);
		
		int partieEntiere = (int) age; 
		int partieApresVirg = (int)((age - partieEntiere) * 12); 
		
		
		if(age>=2)
			anciennete = (double) (salaireCategoriel*partieEntiere/100);
		
		tab[0] = anciennete;
		
		
		tab[1] = (double) partieEntiere;
		tab[2] = (double)partieApresVirg;
		
	
		
		return tab;
	}
}


