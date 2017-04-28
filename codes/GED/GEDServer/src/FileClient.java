import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
 
public class FileClient  extends UnicastRemoteObject implements FileClientInt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public  FileClient(String n) throws RemoteException {
		super();
		name=n;
	}
 
	public String getName() throws RemoteException{
		return name;
	}
    
	public boolean receiveData(String filename, byte[] data, int len) throws RemoteException{
        try{
        	//A modifier pour pouvoir changer le lieu d'�criture
        	File f=new File("GED/"+filename);
        	f.createNewFile();
        	
        	FileOutputStream out=new FileOutputStream(f,true);
        	out.write(data,0,len);
        	out.flush();
        	out.close();
        	System.out.println("Done writing data...");
        }catch(Exception e){
        	e.printStackTrace();
        }  
		return true;
	}

	@Override
	public boolean sendData() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
