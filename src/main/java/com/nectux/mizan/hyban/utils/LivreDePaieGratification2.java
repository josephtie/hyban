package com.nectux.mizan.hyban.utils;

public class LivreDePaieGratification2 {
	
	private String A;
	
	private Float B;
	
	private Double C;
	
	private Double D;
	
	private Double E;
	
	private Double F;
	
	private Double G;
	
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
	
	private Double S;
	
	private Double T;
	
	private Double U;
	
	private Double V;
	
	private Double W;
	
	private Double X;
	
	private Double Y;
	
	private String Z;
	
	private String AA;
	
	private int AB;
	
	private String AC;

	public LivreDePaieGratification2(String nomComplet, Float nombrePart, Double gratificationBase, Double indemniteTransport, Double indemniteFinCarriere, String dateEntree, String situationMatrimoniale, int nombreEnfant, String fonction) {
		super();
		this.A = nomComplet;
		this.B = nombrePart;
		this.C = Math.rint(gratificationBase);
		this.D = C;
		this.E = D * 1.2 / 100;
		this.F = Math.rint(calculCN());
		this.G = Math.rint(calculIGR());
		this.H = Math.rint(E + F + G);
		this.I = Math.rint(calculCNPS());
		this.J = Math.rint(H + I);
		this.K = Math.rint(indemniteTransport);
		this.L = Math.rint(D - J + K);
		this.M = Math.rint(D);
		this.N = Math.rint(D * 1.2 / 100);
		this.O = Math.rint(D * 0.4 / 100);
		this.P = Math.rint(D * 1.2 / 100);
		this.Q = Math.rint(N + O + P);
		this.R = Math.rint(calculPrestationFamiliale());
		this.S = Math.rint(calculAccidentTravail());
		this.T = Math.rint(calculRetraite());
		this.U = Math.rint(indemniteFinCarriere);
		this.V = Math.rint(R + S + T + U);
		this.W = Math.rint(Q + V);
		this.X = Math.rint(M + Q + V);
		this.Y = Math.rint(X * 13);
		
		this.Z = dateEntree;
		this.AA = situationMatrimoniale;
		this.AB = nombreEnfant;
		this.AC = fonction;
	}
	
	public Double calculCN(){
		if(D > 250000.0)
			return (D - 250000.0) * 8 / 100 + 4700;
		else if(D > 162500.0)
			return (D - 162500.0) * 4 / 100 + 1200;
		else if(D > 62500.0)
			return D * 1.2 / 100 - 750;
		else 
			return 0.0;
	}
	
	public Double calculIGR(){
		B = Math.abs(B);
		Double G = ((D * 80 / 100 - E - F) / B) * 85 / 100;
		if(G > 842167.0)
			return G * B * 60 / 160 - 98633.0 * B;
		else if(G > 389084.0)
			return G * B * 45 / 145 - 44181.0 * B;
		else if(G > 220334.0)
			return G * B * 35 / 135 - 24306.0 * B;
		else if(G > 126584.0)
			return G * B * 25 / 125 - 11250.0 * B;
		else if(G > 81584.0)
			return G * B * 20 / 120 - 7031.0 * B;
		else if(G > 45584.0)
			return G * B * 15 / 115 - 4076.0 * B;
		else if(G > 25000.0)
			return G * B * 10 / 110 - 2273.0 * B;
		else
			return 0.0;
	}
	
	public Double calculCNPS(){
		if(D < 1647315.0)
			return D * 6.3 / 100;
		else 
			return 1647315 * 6.3 / 100;
	}
	
	public Double calculPrestationFamiliale(){
		if(D > 70000.0)
			return 70000.0 * 5.75 / 100;
		else if(D > 0)
			return D * 5.75 / 100;
		else
			return 0.0;
	}
	
	public Double calculAccidentTravail(){
		if(D > 70000.0)
			return 70000.0 * 2 / 100;
		else if(D > 0)
			return D * 2 / 100;
		else
			return 0.0;
	}
	
	public Double calculRetraite(){
		if(D < 1647315.0)
			return D * 7.7 / 100;
		else
			return 1647315 * 7.7 / 100;
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public Float getB() {
		return B;
	}

	public void setB(Float b) {
		B = b;
	}

	public Double getC() {
		return C;
	}

	public void setC(Double c) {
		C = c;
	}

	public Double getD() {
		return D;
	}

	public void setD(Double d) {
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

	public Double getG() {
		return G;
	}

	public void setG(Double g) {
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

	public Double getS() {
		return S;
	}

	public void setS(Double s) {
		S = s;
	}

	public Double getT() {
		return T;
	}

	public void setT(Double t) {
		T = t;
	}

	public Double getU() {
		return U;
	}

	public void setU(Double u) {
		U = u;
	}

	public Double getV() {
		return V;
	}

	public void setV(Double v) {
		V = v;
	}

	public Double getW() {
		return W;
	}

	public void setW(Double w) {
		W = w;
	}

	public Double getX() {
		return X;
	}

	public void setX(Double x) {
		X = x;
	}

	public Double getY() {
		return Y;
	}

	public void setY(Double y) {
		Y = y;
	}

	public String getZ() {
		return Z;
	}

	public void setZ(String z) {
		Z = z;
	}

	public String getAA() {
		return AA;
	}

	public void setAA(String aA) {
		AA = aA;
	}

	public int getAB() {
		return AB;
	}

	public void setAB(int aB) {
		AB = aB;
	}

	public String getAC() {
		return AC;
	}

	public void setAC(String aC) {
		AC = aC;
	}

	@Override
	public String toString() {
		return "LivreDePaieGratification2 [A=" + A + ", B=" + B + ", C=" + C + ", D=" + D + ", E=" + E + ", F=" + F
				+ ", G=" + G + ", H=" + H + ", I=" + I + ", J=" + J + ", K=" + K + ", L=" + L + ", M=" + M + ", N=" + N
				+ ", O=" + O + ", P=" + P + ", Q=" + Q + ", R=" + R + ", S=" + S + ", T=" + T + ", U=" + U + ", V=" + V
				+ ", W=" + W + ", X=" + X + ", Y=" + Y + ", Z=" + Z + ", AA=" + AA + ", AB=" + AB + ", AC=" + AC + "]";
	}
	

}
