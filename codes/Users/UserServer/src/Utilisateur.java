import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

public class Utilisateur {

    /**
     *  execute la requête commit après chaque méthode
     * @throws SQLException 
     */
    public static void commit() throws SQLException {
       
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("commit");
            st.close();
       
    };
   
	
	    /**
	     * insert dans la table Utilisateur l' identifiant de l'Utilisateur, son nom et son prénom.
	     * @throws SQLException 
	     */
	    public static void ajouterUtilisateur(int idUser , String userName, String userPassword) throws SQLException{
	    
	            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");

	            Statement stmt = conn.createStatement();
	            String rqt = "insert into Utilisateur values (" + idUser + "," + "'"+userName+"'" + "," + "'"+userPassword+"'" +")";
	            int i = stmt.executeUpdate(rqt);
	            stmt.close();
	            System.out.println("nb mise à jour" + i);
	            commit();
	    	 
	    };
	    
	    /**
	     * calcule l'incrémentation de idUser en executant une requête qui cherche le dernier idUser dans la table Utilisateur. 
	     * @throws SQLException 
	     */
	    public static int calcul_idUser() throws SQLException{
		    int idUser = 0;
		    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT max(idUser) FROM Utilisateur");
		    while (rs.next()) {
		    	idUser = rs.getInt(1);
	        }
	        idUser = idUser + 1;
	        stmt.close();
	        commit();

	        return idUser;
	    }
	    
	    /**
	     * recherche le login
	     * @throws SQLException 
	     */
	    public static int rechercheUtilisateur(String userName) throws SQLException{
	        String idUser;
	        
	        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
		    Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT idUser FROM Utilisateur WHERE lower(username) = lower('" + userName + "')");
	        if (!rs.next()) {
	        	return 0;
	        }else{
	        	do {
	            	idUser = rs.getString(1);
	            } while (rs.next());
	        }
	        stmt.close();
	        commit();
	        return Integer.parseInt(idUser);
	      
	    }
	    
	    
	    
	    public static void chargerProfil(int idUser) throws SQLException {
	    	 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			 Statement stmt = conn.createStatement();
	    	File inputFile = new File("Exemple.xml");
	    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
	        Document doc = null;
			try {
				doc = dBuilder.parse(inputFile);
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
 
	        Element racine = doc.getDocumentElement();

	        NodeList racineNoeuds = racine.getChildNodes();
	 	    int nbRacineNoeuds = racineNoeuds.getLength();
	 	    
		    for (int i = 0; i<nbRacineNoeuds; i++) {
		    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
		    		Element personne = (Element) racineNoeuds.item(i);
		    		
		 		     //Affichage d'une personne
		            String id = personne.getAttribute("id");
		            if (id.compareTo(Integer.toString(idUser))==0){
		            	System.out.println("\n*************PROFIL************");
		            	
		            	Element nom = (Element) personne.getElementsByTagName("nom").item(0);
		     		    Element prenom = (Element) personne.getElementsByTagName("prenom").item(0);
		     		    //Element birthday = (Element) personne.getElementsByTagName("birthday").item(0);
		     		    //Element placeOfBirth = (Element) personne.getElementsByTagName("placeOfBirth").item(0);
		     		   
		    		    //Affichage du nom et du prénom
		    		    System.out.println("nom : " + nom.getTextContent());
		    		    System.out.println("prénom : " + prenom.getTextContent());
		    		    //System.out.println("anniversaire : " + birthday.getTextContent());
		    		    //System.out.println("Place of birth : " + placeOfBirth.getTextContent());
		            	 
		            }
		    	}
		    }
		    stmt.close();
	    }
	    
	    public static void ajouterProfil(int idUser, String nom, String prenom) throws SQLException {
	    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
	    	File inputFile = new File("Exemple.xml");
	    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
	        Document doc = null;
			try {
				doc = dBuilder.parse(inputFile);
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}
 
	        Element racine = doc.getDocumentElement();

	        
	        racine.appendChild(getUser(doc, idUser, nom, prenom));
	        

	 	    // writing xml file
	 	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 	    Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 	    DOMSource source = new DOMSource(doc);
	 	     File outputFile = new File("Exemple.xml");
	 	    StreamResult result = new StreamResult(outputFile );
	 	    // creating output stream
	 	    try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	   stmt.close();
	    }
		    
	    

	   
	    
	private static Node getUser(Document doc, int idUser, String nom, String prenom) {
	    Element user = doc.createElement("user");
	    user.setAttribute("id", Integer.toString(idUser));
	    user.appendChild(getUserElements(doc, user, "nom", nom));
	    user.appendChild(getUserElements(doc, user, "prenom", prenom));
	
	    return user;
	}

	// utility method to create text node
	private static Node getUserElements(Document doc, Element element, String nom, String value) {
	    Element node = doc.createElement(nom);
	    node.appendChild(doc.createTextNode(value));
	    return node;
	} 
	
	 public static void modifierProfil(int idUser, String nom, String prenom) throws SQLException {
	    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
	    	File inputFile = new File("Exemple.xml");
	    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
	        Document doc = null;
			try {
				doc = dBuilder.parse(inputFile);
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}

	        Element racine = doc.getDocumentElement();

	        NodeList racineNoeuds = racine.getChildNodes();
	 	    int nbRacineNoeuds = racineNoeuds.getLength();
	 	    
		    for (int i = 0; i<nbRacineNoeuds; i++) {
		    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
		    		Element personne = (Element) racineNoeuds.item(i);
		    		Node nomNoeud = personne.getChildNodes().item(0);
		    		Node prenomNoeud = personne.getChildNodes().item(1);
		 		     //Affichage d'une personne
		            String id = personne.getAttribute("id");
		            if (id.compareTo(Integer.toString(idUser))==0){
				        nomNoeud.setTextContent(nom);
				        prenomNoeud.setTextContent(prenom);
		            }
		    	}
		    }

	 	    // writing xml file
	 	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 	    Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 	    DOMSource source = new DOMSource(doc);
	 	     File outputFile = new File("Exemple.xml");
	 	    StreamResult result = new StreamResult(outputFile );
	 	    // creating output stream
	 	    try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	   stmt.close();
	    }
	 
	 public static void modifierUsername(int idUser , String userName) throws SQLException{
		    
         Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");

         Statement stmt = conn.createStatement();
         String rqt = "update Utilisateur set username ='" + userName + "' WHERE idUser = " + idUser ;
         int i = stmt.executeUpdate(rqt);
         stmt.close();
         System.out.println("nb mise à jour" + i);
         commit();
	 };
	 
	 public static void modifierPassword(int idUser , String password) throws SQLException{
		    
         Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");

         Statement stmt = conn.createStatement();
         String rqt = "update Utilisateur set userpassword ='" + password + "' WHERE idUser = " + idUser ;
         int i = stmt.executeUpdate(rqt);
         stmt.close();
         System.out.println("nb mise à jour" + i);
         commit();
 	 
	 };
 
	 public static void supprimerUser(int idUser) throws SQLException{
		    
	     Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	
	     Statement stmt = conn.createStatement();
	     String rqt = "delete from Utilisateur WHERE idUser=" + idUser ;
	     int i = stmt.executeUpdate(rqt);
	  
	     System.out.println("nb mise à jour" + i);
	     File inputFile = new File("Exemple.xml");
	    	
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(inputFile);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

        NodeList list= doc.getElementsByTagName("user"); 
        int i1=list.getLength();
        Node root = doc.getFirstChild();
        while (i1>0){ 
          i1--;
          Node child = list.item(i1); 
          Element childElt = (Element)child; 
          String atr = childElt.getAttribute("id"); 

           if (atr.equals(Integer.toString(idUser))){ 
             root.removeChild(child); 
          } 
       } 

	 	    // writing xml file
	 	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 	    Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 	    DOMSource source = new DOMSource(doc);
	 	     File outputFile = new File("Exemple.xml");
	 	    StreamResult result = new StreamResult(outputFile );
	 	    // creating output stream
	 	    try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	   stmt.close();
	 	  commit();
	    }
		    
}	


