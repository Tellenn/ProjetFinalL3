import java.rmi.*;
import java.util.*;
 
public interface ChatServerInt extends Remote{	
	public int 									login 				(ChatClientInt a, String login, String mdp)	throws RemoteException ;
	public void 								publish 			(String s,int i)							throws RemoteException ;
	public void 								publishPrivate		(String s, int id1, int id2 ) 				throws RemoteException ;
	public TreeMap<Integer, ChatClientInt> 		getConnected		() 											throws RemoteException ;
	public ChatClientInt 						getClient			(int i) 									throws RemoteException ;
	public void 								uploadConversation	(ChatClientInt a, int idPersonne2)			throws RemoteException ;
	public void 								uploadSalon			(ChatClientInt a)							throws RemoteException ;
}