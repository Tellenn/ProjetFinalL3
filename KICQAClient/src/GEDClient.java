import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
 
public class GEDClient  extends UnicastRemoteObject implements GEDClientInt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public  GEDClient(String n) throws RemoteException {
		super();
		name=n;
	}
	/**
	 * Permet de retrouver le nom du client
	 * @return le nom du client
	 */
	public String getName() throws RemoteException{
		return name;
	}
    
	/**
	 * Permet au client de recevoir des données
	 * @param filename nom du fichier transferé
	 * @param data un buffer
	 * @param len la taille du buffer
	 * @param cible le chemin où le fichier sera
	 * @return true si le fichier a bien été transféré
	 */
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

	/**
	 * Permet au clien d'envoyer un fichier
	 * @param server l'interface du serveur au quel on va envoyer le fichier
	 * @param path le chemin du fichier à transferer
	 * @param file nom du fichier a transfer
	 * @return true si le fichier à bien été transféré
	 */
	public int sendData(GEDServeurInt server, String path, String file) throws RemoteException {
		int idfile = 0;
		try{
			 File f1=new File(path+file);			 
			 FileInputStream in=new FileInputStream(f1);			 				 
			 byte [] mydata=new byte[1024*1024];						
			 int mylen=in.read(mydata);
			 while(mylen>0){
				 idfile = server.receiveData(mydata, mylen);	 
				 mylen=in.read(mydata);	
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		return idfile;
	}
}
