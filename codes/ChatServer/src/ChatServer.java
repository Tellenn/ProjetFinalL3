
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.sql.*;
 
public class ChatServer  extends UnicastRemoteObject implements ChatServerInt{
	
	private Vector v=new Vector();	
    private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
    private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
    private static final String login = "charroan";
    private static final String mdp = "MOT DE PASSE ";
    
	public ChatServer() throws RemoteException{}
		
	public boolean login(ChatClientInt a) throws RemoteException{	
		System.out.println(a.getName() + "  got connected....");	
		////////////////////////////////////////
         Connection conn;
         Statement stmt;
         ResultSet rset;
         int resultat = 1;

         /* System.out.println("Veuillez saisir un login :");
          username = sc.nextLine();
          System.out.println("Veuillez saisir un mot de passe :");
          password = sc.nextLine();*/
         try {
			Class.forName(jdbcDriver);
		
	         conn = DriverManager.getConnection(dbUrl,login, mdp);
	         stmt = conn.createStatement();
	         rset = stmt.executeQuery(
	                 "select 'la date d''aujourd''hui est '||"
	                 + "to_char (sysdate, 'DD/MM/YYYY')"
	                 + "from dual");
	         while (rset.next()) {
	             System.out.println(rset.getString(1));
	         }
	         rset = stmt.executeQuery(
	                 "select idUser from Utilisateur where username='"+a.getName() +"'");
	         while (rset.next()) {
	             System.out.println(rset.getString(1));
	         }
	         System.out.println(" lalalal");
         } catch (ClassNotFoundException | SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
		
		////////////////////////////////////////
		a.tell("You have Connected successfully.");
		publish(a.getName()+ " has just connected.");
		v.add(a);
		return true;		
	}
	
	public void publish(String s) throws RemoteException{
	    System.out.println(s);
		for(int i=0;i<v.size();i++){
		    try{
		    	ChatClientInt tmp=(ChatClientInt)v.get(i);
		    	tmp.tell(s);
		    }catch(Exception e){
		    	//problem with the client not connected.
		    	//Better to remove it
		    }
		}
	}
 
	public Vector getConnected() throws RemoteException{
		return v;
	}
}