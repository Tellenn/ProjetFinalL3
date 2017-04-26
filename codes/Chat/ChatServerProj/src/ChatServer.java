
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import chat.ConversationPrivee;
import oracle.net.aso.a;

import java.sql.*;

public class ChatServer extends UnicastRemoteObject implements ChatServerInt {

	private Vector v = new Vector();
	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "charroan";
	private static final String mdp = "Aclf2016";
	private Statement stmt;

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
			System.out.println("C");
			while (rset.next()) {
				System.out.println("Connexion accepté pour "+rset.getString(1)+ "id "+a.getId());
				a.tell("Connexion acceptée.");
				v.add(a);
				return Integer.valueOf(rset.getString(1));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return -1;
	}

	// Le serveur recoit un message d'un client et le transmet à tous les utilisateurs connectés
	public void publish(String s, int id) throws RemoteException {
		// *** On envoie à tous les utilisateurs conecté le message
		for (int i = 0; i < v.size(); i++) {
			try {				
				ChatClientInt chatClient = (ChatClientInt) v.get(i);
				chatClient.tell(s);
				
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

	public Vector getConnected() throws RemoteException {
		return v;
	}

	public ChatClientInt getClient(int i) throws RemoteException{
		return (ChatClientInt)v.get(i);
	}
	
	public void uploadConversation(ChatClientInt a, int idPersonne2)throws RemoteException{
		ConversationPrivee c = new ConversationPrivee(a.getId(),idPersonne2);
		System.out.print(c.toString());
		a.tell(c.toString());
	}

	
}