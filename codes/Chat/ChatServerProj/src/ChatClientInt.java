
import java.rmi.*;
 
public interface ChatClientInt extends Remote{	
	public void tell (String name)throws RemoteException ;
	public String getName()throws RemoteException ;
	public int getId() throws RemoteException ;
	public void setId(int i) throws RemoteException;
}