
import java.rmi.*;
 
public interface UserClientInt extends Remote{	
	public void tell (String name)throws RemoteException ;
	public String getName()throws RemoteException ;
	public int getId() throws RemoteException ;
	public void setId(int idUser)throws RemoteException;
	public void setName(String userName)throws RemoteException;
	public String getMdp()throws RemoteException;
	public void setMdp(String mdp)throws RemoteException;
}