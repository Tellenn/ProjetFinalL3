
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
 
public class UserClient  extends UnicastRemoteObject implements UserClientInt{
	
	private String name;
	private int id=-1;
	private String password;
	private Client ui;	
	public UserClient (String n, String mdp) throws RemoteException {
		name=n;
		password = mdp;
		}
	
	/*public void tell(String st) throws RemoteException{
		System.out.println(st);
		ui.writeMsg(st);
	}*/
	
	public String getName() throws RemoteException{
		return name;
	}
	
	public void setGUI(Client t){ 
		ui=t ; 
	}

	@Override
	public int getId() throws RemoteException{
		return id;
	}

	@Override
	public void setId(int idUser) throws RemoteException{
		id = idUser;
		
	}

	@Override
	public void setName(String userName) throws RemoteException{
		name = userName;
		
	}

	@Override
	public String getMdp() throws RemoteException {
		return password;
	}

	@Override
	public void setMdp(String mdp) throws RemoteException {
		password = mdp;
		
	} 

}