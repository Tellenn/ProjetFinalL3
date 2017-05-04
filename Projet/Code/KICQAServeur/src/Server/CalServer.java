package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CalServer extends UnicastRemoteObject implements CalServerInt {

	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "blondelq";
	private static final String mdp = "634714qB";
	private Statement stmt;

	public CalServer() throws RemoteException {
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Affiche un evenement
	 * 
	 * @param idEvenement
	 *            : id de l'evenement
	 * @throws SQLException
	 **/
	public void afficherOneEvent(int idEvenement) throws SQLException, RemoteException {
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement stmt = conn.createStatement();
		try {

			ResultSet rs = stmt
					.executeQuery("SELECT idEvenement, libelle, dateDebut, dateFin FROM Evenement WHERE idEvenement = "
							+ idEvenement);
			while (rs.next()) {
				System.out.print("id : " + rs.getInt(1) + " libelle : " + rs.getString(2) + " dateD : "
						+ rs.getString(3) + " dateF : " + rs.getString(4) + "\n");
			}
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affiche les événements en fonction de l'idUser
	 * 
	 * @param idUser
	 *            : id de l'utilisateur
	 * @throws SQLException
	 **/
	public void afficherEventsUser(int idUser) throws SQLException, RemoteException {
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement stmt = conn.createStatement();
		try {

			ResultSet rs = stmt.executeQuery(
					"SELECT idEvenement, libelle, dateDebut, dateFin FROM Evenement WHERE idUser = " + idUser
							+ " OR idEvenement IN (SELECT idEvenement FROM Participant WHERE idUser = " + idUser + ")");
			while (rs.next()) {
				System.out.print("id : " + rs.getInt(1) + " libelle : " + rs.getString(2) + " dateD : "
						+ rs.getString(3) + " dateF : " + rs.getString(4) + "\n");
			}
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crée un événement dans la base de données
	 * 
	 * @param dateDebut
	 *            : date de début de l’événement
	 * @param dateFin
	 *            : date de fin de l’événement
	 * @param libelle
	 *            : libelle de l’événement
	 * @throws SQLException
	 */
	public void createEvent(int idUser, String libelle, String dateDebut, String dateFin)
			throws SQLException, RemoteException {

		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement stmt = conn.createStatement();
		try {

			ResultSet rs = stmt.executeQuery("SELECT MAX(idEvenement) FROM Evenement");
			rs.next();
			int idEvent = rs.getInt(1) + 1;

			stmt.executeQuery(
					"INSERT INTO Evenement VALUES (" + idEvent + ", " + idUser + ", '" + libelle + "', to_date('"
							+ dateDebut + "', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American'), to_date('"
							+ dateFin + "', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American'))");
			stmt.executeQuery("COMMIT");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Met à jour un événement dans la BDD
	 * 
	 * @param idEvent
	 *            : id de l’événement à mettre à jour
	 * @param dateDebut
	 *            : nouvelle date de début de l’événement
	 * @param dateFin
	 *            : nouvelle date de fin de l’événement
	 * @param libelle
	 *            : nouveau libellé de l’événement
	 * @throws SQLException
	 */
	public void updateEvent(int idEvent, String libelle, String dateDebut, String dateFin)
			throws SQLException, RemoteException {

		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("UPDATE Evenement SET libelle = '" + libelle + "', dateDebut = to_date('" + dateDebut
					+ "', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American'), dateFin = to_date('" + dateFin
					+ "', 'DD-Mon-YY hh24:mi:ss', 'nls_date_language = American') WHERE idEvenement = " + idEvent);
			stmt.executeQuery("COMMIT");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Supprimer un événement de la base de données
	 * 
	 * @param idEvent
	 *            : id de l’événement à supprimer
	 * @throws SQLException
	 */
	public void deleteEvent(int idEvent) throws SQLException, RemoteException {

		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery("DELETE FROM Participant WHERE idEvenement = " + idEvent);
			stmt.executeQuery("DELETE FROM Evenement WHERE idEvenement = " + idEvent);
			stmt.executeQuery("COMMIT");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Ajoute un participant à un événement : dans la BD ajoute un n-uplet
	 * dans la table Participant
	 * 
	 * @param idEvent
	 *            : id de l’événement
	 * @param idParticipant
	 *            : id du participant
	 * @throws SQLException
	 */
	public void addParticipant(int idEvent, int idParticipant) throws SQLException, RemoteException {

		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery(
					"INSERT INTO Participant (idEvenement, idUser) VALUES (" + idEvent + ", " + idParticipant + ")");
			stmt.executeQuery("COMMIT");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Supprime un participant à un événement : dans la BD supprimer le
	 * n-uplet correspondant dans la table Participant
	 * 
	 * @param idEvent
	 *            : id de l’événement
	 * @param idParticipant
	 *            : id du participant
	 * @throws SQLException
	 */
	public void deleteParticipant(int idEvent, int idParticipant) throws SQLException, RemoteException {

		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement stmt = conn.createStatement();
		try {
			stmt.executeQuery(
					"DELETE FROM Participant WHERE idEvenement = " + idEvent + " AND idUser = " + idParticipant);
			stmt.executeQuery("COMMIT");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
