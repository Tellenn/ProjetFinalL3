import java.rmi.Naming;
import java.util.Scanner;

public class StartServer {
	private static String ipServ = new String("152.77.82.36");
	private static String home = new String("/home/p/perrink/Bureau/");
	private static String Client = new String("Kyllian");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length>0){
			System.out.println(args[0]);
			if(args[0].equals("1")){
				System.out.println("Mode serveur démarré");

				if(args.length>1){
					String entete = new String(home  );
					try{
						java.rmi.registry.LocateRegistry.createRegistry(1099);
						FileServer fs=new FileServer();
						fs.setFile(args[1],entete);
						Naming.rebind("rmi://"+ipServ+"/abc", fs);
						System.out.println("Le serveur est pret a opérer");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else if(args[0].equals("2")){
					
					
					
				System.out.println("Mode Download client démarré");
				try{
					FileClient c=new FileClient(Client);			
					FileServerInt server=(FileServerInt)Naming.lookup("rmi://"+ipServ+"/abc");
					if(args.length==2){
						server.setFile(args[1],home);
					}
					server.login(c,1);
					Scanner s=new Scanner(System.in);			
					while(s.hasNext()){
						String line=s.nextLine();
					}
					System.out.println("Fin du download client");
				}catch(Exception e){
					e.printStackTrace();
				}
			
			}else if(args[0].equals("3")){
					
					
					
				System.out.println("Mode Upload client démarré");
				try{
					FileClient c=new FileClient(Client);			
					FileServerInt server=(FileServerInt)Naming.lookup("rmi://"+ipServ+"/abc");
					if(args.length==2){
						server.setFile(args[1],home);
					}
					c.sendData(server, home, args[1]);
					System.out.println("Fin de l'upload client");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.println("Mode invalide");
			}
		}else{
			System.out.println("Nombres d'arguments invalide");
		}
	}	
}
