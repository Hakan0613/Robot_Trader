package Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;
import DataSet.UpLoadDataSet;
import Simulator.AlgoPrediction.algo;

public class Trader {
	private ArrayList<Cotation> cour;
	
	public Trader(String nomFichier, char separateur) {
		super();
		cour = new ArrayList<Cotation>();
		System.out.println("Chargement des donnée...");
		if(this.chargerCotations(nomFichier, separateur))
			System.out.println("Chargement réussi ");
		else
			System.out.println("Une erreur est survenue");
	}
	
	public void simulation(algo algoPredict) {
		LocalTime heureActuel = LocalTime.of(9, 0);
		Portefeuille portefeuilleAction = new Portefeuille(10000.0);
		AlgoPrediction algoPrediction = new AlgoPrediction();
		Iterator<Action> actionDétenu;
		while(heureActuel.isBefore(LocalTime.of(17, 36))) {
			ArrayList<Cotation> coteHeure = this.getCoteHoraire(heureActuel);
			//Achat
			for (Iterator<Cotation> iterator = coteHeure.iterator(); iterator.hasNext();) {
				Cotation cote = (Cotation) iterator.next();
				if(algoPrediction.prediction(cote, algoPredict)){
					if (portefeuilleAction.autorisationAchat((double) (cote.getCoteDebut()*10))) {
						System.out.println("Achat de l'action "+ cote);
						portefeuilleAction.achat(cote, 10);
						System.out.println("\tCaptile Actuel : " + portefeuilleAction.getCapital());
						System.out.println("Action détenue :\n\t");
//						portefeuilleAction.afficheActionDetenue();
					}
				}
			}
			//Vente
			actionDétenu = portefeuilleAction.getActionDétenu().values().iterator();

			while (actionDétenu.hasNext()) {
				Action action = actionDétenu.next();
				if (algoPrediction.prediction(action, algoPredict)) {
					actionDétenu.remove();
					System.out.println("Vente de l'action " + action);
					portefeuilleAction.vente(action.getNoAction(), 10, 10);
					System.out.println("\tCaptile Actuel : " + portefeuilleAction.getCapital());
					System.out.println("Action détenue :\n\t");
				}
			}
			heureActuel=heureActuel.plusMinutes(5);
		}

		portefeuilleAction.afficheActionDetenue();
		System.out.println(portefeuilleAction.getCapital());
	}
	
	private ArrayList<Cotation> getCoteHoraire(LocalTime heure){
		ArrayList<Cotation> coteHeure = new ArrayList<Cotation>();
		for (Iterator<Cotation> iterator = cour.iterator(); iterator.hasNext();) {
			Cotation cotation = (Cotation) iterator.next();
			if(cotation.getHeure().equals(heure))
				coteHeure.add(cotation);
		}
		return coteHeure;
	}
	private boolean chargerCotations(String nomFichier, char separateur) {
		try {
			Scanner myReader = new Scanner(new File(nomFichier));
			Cotation lastData = null;
			LocalTime currentLineTime = LocalTime.of(9, 0);
			UpLoadDataSet MonSeparateur = new UpLoadDataSet();
			while (myReader.hasNextLine()) {
				String ligne = myReader.nextLine();
				//Verification que le séparateur correspond à celui du fichier
				if(6>ligne.indexOf(separateur) || ligne.indexOf(separateur)>16 || ligne.lastIndexOf(separateur)<20) {
					return false;
				}
				Cotation data = MonSeparateur.separator(ligne, separateur);
				//Cas des donnée manquante
				while(currentLineTime.isBefore((LocalTime) data.getHeure())) {
					lastData.setHeure(currentLineTime);
					this.cour.add(lastData);
					currentLineTime = currentLineTime.plusMinutes(5);
				}
				//System.out.println(data[0] + " " + data[1] + " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5] + " " + data[6] + " " + data[7]);
				this.cour.add(data);
				lastData = data;
				currentLineTime = (lastData.getHeure()).plusMinutes(5);
			}
			myReader.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;    
		}
	}
	
	public void afficheCotations() {
		for (Iterator<Cotation> iterator = cour.iterator(); iterator.hasNext();) 
			System.out.println((Cotation) iterator.next());
		
	}
	
	public static void main(String[] args) {
		Trader alpha = new Trader("\\C:\\Users\\Hakan\\Desktop\\Dossier dev\\Java\\Projet Robot Trader\\SRD_01042019.txt", ';');
		alpha.simulation(algo.Hasard);
	}
}

