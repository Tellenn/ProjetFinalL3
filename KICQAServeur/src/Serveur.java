import java.rmi.Naming;

public class Serveur {
	static String ip;
	
	public void Serveur(String ip){
		Serveur.ip=ip;
	}

	/**
	 * Permet de demarer le serveur GED
	 * @param ipServ l'ip du serveur qui sera également utilisé par les clients
	 */
	public static void start() {
		System.out.println("Demarage du serveur");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("Port ok");
			GEDServeur fs=new GEDServeur();
			System.out.println("Création serveur ok");
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
