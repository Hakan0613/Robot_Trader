package Simulator;

import java.util.HashMap;
import java.util.Map.Entry;

public class Portefeuille {
	private Double capital;
	private HashMap<String,Action> actionDétenu;
	private HashMap<String,Action> actionVendu;
	
	public Portefeuille(Double capital) {
		super();
		this.capital = capital;
		this.actionDétenu = new HashMap<String, Action>();
		this.actionVendu = new HashMap<String, Action>();
	}
	
	public boolean autorisationAchat(Double somme) {
		return this.capital>=somme;
	}
	
	public void achat(Cotation actionAchete, int quantité) {
		this.actionDétenu.put(actionAchete.getNoAction(), new Action(actionAchete.getNoAction(), actionAchete.getDate(), actionAchete.getHeure(), actionAchete.getCoteDebut(), quantité));
		capital -= actionAchete.getCoteDebut()*quantité;
	}
	
	public boolean vente(String noAction, int quantité, float prixVente) {
		if(this.actionDétenu.get(noAction).getNbAction()<quantité)
			return false;
		else {
			this.actionVendu.put(noAction, this.actionDétenu.get(noAction));
			this.actionVendu.get(noAction).setNbAction(quantité);
			this.actionVendu.get(noAction).setCoteVente(prixVente);;
			capital += prixVente*quantité;
			if(this.actionDétenu.get(noAction).getNbAction()>quantité) 
				this.actionDétenu.get(noAction).setNbAction(this.actionDétenu.get(noAction).getNbAction()-quantité);
			else
				this.actionDétenu.remove(noAction);
			return true;
		}
	}

	public void afficheActionDetenue() {
		for (Entry<String, Action> entry : actionDétenu.entrySet()) {
			System.out.println(entry.getValue());
		}
	}
	
	public Double getCapital() {
		return capital;
	}

	public HashMap<String, Action> getActionDétenu() {
		return actionDétenu;
	}

	public HashMap<String, Action> getActionVendu() {
		return actionVendu;
	}
	
	
	
	
}
