
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
 
public class CalClient  extends UnicastRemoteObject implements CalClientInt{
	
	private String name;
	private int id;
	private CalUI ui;	
	public CalClient (String n) throws RemoteException {
		name=n;
		}
	
	public void tell(String st) throws RemoteException{
		System.out.println(st);
		ui.writeMsg(st);
	}
	
	public String getName() throws RemoteException{
		return name;
	}
	
	public void setGUI(CalUI t){ 
		ui=t ; 
	} 	
}