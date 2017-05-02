import java.rmi.Naming;

public class StartServer {

	/**
	 * Permet de demarer le serveur GED
	 * @param ipServ l'ip du serveur qui sera également utilisé par les clients
	 */
	public static void start(String ipServ) {
		System.out.println("Demarage du serveur");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("Port ok");
			FileServer fs=new FileServer();
			System.out.println("Création serveur ok");
			Naming.rebind("rmi://152.77.82.30/abc", fs);//"+ipServ+"
			System.out.println("Le serveur est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
