package Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;
import DataSet.UpLoadDataSet;
import Simulator.AlgoPrediction.algo;

public class Trader {
	private ArrayList<Cotation> cour;

	/**
	 * Construteur qui va charger (dans une arraylist) le fichier qui contient les cotations d'une journée.
	 * @param nomFichier
	 * @param separateur
	 */
	public Trader(String nomFichier, char separateur) {
		super();
		cour = new ArrayList<Cotation>();
		System.out.println("Chargement des donnée en cour...");

		if(this.chargerCotations(nomFichier, separateur))
			System.out.println("Chargement des données réussi.");
		else
			System.out.println("Une erreur est survenue dans le chargement du fichier -"+nomFichier+"- fournis.");
	}


	/**
	 * Méthode qui simule le robot trader. Il parcoure le cour actuel tout les 5 minutes et prends une décision d'achat ou de vente d'un ou plusieur actions.
	 * @param algoPredict
	 */
	public void simulation(algo algoPredict, double capital) {
		System.out.println("Lancement de la simulation avec un capital de départ de " + capital);
		LocalTime heureActuel = LocalTime.of(9, 0);
		Portefeuille portefeuilleAction = new Portefeuille(capital);
		AlgoPrediction algoPrediction = new AlgoPrediction();
		//HashMap<String ,Ordre> actionDétenu = new HashMap<String, Ordre>();
		ArrayList<Cotation> coteHeure;
		//On itere tous les 5 minutes jusqu'à fermeture du marché
		while(heureActuel.isBefore(LocalTime.of(17, 36))) {
			System.out.println("\tIl est " + heureActuel.getHour()+"h "+heureActuel.getMinute()+".");
			coteHeure = this.getCoteHoraire(heureActuel);
			//Achat
			for (Iterator<Cotation> iterator = coteHeure.iterator(); iterator.hasNext();) {
				Cotation cote = (Cotation) iterator.next();
				int quantité = algoPrediction.prediction(cote, algoPredict);
				if(quantité>0){
					if (portefeuilleAction.autorisationAchat((double) (cote.getCoteDebut()*quantité))) {
						System.out.println("\t\tAchat de "+ quantité +" action "+ cote);
						portefeuilleAction.achat(cote, 10);
						System.out.println("\t\tCaptile Actuel : " + portefeuilleAction.getCapital());
						portefeuilleAction.afficheActionDetenue();
					}
				}
			}
			//Vente
			Iterator<Ordre> actionDétenu = portefeuilleAction.getActionDétenu().values().iterator();
			while (actionDétenu.hasNext()){
				Ordre temp = actionDétenu.next();
				int quantité = algoPrediction.prediction(temp, algoPredict);
				if (quantité>0) {
					System.out.println("\t\tVente de "+quantité+" action " + temp);
					portefeuilleAction.vente(temp.getNoAction(), 10, 10);
					System.out.println("\t\tCapitale Actuel : " + portefeuilleAction.getCapital());
					portefeuilleAction.afficheActionDetenue();
					actionDétenu = portefeuilleAction.getActionDétenu().values().iterator();
				}
			}

			heureActuel=heureActuel.plusMinutes(5);
		}

		portefeuilleAction.afficheActionDetenue();
		System.out.println("*******************************");
		System.out.println("\nCapital en fin de simulation "+portefeuilleAction.getCapital());
		System.out.println("\nCapital en action en fin de simulation estimer au cour actuelle "+portefeuilleAction.getCapitalAction(this.getCoteHoraire(LocalTime.of(17,30))));
	}

	/**
	 * Permet de récuperer les cotations à une heure précise.
	 * @param heure
	 * @return Une arraylist de Cotation.
	 */
	private ArrayList<Cotation> getCoteHoraire(LocalTime heure){
		ArrayList<Cotation> coteHeure = new ArrayList<Cotation>();
		for (Iterator<Cotation> iterator = cour.iterator(); iterator.hasNext();) {
			Cotation cotation = (Cotation) iterator.next();
			if(cotation.getHeure().equals(heure))
				coteHeure.add(cotation);
		}
		return coteHeure;
	}


	/**
	 * Permet de charger les cotations du fichier fournis.
	 * @param nomFichier
	 * @param separateur
	 * @return
	 */
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
				if(lastData!=null)
					while(currentLineTime.isBefore((LocalTime) data.getHeure())) {
						lastData.setHeure(currentLineTime);
						this.cour.add(lastData);
						currentLineTime = currentLineTime.plusMinutes(5);
					}

				this.cour.add(data);
				lastData = data;
				currentLineTime = (lastData.getHeure()).plusMinutes(5);
			}
			myReader.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println();
			return false;    
		}
	}

	/**
	 * Permet d'afficher les cotations.
	 */
	public void afficheCotations() {
		for (Iterator<Cotation> iterator = cour.iterator(); iterator.hasNext();) 
			System.out.println("\t\t\t"+(Cotation) iterator.next());
	}
	
	public static void main(String[] args) {
		Trader alpha = new Trader("\\C:\\Users\\Hakan\\Desktop\\Dossier dev\\Java\\Projet Robot Trader\\SRD_01042019.txt", ';');
		alpha.simulation(algo.Hasard, 10000);

	}
}

