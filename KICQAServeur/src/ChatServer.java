
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import chat.ConversationPrivee;
import chat.ConversationSalon;
//import oracle.net.aso.a;

import java.sql.*;

public class ChatServer extends UnicastRemoteObject implements ChatServerInt {

	private TreeMap<Integer, ChatClientInt> p = new TreeMap<Integer, ChatClientInt>();

	private static final String dbUrl 		= "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver 		= "oracle.jdbc.driver.OracleDriver";
	private static final String login 		= "charroan";
	private static final String mdp 		= "Aclf2016";
	private Statement 	 stmt;

	public ChatServer() throws RemoteException {
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	/** authentifie le client sur le serveur
	 * @param a : client
	 * @param login : login
	 * @param mdp : mot de passe
	 * @return idClient
	 * @throws RemoteException: s'il y un problème de communication
	 */
	public int login(ChatClientInt a, String login, String mdp) throws RemoteException {
		//System.out.println(a.getName() + " essaye de se connecter ....");
		ResultSet rset;
		try {
			rset = stmt.executeQuery(
					"select 'la date d''aujourd''hui est '||" + "to_char (sysdate, 'DD/MM/YYYY')" + "from dual");
			while (rset.next()) {
				System.out.println(rset.getString(1));
			}
			// VÃ©rifie les donnÃ©es de connexion
			rset = stmt.executeQuery("Select idUser from Utilisateur where username='" + login + "' AND userPassword='"+mdp +"'");
			while (rset.next()) {
				System.out.println("Connexion acceptÃ© pour "+rset.getString(1)+ "id "+a.getId());
				a.tell("Connexion acceptÃ©e. ");
				a.setId(Integer.valueOf(rset.getString(1)));
				p.put(a.getId(), a);
				return Integer.valueOf(rset.getString(1));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return -1;
	}

	
	
	// Conversation privee
	// Le serveur recoit un message d'un client et le transmet Ã  id2
	public void publishPrivate(String s, int id1, int id2 ) throws RemoteException {
		// *** On envoie Ã  l'utilisateur conectÃ© le message		
		
		//System.out.println("[System]publishPrivate(id1:"+id1+" id2:"+id2+")");
		// *** On met Ã  jour la conversation
		ConversationPrivee c = new ConversationPrivee(id1,id2);
		c.ajouterMessage(id1, s);	
		TreeMap<String, String> t =new TreeMap<String, String>(c.getLastMessage());
		t.put("nom", p.get(Integer.valueOf(t.get("emetteur"))).getName());
		try {
			//si l'utilisateur est connectÃ©, il sera avertit			
			p.get(id1).tell(t);				
		}catch (Exception e) {
			System.out.println("[System]ERREUR: client non connectÃ©");
			// sinon l'utilisateur n'est pas connectÃ©
		}
		try {
			//si l'utilisateur est connectÃ©, il sera avertit
			if(id1!=id2)p.get(id2).tell(t);				
		}catch (Exception e) {
			// sinon l'utilisateur n'est pas connectÃ©, on rattrape l'erreur et on fait rien		
		}
	}

	/** Permet de récupérer les clients connectés
	 * @return les clients connectes
	 * @throws RemoteException: s'il y un problème de communication
	 */
	public TreeMap<Integer, ChatClientInt> getConnected() throws RemoteException {
		return p;
	}

	/** Récupère le client avec l'id id
	 * @param i : id du client
	 * @return le client d'id id
	 * @throws RemoteException: s'il y un problème de communication
	 */
	public ChatClientInt getClient(int id) throws RemoteException{
		return (ChatClientInt)p.get(id);
	}
	
	/** Affiche les idClients des clients connectés du serveur
	 * @throws RemoteException
	 */
	public void afficherIdClients() throws RemoteException{
		for (ChatClientInt item : p.values()) {
			try {		
				System.out.print(item.getId());
			} catch (Exception e) {
				// problem with the client not connected.
				// Better to remove it
			}
		}
	}
	
	/** Récupère la totalité des messages et met à jour le client
	 * @param a: le client
	 * @param idPersonne2: le recepteur
	 * @throws RemoteException
	 */
	public void uploadConversation(ChatClientInt a, int idPersonne2)throws RemoteException{
		ConversationPrivee c = new ConversationPrivee(a.getId(),idPersonne2);
		List<TreeMap<String, String>> tt =new ArrayList<TreeMap<String, String>>(c.getMessages());

		for(int i = 0; i<tt.size(); i++){
			tt.get(i).put("nom", p.get(Integer.valueOf(tt.get(i).get("emetteur"))).getName());
			a.tell(tt.get(i));
		}
	}
	
	/** Récupère la totalité des salons concernés par le client a
	 * @param a : client concerné
	 * @throws RemoteException
	 */
	public void uploadSalon	(ChatClientInt a) throws RemoteException {
		ResultSet rset;
			try {
				rset = stmt.executeQuery("Select IdSalon from droitUtilisateur where idUser=" + a.getId());	
			
				while (rset.next()) {					
					ConversationSalon c = new ConversationSalon(a.getId());
					a.addSalon(Integer.valueOf(rset.getString(1)), c.getNameSalon());				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
