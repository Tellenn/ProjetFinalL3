import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
 
public interface CalClientInt extends Remote{	
	public void createEventOrder(String libelle, Date dateDebut, Date dateFin) throws RemoteException;
}