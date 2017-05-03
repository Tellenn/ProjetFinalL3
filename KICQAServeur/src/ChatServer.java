
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
	private static final String jdbcDriver 	= "oracle.jdbc.driver.OracleDriver";
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

	public int login(ChatClientInt a, String login, String mdp) throws RemoteException {
		//System.out.println(a.getName() + " essaye de se connecter ....");
		ResultSet rset;
		try {
			rset = stmt.executeQuery(
					"select 'la date d''aujourd''hui est '||" + "to_char (sysdate, 'DD/MM/YYYY')" + "from dual");
			while (rset.next()) {
				System.out.println(rset.getString(1));
			}
			// Vérifie les données de connexion
			rset = stmt.executeQuery("Select idUser from Utilisateur where username='" + login + "' AND userPassword='"+mdp +"'");
			while (rset.next()) {
				System.out.println("Connexion accepté pour "+rset.getString(1)+ "id "+a.getId());
				a.tell("Connexion acceptée. ");
				a.setId(Integer.valueOf(rset.getString(1)));
				p.put(a.getId(), a);
				return Integer.valueOf(rset.getString(1));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return -1;
	}

	// Le serveur recoit un message d'un client et le transmet à tous les utilisateurs connectés
	public void publish(String s, int id) throws RemoteException {
		// TODO: A reprendre
		// *** On envoie à tous les utilisateurs conecté le message
		for (ChatClientInt item : p.values()) {
			try {		
				item.tell(s);
			} catch (Exception e) {
				// problem with the client not connected.
				// Better to remove it
			}
		}
		// *** On met à jour la conversation
		// !!!!! Mettre le bon id2
		ConversationPrivee c = new ConversationPrivee(id,3);
		c.ajouterMessage(id, s);
	}
	
	// Conversation privee
	// Le serveur recoit un message d'un client et le transmet à id2
	public void publishPrivate(String s, int id1, int id2 ) throws RemoteException {
		// *** On envoie à l'utilisateur conecté le message	
		//System.out.println(p.get(id1).getName());			
		
		System.out.println("[System]publishPrivate(id1:"+id1+" id2:"+id2+")");
		// *** On met à jour la conversation
		ConversationPrivee c = new ConversationPrivee(id1,id2);
		c.ajouterMessage(id1, s);	
		TreeMap<String, String> t =new TreeMap<String, String>(c.getLastMessage());
		t.put("nom", p.get(Integer.valueOf(t.get("emetteur"))).getName());
		try {
			//si l'utilisateur est connecté, il sera avertit			
			p.get(id1).tell(t);				
		}catch (Exception e) {
			System.out.println("[System]ERREUR: client non connecté");
			// sinon l'utilisateur n'est pas connecté
		}
		try {
			//si l'utilisateur est connecté, il sera avertit
			if(id1!=id2)p.get(id2).tell(t);				
		}catch (Exception e) {
			// sinon l'utilisateur n'est pas connecté, on rattrape l'erreur et on fait rien		
		}
	}

	public TreeMap<Integer, ChatClientInt> getConnected() throws RemoteException {
		return p;
	}

	public ChatClientInt getClient(int id) throws RemoteException{
		return (ChatClientInt)p.get(id);
	}
	
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
	
	public void uploadConversation(ChatClientInt a, int idPersonne2)throws RemoteException{
		ConversationPrivee c = new ConversationPrivee(a.getId(),idPersonne2);
		List<TreeMap<String, String>> tt =new ArrayList<TreeMap<String, String>>(c.getMessages());

		for(int i = 0; i<tt.size(); i++){
			tt.get(i).put("nom", p.get(Integer.valueOf(tt.get(i).get("emetteur"))).getName());
			a.tell(tt.get(i));
		}
	}
	
	public void uploadSalon	(ChatClientInt a) throws RemoteException {
		ResultSet rset;
		String NameSalon; 
		TreeMap<Integer, String> tt =new TreeMap<Integer, String>();
			try {
				rset = stmt.executeQuery("Select IdSalon from droitUtilisateur where idUser=" + a.getId());	
			
				while (rset.next()) {
					System.out.println("Salon "+rset.getString(1)+ " avec iduser= "+a.getId());
					ConversationSalon c = new ConversationSalon(a.getId());
					a.addSalon(Integer.valueOf(rset.getString(1)), c.getNameSalon());				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	
}