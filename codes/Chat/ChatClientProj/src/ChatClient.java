import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
 
public class ChatClient  extends UnicastRemoteObject implements ChatClientInt{
	
	private String name;
	private int id;
	private ChatUI ui;	
	public ChatClient (String n) throws RemoteException {
		name=n;
	}
	
	public void tell(String st) throws RemoteException{
		System.out.println(st);
		ui.writeMsg(st);
	}
	
	public String getName() throws RemoteException{
		return name;
	}
	
	public int getId() throws RemoteException{
		return id;
	}
	
	public void setId(int i) throws RemoteException{
		id = i;
	}
	
	public void setGUI(ChatUI t){ 
		ui=t ; 
	} 	
}