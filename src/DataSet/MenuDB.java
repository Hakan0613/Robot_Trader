package DataSet;

import Graphisme.Fenetre;
import Graphisme.Fenetre.element;

public class MenuDB {
	private String dbDefaultName = "RobotTraderDataSet";
	private MetierDB taskerDB;
	private Fenetre fenetre;
	
	public MenuDB(Fenetre laFenetre) {
		fenetre= laFenetre;
		MetierDB taskerDB = new MetierDB();
		if(new GenerateDB().verifDB(taskerDB, dbDefaultName)) {
			laFenetre.ajoutElement(element.TEXTE, "Une BD nommée " + dbDefaultName +" a était trouvé.\nMenu DataSet :");
			laFenetre.ajoutElement(element.BOUTON, "Charger un nouveau jeux de donnée");
			laFenetre.ajoutElement(element.BOUTON, "Voir un DataSet");
			laFenetre.ajoutElement(element.BOUTON, "Crée une nouvelle BD");
		}
		else {
			laFenetre.ajoutElement(element.TEXTE, "Aucune BD avec le nom par défaut '" + dbDefaultName+"' n'a était détecter.");
			laFenetre.ajoutElement(element.BOUTON, "En crée un nouveau");
			laFenetre.ajoutElement(element.BOUTON, "Changer le nom de BD par défault et ralancer le chargement de la BD");
		}
	}

	public void smUploadData() {
		fenetre.ajoutElement(element.TEXTE, "Veuillez saisir le chemin vers le fichier :");
		fenetre.ajoutElement(element.SAISIE, "Chemin");
		fenetre.ajoutElement(element.TEXTE, "Veuillez saisir le séparateur (un seul caratère) utilisé dans le fichier :");
		fenetre.ajoutElement(element.SAISIE, "Séparateur");
		fenetre.ajoutElement(element.BOUTON, "Valider");
	}

	public void smVoirData() {
		fenetre.ajoutElement(element.TEXTE, "Fonction non disponnible.");
	}

	public void smNewBD() {
		fenetre.ajoutElement(element.TEXTE, "Veuillez nommer la BD :");
		fenetre.ajoutElement(element.SAISIE, "RobotTraderDataSet");
		fenetre.ajoutElement(element.BOUTON, "Valider");
	}
	
	public void smChangeDbName() {
		fenetre.ajoutElement(element.TEXTE, "Veuillez saisir le nom de la BD :");
		fenetre.ajoutElement(element.SAISIE, "RobotTraderDataSet");
		fenetre.ajoutElement(element.BOUTON, "Valider");
	}
}
