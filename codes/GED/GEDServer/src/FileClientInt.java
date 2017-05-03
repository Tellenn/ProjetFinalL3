import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileClientInt extends Remote {

	public boolean receiveData(String filename, byte[] data, int len, String cible) throws RemoteException;

	public int sendData(FileServerInt server, String path, String file) throws RemoteException;

	public String getName() throws RemoteException;
}
