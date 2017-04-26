import java.rmi.*;
import java.util.*;
 
public interface ChatServerInt extends Remote{	
	public int login (ChatClientInt a, String login, String mdp)throws RemoteException ;
	public void publish (String s, int id )throws RemoteException ;
	public Vector getConnected() throws RemoteException ;
	public ChatClientInt getClient(int i) throws RemoteException;
	public void uploadConversation(ChatClientInt a, int idPersonne2)throws RemoteException;
}