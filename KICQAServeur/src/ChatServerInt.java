
import java.rmi.*;
import java.util.*;
 
public interface ChatServerInt extends Remote{	
	/** authentifie le client sur le serveur
	 * @param a : client
	 * @param login : login
	 * @param mdp : mot de passe
	 * @return idClient
	 * @throws RemoteException: s'il y un problème de communication
	 */
	public int 								login 			(ChatClientInt a, String login, String mdp)		throws RemoteException ;
	
	
	/** Publie aux 2 personnes concernées le message s
	 * @param s : message envoyé par id1
	 * @param id1: id de l'emetteur
	 * @param id2: id du recepteur
	 * @throws RemoteException: s'il y un problème de communication
	 */
	public void 								publishPrivate		(String s, int id1, int id2 ) 				throws RemoteException ;
	
	/** Permet de récupérer les clients connectés
	 * @return les clients connectes
	 * @throws RemoteException: s'il y un problème de communication
	 */
	public TreeMap<Integer, ChatClientInt> 					getConnected		() 							throws RemoteException ;
	
	/** Récupère le client avec l'id id
	 * @param i : id du client
	 * @return le client d'id id
	 * @throws RemoteException: s'il y un problème de communication
	 */
	public ChatClientInt 							getClient		(int i) 						throws RemoteException ;
	
	/** Récupère la totalité des messages et met à jour le client
	 * @param a: le client
	 * @param idPersonne2: le recepteur
	 * @throws RemoteException
	 */
	public void 								uploadConversation	(ChatClientInt a, int idPersonne2)			throws RemoteException ;
	
	/** Récupère la totalité des salons concernés par le client a
	 * @param a : client concerné
	 * @throws RemoteException
	 */
	public void 								uploadSalon		(ChatClientInt a)					throws RemoteException ;
}
