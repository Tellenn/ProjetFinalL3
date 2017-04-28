import java.rmi.Naming;
import java.util.Scanner;

public class StartServer {

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
