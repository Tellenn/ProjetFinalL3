import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

public class Utilisateur {
	
	    /**
	     * insert dans la table Utilisateur l' identifiant de l'Utilisateur, son nom et son prénom.
	     * @throws SQLException 
	     */
	    public static void ajouterUtilisateur(Statement stmt,int idUser , String userName, String userPassword) throws SQLException{

	            String rqt = "insert into Utilisateur values (" + idUser + "," + "'"+userName+"'" + "," + "'"+userPassword+"'" +")";

	            int i = stmt.executeUpdate(rqt);
	            stmt.close();
	            //System.out.println("nb mise à jour" + i);
	   
	    };
	    
	    /**
	     * calcule l'incrémentation de idUser en executant une requête qui cherche le dernier idUser dans la table Utilisateur. 
	     * @throws SQLException 
	     */
	    public static int calcul_idUser(Statement stmt) throws SQLException{
	    int idUser = 0;

	            ResultSet rs = stmt.executeQuery("SELECT max (idUser) FROM Utilisateur");

	            while (rs.next()) {
	            	idUser = rs.getInt(1);
	            }
	            idUser = idUser + 1;
	            stmt.close();

	        return idUser;
	    }
	    
	    /**
	     * recherche le login
	     * @throws SQLException 
	     */
	    public static int rechercheUtilisateur(Statement stmt, String userName) throws SQLException{
	        String idUser;
	        
	    
	            ResultSet rs = stmt.executeQuery("SELECT idUser FROM Utilisateur WHERE lower(username) = lower('" + userName + "')");
	            if (!rs.next()) {
	                return 0;
	            }else{
	                do {
	                    idUser = rs.getString(1);
	                } while (rs.next());
	            }
	        return Integer.parseInt(idUser);
	    }
	    
	    
	    
	    public static void chargerProfil(Statement stmt, int idUser) {
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
		            	System.out.println("\n*************PERSONNE************");
		            	
		            	Element nom = (Element) personne.getElementsByTagName("nom").item(0);
		     		    Element prenom = (Element) personne.getElementsByTagName("prenom").item(0);
		     		    Element birthday = (Element) personne.getElementsByTagName("birthday").item(0);
		     		    //Element placeOfBirth = (Element) personne.getElementsByTagName("placeOfBirth").item(0);
		     		   
		    		    //Affichage du nom et du prénom
		    		    System.out.println("nom : " + nom.getTextContent());
		    		    System.out.println("prénom : " + prenom.getTextContent());
		    		    System.out.println("anniversaire : " + birthday.getTextContent());
		    		    //System.out.println("Place of birth : " + placeOfBirth.getTextContent());
		            	 
		            }
		    	}
		    }
	    }
	    
	    public static void ajouterProfil(Statement stmt, int idUser) {
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
		            	System.out.println("\n*************PERSONNE************");
		            	
		            	Element nom = (Element) personne.getElementsByTagName("nom").item(0);
		     		    Element prenom = (Element) personne.getElementsByTagName("prenom").item(0);
		     		    Element birthday = (Element) personne.getElementsByTagName("birthday").item(0);
		     		    //Element placeOfBirth = (Element) personne.getElementsByTagName("placeOfBirth").item(0);
		     		   
		    		    //Affichage du nom et du prénom
		    		    System.out.println("nom : " + nom.getTextContent());
		    		    System.out.println("prénom : " + prenom.getTextContent());
		    		    System.out.println("anniversaire : " + birthday.getTextContent());
		    		    //System.out.println("Place of birth : " + placeOfBirth.getTextContent());
		            	 
		            }
		    	}
		    }
	    }
}
	         
	    
	    
	    
	


