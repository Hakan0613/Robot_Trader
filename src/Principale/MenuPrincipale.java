package Principale;

import DataSet.GenerateDB;
import DataSet.MetierDB;
import DataSet.UpLoadDataSet;
import Graphisme.Fenetre;
import Graphisme.Fenetre.element;
import Simulator.AlgoPrediction;
import Simulator.Trader;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuPrincipale {
	public void menuPrincipale(Fenetre fenetre) {
		fenetre.ajoutElement(element.TEXTE, "Bienvenu sur BetaTrader.                               ");
		fenetre.ajoutElement(element.TEXTE, "BetaTrader vous permet de simuler un marché financier. ");
		fenetre.ajoutElement(element.BOUTON, "Menu DataSet");
		fenetre.ajoutElement(element.BOUTON, "Menu simulation");
	}
	
	public void menuPrincipaleConsole(){
		Scanner clavier = new Scanner(System.in);
		String saisi = "";
		Boolean[] flag = new Boolean[10]; //Permet de switcher les menu
		flag[0]=true;
		for (int i = 1; i < flag.length; i++) {
			flag[i]=false;
		}
		while(flag[0]){
			String dbName = "RobotTraderDataSet";
			System.out.println("Menu principale ;\n\t-Tapez 'Trader' (ou 'T') pour ouvrir le Robot Trader\n\t-Tapez 'DataSet' (ou 'D') pour ouvrir le menu de gestion de la base de données" +
					"\n\t-Tapez 'Quitter' (ou 'Q') pour quitter le programme");
			saisi = clavier.nextLine();
			
			if(saisi.contains("Trader") || (saisi.contains("T")&& saisi.length()==1))
				flag[1]=true;
			while(flag[1]){
				System.out.println("Menu Trader :\n\t-Pour simuler le trader à partir d'une base de données taper 'BD'\n\t-Pour faire la simulationà partir d'un fichier taper 'fichier' (ou 'F')\n\t-Tapez 'Retour' (ou 'R') pour retourner au menu principale");
				Trader trader = null;
				saisi = clavier.nextLine();
				if(saisi.contains("BD") && saisi.length()==2)
					flag[6]=true;
				while (flag[6]){
					do {
						System.out.println("Veuillez saisir la date de début au format dd/mm/yyyy : ");
						saisi= clavier.nextLine();
					} while(saisi.charAt(2)!='/' || saisi.charAt(5)!='/' || saisi.length()!=10);
					System.out.println(saisi.substring(6,10));
					System.out.println(saisi.substring(3,5));
					System.out.println(saisi.substring(0,2));
					LocalDate dateDebut = LocalDate.of(new Integer(saisi.substring(6,10)),new Integer(saisi.substring(3,5)),new Integer(saisi.substring(0,2)));
					do {
						System.out.println("Veuillez saisir la date de fin au format dd/mm/yyyy : ");
						saisi= clavier.nextLine();
					} while(saisi.charAt(2)!='/' || saisi.charAt(5)!='/' || saisi.length()!=10);
					LocalDate dateFin= LocalDate.of(new Integer(saisi.substring(6,10)),new Integer(saisi.substring(3,5)),new Integer(saisi.substring(0,2)));
					System.out.println("La BD utilisé est "+ dbName+". \n\tSi vous voulez changer le nom de la BD utilisé tapper le nom que vous voulez lui donner\n\tTaper 'OK' sinon");
					saisi = clavier.nextLine();
					if (!saisi.equalsIgnoreCase("ok"))
						dbName = saisi;
					trader = new Trader(dateDebut, dateFin, dbName);
					flag[6] = false;
				}
				
				if(saisi.contains("fichier") || (saisi.contains("F")&&saisi.length()==1))
					flag[7]=true;
				while(flag[7]){
					System.out.println("\tVeuillez saisir le nom du fichier à utilise");
					saisi= clavier.nextLine();
					trader = new Trader(saisi, ';');
					flag[7]=false;
				}
				System.out.println("Saisir la capital de départ du robot :");
				float capital = clavier.nextFloat();

				System.out.println("Saisir le délai (en seconde) entre chaque heure :");
				int délai = clavier.nextInt();

				trader.simulation(AlgoPrediction.algo.Hasard, capital, délai);
				System.out.println("Simulation terminée");
			}
			
			if(saisi.contains("DataSet") || saisi.contains("D")) {
				flag[2] = true;
			}
			while(flag[2]){
				System.out.println("Menu DataSet :");
				MetierDB taskDB = new MetierDB();
				GenerateDB generateDB = new GenerateDB();
				if (generateDB.verifDB(taskDB, dbName)) {
					System.out.println("Une BD avec le nom par défault " + dbName + " a été trouver.\n\t-Pour ajouter un jeu de donnée tapez 'Ajouter' (ou 'A')" );
				}
				else
					System.out.println("Aucune BD du nom par défault "+dbName+" n'a été reconnu.");
				System.out.println("\t-Pour crée une nouvelle BD tapez 'Nouveau' (ou 'N')\n\t-Pour changer le nom par défault " + dbName + " par défault tapez 'Renommer' (ou 'R')\n\t" +
						"-Pour revenir au menu principale tapez 'Principale' (ou 'P') ");
				saisi = clavier.nextLine();

				if((saisi.contains("Ajouter") || (saisi.contains("A")&& saisi.length()==1)) && generateDB.verifDB(taskDB, dbName) )
					flag[3]=true;
				while(flag[3]){
					taskDB = new MetierDB(dbName);
					UpLoadDataSet uploader = new UpLoadDataSet(taskDB);
					System.out.println("\nAjout d'un jeu de donnée\n\tVeuillez saisir le nom du fichier ou du dossier (doit contenir exlusivement les fichier à charger)." +
							"\n\tRemarque : les dossier peuvent être très long à charger !");
					saisi= clavier.nextLine();
					if(uploader.chargerDataSet(saisi, ';')) {
						System.out.println("Chargement terminée !");
						flag[3] = false;
					}
					else
						System.out.println("Une erreur c'est produit dans le chargement des données");
				}

				if(saisi.contains("Nouveau") || (saisi.contains("N")&& saisi.length()==1))
					flag[4]=true;
				while(flag[4]){
					System.out.println("Le nom par défault qui sera donner à la BD est "+ dbName+". \n\tSi vous voulez changer le nom donner à la BD tapper le nom que vous voulez lui donner\n\tTaper 'OK' sinon");
					saisi = clavier.nextLine();
					if (!saisi.equalsIgnoreCase("OK"))
						dbName = saisi;
					new GenerateDB(taskDB, dbName);
					flag[4]=false;
				}


				if(saisi.contains("Renommer") || (saisi.contains("R") && saisi.length()==1))
					flag[5]=true;
				while(flag[5]){
					System.out.println("Saisir le nouveau nom de BD");
					dbName = clavier.nextLine();
					flag[5]=false;
				}

				if((saisi.contains("Principale") || (saisi.contains("P")&& saisi.length()==1)))
					flag[2]=false;
			}
			if(saisi.contains("Quitter") || (saisi.contains("Q")&& saisi.length()==1))
				flag[0]=false;
		}
	}
	public static void main(String[] args) {
		//Pour utiliser le menu en console
		MenuPrincipale menuPrincipale = new MenuPrincipale();
		menuPrincipale.menuPrincipaleConsole();


		//Pour utiliser l'affichage (version non finaliser)
//		Fenetre maFenetre = new Fenetre();
//		maFenetre.setVisible(true);
//		menuPrincipale.menuPrincipale(maFenetre);
	}
}
