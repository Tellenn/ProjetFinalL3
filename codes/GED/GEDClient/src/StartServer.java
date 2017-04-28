import java.rmi.Naming;
import java.util.Scanner;
 
public class StartServer {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length>0){
			System.out.println(args[0]);
			if(args[0]=="1"){
				try{
					FileClient c=new FileClient("kyllian");			
		            //AdresseIP
					FileServerInt server=(FileServerInt)Naming.lookup("rmi://147.171.173.177/abc");
					server.login(c,1);
					System.out.println("Listening.....");			
					Scanner s=new Scanner(System.in);			
					while(s.hasNext()){
						String line=s.nextLine();
					}
					System.out.println("Fin du client");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else if(args[0].equals("2")){
				if(args.length>1){
					String entete = new String("C:\\Users\\kylli\\Desktop\\");
					try{
						
						java.rmi.registry.LocateRegistry.createRegistry(1099);
						
						FileServer fs=new FileServer();
			                        //Nom du fichier a transferer /!\ Crash si incorrect
						fs.setFile(args[1],entete);
			                        //Adresse IP
						
				
						Naming.rebind("rmi://147.171.173.177/abc", fs);
						System.out.println("Server will transfer "+args[1]+" to any client");
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					System.out.println("Mode invalide");
				}
			}else{
				System.out.println("Nombres d'arguments invalide, le mode 2 a besoin d'un argument supplementaire");
			}
		}else{
			System.out.println("Nombres d'arguments invalide");
		}
	}	
}
