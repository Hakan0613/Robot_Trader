package DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Gestion directe de la DB
//Cnx se ferme si l'utilisateur sort du sous-menu Data-Set ou lorsque qu'un simulation prend fin
public class MetierDB {
	private ConnectDB connection;
	private Statement stmt;
	public String DBName;
	public MetierDB() {
		this.connection = new ConnectDB();
		String urlDB = "jdbc:mariadb://localhost:3306";
		boolean statutCo = connection.getConnect("org.mariadb.jdbc.Driver", urlDB , "root", "");
		try {
			stmt = connection.cnx.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Connexion à la BD spécifié en paramètre
	 * @param dbName
	 */
	public MetierDB(String dbName) {
		this.DBName = dbName;
		this.connection = new ConnectDB();
		String urlDB = "jdbc:mariadb://localhost:3306/"+DBName;
		boolean statutCo = connection.getConnect("org.mariadb.jdbc.Driver", urlDB , "root", "");
		try {
			stmt = connection.cnx.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Permet de faire des requettes sur la BD
	 * @param querry
	 * @return
	 */
	public ResultSet querryDB(String querry) {
		ResultSet rs = null; 
		try {
			rs = this.stmt.executeQuery(querry);		
		} catch (SQLException e) {
			System.err.println("Problème dans l'execution de la requette ");
			e.printStackTrace();	
		}
		finally {
			return rs;
		}
	}

	/**
	 * Permet de faire des opération de modification (suppression, changement) sur la BD
	 * @param querryUpdate
	 * @return	Nombre de ligne modifier
	 */
	public Integer updateDB(String querryUpdate) {
		int updateLine =0;
		try {
			updateLine=stmt.executeUpdate(querryUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateLine;
	}
	
	
	
	
}
