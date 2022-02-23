package Simulator;

import java.time.LocalDate;
import java.time.LocalTime;

public class Action {
	private String noAction;
	private LocalDate dateAchat;
	private LocalTime heureAchat;
	private Float coteAchat;
	private Float coteVente;
	private int nbAction;
	
	public Action(String noAction, LocalDate dateAchat, LocalTime heureAchat, Float coteAchat, int nbAction) {
		super();
		this.noAction = noAction;
		this.dateAchat = dateAchat;
		this.heureAchat = heureAchat;
		this.coteAchat = coteAchat;
		this.nbAction = nbAction;
	}
	
	

	public Float getCoteVente() {
		return coteVente;
	}

	public void setCoteVente(Float coteVente) {
		this.coteVente = coteVente;
	}



	public String getNoAction() {
		return noAction;
	}

	public void setNbAction(int nbAction) {
		this.nbAction = nbAction;
	}



	public LocalDate getDateAchat() {
		return dateAchat;
	}

	public LocalTime getHeureAchat() {
		return heureAchat;
	}

	public Float getCoteAchat() {
		return coteAchat;
	}

	public int getNbAction() {
		return nbAction;
	}

	@Override
	public String toString() {
		return "Action [noAction=" + noAction + ", dateAchat=" + dateAchat + ", heureAchat=" + heureAchat
				+ ", coteAchat=" + coteAchat + ", nbAction=" + nbAction + "]";
	}
	
}
