
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
 
public class UserClient  extends UnicastRemoteObject implements UserClientInt{
	
	private String name;
	private int id;
	private UserUI ui;	
	public UserClient (String n) throws RemoteException {
		name=n;
		}
	
	public void tell(String st) throws RemoteException{
		System.out.println(st);
		ui.writeMsg(st);
	}
	
	public String getName() throws RemoteException{
		return name;
	}
	
	public void setGUI(UserUI t){ 
		ui=t ; 
	} 	
}