import java.rmi.*;
import java.rmi.server.*;
 
public class StartServer {
	public static void main(String[] args) {
		try {
				//System.setSecurityManager(new RMISecurityManager());
			 	java.rmi.registry.LocateRegistry.createRegistry(1099);
			 	
				UserServerInt b=new UserServer();	
				Naming.rebind("rmi://152.77.82.242/myabc", b);
				System.out.println("[System] User Server is ready.");
			}catch (Exception e) {
					System.out.println("User Server failed: " + e);
			}
	}
}