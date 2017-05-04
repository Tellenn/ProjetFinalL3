package Client;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Antoine
 *
 */
public class ChatClient extends UnicastRemoteObject implements ChatClientInt {

	private String name;
	private int id ;
	private Client client;
	private TreeMap<Integer, String> salons = new TreeMap<>();

	
	public ChatClient(String n, Client c) throws RemoteException {
		name = n;
		client = c;
	}
		
	/** Envoie un message à l'interface du client
	 * @param st : message
	 * @throws RemoteException
	 */
	public void tell(String st) throws RemoteException {
		System.out.println(st);	
		client.writeMsg(st);
	}
	
	/** Ecrit tous les messages pas dans un TreeMap
	 * @param st
	 * @throws RemoteException
	 */
	public void tell(TreeMap<String, String> st) throws RemoteException {
		client.writeMsg(st.get("date") + " - " + st.get("heure") + ":" + "[" + st.get("nom") + "]" + st.get("text"));
	}

	/**
	 * @return le nom du client
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException {
		return name;
	}

	/**
	 * @return l'id du client
	 * @throws RemoteException
	 */
	public int getId() throws RemoteException {
		return id;
	}

	public void setId(int i) throws RemoteException {
		id = i;
	}

	/** retourne le nom du salon avec l'idSalon id
	 * @param id : idSalon
	 * @return
	 * @throws RemoteException
	 */
	public String getNomSalon(int id) throws RemoteException {
		return salons.get(id);
	}
	
	/** retourne les salons d'un client
	 * @param idSalon
	 * @param nomSalon
	 * @throws RemoteException
	 */
	public TreeMap<Integer, String> getSalons() throws RemoteException {
		return salons;
	}

	/** ajoute un salon à un client
	 * @param idSalon
	 * @param nomSalon
	 * @throws RemoteException
	 */
	public void addSalon(int idSalon,String nomSalon) throws RemoteException {
		salons.put(idSalon,nomSalon);
	}
}
