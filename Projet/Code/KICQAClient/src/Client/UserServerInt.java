package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
 
public interface UserServerInt extends Remote{	
/**
	 * sert a se connecter
	 * @param a le client
	 * @param login le username du client
	 * @param mdp le mot de passe du client
	 * @return -1 si on n'arrive pas a se connecter ou l'idUser du client
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public int login(UserClientInt a, String login, String mdp) throws RemoteException;
	/**
	 * sert a publier s
	 * @param s la chaine de caractere a afficher
	 * @param id l'identifiant de l'utilisateur
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void publish (String s, int id )throws RemoteException ;
	/**
	 * sert a enregistrer dans des vecteurs les clients qui se connectent
	 * @return un vecteur
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public Vector getConnected() throws RemoteException ;
	
	/**
	 * sert a ajouter une relation entre le client a et l'idUser2
	 * @param a le client
	 * @param idUser2 l'utilisateur avec qui on veut ajouter une relation
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void ajoutDeRelation(UserClientInt a, int idUser2) throws RemoteException;
	/**
	 * sert a supprimer une relation entre le client a et l'idUser2
	 * @param a le client
	 * @param idUser2 l'utilisateur avec qui on veut supprimer une relation
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void suppressiontDeRelation(UserClientInt a, int idUser2) throws RemoteException ;
	/**
	 * sert a charger le profil du client a
	 * @param a le client
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void chargementProfil(UserClientInt a) throws RemoteException ;
	/**
	 * sert a modifier tous les champs du client a
	 * @param a le client
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void modificationProfil(UserClientInt a) throws RemoteException ;
	/**
	 * sert a modifier le champ avec le contenu content du client a
	 * @param a le client
	 * @param champ le champ a modifier
	 * @param content le contenu du champ
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void modificationProfilParChamp(UserClientInt a,String champ, String content) throws RemoteException ;
	/**
	 * sert a modifier le login du client a 
	 * @param a le client
	 * @param userName le nouveau login
	 * @throws RemoteExceptions'il y a un problem de communication 
	 */
	public void modificationUsername(UserClientInt a,String userName) throws RemoteException ;
	/**
	 * sert a modifier le mot de passe du client a
	 * @param a le client
	 * @param password le nouveau mot de passe
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void modificationPassword(UserClientInt a,String password) throws RemoteException ;
	/**
	 * sert a ajouter le client a dans la base de donnees avec le login userName et le mot de passe userPassword
	 * @param a le nouveau client
	 * @param userName le nouveau login
	 * @param userPassword le nouveau mot de passe
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void ajoutUser(UserClientInt a, String userName, String userPassword) throws RemoteException ;
	/**
	 * sert a supprimer le client a
	 * @param a le client 
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void suppressionUser(UserClientInt a) throws RemoteException ;	
	/**
	 * sert a ajouter un champ dans le fichier xml
	 * @param champ le champ a ajouter
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void ajoutChamp(String champ) throws RemoteException ;	
	/**
	 * sert a ajouter un nouveau champ avec le contenu content au client a
	 * @param a le client 
	 * @param champ le nouveau champ
	 * @param content le contenu du champ
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void ajoutChamp(UserClientInt a, String champ, String content) throws RemoteException ;	
	/**
	 * sert a supprimer un champ dans le fichier xml
	 * @param champ le champ a supprimer
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void suppressionChamp(String champ) throws RemoteException ;	
	/**
	 * sert a supprimer le champ champ du client a
	 * @param a le client 
	 * @param champ le champ a supprimer
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void suppressionChamp(UserClientInt a, String champ) throws RemoteException ;
	/**
	 * sert a ajouter un nouveau admin a à la base de données
	 * @param a le client 
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void ajoutAdmin(UserClientInt a) throws RemoteException;
	/**
	 * sert a supprimerl'admin a de la base de données
	 * @param a le client 
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void suppressionAdmin(UserClientInt a) throws RemoteException;

}
