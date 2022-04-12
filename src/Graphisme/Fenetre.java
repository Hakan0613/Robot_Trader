package Graphisme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.JTextComponent;

public class Fenetre extends JFrame {
	
	private JPanel fenetre;
	private JToolBar barreMenu;
	private HashMap<Integer, Object> theme; //"clair" ou "sombre"
	private JPanel fenetreCentrale;
	private String themeActuel;
	private ActionMenu clicAction;
	public enum element { BOUTON, TEXTE, SAISIE, CHECKBOX, LISTEDEROULANTE}
	
	public Fenetre() {
		theme= new HashMap<Integer, Object>();
		
		//Fenetre debut
		setTitle("Alpha Trading");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/Ressources/alpha.png")));
		//Fenetre Fin
		
		//Conteneur principale debut
		
		fenetre = (JPanel) getContentPane();
		
			//Barre de menu début
		barreMenu = new JToolBar();
		barreMenu.setOpaque(false);
				//Bouton theme début
		JButton boutonTheme = new JButton();
		boutonTheme.setContentAreaFilled(false);
		boutonTheme.setBorderPainted(false);
		boutonTheme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeTheme();
			}
		});
		barreMenu.add(boutonTheme);
		JButton boutonAccueil = new JButton();
		boutonAccueil.setContentAreaFilled(false);
		boutonAccueil.setBorderPainted(false);
		barreMenu.add(boutonAccueil);
		JButton boutonRetour = new JButton();
		boutonRetour.setContentAreaFilled(false);
		boutonRetour.setBorderPainted(false);
		barreMenu.add(boutonRetour);
				//Bouton theme fin
		fenetre.add(barreMenu, BorderLayout.NORTH);
			//Barre de menu Fin
		
			//Conteneur centrale debut
		fenetreCentrale = new JPanel(new GridLayout(0, 1, 0, 0) );
		fenetreCentrale.setOpaque(false);
		
		fenetre.add(fenetreCentrale, BorderLayout.CENTER);
			//Conteneur centrale Fin
		clicAction = new ActionMenu(this);
		//Conteneur principale fin
		
		this.themeDefault();  //Theme par default selon l'heure actuel
	}
	
	public void changeTitle(String menuActuel) {
		setTitle("Alpha Trading - "+ menuActuel);
	}

	/**
	 * Permet l'ajout d'élement dans la fenetre principale.
	 * @param elt
	 * @param texte
	 */
	public void ajoutElement(element elt, String texte) {
		Object monElement;
		switch (elt) {
		case BOUTON:
			monElement = new JButton(texte);
			((JButton) monElement).setBorder(BorderFactory.createLineBorder((Color) this.theme.get(3), 2, true));
			((JButton) monElement).addActionListener(clicAction);
			((JButton) monElement).setSelected(true);
			break;
			
		case TEXTE:
			monElement = new JTextPane();
			((JTextComponent) monElement).setText(texte);
			((JTextComponent) monElement).setEditable(false);;
			((JTextComponent) monElement).setSelectedTextColor((Color) this.theme.get(2));
			((JTextComponent) monElement).setSelectionColor((Color) this.theme.get(1));
			break;
		case SAISIE:
			monElement = new JTextField();
			((JTextComponent) monElement).setText(texte);
			((JTextComponent) monElement).setSelectedTextColor((Color) this.theme.get(2));
			((JTextComponent) monElement).setSelectionColor((Color) this.theme.get(1));
			break;

		default: monElement = null;
			break;
		}
		
		((Component) monElement).setForeground((Color) theme.get(1));
		((Component) monElement).setBackground((Color) this.theme.get(2));
		((JComponent) monElement).setOpaque(false);
		
		((Component) monElement).setFont(new Font("SansSerif", Font.BOLD, 15));
		fenetreCentrale.add((Component) monElement);
	}


	/**
	 * Permet de nouveller la fenetre centrale (supprimer les élements de la fenetre centrale)
	 */
	public void newPanneauC() {
		fenetreCentrale.removeAll();
	}


	/**
	 * Permet de rafraichir la fenetre avec le theme courant (modifié).
	 */
	private void appliqueTheme() {
		fenetre.setBackground((Color) theme.get(2));
		for (int i = 0; i < barreMenu.getComponentCount(); i++) {
			System.out.println("Composant "+i+" get "+ (i+4));
			((JButton)barreMenu.getComponent(i)).setIcon((ImageIcon)this.theme.get(i+4));
		}
		for (int i = 0; i < fenetreCentrale.getComponentCount(); i++) {
			this.fenetreCentrale.getComponent(i).setForeground((Color) this.theme.get(1));  //Couleur texte
		}
	}

	/**
	 * Applique l'opposer du theme courant.
	 */
	private void changeTheme() {
		if(this.themeActuel=="clair")
			switchTheme("sombre");
		else
			switchTheme("clair");
	}


	/**
	 * Applique le theme passer en paramètre.
	 * @param themeChoix
	 */
	private void switchTheme(String themeChoix)
	{
		this.themeActuel = themeChoix;
		if(themeChoix.contentEquals("clair")) {
			this.theme.put(1, Color.DARK_GRAY); //Couleur primaire
			this.theme.put(2, Color.WHITE); //Couleur secondaire
			this.theme.put(3, Color.GRAY); // couleur décor
			this.theme.put(4, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/Ressources/sombre.png"))));  //icon theme
			this.theme.put(5, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/Ressources/accueilsombre.png"))));  //accueil theme
			this.theme.put(6, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/Ressources/retoursombre.png"))));  //retour theme
		}
		else {
			this.theme.put(1, Color.WHITE); //Couleur primaire
			this.theme.put(2, Color.DARK_GRAY); //Couleur secondaire
			this.theme.put(3, Color.GRAY); // couleur décor
			this.theme.put(4, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/Ressources/clair.png"))));  //icon theme
			this.theme.put(5, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/Ressources/acceuilclair.png"))));  //accueil theme
			this.theme.put(6, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/Ressources/retourclair.png"))));  //retour theme
		}
		this.appliqueTheme();
	}

	/**
	 * Recupérer le theme par défault en fonction de l'heure actuel et change le theme courant.
	 */
	private void themeDefault()
	{
		if(isBetween(LocalTime.now(), LocalTime.of(8, 0), LocalTime.of(18, 0))){
			switchTheme("clair");
		}
		else
		{
			switchTheme("sombre");
		}
	}

	/**
	 * Méthode annexe qui permet de vérfier qu'une heure est compris entre de dates.
	 * @param candidate
	 * @param start
	 * @param end
	 * @return Boolean
	 */
	//Verifie si une date est comprise entre deux date
	private static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
		  return !candidate.isBefore(start) && !candidate.isAfter(end);  // Inclusive.
	}	

}
	

