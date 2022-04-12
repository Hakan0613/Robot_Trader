package Simulator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import DataSet.MetierDB;
import DataSet.UpLoadDataSet;
import Simulator.AlgoPrediction.algo;

public class Trader {
	private Liste<Cotation> cour;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private  MetierDB metierDB;

	/**
	 * Construteur qui va charger (dans une arraylist) le fichier qui contient les cotations d'une journée.
	 * @param nomFichier
	 * @param separateur
	 */
	public Trader(String nomFichier, char separateur) {
		super();
		cour = new Liste<>();
		System.out.println("Chargement des donnée en cour...");

		if(this.chargerCotations(nomFichier, separateur)) {
			System.out.println("Chargement des données réussi.");
			this.dateDebut = this.cour.get(0).getDate();
			this.dateFin = this.dateDebut;
		}
		else
			System.out.println("Une erreur est survenue dans le chargement du fichier -"+nomFichier+"- fournis.");

	}

	public Trader(LocalDate dateDebutP, LocalDate dateFinP, String DBName) {
		super();
		this.dateDebut = dateDebutP;
		this.dateFin = dateFinP;
		this.cour = new Liste<Cotation>();
		metierDB = new MetierDB(DBName);
		UpLoadDataSet loader = new UpLoadDataSet(metierDB);
		if(dateDebutP.isBefore(dateFinP))
			while(dateDebutP.isBefore(dateFinP) || dateDebutP.isEqual(dateFinP)){
				System.out.println("Chargement des données du " +dateDebutP);
				try{
					this.cour.addAll(loader.getHistorique(dateDebutP));
				}
				catch (NullPointerException n){
					System.err.println("Erreur dans le chargement des données du " +dateDebutP);
				}
				dateDebutP = dateDebutP.plusDays(1);
			}
		else
			System.out.println("La date de debut doit etre antérieur à la date de fin");
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
				while(currentLineTime.isBefore((LocalTime) data.getHeure())) {
					if(lastData!=null) {
						lastData.setHeure(currentLineTime);
						this.cour.add(lastData);
					}
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
			System.err.println();
			return false;
		}
	}

	/**
	 * Méthode qui simule le robot trader. Il parcoure le cour actuel tout les 5 minutes et prends une décision d'achat ou de vente d'un ou plusieur actions.
	 * @param algoPredict
	 */
	public void simulation(algo algoPredict, Float capital, int intervalle) {
		System.out.println("Lancement de la simulation avec un capital de départ de " + capital);
		LocalTime heureActuel;
		Portefeuille portefeuilleAction = new Portefeuille(capital);
		AlgoPrediction algoPrediction = new AlgoPrediction(this.metierDB);
		Liste<Cotation> coteHoraire;
		LocalDate dateEncours = this.dateDebut;
		Pause pause = new Pause();;
		pause.start();
		while (dateEncours.isBefore(this.dateFin) || dateEncours.isEqual(this.dateFin)) {
			System.out.println("Simulation de la journée " + dateEncours.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			heureActuel = LocalTime.of(9, 0);
			//On itere tous les 5 minutes jusqu'à fermeture du marché
			while (heureActuel.isBefore(LocalTime.of(17, 36))) {
				//Gestion des pause (affiche évolution portefeuille)
				pause.setPortefeuilleAction(portefeuilleAction);
				try {
					if (pause.flag) {
						while (pause.flag) {}
						pause = new Pause();
						pause.start();
					}
					TimeUnit.SECONDS.sleep(intervalle);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\tIl est " + heureActuel.format(DateTimeFormatter.ofPattern("HH:mm")) + ".");
				coteHoraire = this.cour.getSubList(LocalDateTime.of(dateEncours,heureActuel));
				//Achat
				int[] quantité = algoPrediction.prediction(coteHoraire, algoPredict);
				for (int i = 0; i < quantité.length; i++)
					if (quantité[i] > 0) {
						if (portefeuilleAction.autorisationAchat((Float) (coteHoraire.get(i).getCoteMin() * quantité[i]))) {
							System.out.println("\t\tAchat de " + quantité[i] + " action " + coteHoraire.get(i));
							portefeuilleAction.achat(coteHoraire.get(i), quantité[i]);
						}
					}
				System.out.println("\t\tCaptile Actuel : " + portefeuilleAction.getCapital());

				//Vente

				Liste<Ordre> actionDétenu = new Liste<Ordre>(portefeuilleAction.getActionDétenu());
				quantité = algoPrediction.prediction(actionDétenu, algoPredict);

				for (int i = 0; i < quantité.length; i++)
					if (quantité[i] > 0) {
						System.out.println("\t\tVente de " + quantité[i] + " action " + actionDétenu.get(i));
						portefeuilleAction.vente(coteHoraire.get(actionDétenu.get(i).getNoAction()), quantité[i]);
					}
//				System.out.println("\t\tCapitale Actuel : " + portefeuilleAction.getCapital());
				System.out.println(portefeuilleAction.getActionDétenu().getAffichage());
				heureActuel = heureActuel.plusMinutes(5);
			}
			dateEncours = dateEncours.plusDays(1);
		}
		pause.interrupt();
		System.out.println("*******************************");
		System.out.println(portefeuilleAction.getActionDétenu().getAffichage());
		System.out.println("\nCapital en fin de simulation "+portefeuilleAction.getCapital());
		System.out.println("\nCapital en action en fin de simulation estimer au cour actuelle "+portefeuilleAction.getCapitalAction(this.cour.getSubList(LocalDateTime.of(dateFin, LocalTime.of(17,30)))));
		System.out.println(portefeuilleAction.getEvolutionPortfeuille());
	}

	public static void main(String[] args) {
		Trader test = new Trader("C:\\Users\\Hakan\\Desktop\\Dossier dev\\Java\\Projet Robot Trader\\Intraday_srd_05122019\\2019\\01-2019\\SRD_02012019.txt", ';');
		test.simulation(algo.Hasard, 10000f, 0);
	}
}

