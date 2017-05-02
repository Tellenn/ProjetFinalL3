import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import user.Utilisateur;

public class UserServer extends UnicastRemoteObject implements UserServerInt {
	Utilisateur user = new Utilisateur();
	private Vector v = new Vector();
	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "charroan";
	private static final String mdp = "Aclf2016";
	private Statement stmt;

	public UserServer() throws RemoteException {
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

		public int login(UserClientInt a, String login, String mdp) throws RemoteException{	
			
			ResultSet rset;
			try {
				rset = stmt.executeQuery("Select idUser from Utilisateur where username='" + login + "' AND userPassword='"+mdp +"'");
				while (rset.next()) {
					a.tell("Connexion acceptée. ");
					a.setId(Integer.valueOf(rset.getString(1)));
					System.out.println("Connexion accepté pour "+login+ " id: "+a.getId()+ " password : "+ mdp);
					return Integer.valueOf(rset.getString(1));
				}
				stmt.close();
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
		  	a.tell("You have Connected successfully.");
			///publish(a.getName()+ " has just connected.",idUser);
		  	v.add(a);
			return -1;
		}	
		
		public void ajoutDeRelation(UserClientInt a, int idUser2) throws RemoteException{	
			int idUser1 = user.rechercheUtilisateur(a.getName());
			System.out.println("idUser1: " +idUser1 );
			Utilisateur user = new Utilisateur();
			user.ajouterRelation(idUser1, idUser2);
		}
		
		public void suppressiontDeRelation(UserClientInt a, int idUser2) throws RemoteException{	
			int idUser1 = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.supprimerRelation(idUser1, idUser2);
		}
		
		public void chargementProfil(UserClientInt a) throws RemoteException{	
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.chargerProfil(idUser);
		}
		
		public void modificationProfil(UserClientInt a) throws RemoteException{	
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.modifierProfil(idUser);
		}
		
		public void modificationProfilParChamp(UserClientInt a,String champ, String content) throws RemoteException{	
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.modifierProfilParChamp(idUser, champ, content);
		}
		
		public void modificationUsername(UserClientInt a,String userName) throws RemoteException{	
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.modifierUsername(idUser, userName);
		}
		
		public void modificationPassword(UserClientInt a,String password) throws RemoteException{	
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.modifierPassword(idUser, password);
		}
		
		public void ajoutUser(UserClientInt a, String userName, String userPassword) throws RemoteException{
			Utilisateur user = new Utilisateur();
			int idUser = user.calcul_idUser();
			user.ajouterUtilisateur(idUser, userName, userPassword);
			a.setId(idUser);
			a.setName(userName);
			v.add(a);
		}
		
		public void suppressionUser(UserClientInt a) throws RemoteException{	
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.supprimerUser(idUser);
			v.remove(a);
		}
		
		public void ajoutChamp(String champ) throws RemoteException{
			Utilisateur user = new Utilisateur();
			user.ajouterChamp(champ);
		}
		
		public void ajoutChamp(UserClientInt a, String champ, String content) throws RemoteException{
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.ajouterChamp(champ, idUser, content);
		}
		
		public void suppressionChamp(String champ) throws RemoteException{
			Utilisateur user = new Utilisateur();
			user.supprimerChamp(champ);
		}
		
		public void suppressionChamp(UserClientInt a, String champ) throws RemoteException{
			int idUser = user.rechercheUtilisateur(a.getName());
			Utilisateur user = new Utilisateur();
			user.supprimerChamp(champ, idUser);
		}



	public Vector getConnected() throws RemoteException {
		return v;
	}

	@Override
	public void publish(String s, int id) throws RemoteException {
		
		
	}

}
	