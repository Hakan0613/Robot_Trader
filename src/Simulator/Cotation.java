package Simulator;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cotation {
	private String noAction;
	private LocalDate date;
	private LocalTime heure;
	private Float coteDebut;
	private Float coteMax;
	private Float coteMin;
	private Float coteFin;
	private int nbTransaction;

	
	public Cotation(String noAction, LocalDate date, LocalTime heure, Float coteDebut, Float coteMax, Float coteMin, Float coteFin, int nbTransaction) {
		super();
		this.noAction = noAction;
		this.date = date;
		this.heure = heure;
		this.coteDebut = coteDebut;
		this.coteMax = coteMax;
		this.coteMin = coteMin;
		this.coteFin = coteFin;
		this.nbTransaction = nbTransaction;
	}
	
	public String getNoAction() {
		return noAction;
	}
	public void setNoAction(String noAction) {
		this.noAction = noAction;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getHeure() {
		return heure;
	}
	public void setHeure(LocalTime heureP) {
		this.heure = heureP;
	}
	public Float getCoteDebut() {
		return coteDebut;
	}
	public void setCoteDebut(Float coteDebut) {
		this.coteDebut = coteDebut;
	}
	public Float getCoteMax() {
		return coteMax;
	}
	public void setCoteMax(Float coteMax) {
		this.coteMax = coteMax;
	}
	public Float getCoteMin() {
		return coteMin;
	}
	public void setCoteMin(Float coteMin) {
		this.coteMin = coteMin;
	}
	public Float getCoteFin() {
		return coteFin;
	}
	public void setCoteFin(Float coteFin) {
		this.coteFin = coteFin;
	}
	public int getNbTransaction() {
		return nbTransaction;
	}
	public void setNbTransaction(int nbTransaction) {
		this.nbTransaction = nbTransaction;
	}

	@Override
	public String toString() {
		return "Cotation [noAction=" + noAction + ", date=" + date + ", heure=" + heure + ", coteDebut=" + coteDebut
				+ ", coteMax=" + coteMax + ", coteMin=" + coteMin + ", coteFin=" + coteFin + ", nbTransaction="
				+ nbTransaction + "]";
	}

}
