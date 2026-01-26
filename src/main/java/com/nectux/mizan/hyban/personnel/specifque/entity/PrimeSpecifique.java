package com.nectux.mizan.hyban.personnel.specifque.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nectux.mizan.hyban.parametrages.entity.Auditable;
import com.nectux.mizan.hyban.parametrages.entity.PeriodePaie;
import com.nectux.mizan.hyban.parametrages.entity.Rubrique;
import com.nectux.mizan.hyban.personnel.entity.ContratPersonnel;
import com.nectux.mizan.hyban.utils.CustomDateDeserializer;
import com.nectux.mizan.hyban.utils.Utils;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component("PrimeSpecifique")
@Scope("prototype")
@Table(name="PrimeSpecifique")

public class PrimeSpecifique extends Auditable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	private Double montant;





	@ManyToOne
	@JoinColumn(nullable=false)
	private RubriqueSpecifique prime;

	@ManyToOne
	@JoinColumn(nullable=false)
	private SpecialContract specialContract;

	@JsonSerialize(using = CustomDateDeserializer.class)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private java.util.Date dateSaisie;

	@ManyToOne
	@JoinColumn(nullable=false)
	private PeriodePaie periodePaie;

	@Transient
	private String mtmontant;

	@Transient
	private String dSaisie;



	public PrimeSpecifique() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}


	
	public PeriodePaie getPeriode() {
		return periodePaie;
	}

	public void setPeriode(PeriodePaie periodePaie) {
		this.periodePaie = periodePaie;
	}






	public java.util.Date getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(java.util.Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

	public String getMtmontant() {
		return mtmontant=Utils.formattingAmount(montant);
	}

	public void setMtmontant(String mtmontant) {
		this.mtmontant = mtmontant;
	}

	public String getdSaisie() {
		return dSaisie=Utils.dateToString(dateSaisie, "dd/MM/yyyy");
	}

	public void setdSaisie(String dSaisie) {
		this.dSaisie = dSaisie;
	}

    public RubriqueSpecifique getPrime() {
        return prime;
    }

    public void setPrime(RubriqueSpecifique prime) {
        this.prime = prime;
    }

    public SpecialContract getSpecialContract() {
        return specialContract;
    }

    public void setSpecialContract(SpecialContract specialContract) {
        this.specialContract = specialContract;
    }

    public PeriodePaie getPeriodePaie() {
        return periodePaie;
    }

    public void setPeriodePaie(PeriodePaie periodePaie) {
        this.periodePaie = periodePaie;
    }


    @Override
    public String toString() {
        return "PrimeSpecifique{" +
                "id=" + id +
                ", montant=" + montant +
                ", prime=" + prime +
                ", specialContract=" + specialContract +
                ", dateSaisie=" + dateSaisie +
                ", periodePaie=" + periodePaie +
                ", mtmontant='" + mtmontant + '\'' +
                ", dSaisie='" + dSaisie + '\'' +
                '}';
    }
}
