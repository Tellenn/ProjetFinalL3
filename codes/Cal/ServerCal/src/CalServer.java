
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.Date;

import oracle.net.aso.a;

import java.sql.*;

public class CalServer extends UnicastRemoteObject implements CalServerInt {

	private Vector v = new Vector();
	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "blondelq";
	private static final String mdp = "634714qB";
	private Statement stmt;

	public CalServer() throws RemoteException {
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public boolean login(CalClientInt a) throws RemoteException, SQLException{	
		
        Connection conn;
        Statement stmt;
        ResultSet rset;
        int resultat = 1;
        String Rep ="";
        System.out.println("    ******************************");
        System.out.println("    *  		  Calendrier	     *");
        System.out.println("    ******************************");

        System.out.println("------------------ MENU -----------------");
        System.out.println("Tapez 1 pour créer un évènement");
        System.out.println("Tapez 2 pour modifier un évènement");
        System.out.println("Tapez 3 pour supprimer un évènement");
        System.out.println("Tapez 4 ajouter un participant à un évènement");
        System.out.println("Tapez 5 supprimer un participant à un évènement");
        System.out.println("-----------------------------------------");
        Scanner sc = new Scanner(System.in);
        Rep = sc.nextLine();

        switch (Rep) {
        	case "1":
        		
        		System.out.println("Libelle de l'évènement ?");
	        	String libelle = sc.nextLine();
	        	System.out.println("Date de début de l'évènement ?");
	        	String dateDebut = sc.nextLine();
	        	System.out.println("Date de début de l'évènement ?");
	        	String dateFin = sc.nextLine();
	        	Calendrier
	        	modifierUsername(idUser, userName);
	        	System.out.println("Modification de login done ");
	        	Utilisateur.modifierPassword(idUser, mdp);
	        	System.out.println("Modification de password done ");
				break;
        		
        		int idUser = Calendrier.rechercheUtilisateur(a.getName());
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
	
	
//	private void createEvent(Date dateDebut, Date dateFin, String libelle){
//		
//	}

//	public void createEvent(Evenement e) throws RemoteException {
//		//System.out.println(a.getName() + " essaye de se connecter ....");
//		int resultat = 1;
//		ResultSet rset;
//		try {
//			rset = stmt.executeQuery(
//					"select 'la date d''aujourd''hui est '||" + "to_char (sysdate, 'DD/MM/YYYY')" + "from dual");
//			while (rset.next()) {
//				System.out.println(rset.getString(1));
//			}
////			rset = stmt.executeQuery("Select idUser from Utilisateur where username='" + a.getName() + "'");
////			while (rset.next()) {System.out.println(rset.getString(1));
////				//int id = Integer.valueOf(rset.getString(1));
////				//System.out.println(id);
////				//a.tell("Connexion accepté.");
////				// Carine devrat récup l'id client en chargeant le fichier
////				//publish(a.getName() + " est connecté.", id);
////				v.add(a);
////			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
////
//	// Le serveur recoit un message d'un client
//	public void publish(String s, int id) throws RemoteException {
//		// *** On envoie à tous les utilisateurs conecté le message
//		for (int i = 0; i < v.size(); i++) {
//			try {				
//				CalClientInt chatClient = (CalClientInt) v.get(i);
//				chatClient.tell(s);
//				
//			} catch (Exception e) {
//				// problem with the client not connected.
//				// Better to remove it
//			}
//		}
//		// *** On met à jour la conversation
//		// !!!!! Mettre le bon id2
//		ConversationPrivee c = new ConversationPrivee(id,2);
//		c.ajouterMessage(id, s);
//	}
//
//	private void updateXML(String s) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public Vector getConnected() throws RemoteException {
//		return v;
//	}

}
