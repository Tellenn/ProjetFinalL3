import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.TreeMap;
 
public interface ChatClientInt extends Remote{	
	public void 	tell (String name)								 throws RemoteException ;
	public void 	tell(TreeMap<String, String> st) 				 throws RemoteException ;
	public String 	getName()										 throws RemoteException ;
	public int 		getId()											 throws RemoteException ;
	public void 	setId(int i) 									 throws RemoteException ;
	public int 		getNumSalon()									 throws RemoteException ;
	public void 	setNumSalon(int numSalon)						 throws RemoteException ;
	public int 		getIdConversationPrivee()						 throws RemoteException ;
	public void 	setIdConversationPrivee(int idConversationPrivee)throws RemoteException ;
	public String 	getNomSalon(int id) 						 	 throws RemoteException ;
	public void 	addSalon(int idSalon,String nomSalon) 			 throws RemoteException ;
}