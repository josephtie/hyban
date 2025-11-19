package com.nectux.mizan.hyban.controllers;


import com.nectux.mizan.hyban.paie.dto.BulletinPaieDTO;
import com.nectux.mizan.hyban.paie.entity.BulletinPaie;
import com.nectux.mizan.hyban.paie.entity.ImprimBulletinPaie;
import com.nectux.mizan.hyban.paie.entity.PrimePersonnel;
import com.nectux.mizan.hyban.paie.service.BulletinPaieService;
import com.nectux.mizan.hyban.parametrages.dto.AuthResponse;
import com.nectux.mizan.hyban.parametrages.dto.LoginRequest;
import com.nectux.mizan.hyban.parametrages.dto.ProfileDto;
import com.nectux.mizan.hyban.parametrages.dto.RegisterRequest;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Rubrique;
import com.nectux.mizan.hyban.parametrages.entity.Societe;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.utils.ProvisionConge;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayController {

    @Autowired
    private BulletinPaieService bulletinPaieService;

    @Autowired
    private PeriodePaieService periodePaieService;

    @PostMapping ("/{personnalId}")
    public List<BulletinPaie> getCurrentYearBulletins(@PathVariable Long personnalId) {
       PeriodePaie maperiode=periodePaieService.findPeriodeactive();
        return bulletinPaieService.getCurrentYearBulletins(personnalId,maperiode.getAnnee().getAnnee()).getRows();
    }


    @PostMapping ("/{payrollId}")
    public BulletinPaie getCurrentSelectBulletin(@PathVariable Long payrollId) {
        //PeriodePaie maperiode=periodePaieService.findPeriodeactive();
        return bulletinPaieService.findbulletin(payrollId);
    }
//    @PostMapping("/login")
//    public AuthResponse login(@RequestBody LoginRequest request){
//        return authService.login(request);
//    }
//
//    @GetMapping("/me")
//    public ProfileDto me(@AuthenticationPrincipal String username){
//        return authService.profile(username);
//    }

//    @RequestMapping(value = "/jrAfficheBulletinPersonnel", method = RequestMethod.GET)
//    public BulletinPaieDTO AfficheBulletinPersonnelCV(@RequestParam(value="idbul", required=true) Long idCV) throws Exception {
//
//        String view="livrepaie";
//        List<BulletinPaie> listImprimebulletin=  new ArrayList<BulletinPaie>();
//        BulletinPaie bulletin = new BulletinPaie();
//        BulletinPaieDTO bulletinDTO =new BulletinPaieDTO();
//
//        try {
//            bulletin = bulletinPaieRepository.findById(idCV).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + idCV));;
//
//            List<ImprimBulletinPaie> listImprimBulletinPaie = new ArrayList<ImprimBulletinPaie>();
//            List<ImprimBulletinPaie> listImprimBulletinPaieEngt = new ArrayList<ImprimBulletinPaie>();
//            List<ImprimBulletinPaie> listImprimBulletinPaieIndem = new ArrayList<ImprimBulletinPaie>();
//
//            if(bulletin.getId() != null) {
//
//                view = "bulletinpdf";
//                ImprimBulletinPaie imprimBulletinPaieSB = new ImprimBulletinPaie();
//                imprimBulletinPaieSB.setLibelle("SALAIRE DE BASE CATEGORIE");
//                imprimBulletinPaieSB.setTaux(bulletin.getJourTravail());
//                //calcul de la base
//                //	if(bulletin.getJourTravail() == 30){
//                imprimBulletinPaieSB.setBase(bulletin.getSalairbase());
//                imprimBulletinPaieSB.setGain(bulletin.getSalairbase());
//                listImprimBulletinPaie.add(imprimBulletinPaieSB);
//
//                ImprimBulletinPaie imprimBulletinPaieSs = new ImprimBulletinPaie();
//                imprimBulletinPaieSs.setLibelle("SURSALAIRE");
//                imprimBulletinPaieSs.setTaux(bulletin.getJourTravail());
//                imprimBulletinPaieSs.setBase(bulletin.getSursalaire());
//                imprimBulletinPaieSs.setGain(bulletin.getSursalaire());
//                listImprimBulletinPaie.add(imprimBulletinPaieSs);
//
//
//                ImprimBulletinPaie imprimBulletinPaieanc = new ImprimBulletinPaie();
//                imprimBulletinPaieanc.setLibelle("PRIME D'ANCIENNETE");
//                imprimBulletinPaieanc.setBase(bulletin.getPrimeanciennete());
//                imprimBulletinPaieanc.setGain(bulletin.getPrimeanciennete());
//                listImprimBulletinPaie.add(imprimBulletinPaieanc);
//
//                ImprimBulletinPaie imprimBulletinPaiePanc = new ImprimBulletinPaie();
//                imprimBulletinPaiePanc.setLibelle("INDEMNITE DE RESIDENCE");
//                imprimBulletinPaiePanc.setTaux(bulletin.getJourTravail());
//
//                imprimBulletinPaiePanc.setBase(bulletin.getIndemnitelogement());
//                imprimBulletinPaiePanc.setGain(bulletin.getIndemnitelogement());
//                listImprimBulletinPaie.add(imprimBulletinPaiePanc);
//
//                ImprimBulletinPaie imprimBulletinPaieLog = new ImprimBulletinPaie();
//                imprimBulletinPaieLog.setLibelle("IND. DE TRANSPORT IMP ");
//                imprimBulletinPaieLog.setTaux(bulletin.getJourTravail());
//                //calcul de la base
//                //	if(bulletin.getJourTravail() == 30){
//                imprimBulletinPaieLog.setBase(bulletin.getIndemniteTransportImp());
//                imprimBulletinPaieLog.setGain(bulletin.getIndemniteTransportImp());
//                listImprimBulletinPaie.add(imprimBulletinPaieLog);
//
//                ImprimBulletinPaie imprimBulletinPaieDivImp1 = new ImprimBulletinPaie();
//                imprimBulletinPaieDivImp1.setLibelle("PRIMES IMPOSABLES ");
//                imprimBulletinPaieDivImp1.setTaux(bulletin.getJourTravail());
//                //calcul de la base
//                //	if(bulletin.getJourTravail() == 30){
//                imprimBulletinPaieDivImp1.setBase(bulletin.getAutreIndemImposable());
//                imprimBulletinPaieDivImp1.setGain(bulletin.getAutreIndemImposable());
//                listImprimBulletinPaie.add(imprimBulletinPaieDivImp1);
//
//                List<Rubrique> listPrimeImp = new ArrayList<Rubrique>();
//                listPrimeImp = rubriqueService.getRubriquesActivesType(1);
//                for (Rubrique rubrique : listPrimeImp) {
//                    List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
//                    listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
//                    if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
//                        ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
//                        imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
//                        if (listprimepersonnel.get(0).getPrime().getTaux() != null)
//                            imprimBulletinPaieDivImp.setTaux(listprimepersonnel.get(0).getValeur().doubleValue());
//                        else
//                            imprimBulletinPaieDivImp.setTaux(bulletin.getJourTravail());
//                        imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
//                        imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
//                        listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
//                    }
//                }
//                List<Rubrique> listPrimeImp1 = new ArrayList<Rubrique>();
//                listPrimeImp1 = rubriqueService.getRubriquesActivesType(3);
//                for (Rubrique rubrique : listPrimeImp1) {
//                    List<PrimePersonnel> listprimepersonnel1 = new ArrayList<PrimePersonnel>();
//                    listprimepersonnel1 = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
//                    if (listprimepersonnel1.size() > 0 && listprimepersonnel1.get(0) != null) {
//                        ImprimBulletinPaie imprimBulletinPaieDivImp10 = new ImprimBulletinPaie();
//                        imprimBulletinPaieDivImp10.setLibelle(listprimepersonnel1.get(0).getPrime().getLibelle());
//                        imprimBulletinPaieDivImp10.setTaux(bulletin.getJourTravail());
//                        imprimBulletinPaieDivImp10.setBase(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
//                        imprimBulletinPaieDivImp10.setGain(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
//                        listImprimBulletinPaie.add(imprimBulletinPaieDivImp10);
//                    }
//                }
//
//                ImprimBulletinPaie imprimBulletinPaieBrutImpos = new ImprimBulletinPaie();
//                imprimBulletinPaieBrutImpos.setLibelle("***** Salaire Brut Imposable ******");
//                imprimBulletinPaieBrutImpos.setGain(bulletin.getBrutImposable());
//                listImprimBulletinPaie.add(imprimBulletinPaieBrutImpos);
//
//                ImprimBulletinPaie imprimBulletinPaieTransport = new ImprimBulletinPaie();
//                imprimBulletinPaieTransport.setLibelle("IND. DE TRANSPORT NON IMPOS");
//                //imprimBulletinPaieTransport.setTaux(bulletin.getJourTravail());
//                imprimBulletinPaieTransport.setGain(bulletin.getIndemniteTransport());
//                listImprimBulletinPaie.add(imprimBulletinPaieTransport);
//
////			ImprimBulletinPaie imprimBulletinPaieRESP= new ImprimBulletinPaie();
////			imprimBulletinPaieRESP.setLibelle("AUTRES NON IMPOSABLES ");
////			//imprimBulletinPaieRESP.setTaux(bulletin.getJourTravail());
////			imprimBulletinPaieRESP.setGain(bulletin.getAutreNonImposable());
////			listImprimBulletinPaie.add(imprimBulletinPaieRESP);
//
//                List<Rubrique> listPrimeImpnon = new ArrayList<Rubrique>();
//                listPrimeImpnon = rubriqueService.getRubriquesActivesType(2);
//                for (Rubrique rubrique : listPrimeImpnon) {
//                    List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
//                    listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
//                    if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
//                        ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
//                        imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
//                        imprimBulletinPaieDivImp.setTaux(bulletin.getJourTravail());
//                        imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
//                        imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
//                        listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
//                    }
//                }
//                List<Rubrique> listPrimeImp12 = new ArrayList<Rubrique>();
//                listPrimeImp12 = rubriqueService.getRubriquesActivesType(3);
//                for (Rubrique rubrique : listPrimeImp12) {
//                    List<PrimePersonnel> listprimepersonnel1 = new ArrayList<PrimePersonnel>();
//                    listprimepersonnel1 = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
//                    if (listprimepersonnel1.size() > 0 && listprimepersonnel1.get(0) != null) {
//                        ImprimBulletinPaie imprimBulletinPaieDivImp10 = new ImprimBulletinPaie();
//                        imprimBulletinPaieDivImp10.setLibelle(listprimepersonnel1.get(0).getPrime().getLibelle());
//                        imprimBulletinPaieDivImp10.setTaux(bulletin.getJourTravail());
//                        imprimBulletinPaieDivImp10.setBase(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
//                        imprimBulletinPaieDivImp10.setGain(listprimepersonnel1.get(0).getMontant() - listprimepersonnel1.get(0).getPrime().getMtExedent());
//                        listImprimBulletinPaie.add(imprimBulletinPaieDivImp10);
//                    }
//                }
//
//                ImprimBulletinPaie imprimBulletinPaieSalBrutNon = new ImprimBulletinPaie();
//                imprimBulletinPaieSalBrutNon.setLibelle("***** Salaire Brut Non Imposable ******");
//                imprimBulletinPaieSalBrutNon.setTaux(bulletin.getJourTravail());
//                imprimBulletinPaieSalBrutNon.setGain(bulletin.getBrutNonImposable());
//                listImprimBulletinPaie.add(imprimBulletinPaieSalBrutNon);
//
//                List<Rubrique> listPrimeG = new ArrayList<Rubrique>();
//                listPrimeG = rubriqueService.getRubriquesActivesType(5);
//                for (Rubrique rubrique : listPrimeG) {
//                    List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
//                    listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
//                    if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
//                        ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
//                        imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
//                        //imprimBulletinPaieDivImp.setTaux(null);
//                        imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
//                        imprimBulletinPaieDivImp.setGain(listprimepersonnel.get(0).getMontant());
//                        listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
//                    }
//                }
//
//
//                ImprimBulletinPaie imprimBulletinPaieIts = new ImprimBulletinPaie();
//                imprimBulletinPaieIts.setLibelle("I.T.S (nouveau Barême)");
//                imprimBulletinPaieIts.setTaux(null);
//                imprimBulletinPaieIts.setBase(bulletin.getBrutImposable());
//                imprimBulletinPaieIts.setRetenue(bulletin.getIts());
//                listImprimBulletinPaie.add(imprimBulletinPaieIts);
//
//                //ImprimBulletinPaie imprimBulletinPaieIgr = new ImprimBulletinPaie();
//                ////imprimBulletinPaieIgr.setLibelle("I.G.R");
//                //imprimBulletinPaieCn.setTaux(1.2D);
//                //imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//                //imprimBulletinPaieIgr.setRetenue(bulletin.getIgr());
//                //////listImprimBulletinPaie.add(imprimBulletinPaieIgr);
//
////			ImprimBulletinPaie imprimBulletinPaieCn = new ImprimBulletinPaie();
////			imprimBulletinPaieCn.setLibelle("CONTRIBUTION NATIONALE");
////			//imprimBulletinPaieCn.setTaux(1.2D);
////			//imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
////			imprimBulletinPaieCn.setRetenue(bulletin.getCn());
////			listImprimBulletinPaie.add(imprimBulletinPaieCn);
//
//
//                ImprimBulletinPaie imprimBulletinPaieCnps = new ImprimBulletinPaie();
//                imprimBulletinPaieCnps.setLibelle("RETRAITE CNPS ");
//                imprimBulletinPaieCnps.setTaux(6.30);
//                imprimBulletinPaieCnps.setBase(bulletin.getBasecnps());
//                imprimBulletinPaieCnps.setRetenue(bulletin.getCnps());
//                listImprimBulletinPaie.add(imprimBulletinPaieCnps);
//
//                ImprimBulletinPaie imprimBulletinPaieIS = new ImprimBulletinPaie();
//                imprimBulletinPaieIS.setLibelle("IMPOTS/SALAIRE LOCAL");
//                //imprimBulletinPaieCn.setTaux(1.2D);
//                //imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//                imprimBulletinPaieIS.setRetenuePatron(bulletin.getIts());
//                listImprimBulletinPaie.add(imprimBulletinPaieIS);
//
//                ImprimBulletinPaie imprimBulletinPaieCnpsPATRON = new ImprimBulletinPaie();
//                imprimBulletinPaieCnpsPATRON.setLibelle("RETRAITE CNPS/PART PATRONAL ");
//                //SSSS	imprimBulletinPaieCnpsPATRON.setTauxPatron(7.70);
//                imprimBulletinPaieCnpsPATRON.setBase(bulletin.getBasecnps());
//                imprimBulletinPaieCnpsPATRON.setRetenuePatron(bulletin.getRetraite());
//                listImprimBulletinPaie.add(imprimBulletinPaieCnpsPATRON);
//
//
//                ImprimBulletinPaie imprimBulletinPaiePF = new ImprimBulletinPaie();
//                imprimBulletinPaiePF.setLibelle("PRESTATION FAMILIALE");
//                //imprimBulletinPaiePF.setTauxPatron(5.75);
//                imprimBulletinPaiePF.setBase(70000d);
//                imprimBulletinPaiePF.setRetenuePatron(bulletin.getPrestationFamiliale());
//                listImprimBulletinPaie.add(imprimBulletinPaiePF);
//
//                ImprimBulletinPaie imprimBulletinPaieAC = new ImprimBulletinPaie();
//                imprimBulletinPaieAC.setLibelle("ACCIDENT DE TRAVAIL");
//                imprimBulletinPaieAC.setTauxPatron(2.0);
//                imprimBulletinPaieAC.setBase(70000d);
//                imprimBulletinPaieAC.setRetenuePatron(bulletin.getAccidentTravail());
//                listImprimBulletinPaie.add(imprimBulletinPaieAC);
//
//                ImprimBulletinPaie imprimBulletinPaieTA = new ImprimBulletinPaie();
//                imprimBulletinPaieTA.setLibelle("FDFP -TAXE APPRENTISAGE ");
//                imprimBulletinPaieTA.setTauxPatron(0.40d);
//                imprimBulletinPaieTA.setBase(bulletin.getBrutImposable());
//                imprimBulletinPaieTA.setRetenuePatron(bulletin.getTa());
//                listImprimBulletinPaie.add(imprimBulletinPaieTA);
//
//                ImprimBulletinPaie imprimBulletinPaieFDFP = new ImprimBulletinPaie();
//                imprimBulletinPaieFDFP.setLibelle("FDFP -TAXE A LA FPC ");
//                imprimBulletinPaieFDFP.setTauxPatron(0.6D);
//                imprimBulletinPaieFDFP.setBase(bulletin.getBrutImposable());
//                imprimBulletinPaieFDFP.setRetenuePatron(bulletin.getFpc());
//                listImprimBulletinPaie.add(imprimBulletinPaieFDFP);
//
//                ImprimBulletinPaie imprimBulletinPaieFDFPRegul = new ImprimBulletinPaie();
//                imprimBulletinPaieFDFPRegul.setLibelle("FDFP -TAXE A LA FPC  REGUL");
//                imprimBulletinPaieFDFPRegul.setTauxPatron(0.6D);
//                imprimBulletinPaieFDFPRegul.setBase(bulletin.getBrutImposable());
//                imprimBulletinPaieFDFPRegul.setRetenuePatron(bulletin.getFpcregul());
//                listImprimBulletinPaie.add(imprimBulletinPaieFDFPRegul);
//
//
//                ImprimBulletinPaie imprimBulletinPaieAMAO = new ImprimBulletinPaie();
//                imprimBulletinPaieAMAO.setLibelle("AVANCES/ACOMPTES ");
//                //imprimBulletinPaieCn.setTaux(1.2D);
//                //imprimBulletinPaieCn.setBase(bulletin.getBrutImposable());
//                imprimBulletinPaieAMAO.setRetenue(bulletin.getAvanceetacompte());
//                listImprimBulletinPaie.add(imprimBulletinPaieAMAO);
//
//                ImprimBulletinPaie imprimBulletinPaieAMAO1 = new ImprimBulletinPaie();
//                imprimBulletinPaieAMAO1.setLibelle("AUTRES PRETS ");
//                imprimBulletinPaieAMAO1.setRetenue(bulletin.getPretaloes());
//                listImprimBulletinPaie.add(imprimBulletinPaieAMAO1);
//                List<Rubrique> listPrimeM = new ArrayList<Rubrique>();
//                listPrimeM = rubriqueService.getRubriquesActivesType(4);
//                for (Rubrique rubrique : listPrimeM) {
//                    List<PrimePersonnel> listprimepersonnel = new ArrayList<PrimePersonnel>();
//                    listprimepersonnel = primePersonnelRepository.findByContratPersonnelIdAndPeriodePaieIdAndPrimeId(bulletin.getContratPersonnel().getId(), bulletin.getPeriodePaie().getId(), rubrique.getId());
//                    if (listprimepersonnel.size() > 0 && listprimepersonnel.get(0) != null) {
//                        ImprimBulletinPaie imprimBulletinPaieDivImp = new ImprimBulletinPaie();
//                        imprimBulletinPaieDivImp.setLibelle(listprimepersonnel.get(0).getPrime().getLibelle());
//                        //imprimBulletinPaieDivImp.setTaux(null);
//                        //	imprimBulletinPaieDivImp.setBase(listprimepersonnel.get(0).getMontant());
//                        imprimBulletinPaieDivImp.setRetenue(listprimepersonnel.get(0).getMontant());
//                        listImprimBulletinPaie.add(imprimBulletinPaieDivImp);
//                    }
//                }
//
//
//                bulletin.setListImprimBulletinPaie(listImprimBulletinPaie);
//
//
//                Date dateRetourDernierConge = null;
//                if (bulletin.getContratPersonnel().getPersonnel().getDateRetourcge() == null) {
//                    logger.info("**********************************************g suiiiiiiiiiiiiii");
//                    dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateArrivee();
//                } else {
//                    dateRetourDernierConge = bulletin.getContratPersonnel().getPersonnel().getDateRetourcge();
//                }
//                int tps = ProvisionConge.calculerTempsPresence(dateRetourDernierConge, bulletin.getPeriodePaie().getDatefin());
//                bulletin.setNbcongedu(String.valueOf(bulletin.getTempsOfpresence()));
//                bulletin.setTpsdepresence(String.valueOf(bulletin.getMoisOfpresence()));
//                JRDataSource jrDatasource = null;
//
//                listImprimebulletin.add(bulletin);
//                //System.out.println("-----------nb list bull imprrrr "+listImprimebulletin.size());
//                //impressionService.imprimeListBulletinN(codeAnsco, listImprimebulletin, 1);
//                List<Societe> malist = societeService.findtsmois();
//
//                //modelMap.addAttribute("logo", request.getSession().getServletContext().getRealPath(malist.get(0).getUrlLogo()));
//                //modelMap.addAttribute("montantcumulSalaireNet", Utils.formattingAmount(bulletin.getCumulSalaireNet()));
//
//
//                //BulletinPaie bulletinData = getPayslipData(bulletin.getId(),bulletin);
//                //byte[] pdfBytes = generatePayslipPdf(bulletin,request);
//
//                //HttpHeaders headers = new HttpHeaders();
//                //headers.setContentType(MediaType.APPLICATION_PDF);
//                //headers.setContentDispositionFormData("filename", "Bulletin_Paie.pdf");
//
//                //	return ResponseEntity.ok().headers(headers).body(pdfBytes);
//                bulletinDTO.setRow(bulletin);
//                bulletinDTO.setStatus(true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Remplacer par un logger
//            //return ResponseEntity.internalServerError().build();
//        }
//
//        //	System.out.println("Vue retournée : " + view);
//        //return view; //mav;
//
//
//        return bulletinDTO;
//    }

}
