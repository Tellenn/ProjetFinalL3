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
	

    public static void commit() throws SQLException {
        
    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "perrink", "Kalayoda1");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("commit");
        st.close();
       
    };
	
	public boolean AjoutDoc(String nomDoc, String chemin) throws SQLException{
		boolean bool = true;
		ResultSet res;
		res=stmt.executeQuery("select max(idFichier) from Fichier");
		res.next();
		int idfichier=1+res.getInt(1);
		stmt.executeUpdate("INSERT INTO Fichier VALUES("+idfichier+",'"+nomDoc+"',1,'ma tronche',"+null+")");
		String[] dossiers=chemin.split("/");
		res=stmt.executeQuery("select idDossier from Dossier where nomDossier='"+dossiers[dossiers.length-1]+"'");
		res.next();
		int idpere = res.getInt(1);
		stmt.executeUpdate("INSERT INTO FichierDansDossier VALUES("+idfichier+","+idpere+")");
		commit();
		
		
		return bool;
	}

	@Override
	public boolean sendData(FileClientInt c,String cible) throws RemoteException {
		try{
			 File f1=new File(path+file);			 
			 FileInputStream in=new FileInputStream(f1);			 				 
			 byte [] mydata=new byte[1024*1024];						
			 int mylen=in.read(mydata);
			 while(mylen>0){
				 c.receiveData(f1.getName(), mydata, mylen,cible);	 
				 mylen=in.read(mydata);	
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		return false;
	}

	public boolean receiveData(byte[] data, int len) throws RemoteException {
		try{
			File f=new File("GED/"+file);
		    f.createNewFile();
		    FileOutputStream out=new FileOutputStream(f,true);
	        out.write(data,0,len);
	        out.flush();
	        out.close();
	        System.out.println("Done writing data...");
	        //this.AjoutDoc(file,"RH");
	        }catch(Exception e){
	        	e.printStackTrace();
	        }  
		return true;
	}
}
