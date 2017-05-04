package Client;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.rmi.Naming;

public class testclient {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
		 int choix;
		 	String ip = "127.0.0.1";
            Scanner sc = new Scanner(System.in) ;

		 	 System.out.println("Bonjour et bienvenue dans la version test du framework !");
             do {
                 System.out.println("Quelle partie du framework voulez-vous essayer ?");
                 System.out.println("1 : La gestion utilisateur,");
                 System.out.println("2 : Le chat,");
                 System.out.println("3 : La GED,");
                 System.out.println("4 : Le calendrier,");
                 System.out.println("0 : Quitter");
                 choix = sc.nextInt();
             } while( choix!=1 && choix!=2 && choix!=3 && choix!=4 && choix!=0);
             switch(choix){
             case 1 :
            	 break;
             case 2 :
            	 break;
             case 3 : 
            	 do {
            		 do {
		                 System.out.println("Vous avez choisi de tester la GED");
		                 System.out.println("Voici la liste des differentes fonction que nous pouvons vous proposer :");
		                 System.out.println("1 : Création d'un dossier,");
		                 System.out.println("2 : Upload d'un fichier,");
		                 System.out.println("3 : Telechargement d'un fichier,");
		                 System.out.println("4 : Récuperer info dossier,");
		                 System.out.println("5 : Récuperer les utilisateur ayant accès a un fichier,");
		                 System.out.println("6 : Récuperer les utilisateur ayant accès a un dossier,");
		                 System.out.println("7 : Récuperer l'id du père du dossier,");
		                 System.out.println("8 : Récuperer les id des fils du dossier,");
		                 System.out.println("9 : Partager un fichier avec un utilisateur,");
		                 System.out.println("10 : Partager un dossier avec un utilisateur,");
		                 System.out.println("11 : Supprimer l'accès à un document,");
		                 System.out.println("12 : Supprimer l'accès à un fichier,");
		                 System.out.println("13 : Supprimer un fichier");
		                 System.out.println("14 : Supprimer un dossier");
		                 System.out.println("15 : Un lancement de toute les fonctions");
		                 System.out.println("0 : Retour,");
            		 }while( choix<0 && choix>15);
            		 GEDServeurInt serverged = Client.connectGED(ip);
            		 switch(choix){
                     case 1 :
                    	 System.out.println("Pour pouvoir créer un dossier, entrez un nom :");
                    	 String nomfol = sc.next();
                    	 int idfol = Client.createFolder(serverged, nomfol);
                    	 System.out.println("L'id du nouveau dossier est "+idfol);
                    	 break;
                     case 2 :
                    	 System.out.println("Dans cet exemple, le fichier a transferer doit être dans votre bureau, il sera télécharger dans un dossier nommé \"GED\"");
                    	 System.out.println("Veuillez entrer le nom du fichier");
                    	 String nomfichier = sc.next();
                    	 String client = "Miage";
                    	 String home = "/home/p/perrink/";
                    	 int idfichier = Client.UploadFile(serverged, client, home, nomfichier);
                    	 System.out.println("L'id du nouveau dossier est "+idfichier);
                    	 break;
                     case 3 : 
                    	 break;
                     case 4 :
                    	 break;
                     case 5 :
                    	 break;
                     case 6 : 
                    	 break;
                     case 7 :
                    	 break;
                     case 8 :
                    	 break;
                     case 9 : 
                    	 break;
                     case 10 :
                    	 break;
                     case 11 :
                    	 break;
                     case 12 : 
                    	 break;
                     case 13 :
                    	 break;
                     case 14 :
                    	 break;
                     case 15 :
                    	 break;
                     default :
                    	 choix = 0;
            		 }
            	 }while(choix !=0);                 
            	 break;
             case 4 : 
            	 break;
             default :
                 System.out.println("Vous quittez l'application de test! Aurevoir!");

             
             
             }
                     
                     /*
         			 * 
         			 * PARTIE GED
         			 * 
         			 */
                     
		 	/*
		 	
			String home = "C:\\Users\\Kyllian\\Desktop\\";
			String file = "sabre.png";
			String cible = "";
			String client = "Kyllian";
			String nomfol = "RH";
			int idfol = 4;
			int iddoc = 5;
			int iduser = 1;

			GEDServeurInt serverged = Client.connectGED(ip);
			
			1 System.out.println("Debut créer dossier");
			idfol = Client.createFolder(serverged, nomfol);

			2 System.out.println("Debut upload file");
			iddoc = Client.UploadFile(serverged, client, home, file);

			3 System.out.println("Debut download file");
			Client.downloadFile(serverged, client, home, file, cible);

			4 System.out.println("Debut info dossier");
			Client.infoFolder(serverged, nomfol);

			5 System.out.println("Debut getAccessFolder");
			Client.getAccessFolder(serverged, idfol);

			6 System.out.println("Debut getAccessDoc");
			Client.getAccessDoc(serverged, iddoc);

			7 System.out.println("Debut getRacineFolder");
			Client.getRacineFolder(serverged, idfol);

			8 System.out.println("Debut getFilsFolder");
			Client.getFilsFolder(serverged, idfol);

			9 System.out.println("Debut Sharefile");
			Client.ShareFile(serverged, iddoc, iduser);

			10 System.out.println("Debut Sharefile");
			Client.ShareFolder(serverged, idfol, iduser);

			11 System.out.println("Debut deleteAcess doc");
			Client.deleteAccessDoc(serverged, iddoc, iduser);

			12 System.out.println("Debut deleteAcess folder");
			Client.deleteAccessFolder(serverged, idfol, iduser);

			13 System.out.println("Debut delete file");
			Client.DeleteFile(serverged, 4, home + file);

			14System.out.println("Debut delete folder");
			Client.DeleteFolder(serverged, idfol, home);*/
			
			
			/*
			 * 
			 * PARTIE CALENDRIER
			 * 
			 */
			/*
			CalServerInt servercal = Client.connectCal(ip);
			Client.afficherOneEventOrder(servercal, 5);
			Client.afficherEventsUserOrder(servercal, 5);
			Client.createEventOrder(servercal, 5, "test", "3-MAY-17 9:00:00", "3-MAY-17 10:00:00");
			Client.updateEventOrder(servercal, 1, "update event 1", "3-MAY-17 9:00:00", "3-MAY-17 10:00:00");
			Client.deleteEventOrder(servercal, 1);
			Client.addParticipantOrder(servercal, 6, 1);
			Client.deleteParticipantOrder(servercal, 6, 1);*/
		 	
		 	
		 	/*
			 * 
			 * PARTIE CHAT
			 * 
			 */		 	
		 	/*Client c = new Client();
		 	
		 	c.connectChat(ip);
		 	int idDestinataire = 9;
		 	c.sendTextPrivate("J'envoie un message", idDestinataire );		 	
		 	*/
		 	
		 	
		 	/*
			 * 
			 * PARTIE USER
			 * 
			 */
		 	/*Client client = new Client();
		 	String username = "aminc";
		 	String password = "toto";
		 	client.doConnect(username,password);
		 	client.ajoutUtilisateur(username, password);
		 	client.suppressionUtilisateur();
		 	client.ajoutAdmin();
		 	client.suppressionAdmin();
		 	
		 	client.chargementProfil();
		 	client.ajoutChamp("age");
		 	client.suppressionChamp("age");
		 	client.ajoutChamp("age", "20");
		 	client.modificationProfilParChamp("nom", "amin");
		 	client.suppressionChampPourUser("age");
		 	client.modificationPassword("toto");
		 	client.modificationUsername("aminc");
		 	client.modificationProfil();
		 	client.ajoutRelation(2);
		 	client.suppressionRelation(2);*/
		 	
		 	
		 	
	}
}
