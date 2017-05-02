package user;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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

/**
 * @author carineamin
 *
 */
public class Utilisateur {
	
	private static Document doc;
	private final static String chemin = "../";
	private static String nomFichier = "Exemple.xml";
	private Scanner sc;

	
	public Utilisateur(){

	        try {
	            // analyse du document
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder p = dbFactory.newDocumentBuilder();
	            File monFichier = new File(chemin+nomFichier); 
				if(monFichier.exists()){	
		            // récupération de la structure objet du document
		           doc = p.parse(chemin+nomFichier);
				}else{
					//System.out.println("else" + monFichier.getAbsolutePath());
					doc =  p.newDocument();
					Element users = doc.createElement("users");
					doc.appendChild(users);
				}
	        }catch (Exception e) {
	        	System.out.println(e);
	        }

	}

    /**
     *  execute la requête commit après chaque méthode
     * @throws SQLException quand on n'arrive pas a executer la requête.
     */

    public static void commit() throws SQLException {
       
    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
        Statement st = conn.createStatement();
        st.executeQuery("commit");
        st.close();
       
    };
   
	

	    /**
	     * insert dans la table Utilisateur l' identifiant de l'Utilisateur, son nom et son prénom.
	     * @param idUser l'identifiant de l'utilisateur
	     * @param userName le login de l'utilisateur
	     * @param userPassword le mot de passe de l'utilisateur
	     */
	    public void ajouterUtilisateur(int idUser , String userName, String userPassword) {
	    
	            Connection conn;
				try {
					conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
					Statement stmt;
					stmt = conn.createStatement();
					String rqt = "insert into Utilisateur values (" + idUser + "," + "'"+userName+"'" + "," + "'"+userPassword+"'" +")";
					stmt.executeUpdate(rqt);
					stmt.close();
					commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String nom ="";
				String prenom="";
				ajouterProfil(idUser,nom,prenom);
	    	 
	    };
	    
	    /**
	     * insert la relation entre idUser1 et idUser2 dans la base de données
	     * @param idUser1 représente l'identifiant du premier utilisateur
	     * @param idUser2 représente l'identifiant du second utilisateur
	     */
	    public void ajouterRelation(int idUser1 , int idUser2){
	    	try {
	            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	
	            Statement stmt = conn.createStatement();
	            String rqt = "insert into Relation values (" + idUser1 + "," + idUser2 +")";
	            stmt.executeUpdate(rqt);
	            stmt.close();
	           
				commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 
    };
    
    /**
     * Supprime la relation entre IdUser1 et IdUser2 de la base de données
     * @param idUser1 représente l'identifiant du premier utilisateur
	 * @param idUser2 représente l'identifiant du second utilisateur
     */
    public void supprimerRelation(int idUser1 , int idUser2){
    	try {
	        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	
	        Statement stmt = conn.createStatement();
	        String rqt = "delete FROM Relation WHERE idUser1=" + idUser1 + "and idUser2=" + idUser2 ;
	        stmt.executeUpdate(rqt);
	        stmt.close();
	        commit();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    };
	    
	  
	    
	    /**
	     * calcule l'incrémentation de idUser en executant une requête qui cherche le dernier idUser dans la table Utilisateur. 
	     * @return idUser 
	     */
	    public int calcul_idUser() {
	    	int idUser = 0;
	    	try {
			    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			    Statement stmt = conn.createStatement();
			    ResultSet rs = stmt.executeQuery("SELECT max(idUser) FROM Utilisateur");
			    while (rs.next()) {
			    	idUser = rs.getInt(1);
		        }
		        idUser = idUser + 1;
		        stmt.close();
		        commit();
		        
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return idUser;

	        
	    }
	    

	    /**
	     * recherche l'idUser dans la base de données
	     * @param userName le login de l'utilisateur
	     * @return l'idUser de l'utilisateur ayant le login userName
	     */
	    public int rechercheUtilisateur(String userName){
	        String idUser="";
	        try {
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
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Integer.parseInt(idUser);
	      
	    }
	    
	    
	    
	    /**
	     * affiche le profil de l'idUser donné
	     * @param idUser l'identifiant de l'utilisateur qu'on veut charger son profil
	     */
	    public void chargerProfil(int idUser) {
	    	try {
		    	 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
				 Statement stmt = conn.createStatement();
				 Document doc = null;
				 doc =parser(nomFichier);
	
	 
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
			    		    for(int j=0; j<personne.getChildNodes().getLength(); j++){
				    			Node noeudEnPlus = personne.getChildNodes().item(j);
				    			System.out.println(noeudEnPlus.getNodeName() + ": "+noeudEnPlus.getTextContent());	
				    		}	            	 
			            }
			    	}
			    }
			    stmt.close();
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    
	    /**
	     * ajoute le profil dans le fichier xml des profils
	     * @param idUser l'identifiant de l'utilisateur 
	     * @param nom le nom de l'utilisateur
	     * @param prenom le prenom de l'utilisateur
	     */
	    public void ajouterProfil(int idUser, String nom, String prenom) {
	    	try{
		    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
				Statement stmt = conn.createStatement();
				 Document doc = null;
				 doc = parser(nomFichier);
	
	 
		        Element racine = doc.getDocumentElement();
	
		        
		        racine.appendChild(getUser(doc, idUser, nom, prenom));
		        
	
		        save();
		 	   	stmt.close();
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		    
	   
	    
	/**
	 * creer les champs nom et prenom dans le fichier xml avec leur valeur et creation de l'element user
	 * @param doc ou on veut creer le noeud user
	 * @param idUser l'identiant de l'utilisateur
	 * @param nom le champ qu'on veut creer
	 * @param prenom le champ qu'on veut creer
	 * @return la noeud user qu'on a creer 
	 */
	private static Node getUser(Document doc, int idUser, String nom, String prenom) {
	    Element user = doc.createElement("user");
	    user.setAttribute("id", Integer.toString(idUser));
	    user.appendChild(getUserElements(doc, "nom", nom));
	    user.appendChild(getUserElements(doc, "prenom", prenom));
	
	    return user;
	}

	/**
	 * creer et remplir le champ champ avec sa valeur 
	 * @param doc ou se trouve le noeud
	 * @param champ le champ a créer
	 * @param content la valeur du champ 
	 * @return la noeud créee
	 */
	private static Node getUserElements(Document doc, String champ, String content) {
	    Element node = doc.createElement(champ);
	    node.appendChild(doc.createTextNode(content));
	    return node;
	} 
	
	 /**
	  *modifie le profil de l'idUser donné 
	  * @param idUser identifiant de l'utilisateur
	  */
	public void modifierProfil(int idUser) {
		 try{
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
			Document doc = null;
			doc =parser(nomFichier);
	
		    Element racine = doc.getDocumentElement();
	
		    NodeList racineNoeuds = racine.getChildNodes();
		 	int nbRacineNoeuds = racineNoeuds.getLength();
		 	sc = new Scanner(System.in);   
			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element personne = (Element) racineNoeuds.item(i);
			        String id = personne.getAttribute("id");
			        if (id.compareTo(Integer.toString(idUser))==0){
			        	for(int j=0; j<personne.getChildNodes().getLength(); j++){
				    		Node noeudEnPlus = personne.getChildNodes().item(j);
				    		System.out.println("Modification du Profil: " + noeudEnPlus.getNodeName() );
				    		String rep = sc.nextLine();
				    		noeudEnPlus.setTextContent(rep);
				    		}
			            }
			    	}
			    }
	
			    save();
		 	   stmt.close();
		 	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	 
	 /**
	 *modifie le champ donné avec le contenu content de l'idUser donné 
	 * @param idUser l'identifiant de l'utilisateur
	 * @param champ le nom du champ
	 * @param content le contenu du champ
	 */
	public void modifierProfilParChamp(int idUser, String champ, String content) {
		 try{
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
			Document doc = null;
			doc =parser(nomFichier);

		    Element racine = doc.getDocumentElement();

		    NodeList racineNoeuds = racine.getChildNodes();
		 	int nbRacineNoeuds = racineNoeuds.getLength();

			for (int i = 0; i<nbRacineNoeuds; i++) {
			  if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    Element personne = (Element) racineNoeuds.item(i);
			    String id = personne.getAttribute("id");
			    NodeList noeudListe = personne.getChildNodes();
			    
			    for (int j = 0; j<noeudListe.getLength(); j++) {
			    	Node noeudCourant = noeudListe.item(j);
			    	if ((id.compareTo(Integer.toString(idUser))==0) && (noeudCourant.getNodeName().compareTo(champ)==0) ){
			    		noeudCourant.setTextContent(content);
			    	}
			    }
			  }
			}

			save();
			stmt.close();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	 }
	 
	 /**
	 *modifie le login de l'idUser donné
	 * @param idUser l'identifiant de l'utilisateur
	 * @param userName le nouveau login de l'utilisateur
	 */
	public void modifierUsername(int idUser , String userName) {
		 try{
		    
	         Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	
	         Statement stmt = conn.createStatement();
	         String rqt = "update Utilisateur set username ='" + userName + "' WHERE idUser = " + idUser ;
	         stmt.executeUpdate(rqt);
	         stmt.close();
	         commit();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	 };
	 
	 /**
	 *modifie le mot de passe de l'idUser donné
	 * @param idUser l'identifiant de l'utilisateur
	 * @param password le nouveau mot de passe de l'utilisateur
	 */
	public void modifierPassword(int idUser , String password){
		 try{  
	         Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	
	         Statement stmt = conn.createStatement();
	         String rqt = "update Utilisateur set userpassword ='" + password + "' WHERE idUser = " + idUser ;
	         stmt.executeUpdate(rqt);
	         stmt.close();
	         commit();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
	 };
 
	 /**
	 *supprime l'utilisateur de la base de donné et du fichier xml
	 * @param idUser l'identifiant de l'utilisateur
	 */
	public void supprimerUser(int idUser){
		try{   
		     Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
		
		     Statement stmt = conn.createStatement();
		     String rqt = "delete from Utilisateur WHERE idUser=" + idUser ;
		     String rqt2 = "delete from Relation WHERE idUser1=" + idUser+ " or idUser2 = "+ idUser;
		     stmt.executeUpdate(rqt2);
		     stmt.executeUpdate(rqt);
		     Document doc = null;
			 doc =parser("Exemple.xml");
	
	
	
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
	
	        save();
		 	stmt.close();
		 	commit();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	 }

	
	 /**
	 * Ajoute champ avec le contenu content pour l'idUser donné
	 * @param champ le champ a ajouter
	 * @param idUser l'identifiant de l'utilisateur
	 * @param content le contenu du champ
	 */
	public void ajouterChamp(String champ, int idUser, String content) {
		 try{
			 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			 Statement stmt = conn.createStatement();
			 Document doc = null;
			 doc =parser("Exemple.xml");
	
	
	
		        Element racine = doc.getDocumentElement();
	
		        NodeList racineNoeuds = racine.getChildNodes();
		 	    int nbRacineNoeuds = racineNoeuds.getLength();
		 	    
			    for (int i = 0; i<nbRacineNoeuds; i++) {
			    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    		Element personne = (Element) racineNoeuds.item(i);
			            String id = personne.getAttribute("id");
			            if (id.compareTo(Integer.toString(idUser))==0){
			            	personne.appendChild(getUserElements(doc,champ,content));
			            }	        
			    	}
			    }
			        
			   save();
		 	   stmt.close();
		 	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 	}
	    }
	 
	 /**
	 *ajoute champ dans le fichier xml (pour tous les utilisateurs) 
	 * @param champ le champ a ajouter
	 */
	public void ajouterChamp(String champ){
		 try{
			 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			 Statement stmt = conn.createStatement();
			 Document doc = null;
			 doc =parser(nomFichier);
		      
			 Element racine = doc.getDocumentElement();
		     NodeList racineNoeuds = racine.getChildNodes();
		 	 int nbRacineNoeuds = racineNoeuds.getLength();
			    for (int i = 0; i<nbRacineNoeuds; i++) {
			    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    		Element personne = (Element) racineNoeuds.item(i);
			    		personne.appendChild(getUserElements(doc,champ,""));   	        
			    	}
			    }
			        
			   save();
		 	   stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	
   	 /**
   	 *Supprime champ dans le fichier xml pour l'idUser donné 
   	 * @param champ le champ supprimé
   	 * @param idUser l'identifiant de l'utilisateur
   	 */
   	public void supprimerChamp(String champ, int idUser) {
   		 try {
	   		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();	
			Document doc = null;
			 doc =parser(nomFichier);
	
	
			 Element racine = doc.getDocumentElement();
	
		        NodeList racineNoeuds = racine.getChildNodes();
		 	    int nbRacineNoeuds = racineNoeuds.getLength();
		 	    
			    for (int i = 0; i<nbRacineNoeuds; i++) {
			    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    		Element personne = (Element) racineNoeuds.item(i);
			            String id = personne.getAttribute("id");
			            if (id.compareTo(Integer.toString(idUser))==0){
			            	NodeList champElements =doc.getElementsByTagName(champ);
			            	Node champElt = champElements.item(0);
			            	personne.removeChild(champElt);
			            	
			            }      
			    	} 
			    } 
	
		        save();
			 	stmt.close();
			 	commit();
   		 	} catch (SQLException e) {
				// TODO Auto-generated catch block
   		 		e.printStackTrace();
   		 	}
	    }
	  
   	/**
   	 * Supprime champ dans le fichier xml pour tous les utilisateurs
   	 * @param champ le champ supprimé
   	 */
   	public void supprimerChamp(String champ) {
   		try{
	   		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();	
			Document doc = null;
			 doc = parser(nomFichier);
	
	
			 Element racine = doc.getDocumentElement();
	
		     NodeList racineNoeuds = racine.getChildNodes();
		     
		 	 int nbRacineNoeuds = racineNoeuds.getLength();
		 	    
			 for (int i = 0; i<nbRacineNoeuds; i++) {
				 if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    	Element personne = (Element) racineNoeuds.item(i);
			    	int nbPersonneNoeuds = personne.getChildNodes().getLength();
			   		int j = 0;
			   		while ( j<nbPersonneNoeuds){
			   			if (personne.getElementsByTagName(champ).getLength()>0){
			   				NodeList champElements =personne.getElementsByTagName(champ);
		    				Node champElt = champElements.item(j);
		    				personne.removeChild(champElt);		    				
			    		}
			    		j++;      
			    	} 
			   	} 
			   } 
	
			 save();
			 stmt.close();
			 commit();
   		} catch (SQLException e) {
   			e.printStackTrace();
		}
	 }
   	 
   	 /**
   	 * sauvegarde le fichier 
   	 */
   	public static void save(){
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
	 	     File outputFile = new File(chemin+nomFichier);
	 	    StreamResult result = new StreamResult(outputFile );
	 	    // creating output stream
	 	    try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	 
   	 }
   	 
   	/**
   	 * parse le fichier donné
   	 * @param file le nom du fichier
   	 * @return document
   	 */
   	public static Document parser(String file){

	    	File inputFile = new File(file);
	    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
	
			try {
				doc = dBuilder.parse(inputFile);
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}

		
		return doc;
   	}
	
}	


