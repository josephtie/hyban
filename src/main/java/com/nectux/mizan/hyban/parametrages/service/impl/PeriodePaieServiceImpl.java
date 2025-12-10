package com.nectux.mizan.hyban.parametrages.service.impl;

import java.util.*;

import com.nectux.mizan.hyban.parametrages.repository.MoisRepository;
import com.nectux.mizan.hyban.parametrages.service.MoisService;
import com.nectux.mizan.hyban.parametrages.entity.Mois;
import com.nectux.mizan.hyban.parametrages.dto.PeriodePaieDTO;
import com.nectux.mizan.hyban.parametrages.entity.Exercice;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.ExerciceRepository;
import com.nectux.mizan.hyban.parametrages.repository.PeriodePaieRepository;
import com.nectux.mizan.hyban.parametrages.service.PeriodePaieService;
import com.nectux.mizan.hyban.utils.DateManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Transactional
@Service("periodepaieService")
public class PeriodePaieServiceImpl implements PeriodePaieService {
	
	private static final Logger logger = LogManager.getLogger(PeriodePaieServiceImpl.class);
	
	
	@Autowired private PeriodePaieRepository periodePaieRepository;
	@Autowired private MoisRepository moisRepository;
	@Autowired private MoisService moisService;
	@Autowired private ExerciceRepository exerciceRepository;
	
	@Override
	public PeriodePaie save(PeriodePaie periodePaie) {
		// TODO Auto-generated method stub
		periodePaie = periodePaieRepository.save(periodePaie);
		
		return periodePaie;
	}
	@Override
	public PeriodePaieDTO save(Long id, String datedeb, String datefin,Long mois,Long annee) {
		// TODO Auto-generated method stub
		PeriodePaieDTO periodepaieDTO = new PeriodePaieDTO();
		try{
			PeriodePaie periodepaie = new PeriodePaie();
		
			if(id != null)
				periodepaie = periodePaieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			
			periodepaie.setDatedeb(DateManager.stringToDate(datedeb, "dd/MM/yyyy"));
			periodepaie.setDatefin(DateManager.stringToDate(datefin, "dd/MM/yyyy"));
			periodepaie.setAnnee(exerciceRepository.findById(annee).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + annee)));
			periodepaie.setMois(moisRepository.findById(mois).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + mois)));
			periodepaie=periodePaieRepository.save(periodepaie);
			periodepaieDTO.setRow(periodepaie);
			periodepaieDTO.setResult("success");
			logger.info(new StringBuilder().append(">>>>> ").append(mois.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
		} catch(Exception ex){
			periodepaieDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT UTILISATEUR [NOM : ").append(datedeb).toString());
			ex.getStackTrace();
		}
		return periodepaieDTO;
	}
	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		PeriodePaie periodepaie = periodePaieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
		if(periodepaie == null)
			return false;
		
		periodePaieRepository.delete(periodepaie);
		return true;
	}
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return (int) periodePaieRepository.count();
	}
	@Override
	public PeriodePaieDTO loadPeriodePaie(Pageable pageable) {
		// TODO Auto-generated method stub
		PeriodePaieDTO periodeDTO = new PeriodePaieDTO();
		Page<PeriodePaie> page = periodePaieRepository.findAll(pageable);
		periodeDTO.setRows(page.getContent());
		periodeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> UTILISATEURS CHARGES AVEC SUCCES").toString());
		return periodeDTO;
	}
	@Override
	public PeriodePaieDTO loadPeriodePaie(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		PeriodePaieDTO periodeDTO = new PeriodePaieDTO();
		Page<PeriodePaie> page = periodePaieRepository.findAll(pageable);
		periodeDTO.setRows(page.getContent());
		periodeDTO.setTotal(page.getTotalElements());
		logger.info(new StringBuilder().append(">>>>> PERIODE PAIE CHARGES AVEC SUCCES").toString());
		return periodeDTO;
	}
	@SuppressWarnings("static-access")
	@Override
	public PeriodePaieDTO genererPeriode(Long moiss, String annee) {
		// TODO Auto-generated method stub
		PeriodePaieDTO periodepaieDTO = new PeriodePaieDTO();
		try{
			//PeriodePaie periodepaie = new PeriodePaie();
		
			
			//if(printDTO.getS3().equals("Non"))
			//{
				System.out.println("periode mensuell: ");
				//if(printDTO.getVal1() != null && printDTO.getVal5() != null && printDTO.getVal3() != null)
				//	{
				//if (periodePaieRepository.count()>0){}else{
						//long an = annee;
						long mois = moiss;
						int valmois ;
						Exercice anpaienew = new Exercice();
						Exercice anpaieANT = new Exercice();
						Exercice anpaieActif = new Exercice();
						Exercice anpaiefind = new Exercice();
						Exercice anpaie = new Exercice();
						anpaiefind=exerciceRepository.findByExoLibelle(annee);
						if(anpaiefind!=null){
							Exercice finalAnpaiefind = anpaiefind;
							anpaie = exerciceRepository.findById(anpaiefind.getId()).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + finalAnpaiefind));
						}else{
							anpaieActif=exerciceRepository.findByExoCloture();	
							if(anpaieActif==null){anpaienew.setCloture(false);}
						anpaienew.setAnnee(annee);
						
						anpaienew=exerciceRepository.save(anpaienew);
							Exercice finalAnpaienew = anpaienew;
							anpaie = exerciceRepository.findById(anpaienew.getId()).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + finalAnpaienew.getId()));
						
						
						}
						String anneeaformat = anpaie.getAnnee();
//						
						int anneeformat  = Integer.parseInt(anneeaformat);
						 System.out.println("anneeformat: " + anneeformat);
		//
						Mois moipaie = new Mois();
						moipaie = moisRepository.findById(mois).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + mois));
						String moisaformat = moipaie.getMois();
						long moisposition =moipaie.getId();
						System.out.println("moisaformat: " + moisaformat);
						valmois=(int) moisposition;

						System.out.println("mois: " + valmois);
						int jour = 1;
//						
				        int nbremoisrestant = 12 - valmois;
					    //DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					    GregorianCalendar cal = new GregorianCalendar();
					    GregorianCalendar cal1 = new GregorianCalendar();
//					   
					    cal.clear();
					    cal1.clear();
					 	cal.set(anneeformat,valmois-1,jour);
//				
					 	System.out.println("calendar: " + cal);
//					 
					    java.util.Date utilDate = cal.getTime();			    
					    System.out.println("date debut: "+utilDate);
					    cal1.setTime(utilDate);
					    int DernierJourMois= cal.getActualMaximum(cal.DATE);
					   	System.out.println("nbrejourmois: " + DernierJourMois);
					   	cal1.set(anneeformat, valmois-1, DernierJourMois);
					   	java.util.Date utilDatefinr = cal1.getTime();
					   	System.out.println("datefinop: " + utilDatefinr);//					  
						PeriodePaie periodePaieCourantNon = new PeriodePaie();
						PeriodePaie periodePaieFindCourantNon = new PeriodePaie();
						periodePaieCourantNon.setAnnee(anpaie);
					
						try {
							periodePaieCourantNon.setDatefin(utilDatefinr);
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							periodePaieCourantNon.setDatedeb(utilDate);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						periodePaieCourantNon.setMois(moipaie);
						// verif si ya pas de periode active
						periodePaieFindCourantNon=periodePaieRepository.recherchperiodeCloture();
						if(periodePaieFindCourantNon==null)
						periodePaieCourantNon.setCloture(false);
//						//Ajout
						PeriodePaie periodePaieFind=null;
						periodePaieFind=periodePaieRepository.findByAnneeIdAndMoisId(anpaie.getId(), periodePaieCourantNon.getMois().getId());
						if(periodePaieFind==null)
						periodePaieRepository.save(periodePaieCourantNon);
						System.out.println("#######  #####  ##### Periode paie Non ooooo "+periodePaieCourantNon.toString());
					
						System.out.println("Nbremoisrestant: " + nbremoisrestant);
					    for(int i = 1; i < nbremoisrestant+1 ; i++)
					    {
					    	  GregorianCalendar cal13 = new GregorianCalendar();
					    	  GregorianCalendar cal14 = new GregorianCalendar();
					    	  cal13.set(anneeformat, valmois-1+i, 1);
					    	  java.util.Date utilDate1 = cal13.getTime();
					    	       System.out.println("date debut: "+utilDate1+"I:  "+i);			    	 
					    	    int DernierJourMois1= cal13.getActualMaximum(cal13.DATE);
							   	System.out.println("nbrejourmois: " + DernierJourMois1);
						 	  cal14.set(anneeformat, valmois-1+i, DernierJourMois1);
							  	java.util.Date utilDate11 = cal14.getTime();
							   int 	month = cal14.get(Calendar.MONTH);
//							    System.out.println("date fin: "+utilDate11+"I:  "+i);
//							 //   int py=valmois-1;
//							 //   System.out.println("PY: "+py);
							    String moisencours = "";
							    if(month==0) {moisencours="JANVIER";}
							    if(month==1) {moisencours="FEVRIER";}
							    if(month==2) {moisencours="MARS";}
							    if(month==3) {moisencours="AVRIL";}
							    if(month==4) {moisencours="MAI";}
							    if(month==5) {moisencours="JUIN";}
							    if(month==6) {moisencours="JUILLET";}
							    if(month==7) {moisencours="AOUT";}
							    if(month==8) {moisencours="SEPTEMBRE";}
							    if(month==9) {moisencours="OCTOBRE";}
							    if(month==10) {moisencours="NOVEMBRE";}
							    if(month==11) {moisencours="DECEMBRE";}
//							
							    Mois moistop=new Mois();
							    moistop=moisService.findbymois(moisencours);
							    System.out.println("mois par nom: "+moistop.getMois());
							    PeriodePaie PeriodePaieCourant1 = new PeriodePaie();
//		                      
								PeriodePaieCourant1.setAnnee(anpaie);
							
								PeriodePaieCourant1.setDatefin(utilDate11); 
								PeriodePaieCourant1.setDatedeb(utilDate1);
								PeriodePaieCourant1.setMois(moistop);
								PeriodePaie periodePaieFind4=null;
								periodePaieFind4=periodePaieRepository.findByAnneeIdAndMoisId(anpaie.getId(), moistop.getId());
//								//Ajout
								if(periodePaieFind4==null){periodePaieRepository.save(PeriodePaieCourant1);}else{}
								
//					    	
					    }
//					    
					 
//					    // pour 3 ans
					  
					    for(int A = 1; A <3 ; A++){
				    	int ansuiv=0;
				    	ansuiv=anneeformat+A;
					    	 System.out.println("Annee suivante "+ ansuiv);
					    	
//					    	  
					    	 for(int vs = 1; vs <=12 ; vs++){
//					    	 
					    	  GregorianCalendar cal130 = new GregorianCalendar();
					    	  GregorianCalendar cal140 = new GregorianCalendar();
					    	  cal130.set(ansuiv, vs-1, 1);
					    	  java.util.Date utilDate1 = cal130.getTime();
					    	       System.out.println("date debut: "+utilDate1+"I:  "+vs);			    	 
				    	    int DernierJourMois1= cal130.getActualMaximum(cal130.DATE);
							   	System.out.println("nbrejourmois: " + DernierJourMois1);
							 	cal140.set(ansuiv, vs-1, DernierJourMois1);
							  	java.util.Date utilDate11 = cal140.getTime();
							   int 	month = cal140.get(Calendar.MONTH);
						    System.out.println("date fin: "+utilDate11+"I:  "+vs);

						    String moisencours = "";
							    if(month==0) {moisencours="JANVIER";}
							    if(month==1) {moisencours="FEVRIER";}
							    if(month==2) {moisencours="MARS";}
							    if(month==3) {moisencours="AVRIL";}
							    if(month==4) {moisencours="MAI";}
							    if(month==5) {moisencours="JUIN";}
							    if(month==6) {moisencours="JUILLET";}
							    if(month==7) {moisencours="AOUT";}
							    if(month==8) {moisencours="SEPTEMBRE";}
							    if(month==9) {moisencours="OCTOBRE";}
							    if(month==10) {moisencours="NOVEMBRE";}
							    if(month==11) {moisencours="DECEMBRE";}
//							
							    Mois moistop=new Mois();
							    moistop=moisService.findbymois(moisencours);
							    System.out.println("mois par nom: "+moistop.getMois());
							    PeriodePaie PeriodePaieCourant1 = new PeriodePaie();
		                        String valan=String.valueOf(ansuiv);
							    Exercice ansop=new Exercice();
							    Exercice ansop1=new Exercice();
						    List<Exercice> listAnnee = new ArrayList<Exercice>();
							    listAnnee=exerciceRepository.findByAnnee(valan);
						    if(listAnnee.size()>0)
							    {
							    	PeriodePaieCourant1.setAnnee(listAnnee.get(0));
							    }else
							    {
							    	ansop.setAnnee(valan);
							    	ansop1=exerciceRepository.save(ansop);
							    	PeriodePaieCourant1.setAnnee(ansop1);
							    }
							
								PeriodePaieCourant1.setDatefin(utilDate11); 
								PeriodePaieCourant1.setDatedeb(utilDate1);
								PeriodePaieCourant1.setMois(moistop);
								//Ajout
								PeriodePaie periodePaieFind1=null;
								periodePaieFind1=periodePaieRepository.findByAnneeIdAndMoisId(PeriodePaieCourant1.getAnnee().getId(), PeriodePaieCourant1.getMois().getId());
								if(periodePaieFind1==null)
								periodePaieRepository.save(PeriodePaieCourant1);
					    	
					       } 
				  } 

			//   //annee moins (1)
					 //   anpaieANT.setId(anpaie.getId()-1);
						anpaieANT.setAnnee(String.valueOf(Integer.parseInt(annee)-1));
						anpaieANT=exerciceRepository.save(anpaieANT);
						System.out.println("Nbremoisrestant: " + nbremoisrestant);
					    for(int i = 1; i < 12 ; i++)
					    {
					    	  GregorianCalendar cal13 = new GregorianCalendar();
					    	  GregorianCalendar cal14 = new GregorianCalendar();
					    	  cal13.set(anneeformat-1, i, 1);
					    	  java.util.Date utilDate1 = cal13.getTime();
					    	       System.out.println("date debut: "+utilDate1+"I:  "+i);			    	 
					    	    int DernierJourMois1= cal13.getActualMaximum(cal13.DATE);
							   	System.out.println("nbrejourmois: " + DernierJourMois1);
						 	  cal14.set(anneeformat-1, i, DernierJourMois1);
							  	java.util.Date utilDate11 = cal14.getTime();
							   int 	month = cal14.get(Calendar.MONTH);
//							    System.out.println("date fin: "+utilDate11+"I:  "+i);
//							 //   int py=valmois-1;
//							 //   System.out.println("PY: "+py);
							    String moisencours = "";
							    if(month==0) {moisencours="JANVIER";}
							    if(month==1) {moisencours="FEVRIER";}
							    if(month==2) {moisencours="MARS";}
							    if(month==3) {moisencours="AVRIL";}
							    if(month==4) {moisencours="MAI";}
							    if(month==5) {moisencours="JUIN";}
							    if(month==6) {moisencours="JUILLET";}
							    if(month==7) {moisencours="AOUT";}
							    if(month==8) {moisencours="SEPTEMBRE";}
							    if(month==9) {moisencours="OCTOBRE";}
							    if(month==10) {moisencours="NOVEMBRE";}
							    if(month==11) {moisencours="DECEMBRE";}
//							
							    Mois moistop=new Mois();
							    moistop=moisService.findbymois(moisencours);
							    System.out.println("mois par nom: "+moistop.getMois());
							    PeriodePaie PeriodePaieCourant1 = new PeriodePaie();
//		                      
								PeriodePaieCourant1.setAnnee(anpaieANT);
							
								PeriodePaieCourant1.setDatefin(utilDate11); 
								PeriodePaieCourant1.setDatedeb(utilDate1);
								PeriodePaieCourant1.setMois(moistop);
								PeriodePaie periodePaieFind4=null;
								periodePaieFind4=periodePaieRepository.findByAnneeIdAndMoisId(anpaieANT.getId(), moistop.getId());
//								//Ajout
								if(periodePaieFind4==null){periodePaieRepository.save(PeriodePaieCourant1);}else{}
								
//					    	
					    }
			// === Génération des périodes N-1, N-2, N-3 === //
			for (int A = 1; A <= 4; A++) {

				int anPrec = anneeformat - A;
				System.out.println("Année précédente : " + anPrec);

				Exercice exPrec;
				List<Exercice> existList = exerciceRepository.findByAnnee(String.valueOf(anPrec));

				if (existList.size() > 0) {
					exPrec = existList.get(0);
				} else {
					exPrec = new Exercice();
					exPrec.setAnnee(String.valueOf(anPrec));
					exPrec = exerciceRepository.save(exPrec);
				}

				// Génération des 12 mois
				for (int m = 1; m <= 12; m++) {

					GregorianCalendar cal30 = new GregorianCalendar();
					GregorianCalendar calFin = new GregorianCalendar();

					cal30.set(anPrec, m - 1, 1);
					Date dateDeb = cal30.getTime();

					int lastDay = cal30.getActualMaximum(Calendar.DAY_OF_MONTH);
					calFin.set(anPrec, m - 1, lastDay);
					Date dateFin = calFin.getTime();

					int monthIndex = cal30.get(Calendar.MONTH);

					String nomMois = getMois(monthIndex);
					Mois moisEntity = moisService.findbymois(nomMois);

					PeriodePaie pp = new PeriodePaie();
					pp.setAnnee(exPrec);
					pp.setDatedeb(dateDeb);
					pp.setDatefin(dateFin);
					pp.setMois(moisEntity);

					// Vérification si déjà existant
					PeriodePaie exist = periodePaieRepository.findByAnneeIdAndMoisId(exPrec.getId(), moisEntity.getId());
					if (exist == null) {
						periodePaieRepository.save(pp);
					}
				}
			}


			periodepaieDTO.setRows(periodePaieRepository.findAll());
			periodepaieDTO.setResult("success");
			
			
			logger.info(new StringBuilder().append(">>>>> ").append(annee.toString()).append(" ENREGISTRE AVEC SUCCES").toString());
			//	}
	} catch(Exception ex){
			periodepaieDTO.setResult("failed");
			logger.error(ex.getMessage());
			logger.error(new StringBuilder().append(">>>>>  ERREUR SUR ENREGISTREMENT PERIODE PAIE [NOM : ").append(ex.getMessage()).toString());
			ex.getStackTrace();
		}
		return periodepaieDTO;
	}
	@Override
	public PeriodePaie findPeriodePaie(Long id) {
		// TODO Auto-generated method stub
		PeriodePaie periodepaie = new PeriodePaie();
		try{
			periodepaie = periodePaieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pret not found for id " + id));
			logger.info("la periode de paie (" + id + " :: " + periodepaie.getId() + ") a ete trouve avec succes");
		} catch(Exception ex){
			ex.getMessage();
			ex.getStackTrace();
			logger.error("une erreur a ete detectee lors de la recherche de la periode de paie (" + id + ")");
		}
		return periodepaie;
	}


	private String getMois(int month) {
		switch(month) {
			case 0: return "JANVIER";
			case 1: return "FEVRIER";
			case 2: return "MARS";
			case 3: return "AVRIL";
			case 4: return "MAI";
			case 5: return "JUIN";
			case 6: return "JUILLET";
			case 7: return "AOUT";
			case 8: return "SEPTEMBRE";
			case 9: return "OCTOBRE";
			case 10: return "NOVEMBRE";
			case 11: return "DECEMBRE";
		}
		return null;
	}








	@Override
	public PeriodePaie findPeriodeactive() {
		// TODO Auto-generated method stub
		return periodePaieRepository.recherchperiodeCloture();
	}
	@Override
	public List<PeriodePaie> listperiodesupAuPret() {
		// TODO Auto-generated method stub
		PeriodePaie periodeactif=new PeriodePaie();
		periodeactif=periodePaieRepository.recherchperiodeCloture();
		List<PeriodePaie> malist=new ArrayList<PeriodePaie>(); 
		List<PeriodePaie> malistretour=new ArrayList<PeriodePaie>();
		  malist=periodePaieRepository.findAll();
		   for(int i = 0; i < malist.size(); i++){
			  if(malist.get(i).getId()>=periodeactif.getId())
			  {
				  malistretour.add(malist.get(i))  ;
			  }
		   }
		return malistretour;
	}
	
	@Override
	public List<PeriodePaie> findPeriodeCloture() {
		// TODO Auto-generated method stub
		return periodePaieRepository.findByClotureTrue();
	}
	
	@Override
	public List<PeriodePaie> findPeriodeOuverte() {
		// TODO Auto-generated method stub
		return periodePaieRepository.findByClotureIsNotNull();
	}
	@Override
	public List<PeriodePaie> findPeriodedAnnee(String annee) {
		
		return periodePaieRepository.findByAnneeAnnee(annee,sortByIdAsc());
	}
	

	private Sort sortByIdAsc() {
		return Sort.by(Sort.Direction.ASC, "personnel.id");
	}

	@Override
	public List<PeriodePaie> listAllperiode() {
		// TODO Auto-generated method stub
		return periodePaieRepository.findAll();
	}
	/*@Override
	public PeriodePaie findPeriodePaiebydate(String date) {
		// TODO Auto-generated method stub
	
			// TODO Auto-generated method stub
			PeriodePaie periodepaie = new PeriodePaie();
			try{
				periodepaie = periodePaieRepository.findByDateFinBetween(periodePaieRepository.findOne(1L).getDatedeb(),);
				logger.info("la periode de paie (" + id + " :: " + periodepaie.getId() + ") a ete trouve avec succes");
			} catch(Exception ex){
				ex.getMessage();
				ex.getStackTrace();
				logger.error("une erreur a ete detectee lors de la recherche de la periode de paie (" + id + ")");
			}
			return null;
		
	}*/
	/*@Override
	public PeriodePaie findPeriodePaiebydate(String date) {
		// TODO Auto-generated method stub
		return null;
	}*/
	@Override
	public PeriodePaie findPeriodePaiebydate(String date) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<PeriodePaie> findByAnneeparAnnee(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
