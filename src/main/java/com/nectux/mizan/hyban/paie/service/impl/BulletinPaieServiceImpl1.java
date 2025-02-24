package com.nectux.mizan.hyban.paie.service.impl;/*package net.iconseils.rhpaiecgeci.paie.service.impl;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BulletinPaieDTO;
import LivreDePaieDTO;
import BulletinPaie;
import Echelonnement;
import LivreDePaie;
import TempEffectif;
import BulletinPaieRepository;
import EchelonnementRepository;
import TempEffectifRepository;
import BulletinPaieService;
import PeriodePaie;
import PlanningConge;
import PeriodePaieRepository;
import PlanningCongeRepository;
import ContratPersonnel;
import Personnel;
import ContratPersonnelRepository;
import PersonnelRepository;
import DifferenceDate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("bulletinPaieService1")
public class BulletinPaieServiceImpl1 implements BulletinPaieService {
	
	private static final Logger logger = Logger.getLogger(BulletinPaieServiceImpl1.class);
	
	@Autowired private EchelonnementRepository echelonnementRepository;
	@Autowired private BulletinPaieRepository bulletinPaieRepository;
	
	//@Autowired private LivreDePaieRepository livreDePaieRepository;
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private ContratPersonnelRepository contratPersonnelRepository;
	@Autowired private TempEffectifRepository tempeffectifRepository;
	@Autowired private PlanningCongeRepository planningCongeRepository;
	
	List<LivreDePaie> livredepaieList=null;
	List<BulletinPaie> bulletinpaieList;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BulletinPaie save(BulletinPaie bulletinPaie) {
		// TODO Auto-generated method stub
		bulletinPaie = bulletinPaieRepository.save(bulletinPaie);
	
		return bulletinPaie;
	}


	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		BulletinPaie bulletinPaie = bulletinPaieRepository.findOne(id);
		if(bulletinPaie == null)
			return false;
		
	
		bulletinPaieRepository.delete(id);
		return true;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) bulletinPaieRepository.count();
	}

	@Override
	public BulletinPaieDTO loadBulletinPaie(Pageable pageable,PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaie(pageable,maperiode);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}

	@Override
	public BulletinPaieDTO loadBulletinPaie(Pageable pageable,PeriodePaie maperiode, String search, String search1) {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaieAndContratPersonnelPersonnelNomIgnoreCaseContainingOrContratPersonnelPersonnelPrenomIgnoreCaseContaining(pageable, maperiode, search,search);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}

	@Override
	public List<BulletinPaie> findAllBulletinByPeriodePaieForLivrePaieforBanque(Long idPeriodePaie, boolean idBanque) {
		// TODO Auto-generated method stub
		List<BulletinPaie> listBulletin = new ArrayList<BulletinPaie>();
		try{
			listBulletin = bulletinPaieRepository.findByPeriodePaieIdAndCalculerTrue(idPeriodePaie,idBanque,sortByPersonnelAsc());
			logger.info(listBulletin.size() + " Bulletin(s) pa periode actif(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			System.out.println("----------------------liste des bulletins" +ex.getMessage());
		   
		}
		return listBulletin;
	}
	
	private Sort sortByPersonnelAsc() {
        return new Sort(Sort.Direction.ASC, "personnel.id");
 }
	
	@Override
	public BulletinPaie findBulletinByPeriodePaieAndPersonnel(PeriodePaie periodePaie, Personnel personnel) {
		// TODO Auto-generated method stub
		BulletinPaie bulletin = new BulletinPaie();
		try{
			bulletin =bulletinPaieRepository.findByBulletinAndPersonnel(personnel.getId(), periodePaie.getId());
			logger.info(bulletin.getId() + " Bulletin(s) pa periode actif(s) a / ont ete liste(s) avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			System.out.println("----------------------liste des bulletins" +ex.getMessage());
		    
			logger.error("une erreur a ete detectee lors de la recherche des bulletins par periode");
		}
		return bulletin;
	}

	@SuppressWarnings("unused")
	@Override
	public LivreDePaieDTO  genererMois1(Long idPeriode) {
		logger.error(new StringBuilder().append(">>>>>  PREPARATION DE CALCUL BULLETIN [PERIODE : ").append(idPeriode).toString());
		// TODO Auto-generated method stub
		
		  FileOutputStream fileOut=null;
		  StringBuilder sb = new StringBuilder();
		  BulletinPaieDTO bulletinPaiedto = new BulletinPaieDTO();
		  LivreDePaieDTO livreDEPaieList = new LivreDePaieDTO();
		  livredepaieList = new ArrayList<LivreDePaie>();
		  bulletinpaieList = new ArrayList<BulletinPaie>();
		  PeriodePaie periodePaieActif = new PeriodePaie(); PeriodePaie periodePaiefind= new PeriodePaie();
		  Personnel personnel = new Personnel();
		  List<Personnel> personnelList = new ArrayList<Personnel>();
		  List<Personnel> personnelListTrt = new ArrayList<Personnel>();
		  List<ContratPersonnel> ctratpersonnelListTrt = new ArrayList<ContratPersonnel>();
		  ContratPersonnel ctratpersonnel = new ContratPersonnel();
		  ContratPersonnel ctratpersonnelFind = new ContratPersonnel();
		  periodePaieActif=periodePaieRepository.findOne(idPeriode);
		//  try {
		// verif contrat actif
		  personnelList=personnelRepository.findByStatut(true);
		  
		  for(int i = 0; i < personnelList.size(); i++){
			  ctratpersonnelFind=contratPersonnelRepository.findByPersonnelIdAndStatut(personnelList.get(i).getId(),true);
			if(ctratpersonnelFind!=null)  personnelListTrt.add(ctratpersonnelFind.getPersonnel());
			  logger.info("*****************************************personnel list******"+ctratpersonnelListTrt.toString());
		 
		  }
		  
	        for(Personnel person : personnelListTrt){
	        	        	
	        	BulletinPaie detailsbull = new BulletinPaie();
	             float nbrepart =calculNbrepart(person.getNombrEnfant(),person);		    	
		    	
		    	 ContratPersonnel ctratpersonnellz = new ContratPersonnel();	   
		    	ctratpersonnellz=contratPersonnelRepository.findByPersonnelIdAndStatut(person.getId(),true);
		    	if(ctratpersonnellz.getStatut()==false)ctratpersonnellz=null;
		    		
		    	Double[]  ancienete=calculAnciennete(ctratpersonnellz.getCategorie().getSalaireDeBase(),ctratpersonnellz.getPersonnel().getDateArrivee());
		    	double newancienete;
		    	if(ctratpersonnellz.getAncienneteInitial()!=0) {
		    		 newancienete=ancienete[1] +ctratpersonnellz.getAncienneteInitial();
		    	}else{
		    		newancienete=ancienete[1];
		    	}
		    	int anc=(int)newancienete;
		 
		    	if(ctratpersonnellz.getCategorie().getSalaireDeBase()!=null){
		          ctratpersonnellz.getCategorie().getSalaireDeBase();
		    	}
		    	
		 
		    	Double indemLogt=0d;
		    	if(ctratpersonnellz.getCategorie().getIndemniteLogement()!=null){
		    		indemLogt=ctratpersonnellz.getCategorie().getIndemniteLogement();
		    	}else{
		    		indemLogt=0d;
		    	}
		    			    	
		  
		    
		    	java.util.List<Echelonnement> monEchelonnment= echelonnementRepository.findByPretPersonnelPersonnelIdAndPeriodePaieId(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId());
		    	Double somavsAcpte=0D;Double somalios=0D;
		    	if(monEchelonnment.size()>0){
		    		for(int k = 0; k < monEchelonnment.size(); k++){
		    			Echelonnement monechel= new Echelonnement();
		    			if(monEchelonnment.get(k).getPretPersonnel().getPret().getId()==1L ||monEchelonnment.get(k).getPretPersonnel().getPret().getId()==2L){
		    				monechel=echelonnementRepository.findOne(monEchelonnment.get(k).getId());
		    			    monechel.setPaye(true);
		    			    monechel=echelonnementRepository.save(monechel);
		    				somavsAcpte=monEchelonnment.get(k).getMontant()+somavsAcpte;
		    			}
		    			// PRET ALOES
		    			if(monEchelonnment.get(k).getPretPersonnel().getPret().getId()==3L ){
		    				monechel=echelonnementRepository.findOne(monEchelonnment.get(k).getId());
	    			       monechel.setPaye(true);
	    			       monechel=echelonnementRepository.save(monechel);
		    				somalios=monEchelonnment.get(k).getMontant()+somalios;
		    			}
		    		}
		    	}
		    	
		    	BulletinPaie	 bulletinpaiePrec=new BulletinPaie();			     
			     bulletinpaiePrec=bulletinPaieRepository.findByBulletinAndPersonnelCloture(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId()-1L);
			     int countbull=0;
			     countbull=bulletinPaieRepository.findNbrebulletinTrueCount(ctratpersonnellz.getId());
			     
			      PlanningConge     planningConge = planningCongeRepository.findByContratPersonnelAndStatut(ctratpersonnellz,true);
			     TempEffectif tpeff;
				 tpeff=tempeffectifRepository.findByPersonnelAndPeriodePaie(ctratpersonnellz.getPersonnel(), periodePaieActif);
		    	
		    	  LivreDePaie livrePaiecalD = new LivreDePaie();
		    	  LivreDePaie livrePaiecal = new LivreDePaie();
		    		 int op=0;
		    		 if(anc < 2) op=0;		    		 
		    		 if(anc>=2 && anc<=25) op=anc;
		    		 if(anc>25) op=25;
		    	 if(countbull==0){   
		    	
		    		 
				 livrePaiecal = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),5000d, ctratpersonnellz.getIndemniteLogement(),0d, 0d,ctratpersonnellz,null,periodePaieActif);
				while (livrePaiecal.getNetPayer()!=ctratpersonnellz.getNetAPayer()) {
				
					//nouvPrimeanc=nouvMontantBrutImp-livrePaiecal.getSalaireBase()-nouvValsursal-livrePaiecal.getIndemniteLogement();
					 Double nouvSursal = new Double(0);Double nouvDiff= new Double(0);Double nouvMontantBrutImp= new Double(0);
					nouvMontantBrutImp=Math.rint(ctratpersonnellz.getNetAPayer()*livrePaiecal.getBrutImposable()/livrePaiecal.getNetPayer());
					nouvDiff=nouvMontantBrutImp-livrePaiecal.getBrutImposable();						
					nouvSursal=nouvDiff+livrePaiecal.getSursalaire();						
					livrePaiecal = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),nouvSursal, ctratpersonnellz.getIndemniteLogement(), 0d, 0d,ctratpersonnellz,null,periodePaieActif);	
					  		 logger.info("*********************SECOND BULLETIN********************############## SECOND BULLETIN #############-----------"+livrePaiecal.toString());	
				}
				logger.info("*********************FINAL BULLETIN********************############## FINAL BULLETIN #############-----------"+livrePaiecal.toString());	
				
				
				if ( tpeff==null){
					livrePaiecalD = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), livrePaiecal.getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),livrePaiecal.getSursalaire(), livrePaiecal.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,null,periodePaieActif);
				}else{
					livrePaiecalD = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), livrePaiecal.getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),livrePaiecal.getSursalaire(), livrePaiecal.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,tpeff,periodePaieActif);	
				}
				
		     }else{
		    	 
		    	 		List<BulletinPaie> monbull=bulletinPaieRepository.findTop1ByContratPersonnelOrderByIdDesc(ctratpersonnellz);
		    	 		if(monbull.size()==0){} else{		    		
		    	 			if ( tpeff==null){
		    		 
		    				livrePaiecalD = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),monbull.get(0).getSursalaire(), ctratpersonnellz.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,null,periodePaieActif);
					
		    	 			}else{
						
		    	 				livrePaiecalD = new LivreDePaie(ctratpersonnellz.getPersonnel().getMatricule(),ctratpersonnellz.getPersonnel().getNom()+" "+ctratpersonnellz.getPersonnel().getPrenom(), ctratpersonnellz.getPersonnel().getNombrePart(), op, ctratpersonnellz.getCategorie().getSalaireDeBase(),monbull.get(0).getSursalaire(), ctratpersonnellz.getIndemniteLogement(),somavsAcpte, somalios,ctratpersonnellz,tpeff,periodePaieActif);	
					
		    	 	}
		    	 
		      } 
		     }
				livrePaiecalD.setPeriodePaie(periodePaieActif);
				livrePaiecalD.setContratPersonnel(ctratpersonnellz);
				livrePaiecalD.setJourTravail(livrePaiecalD.getJourTravail());
				livrePaiecalD.setTemptravail(livrePaiecalD.getTemptravail());
				detailsbull.setNombrePart(livrePaiecalD.getNombrePart());
			   	logger.info("########################################### BULLETIN EXISTANT NBRE PART ###########################"+detailsbull.getNombrePart());
			     detailsbull.setAnciennete(livrePaiecalD.getAnciennete());
			     detailsbull.setSalairbase(livrePaiecalD.getSalaireBase());
			     detailsbull.setSursalaire(livrePaiecalD.getSursalaire());
			     detailsbull.setPrimeanciennete(livrePaiecalD.getPrimeAnciennete());
			     detailsbull.setIndemnitelogement(livrePaiecalD.getIndemniteLogement());			     
			     detailsbull.setBrutImposable(livrePaiecalD.getBrutImposable());
			     detailsbull.setIts(livrePaiecalD.getIts());
			     detailsbull.setCn(livrePaiecalD.getCn()); 
			     detailsbull.setIgr(livrePaiecalD.getIgr());						     
			     detailsbull.setTotalretenuefiscal(livrePaiecalD.getTotalRetenueFiscale());
			     detailsbull.setCnps(livrePaiecalD.getCnps());
			     detailsbull.setCongeAc(false);
			     
			     if(bulletinpaiePrec==null){
			    	 
			    	 detailsbull.setTempsOfpresence(livrePaiecalD.getTempspresence());
			          detailsbull.setMoisOfpresence(livrePaiecalD.getMoisdepresence());
			     	 BulletinPaie	 bulletinpaiePrecLev2=new BulletinPaie();			     
			    	 bulletinpaiePrecLev2=bulletinPaieRepository.findByBulletinAndPersonnelCloture(ctratpersonnellz.getPersonnel().getId(), periodePaieActif.getId()-2L);
			    	 if(bulletinpaiePrecLev2==null){}
			    	 else{
			    		 if(bulletinpaiePrecLev2.getCongeAc()==true){
				    		 	detailsbull.setTempsOfpresence(0);
				    		 	detailsbull.setMoisOfpresence(0);
				    	 }
			    		 
			    	 }
			    	 
			    
			     }else{
			    	 
			    	 if(bulletinpaiePrec.getCongeAc()==true){
			    		 	detailsbull.setTempsOfpresence(0);
			    		 	detailsbull.setMoisOfpresence(0);
			    	 }else{
			    		 	detailsbull.setTempsOfpresence(livrePaiecalD.getTempspresence());
			    		 	detailsbull.setMoisOfpresence(livrePaiecalD.getMoisdepresence());
				     }
			     }
			     
			    
			     if(bulletinpaiePrec==null){
			    	 
			    	 detailsbull.setCumulIts(0d+livrePaiecalD.getIts());
			    	 detailsbull.setCumulCn(0d+livrePaiecalD.getCn());
			    	 detailsbull.setCumulIgr(0d+livrePaiecalD.getIgr());
			    	 detailsbull.setCumulCnpsSal(0d+livrePaiecalD.getCnps());
			    	 detailsbull.setCumuljourTravail(30d);
			     }else{
			    	  if(bulletinpaiePrec.getCumulIts()==null){detailsbull.setCumulIts(0d+livrePaiecalD.getIts());}else{detailsbull.setCumulIts(bulletinpaiePrec.getCumulIts()+livrePaiecalD.getIts());}
			    	  if(bulletinpaiePrec.getCumulCn()==null){ detailsbull.setCumulCn(0d+livrePaiecalD.getCn());}else{detailsbull.setCumulCn(bulletinpaiePrec.getCumulCn()+livrePaiecalD.getCn());};
					  if(bulletinpaiePrec.getCumulIgr()==null){ detailsbull.setCumulIgr(0d+livrePaiecalD.getIgr());}else{detailsbull.setCumulIgr(bulletinpaiePrec.getCumulIgr()+livrePaiecalD.getIgr());};
					  if(bulletinpaiePrec.getCumulCnpsSal()==null){ detailsbull.setCumulCnpsSal(0d+livrePaiecalD.getCnps());}else{detailsbull.setCumulCnpsSal(bulletinpaiePrec.getCumulCnpsSal()+livrePaiecalD.getCnps());}
				
			    }		     
			     		     
			     detailsbull.setAvanceetacompte(livrePaiecalD.getAvceAcpte());
			     detailsbull.setPretaloes(livrePaiecalD.getPretAlios());
			     detailsbull.setCarec(livrePaiecalD.getCarec()); 
			     detailsbull.setTotalretenue(livrePaiecalD.getTotalRetenue());
			     detailsbull.setIndemniteRepresentation(livrePaiecalD.getIndemniteRepresentation());
			     detailsbull.setIndemniteTransport(livrePaiecalD.getIndemniteTransport()); 
			     detailsbull.setNetapayer(livrePaiecalD.getNetPayer()); 
			     detailsbull.setTotalbrut(livrePaiecalD.getTotalBrut());
			     detailsbull.setImpotSalaire(livrePaiecalD.getIs());
			     detailsbull.setTa(livrePaiecalD.getTa()); 
			     detailsbull.setJourTravail(livrePaiecalD.getJourTravail());
			     detailsbull.setTemptravail(livrePaiecalD.getTemptravail());
			     detailsbull.setFpc(livrePaiecalD.getFpc()); 
			     detailsbull.setPrestationFamiliale(livrePaiecalD.getPrestationFamiliale());
			     detailsbull.setAccidentTravail(livrePaiecalD.getAccidentTravail());
			     detailsbull.setRetraite(livrePaiecalD.getRetraite());
			     detailsbull.setTotalpatronal(livrePaiecalD.getTotalPatronal());
			     detailsbull.setTotalmassesalarial(livrePaiecalD.getTotalMasseSalariale());
			     detailsbull.setCalculer(true);
			     detailsbull.setCloture(false);
			     detailsbull.setPeriodePaie(livrePaiecalD.getPeriodePaie());
			     detailsbull.setContratPersonnel(livrePaiecalD.getContratPersonnel());
			     detailsbull.setJourTravail(livrePaiecalD.getJourTravail());
			    // detailsbull.set(livrePaiecalD.getJourTravail());
			     livrePaiecalD.setBullpaie(detailsbull);
				
			    // bulletinpaie= bulletinPaieRepository.save(bulletinpaie);
			    
			    // livrePaiecal.setDetailsbull(bulletinpaie);
				//LivreDePaie livrePaie = new LivreDePaie(person.getMatricule(),person.getNom(), nbrepart, anc, ctratpersonnellz.getCategorie().getSalaireDeBase(),20000d, indemLogt, somavsAcpte, somalios);	
		    	 logger.info("*****************************************BULLETIN BULLETIN BULLETIN******************"+livrePaiecalD.toString()); 
		    	 System.out.println("*****************************************BULLETIN BULLETIN BULLETIN******************"+livrePaiecalD.toString());
		    	 livredepaieList.add(livrePaiecalD); 
		    	// bulletinpaieList.add(bulletinpaie);
	        }
	    	
	       
	        logger.info("*********************LISTE DES  BULLETIN********************############## LISTE DES  BULLETIN #############-----------"+livredepaieList.toString());	
		} catch (Exception e) {
			// TODO: handle exception
			 livreDEPaieList.setResult(e.getMessage());
			 livreDEPaieList.setRows(livredepaieList);
		        livreDEPaieList.setResult("error");
		        livreDEPaieList.setTotal(livredepaieList.size());
		}
		  livreDEPaieList.setRows(livredepaieList);
	        livreDEPaieList.setResult("success");
	        livreDEPaieList.setTotal(livredepaieList.size());  
	        
        System.out.println("FINISH"+ bulletinPaiedto.getRows());
		return livreDEPaieList;
	
	}
	

public Float calculNbrepart( Integer nbEnfant, Personnel situationMatrimoniale){

		Float nbPart = 0F;
			
	
			if((situationMatrimoniale.getSituationMatrimoniale() == 2 || situationMatrimoniale.getSituationMatrimoniale() == 3 || situationMatrimoniale.getSituationMatrimoniale() == 4) && nbEnfant == 0)
				nbPart = (float) 1;
			
		
			if((situationMatrimoniale.getSituationMatrimoniale() == 2 || situationMatrimoniale.getSituationMatrimoniale() == 3) && nbEnfant > 0){
				nbPart = (float) (1.5 + (nbEnfant * 0.5));
				
				if(nbPart>5)
					nbPart = (float) 5;
			}
		
			if(situationMatrimoniale.getSituationMatrimoniale() == 1 && nbEnfant == 0)
				nbPart = (float) 2;
				
	
			if((situationMatrimoniale.getSituationMatrimoniale() == 1 || situationMatrimoniale.getSituationMatrimoniale() == 4) && nbEnfant > 0){
				nbPart = (float) (2 + (nbEnfant * 0.5));
						
				if(nbPart>5)
					nbPart = (float) 5;
			}
			return nbPart;
	}
	public  String dateToString(Date date, String sFormat){
		return new SimpleDateFormat(sFormat).format(date);
	}
	
	public  Double[] contratPersonnel(Double salaireCategoriel, Date dateEntree){
		
		Double[] tab = new Double[5];
		
		Double anciennete = (double) 0;
		
		
		double age = DifferenceDate.valAge(new Date(), dateEntree);
		
		int partieEntiere = (int) age; 
		int partieApresVirg = (int)((age - partieEntiere) * 12); 
		
		
		if(age>=2)
			anciennete = (double) (salaireCategoriel*partieEntiere/100);
		
		tab[0] = anciennete;
		
		
		tab[1] = (double) partieEntiere;
		tab[2] = new Double(partieApresVirg);
		
	
		
		return tab;
	}
	public Double calculerAccidentTravail(double brutImposable,double indemniteRepresentation){
		Double pf = brutImposable + indemniteRepresentation;
		if(pf > 70000)
			pf = 70000 * 2.0 / 100;
		else if(pf > 0)
			pf = brutImposable * 2 / 100;
		else 
			pf = 0.0;
		return pf;
	}
	
	public Double calcalerPrestationFamilial(double brutImposable,double indemniteRepresentation){
		Double pf = brutImposable + indemniteRepresentation;
		if(pf > 70000)
			pf = 70000 * 5.75 / 100;
		else if(pf > 0)
			pf = brutImposable * 5.75 / 100;
		else 
			pf = 0.0;
		return pf;
	}
	
	public Double calculerIndemniteTransport(double brutImposable){
		Double it = brutImposable * 10 / 100 - 25000.0;
		if(it > 0)
			it = 25000.0;
		else
			it = 0.0;
		return it;
	}
	
	public Double calculerIndemniterRepresentation(double brutImposable){
		Double ir = brutImposable * 10 / 100 - 25000.0;
		if(ir > 0)
			ir = brutImposable * 10 / 100 - 25000.0;
		else 
			ir = 0.0;
		return ir;
	}
	
	public Double calculerCN(double brutImposable){
		Double cn;
		if(brutImposable > 250000.0)
			cn = (brutImposable - 250000.0) * 8 / 100 + 4700;
		else if(brutImposable > 162500.0)
			cn = (brutImposable - 162500.0) * 4 / 100 + 1200;
		else if(brutImposable > 62500.0)
			cn = brutImposable * 1.2 / 100 - 750;
		else
			cn = 0.0;
		return cn;
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
		tab[2] = new Double(partieApresVirg);
		
	
		
		return tab;
	}
	
	public Double calculerIGR(double brutImposable,double its,double cn,double nombrePart){
		Double igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100;
		if(igr > 842167.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 60 / 160 - 98633.0 * nombrePart;
		else if(igr > 389084.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 45 / 145 - 44181.0 * nombrePart;
		else if(igr > 220334.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 35 / 135 - 24306.0 * nombrePart;
		else if(igr > 126584.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 25 / 125 - 11250.0 * nombrePart;
		else if(igr > 81584.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 20 / 120 - 7031.0 * nombrePart;
		else if(igr > 45584.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 115 - 4076.0 * nombrePart;
		else if(igr > 25000.0)
			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 110 - 2273.0 * nombrePart;
		else 
			igr = 0.0;
		return igr;
	}

	public Double calculCNPS(double brutImposable,double indemniteRepresentation){
		Double cnps = (brutImposable + indemniteRepresentation);
		if(cnps < 1647315.0)
			cnps = (brutImposable + indemniteRepresentation) * 6.3 / 100;
		else 
			cnps = 1647315 * 6.3 / 100;
		return cnps;
	}

	@Override
	public List<BulletinPaie> rechercherBulletinMois(PeriodePaie periodePaie) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaie(periodePaie);
	}



	@Override
	public List<BulletinPaie> rechercherBulletinMoisCalculer(
			PeriodePaie periodePaie, boolean etat) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAndCalculer(periodePaie,etat);
	}

	@Override
	public BulletinPaieDTO generateLivreDePaie() {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinDTO = new BulletinPaieDTO();
		List<BulletinPaie> bullList = new ArrayList<BulletinPaie>();
		for(LivreDePaie livre : livredepaieList){
			BulletinPaie bull=  bulletinPaieRepository.findByBulletinAndPersonnelCalcultrue(livre.getContratPersonnel().getPersonnel().getId(),livre.getPeriodePaie().getId());
			if(bull==null){
				
			}else{
				livre.getBullpaie().setId(bull.getId());
			}
			bullList.add(livre.getBullpaie());
		}
		bulletinPaieRepository.save(bullList);
		bulletinDTO.setRows(bullList);
		bulletinDTO.setResult("Bulletin generes avec succes");
		return bulletinDTO;
	}

	@Override
	public BulletinPaieDTO BulletinMoisCalculer(Pageable pageable,PeriodePaie periodePaie) {
		// TODO Auto-generated method stub
		BulletinPaieDTO bulletinPaieDTO = new BulletinPaieDTO();
		Page<BulletinPaie> page = bulletinPaieRepository.findByPeriodePaieAndCalculerTrue(pageable,periodePaie);
		bulletinPaieDTO.setRows(page.getContent());
		bulletinPaieDTO.setTotal(page.getTotalElements());
		if(page.getTotalElements()>0){bulletinPaieDTO.setResult("success");}else{bulletinPaieDTO.setResult("errors");}
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return bulletinPaieDTO;
	}

	@Override
	public List<BulletinPaie> findBulletinByPeriodePaie(Long idPeriode) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieId(idPeriode);
	}
	@Override
	public List<BulletinPaie> rechercherBulletinAnnuel(Long anneeId,Long Idpers) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAnneeIdAndContratPersonnelPersonnelIdAndCalculer(anneeId,Idpers,true);
	}

	@Override
	public List<BulletinPaie> rechercherBulletinAnnuelglobal(Long anneeId) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
	}
	
	@Override
	public Double salaireMoyenMensuel(ContratPersonnel contratPersonnel) {
		// TODO Auto-generated method stub
		Double salairMoyenMensuelle = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findTop12ByContratPersonnel(contratPersonnel);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			salairMoyenMensuelle = bulletinPaie.getTotalbrut() - bulletinPaie.getIndemniteTransport() + salairMoyenMensuelle;
		}
	
		salairMoyenMensuelle = salairMoyenMensuelle / bulletinPaieList.size();
		return salairMoyenMensuelle;
	}

	@Override
	public Double indemniteMoyenMensuel(ContratPersonnel contratPersonnel) {
		// TODO Auto-generated method stub
		Double indemniteRepresentationMoyenMensuelle = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findTop12ByContratPersonnel(contratPersonnel);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			indemniteRepresentationMoyenMensuelle = bulletinPaie.getIndemniteRepresentation()  + indemniteRepresentationMoyenMensuelle;
		}
		indemniteRepresentationMoyenMensuelle = indemniteRepresentationMoyenMensuelle / bulletinPaieList.size();
		return indemniteRepresentationMoyenMensuelle;
	}


	@Override
	public Double MensuelCn(PeriodePaie maperiode) {		// TODO Auto-generated method stub
		
		Double MensuelCn = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelCn = bulletinPaie.getCn()  + MensuelCn;
		}
		
		return MensuelCn;
		
	}


	
	@Override
	public Double MensuelIgr(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgr = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgr = bulletinPaie.getIgr()  + MensuelIgr;
		}
		
		return MensuelIgr;
	}


	@Override
	public Double MensuelIs(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIs = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIs = bulletinPaie.getIts()  + MensuelIs;
		}
		
		return MensuelIs;
	}


	@Override
	public Double MensuelIgrPatron(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getImpotSalaire()  + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrut(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getTotalbrut() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelCnAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getCn() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelIgrAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getIgr()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrutAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getTotalbrut()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelIsAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getIts()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelIgrPatronAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getImpotSalaire()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrutImpAnne(Long anneeId) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaieAnneeId(anneeId);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getBrutImposable()+ MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelJourtravail(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getJourTravail() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public Double MensuelBrutImposable(PeriodePaie maperiode) {
		// TODO Auto-generated method stub
		Double MensuelIgrPatron = 0.0;
		List<BulletinPaie> bulletinPaieList = bulletinPaieRepository.findByPeriodePaie(maperiode);
		for(BulletinPaie bulletinPaie : bulletinPaieList){
			MensuelIgrPatron = bulletinPaie.getBrutImposable() + MensuelIgrPatron;
		}
		
		return MensuelIgrPatron;
	}


	@Override
	public List<BulletinPaie> rechercherBulletinAnneeCalculer(Long idanne,boolean etat) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByPeriodePaieAnneeIdAndCalculer(idanne, etat);
	}


	@Override
	public List<BulletinPaie> findAllBulletinByvirementforBanque(
			Long idPeriodePaie, Long idBanque) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findByContratPersonnelPersonnelBanquekIdAndPeriodePaieIdAndCalculerTrue(idBanque,idPeriodePaie);
	}


	@Override
	public Integer Nbrebulletin(Long idcontratPersonnel) {
		// TODO Auto-generated method stub
		return bulletinPaieRepository.findNbrebulletinTrueCount(idcontratPersonnel);
	}




}
*/