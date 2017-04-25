import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import chat.ConversationPrivee;
import oracle.net.aso.a;

import java.sql.*;

public class ChatServer extends UnicastRemoteObject implements ChatServerInt {

	private Vector v = new Vector();
	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "charroan";
	private static final String mdp = "Aclf2016";
	private Statement stmt;

	public ChatServer() throws RemoteException {
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

		public boolean login(ChatClientInt a) throws RemoteException, SQLException{	
		//System.out.println(a.getName() + "  got connected....");	
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
	         } catch (ClassNotFoundException | SQLException e) {
	      		// TODO Auto-generated catch block
	      		e.printStackTrace();
	      	}
	         //recupère l'idUser
	         int idUser = Utilisateur.rechercheUtilisateur(a.getName());
	         if (idUser != 0){
	        	 Utilisateur.chargerProfil(idUser);
	         }
	         else{
	        	 System.out.println("Vous n'avez pas de profil, voulez vous créer un? (O ou N)");
	        	 Scanner sc = new Scanner(System.in);
	        	 String Rep = sc.nextLine();
	        	 if (Rep.compareTo("O") == 0){
	        		 System.out.println("Veuillez choisir un username");
	        		 String username = sc.nextLine();
	        		 System.out.println("Veuillez choisir un mot de passe");
	        		 String mdp = sc.nextLine();
	        		 idUser = Utilisateur.calcul_idUser();
	        		 Utilisateur.ajouterUtilisateur(idUser, username, mdp);
	        		 
	        		 System.out.println("Veuillez entrer votre nom");
	        		 String nom = sc.nextLine();
	        		 System.out.println("Veuillez entrer votre prénom");
	        		 String prenom = sc.nextLine();	        		 
	        		 Utilisateur.ajouterProfil(idUser, nom, prenom);
	        		 
	        	 }else{
	        		 System.out.println("Connexion impossible");
	        		 return false;
	        	 }
	        	 
 
	         }
 
		
  	////////////////////////////////////////
  	a.tell("You have Connected successfully.");
	publish(a.getName()+ " has just connected.",idUser);
  	v.add(a);
	return true;
  	}	

	// Le serveur recoit un message d'un client
	public void publish(String s, int id) throws RemoteException {
		// *** On envoie à tous les utilisateurs conecté le message
		for (int i = 0; i < v.size(); i++) {
			try {				
				ChatClientInt chatClient = (ChatClientInt) v.get(i);
				chatClient.tell(s);
				
			} catch (Exception e) {
				// problem with the client not connected.
				// Better to remove it
			}
		}
		// *** On met à jour la conversation
		// !!!!! Mettre le bon id2
		ConversationPrivee c = new ConversationPrivee(id,3);
		c.ajouterMessage(id, s);
	}

	private void updateXML(String s) {
		// TODO Auto-generated method stub
		
	}

	public Vector getConnected() throws RemoteException {
		return v;
	}
}
	