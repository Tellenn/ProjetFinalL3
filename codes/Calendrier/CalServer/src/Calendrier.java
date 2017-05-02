import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Calendrier {

//	/**
//  * Execute la requête commit après chaque méthode
//  * @throws SQLException 
//  */
// public static void commit() throws SQLException {
//    
// 	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
//     Statement st = conn.createStatement();
//     ResultSet rs = st.executeQuery("commit");
//     st.close();
//    
// };
	
	public static void insert() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "blondelq", "634714qB");
	    Statement stmt = conn.createStatement();
		try {
			
			ResultSet rs = stmt.executeQuery("select max(idEvenement) from Evenement");
	        rs.next();
	        int idEvent = rs.getInt(1) + 1;
			
	        stmt.executeQuery("INSERT INTO Evenement VALUES (9, 'test', to_date('27-APR-17 14:00:00', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American'), to_date('27-APR-17 15:00:00', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American'))");
			stmt.executeQuery("commit");
	        stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Affiche les événements en fonction de l'idUser
	* @param idUser
	**/
	public static void afficher(int idUser) throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "blondelq", "634714qB");
	    Statement stmt = conn.createStatement();
		try {
			
	        ResultSet rs = stmt.executeQuery("SELECT idEvenement, libelle, dateDebut, dateFin FROM Evenement WHERE idUser = " + idUser + " OR idEvenement IN (SELECT idEvenement FROM Participant WHERE idUser = " + idUser + ")");
			while(rs.next()){
	            System.out.print("id : "+rs.getInt(1) + " libelle : "+rs.getString(2) + " dateD : "+rs.getString(3) + " dateF : "+rs.getString(4) + "\n");
	        }
	        stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	* Crée un événement à la fois dans la base de données et dans le fichier xml
	* @param dateDebut	date de début de l’événement
	* @param dateFin	date de fin de l’événement
	* @param libelle	libelle de l’événement 
	* @throws SQLException 
	*/
	public static void createEvent(int idUser, String libelle, String dateDebut, String dateFin) throws SQLException{

		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "blondelq", "634714qB");
	    Statement stmt = conn.createStatement();
		try {
			
			ResultSet rs = stmt.executeQuery("select max(idEvenement) from Evenement");
	        rs.next();
	        int idEvent = rs.getInt(1) + 1;
			
			stmt.executeQuery("INSERT INTO Evenement VALUES (" + idEvent + ", " + idUser + ", '" + libelle + "', to_date('" + dateDebut + "', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American'), to_date('" + dateFin + "', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American'))");
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
	public static void updateEvent(int idEvent, String libelle, String dateDebut, String dateFin) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "blondelq", "634714qB");
	    Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("UPDATE Evenement SET libelle = '" + libelle + "', dateDebut = to_date('" + dateDebut + "', 'DD-Mon-YY', 'nls_date_language = American'), dateFin = to_date('" + dateFin + "', 'DD-Mon-YY', 'nls_date_language = American') WHERE idEvenement = " + idEvent);
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
	public static void deleteEvent(int idEvent) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "blondelq", "634714qB");
	    Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("DELETE FROM Participant WHERE idEvenement = " + idEvent);
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
	public static void addParticipant(int idEvent, int idParticipant) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "blondelq", "634714qB");
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
	public static void deleteParticipant(int idEvent, int idParticipant) throws SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "blondelq", "634714qB");
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
//	public static void createField(String nameField){
//		
//	}
//
//	/**
//	* Pour un événement donné, récupère la valeur du nom du champ passé en paramètre
//	* @param idEvent	id de l’événement
//	* @param nameField	nom du champ dont on récupère la valeur
//	* @return		la valeur du champ passé en paramètre
//	*/
//	public static String getField(int idEvent, String nameField){
//		
//	}
//
//	/**
//	* Pour un événement donné, on met à jour la valeur d’un champ
//	* @param idEvent	id de l’événement
//	* @param nameField	nom du champ à mettre à jour
//	* @param valueField	nouvelle valeur du champ
//	*/
//	public static void setField(int idEvent, String nameField, String valueField){
//		
//	}
//
//	/**
//	* Pour un événement donné, supprimer un champ
//	* @param idEvent	id de l’événement
//	* @param nameField	nom du champ à supprimer
//	*/
//	public static void deleteField(int idEvent String nameField){
//		
//	}
	
}
