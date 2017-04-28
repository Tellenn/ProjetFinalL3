import java.rmi.*;
 
public interface FileServerInt extends Remote{
 		
		public void setFile(String f, String path) throws RemoteException;
		
		public boolean sendData(FileClientInt c, String cible) throws RemoteException;
				
		public boolean receiveData(byte[] data, int len) throws RemoteException;
}