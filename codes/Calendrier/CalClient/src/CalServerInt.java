import java.rmi.*;
import java.util.*;
 
public interface CalServerInt extends Remote{	
	public boolean login (CalClientInt a)throws RemoteException ;
	public void publish (String s, int id )throws RemoteException ;
	public Vector getConnected() throws RemoteException ;
}