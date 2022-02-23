package Graphisme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DataSet.MenuDB;

public class ActionMenu implements ActionListener
{  	
	private Fenetre fenetre;
	private MenuDB menuDataSet;
	public ActionMenu(Fenetre laFenetre) {
		this.fenetre = laFenetre;
	}
	
	
	//Vas lancer les menu et sou menu et les formulaire
	@Override
	public void actionPerformed(ActionEvent action) {
		this.fenetre.newPanneauC();
		switch (action.getActionCommand()) {
			
			case "Menu simulation":
				
				break;
				
			case "Menu DataSet":
				this.menuDataSet = new MenuDB(this.fenetre);
				break;
			case "Charger un nouveau jeux de donnée":
				this.menuDataSet.smUploadData();
				break;
							
			case "Voir un DataSet":
				this.menuDataSet.smVoirData();;
				break;
			case "Crée une nouvelle BD":
				this.menuDataSet.smNewBD();
				break;
			case "En crée un nouveau":
				this.menuDataSet.smNewBD();
				break;
			case "Changeant le nom par défault":
				this.menuDataSet.smChangeDbName();
				break;
			case "Valider BD crée":
				this.menuDataSet.smNewBD();
				break;
			default:
				break;
		} 
		this.fenetre.repaint();
 	}
}

