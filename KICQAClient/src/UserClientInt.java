
import java.rmi.*;
 
public interface UserClientInt extends Remote{	
	/**
	 * sert a connaitre le login
	 * @return userName
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public String getName()throws RemoteException ;
	
	/**
	 * sert a connaitre l'identifiant
	 * @return identifiant
	 * @throws RemoteException s'il y a un problem de communication 
	 */

	public int getId() throws RemoteException ;
	/**
	 * sert a modifier l'identifiant
	 * @param idUser l'identifiant
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void setId(int idUser)throws RemoteException;
	/**
	 * sert a modifier le login en userName
	 * @param userName le login
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void setName(String userName)throws RemoteException;
	/**
	 * sert a connaitre le mot de passe
	 * @return le mot de passe
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public String getMdp()throws RemoteException;
	/**
	 * sert a modifier le mot de passe en mdp
	 * @param mdp le nouveau mot de passe 
	 * @throws RemoteException s'il y a un problem de communication 
	 */
	public void setMdp(String mdp)throws RemoteException;
}
}
