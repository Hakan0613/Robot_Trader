package Simulator;

import java.util.ArrayList;

/**
 * Classe qui contient les algos de prédiction.
 */
public class AlgoPrediction {
	enum algo{
		Hasard
	}
	private ArrayList<Cotation> historiqueCotation;

	public AlgoPrediction() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Permet de faire prediction du choix d'achat d'une action.
	 * @param coteActuel
	 * @param choix
	 * @return Quantité à acheté
	 */
	public int prediction(Object coteActuel, algo choix) {
		// TODO Auto-generated constructor stub
		switch (choix) {
			case Hasard:
				return this.hasard();
		default:
			return 0;
		}
	}

	/**
	 * Algorithme qui détermine une décision aléatoire.
	 * @return Renvoie la quantité 10 si l'aléatoire vaut vrai.
	 */
	private int hasard() {
		if (Math.random()<0.1)
			return 10;
		else
			return 0;
	}
}
