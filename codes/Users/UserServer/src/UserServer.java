import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


import oracle.net.aso.a;

import java.sql.*;

public class UserServer extends UnicastRemoteObject implements UserServerInt {

	private Vector v = new Vector();
	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "charroan";
	private static final String mdp = "Aclf2016";
	private Statement stmt;

	public UserServer() throws RemoteException {
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

		public boolean login(UserClientInt a) throws RemoteException, SQLException{	
		//System.out.println(a.getName() + "  got connected....");	
		////////////////////////////////////////
         Connection conn;
         Statement stmt;
         ResultSet rset;
         int resultat = 1;
         Scanner sc = new Scanner(System.in);
          System.out.println("Veuillez saisir un login :");
          String username = sc.nextLine();
          System.out.println("Veuillez saisir un mot de passe :");
          String password = sc.nextLine();
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
	        	 System.out.println("Profil existe ");
	        	 Utilisateur.chargerProfil(idUser);
	        	 
	        	// System.out.println("Modification des données: login? ");
	        	 //String userName = sc.nextLine();
	        	 //System.out.println("Modification des données: password? ");
	        	 //String mdp = sc.nextLine();
	        	 //Utilisateur.modifierUsername(idUser, userName);
	        	 //System.out.println("Modification de login done ");
	        	 //Utilisateur.modifierPassword(idUser, mdp);
	        	 //System.out.println("Modification de password done ");
	        	 
	        	 //System.out.println("Modification du Profil: nom? ");
	        	 //String nom = sc.nextLine();
	        	// System.out.println("Modification du Profil: prénom? ");
	        	// String prenom = sc.nextLine();
	        	// Utilisateur.modifierProfil(idUser, nom, prenom);
	        	// System.out.println("Modification du Profil done ");
	        	// Utilisateur.chargerProfil(idUser);
	        	 
	        	// Utilisateur.supprimerUser(idUser);
	        	 //System.out.println("Suppression du Profil done ");
	         }
	         else{
	        	 System.out.println("Vous n'avez pas de profil, voulez vous créer un? (O ou N)");
	        	
	        	 String Rep = sc.nextLine();
	        	 if (Rep.compareTo("O") == 0){
	        		 System.out.println("Veuillez choisir un username");
	        		 username = sc.nextLine();
	        		 System.out.println("Veuillez choisir un mot de passe");
	        		 password = sc.nextLine();
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
	///publish(a.getName()+ " has just connected.",idUser);
  	v.add(a);
	return true;
  	}	


	private void updateXML(String s) {
		// TODO Auto-generated method stub
		
	}

	public Vector getConnected() throws RemoteException {
		return v;
	}
}
	