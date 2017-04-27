import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

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
		
         Connection conn;
         Statement stmt;
         ResultSet rset;
         int resultat = 1;
         String Rep ="";
         System.out.println("    ******************************");
         System.out.println("    *  Gestion des utilisateurs  *");
         System.out.println("    ******************************");
         System.out.println("------------------ MENU -----------------");
         System.out.println("Tapez 1 pour se connecter");
         System.out.println("Tapez 2 pour s'ajouter dans la base de données");
         System.out.println("-----------------------------------------");
         Scanner sc = new Scanner(System.in);
         Rep = sc.nextLine();

         switch (Rep) {
         	case "1":
         		
         		int idUser = Utilisateur.rechercheUtilisateur(a.getName());
		        if (idUser != 0){
		        	 System.out.println("Profil existe ");
		        	 while(Rep.compareTo("exit") != 0){

		                 System.out.println("    ******************************");
		                 System.out.println("    *  Gestion des utilisateurs  *");
		                 System.out.println("    ******************************");

		                 System.out.println("------------------ MENU -----------------");
		                 System.out.println("Tapez 1 pour charger votre profil");
		                 System.out.println("Tapez 2 pour modifier votre profil");
		                 System.out.println("Tapez 3 pour modifier vos données");
		                 System.out.println("Tapez 4 pour supprimer vos données et votre profil");
		                 System.out.println("Tapez 5 pour ajouter un champ");
		                 System.out.println("Tapez 6 pour supprimer un champ");
		                 System.out.println("-----------------------------------------");

		                 System.out.print(">");
		                 Rep = sc.nextLine();

			             switch (Rep) {
			                 case "1":
						         Utilisateur.chargerProfil(idUser);
						         break;					         
					        
				          	////////////////////////////////////////
				             case "2":
				            	 
					        	 Utilisateur.modifierProfil(idUser);
					        	 System.out.println("Modification du Profil done ");
					        	 Utilisateur.chargerProfil(idUser);
					        	 break;
					        	 
							////////////////////////////////////////
							case "3":
								System.out.println("Modification des données: login? ");
					        	String userName = sc.nextLine();
					        	System.out.println("Modification des données: password? ");
					        	String mdp = sc.nextLine();
					        	Utilisateur.modifierUsername(idUser, userName);
					        	System.out.println("Modification de login done ");
					        	Utilisateur.modifierPassword(idUser, mdp);
					        	System.out.println("Modification de password done ");
								break;
								
							////////////////////////////////////////
							case "4":
								Utilisateur.supprimerUser(idUser);
					        	System.out.println("Suppression du Profil done ");
					        	break;
					        ////////////////////////////////////////
							case "5":
								System.out.println("Quelle est le champ que vous voulez ajouter? ");
					        	String champ = sc.nextLine();
					        	System.out.println("et son content? ");
					        	String content = sc.nextLine();
								Utilisateur.ajouterChamp(champ, idUser, content);
					        	System.out.println("ajout du champ done ");
					        	break;
						        ////////////////////////////////////////
							case "6":
								System.out.println("Quelle est le champ que vous voulez supprimer ");
								champ = sc.nextLine();
						        Utilisateur.supprimerChamp(champ, idUser);
						        break;
				         }
			            
		        	 }
		        }else{
			       System.out.println("Vous n'avez pas de profil.");		        	 
		        }
			             //////////////////////////////////////////////////////////////////////////////////////
		             case "2":
	                     
			        	 System.out.println("Vous n'avez pas de profil, voulez vous créer un? (O ou N)");
			        	
			        	  Rep = sc.nextLine();
				        	 if (Rep.compareTo("O") == 0){
				        		 System.out.println("Veuillez choisir un username");
				        		 String username = sc.nextLine();
				        		 System.out.println("Veuillez choisir un mot de passe");
				        		 String password = sc.nextLine();
				        		 idUser = Utilisateur.calcul_idUser();
				        		 Utilisateur.ajouterUtilisateur(idUser, username, password);
				        		 
				        		 System.out.println("Veuillez entrer votre nom");
				        		 String nom = sc.nextLine();
				        		 System.out.println("Veuillez entrer votre prénom");
				        		 String prenom = sc.nextLine();	        		 
				        		 Utilisateur.ajouterProfil(idUser, nom, prenom);
				        		 Utilisateur.chargerProfil(idUser);
				        		 
			        	 }else{
			        		 System.out.println("Connexion impossible");
			        		 return false;
			        	 }
			        	break;
			        	
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
	