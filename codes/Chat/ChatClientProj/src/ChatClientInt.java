import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface ChatClientInt extends Remote{	
	public void 	tell (String name)								 throws RemoteException ;
	public String 	getName()										 throws RemoteException ;
	public int 		getId()											 throws RemoteException ;
	public void 	setId(int i) 									 throws RemoteException ;
	public int 		getNumSalon()									 throws RemoteException ;
	public void 	setNumSalon(int numSalon)						 throws RemoteException ;
	public int 		getIdConversationPrivee()						 throws RemoteException ;
	public void 	setIdConversationPrivee(int idConversationPrivee)throws RemoteException ;
}