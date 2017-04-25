import java.rmi.*;
import java.util.*;
 
public interface ChatServerInt extends Remote{	
	public boolean login (ChatClientInt a)throws RemoteException ;
	public void publish (String s, int id )throws RemoteException ;
	public Vector getConnected() throws RemoteException ;
}