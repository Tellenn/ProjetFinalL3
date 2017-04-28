import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface FileClientInt extends Remote{
 
	public boolean receiveData(String filename, byte[] data, int len) throws RemoteException;
	
	public boolean sendData() throws RemoteException;
	
	public String getName() throws RemoteException;
}