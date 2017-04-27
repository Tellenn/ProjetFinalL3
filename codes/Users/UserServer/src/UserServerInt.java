
import java.rmi.*;
import java.sql.SQLException;
import java.util.*;
 
public interface UserServerInt extends Remote{	
	public boolean login (UserClientInt a)throws RemoteException, SQLException ;
	public Vector getConnected() throws RemoteException ;
}