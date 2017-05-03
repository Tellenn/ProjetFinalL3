import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
public class Client {
	///////////////////////////
	///						///
	///		PARTIE GED		///
	///						///
	///////////////////////////
	/**
	 * Télécharge depuis le serveur le fichier cible pour le client
	 * @param ipServ l'ip du serveur cible
	 * @param client le nom du client
	 * @param home l'endroit où est rangé le fichier dans le serveur
	 * @param file le nom du fichier
	 * @param cible l'endroit où le fichier doit être téléchargé
	 */
	public static void downloadFile(String ipServ, String client, String home, String file, String cible) {
		System.out.println("Mode Download client démarré");
		try {
			GEDClient c = new GEDClient(client);
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.setFile(file, home);
			server.sendData(c, cible);
			System.out.println("Fin du download client");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet à un client d'upload la sur la GED un fichier
	 * @param ipServ ip du serveur
	 * @param client nom du client
	 * @param home endroit où est rengé le serveur
	 * @param file nom du fichier
	 */
	public static int UploadFile(String ipServ, String client, String home, String file) {
		int id = 0;
		System.out.println("Mode Upload client démarré");
		try {
			GEDClient c = new GEDClient(client);
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.setFile(file, "GED/");
			id = c.sendData(server, home, file);
			System.out.println("Fin de l'upload client");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Supprimer un fichier
	 * 
	 * @param ipServ
	 * @param iddoc
	 * @param home
	 */
	public static void DeleteFile(String ipServ, int iddoc, String home) {

		System.out.println("Mode Delete File ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.deleteDoc(iddoc, home);
			System.out.println("Fichier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprimer un dossier
	 * 
	 * @param ipServ 
	 * @param idfol
	 * @param home
	 */
	public static void DeleteFolder(String ipServ, int idfol, String home) {

		System.out.println("Mode Delete Folder ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.deleteFolder(idfol, home);
			System.out.println("Dossier supprimé");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Partager un Fichier
	 * 
	 * @param ipServ
	 * @param iddoc
	 * @param iduser
	 */
	public static void ShareFile(String ipServ, int iddoc, int iduser) {

		System.out.println("Mode Partage File ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.shareDoc(iddoc, iduser);
			System.out.println("Fichier partagé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Partager un dossier
	 * 
	 * @param ipServ
	 * @param idfol
	 * @param iduser
	 */
	public static void ShareFolder(String ipServ, int idfol, int iduser) {

		System.out.println("Mode Partage Folder ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.shareFolder(idfol, iduser);
			System.out.println("Dossier partagé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprimer le droit d'acces d'un utilisateur sur un fichier
	 * 
	 * @param ipServ
	 * @param iddoc
	 * @param iduser
	 */
	public static void deleteAccessDoc(String ipServ, int iddoc, int iduser) {

		System.out.println("Mode Delete Droit File ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.deleteAccessDoc(iddoc, iduser);
			System.out.println("Droit Fichier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprimer le droit d'acces d'un utilisateur sur un dossier
	 * 
	 * @param ipServ
	 * @param idfol
	 * @param iduser
	 */
	public static void deleteAccessFolder(String ipServ, int idfol, int iduser) {

		System.out.println("Mode Delete Droit Folder ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.deleteAccessFolder(idfol, iduser);
			System.out.println("Droit Dossier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Créer un dossier
	 * 
	 * @param ipServ
	 * @param idfol
	 * @param nomfol
	 */
	public static int createFolder(GEDServeurInt server, String nomfol) {
		int idfol = 0;
		System.out.println("Mode Create Folder ");
		try {
			idfol = server.createFolder(nomfol, "GED");
			System.out.println("Dossier Créé");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idfol;
	}

	/**
	 * Affichage Information d'un dossier
	 * 
	 * @param ipServ
	 * @param nomfol
	 */
	public static void infoFolder(String ipServ, String nomfol) {

		System.out.println("Mode Edit Dossier ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.infoFolder(nomfol);
			System.out.println("Info Dossier ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affichage Information d'acces à un dossier
	 * 
	 * @param ipServ
	 * @param idfol
	 */
	public static void getAccessFolder(String ipServ, int idfol) {

		System.out.println("Mode Edit Dossier ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.getAccessFolder(idfol);
			System.out.println("Info Acces Dossier");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affichage Information d'acces à un fichier
	 * 
	 * @param ipServ
	 * @param iddoc
	 */
	public static void getAccessDoc(String ipServ, int iddoc) {

		System.out.println("Mode Edit Acces File ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.getAccessDoc(iddoc);
			System.out.println("Fichier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affichage Information dossier Racine
	 * 
	 * @param ipServ
	 * @param idfol
	 */
	public static void getRacineFolder(String ipServ, int idfol) {

		System.out.println("Mode Delete File ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.getRacineFolder(idfol);
			System.out.println("Info ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affichage Information dossier fils d'un dossier
	 * 
	 * @param ipServ
	 * @param idfol
	 */
	public static void getFilsFolder(String ipServ, int idfol) {

		System.out.println("Mode Delete File ");
		try {
			GEDServeurInt server = (GEDServeurInt) Naming.lookup("rmi://" + ipServ + "/abc");
			server.getFilsFolder(idfol);
			System.out.println("Info  ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	///////////////////////////
	///						///
	///		PARTIE CAL		///
	///						///
	///////////////////////////
	
	/**
	* Donne l'ordre au seveur d'afficher un événement
	* @param server : serveur
	* @param idEvenement : id de l'evenement
	**/
	public static void afficherOneEventOrder(CalServerInt server, int idEvenement) throws RemoteException{
		try {
			server.afficherOneEvent(idEvenement);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* Donne l'ordre au seveur d'afficher les événements en fonction de l'idUser
	* @param server : serveur
	* @param idUser : id de l'utilisateur
	**/
	public static void afficherEventsUserOrder(CalServerInt server, int idUser) throws RemoteException {
		try {
			server.afficherEventsUser(idUser);
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
