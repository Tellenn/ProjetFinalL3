import java.rmi.RemoteException;

public interface CalClientInt {
	public void afficherOneEventOrder(int idEvenement) throws RemoteException;
	public void afficherEventsUserOrder(CalServerInt server, int idUser) throws RemoteException;
	public void createEventOrder(CalServerInt server, int idUser, String libelle, String dateDebut, String dateFin) throws RemoteException;
	public void updateEventOrder(CalServerInt server, int idEvent, String libelle, String dateDebut, String dateFin) throws RemoteException;
	public void deleteEventOrder(CalServerInt server, int idEvent) throws RemoteException;
	public void addParticipantOrder(CalServerInt server, int idEvent, int idParticipant) throws RemoteException;
	public void deleteParticipantOrder(CalServerInt server, int idEvent, int idParticipant) throws RemoteException;
}
