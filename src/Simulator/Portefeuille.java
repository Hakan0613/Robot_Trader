package Simulator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Portefeuille{
	private Float capital;
	private Float capitalDepart;
	private Float capitalAction;
	private Liste<Ordre> actionDétenu;
	private Liste<Ordre> actionVendu;
	
	public Portefeuille(Float capital) {
		super();
		this.capital = capital;
		this.capitalDepart = capital;
		this.capitalAction = 0.0f;
		this.actionDétenu = new Liste<Ordre>();
		this.actionVendu = new Liste<Ordre>();
	}

	/**
	 * Vérifie si il y a suffisament de fond pour acheter une action.
	 * @param somme
	 * @return Autorisation
	 */
	public boolean autorisationAchat(Float somme) {
		return this.capital>=somme;
	}

	/**
	 *Permet l'ajout de l'action acheté au portefeuille et le capital est décremente du montant d'achat.
	 * @param actionAchete
	 * @param quantité
	 */
	public void achat(Cotation actionAchete, int quantité) {
		this.actionDétenu.add(new Ordre(actionAchete.getNoAction(), actionAchete.getCoteDebut(), quantité, LocalDateTime.of(actionAchete.getDate(), actionAchete.getHeure())));
		capital -= actionAchete.getCoteDebut()*quantité;
	}


	public boolean vente(Cotation cote, int quantité) {
		//Cas ou on à pas suffisament de quantité
		if(this.actionDétenu.get(cote.getNoAction()).getQuantité()<quantité)
			return false;
		//Cas ou il y a suffisament de quantité
		else {
			//On ajoute l'action vendu vendu (cad ordre) dans la liste des actions vendu
			this.actionVendu.add(new Ordre(cote.getNoAction(), cote.getCoteMin(), quantité, LocalDateTime.of(cote.getDate(), cote.getHeure())));
			//Le capital augmente du montant des la vente
			capital = capital + (cote.getCoteMin() * quantité);
			//Cas ou il y a plus de quantité que ce qui a été vendu
			if(this.actionDétenu.get(cote.getNoAction()).getQuantité()>quantité)
				this.actionDétenu.get(cote.getNoAction()).setQuantité(this.actionDétenu.get(cote.getNoAction()).getQuantité()-quantité);
			//Cas ou il reste plus d'action
			else{
				this.actionDétenu.delete(cote.getNoAction());
			}
			return true;
		}
	}
	
	public Float getCapital() {
		return capital;
	}

	public Liste<Ordre> getActionDétenu() {
		return this.actionDétenu;
	}

	public Liste<Ordre> getActionVendu() {
		return this.actionVendu;
	}

	public Float getCapitalAction(Liste<Cotation> lastCote) {
		Float capitalActionV = 0.0f;
		//Pour chaque action détenue
		for (int i = 0; i < this.actionDétenu.getAll().size() ; i++) {
			capitalActionV = capitalActionV + (actionDétenu.get(i).getQuantité() * lastCote.get(actionDétenu.get(i).getNoAction()).getCoteMin());
		}
		this.capitalAction = capitalActionV;
		return capitalAction;
	}
	
	public String getEvolutionPortfeuille(){
		return "Evolution du capital de "+this.capitalDepart + " à "+this.capital +" soit " + (((this.capital-this.capitalDepart)/this.capitalDepart)*100)+"% \nNombre d'action détenue " + this.actionDétenu.getAll().size();
	}

}
