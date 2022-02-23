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
			laFenetre.ajoutElement(element.TEXTE, "Aucun BD avec le nom par défaut '" + dbDefaultName+"' n'a était détecter.");
			laFenetre.ajoutElement(element.BOUTON, "En crée un nouveau");
			laFenetre.ajoutElement(element.BOUTON, "Changeant le nom par défault");
		}
	}
	
	public void smNewBD() {
		fenetre.ajoutElement(element.TEXTE, "Veuillez nommer la BD :");
		fenetre.ajoutElement(element.SAISIE, "RobotTraderDataSet");
		fenetre.ajoutElement(element.BOUTON, "Valider BD crée");
	}
	
	public void smBD() {
		
	}
	
	public void smUploadData() {
		
	}
	
	public void smVoirData() {
			
		}
	
	public void smChangeDbName() {
		
	}

	
	
}
