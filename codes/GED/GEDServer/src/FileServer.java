import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FileServer extends UnicastRemoteObject implements FileServerInt {

	private String file = "";
	private String path = "";
	private static final String dbUrl = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	private static final String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	private static final String login = "perrink";
	private static final String mdp = "Kalayoda1";
	private Statement stmt;

	protected FileServer() throws RemoteException {
		super();
		Connection conn;

		try {
			Class.forName(jdbcDriver);

			conn = DriverManager.getConnection(dbUrl, login, mdp);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// TODO Auto-generated constructor stub
	}

	/**
	 * Permet de changer le fichier sur le quel on travail
	 * @param f le nom du fichier
	 * @param path le chemin vers le fichier
	 */
	public void setFile(String f, String path) {
		file = f;
		this.path = path;
	}
	
	/**
	 * Permet de commit sur la base de donnée
	 * @throws SQLException
	 */
	public static void commit() throws SQLException {

		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("commit");
		st.close();

	};

	 /**
     * Permet d'ajouter a la base de donnée le document fournis en argument
     * @param nomDoc nom du fichier a ajouter
     * @param chemin chemin du fichier a ajouter
     * @return true si l'ajout s'est correctement effectué
     * @throws SQLException
     */
	public int AjoutDoc(String nomDoc, String chemin) throws SQLException {
		ResultSet res;
		res = stmt.executeQuery("select max(idFichier) from Fichier");
		res.next();
		System.out.println("Resultat : " + res.getInt(1));
		int idfichier = 1 + res.getInt(1);
		stmt.executeUpdate(
				"INSERT INTO Fichier VALUES(" + idfichier + ",'" + nomDoc + "',1,'ma tronche'," + null + ")");
		String[] dossiers = chemin.split("/");
		res = stmt
				.executeQuery("select idDossier from Dossier where nomDossier='" + dossiers[dossiers.length - 1] + "'");
		res.next();
		int idpere = res.getInt(1);
		stmt.executeUpdate("INSERT INTO FichierDansDossier VALUES(" + idfichier + "," + idpere + ")");
		commit();

		return idfichier;
	}

	/**
	 * Permet d'envoyer un flot de donné vers un utilisateur
	 * @param c l'interface client
	 * @param cible le lieu ou le client va ranger le fichier
	 * @return true si l'envoi s'est bien passé
	 */
	public boolean sendData(FileClientInt c, String cible) throws RemoteException {
		try {
			File f1 = new File(path + file);
			FileInputStream in = new FileInputStream(f1);
			byte[] mydata = new byte[1024 * 1024];
			int mylen = in.read(mydata);
			while (mylen > 0) {
				c.receiveData(f1.getName(), mydata, mylen, cible);
				mylen = in.read(mydata);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}
	
	/**
	 * Permet au serveur de recevoir des données
	 * @param data un buffer
	 * @param len la taille du buffer
	 * @return true si le transfert s'est bien passé
	 */
	public int receiveData(byte[] data, int len) throws RemoteException {
		int id = 0;
		try {
			File f = new File(/*path + */file);
			f.createNewFile();
			FileOutputStream out = new FileOutputStream(f, true);
			out.write(data, 0, len);
			out.flush();
			out.close();
			System.out.println("Done writing data...");
			id = this.AjoutDoc(file, "RH");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}


	/**
	 * Supprime un document à la GED
	 * 
	 * @param iddoc
	 * @param chemin
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void deleteDoc(int iddoc, String chemin) throws RemoteException {

		String sql = "delete from Fichier where idFichier=" + iddoc;//TODO ON DELETE CASCADE dans la table
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();
				conn.commit();
				deleteFichier(chemin);
				System.out.println("Fichier supprimer");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Supprime un Fichier vide dans l'architecture
	 * 
	 * @param chemin
	 */
	public void deleteFichier(String chemin) {
		new File(chemin).delete();
	}

	/**
	 * Ajoute un utilisateur un droit sur un fichier
	 * 
	 * @param idDoc
	 * @param idUser
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void shareDoc(int idDoc, int idUser) throws RemoteException {
		String sql = "insert into droitfichier values (?,?)";
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, idDoc);
				pstmt.setInt(2, idUser);
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Fichier " + getDocName(idDoc) + " partagé avec succès avec l'utilisateur "
						+ getUserName(idUser));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Ajoute un utilisateur un droit sur un dossier
	 * 
	 * @param idFolder
	 * @param idUser
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void shareFolder(int idFolder, int idUser) throws RemoteException {
		String sql = "insert into droitdossier values (?,?)";
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, idFolder);
				pstmt.setInt(2, idUser);
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Dossier " + getFolderName(idFolder) + " partagé avec succès avec l'utilisateur "
						+ getUserName(idUser));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Supprime le droit d'accès à un fichier, pour un utilisateur
	 * 
	 * @param iddoc
	 * @param iduser
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void deleteAccessDoc(int iddoc, int iduser) throws RemoteException {
		String sql = "Delete from droitfichier where idFichier=" + iddoc + "and iduser=" + iduser;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Droit d'acces Fichier supprimer");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Supprime le droit d'accès à un dossier, pour un utilisateur
	 * 
	 * @param idfol
	 * @param iduser
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void deleteAccessFolder(int idfol, int iduser) throws RemoteException {
		String sql = "Delete from droitdossier where iddossier=" + idfol + "and iduser=" + iduser;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Droit d'acces dossier supprimer");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Créer un dossier dans l'architecture GED
	 * 
	 * @param id
	 * @param nomfol
	 * @throws java.rmi.RemoteException
	 */
	@Override 
	public int createFolder(String nomfol, String nomPere) throws RemoteException {
		System.out.println("Création d'un dossier");
		int idDossier = 0;
		try {
			ResultSet res;
			res = stmt.executeQuery("select max(idDossier) from Dossier");
			System.out.println("SELECT 1 OK");
			res.next();
			idDossier = 1 + res.getInt(1);
			System.out.println("INSERT 1 : "+idDossier+" et "+nomfol);
			stmt.executeUpdate("INSERT INTO Dossier VALUES(" + idDossier + ",'" + nomfol + "')");
			System.out.println("INSERT 1 OK");
			res = stmt.executeQuery("Select idDossier from Dossier where nomDossier='" + nomPere + "'");
			System.out.println("SELECT 2 OK");
			res.next();
			int idPere = res.getInt(1);
			stmt.executeUpdate("INSERT INTO DOSSIERDANSDOSSIER VALUES(" + idDossier + "," + idPere + ")");
			System.out.println("INSERT 2 OK");
			commit();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return idDossier;
	}

	/**
	 * Supprime un dossier vide dans l'architecture
	 * 
	 * @param idfol
	 * @param chemin
	 * @throws java.rmi.RemoteException
	 * @throws SQLException 
	 */
	@Override
	public void deleteFolder(int idfol, String chemin) throws RemoteException, SQLException {
		String sql = "Delete from dossier where iddossier="+idfol;
		System.out.println(idfol+" "+chemin);
		try {
			//deleteDocFolder(idfol);
			//deleteFol(idfol);
			//deleteDroitFol(idfol);
			//deleteDossier(chemin);
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Dossier supprimer");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Supprime un dossier vide dans l'architecture
	 * 
	 * @param chemin
	 */
	public void deleteDossier(String chemin) {
		new File(chemin).delete();
		System.out.println("Dossier supprimer");
	}

	/**
	 * Supprime Tous les fichier d'un dossier dans l'architecture
	 * fichierdansdossier
	 * 
	 * @param iddoc
	 * @throws java.sql.SQLException
	 */
	public void deleteDocFolder(int iddoc) throws SQLException {
		//int idfichier = SearchDoc(iddoc);
		String sql = "Delete from fichierdansdossier where idDossier=" + iddoc;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Fichier supprimer");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Supprime un dossier dans l'architecture dossierdansdossier
	 * 
	 * @param idfol
	 * @throws java.sql.SQLException
	 */
	public static void deleteFol(int idfol) throws SQLException {
		//TODO faire pour iddossierpere
		String sql = "Delete from dossierdansdossier where iddossierfils=" + idfol;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Dossier fils supprimer");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Supprime un dossier dans l'architecture Droitdossier
	 * 
	 * @param idfol
	 * @throws java.sql.SQLException
	 */
	public void deleteDroitFol(int idfol) throws SQLException {

		String sql = "Delete from droitdossier where iddossier=" + idfol;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.executeUpdate();
				conn.commit();
				System.out.println("Dossier avec droit supprimer");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Recherche un fichier dans l'architecture
	 * 
	 * @param idfol
	 * @return
	 * @throws java.sql.SQLException
	 */
	public int SearchDoc(int idfol) throws SQLException {
		String sql = "select idfichier from fichierdansdossier where iddossier=" + idfol;
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		conn.setAutoCommit(true);
		// Execute the query
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * Renvoi les information du dossier contenue dans le XML
	 * 
	 * @param nomFolder
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void infoFolder(String nomFolder) throws RemoteException {
		String sql = "select * from dossier where lower(nomdossier)='" + nomFolder + "'";
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			// Execute the query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("L'identifiant du dossier " + rs.getInt(1));
				System.out.println("Nom du dossier " + rs.getString(2));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Renvoi l'id du dossier contenu dans le XML
	 * 
	 * @param nomDoc
	 * @return id du doc passé en paramètre
	 * @throws java.sql.SQLException
	 */
	public int getIdDoc(String nomDoc) throws SQLException {
		String sql = "select idfichier from fichier where lower(nomfichier)='" + nomDoc + "'";
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		conn.setAutoCommit(true);
		// Execute the query
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * 
	 * @param nomFolder
	 * @return
	 * @throws java.sql.SQLException
	 */
	public int getIdFolder(String nomFolder) throws SQLException {
		String sql = "select iddossier from dossier where lower(nomdossier)='" + nomFolder + "'";
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		conn.setAutoCommit(true);
		// Execute the query
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * @param nomDoc
	 * @return
	 * @throws java.rmi.RemoteException
	 */
	public String getChemin(String nomDoc) throws RemoteException {
		String path = new File("git").getAbsolutePath();
		return path;
	}

	/**
	 * @param idFolder
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void getAccessFolder(int idFolder) throws RemoteException {
		String sql = "select distinct iduser from droitdossier where iddossier=" + idFolder;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			// Execute the query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Les utilisateurs ayant droit sur ce Dossier " + getFolderName(idFolder));
			while (rs.next()) {
				System.out.println(getUserName(rs.getInt(1)));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Repere les personnes ayant droit à un fichier donnné
	 * 
	 * @param idDoc
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void getAccessDoc(int idDoc) throws RemoteException {
		String sql = "select distinct iduser from droitfichier where idfichier=" + idDoc;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			// Execute the query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Les utilisateurs ayant droit sur ce fichier " + getDocName(idDoc));
			while (rs.next()) {
				System.out.println(getUserName(rs.getInt(1)));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * @param idUser
	 * @return ???
	 * @throws java.sql.SQLException
	 */
	public String getUserName(int idUser) throws SQLException {
		String sql = "select distinct username from utilisateur where iduser=" + idUser;
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		conn.setAutoCommit(true);
		// Execute the query
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		return rs.getString(1);
	}

	/**
	 *
	 * @param idDoc
	 * @return
	 * @throws SQLException
	 */
	public String getDocName(int idDoc) throws SQLException {
		String sql = "select distinct nomfichier from fichier where idfichier=" + idDoc;
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		conn.setAutoCommit(true);
		// Execute the query
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		return rs.getString(1);
	}

	/**
	 * Recupère le dossier racine d'un dossier dont l'id est passé en paramètre
	 * 
	 * @param idFolder
	 * @throws java.rmi.RemoteException
	 */
	@Override
	public void getRacineFolder(int idFolder) throws RemoteException {
		String sql = "select * from dossierdansdossier where iddossierfils=" + idFolder;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			// Execute the query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Nom du dossier Racine " + getFolderName(rs.getInt(2)));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Recupere l'ensemble des dossiers fils d'un dossier donnée
	 * 
	 * @param idFolder
	 * @throws java.rmi.RemoteException
	 */
	public void getFilsFolder(int idFolder) throws RemoteException {
		String sql = "select * from dossierdansdossier where iddossierpere=" + idFolder;
		try {
			Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
			conn.setAutoCommit(true);
			// Execute the query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Nom du dossier Fils " + getFolderName(rs.getInt(1)));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Renvoi le nom d'un dossier dont id est passé en paramètre
	 * 
	 * @param idFolder
	 * @return
	 * @throws java.sql.SQLException
	 */
	public String getFolderName(int idFolder) throws SQLException {
		String sql = "select distinct nomdossier from dossier where iddossier=" + idFolder;
		Connection conn = DriverManager.getConnection(dbUrl, login, mdp);
		conn.setAutoCommit(true);
		// Execute the query
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		return rs.getString(1);

	}
}
