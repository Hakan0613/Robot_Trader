package Simulator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map.Entry;

public class Portefeuille {
	private Double capital;
	private HashMap<String,Ordre> actionDétenu;
	private HashMap<String,Ordre> actionVendu;
	
	public Portefeuille(Double capital) {
		super();
		this.capital = capital;
		this.actionDétenu = new HashMap<String, Ordre>();
		this.actionVendu = new HashMap<String, Ordre>();
	}

	/**
	 * Vérifie si il y a suffisament de fond pour acheter une action.
	 * @param somme
	 * @return Autorisation
	 */
	public boolean autorisationAchat(double somme) {
		return this.capital>=somme;
	}

	/**
	 *Permet l'ajout de l'action acheté au portefeuille et le capital est décremente du montant d'achat.
	 * @param actionAchete
	 * @param quantité
	 */
	public void achat(Cotation actionAchete, int quantité) {
		this.actionDétenu.put(actionAchete.getNoAction(), new Ordre(actionAchete.getNoAction(), actionAchete.getCoteDebut(), quantité, LocalDateTime.now()));
		capital -= actionAchete.getCoteDebut()*quantité;
	}

	/**
	 * Permet la vente d'une action. L'action vendu retirer des actions détenues et est ajouté dans liste des actions vendues et le capital est augmenter du montant de la vente.
	 * @param noAction
	 * @param quantité
	 * @param prixVente
	 * @return
	 */
	public boolean vente(String noAction, int quantité, float prixVente) {
		//Cas ou on à pas suffisament de quantité
		if(this.actionDétenu.get(noAction).getQuantité()<quantité)
			return false;
		//Cas ou il y a suffisament de quantité
		else {
			this.actionVendu.put(noAction, this.actionDétenu.get(noAction));
			this.actionVendu.get(noAction).setQuantité(quantité);
			this.actionVendu.get(noAction).setPrix(prixVente);;
			capital += prixVente*quantité;
			//Cas ou il y a plus de quantité que ce qui a été vendu
			if(this.actionDétenu.get(noAction).getQuantité()>quantité)
				this.actionDétenu.get(noAction).setQuantité(this.actionDétenu.get(noAction).getQuantité()-quantité);
			//Cas ou il reste plus d'action
			else{
				this.actionDétenu.remove(noAction);
			}

			return true;
		}
	}

	public void afficheActionDetenue() {
		System.out.println("\t\tAction détenue :\n\t\t***");
		for (Entry<String, Ordre> entry : actionDétenu.entrySet()) {
			System.out.println("\t\t\t"+entry.getValue());
		}
		System.out.println("\t\t***");
	}
	
	public Double getCapital() {
		return capital;
	}

	public HashMap<String, Ordre> getActionDétenu() {
		return actionDétenu;
	}

	public HashMap<String, Ordre> getActionVendu() {
		return actionVendu;
	}
	
	
	
	
}
