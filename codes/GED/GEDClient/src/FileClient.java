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
    
	public boolean receiveData(String filename, byte[] data, int len, String cible) throws RemoteException{
        try{
        	//A modifier pour pouvoir changer le lieu d'�criture
        	File f=new File(cible+filename);
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

	public boolean sendData(FileServerInt server, String path, String file) throws RemoteException {
		try{
			 File f1=new File(path+file);			 
			 FileInputStream in=new FileInputStream(f1);			 				 
			 byte [] mydata=new byte[1024*1024];						
			 int mylen=in.read(mydata);
			 while(mylen>0){
				 server.receiveData(mydata, mylen);	 
				 mylen=in.read(mydata);	
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		return false;
	}
}
