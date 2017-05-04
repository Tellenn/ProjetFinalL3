package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.TreeMap;

public interface ChatClientInt extends Remote {

	/**
	 * Envoie un message à l'interface du client
	 * 
	 * @param st
	 *            : message
	 * @throws RemoteException
	 */
	public void tell(String st) throws RemoteException;

	/**
	 * Ecrit tous les messages pas dans un TreeMap
	 * 
	 * @param st
	 * @throws RemoteException
	 */
	public void tell(TreeMap<String, String> st) throws RemoteException;

	/**
	 * @return le nom du client
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;

	/**
	 * @return l'id du client
	 * @throws RemoteException
	 */
	public int getId() throws RemoteException;

	public void setId(int i) throws RemoteException;

	/**
	 * retourne le nom du salon avec l'idSalon id
	 * 
	 * @param id
	 *            : idSalon
	 * @return
	 * @throws RemoteException
	 */
	public String getNomSalon(int id) throws RemoteException;

	/**
	 * ajoute un salon à un client
	 * 
	 * @param idSalon
	 * @param nomSalon
	 * @throws RemoteException
	 */
	public void addSalon(int idSalon, String nomSalon) throws RemoteException;
}
