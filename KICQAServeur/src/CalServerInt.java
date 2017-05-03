import java.rmi.*;
import java.sql.SQLException;

public interface CalServerInt extends Remote{
	public void afficherOneEvent(int idEvenement) throws SQLException, RemoteException;
	public void afficherEventsUser(int idUser) throws SQLException, RemoteException;
	public void createEvent(int idUser, String libelle, String dateDebut, String dateFin) throws SQLException, RemoteException;
	public void updateEvent(int idEvent, String libelle, String dateDebut, String dateFin) throws SQLException, RemoteException;
	public void deleteEvent(int idEvent) throws SQLException, RemoteException;
	public void addParticipant(int idEvent, int idParticipant) throws SQLException, RemoteException;
	public void deleteParticipant(int idEvent, int idParticipant) throws SQLException, RemoteException;
}
