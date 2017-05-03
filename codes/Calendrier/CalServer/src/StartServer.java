import java.rmi.Naming;

public class StartServer {

	/**
	 * Demarrage server
	 */
	public static void start(String ipServ) {
		System.out.println("Demarage du serveur");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("Port ok");
			CalServer cal=new CalServer();
			System.out.println("Création serveur ok");
			Naming.rebind("rmi://172.20.10.5/abc", cal);//"+ipServ+"
			System.out.println("Le serveur est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
