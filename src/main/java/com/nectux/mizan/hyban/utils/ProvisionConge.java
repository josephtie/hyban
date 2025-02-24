package com.nectux.mizan.hyban.utils;

import java.util.Calendar;
import java.util.Date;

import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;

public class ProvisionConge {
	
	private String B;
	
	private Float C;
	
	private int D;
	
	private Double E;
	
	private Double F;
	
	private int G;
	
	private Double H;
	
	private Double I;
	
	private Double J;
	
	private Double K;
	
	private Double L;
	
	private Double M;
	
	private Double N;
	
	private Double O;
	
	private Double P;
	
	private Double Q;
	
	private Double R;
	
	private Double ITS;
	
	private Double CN;
	
	private Double IGR;
	
	private Double CNPS;
	
	private Double totalRetenueFiscale;
	
	private Double allocationCongeNet;
	
	public ProvisionConge(String nomComplet, Float nombrePart, Date retourDernierConge, Date departEnConge, Double salaireMoyenMensuel, Double indemniteRepresentationMoyenMensuelle,ContratPersonnel contratPersonnel) {
		super();
		this.B = nomComplet;
		this.C = nombrePart;
		this.D = calculerTempsPresence(retourDernierConge, departEnConge);	
		this.E = salaireMoyenMensuel;
		this.F = indemniteRepresentationMoyenMensuelle;
		this.G = (int) Math.rint(D * 2.2 * 1.25);		
		/*new loi*/
		//Jour ouvrable supplement//
		 Double[]ancienete= calculAnciennete(contratPersonnel.getCategorie().getSalaireDeBase(),contratPersonnel.getDateDebut());
		 	double newancienete;
	    	if(contratPersonnel.getAncienneteInitial()!=0) {
	    		 newancienete=ancienete[1] +contratPersonnel.getAncienneteInitial();
	    	}else{
	    		newancienete=ancienete[1];
	    	}
	    	double anc=(int)newancienete + ancienete[2];
	    	
	     int jourSuppAnc=0; int jourSuppDam = 0;int jourSuppMed = 0;
	     
		 if(anc>5 && anc<=10)  jourSuppAnc=1;
		 if(anc>10 && anc<=15) jourSuppAnc=2;
		 if(anc>15 && anc<=20) jourSuppAnc=3;
		 if(anc>20 && anc<=25) jourSuppAnc=5;
		 if(anc>25 && anc<=30) jourSuppAnc=7;
		 if(anc>30) jourSuppAnc=8;
		 
		 Double age=DifferenceDate.valAge(new Date(), contratPersonnel.getPersonnel().getDateNaissance());
		 if(contratPersonnel.getPersonnel().getSexe()=="Feminin" && age<=21 && contratPersonnel.getPersonnel().getNombrEnfant()>0){
			 jourSuppDam=2*contratPersonnel.getPersonnel().getNombrEnfant();
		 }
		 if(contratPersonnel.getPersonnel().getSexe()=="Feminin" && age>21 && contratPersonnel.getPersonnel().getNombrEnfant()>0){
			 
			 if(contratPersonnel.getPersonnel().getNombrEnfant()>=4)jourSuppDam=2*1;
			 if(contratPersonnel.getPersonnel().getNombrEnfant()>=5)jourSuppDam=2*2;
			 if(contratPersonnel.getPersonnel().getNombrEnfant()>=6)jourSuppDam=2*3;
			 if(contratPersonnel.getPersonnel().getNombrEnfant()>=7)jourSuppDam=2*4;
			 if(contratPersonnel.getPersonnel().getNombrEnfant()>=8)jourSuppDam=2*5;
			 if(contratPersonnel.getPersonnel().getNombrEnfant()>=9)jourSuppDam=2*6;
		 }
		 
		 if(contratPersonnel.getPersonnel().getSituationMedaille()==1 ){
			 jourSuppMed=1;
		 } 
		 
		 this.G =G+jourSuppAnc+jourSuppDam+jourSuppMed;
		/*new loi*/
		this.H = Math.rint((E + F) * G / 30);
		this.I = Math.rint((E / 12) * G / 30);
		this.J = Math.rint(I * 1.2 / 100);
		this.K = Math.rint(I * 0.4 / 100);
		this.L = Math.rint(I * 1.2 / 100);
		this.M = Math.rint(J + K + L);
		this.N = Math.rint(calculerPrestationFamiliale());
		this.O = Math.rint(calculerAccidentTravail());
		this.P = Math.rint(calculerRetraite());
		this.Q = Math.rint(N + O + P);
		this.R = Math.rint(M + Q);
		
		this.ITS = Math.rint(I * 1.2 / 100);
		this.CN = Math.rint(calculCN());
		this.IGR = Math.rint(calculIGR());
		this.CNPS = Math.rint(calculCNPS());
		this.totalRetenueFiscale = Math.rint(ITS + CN + IGR+CNPS);
		this.allocationCongeNet = Math.rint(I - totalRetenueFiscale);
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public Float getC() {
		return C;
	}

	public void setC(Float c) {
		C = c;
	}

	public int getD() {
		return D;
	}

	public void setD(int d) {
		D = d;
	}

	public Double getE() {
		return E;
	}

	public void setE(Double e) {
		E = e;
	}

	public Double getF() {
		return F;
	}

	public void setF(Double f) {
		F = f;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}

	public Double getH() {
		return H;
	}

	public void setH(Double h) {
		H = h;
	}

	public Double getI() {
		return I;
	}

	public void setI(Double i) {
		I = i;
	}

	public Double getJ() {
		return J;
	}

	public void setJ(Double j) {
		J = j;
	}

	public Double getK() {
		return K;
	}

	public void setK(Double k) {
		K = k;
	}

	public Double getL() {
		return L;
	}

	public void setL(Double l) {
		L = l;
	}

	public Double getM() {
		return M;
	}

	public void setM(Double m) {
		M = m;
	}

	public Double getN() {
		return N;
	}

	public void setN(Double n) {
		N = n;
	}

	public Double getO() {
		return O;
	}

	public void setO(Double o) {
		O = o;
	}

	public Double getP() {
		return P;
	}

	public void setP(Double p) {
		P = p;
	}

	public Double getQ() {
		return Q;
	}

	public void setQ(Double q) {
		Q = q;
	}

	public Double getR() {
		return R;
	}

	public void setR(Double r) {
		R = r;
	}

	public Double getITS() {
		return ITS;
	}

	public void setITS(Double iTS) {
		ITS = iTS;
	}

	public Double getCN() {
		return CN;
	}

	public void setCN(Double cN) {
		CN = cN;
	}

	public Double getIGR() {
		return IGR;
	}

	public void setIGR(Double iGR) {
		IGR = iGR;
	}

	public Double getTotalRetenueFiscale() {
		return totalRetenueFiscale;
	}

	public Double getCNPS() {
		return CNPS;
	}

	public void setCNPS(Double cNPS) {
		CNPS = cNPS;
	}

	public void setTotalRetenueFiscale(Double totalRetenueFiscale) {
		this.totalRetenueFiscale = totalRetenueFiscale;
	}

	public Double getAllocationCongeNet() {
		return allocationCongeNet;
	}

	public void setAllocationCongeNet(Double allocationCongeNet) {
		this.allocationCongeNet = allocationCongeNet;
	}

	public Double calculerPrestationFamiliale(){
		if(I > 70000)
			return 70000 * 5.75 / 100;
		else if(I > 0)
			return I * 5.75 / 100;
		return 0.0;
	}
	
	public Double calculerAccidentTravail(){
		if(I > 70000)
			return 70000 * 2.0 / 100;
		else if(I > 0)
			return I * 2.0 / 100;
		return 0.0;
	}
	
	public Double calculerRetraite(){
		if(I < 1647315)
			return I * 7.7 / 100;
		return 1647315 * 6.6 / 100;
	}
	
	public static int calculerTempsPresence(Date retourDernierConge, Date departEnConge){
		int annees = 0; 
		int mois = 0; 
		int jours = 0; 
	
		Calendar calendrierNaissance = Calendar.getInstance(); 
		calendrierNaissance.setTimeInMillis(retourDernierConge.getTime()); 
		
		Calendar calendrierMaintenant = Calendar.getInstance(); 
		calendrierMaintenant.setTimeInMillis(departEnConge.getTime()); 
		// Calcul du nombre annees. 
		annees = calendrierMaintenant.get(Calendar.YEAR) - calendrierNaissance.get(Calendar.YEAR); 
		int moisMaintenant = calendrierMaintenant.get(Calendar.MONTH) + 1; 
		int moisNaissance = calendrierNaissance.get(Calendar.MONTH) + 1; 
	
		mois = moisMaintenant - moisNaissance; 
	// Si la difference est negative, reduire annee de 1 et calculer le nombre de mois. 
	if (mois < 0) { 
		    annees--; 
		    mois = 12 - moisNaissance + moisMaintenant; 
		    if (calendrierMaintenant.get(Calendar.DATE) < calendrierNaissance.get(Calendar.DATE)) { 
		        mois--; 
		    } 
		} else if (mois == 0 && calendrierMaintenant.get(Calendar.DATE) < calendrierNaissance.get(Calendar.DATE)) { 
		    annees--; 
		    mois = 11; 
		} 
		
		if (calendrierMaintenant.get(Calendar.DATE) > calendrierNaissance.get(Calendar.DATE)) { 
		    jours = calendrierMaintenant.get(Calendar.DATE) - calendrierNaissance.get(Calendar.DATE); 
		} else if (calendrierMaintenant.get(Calendar.DATE) < calendrierNaissance.get(Calendar.DATE)) { 
		    int aujourdhui = calendrierMaintenant.get(Calendar.DAY_OF_MONTH); 
		    calendrierMaintenant.add(Calendar.MONTH, -1); 
		    jours = calendrierMaintenant.getActualMaximum(Calendar.DAY_OF_MONTH) - calendrierNaissance.get(Calendar.DAY_OF_MONTH) + aujourdhui; 
		} else { 
		    jours = 0; 
		    if (mois == 12) { 
		        annees++; 
		        mois = 0; 
		    } 
		} 
		mois = 12 * annees + mois;
		
		int conge[] = new int[2];
		conge[0] = mois;
		conge[1] = jours;
		if(jours == 0)
			return mois - 1;
		else
			return mois;
	}
	
	public Double calculIGR(){
		C = Math.abs(C);
		Double G = ((I * 80 / 100 - ITS - CN) / C) * 85 / 100;
		if(G > 842167.0)
			return G * C * 60 / 160 - 98633.0 * C;
		else if(G > 389084.0)
			return G * C * 45 / 145 - 44181.0 * C;
		else if(G > 220334.0)
			return G * C * 35 / 135 - 24306.0 * C;
		else if(G > 126584.0)
			return G * C * 25 / 125 - 11250.0 * C;
		else if(G > 81584.0)
			return G * C * 20 / 120 - 7031.0 * C;
		else if(G > 45584.0)
			return G * C * 15 / 115 - 4076.0 * C;
		else if(G > 25000.0)
			return G * C * 10 / 110 - 2273.0 * C;
		else
			return 0.0;
	}
	public Double calculCNPS(){
		if(I < 1647315.0)
			return I * 6.3 / 100;
		else 
			return 1647315 * 6.3 / 100;
	}
	public Double calculCN(){
		if(I > 250000.0)
			return (I - 250000.0) * 8 / 100 + 4700;
		else if(I > 162500.0)
			return (I - 162500.0) * 4 / 100 + 1200;
		else if(I > 62500.0)
			return I * 1.2 / 100 - 750;
		else 
			return 0.0;
	}

	@Override
	public String toString() {
		return "ProvisionConge [B=" + B + ", C=" + C + ", D=" + D + ", E=" + E
				+ ", F=" + F + ", G=" + G + ", H=" + H + ", I=" + I + ", J="
				+ J + ", K=" + K + ", L=" + L + ", M=" + M + ", N=" + N
				+ ", O=" + O + ", P=" + P + ", Q=" + Q + ", R=" + R + ", ITS="
				+ ITS + ", CN=" + CN + ", IGR=" + IGR + ", CNPS=" + CNPS
				+ ", totalRetenueFiscale=" + totalRetenueFiscale
				+ ", allocationCongeNet=" + allocationCongeNet + "]";
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
		tab[2] = (double)(partieApresVirg);
		
	
		
		return tab;
	}
}
