import java.rmi.Naming;
 
public class StartFileServer {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String entete = new String("C:\\Users\\kylli\\Desktop\\");
		try{
			
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			
			FileServer fs=new FileServer();
                        //Nom du fichier a transferer /!\ Crash si incorrect
			fs.setFile(args[0],entete);
                        //Adresse IP
			
	
			Naming.rebind("rmi://147.171.173.3/abc", fs);
			System.out.println("Server will transfer "+args[0]+" to any client");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}
