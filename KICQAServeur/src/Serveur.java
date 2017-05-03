import java.rmi.Naming;

public class Serveur {

	/**
	 * Permet de demarer le serveur GED
	 * @param ip l'ip du serveur
	 */
	public static void startGEDserver(String ip) {
		System.out.println("Demarrage du serveur GED");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("Port ok");
			GEDServeur fs=new GEDServeur();
			System.out.println("New server ok");
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur GED est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de demarer le serveur Calendrier
	 * @param ip l'ip du serveur
	 */
	public static void startCALserver(String ip) {
		System.out.println("Demarrage du serveur CAL");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1100);
			CalServer fs=new CalServer();
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur CAL est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de demarer le serveur Gestion utilisateurs
	 * @param ip l'ip du serveur
	 */
	public static void startUSERserver(String ip) {
		System.out.println("Demarrage du serveur USER");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1101);
			UserServer fs=new UserServer();
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur USER est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de demarer le serveur Chat
	 * @param ip l'ip du serveur
	 */
	public static void startCHATserver(String ip) {
		System.out.println("Demarrage du serveur CHAT");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1102);
			ChatServer fs=new ChatServer();
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur CHAT est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
