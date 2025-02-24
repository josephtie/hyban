/**
 * 
 */
package com.nectux.mizan.hyban.paie.dto;

import com.nectux.mizan.hyban.utils.Utils;

/**
 * @author Lag
 *
 */

public class DisaMensuelDTO {

	private String categorie;
	private Integer nbSalarie;
	private Double valtx;
	private String valmt;
	private String numcnps;
	private Double cumulCnps;
	private Double cumulPfAt;
	
	@javax.persistence.Transient
	private String montantcumulCnps;
	
	
	@javax.persistence.Transient
	private String montantcumulPfAt;
	
	@javax.persistence.Transient
	private String mtcumulCnps;
	
	
	@javax.persistence.Transient
	private String mtcumulPfAt;
	
	public DisaMensuelDTO() {

	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Integer getNbSalarie() {
		return nbSalarie;
	}

	public void setNbSalarie(Integer nbSalarie) {
		this.nbSalarie = nbSalarie;
	}

	public String getNumcnps() {
		return numcnps;
	}

	public void setNumcnps(String numcnps) {
		this.numcnps = numcnps;
	}

	public Double getCumulCnps() {
		return cumulCnps;
	}

	public void setCumulCnps(Double cumulCnps) {
		this.cumulCnps = cumulCnps;
	}

	public Double getCumulPfAt() {
		return cumulPfAt;
	}

	public void setCumulPfAt(Double cumulPfAt) {
		this.cumulPfAt = cumulPfAt;
	}

	public Double getValtx() {
		return valtx;
	}

	public void setValtx(Double valtx) {
		this.valtx = valtx;
	}

	public String getValmt() {
		return valmt;
	}

	public void setValmt(String valmt) {
		this.valmt = valmt;
	}

	public String getMtcumulCnps() {
		return mtcumulCnps=Utils.formattingAmount(cumulCnps);
	}

	public void setMtcumulCnps(String mtcumulCnps) {
		this.mtcumulCnps = mtcumulCnps;
	}

	public String getMtcumulPfAt() {
		return mtcumulPfAt=Utils.formattingAmount(cumulPfAt);
	}

	public void setMtcumulPfAt(String mtcumulPfAt) {
		this.mtcumulPfAt = mtcumulPfAt;
	}
	
	public String getMontantcumulCnps() {
		return montantcumulCnps=Utils.formattingAmount(cumulCnps);
	}

	public void setMontantcumulCnps(String montantcumulCnps) {
		this.montantcumulCnps = montantcumulCnps;
	}

	public String getMontantcumulPfAt() {
		return montantcumulPfAt=Utils.formattingAmount(cumulPfAt);
	}

	public void setMontantcumulPfAt(String montantcumulPfAt) {
		this.montantcumulPfAt = montantcumulPfAt;
	}

	@Override
	public String toString() {
		return "DisaMensuelDTO [categorie=" + categorie + ", nbSalarie="
				+ nbSalarie + ", valtx=" + valtx + ", valmt=" + valmt
				+ ", numcnps=" + numcnps + ", cumulCnps=" + cumulCnps
				+ ", cumulPfAt=" + cumulPfAt + "]";
	}

	

}
