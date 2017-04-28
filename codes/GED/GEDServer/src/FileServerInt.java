import java.rmi.*;
 
public interface FileServerInt extends Remote{
 
		public boolean login(FileClientInt c, int order) throws RemoteException;
		
		public boolean sendData(FileClientInt c) throws RemoteException;
		
		public boolean receiveData(String filename, byte[] data, int len) throws RemoteException;
}