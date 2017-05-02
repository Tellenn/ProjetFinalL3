import java.rmi.Naming;
import java.util.Scanner;

public class InteractionClient {

	/**
	 * Télécharge depuis le serveur le fichier cible pour le client
	 * @param ipServ l'ip du serveur cible
	 * @param client le nom du client
	 * @param home l'endroit où est rangé le fichier dans le serveur
	 * @param file le nom du fichier
	 * @param cible l'endroit où le fichier doit être téléchargé
	 */
	public static void downloadFile(String ipServ, String client, String home, String file, String cible) {
		System.out.println("Mode Download client démarré");
		try{
			FileClient c=new FileClient(client);			
			FileServerInt server=(FileServerInt)Naming.lookup("rmi://"+ipServ+"/abc");
			server.setFile(file,home);
			server.sendData(c,cible);
			/*Scanner s=new Scanner(System.in);			
			while(s.hasNext()){
				String line=s.nextLine();
			}*/
			System.out.println("Fin du download client");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
			
	
	/**
	 * Permet à un client d'upload la sur la GED un fichier
	 * @param ipServ ip du serveur
	 * @param client nom du client
	 * @param home endroit où est rengé le serveur
	 * @param file nom du fichier
	 */
	public static void UploadFile(String ipServ, String client, String home, String file) {	
					
		System.out.println("Mode Upload client démarré");
		try{
			FileClient c=new FileClient(client);			
			FileServerInt server=(FileServerInt)Naming.lookup("rmi://"+ipServ+"/abc");
			//server.setFile(file,home);
			c.sendData(server, home, file);
			System.out.println("Fin de l'upload client");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}
