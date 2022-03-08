package Main;

import Graphisme.Fenetre;
import Graphisme.Fenetre.element;
import Simulator.Trader;

public class MenuPrincipale {
	public MenuPrincipale(Fenetre fenetre) {
		fenetre.ajoutElement(element.TEXTE, "Bienvenu sur BetaTrader.                               ");
		fenetre.ajoutElement(element.TEXTE, "BetaTrader vous permet de simuler un marché financier. ");
		fenetre.ajoutElement(element.BOUTON, "Menu DataSet");
		fenetre.ajoutElement(element.BOUTON, "Menu simulation");
	}
	
	public static void main(String[] args) {
		Fenetre maFenetre = new Fenetre();
		maFenetre.setVisible(true);
		MenuPrincipale test = new MenuPrincipale(maFenetre);
	}
}
