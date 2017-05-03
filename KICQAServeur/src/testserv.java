public class testserv {
	public static void main(String[] args){
	System.out.print("jezi");
	 String ip="127.0.0.1";
	 Serveur.startGEDserver(ip); 
	 Serveur.startCALserver(ip);
	 Serveur.startUSERserver(ip);
	 Serveur.startCHATserver(ip);
	}
}
