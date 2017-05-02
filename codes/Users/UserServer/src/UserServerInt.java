
import java.rmi.*;
import java.sql.SQLException;
import java.util.*;
 
public interface UserServerInt extends Remote{	
	public int login(UserClientInt a, String login, String mdp) throws RemoteException;
	public void publish (String s, int id )throws RemoteException ;
	public Vector getConnected() throws RemoteException ;
	
	public void ajoutDeRelation(UserClientInt a, int idUser2) throws RemoteException;
	public void suppressiontDeRelation(UserClientInt a, int idUser2) throws RemoteException ;
	public void chargementProfil(UserClientInt a) throws RemoteException ;
	public void modificationProfil(UserClientInt a) throws RemoteException ;
	public void modificationProfilParChamp(UserClientInt a,String champ, String content) throws RemoteException ;
	public void modificationUsername(UserClientInt a,String userName) throws RemoteException ;
	public void modificationPassword(UserClientInt a,String password) throws RemoteException ;
	public void ajoutUser(UserClientInt a, String userName, String userPassword) throws RemoteException ;
	public void suppressionUser(UserClientInt a) throws RemoteException ;	
	public void ajoutChamp(String champ) throws RemoteException ;	
	public void ajoutChamp(UserClientInt a, String champ, String content) throws RemoteException ;	
	public void suppressionChamp(String champ) throws RemoteException ;	
	public void suppressionChamp(UserClientInt a, String champ) throws RemoteException ;
}