import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
 
 
public class FileServer  extends UnicastRemoteObject implements FileServerInt {
	
	private String file="";	
	private String path="";
	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "perrink";
	private static final String mdp = "Kalayoda1";
	private Statement stmt;
	protected FileServer() throws RemoteException {
		super();
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// TODO Auto-generated constructor stub
	}
 
	public void setFile(String f, String path){
		file=f;
		this.path=path;
	}
	
	public boolean login(FileClientInt c, int order) throws RemoteException{
		switch(order){
		case 1 :
			try{
				sendData(c);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
    public static void commit() throws SQLException {
        
    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "perrink", "Kalayoda1");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("commit");
        st.close();
       
    };
	
	public boolean AjoutDoc(String nomDoc, String chemin) throws SQLException{
		boolean bool = true;
		ResultSet res;
		System.out.println("Requète 1 début");
		res=stmt.executeQuery("select max(idFichier) from Fichier");
		System.out.println("Requète 1 fin");
		res.next();
		System.out.println("Resultat : "+res.getInt(1));
		int idfichier=1+res.getInt(1);
		System.out.println("Début de la requete 2");
		stmt.executeUpdate("INSERT INTO Fichier VALUES("+idfichier+",'"+nomDoc+"',1,'ma tronche',"+null+")");
		System.out.println("Requète 2 fin");
		String[] dossiers=chemin.split("/");
		System.out.println("Requète 3 début");
		res=stmt.executeQuery("select idDossier from Dossier where nomDossier='"+dossiers[dossiers.length-1]+"'");
		System.out.println("Requète 3 fin");
		res.next();
		int idpere = res.getInt(1);
		System.out.println("Resultat reqete 3 = "+idpere+". Début de la requète 4");
		stmt.executeUpdate("INSERT INTO FichierDansDossier VALUES("+idfichier+","+idpere+")");
		System.out.println("Requète 4 fin");
		commit();
		
		
		return bool;
	}

	@Override
	public boolean sendData(FileClientInt c) throws RemoteException {
		try{
			 File f1=new File(path+file);			 
			 FileInputStream in=new FileInputStream(f1);			 				 
			 byte [] mydata=new byte[1024*1024];						
			 int mylen=in.read(mydata);
			 while(mylen>0){
				 c.receiveData(f1.getName(), mydata, mylen);	 
				 mylen=in.read(mydata);	
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		return false;
	}

	@Override
	public boolean receiveData(String filename, byte[] data, int len) throws RemoteException {
		//this.AjoutDoc(file,"RH");
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
}
