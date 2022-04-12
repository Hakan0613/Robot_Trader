package Simulator;

import DataSet.MetierDB;
import DataSet.UpLoadDataSet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe qui contient les algos de prédiction.
 */
public class AlgoPrediction {
	public enum algo{
		Hasard
	}
	private Liste<Cotation> historiqueCotation;
	private UpLoadDataSet loader;

	public AlgoPrediction(MetierDB metierDB) {
		this.loader = new UpLoadDataSet(metierDB);
	}

	/**
	 * Permet de faire prediction du choix d'achat d'une action.
	 * @param coteActuel
	 * @param choix
	 * @return Quantité à acheté
	 */
	public int[] prediction(Liste<?> coteActuel, algo choix) {
		// TODO Auto-generated constructor stub
		int[] prediction = new int[coteActuel.lenght()];
		switch (choix) {
			case Hasard:
				for(int i=0; i < coteActuel.lenght();i++)
					prediction[i]=this.hasard();
		default:

		}
		return prediction;
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

	private int[] regressionLinéaire(){
		return null;
	}

	private ArrayList<Cotation> getLast7Days(LocalDate date){
		ArrayList<Cotation> liste = new ArrayList<>();
		LocalDate dateDebutP = date.minusDays(7);
		LocalDate dateFinP = date.minusDays(1);
		ArrayList<Cotation> temp = new ArrayList<>();
		while(dateDebutP.isBefore(dateFinP) || dateDebutP.isEqual(dateFinP)){
			System.out.println("Chargement des données du " +dateDebutP);
			temp=loader.getHistorique(dateDebutP);
			if(temp!=null)
				liste.addAll(temp);
			dateDebutP = dateDebutP.plusDays(1);
		}
		return liste;
	}
}
