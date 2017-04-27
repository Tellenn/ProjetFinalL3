import java.rmi.*;
import java.util.*;
 
public interface CalServerInt extends Remote{
	public void createEvent(Evenement event) throws RemoteException;	
}