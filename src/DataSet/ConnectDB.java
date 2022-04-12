package DataSet;

import java.sql.*;


public class ConnectDB {
	Connection cnx;

	/**
	 * @param leDriver
	 * @param pseudoURL
	 * @param user
	 * @param mdp
	 * @return Vrai si connexion réussi
	 */
	@SuppressWarnings("finally")
	public boolean getConnect(String leDriver, String pseudoURL, String user, String mdp) {
		boolean res=false;
		try {
			//E1 : charger le driver mysql
			Class.forName(leDriver).newInstance(); // creation d'une instance du Driver
			//E2 : crée la connection
			this.cnx = DriverManager.getConnection(pseudoURL,user,mdp);
			res=true;
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.err.println("Problème au chargement du Driver ou dans le demande de connexion");
		}
		finally {
			return res;
		}
	}
}
