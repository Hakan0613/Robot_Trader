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
	private String context; //"menuPrincipal", "menuAjout", "menuSimulate", "menuConfiguration", "ajoutDataSet", "ajoutUnique", 
	private JPanel fenetreCentrale;
	private String themeActuel;
	private JButton boutonTheme;
	//private Box verticalBox;
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/src/Ressources/alpha.png")));
		//Fenetre Fin
		
		//Conteneur principale debut
		
		fenetre = (JPanel) getContentPane();
		
			//Barre de menu début
		JToolBar barreMenu = new JToolBar();		
		barreMenu.setOpaque(false);
				//Bouton theme début
		this.boutonTheme = new JButton();
		this.boutonTheme.setContentAreaFilled(false);
		this.boutonTheme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changeTheme();
			}
		});
		barreMenu.add(boutonTheme);
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
	
	//Methode permettant de suprimmer le contenu du panneau centrale
	public void newPanneauC() {
		fenetreCentrale.removeAll();
	}
	
	//Methode qui applique un theme donner en parametre
	private void appliqueTheme() {
		fenetre.setBackground((Color) theme.get(2));
		boutonTheme.setIcon((Icon) theme.get(4));
		boutonTheme.setBorderPainted(false);
		for (int i = 0; i < fenetreCentrale.getComponentCount(); i++) {
			this.fenetreCentrale.getComponent(i).setForeground((Color) this.theme.get(1));  //Couleur texte
		}
	}
	
	//Methode pour changer le theme
	private void changeTheme() {
		if(this.themeActuel=="clair")
			switchTheme("sombre");
		else
			switchTheme("clair");
	}
	
	//Pour changer le style de theme
	private void switchTheme(String theme)
	{
		this.themeActuel = theme;
		if(theme.contentEquals("clair")) {
			this.theme.put(1, Color.DARK_GRAY); //Couleur primaire
			this.theme.put(2, Color.WHITE); //Couleur secondaire
			this.theme.put(3, Color.GRAY); // couleur décor
			this.theme.put(4, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/src/Ressources/sombre.png"))));  //icon theme
		}
		else {
			this.theme.put(1, Color.WHITE); //Couleur primaire
			this.theme.put(2, Color.DARK_GRAY); //Couleur secondaire
			this.theme.put(3, Color.GRAY); // couleur décor
			this.theme.put(4, new ImageIcon(Toolkit.getDefaultToolkit().getImage(Fenetre.class.getResource("/src/Ressources/clair.png"))));  //icon theme
		}
		this.appliqueTheme();
	}

	//Recupérer le theme par défault
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
	
	//Verifie si une date est comprise entre deux date
	private static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
		  return !candidate.isBefore(start) && !candidate.isAfter(end);  // Inclusive.
	}	

}
	

