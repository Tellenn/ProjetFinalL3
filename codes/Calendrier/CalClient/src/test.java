import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class test {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{			
		CalServerInt server=(CalServerInt)Naming.lookup("rmi://172.20.10.5/abc");
		//CalClient.afficherOrder(server, 5);
		//CalClient.createEventOrder(server, 5, "test", "3-MAY-17 9:00:00", "3-MAY-17 10:00:00");
		//CalClient.updateEventOrder(server, 1, "udate event 1", "3-MAY-17 9:00:00", "3-MAY-17 10:00:00");
		//CalClient.deleteEventOrder(server, 1);
		//CalClient.addParticipantOrder(server, 6, 1);
		CalClient.deleteParticipantOrder(server, 6, 1);
	}
}