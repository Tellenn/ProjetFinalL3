import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;

public class Calendrier {

	
//	/**
//     * Execute la requête commit après chaque méthode
//     * @throws SQLException 
//     */
//    public static void commit() throws SQLException {
//       
//    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
//        Statement st = conn.createStatement();
//        ResultSet rs = st.executeQuery("commit");
//        st.close();
//       
//    };
	
	/**
	* Crée un événement à la fois dans la base de données et dans le fichier xml
	* @param dateDebut	date de début de l’événement
	* @param dateFin	date de fin de l’événement
	* @param libelle	libelle de l’événement 
	* @throws SQLException 
	*/
	public void createEvent(String libelle, Date dateDebut, Date dateFin) throws SQLException{

		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	    Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("INSERT INTO Evenement(libelle, dateDebut, dateFin) VALUES (" + libelle + ", " + dateDebut.toString() + ", " + dateFin.toString() +")");
			stmt.executeQuery("commit");
            stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	* Met à jour un événement dans le fichier xml
	* @param idEvent	id de l’événement à mettre à jour
	* @param dateDebut	nouvelle date de début de l’événement
	* @param dateFin	nouvelle date de fin de l’événement
	* @param libelle	nouveau libellé de l’événement
	* @throws SQLException
	*/
	private void updateEvent(int idEvent, Date dateDebut, Date dateFin, String libelle) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	    Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("UPDATE SET libelle = " + libelle + 
										", dateDebut = " + dateDebut + 
										", dateFin = " + dateFin + 
										" WHERE idEvenement = " + idEvent);
			stmt.executeQuery("commit");
            stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	* Supprimer un événement à la fois de la base de données et dans le fichier xml
	* @param idEvent	id de l’événement à supprimer
	* @throws SQLException
	*/
	private void deleteEvent(int idEvent) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	    Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("DELETE FROM Evenement WHERE idEvenement = " + idEvent);
			stmt.executeQuery("commit");
            stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	* Ajoute un participant à un événement : dans la BD ajoute un n-uplet dans la table Participant
	* @param idEvent	id de l’événement
	* @param idParticipant	id du participant
	* @throws SQLException
	*/
	private void addParticipant(int idEvent, int idParticipant) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	    Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("INSERT INTO Participant (idEvenement, idUser) VALUES (" + idEvent + ", " + idParticipant +")");
			stmt.executeQuery("commit");
            stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	* Supprime un participant à un événement : dans la BD supprimer le n-uplet correspondant dans la table Participant
	* @param idEvent	id de l’événement
	* @param idParticipant	id du participant
	* @throws SQLException
	*/
	private void deleteParticipant(int idEvent, int idParticipant) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	    Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("DELETE FROM Participant WHERE idEvenement = " + idEvent + " AND idUser = " + idParticipant);
			stmt.executeQuery("commit");
            stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

//	/**
//	* Ajoute un nouveau champ dans le fichier xml pour un événement donné
//	* @param idEvent	id de l’événement
//	* @param nameField	nom du champ à ajouter
//	*/
//	private void createField(int idEvent, String nameField){
//		
//	}
//
//	/**
//	* Pour un événement donné, récupère la valeur du nom du champ passé en paramètre
//	* @param idEvent	id de l’événement
//	* @param nameField	nom du champ dont on récupère la valeur
//	* @return		la valeur du champ passé en paramètre
//	*/
//	private String getField(int idEvent, String nameField){
//		
//	}
//
//	/**
//	* Pour un événement donné, on met à jour la valeur d’un champ
//	* @param idEvent	id de l’événement
//	* @param nameField	nom du champ à mettre à jour
//	* @param valueField	nouvelle valeur du champ
//	*/
//	private void setField(int idEvent, String nameField, String valueField){
//		
//	}
//
//	/**
//	* Pour un événement donné, supprimer un champ
//	* @param idEvent	id de l’événement
//	* @param nameField	nom du champ à supprimer
//	*/
//	private void deleteField(int idEvent String nameField){
//		
//	}
	
}
