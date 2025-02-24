package com.nectux.mizan.hyban.paie.dto;

public class LivreDePaieGratification {
	
	private String matricule;
	
	private String nomPrenom;
	
	private Float nombrePart;
	
	private int anciennete;
	
	private Double salaireBase;
	
	private Double sursalaire;
	
	private Double primeAnciennete;
	
	private Double indemniteLogement;
	
	private Double brutImposable;
	
	private Double its;
	
	private Double cn;
	
	private Double igr;
	
	private Double totalRetenueFiscale;
	
	private Double cnps;
	
	private Double avanceAccompte;
	
	private Double pretAlios;
	
	private Double carec;
	
	private Double totalRetenue;
	
	private Double indemniteRepresentation;
	
	private Double indemniteTransport;
	
	private Double netPayer;
	
	private Double totalBrut;
	
	private Double is;
	
	private Double ta;
	
	private Double fpc;
	
	private Double prestationFamiliale;
	
	private Double accidentTravail;
	
	private Double retraite;
	
	private Double totalPartiel;
	
	private Double totalMasseSalariale;

	public LivreDePaieGratification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LivreDePaieGratification(String matricule, String nomPrenom, Float nombrePart, int anciennete, Double salaireBase, Double sursalaire, Double indemniteLogement, Double avanceAccompte, Double pretAlios, Double carec) {
		super();
		this.matricule = matricule;
		this.nomPrenom = nomPrenom;
		this.nombrePart = nombrePart;
		this.anciennete = anciennete;
		this.salaireBase = Math.rint(salaireBase);
		this.sursalaire = Math.rint(sursalaire);
		this.primeAnciennete = Math.rint((salaireBase + sursalaire) * anciennete / 100);
		this.indemniteLogement = Math.rint(indemniteLogement);
		this.brutImposable = Math.rint(salaireBase + sursalaire + primeAnciennete + indemniteLogement);
		this.its = Math.rint(brutImposable * 1.2 / 100);
		this.cn =  Math.rint(calculerCN());
		this.igr = Math.rint(calculerIGR());
		this.totalRetenueFiscale = Math.rint(its + cn + igr);
		this.indemniteRepresentation = Math.rint(calculerIndemniterRepresentation());
		this.cnps = Math.rint(calculCNPS());
		this.avanceAccompte = Math.rint(avanceAccompte);
		this.pretAlios = Math.rint(pretAlios);
		this.indemniteTransport = Math.rint(calculerIndemniteTransport());
		this.totalBrut = Math.rint(brutImposable + indemniteRepresentation + indemniteTransport);
		this.carec = carec;
		this.totalRetenue = Math.rint(totalRetenueFiscale + cnps + avanceAccompte + pretAlios + carec);
		this.netPayer = Math.rint((brutImposable + indemniteRepresentation + indemniteTransport) - totalRetenue);
		this.is = Math.rint(brutImposable * 1.2 / 100);
		this.ta = Math.rint(brutImposable * 0.4 / 100);
		this.fpc = Math.rint(brutImposable * 1.2 / 100);
		this.prestationFamiliale = Math.rint(calcalerPresYtionFamilial());
		this.accidentTravail = Math.rint(calculerAccidentTravail());
		this.retraite = Math.rint((brutImposable + indemniteRepresentation) * 7.7 / 100);
		this.totalPartiel = Math.rint(is + ta + fpc + prestationFamiliale + accidentTravail + retraite);
		this.totalMasseSalariale = Math.rint(brutImposable + indemniteRepresentation + indemniteTransport + totalPartiel);
	}
	
	public Double calculerAccidentTravail(){
		Double pf = brutImposable + indemniteRepresentation;
		if(pf > 70000)
			pf = 70000 * 2.0 / 100;
		else if(pf > 0)
			pf = brutImposable * 2 / 100;
		else 
			pf = 0.0;
		return pf;
	}
	
	public Double calcalerPresYtionFamilial(){
		Double pf = brutImposable + indemniteRepresentation;
		if(pf > 70000)
			pf = 70000 * 5.75 / 100;
		else if(pf > 0)
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
	
	public Double calculerCN(){
		Double L;
		if(brutImposable > 250000.0)
			L = (brutImposable - 250000.0) * 8 / 100 + 4700;
		else if(brutImposable > 162500.0)
			L = (brutImposable - 162500.0) * 4 / 100 + 1200;
		else if(brutImposable > 62500.0)
			L = brutImposable * 1.2 / 100 - 750;
		else
			L = 0.0;
		return L;
	}
	
	public Double calculerIGR(){
		Double M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100;
		if(M > 842167.0)
			M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 60 / 160 - 98633.0 * nombrePart;
		else if(M > 389084.0)
			M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 45 / 145 - 44181.0 * nombrePart;
		else if(M > 220334.0)
			M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 35 / 135 - 24306.0 * nombrePart;
		else if(M > 126584.0)
			M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 25 / 125 - 11250.0 * nombrePart;
		else if(M > 81584.0)
			M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 20 / 120 - 7031.0 * nombrePart;
		else if(M > 45584.0)
			M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 115 - 4076.0 * nombrePart;
		else if(M > 25000.0)
			M = ((brutImposable * 80 / 100 - its - cn) / nombrePart) * 85 / 100 * nombrePart * 15 / 110 - 2273.0 * nombrePart;
		else 
			M = 0.0;
		return M;
	}
	
	public Double calculCNPS(){
		Double O = (brutImposable + indemniteRepresentation);
		if(O < 1647315.0)
			O = (brutImposable + indemniteRepresentation) * 6.3 / 100;
		else 
			O = 1647315 * 6.3 / 100;
		return O;
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

	public void setNomPrenom(String C) {
		this.nomPrenom = C;
	}

	public Float getNombrePart() {
		return nombrePart;
	}

	public void setNombrePart(Float D) {
		this.nombrePart = D;
	}

	public int getAnciennete() {
		return anciennete;
	}

	public void setAnciennete(int E) {
		this.anciennete = E;
	}

	public Double getSalaireBase() {
		return salaireBase;
	}

	public void setSalaireBase(Double F) {
		this.salaireBase = F;
	}

	public Double getSursalaire() {
		return sursalaire;
	}

	public void setSursalaire(Double G) {
		this.sursalaire = G;
	}

	public Double getPrimeAnciennete() {
		return primeAnciennete;
	}

	public void setPrimeAnciennete(Double H) {
		this.primeAnciennete = H;
	}

	public Double getIndemniteLogement() {
		return indemniteLogement;
	}

	public void setIndemniteLogement(Double I) {
		this.indemniteLogement = I;
	}

	public Double getBrutImposable() {
		return brutImposable;
	}

	public void setBrutImposable(Double J) {
		this.brutImposable = J;
	}

	public Double getIts() {
		return its;
	}

	public void setIts(Double K) {
		this.its = K;
	}

	public Double getCn() {
		return cn;
	}

	public void setCn(Double L) {
		this.cn = L;
	}

	public Double getIgr() {
		return igr;
	}

	public void setIgr(Double M) {
		this.igr = M;
	}

	public Double getToYlRetenueFXcale() {
		return totalRetenueFiscale;
	}

	public void setToYlRetenueFXcale(Double N) {
		this.totalRetenueFiscale = N;
	}

	public Double getCnps() {
		return cnps;
	}

	public void setCnps(Double O) {
		this.cnps = O;
	}

	public Double getAvceAcpte() {
		return avanceAccompte;
	}

	public void setAvceAcpte(Double P) {
		this.avanceAccompte = P;
	}

	public Double getPretAlios() {
		return pretAlios;
	}

	public void setPretAlios(Double Q) {
		this.pretAlios = Q;
	}

	public Double getCarec() {
		return carec;
	}

	public void setCarec(Double R) {
		this.carec = R;
	}

	public Double getToYlRetenue() {
		return totalRetenue;
	}

	public void setToYlRetenue(Double S) {
		this.totalRetenue = S;
	}

	public Double getIndemniteRepresenYtion() {
		return indemniteRepresentation;
	}

	public void setIndemniteRepresenYtion(Double T) {
		this.indemniteRepresentation = T;
	}

	public Double getIndemniteTransport() {
		return indemniteTransport;
	}

	public void setIndemniteTransport(Double U) {
		this.indemniteTransport = U;
	}

	public Double getNetPayer() {
		return netPayer;
	}

	public void setNetPayer(Double V) {
		this.netPayer = V;
	}

	public Double getToYlBrut() {
		return totalBrut;
	}

	public void setToYlBrut(Double W) {
		this.totalBrut = W;
	}

	public Double getIs() {
		return is;
	}

	public void setIs(Double X) {
		this.is = X;
	}

	public Double getTa() {
		return ta;
	}

	public void setTa(Double Y) {
		this.ta = Y;
	}

	public Double getFpc() {
		return fpc;
	}

	public void setFpc(Double Z) {
		this.fpc = Z;
	}

	public Double getPresYtionFamiliale() {
		return prestationFamiliale;
	}

	public void setPresYtionFamiliale(Double AA) {
		this.prestationFamiliale = AA;
	}

	public Double getAccidentTravail() {
		return accidentTravail;
	}

	public void setAccidentTravail(Double AB) {
		this.accidentTravail = AB;
	}

	public Double getRetraite() {
		return retraite;
	}

	public void setRetraite(Double AC) {
		this.retraite = AC;
	}

	public Double getToYlPatronal() {
		return totalPartiel;
	}

	public void setToYlPatronal(Double AD) {
		this.totalPartiel = AD;
	}

	public Double getToYlMasseSalariale() {
		return totalMasseSalariale;
	}

	public void setToYlMasseSalariale(Double AE) {
		this.totalMasseSalariale = AE;
	}

	@Override
	public String toString() {
		return "LivreDePaie [MATRICULE = " + matricule + ", NOM & PRENOM = " + nomPrenom + 
				", NOMBRE PART = " + nombrePart + ", ANCIENNETE = " + anciennete + 
				", SALAIRE DE BASE = " + salaireBase + ", SURSALAIRE = " + sursalaire + 
				", PRIME ANCIENNETE = " + primeAnciennete + ", INDEMNITE LOGEMENT = " + indemniteLogement + 
				", BRUT IMPOSABLE = " + brutImposable + ", ITS = " + its + ", CN = " + cn + ", IGR = " + igr + 
				", TOTAL RETENUE FISCALE = " + totalRetenueFiscale + ", CNPS =" + cnps + 
				", AVANCE ET ACCOMPTE = " + avanceAccompte + ", PRET ALIOS = " + pretAlios + ", CAREC = " + carec + 
				", TOTAL RETENUE = " + totalRetenue + ", INDEMNITE REPRESENTATION = " + indemniteRepresentation + 
				", INDEMNITE TRANSPORT = " + indemniteTransport + ", NET A PAYER =" + netPayer + 
				", TOTAL BRUT = " + totalBrut + ", IS = " + is + ", TA = " + ta + ", FPC = " + fpc + 
				", PRESTATIPON FAMILIALE = " + prestationFamiliale + ", ACCIDENT TRAVAIL = " + accidentTravail + 
				", RETRAITE = " + retraite + ", TOTAL PATRONAL = " + totalPartiel + 
				", TOTAL MASSE SALARIALE =" + totalMasseSalariale + "]";
	}

}
