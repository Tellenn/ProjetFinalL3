import java.rmi.Naming;
 
public class StartFileServer {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			
			FileServer fs=new FileServer();
                        //Nom du fichier a transferer
			fs.setFile("/home/p/perrink/Bureau/me.jpg");	
                        //Adresse IP
			Naming.rebind("rmi://152.77.82.229/abc", fs);
			System.out.println("File Server is Ready");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}