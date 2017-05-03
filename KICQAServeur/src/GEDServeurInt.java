import java.rmi.*;
import java.sql.SQLException;

public interface GEDServeurInt extends Remote {
	/**
	 * Modifier un fichier
	 * 
	 * @param f
	 * @param path
	 * @throws java.rmi.RemoteException
	 */
	public void setFile(String f, String path) throws RemoteException;

	/**
	 * Envoier des données
	 * 
	 * @param c
	 * @param cible
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public boolean sendData(GEDClientInt c, String cible) throws RemoteException;

	/**
	 * Recevoir des données
	 * 
	 * @param data
	 * @param len
	 * @return id du fichier retourné
	 * @throws java.rmi.RemoteException
	 */
	public int receiveData(byte[] data, int len) throws RemoteException;

	/**
	 * Supprimer un fihier
	 * 
	 * @param iddoc
	 * @param chemin
	 * @throws java.rmi.RemoteException
	 */
	public void deleteDoc(int iddoc, String chemin) throws RemoteException;

	/**
	 * Partager un Fichier
	 * 
	 * @param idDoc
	 * @param idUser
	 * @throws java.rmi.RemoteException
	 */
	public void shareDoc(int idDoc, int idUser) throws RemoteException;

	/**
	 * Partager un dossier
	 * 
	 * @param idFolder
	 * @param idUser
	 * @throws java.rmi.RemoteException
	 */
	public void shareFolder(int idFolder, int idUser) throws RemoteException;

	/**
	 * Supprimer le droit d'acces d'un utilisateur sur un fichier
	 * 
	 * @param iddoc
	 * @param iduser
	 * @throws java.rmi.RemoteException
	 */
	public void deleteAccessDoc(int iddoc, int iduser) throws RemoteException;

	/**
	 * Supprimer le droit d'acces d'un utilisateur sur un dossier
	 * 
	 * @param idfol
	 * @param iduser
	 * @throws java.rmi.RemoteException
	 */
	public void deleteAccessFolder(int idfol, int iduser) throws RemoteException;

	/**
	 * Créer un dossier
	 * 
	 * @param nomfol
	 *            nom du dossier
	 * @param nomPere
	 *            nom du pere
	 * @throws java.rmi.RemoteException
	 * @return id du dossier créé
	 */
	public int createFolder(String nomfol, String nomPere) throws RemoteException;

	/**
	 * Supprimer un dossier
	 * 
	 * @param idfol
	 * @param chemin
	 * @throws java.rmi.RemoteException
	 */
	public void deleteFolder(int idfol, String chemin) throws RemoteException, SQLException;

	/**
	 * Affichage Information d'un dossier
	 * 
	 * @param nomFolder
	 * @throws java.rmi.RemoteException
	 */
	public void infoFolder(String nomFolder) throws RemoteException;

	/**
	 * Affichage Information d'acces à un dossier
	 * 
	 * @param idFolder
	 * @throws java.rmi.RemoteException
	 */
	public void getAccessFolder(int idFolder) throws RemoteException;

	/**
	 * Affichage Information d'acces à un fichier
	 * 
	 * @param idDoc
	 * @throws java.rmi.RemoteException
	 */
	public void getAccessDoc(int idDoc) throws RemoteException;

	/**
	 * Affichage Information dossier Racine
	 * 
	 * @param idFolder
	 * @throws java.rmi.RemoteException
	 */
	public void getRacineFolder(int idFolder) throws RemoteException;

	/**
	 * Affichage Information dossier fils d'un dossier
	 * 
	 * @param idFolder
	 * @throws java.rmi.RemoteException
	 */
	public void getFilsFolder(int idFolder) throws RemoteException;

}
