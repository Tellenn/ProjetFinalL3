import java.rmi.Naming;
import java.util.Scanner;

public class StartServer {

	/**
	 * Permet de demarer le serveur GED
	 * @param ipServ l'ip du serveur qui sera également utilisé par les clients
	 */
	public static void start(String ipServ) {
		System.out.println("Serveur démarré");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			FileServer fs=new FileServer();
			Naming.rebind("rmi://"+ipServ+"/abc", fs);
			System.out.println("Le serveur est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
