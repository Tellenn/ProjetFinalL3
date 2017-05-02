import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

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
        System.out.println("-----------------------------------------");
        System.out.println("      		  	Calendrier	     		 ");
        System.out.println("-----------------------------------------");
        System.out.println("------------------ MENU -----------------");
        System.out.println("Tapez 1 pour créer un évènement");
        System.out.println("Tapez 2 pour modifier un évènement");
        System.out.println("Tapez 3 pour supprimer un évènement");
        System.out.println("Tapez 4 ajouter un participant à un évènement");
        System.out.println("Tapez 5 supprimer un participant à un évènement");
        System.out.println("-----------------------------------------");
        Scanner sc = new Scanner(System.in);
        Rep = sc.nextLine();
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
//		Date dateDebut = null;
//		Date dateFin = null;
		String dateDebut;
		String dateFin;
        String libelle;
		int idEvent;
		int idPart;
		int idUser;
		
        switch (Rep) {
        	case "1":
        		System.out.println("-----------------------------------------");
        		System.out.println("	  Création d'un nouvel évènement	 ");
        		System.out.println("-----------------------------------------");
        		System.out.println("Votre id ?");
	        	idUser = Integer.parseInt(sc.nextLine());
        		
        		System.out.println("Libelle de l'evenement ?");
	        	libelle = sc.nextLine();
	        	
	        	System.out.println("Date de debut de l'evenement ?");
//	        	try{
//	        	dateDebut = formatter.parse(sc.nextLine());
//		        } catch (ParseException e) {
//			    	e.printStackTrace();
//			    }
	        	dateDebut = sc.nextLine();
	        	
	        	System.out.println("Date de fin de l'evenement ?");
//			    try{
//			    	dateFin = formatter.parse(sc.nextLine());
//			    } catch (ParseException e) {
//			    	e.printStackTrace();
//			    }
	        	dateFin = sc.nextLine();
	        	
	        	Calendrier.createEvent(idUser, libelle, dateDebut, dateFin);
        		//Calendrier.afficher();
        		break;
        	
        	case "2":
        		System.out.println("-----------------------------------------");
        		System.out.println("	    Modification d'un évènement	     ");
        		System.out.println("-----------------------------------------");
        		System.out.println("id de l'evenement à modifier?");
        		idEvent = Integer.parseInt(sc.nextLine());
        		
        		System.out.println("Libelle de l'evenement ?");
	        	libelle = sc.nextLine();
	        	
	        	System.out.println("Date de debut de l'evenement ?");
//	        	try{
//	        	dateDebut = formatter.parse(sc.nextLine());
//		        } catch (ParseException e) {
//			    	e.printStackTrace();
//			    }
	        	dateDebut = sc.nextLine();
	        	
	        	System.out.println("Date de fin de l'evenement ?");
//			    try{
//			    	dateFin = formatter.parse(sc.nextLine());
//			    } catch (ParseException e) {
//			    	e.printStackTrace();
//			    }
	        	dateFin = sc.nextLine();
	        	
			    Calendrier.updateEvent(idEvent, libelle, dateDebut, dateFin);
			    break;
			    
        	case "3":
        		System.out.println("-----------------------------------------");
        		System.out.println("	    Supression d'un évènement	     ");
        		System.out.println("-----------------------------------------");
        		System.out.println("id de l'evenement à supprimer?");
        		idEvent = Integer.parseInt(sc.nextLine());
        		
        		Calendrier.deleteEvent(idEvent);
        		break;
        		
			case "4":
				System.out.println("-----------------------------------------");
        		System.out.println("  Ajouter un participant à un évènement  ");
        		System.out.println("-----------------------------------------");
        		System.out.println("id de l'evenement ?");
        		idEvent = Integer.parseInt(sc.nextLine());
        		System.out.println("id du participant ?");
        		idPart = Integer.parseInt(sc.nextLine());
        		
        		Calendrier.addParticipant(idEvent, idPart);
				break;
				
			case "5":
				System.out.println("-----------------------------------------");
        		System.out.println(" Supprimer un participant à un évènement ");
        		System.out.println("-----------------------------------------");
        		System.out.println("id de l'evenement ?");
        		idEvent = Integer.parseInt(sc.nextLine());
        		System.out.println("id du participant ?");
        		idPart = Integer.parseInt(sc.nextLine());
        		
        		Calendrier.deleteParticipant(idEvent, idPart);
				break;
				
			case "0":
				System.out.println("-----------------------------------------");
        		System.out.println(" 		  Afficher les événements		 ");
        		System.out.println("-----------------------------------------");
				System.out.println("Votre id ?");
	        	idUser = Integer.parseInt(sc.nextLine());
				Calendrier.afficher(idUser);
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
	