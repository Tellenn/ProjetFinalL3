import java.rmi.Naming;

public class Serveur {
	static String ip;
	
	public void Serveur(String ip){
		Serveur.ip=ip;
	}

	/**
	 * Permet de demarer le serveur GED
	 */
	public static void startGEDserver() {
		System.out.println("Demarage du serveur GED");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(2220);
			GEDServeur fs=new GEDServeur();
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur GED est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de demarer le serveur Calendrier
	 */
	public static void startCALserver() {
		System.out.println("Demarage du serveur CAL");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(2221);
			CalServer fs=new CalServer();
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur CAL est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de demarer le serveur Gestion utilisateurs
	 */
	public static void startUSERserver() {
		System.out.println("Demarage du serveur USER");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(2222);
			UserServer fs=new UserServer();
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur USER est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de demarer le serveur Chat
	 */
	public static void startCHATserver() {
		System.out.println("Demarage du serveur CHAT");
		try{
			java.rmi.registry.LocateRegistry.createRegistry(2223);
			ChatServer fs=new ChatServer();
			Naming.rebind("rmi://"+ip+"/abc", fs);
			System.out.println("Le serveur CHAT est pret a opérer");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
