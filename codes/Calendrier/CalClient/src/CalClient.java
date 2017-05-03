import java.rmi.RemoteException;
import java.sql.SQLException;

public class CalClient {

	/**
	* Donne l'ordre au seveur d'afficher les événements en fonction de l'idUser
	* @param server : serveur
	* @param idUser : id de l'utilisateur
	**/
	public static void afficherOrder(CalServerInt server, int idUser) throws RemoteException {
		try {
			server.afficher(idUser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* Donne l'ordre au seveur de créer un événement dans la base de données
	* @param server : serveur
	* @param dateDebut : date de début de l’événement
	* @param dateFin : date de fin de l’événement
	* @param libelle : libelle de l’événement  
	*/
	public static void createEventOrder(CalServerInt server, int idUser, String libelle, String dateDebut, String dateFin) throws RemoteException{
		try {
			server.createEvent(idUser, libelle, dateDebut, dateFin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	* Donne l'ordre au seveur de mettre à jour un événement dans la BDD
	* @param server : serveur
	* @param idEvent : id de l’événement à mettre à jour
	* @param dateDebut : nouvelle date de début de l’événement
	* @param dateFin : nouvelle date de fin de l’événement
	* @param libelle : nouveau libellé de l’événement
	*/
	public static void updateEventOrder(CalServerInt server, int idEvent, String libelle, String dateDebut, String dateFin) throws RemoteException{
		try {
			server.updateEvent(idEvent, libelle, dateDebut, dateFin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	* Donne l'ordre au seveur de supprimer un événement de la base de données
	* @param server : serveur
	* @param idEvent : id de l’événement à supprimer
	*/
	public static void deleteEventOrder(CalServerInt server, int idEvent) throws RemoteException{
		try {
			server.deleteEvent(idEvent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	* Donne l'ordre au seveur d'ajouter un participant à un événement : dans la BD ajoute un n-uplet dans la table Participant
	* @param server : serveur
	* @param idEvent : id de l’événement
	* @param idParticipant : id du participant
	*/
	public static void addParticipantOrder(CalServerInt server, int idEvent, int idParticipant) throws RemoteException{
		try {
			server.addParticipant(idEvent, idParticipant);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	* Donne l'ordre au seveur de supprimer un participant à un événement : dans la BD supprimer le n-uplet correspondant dans la table Participant
	* @param server : serveur
	* @param idEvent : id de l’événement
	* @param idParticipant : id du participant
	*/
	public static void deleteParticipantOrder(CalServerInt server, int idEvent, int idParticipant) throws RemoteException{
		try {
			server.deleteEvent(idEvent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
