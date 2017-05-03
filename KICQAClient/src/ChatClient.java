import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

public class ChatClient extends UnicastRemoteObject implements ChatClientInt {

	private String name;
	private int id ;
	private Client client;
	private TreeMap<Integer, String> salons = new TreeMap<>();

	public ChatClient(String n, Client c) throws RemoteException {
		name = n;
		client = c;
	}
		
	public void tell(String st) throws RemoteException {
		System.out.println(st);	
		client.writeMsg(st);
	}
	
	public void tell(TreeMap<String, String> st) throws RemoteException {
		client.writeMsg(st.get("date") + " - " + st.get("heure") + ":" + "[" + st.get("nom") + "]" + st.get("text"));
	}

	public String getName() throws RemoteException {
		return name;
	}

	public int getId() throws RemoteException {
		return id;
	}

	public void setId(int i) throws RemoteException {
		id = i;
	}

	public String getNomSalon(int id) throws RemoteException {
		return salons.get(id);
	}
	
	public TreeMap<Integer, String> getSalons() throws RemoteException {
		return salons;
	}

	public void addSalon(int idSalon,String nomSalon) throws RemoteException {
		salons.put(idSalon,nomSalon);
	}
}