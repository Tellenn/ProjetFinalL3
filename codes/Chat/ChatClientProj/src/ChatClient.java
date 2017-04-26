import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
 
public class ChatClient  extends UnicastRemoteObject implements ChatClientInt{
	
	private String 	name;
	private int 	id;
	private ChatUI 	ui;	
	private int 	numSalon 				= -1;
	private int 	idConversationPrivee 	= -1;
	
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
	
	public int getNumSalon() {
		return numSalon;
	}

	public void setNumSalon(int numSalon) {
		this.numSalon = numSalon;
		this.idConversationPrivee = -1;
	}

	public int getIdConversationPrivee() {
		return idConversationPrivee;
	}

	public void setIdConversationPrivee(int idConversationPrivee) {
		this.idConversationPrivee = idConversationPrivee;
		this.numSalon = -1;
	}

	public void setGUI(ChatUI t){ 
		ui=t ; 
	}
	
}