import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.IdentityHashMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JOptionPane;
public abstract class Client {
	///////////////////////////
	///						///
	///		PARTIE CHAT		///
	///						///
	///////////////////////////
	// TODO: connecter et créer objet
	private static 			ChatClient 		clientChat;
	private 				ChatServerInt 	serverChat;	
	
	/**
	 * Permet de démarer un serveur
	 * @param ip: l'ip du serveur
	 * @throws RemoteException 
	 */
	public void connectChat (String ip) throws RemoteException{
		serverChat = null;
		try {
			serverChat = (ChatServerInt) Naming.lookup("rmi://" + ip + "/chat");
			clientChat = new ChatClient(client.getName(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet d'envoyer un message privé
	 * @param text: le texte à envoyer 
	 * @param idDestinataire: personne qui reçoit le message
	 */
	public void sendTextPrivate(String text, int idDestinataire) {			
		try {
			serverChat.publishPrivate(text, client.getId(), idDestinataire);
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	// Message de salon
	 /* public void sendTextSalon(String text, int idRecepteur) {	
		if (client.getIdConversationPrivee() != -1) {
			try {
				server.publishPrivate(st, client.getId(), idRecepteur);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			if (client.getNumSalon() != -1) {
				try {
					server.publish(st, client.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("[System] ERROR: Pas normal d'être là");
				// erreur
			}
		}
		tf.setText("");
	}*/
	
	/**
	 * Permet d'ajouter un salon privé à un client
	 * @param text: nom du salon 
	 * @param idDestinataire: personne qui reçoit le message
	 */
	public void addSalon(String nomSalon, int idRecepteur) {			
		/*try {
			serverChat.publishPrivate(nomSalon, client.getId(), idRecepteur);
		} catch (Exception e) {
			System.out.println(e);
		}*/		
	}
	
	
	// ecrit le message dans l'interface à completer
	// TODO: à coder par le développeur
	public  void writeMsg(String st){
		//tx.setText(tx.getText() + "\n" + st);
	}


	
	///////////////////////////
	///						///
	///		PARTIE User		///
	///						///
	///////////////////////////
	
	private static 			UserClient 		client;
	private 				UserServerInt 	server;
	private static final 	String 			ip 		= "152.77.82.56";
	
	public void doConnect(String login, String mdp) {

		if (login.length() < 2) {
			// il faut que le nom ait plus de 2 caractères
			System.out.println( "Format du mot de login impossible");
			return;
		}
		if (mdp.length() < 2) {
			//il faut que le champ ne soit pas vide
			System.out.println(  "Format du mot de passe impossible");
			return;
		}
		try {
			//ip.getText()
			client = new UserClient(login,mdp);
			client.setGUI(this);
			server = (UserServerInt) Naming.lookup("rmi://" + ip + "/myabc");

			int id = server.login(client, login, mdp);
			if (id != -1) {
				System.out.println("id ======="+id);
				client.setId(id);
			} else {
				System.out.println("Identification impossible, Veuillez écrire un login ou mot de passe correct");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println( "ERROR, Connexion impossible....");
		}

	}


	public void ajoutUtilisateur(){
		try {
			server.ajoutUser(client, client.getName(), client.getMdp());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void suppressionUtilisateur(){
		try {
			server.suppressionUser(client);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void ajoutRelation(int idUser2){
		try {
			server.ajoutDeRelation(client, idUser2);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void suppressionRelation(int idUser2){
		try {
			server.suppressiontDeRelation(client, idUser2);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void ajoutAdmin(){
		try {
			server.ajoutAdmin(client);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void suppressionAdmin(){
		try {
			server.suppressionAdmin(client);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void chargementProfil(){
		try {
			server.chargementProfil(client);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void modificationProfil(){
		try {
			server.modificationProfil(client);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void modificationUsername(String userName){
		try {
			server.modificationUsername(client, userName);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void modificationPassword(String userPassword){
		try {
			server.modificationPassword(client, userPassword);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void modificationProfilParChamp(String champ, String content){
		try {
			server.modificationProfilParChamp(client, champ, content);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void ajoutChamp(String champ){
		try {
			server.ajoutChamp(champ);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void ajoutChamp(String champ, String content){
		try {
			server.ajoutChamp(client,champ,content);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void suppressionChamp(String champ){
		try {
			server.suppressionChamp(champ);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void suppressionChampPourUser(String champ){
		try {
			server.suppressionChamp(client,champ);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	///////////////////////////
	///						///
	///		PARTIE GED		///
	///						///
	///////////////////////////
	/**
	 * Permet de démarer un serveur
	 * @param ipServ l'ip du serveur
	 * @return l'interface serveur pour effectuer les autres action
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public static GEDServeurInt connectGED (String ip) throws MalformedURLException, RemoteException, NotBoundException{
		GEDServeurInt server = null;
		try {
			server = (GEDServeurInt) Naming.lookup("rmi://" + ip + "/abc");
		} catch (Exception e) {
			e.printStackTrace();
		}
			return server;
	}
	
	/**
	 * Télécharge depuis le serveur le fichier cible pour le client
	 * @param server l'interface serveur utilisé
	 * @param client le nom du client
	 * @param home l'endroit où est rangé le fichier dans le serveur
	 * @param file le nom du fichier
	 * @param cible l'endroit où le fichier doit être téléchargé
	 */
	public static void downloadFile(GEDServeurInt server, String client, String home, String file, String cible) {
		System.out.println("Mode Download client démarré");
		try {
			GEDClient c = new GEDClient(client);
			server.setFile(file, home);
			server.sendData(c, cible);
			System.out.println("Fin du download client");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet à un client d'upload la sur la GED un fichier
	 * @param server l'interface serveur utilisé
	 * @param client nom du client
	 * @param home endroit où est rengé le serveur
	 * @param file nom du fichier
	 */
	public static int UploadFile(GEDServeurInt server, String client, String home, String file) {
		int id = 0;
		System.out.println("Mode Upload client démarré");
		try {
			GEDClient c = new GEDClient(client);
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
	 * @param server l'interface serveur utilisé
	 * @param iddoc l'id du document à supprimer
	 * @param home le chemin vers le fichier à supprimer
	 */
	public static void DeleteFile(GEDServeurInt server, int iddoc, String home) {

		System.out.println("Mode Delete File ");
		try {
			server.deleteDoc(iddoc, home);
			System.out.println("Fichier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprimer un dossier
	 * @param server l'interface serveur utilisé
	 * @param idfol l'id du fichier à supprimer
	 * @param home le chemin vers le dossier
	 */
	public static void DeleteFolder(GEDServeurInt server, int idfol, String home) {

		System.out.println("Mode Delete Folder ");
		try {
			server.deleteFolder(idfol, home);
			System.out.println("Dossier supprimé");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Partager un Fichier
	 * @param server l'interface serveur utilisé
	 * @param iddoc l'id du document à partager
	 * @param iduser id de l'utilisateur avec qui partager
	 */
	public static void ShareFile(GEDServeurInt server, int iddoc, int iduser) {

		System.out.println("Mode Partage File ");
		try {
			server.shareDoc(iddoc, iduser);
			System.out.println("Fichier partagé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Partager un dossier	
	 * @param server l'interface serveur utilisé
	 * @param idfol id du dossier à partager
	 * @param iduser id de l'utilisateur à qui partager le dossier
	 */
	public static void ShareFolder(GEDServeurInt server, int idfol, int iduser) {

		System.out.println("Mode Partage Folder ");
		try {
			server.shareFolder(idfol, iduser);
			System.out.println("Dossier partagé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprimer le droit d'acces d'un utilisateur sur un fichier
	 * @param server l'interface serveur utilisé
	 * @param iddoc id du document où l'accès doit être révoqué
	 * @param iduser id du client à qui on va révoquer l'accès
	 */
	public static void deleteAccessDoc(GEDServeurInt server, int iddoc, int iduser) {

		System.out.println("Mode Delete Droit File ");
		try {
			server.deleteAccessDoc(iddoc, iduser);
			System.out.println("Droit Fichier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprimer le droit d'acces d'un utilisateur sur un dossier
	 * @param server l'interface serveur utilisé
	 * @param idfol
	 * @param iduser
	 */
	public static void deleteAccessFolder(GEDServeurInt server, int idfol, int iduser) {

		System.out.println("Mode Delete Droit Folder ");
		try {
			server.deleteAccessFolder(idfol, iduser);
			System.out.println("Droit Dossier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Créer un dossier
	 * @param server l'interface serveur utilisé
	 * @param nomfol le nom du dossier à créer
	 * @return l'id du dossier créé
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
	 * Renvoi l'id du dossier en fonction du nom
	 * @param server l'interface serveur utilisé
	 * @param nomfol le nom du dossier au quel on récupère les info
	 * @return Retourne l'id
	 */
	public static int infoFolder(GEDServeurInt server, String nomfol) {
		int iddos = 0;
		System.out.println("Mode Edit Dossier ");
		try {
			iddos = server.infoFolder(nomfol);
			System.out.println("Info Dossier ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iddos;
	}

	/**
	 * Affichage Information d'acces à un dossier
	 * @param server l'interface serveur utilisé
	 * @param idfol l'id du dossier 
	 * @return Retourne les users qui ont accès au dossier
	 */
	public static Vector<Integer> getAccessFolder(GEDServeurInt server, int idfol) {
		Vector<Integer> users = null;
		System.out.println("Mode Edit Dossier ");
		try {
			users = server.getAccessFolder(idfol);
			System.out.println("Info Acces Dossier");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * Affichage Information d'acces à un fichier	
	 * @param server l'interface serveur utilisé
	 * @param iddoc l'id du document
	 * @return retourne les users qui ont accès au fichier
	 */
	public static Vector<Integer> getAccessDoc(GEDServeurInt server, int iddoc) {
		Vector<Integer> users = null;
		System.out.println("Mode Edit Acces File ");
		try {
			users = server.getAccessDoc(iddoc);
			System.out.println("Fichier supprimé ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * Affichage Information dossier Racine
	 * @param server l'interface serveur utilisé
	 * @param idfol id du dossier fils
	 * @return l'id du dossier pere
	 */
	public static int getRacineFolder(GEDServeurInt server, int idfol) {
		int idPere = 0;
		System.out.println("Mode Delete File ");
		try {
			idPere = server.getRacineFolder(idfol);
			System.out.println("Info ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idPere;
	}

	/**
	 * Affichage Information dossier fils d'un dossier
	 * @param server l'interface serveur utilisé
	 * @param idfol id du dossier père
	 * @return  Retourne les ids fils
	 */
	public static Vector<Integer> getFilsFolder(GEDServeurInt server, int idfol) {
		Vector<Integer> idfils = null;
		System.out.println("Mode Delete File ");
		try {
			idfils = server.getFilsFolder(idfol);
			System.out.println("Info  ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idfils;
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
			e.printStackTrace();
		}
	}

	
	
}
