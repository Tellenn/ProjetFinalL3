import java.rmi.*;
import java.util.*;
 
public interface UserServerInt extends Remote{	
	public boolean login (UserClientInt a)throws RemoteException ;
	public void publish (String s, int id )throws RemoteException ;
	public Vector getConnected() throws RemoteException ;
}