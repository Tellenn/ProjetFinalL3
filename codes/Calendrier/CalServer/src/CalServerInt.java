
import java.rmi.*;
import java.sql.SQLException;
import java.util.*;
 
public interface CalServerInt extends Remote{	
	public boolean login (CalClientInt a)throws RemoteException, SQLException ;
	public Vector getConnected() throws RemoteException ;
}