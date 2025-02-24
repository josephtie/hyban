/**
 * 
 */
package com.nectux.mizan.hyban.paie.dto;

import javax.persistence.Transient;

import com.nectux.mizan.hyban.utils.Utils;

/**
 * @author Lag
 *
 */
public class DisaTrimestrielDTO {

	private String categorie;
	private String categorie1;
	private String numcnps;
	
	private Integer nbSalarie1;
	
	private Double cumulCnps1;
	private Double cumulPfAt1;
	@Transient
	private String mtcumulCnps1;
	@Transient
	private String mtcumulPfAt1;
	

	private Integer nbSalarie2;
	private Double cumulCnps2;
	private Double cumulPfAt2;
	@Transient
	private String mtcumulCnps2;
	@Transient
	private String mtcumulPfAt2;
	
	
	private Integer nbSalarie3;
	private Double cumulCnps3;
	private Double cumulPfAt3;
	@Transient
	private String mtcumulCnps3;
	@Transient
	private String mtcumulPfAt3;
	
	public DisaTrimestrielDTO() {

	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getNumcnps() {
		return numcnps;
	}

	public void setNumcnps(String numcnps) {
		this.numcnps = numcnps;
	}

	public Integer getNbSalarie1() {
		return nbSalarie1;
	}

	public void setNbSalarie1(Integer nbSalarie1) {
		this.nbSalarie1 = nbSalarie1;
	}

	
	public Integer getNbSalarie2() {
		return nbSalarie2;
	}

	public void setNbSalarie2(Integer nbSalarie2) {
		this.nbSalarie2 = nbSalarie2;
	}

	
	public Integer getNbSalarie3() {
		return nbSalarie3;
	}

	public void setNbSalarie3(Integer nbSalarie3) {
		this.nbSalarie3 = nbSalarie3;
	}

	
	public Double getCumulCnps1() {
		return cumulCnps1;
	}

	public void setCumulCnps1(Double cumulCnps1) {
		this.cumulCnps1 = cumulCnps1;
	}

	public Double getCumulPfAt1() {
		return cumulPfAt1;
	}

	public void setCumulPfAt1(Double cumulPfAt1) {
		this.cumulPfAt1 = cumulPfAt1;
	}

	public Double getCumulCnps2() {
		return cumulCnps2;
	}

	public void setCumulCnps2(Double cumulCnps2) {
		this.cumulCnps2 = cumulCnps2;
	}

	public Double getCumulPfAt2() {
		return cumulPfAt2;
	}

	public void setCumulPfAt2(Double cumulPfAt2) {
		this.cumulPfAt2 = cumulPfAt2;
	}

	public Double getCumulCnps3() {
		return cumulCnps3;
	}

	public void setCumulCnps3(Double cumulCnps3) {
		this.cumulCnps3 = cumulCnps3;
	}

	public Double getCumulPfAt3() {
		return cumulPfAt3;
	}

	public void setCumulPfAt3(Double cumulPfAt3) {
		this.cumulPfAt3 = cumulPfAt3;
	}

	public String getCategorie1() {
		return categorie1;
	}

	public void setCategorie1(String categorie1) {
		this.categorie1 = categorie1;
	}

	public String getMtcumulCnps1() {
		return mtcumulCnps1=Utils.formattingAmount(cumulCnps1);
	}

	public void setMtcumulCnps1(String mtcumulCnps1) {
		this.mtcumulCnps1 = mtcumulCnps1;
	}

	public String getMtcumulPfAt1() {
		return mtcumulPfAt1= Utils.formattingAmount(cumulPfAt1);
	}

	public void setMtcumulPfAt1(String mtcumulPfAt1) {
		this.mtcumulPfAt1 = mtcumulPfAt1;
	}

	public String getMtcumulCnps2() {
		return mtcumulCnps2= Utils.formattingAmount(cumulCnps2);
	}

	public void setMtcumulCnps2(String mtcumulCnps2) {
		this.mtcumulCnps2 = mtcumulCnps2;
	}

	public String getMtcumulPfAt2() {
		return mtcumulPfAt2= Utils.formattingAmount(cumulPfAt2);
	}

	public void setMtcumulPfAt2(String mtcumulPfAt2) {
		this.mtcumulPfAt2 = mtcumulPfAt2;
	}

	public String getMtcumulCnps3() {
		return mtcumulCnps3= Utils.formattingAmount(cumulCnps3);
	}

	public void setMtcumulCnps3(String mtcumulCnps3) {
		this.mtcumulCnps3 = mtcumulCnps3;
	}

	public String getMtcumulPfAt3() {
		return mtcumulPfAt3= Utils.formattingAmount(cumulPfAt3);
	}

	public void setMtcumulPfAt3(String mtcumulPfAt3) {
		this.mtcumulPfAt3 = mtcumulPfAt3;
	}

	@Override
	public String toString() {
		return "DisaTrimestrielDTO [categorie=" + categorie + ", numcnps="
				+ numcnps + ", nbSalarie1=" + nbSalarie1 + ", cumulCnps1="
				+ cumulCnps1 + ", cumulPfAt1=" + cumulPfAt1 + ", nbSalarie2="
				+ nbSalarie2 + ", cumulCnps2=" + cumulCnps2 + ", cumulPfAt2="
				+ cumulPfAt2 + ", nbSalarie3=" + nbSalarie3 + ", cumulCnps3="
				+ cumulCnps3 + ", cumulPfAt3=" + cumulPfAt3
				+ ", getCategorie()=" + getCategorie() + ", getNumcnps()="
				+ getNumcnps() + ", getNbSalarie1()=" + getNbSalarie1()
				+ ", getNbSalarie2()=" + getNbSalarie2() + ", getNbSalarie3()="
				+ getNbSalarie3() + ", getCumulCnps1()=" + getCumulCnps1()
				+ ", getCumulPfAt1()=" + getCumulPfAt1() + ", getCumulCnps2()="
				+ getCumulCnps2() + ", getCumulPfAt2()=" + getCumulPfAt2()
				+ ", getCumulCnps3()=" + getCumulCnps3() + ", getCumulPfAt3()="
				+ getCumulPfAt3() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	

}
