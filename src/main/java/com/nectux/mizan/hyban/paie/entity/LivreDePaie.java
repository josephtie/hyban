package com.nectux.mizan.hyban.paie.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.nectux.mizan.hyban.paie.repository.PrimePersonnelRepository;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.repository.RubriqueRepository;
import com.nectux.mizan.hyban.parametrages.entity.Rubrique;
import com.nectux.mizan.hyban.utils.CalculRICF;
import com.nectux.mizan.hyban.utils.DifferenceDate;
import com.nectux.mizan.hyban.utils.Utils;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.utils.ProvisionConge;
import org.springframework.beans.factory.annotation.Autowired;

import static com.nectux.mizan.hyban.utils.CNPSCalculator.calculerCNPS;
import static com.nectux.mizan.hyban.utils.CalculRICF.getRICF;
import static com.nectux.mizan.hyban.utils.ITSCalculator.calculerITS;


public class LivreDePaie {

	RubriqueRepository rubriqueRepository;
	@Autowired
	private	PrimePersonnelRepository primePersonnelRepository;


	private String matricule;

	private String nomPrenom;

	private Float nombrePart;

	private int anciennete;

	private Double salaireBase;

	@Transient
	private String mtsalaireBase;

	private Double autreImposable;

	@Transient
	private String mtautreImposable;

	private Double autreIndemImposable;

	@Transient
	private String mtautreIndemImposable;


	private Double plafondFamiliale;
	@Transient
	private String mtplafondFamiliale;
	private Double autreNonImposable;

	@Transient
	private String mtautreNonImposable;

	private Double sursalaire;

	@Transient
	private String mtsursalaire;

	private Double primeAnciennete;

	private Integer moisdepresence;

	private Integer tempspresence;

	@Transient
	private String mtprimeAnciennete;

	private Double indemniteLogement;
	@Transient
	private String mtindemniteLogement;

	private Double brutImposable;
	@Transient
	private String mtbrutImposable;

	private Double autrePrelevmentSociale;
	@Transient
	private String mtautrePrelevmentSociale;

	private Double autrePrelevment;
	@Transient
	private String mtautrePrelevment;


	private Double autrePrelevmentMutuelle;
	@Transient
	private String mtautrePrelevmentMutuelle;

	private Double regularisation;
	@Transient
	private String mtregularisation;

	private Double brutNonImposable;
	@Transient
	private String mtbrutNonImposable;
	
	private Double its;


	@Transient
	private String mtits;

	private Double CMU;
	@Transient
	private String mtCMU ;

	private Double CMUSalarial;
	@Transient
	private String mtCMUSalarial ;

	private Double CMUPatronal;
	@Transient
	private String mtCMUPatronal ;
	
	private Double cn;
	@Transient
	private String mtcn;
	
	private Double igr;
	@Transient
	private String mtigr;
	
	private Double totalRetenueFiscale;
	@Transient
	private String mttotalRetenueFiscale;
	
	private Double cnps;
	@Transient
	private String mtcnps;
	
	private Double basecnps;
	@Transient
	private String mtbasecnps;
	
	private Double avceAcpte;
	@Transient
	private String mtavceAcpte;
	
	private Double pretAlios;
	@Transient
	private String mtpretAlios;
	
	private Double carec;
	@Transient
	private String mtcarec;
	
	private Double totalRetenue;
	@Transient
	private String mttotalRetenue;

	private Double RetenueSociale;
	@Transient
	private String mtRetenueSocial;

	private Double totalRetenueSociale;
	@Transient
	private String mttotalRetenueSocial;
	
	private Double indemniteRepresentation;
	@Transient
	private String mtindemniteRepresentation;
	
	private Double indemniteTransport;
	@Transient
	private String mtindemniteTransport;
	
	
	private Double indemniteTransportImp;
	@Transient
	private String mtindemniteTransportImp;
	
	private Double indemniteResponsabilte;
	@Transient
	private String mtindemniteResponsabilte;
	
	
	private Double netPayer;
	@Transient
	private String mtnetPayer;
	
	private Double totalBrut;
	@Transient
	private String mttotalBrut;
	
	private Double is;
	@Transient
	private String mtis;
	
	private Double ta;
	@Transient
	private String mtta;
	
	private Double fpc;
	@Transient
	private String mtfpc;

	private Double fpcregul;
	@Transient
	private String mtfpcregul;
	
	private Double prestationFamiliale;
	@Transient
	private String mtprestationFamiliale;
	
	private Double accidentTravail;
	@Transient
	private String mtaccidentTravail;
	
	private Double retraite;
	@Transient
	private String mtretraite;
	
	private Double totalPatronal;
	@Transient
	private String mttotalPatronal;
	
	private Double totalMasseSalariale;
	@Transient
	private String mttotalMasseSalariale;

	private Double retenueSociiale;
	@Transient
	private String mtretenueSociiale;
	
	private BulletinPaie bullpaie;
	
	private Double jourTravail;
	
	private Double temptravail;
	
	@Transient
	List<PrimePersonnel> listIndemniteBrut = new ArrayList<PrimePersonnel>();
	
	@Transient
	List<PrimePersonnel> listIndemniteNonImp = new ArrayList<PrimePersonnel>();

	@Transient
	List<Rubrique> listRubrique = new ArrayList<Rubrique>();

	@Transient
	List<PrimePersonnel> listIndemBrutNonImp = new ArrayList<PrimePersonnel>();


	@Transient
	List<PrimePersonnel> listRetenueMutuellt = new ArrayList<PrimePersonnel>();


	@Transient
	List<PrimePersonnel> listRetenueSociale = new ArrayList<PrimePersonnel>();

	@Transient
	List<PrimePersonnel> listGainsNet = new ArrayList<PrimePersonnel>();


	@ManyToOne
	@JoinColumn(nullable=false)
	private ContratPersonnel contratPersonnel;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;
	

	public LivreDePaie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LivreDePaie(String mat, String nomPre, Float nbrePart, int ancien, Double salBase, Double sursal, Double indemLog, Double avanceEtAccompte, Double pretALIOS,ContratPersonnel ctratperso,TempEffectif tempeffect,PeriodePaie plconge,List<PrimePersonnel> listIndemnite,List<PrimePersonnel> listIndemniteNonImp1,List<PrimePersonnel> listMutuelle,List<PrimePersonnel> listGains,List<PrimePersonnel> listRetenueSociale) {
		super();
		this.matricule = mat;
		this.nomPrenom = nomPre;
        final int JOURS_OUVRABLES_MOIS = 30;

		this.listIndemniteBrut=listIndemnite;
		//this.listRubrique=rubriqueRepository.findByActiveTrue();
		this.listIndemniteNonImp=listIndemniteNonImp1;
		this.listRetenueSociale =listRetenueSociale;
		this.listRetenueMutuellt=listMutuelle;
		this.listGainsNet=listGains;
		if(ctratperso.getPersonnel().getCarec()==true){

						if(tempeffect==null){

                            this.nombrePart = nbrePart != null ? nbrePart : 0F;
                            this.anciennete = ancien;
						   this.salaireBase = Math.ceil(salBase);
							if(sursal==null)
							  this.sursalaire = Math.ceil(0d);
							else
							 this.sursalaire = Math.ceil(sursal);

							if(ancien>= 2)
								this.primeAnciennete = Math.ceil((salaireBase) * ancien / 100);
							else
							 this.primeAnciennete = Math.ceil(0);

							if(indemLog==null)
								this.indemniteLogement = Math.ceil(0);
							else
							 this.indemniteLogement = Math.ceil(indemLog);


							 if(ctratperso.getIndemniteTransport()==null)
							 {
                                 this.indemniteTransportImp=Math.ceil(0);
							     this.indemniteTransport=Math.ceil(0);
							 }else{

							    if(ctratperso.getIndemniteTransport() > 30000){
									 this.indemniteTransportImp=ctratperso.getIndemniteTransport()-30000;
									 this.indemniteTransport=30000d;
								}else{
									    this.indemniteTransportImp=Math.ceil(0);
										this.indemniteTransport=ctratperso.getIndemniteTransport();
								}
							 }


								     this.autreIndemImposable=0d;

									       autreImposable=0d;
									if(listIndemniteBrut.size()>0 || listIndemniteBrut!=null){
										for(PrimePersonnel primeImpos : listIndemniteBrut){
											if(primeImpos.getPrime().getTaux()!=null && primeImpos.getValeur()>0)
											{
										     	autreImposable=autreImposable+(primeImpos.getMontant());

											}else{
												   if(primeImpos.getPrime().getMtExedent()!=null)
													   autreImposable=autreImposable+primeImpos.getMontant()-primeImpos.getPrime().getMtExedent();
												   else
													   autreImposable=autreImposable+primeImpos.getMontant();
										}
									}
								}

							 this.jourTravail=30d;
							this.temptravail=173.33d;
						}
					   else
					   {       this.anciennete = ancien;

                                this.nombrePart = nbrePart != null ? nbrePart : 0F;
							   this.salaireBase = Math.ceil(salBase*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS);
								if(sursal==null)
								  this.sursalaire = Math.ceil(0d);
								   else
								 this.sursalaire = Math.ceil(sursal*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS);

								if(ancien>= 2)
								  this.primeAnciennete = Math.ceil((salaireBase) * ancien / 100);
								else
									this.primeAnciennete = Math.ceil(0);

								if(indemLog==null)
								   this.indemniteLogement = Math.ceil(0);
								else
									this.indemniteLogement = Math.ceil(indemLog*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS);

							   if(ctratperso.getIndemniteTransport()==null)
								{  this.indemniteTransportImp=Math.ceil(0);
								  this.indemniteTransport=Math.ceil(0);

								}else{
								  if(ctratperso.getIndemniteTransport() > 30000){
									 this.indemniteTransportImp=(ctratperso.getIndemniteTransport()-30000)*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS;
									this.indemniteTransport=30000d;
									 }else{
									  this.indemniteTransportImp=0d;
										this.indemniteTransport=ctratperso.getIndemniteTransport();
								   }
								}

								this.autreIndemImposable=0d;
							   if(ctratperso.getIndemniteRepresent()==null) ctratperso.setIndemniteRepresent(0d);

			//			       if((indemniteTransport+ctratperso.getIndemniteRepresent())<(salaireBase+primeAnciennete+indemniteLogement+sursalaire+ctratperso.getIndemniteRepresent()+indemniteTransport)*10/100)
			//				      autreIndemImposable=0d;
			//					  else
			//					   autreIndemImposable=Math.ceil((indemniteTransport+ctratperso.getIndemniteRepresent()-((salaireBase+primeAnciennete+indemniteLogement+sursalaire+ctratperso.getIndemniteRepresent()+indemniteTransport)*10/100)));
			//		     //*tempeffect.getJourspresence()/30
								autreImposable=0d;
								if(listIndemniteBrut.size()>0 || listIndemniteBrut!=null){
							   for(PrimePersonnel primeImpos : listIndemniteBrut){
								   if(primeImpos.getPrime().getTaux()!=null && primeImpos.getValeur()>0)
								  {
									 autreImposable=autreImposable+(primeImpos.getValeur()*(primeImpos.getMontant()+(primeImpos.getMontant()*primeImpos.getPrime().getTaux()/100))*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS);
								  }else{

									   if(primeImpos.getPrime().getMtExedent()!=null)
										autreImposable=autreImposable+(primeImpos.getMontant()-primeImpos.getPrime().getMtExedent())*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS;
										 else
									   autreImposable=autreImposable+primeImpos.getMontant()*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS;
								  }
							   }
							 }
							this.jourTravail=tempeffect.getJourspresence();
							 this.temptravail=tempeffect.getHeurspresence();
					   }
		
			this.brutImposable = Math.ceil(salaireBase + sursalaire + primeAnciennete + indemniteLogement+indemniteTransportImp+autreIndemImposable+autreImposable);
			Double ricf = getRICF(nombrePart);
			double itsbrut =Math.ceil(calculerITS(brutImposable,true));
			this.its = Math.max(0, itsbrut - ricf / 12);
		   this.cn = 0d;
		    this.igr = 0d;
		    this.totalRetenueFiscale = Math.ceil(its );//+ cn + igr);
	     	if(ctratperso.getIndemniteRepresent()==null)
			this.indemniteRepresentation = Math.ceil(0);
			else
			this.indemniteRepresentation = Math.ceil(ctratperso.getIndemniteRepresent());


//		if(ctratperso.getIndemniteResp()==null)
//			this.indemniteResponsabilte = Math.ceil(0);
//		else
//			this.indemniteResponsabilte = Math.ceil(ctratperso.getIndemniteResp());

			autreNonImposable=0d;
			if(listIndemniteNonImp.size()>0 || listIndemniteNonImp!=null){
  			for(PrimePersonnel primeImpos : listIndemniteNonImp){	
  				/*if(primeImpos.getPrime().getTaux()!=null && primeImpos.getValeur()>0)
  				{
  					autreNonImposable=autreNonImposable+(primeImpos.getMontant()*primeImpos.getPrime().getTaux()*primeImpos.getValeur())*tempeffect.getJourspresence()/30;
  				}else{*/
  				if(tempeffect==null){
  					 if(primeImpos.getPrime().getMtExedent()!=null)
  					   autreNonImposable=autreNonImposable+primeImpos.getPrime().getMtExedent();
  					 else
  						autreNonImposable=autreNonImposable+primeImpos.getMontant(); 
  				}else{
  					
  					 if(primeImpos.getPrime().getMtExedent()!=null)
    					   autreNonImposable=autreNonImposable+primeImpos.getPrime().getMtExedent()*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS;
    					 else    						
  					       autreNonImposable=autreNonImposable+(primeImpos.getMontant()*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS);
  				}
  				//}
  			}	
  			}

				this.brutNonImposable=indemniteRepresentation+indemniteTransport+autreNonImposable;
		//this.indemniteRepresentation = Math.ceil(calculerIndemniterRepresentation());
				this.basecnps=brutImposable + indemniteRepresentation+autreNonImposable - autreIndemImposable;
				//double cnpsBrut[]=calculerCNPS(basecnps,2);
				this.cnps = Math.ceil(calculCNPS(basecnps));//Math.ceil(cnpsBrut[2]);
				this.avceAcpte = Math.ceil(avanceEtAccompte);
				this.pretAlios = Math.ceil(pretALIOS);
		//this.indemniteTransport = Math.ceil(calculerIndemniteTransport());
			this.totalBrut = Math.ceil(brutImposable + indemniteRepresentation+ indemniteTransport+autreNonImposable);
			autrePrelevmentSociale=0d; double patronalcmu=0d;double salarialcmu=0d;
			if(listRetenueSociale != null && !listRetenueSociale.isEmpty()){
				for(PrimePersonnel sociale : listRetenueSociale){
					if (sociale.getPrime().getPermanent()) {
						if (sociale.getPrime().getLibelle().equals("CMU Patronal")) {
							patronalcmu = sociale.getMontant();
						} else if (sociale.getPrime().getLibelle().equals("CMU Salarial")) {
							salarialcmu = sociale.getMontant();
						} else {
							autrePrelevmentSociale += sociale.getMontant();
						}
					}

				}
			}
			//this.CMU=autrePrelevmentSociale;
			this.CMUSalarial=salarialcmu;
			this.retenueSociiale=autrePrelevmentSociale +cnps;
            this.CMUPatronal=patronalcmu;
			autrePrelevmentMutuelle=0d;
			if(listMutuelle.size()>0 || listMutuelle!=null){
				for(PrimePersonnel mutuell : listMutuelle){
					autrePrelevmentMutuelle=autrePrelevmentMutuelle+mutuell.getMontant();
				}
			}

				this.totalRetenue = Math.ceil(totalRetenueFiscale + avceAcpte + pretAlios+autrePrelevmentMutuelle +retenueSociiale);
				regularisation=0d;
			if(listGains.size()>0 || listGains!=null){
				for(PrimePersonnel primeGains : listGains){
					regularisation=regularisation+primeGains.getMontant();
				}
			}
				this.netPayer = Math.ceil((brutImposable + indemniteRepresentation + indemniteTransport+autreNonImposable)+ regularisation -autreIndemImposable- totalRetenue);
				this.is = its;
				this.ta = Math.ceil(brutImposable * 0.4 / 100);
				this.fpc =Math.ceil(brutImposable * 0.6 / 100);
				this.fpcregul =Math.ceil(brutImposable * 0.6 / 100);
				this.prestationFamiliale = Math.ceil(calcalerPrestationFamilial());
				this.accidentTravail = Math.ceil(calculerAccidentTravail());
				this.retraite = Math.ceil((brutImposable + indemniteRepresentation+autreNonImposable) * 7.7 / 100);
				this.totalPatronal =Math.ceil(is + ta + fpc+fpcregul +prestationFamiliale + accidentTravail + retraite+ CMUPatronal); ///Math.ceil( ta + fpc + prestationFamiliale + accidentTravail + retraite);
				this.totalMasseSalariale = Math.ceil(brutImposable + indemniteRepresentation+ indemniteTransport +autreNonImposable+regularisation+ totalPatronal);
				this.tempspresence= countnbreJrdu(ctratperso.getPersonnel().getDateRetourcge(), plconge.getDatefin(), ctratperso);
				this.moisdepresence= ProvisionConge.calculerTempsPresence(ctratperso.getPersonnel().getDateRetourcge(), plconge.getDatefin());
	    }
	    else{

			///// traitement consultant et stagiaire   //////////
                    nombrePart = 0F;
                    anciennete = 0;
			       autreImposable=0d;
			    if(listIndemniteBrut.size()>0 || listIndemniteBrut!=null){
				  for(PrimePersonnel primeImpos : listIndemniteBrut){
					if(primeImpos.getPrime().getTaux()!=null && primeImpos.getValeur()>0)
					{
						autreImposable=autreImposable+primeImpos.getValeur()*(primeImpos.getMontant()+(primeImpos.getMontant()*primeImpos.getPrime().getTaux()/100));
					}else{
						if(primeImpos.getPrime().getMtExedent()!=null)
							autreImposable=autreImposable+primeImpos.getMontant()-primeImpos.getPrime().getMtExedent();
						else
							autreImposable=autreImposable+primeImpos.getMontant();
					}
				}

				autreNonImposable=0d;
				if(listIndemniteNonImp.size()>0 || listIndemniteNonImp!=null){
					for(PrimePersonnel primeImpos : listIndemniteNonImp){
  				/*if(primeImpos.getPrime().getTaux()!=null && primeImpos.getValeur()>0)
  				{
  					autreNonImposable=autreNonImposable+(primeImpos.getMontant()*primeImpos.getPrime().getTaux()*primeImpos.getValeur())*tempeffect.getJourspresence()/30;
  				}else{*/
						if(tempeffect==null){
							if(primeImpos.getPrime().getMtExedent()!=null)
								autreNonImposable=autreNonImposable+primeImpos.getPrime().getMtExedent();
							else
								autreNonImposable=autreNonImposable+primeImpos.getMontant();
						}else{

							if(primeImpos.getPrime().getMtExedent()!=null)
								autreNonImposable=autreNonImposable+primeImpos.getPrime().getMtExedent()*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS;
							else
								autreNonImposable=autreNonImposable+(primeImpos.getMontant()*tempeffect.getJourspresence()/JOURS_OUVRABLES_MOIS);
						}
						//}
					}
				}
					regularisation=0d;
					if(listGains.size()>0 || listGains!=null){
						for(PrimePersonnel primeGains : listGains){

							regularisation=regularisation+primeGains.getMontant();

							//}
						}
					}
			//if(ctratperso.getPersonnel().getCarec()==false)
			//{	 this.its = Math.ceil(0);
				this.cn =  Math.ceil(0);
				this.igr = Math.ceil(0);
				this.totalRetenueFiscale = Math.ceil(0);
				this.brutImposable = Math.ceil(autreImposable);
				this.brutNonImposable = Math.ceil(autreNonImposable);
				this.basecnps=Math.ceil(0);
				this.is = Math.ceil(0);
				this.ta = Math.ceil(0);
				this.fpc = Math.ceil(0);
				this.prestationFamiliale = Math.ceil(0);
				this.accidentTravail = Math.ceil(0);
				this.retraite = Math.ceil(0);
				this.cnps = Math.ceil(0);
				this.retraite = Math.ceil(0);
				this.plafondFamiliale = Math.ceil(0);

				avceAcpte=Math.ceil(0); pretAlios=Math.ceil(0);
					autrePrelevment=0d;
					if(listMutuelle.size()>0 || listMutuelle!=null){
						for(PrimePersonnel mutuell : listMutuelle){

							autrePrelevment=autrePrelevment+mutuell.getMontant();

							//}
						}
					}
				this.totalRetenue = Math.ceil(totalRetenueFiscale + cnps + avceAcpte + pretAlios +autrePrelevment);
				this.netPayer = Math.ceil(brutImposable +regularisation+ brutNonImposable-totalRetenue)+ctratperso.getNetAPayer();
				//this.is = Math.ceil(brutImposable * 1.2 / 100);
				//this.ta = Math.ceil(brutImposable * 0.4 / 100);
				//this.fpc = Math.ceil(brutImposable * 1.2 / 100);
				//this.prestationFamiliale = Math.ceil(calcalerPrestationFamilial());
				//this.accidentTravail = Math.ceil(calculerAccidentTravail());
				//this.retraite = Math.ceil((brutImposable + indemniteRepresentation+indemniteResponsabilte+autreNonImposable) * 7.7 / 100);
				this.totalPatronal = Math.ceil(is + ta + fpc + prestationFamiliale + accidentTravail + retraite);
				this.totalMasseSalariale = Math.ceil(brutImposable + brutNonImposable+regularisation+ totalPatronal)+ctratperso.getNetAPayer();
				this.tempspresence= countnbreJrdu(ctratperso.getPersonnel().getDateRetourcge(), plconge.getDatefin(), ctratperso);
				this.moisdepresence= ProvisionConge.calculerTempsPresence(ctratperso.getPersonnel().getDateRetourcge(), plconge.getDatefin());
			//}

		}
		}
	}
	
	public Double calculerAccidentTravail(){
		Double pf = brutImposable + autreNonImposable;
		if(pf == 70000)
			pf = 70000 * 4.0 / 100;
		else if(pf > 70000)
			pf = brutImposable * 4 / 100;
		else 
			pf = 0.0;
		return pf;
	}
	
	public Double calcalerPrestationFamilial(){
		Double pf = brutImposable + autreNonImposable;
		if(pf == 70000)
			pf = 70000 * 5.75 / 100;
		else if(pf > 70000)
			pf = brutImposable * 5.75 / 100;
		else 
			pf = 0.0;
		return pf;
	}
	
	public Double calculerIndemniteTransport(){
		Double it = brutImposable * 10 / 100 - 25000.0;
		if(it > 0)
			it = 25000.0;
		else
			it = 0.0;
		return it;
	}
	
	public Double calculerIndemniterRepresentation(){
		Double ir = brutImposable * 10 / 100 - 25000.0;
		if(ir > 0)
			ir = brutImposable * 10 / 100 - 25000.0;
		else 
			ir = 0.0;
		return ir;
	}
	
//	public Double calculerCN(){
//		Double cn;
//		if(brutImposable > 250000.0)
//			cn = (brutImposable - 250000.0) * 8 / 100 + 4700;
//		else if(brutImposable > 162500.0)
//			cn = (brutImposable - 162500.0) * 4 / 100 + 1200;
//		else if(brutImposable > 62500.0)
//			cn = brutImposable * 1.2 / 100 - 750;
//		else
//			cn = 0.0;
//		return cn;
//	}
	
//	public Double calculerIGR(){
//		Double igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100;
//		if(igr > 842167.0)
//			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 60 / 160 - 98633.0 * nombrePart;
//		else if(igr > 389084.0)
//			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 45 / 145 - 44181.0 * nombrePart;
//		else if(igr > 220334.0)
//			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 35 / 135 - 24306.0 * nombrePart;
//		else if(igr > 126584.0)
//			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 25 / 125 - 11250.0 * nombrePart;
//		else if(igr > 81584.0)
//			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 20 / 120 - 7031.0 * nombrePart;
//		else if(igr > 45584.0)
//			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 115 - 4076.0 * nombrePart;
//		else if(igr > 25000.0)
//			igr = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 110 - 2273.0 * nombrePart;
//		else
//			igr = 0.0;
//		return igr;
//	}
	
	public Double calculCNPS(Double basecnps){
		Double cnps = (basecnps ); //3000000
		if(cnps < 3375000.0)
			cnps = (basecnps ) * 6.3 / 100;
		else
			cnps = 3375000.0 * 6.3 / 100;
		return cnps;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNomPrenom() {
		return nomPrenom;
	}

	public void setNomPrenom(String nomPrenom) {
		this.nomPrenom = nomPrenom;
	}

	public Float getNombrePart() {
		return nombrePart;
	}

	public void setNombrePart(Float nombrePart) {
		this.nombrePart = nombrePart;
	}

	public int getAnciennete() {
		return anciennete;
	}

	public void setAnciennete(int anciennete) {
		this.anciennete = anciennete;
	}

	public Double getSalaireBase() {
		return salaireBase;
	}

	public void setSalaireBase(Double salaireBase) {
		this.salaireBase = salaireBase;
	}

	public Double getSursalaire() {
		return sursalaire;
	}

	public Integer getTempspresence() {
		return tempspresence;
	}

	public Integer getMoisdepresence() {
		return moisdepresence;
	}

	public void setMoisdepresence(Integer moisdepresence) {
		this.moisdepresence = moisdepresence;
	}

	public void setTempspresence(Integer tempspresence) {
		this.tempspresence = tempspresence;
	}

	public void setSursalaire(Double sursalaire) {
		this.sursalaire = sursalaire;
	}

	public Double getPrimeAnciennete() {
		return primeAnciennete;
	}

	public void setPrimeAnciennete(Double primeAnciennete) {
		this.primeAnciennete = primeAnciennete;
	}

	public Double getIndemniteLogement() {
		return indemniteLogement;
	}

	public void setIndemniteLogement(Double indemniteLogement) {
		this.indemniteLogement = indemniteLogement;
	}

	public Double getBrutImposable() {
		return brutImposable;
	}

	public void setBrutImposable(Double brutImposable) {
		this.brutImposable = brutImposable;
	}

	public Double getIts() {
		return its;
	}

	public void setIts(Double its) {
		this.its = its;
	}

	public Double getCn() {
		return cn;
	}

	public void setCn(Double cn) {
		this.cn = cn;
	}

	public Double getIgr() {
		return igr;
	}

	public void setIgr(Double igr) {
		this.igr = igr;
	}

	public Double getTotalRetenueFiscale() {
		return totalRetenueFiscale;
	}

	public void setTotalRetenueFiscale(Double totalRetenueFiscale) {
		this.totalRetenueFiscale = totalRetenueFiscale;
	}

	public Double getCnps() {
		return cnps;
	}

	public void setCnps(Double cnps) {
		this.cnps = cnps;
	}

	public Double getAvceAcpte() {
		return avceAcpte;
	}

	public void setAvceAcpte(Double avceAcpte) {
		this.avceAcpte = avceAcpte;
	}

	public Double getPretAlios() {
		return pretAlios;
	}

	public void setPretAlios(Double pretAlios) {
		this.pretAlios = pretAlios;
	}

	public Double getCarec() {
		return carec;
	}

	public void setCarec(Double carec) {
		this.carec = carec;
	}

	public Double getTotalRetenue() {
		return totalRetenue;
	}

	public void setTotalRetenue(Double totalRetenue) {
		this.totalRetenue = totalRetenue;
	}

	public Double getIndemniteRepresentation() {
		return indemniteRepresentation;
	}

	public void setIndemniteRepresentation(Double indemniteRepresentation) {
		this.indemniteRepresentation = indemniteRepresentation;
	}

	public Double getIndemniteTransport() {
		return indemniteTransport;
	}

	public void setIndemniteTransport(Double indemniteTransport) {
		this.indemniteTransport = indemniteTransport;
	}

	public Double getNetPayer() {
		return netPayer;
	}

	public void setNetPayer(Double netPayer) {
		this.netPayer = netPayer;
	}

	public Double getTotalBrut() {
		return totalBrut;
	}

	public void setTotalBrut(Double totalBrut) {
		this.totalBrut = totalBrut;
	}

	public Double getIs() {
		return is;
	}

	public void setIs(Double is) {
		this.is = is;
	}

	public Double getTa() {
		return ta;
	}

	public void setTa(Double ta) {
		this.ta = ta;
	}

	public Double getFpc() {
		return fpc;
	}

	public void setFpc(Double fpc) {
		this.fpc = fpc;
	}

	public Double getPrestationFamiliale() {
		return prestationFamiliale;
	}

	public void setPrestationFamiliale(Double prestationFamiliale) {
		this.prestationFamiliale = prestationFamiliale;
	}

	public Double getAccidentTravail() {
		return accidentTravail;
	}

	public void setAccidentTravail(Double accidentTravail) {
		this.accidentTravail = accidentTravail;
	}

	public Double getRetraite() {
		return retraite;
	}

	public void setRetraite(Double retraite) {
		this.retraite = retraite;
	}

	public Double getTotalPatronal() {
		return totalPatronal;
	}

	public void setTotalPatronal(Double totalPatronal) {
		this.totalPatronal = totalPatronal;
	}

	public Double getTotalMasseSalariale() {
		return totalMasseSalariale;
	}

	public void setTotalMasseSalariale(Double totalMasseSalariale) {
		this.totalMasseSalariale = totalMasseSalariale;
	}

	
	
	public ContratPersonnel getContratPersonnel() {
		return contratPersonnel;
	}

	public void setContratPersonnel(ContratPersonnel contratPersonnel) {
		this.contratPersonnel = contratPersonnel;
	}

	public PeriodePaie getPeriodePaie() {
		return periodePaie;
	}

	public void setPeriodePaie(PeriodePaie periodePaie) {
		this.periodePaie = periodePaie;
	}

	
	public Double getJourTravail() {
		return jourTravail;
	}

	public void setJourTravail(Double jourTravail) {
		this.jourTravail = jourTravail;
	}

	public Double getTemptravail() {
		return temptravail;
	}

	public void setTemptravail(Double temptravail) {
		this.temptravail = temptravail;
	}

	public BulletinPaie getBullpaie() {
		return bullpaie;
	}

	public void setBullpaie(BulletinPaie bullpaie) {
		this.bullpaie = bullpaie;
	}

	
	
	public String getMtsalaireBase() {
	
		return mtsalaireBase= Utils.formattingAmount(salaireBase);
	}

	public void setMtsalaireBase(String mtsalaireBase) {
		this.mtsalaireBase = mtsalaireBase;
	}

	public String getMtsursalaire() {
		
		return mtsursalaire= Utils.formattingAmount(sursalaire);
	}

	public void setMtsursalaire(String mtsursalaire) {
		this.mtsursalaire = mtsursalaire;
	}

	public String getMtprimeAnciennete() {
		
		return mtprimeAnciennete= Utils.formattingAmount(primeAnciennete);
	}

	public void setMtprimeAnciennete(String mtprimeAnciennete) {
		this.mtprimeAnciennete = mtprimeAnciennete;
	}

	public String getMtindemniteLogement() {
	
		return mtindemniteLogement= Utils.formattingAmount(indemniteLogement);
	}

	public void setMtindemniteLogement(String mtindemniteLogement) {
		this.mtindemniteLogement = mtindemniteLogement;
	}

	public String getMtbrutImposable() {
	
		return mtbrutImposable= Utils.formattingAmount(brutImposable);
	}

	public void setMtbrutImposable(String mtbrutImposable) {
		this.mtbrutImposable = mtbrutImposable;
	}

	public String getMtits() {
		
		return mtits= Utils.formattingAmount(its);
	}

	public void setMtits(String mtits) {
		this.mtits = mtits;
	}

	public String getMtcn() {
		
		return mtcn= Utils.formattingAmount(cn);
	}

	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}

	public String getMtigr() {
	
		return mtigr= Utils.formattingAmount(igr);
	}

	public void setMtigr(String mtigr) {
		this.mtigr = mtigr;
	}

	public String getMttotalRetenueFiscale() {
		
		return mttotalRetenueFiscale= Utils.formattingAmount(totalRetenueFiscale);
	}

	public void setMttotalRetenueFiscale(String mttotalRetenueFiscale) {
		this.mttotalRetenueFiscale = mttotalRetenueFiscale;
	}

	public String getMtcnps() {
		
		return mtcnps= Utils.formattingAmount(cnps);
	}

	public void setMtcnps(String mtcnps) {
		this.mtcnps = mtcnps;
	}

	public String getMtavceAcpte() {
		
		return mtavceAcpte= Utils.formattingAmount(avceAcpte);
	}

	public void setMtavceAcpte(String mtavceAcpte) {
		this.mtavceAcpte = mtavceAcpte;
	}

	public String getMtpretAlios() {
		
		return mtpretAlios= Utils.formattingAmount(pretAlios);
	}

	public void setMtpretAlios(String mtpretAlios) {
		this.mtpretAlios = mtpretAlios;
	}

	public String getMtcarec() {
		
		return mtcarec= Utils.formattingAmount(carec);
	}

	public void setMtcarec(String mtcarec) {
		this.mtcarec = mtcarec;
	}

	public String getMttotalRetenue() {
	
		return mttotalRetenue= Utils.formattingAmount(totalRetenue);
	}

	public void setMttotalRetenue(String mttotalRetenue) {
		this.mttotalRetenue = mttotalRetenue;
	}

	public String getMtindemniteRepresentation() {
		
		return mtindemniteRepresentation= Utils.formattingAmount(indemniteRepresentation);
	}

	public void setMtindemniteRepresentation(String mtindemniteRepresentation) {
		this.mtindemniteRepresentation = mtindemniteRepresentation;
	}

	public String getMtindemniteTransport() {
		
		return mtindemniteTransport= Utils.formattingAmount(indemniteTransport);
	}

	public void setMtindemniteTransport(String mtindemniteTransport) {
		this.mtindemniteTransport = mtindemniteTransport;
		
	}

	public String getMtnetPayer() {
		
		return mtnetPayer= Utils.formattingAmount(netPayer);
	}

	public void setMtnetPayer(String mtnetPayer) {
		this.mtnetPayer = mtnetPayer;
	}

	public String getMttotalBrut() {
		
		return mttotalBrut= Utils.formattingAmount(totalBrut);
	}

	public void setMttotalBrut(String mttotalBrut) {
		this.mttotalBrut = mttotalBrut;
	}

	public String getMtis() {
		
		return mtis= Utils.formattingAmount(is);
	}

	public void setMtis(String mtis) {
		this.mtis = mtis;
	}

	public String getMtta() {
		
		return mtta= Utils.formattingAmount(ta);
	}

	public void setMtta(String mtta) {
		this.mtta = mtta;
	}

	public String getMtfpc() {
	
		return mtfpc= Utils.formattingAmount(fpc);
	}

	public void setMtfpc(String mtfpc) {

		this.mtfpc = mtfpc;
	}

	public String getMtprestationFamiliale() {
		
		return mtprestationFamiliale= Utils.formattingAmount(prestationFamiliale);
	}

	public void setMtprestationFamiliale(String mtprestationFamiliale) {
		this.mtprestationFamiliale = mtprestationFamiliale;
	}

	public String getMtaccidentTravail() {
		
		return mtaccidentTravail= Utils.formattingAmount(accidentTravail);
	}

	public void setMtaccidentTravail(String mtaccidentTravail) {
		this.mtaccidentTravail = mtaccidentTravail;
	}

	public String getMtretraite() {
		return mtretraite= Utils.formattingAmount(retraite);
	}

	public void setMtretraite(String mtretraite) {
		this.mtretraite = mtretraite;
	}

	public String getMttotalPatronal() {
	
		return mttotalPatronal= Utils.formattingAmount(totalPatronal);
	}

	public void setMttotalPatronal(String mttotalPatronal) {
		this.mttotalPatronal = mttotalPatronal;
	}

	public String getMttotalMasseSalariale() {
		return mttotalMasseSalariale= Utils.formattingAmount(totalMasseSalariale);
	}

	public void setMttotalMasseSalariale(String mttotalMasseSalariale) {
		this.mttotalMasseSalariale = mttotalMasseSalariale;
	}

	public Double getBasecnps() {
		return basecnps;
	}

	public void setBasecnps(Double basecnps) {
		this.basecnps = basecnps;
	}

	public String getMtbasecnps() {
		return mtbasecnps= Utils.formattingAmount(basecnps);
	}

	public void setMtbasecnps(String mtbasecnps) {
		this.mtbasecnps = mtbasecnps;
	}

	public Double getIndemniteTransportImp() {
		return indemniteTransportImp;
	}

	public void setIndemniteTransportImp(Double indemniteTransportImp) {
		this.indemniteTransportImp = indemniteTransportImp;
	}

	public String getMtindemniteTransportImp() {
		return mtindemniteTransportImp=Utils.formattingAmount(indemniteTransportImp);
	}

	public void setMtindemniteTransportImp(String mtindemniteTransportImp) {
		this.mtindemniteTransportImp = mtindemniteTransportImp;
	}

	public Double getIndemniteResponsabilte() {
		return indemniteResponsabilte;
	}

	public void setIndemniteResponsabilte(Double indemniteResponsabilte) {
		this.indemniteResponsabilte = indemniteResponsabilte;
	}

	public String getMtindemniteResponsabilte() {
		return mtindemniteResponsabilte=Utils.formattingAmount(indemniteResponsabilte);
	}

	public void setMtindemniteResponsabilte(String mtindemniteResponsabilte) {
		this.mtindemniteResponsabilte = mtindemniteResponsabilte;
	}

	public Double getBrutNonImposable() {
		return brutNonImposable;
	}

	public void setBrutNonImposable(Double brutNonImposable) {
		this.brutNonImposable = brutNonImposable;
	}

	public String getMtbrutNonImposable() {
		return mtbrutNonImposable=Utils.formattingAmount(brutNonImposable);
	}

	public void setMtbrutNonImposable(String mtbrutNonImposable) {
		this.mtbrutNonImposable = mtbrutNonImposable;
	}

	public List<PrimePersonnel> getListIndemniteBrut() {
		return listIndemniteBrut;
	}

	public void setListIndemniteBrut(List<PrimePersonnel> listIndemniteBrut) {
		this.listIndemniteBrut = listIndemniteBrut;
	}

	public List<PrimePersonnel> getListIndemniteNonImp() {
		return listIndemniteNonImp;
	}

	public void setListIndemniteNonImp(List<PrimePersonnel> listIndemniteNonImp) {
		this.listIndemniteNonImp = listIndemniteNonImp;
	}

	public List<PrimePersonnel> getListIndemBrutNonImp() {
		return listIndemBrutNonImp;
	}

	public void setListIndemBrutNonImp(List<PrimePersonnel> listIndemBrutNonImp) {
		this.listIndemBrutNonImp = listIndemBrutNonImp;
	}

	public Double getAutreImposable() {
		return autreImposable;
	}

	public void setAutreImposable(Double autreImposable) {
		this.autreImposable = autreImposable;
	}

	public String getMtautreImposable() {
		return mtautreImposable=Utils.formattingAmount(autreImposable);
	}

	public void setMtautreImposable(String mtautreImposable) {
		this.mtautreImposable = mtautreImposable;
	}

	public Double getAutreNonImposable() {
		return autreNonImposable;
	}

	public void setAutreNonImposable(Double autreNonImposable) {
		this.autreNonImposable = autreNonImposable;
	}

	public String getMtautreNonImposable() {
		return mtautreNonImposable=Utils.formattingAmount(autreNonImposable);
	}

	public void setMtautreNonImposable(String mtautreNonImposable) {
		this.mtautreNonImposable = mtautreNonImposable;
	}

	public Double getAutreIndemImposable() {
		return autreIndemImposable;
	}

	public void setAutreIndemImposable(Double autreIndemImposable) {
		this.autreIndemImposable = autreIndemImposable;
	}

	public String getMtautreIndemImposable() {
		return mtautreIndemImposable=Utils.formattingAmount(autreIndemImposable);
	}

	public void setMtautreIndemImposable(String mtautreIndemImposable) {
		this.mtautreIndemImposable = mtautreIndemImposable;
	}

	public Double getPlafondFamiliale() {
		return plafondFamiliale;
	}

	public void setPlafondFamiliale(Double plafondFamiliale) {
		this.plafondFamiliale = plafondFamiliale;
	}

	public String getMtplafondFamiliale() {
		return mtplafondFamiliale;
	}

	public void setMtplafondFamiliale(String mtplafondFamiliale) {
		this.mtplafondFamiliale = mtplafondFamiliale;
	}

	public List<PrimePersonnel> getListRetenueMutuellt() {
		return listRetenueMutuellt;
	}

	public void setListRetenueMutuellt(List<PrimePersonnel> listRetenueMutuellt) {
		this.listRetenueMutuellt = listRetenueMutuellt;
	}

	public List<PrimePersonnel> getListGainsNet() {
		return listGainsNet;
	}

	public void setListGainsNet(List<PrimePersonnel> listGainsNet) {
		this.listGainsNet = listGainsNet;
	}

	public Double getAutrePrelevment() {
		return autrePrelevment;
	}

	public void setAutrePrelevment(Double autrePrelevment) {
		this.autrePrelevment = autrePrelevment;
	}

	public String getMtautrePrelevment() {
		return mtautrePrelevment=Utils.formattingAmount(autrePrelevment);
	}

	public void setMtautrePrelevment(String mtautrePrelevment) {
		this.mtautrePrelevment = mtautrePrelevment;
	}

	public Double getRegularisation() {
		return regularisation;
	}

	public void setRegularisation(Double regularisation) {
		this.regularisation = regularisation;
	}

	public String getMtregularisation() {
		return mtregularisation=Utils.formattingAmount(regularisation);
	}

	public void setMtregularisation(String mtregularisation) {
		this.mtregularisation = mtregularisation;
	}

	public List<PrimePersonnel> getListRetenueSociale() {
		return listRetenueSociale;
	}

	public void setListRetenueSociale(List<PrimePersonnel> listRetenueSociale) {
		this.listRetenueSociale = listRetenueSociale;
	}

	public Double getCMU() {
		return CMU;
	}

	public void setCMU(Double CMU) {
		this.CMU = CMU;
	}

	public String getMtCMU() {
		return mtCMU;
	}

	public void setMtCMU(String mtCMU) {
		this.mtCMU = mtCMU;
	}

	public Double getAutrePrelevmentSociale() {
		return autrePrelevmentSociale;
	}

	public void setAutrePrelevmentSociale(Double autrePrelevmentSociale) {
		this.autrePrelevmentSociale = autrePrelevmentSociale;
	}

	public String getMtautrePrelevmentSociale() {
		return mtautrePrelevmentSociale;
	}

	public void setMtautrePrelevmentSociale(String mtautrePrelevmentSociale) {
		this.mtautrePrelevmentSociale = mtautrePrelevmentSociale;
	}

	public Double getRetenueSociale() {
		return RetenueSociale;
	}

	public void setRetenueSociale(Double retenueSociale) {
		RetenueSociale = retenueSociale;
	}

	public String getMtRetenueSocial() {
		return mtRetenueSocial;
	}

	public void setMtRetenueSocial(String mtRetenueSocial) {
		this.mtRetenueSocial = mtRetenueSocial;
	}

	public Double getTotalRetenueSociale() {
		return totalRetenueSociale;
	}

	public void setTotalRetenueSociale(Double totalRetenueSociale) {
		this.totalRetenueSociale = totalRetenueSociale;
	}

	public String getMttotalRetenueSocial() {
		return mttotalRetenueSocial;
	}

	public void setMttotalRetenueSocial(String mttotalRetenueSocial) {
		this.mttotalRetenueSocial = mttotalRetenueSocial;
	}

	public Double getRetenueSociiale() {
		return retenueSociiale;
	}



	public void setRetenueSociiale(Double retenueSociiale) {
		this.retenueSociiale = retenueSociiale;
	}

	public String getMtretenueSociiale() {
		return mtretenueSociiale;
	}

	public void setMtretenueSociiale(String mtretenueSociiale) {
		this.mtretenueSociiale = mtretenueSociiale;
	}

	public Double getCMUSalarial() {
		return CMUSalarial;
	}

	public void setCMUSalarial(Double CMUSalarial) {
		this.CMUSalarial = CMUSalarial;
	}

	public String getMtCMUSalarial() {
		return mtCMUSalarial;
	}

	public void setMtCMUSalarial(String mtCMUSalarial) {
		this.mtCMUSalarial = mtCMUSalarial;
	}

	public Double getCMUPatronal() {
		return CMUPatronal;
	}

	public void setCMUPatronal(Double CMUPatronal) {
		this.CMUPatronal = CMUPatronal;
	}

	public String getMtCMUPatronal() {
		return mtCMUPatronal;
	}

	public void setMtCMUPatronal(String mtCMUPatronal) {
		this.mtCMUPatronal = mtCMUPatronal;
	}

	public Double getFpcregul() {
		return fpcregul;
	}

	public void setFpcregul(Double fpcregul) {
		this.fpcregul = fpcregul;
	}

	public String getMtfpcregul() {
		return mtfpcregul=Utils.formattingAmount(fpcregul);
	}

	public void setMtfpcregul(String mtfpcregul) {
		this.mtfpcregul = mtfpcregul;
	}

	public String getMtautrePrelevmentMutuelle() {
		return mtautrePrelevmentMutuelle;
	}

	public void setMtautrePrelevmentMutuelle(String mtautrePrelevmentMutuelle) {
		this.mtautrePrelevmentMutuelle = mtautrePrelevmentMutuelle;
	}

	public Double getAutrePrelevmentMutuelle() {
		return autrePrelevmentMutuelle;
	}

	public void setAutrePrelevmentMutuelle(Double autrePrelevmentMutuelle) {
		this.autrePrelevmentMutuelle = autrePrelevmentMutuelle;
	}

	@Override
	public String toString() {
		return "LivreDePaie{" +
				"rubriqueRepository=" + rubriqueRepository +
				", primePersonnelRepository=" + primePersonnelRepository +
				", matricule='" + matricule + '\'' +
				", nomPrenom='" + nomPrenom + '\'' +
				", nombrePart=" + nombrePart +
				", anciennete=" + anciennete +
				", salaireBase=" + salaireBase +
				", mtsalaireBase='" + mtsalaireBase + '\'' +
				", autreImposable=" + autreImposable +
				", mtautreImposable='" + mtautreImposable + '\'' +
				", autreIndemImposable=" + autreIndemImposable +
				", mtautreIndemImposable='" + mtautreIndemImposable + '\'' +
				", plafondFamiliale=" + plafondFamiliale +
				", mtplafondFamiliale='" + mtplafondFamiliale + '\'' +
				", autreNonImposable=" + autreNonImposable +
				", mtautreNonImposable='" + mtautreNonImposable + '\'' +
				", sursalaire=" + sursalaire +
				", mtsursalaire='" + mtsursalaire + '\'' +
				", primeAnciennete=" + primeAnciennete +
				", moisdepresence=" + moisdepresence +
				", tempspresence=" + tempspresence +
				", mtprimeAnciennete='" + mtprimeAnciennete + '\'' +
				", indemniteLogement=" + indemniteLogement +
				", mtindemniteLogement='" + mtindemniteLogement + '\'' +
				", brutImposable=" + brutImposable +
				", mtbrutImposable='" + mtbrutImposable + '\'' +
				", autrePrelevmentSociale=" + autrePrelevmentSociale +
				", mtautrePrelevmentSociale='" + mtautrePrelevmentSociale + '\'' +
				", autrePrelevment=" + autrePrelevment +
				", mtautrePrelevment='" + mtautrePrelevment + '\'' +
				", autrePrelevmentMutuelle=" + autrePrelevmentMutuelle +
				", mtautrePrelevmentMutuelle='" + mtautrePrelevmentMutuelle + '\'' +
				", regularisation=" + regularisation +
				", mtregularisation='" + mtregularisation + '\'' +
				", brutNonImposable=" + brutNonImposable +
				", mtbrutNonImposable='" + mtbrutNonImposable + '\'' +
				", its=" + its +
				", mtits='" + mtits + '\'' +
				", CMU=" + CMU +
				", mtCMU='" + mtCMU + '\'' +
				", CMUSalarial=" + CMUSalarial +
				", mtCMUSalarial='" + mtCMUSalarial + '\'' +
				", CMUPatronal=" + CMUPatronal +
				", mtCMUPatronal='" + mtCMUPatronal + '\'' +
				", cn=" + cn +
				", mtcn='" + mtcn + '\'' +
				", igr=" + igr +
				", mtigr='" + mtigr + '\'' +
				", totalRetenueFiscale=" + totalRetenueFiscale +
				", mttotalRetenueFiscale='" + mttotalRetenueFiscale + '\'' +
				", cnps=" + cnps +
				", mtcnps='" + mtcnps + '\'' +
				", basecnps=" + basecnps +
				", mtbasecnps='" + mtbasecnps + '\'' +
				", avceAcpte=" + avceAcpte +
				", mtavceAcpte='" + mtavceAcpte + '\'' +
				", pretAlios=" + pretAlios +
				", mtpretAlios='" + mtpretAlios + '\'' +
				", carec=" + carec +
				", mtcarec='" + mtcarec + '\'' +
				", totalRetenue=" + totalRetenue +
				", mttotalRetenue='" + mttotalRetenue + '\'' +
				", RetenueSociale=" + RetenueSociale +
				", mtRetenueSocial='" + mtRetenueSocial + '\'' +
				", totalRetenueSociale=" + totalRetenueSociale +
				", mttotalRetenueSocial='" + mttotalRetenueSocial + '\'' +
				", indemniteRepresentation=" + indemniteRepresentation +
				", mtindemniteRepresentation='" + mtindemniteRepresentation + '\'' +
				", indemniteTransport=" + indemniteTransport +
				", mtindemniteTransport='" + mtindemniteTransport + '\'' +
				", indemniteTransportImp=" + indemniteTransportImp +
				", mtindemniteTransportImp='" + mtindemniteTransportImp + '\'' +
				", indemniteResponsabilte=" + indemniteResponsabilte +
				", mtindemniteResponsabilte='" + mtindemniteResponsabilte + '\'' +
				", netPayer=" + netPayer +
				", mtnetPayer='" + mtnetPayer + '\'' +
				", totalBrut=" + totalBrut +
				", mttotalBrut='" + mttotalBrut + '\'' +
				", is=" + is +
				", mtis='" + mtis + '\'' +
				", ta=" + ta +
				", mtta='" + mtta + '\'' +
				", fpc=" + fpc +
				", mtfpc='" + mtfpc + '\'' +
				", fpcregul=" + fpcregul +
				", mtfpcregul='" + mtfpcregul + '\'' +
				", prestationFamiliale=" + prestationFamiliale +
				", mtprestationFamiliale='" + mtprestationFamiliale + '\'' +
				", accidentTravail=" + accidentTravail +
				", mtaccidentTravail='" + mtaccidentTravail + '\'' +
				", retraite=" + retraite +
				", mtretraite='" + mtretraite + '\'' +
				", totalPatronal=" + totalPatronal +
				", mttotalPatronal='" + mttotalPatronal + '\'' +
				", totalMasseSalariale=" + totalMasseSalariale +
				", mttotalMasseSalariale='" + mttotalMasseSalariale + '\'' +
				", retenueSociiale=" + retenueSociiale +
				", mtretenueSociiale='" + mtretenueSociiale + '\'' +
				", bullpaie=" + bullpaie +
				", jourTravail=" + jourTravail +
				", temptravail=" + temptravail +
				", listIndemniteBrut=" + listIndemniteBrut +
				", listIndemniteNonImp=" + listIndemniteNonImp +
				", listRubrique=" + listRubrique +
				", listIndemBrutNonImp=" + listIndemBrutNonImp +
				", listRetenueMutuellt=" + listRetenueMutuellt +
				", listRetenueSociale=" + listRetenueSociale +
				", listGainsNet=" + listGainsNet +
				", contratPersonnel=" + contratPersonnel +
				", periodePaie=" + periodePaie +
				'}';
	}

	public int countnbreJrdu(Date dateRetourDernierConge, Date dateDepartConge, ContratPersonnel Contratp) {
		// TODO Auto-generated method stub
		
		int tps=ProvisionConge.calculerTempsPresence(dateRetourDernierConge,dateDepartConge);
		int rf=(int) (tps*2.2*1.25);
		 Double[]ancienete= calculAnciennete(Contratp.getCategorie().getSalaireDeBase(),Contratp.getPersonnel().getDateArrivee());
		 	double newancienete;
	    	if(Contratp.getAncienneteInitial()!=0) {
	    		 newancienete=ancienete[1] +Contratp.getAncienneteInitial();
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
		 
		 Double age= DifferenceDate.valAge(new Date(), Contratp.getPersonnel().getDateNaissance());
		 if(Contratp.getPersonnel().getSexe().equals("Feminin") && age<=21 && Contratp.getPersonnel().getNombrEnfant()>0){
			 jourSuppDam=2*Contratp.getPersonnel().getNombrEnfant();
		 }
		 if(Contratp.getPersonnel().getSexe().equals("Feminin") && age>21 && Contratp.getPersonnel().getNombrEnfant()>0){
			 
			 if(Contratp.getPersonnel().getNombrEnfant()>=4)jourSuppDam=2*1;
			 if(Contratp.getPersonnel().getNombrEnfant()>=5)jourSuppDam=2*2;
			 if(Contratp.getPersonnel().getNombrEnfant()>=6)jourSuppDam=2*3;
			 if(Contratp.getPersonnel().getNombrEnfant()>=7)jourSuppDam=2*4;
			 if(Contratp.getPersonnel().getNombrEnfant()>=8)jourSuppDam=2*5;
			 if(Contratp.getPersonnel().getNombrEnfant()>=9)jourSuppDam=2*6;
		 }
		 
		 if(Contratp.getPersonnel().getSituationMedaille()==1 ){
			 jourSuppMed=1;
		 } 
		 int rfp=(int) (jourSuppAnc+jourSuppDam+jourSuppMed);
		return (int) rfp+rf;
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
		tab[2] = (double) (partieApresVirg);
		
	
		
		return tab;
	}


}
