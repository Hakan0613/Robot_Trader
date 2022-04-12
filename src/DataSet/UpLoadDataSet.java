package DataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import Simulator.Cotation;
import Simulator.Liste;

//
public class UpLoadDataSet {
	private MetierDB taskerDB;

	public UpLoadDataSet(MetierDB metier) {
		taskerDB = metier;
	}
	public UpLoadDataSet() {}

	/**
	 * Charge les données depuis le fichier/dossier donnée en paramètre.
	 * @param filePath
	 * @param separateur
	 * @return boolean
	 */
	public boolean chargerDataSet(String filePath,char separateur) {
		File fichier = new File(filePath);
		if(fichier.exists() && fichier.canRead()) {
			if(fichier.isFile())
				return chargerData(fichier, separateur);
			else {
				boolean res = false;
				File[] dossier = fichier.listFiles();
				for (int i = 0; i < dossier.length; i++) {
					res = chargerDataSet(dossier[i].getPath(), separateur);
				}
				return res;
			}
		}
		else
			return false;
	}


	/**
	 * Crée la table de donnée et déclenche le chargement des données.
	 * @param fichier
	 * @param separateur
	 * @return boolean
	 */
	private boolean chargerData(File fichier, char separateur) {
		System.out.println("Fichier en cour de chargement : " + fichier.getName());
		if(fichier.exists() && fichier.canRead()) {
			String nomTable = "data_"+fichier.getName().substring(fichier.getName().indexOf("_")+1, fichier.getName().indexOf("."));
			if(this.newTable(nomTable)) {
				return this.saveData(fichier, nomTable, separateur);
			}
			else
				return false;
		}
		else
			return false;
	}

	/**
	 * Charge chaque ligne du fichier dans la BD.
	 * @param leFichier
	 * @param separateur
	 * @return boolean
	 */
	public Liste<Cotation> loadFile(File leFichier, char separateur) {
		try {
			Scanner myReader = new Scanner(leFichier);
			Cotation lastData = null;
			LocalTime currentLineTime = LocalTime.of(9, 0);
			Liste<Cotation> cotationListe = new Liste<>();
			while (myReader.hasNextLine()) {
				String ligne = myReader.nextLine();
				//Verification que le séparateur correspond à celui du fichier
				if(6>ligne.indexOf(separateur) || ligne.indexOf(separateur)>16 || ligne.lastIndexOf(separateur)<20) {
					System.out.println("Le seprateur specifié n'est pas valide");
				}
				Cotation data = this.separator(ligne, separateur);
				//Cas des donnée manquante
				while(currentLineTime.isBefore((LocalTime) data.getHeure())) {
					if(lastData!=null) {
						lastData.setHeure(currentLineTime);
						cotationListe.add(lastData);
					}
					currentLineTime = currentLineTime.plusMinutes(5);
				}
				cotationListe.add(data);
				lastData = data;
				currentLineTime = lastData.getHeure().plusMinutes(5);
			}
			myReader.close();
			return cotationListe;
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Charge chaque ligne du fichier dans la BD.
	 * @param leFichier
	 * @param nomTable
	 * @param separateur
	 * @return boolean
	 */
	private boolean saveData(File leFichier, String nomTable, char separateur) {
		boolean checkUpload=true;
		try {
			Scanner myReader = new Scanner(leFichier);
			Cotation lastData = null;
			LocalTime currentLineTime = LocalTime.of(9, 0);
			while (myReader.hasNextLine() && checkUpload) {
				String ligne = myReader.nextLine();
				//Verification que le séparateur correspond à celui du fichier
				if(6>ligne.indexOf(separateur) || ligne.indexOf(separateur)>16 || ligne.lastIndexOf(separateur)<20) {
					checkUpload=false;
					System.out.println("Le seprateur specifié n'est pas valide");
				}
				if(checkUpload) {
					Cotation data = this.separator(ligne, separateur);

					//Cas des donnée manquante
					while(currentLineTime.isBefore((LocalTime) data.getHeure())) {
						if(lastData!=null) {
							lastData.setHeure(currentLineTime);
							checkUpload = this.dataUpload(lastData, nomTable);
						}
						currentLineTime = currentLineTime.plusMinutes(5);
					}
					//System.out.println(data[0] + " " + data[1] + " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5] + " " + data[6] + " " + data[7]);
					checkUpload=this.dataUpload(data, nomTable);
					lastData = data;
					currentLineTime = (lastData.getHeure()).plusMinutes(5);
				}
			}myReader.close();
		} catch (FileNotFoundException e) {
			checkUpload=false;
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return checkUpload;
	}

	/**
	 * Méthode qui va crée une table pour les cotations.
	 * @param nomDB
	 * @return boolean
	 */
	private boolean newTable(String nomDB) {
			return -1<this.taskerDB.updateDB("CREATE TABLE `"+this.taskerDB.DBName+"`.`"+nomDB+"`( `noAction` varchar(20) NOT NULL, `date` date NOT NULL, `heure` time NOT NULL, `coteDebut` float NOT NULL, `coteMax` float NOT NULL, `coteMin` float NOT NULL, `coteFin` float NOT NULL, `nbTransaction` int(11) NOT NULL, PRIMARY KEY (`noAction`, `date`, `heure`)) ENGINE=InnoDB");
		}


	/**
	 * Séparer les données d'une ligne de données pour crée et renvoyée une cotation correspondant au données.
	 * @param data
	 * @param separateur
	 * @return Cotation
	 */
	public Cotation separator(String data, char separateur) {
		//System.out.println("Formatage des données");
		ArrayList<Object> dataSeparate = new ArrayList<Object>();
		int lastIndex = 0;
		for (int i = 0; i < 7; i++) {
			
			String maDonnée = data.substring(0+lastIndex, data.indexOf(separateur, lastIndex));
			//Traitement pour les horaires
			if(i==2)
				dataSeparate.add(LocalTime.of(Integer.parseInt(maDonnée.substring(0, maDonnée.indexOf(":"))),Integer.parseInt(maDonnée.substring(maDonnée.indexOf(":")+1,maDonnée.length()))));
			//Traitement des dates
			else if(i==1)
				dataSeparate.add(LocalDate.of(Integer.parseInt(maDonnée.substring(maDonnée.lastIndexOf("/")+1,maDonnée.length()))+2000, Integer.parseInt(maDonnée.substring(maDonnée.indexOf("/")+1,maDonnée.lastIndexOf("/"))), Integer.parseInt(maDonnée.substring(0,maDonnée.indexOf("/")))));
			//Pour les cotations
			else if(i>2)
				dataSeparate.add(Float.parseFloat(data.substring(0+lastIndex, data.indexOf(separateur, lastIndex))));
			else
				dataSeparate.add(data.substring(0+lastIndex, data.indexOf(separateur, lastIndex)));
			lastIndex = data.indexOf(separateur, lastIndex)+1;
		}
		//Dernière valeur
		dataSeparate.add(Integer.parseInt(data.substring(lastIndex, data.length())));
		return new Cotation((String) dataSeparate.get(0), (LocalDate) dataSeparate.get(1), (LocalTime) dataSeparate.get(2), (Float) dataSeparate.get(3), (Float) dataSeparate.get(4), (Float) dataSeparate.get(5), (Float) dataSeparate.get(6), (int) dataSeparate.get(7));
	}


	/**
	 * Charge dans la BD la cotation donné en paramètre.
	 * @param data
	 * @param nomDB
	 * @return
	 */
	private boolean dataUpload(Cotation data, String nomDB) {
		return 0<this.taskerDB.updateDB("INSERT INTO `"+nomDB+"` (`noAction`, `date`, `heure`, `coteDebut`, `coteMax`, `coteMin`, `coteFin`, `nbTransaction`) VALUES ('"+data.getNoAction()+"', '"+data.getDate()+"', '"+data.getHeure()+"', '"+data.getCoteDebut()+"', '"+data.getCoteMax()+"', '"+data.getCoteMin()+"', '"+data.getCoteFin()+"', '"+data.getNbTransaction()+"')");			
	}

	/**
	 * Charger depuis la BD un jeu de donnée à une date spécifier
	 * @param date
	 * @return ArrayList<Cotation>
	 */
	public ArrayList<Cotation> getHistorique(LocalDate date){
		ArrayList<Cotation> cotation = new ArrayList<>();

		ResultSet resultDB = this.taskerDB.querryDB("SELECT * FROM `data_"+date.format(DateTimeFormatter.ofPattern("ddMMyyyy"))+"`");
		Cotation tempCote;
		if (resultDB!=null)
			try {
				while(resultDB.next()) {
					tempCote = new Cotation(resultDB.getString("noAction"),  resultDB.getDate("date").toLocalDate(), resultDB.getTime("heure").toLocalTime(), resultDB.getFloat("coteDebut") , resultDB.getFloat("coteMax"),resultDB.getFloat("coteMin"), resultDB.getFloat("coteFin"), resultDB.getInt("nbTransaction"));
					cotation.add(tempCote);
				}
			}
			catch (SQLException s){
				s.printStackTrace();
				System.err.println("Une erreur dans le chargement des données depuis la BD");
			}
		else
			return null;
		return cotation;
	}
}
