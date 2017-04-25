
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

	public boolean login(ChatClientInt a) throws RemoteException {
		//System.out.println(a.getName() + " essaye de se connecter ....");
		int resultat = 1;
		ResultSet rset;
		try {
			rset = stmt.executeQuery(
					"select 'la date d''aujourd''hui est '||" + "to_char (sysdate, 'DD/MM/YYYY')" + "from dual");
			while (rset.next()) {
				System.out.println(rset.getString(1));
			}
			rset = stmt.executeQuery("Select idUser from Utilisateur where username='" + a.getName() + "'");
			while (rset.next()) {System.out.println(rset.getString(1));
				//int id = Integer.valueOf(rset.getString(1));
				//System.out.println(id);
				a.tell("Connexion accepté.");
				// Carine devrat récup l'id client en chargeant le fichier
				//publish(a.getName() + " est connecté.", id);
				v.add(a);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Le serveur recoit un message d'un client
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

	private void updateXML(String s) {
		// TODO Auto-generated method stub
		
	}

	public Vector getConnected() throws RemoteException {
		return v;
	}

}