import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;

public class testclient {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
		 	
		 	String ip = "127.0.0.1";
			String home = "C:\\Users\\Kyllian\\Desktop\\";
			String file = "sabre.png";
			String cible = "";
			String client = "Kyllian";
			String nomfol = "RH";
			int idfol = 4;
			int iddoc = 5;
			int iduser = 1;
/*
			GEDServeurInt serverged = Client.connectGED(ip);
			
			System.out.println("Debut créer dossier");
			idfol = Client.createFolder(serverged, nomfol);

			System.out.println("Debut upload file");
			iddoc = Client.UploadFile(serverged, client, home, file);

			System.out.println("Debut download file");
			Client.downloadFile(serverged, client, home, file, cible);

			System.out.println("Debut info dossier");
			Client.infoFolder(serverged, nomfol);

			System.out.println("Debut getAccessFolder");
			Client.getAccessFolder(serverged, idfol);

			System.out.println("Debut getAccessDoc");
			Client.getAccessDoc(serverged, iddoc);

			System.out.println("Debut getRacineFolder");
			Client.getRacineFolder(serverged, idfol);

			System.out.println("Debut getFilsFolder");
			Client.getFilsFolder(serverged, idfol);

			System.out.println("Debut Sharefile");
			Client.ShareFile(serverged, iddoc, iduser);

			System.out.println("Debut Sharefile");
			Client.ShareFolder(serverged, idfol, iduser);

			System.out.println("Debut deleteAcess doc");
			Client.deleteAccessDoc(serverged, iddoc, iduser);

			System.out.println("Debut deleteAcess folder");
			Client.deleteAccessFolder(serverged, idfol, iduser);

			System.out.println("Debut delete file");
			Client.DeleteFile(serverged, 4, home + file);

			System.out.println("Debut delete folder");
			Client.DeleteFolder(serverged, idfol, home);*/
			
			
			/*
			 * 
			 * PARTIE CALENDRIER
			 * 
			 */
			
			CalServerInt servercal = Client.connectCal(ip);
			Client.afficherOneEventOrder(servercal, 5);
			Client.afficherEventsUserOrder(servercal, 5);
			Client.createEventOrder(servercal, 5, "test", "3-MAY-17 9:00:00", "3-MAY-17 10:00:00");
			Client.updateEventOrder(servercal, 1, "update event 1", "3-MAY-17 9:00:00", "3-MAY-17 10:00:00");
			Client.deleteEventOrder(servercal, 1);
			Client.addParticipantOrder(servercal, 6, 1);
			Client.deleteParticipantOrder(servercal, 6, 1);
		}
}
