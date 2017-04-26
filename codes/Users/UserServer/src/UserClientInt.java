
import java.rmi.*;
 
public interface UserClientInt extends Remote{	
	public void tell (String name)throws RemoteException ;
	public String getName()throws RemoteException ;
}