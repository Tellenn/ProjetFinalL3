import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
 
public class Evenement  extends UnicastRemoteObject implements CalClientInt{
	
	private String libelle;
	private Date dateDebut;
	private Date dateFin;
	
	public Evenement (String libelle, Date dateDebut, Date dateFin) throws RemoteException {
		this.libelle = libelle;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	
}