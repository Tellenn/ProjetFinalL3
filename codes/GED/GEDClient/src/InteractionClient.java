import java.rmi.Naming;
import java.util.Scanner;

public class InteractionClient {

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
