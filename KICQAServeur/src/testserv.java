public class testserv {
	public static void main(String[] args){
	 String ip="127.0.0.1";
	 Serveur.startGEDserver(ip); 
	 Serveur.startCALserver(ip);
	 Serveur.startUSERserver(ip);
	 Serveur.startCHATserver(ip);
	}
}
