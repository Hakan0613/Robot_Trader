package DataSet;

import java.sql.SQLException;

//Pour crée une nouvelle BD 
public class GenerateDB {

	public GenerateDB(MetierDB taskDB, String dbName) {
		if(verifDB(taskDB, dbName)) {
			System.out.println("Une base de données sous ce nom existe déjà");
		}
		else {
			createDB(taskDB, dbName);
			System.out.println("BD crée avec succée");
		}
	}

	public GenerateDB() {
		// TODO Auto-generated constructor stub
	}



	// Méthode qui verifie si une DB porte déjà le nom donné
	public boolean verifDB(MetierDB taskDB, String dbName) {
		try {
			return taskDB.querryDB("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"+dbName+"'").last();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// Méthode qui créé une DB
	private boolean createDB(MetierDB taskDB, String dbName ) {
		return taskDB.updateDB("CREATE DATABASE " +dbName) > 0 ;
	}


}
