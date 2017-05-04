
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

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
	
	private static Document doc;
	private final static String chemin = "xml/";
	private static String nomFichier = "user.xml";

	
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
					System.out.println("else" + monFichier.getAbsolutePath());
					doc =  p.newDocument();
					Element users = doc.createElement("users");
					doc.appendChild(users);
					save();
				}
	        }catch (Exception e) {
	        	System.out.println(e);
	        }
	       

	}

    /**
     *  execute la requête commit après chaque méthode
     * @throws SQLException 
     */
    public static void commit() throws SQLException {
       
    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
        Statement st = conn.createStatement();
        st.executeQuery("commit");
        st.close();
       
    };
   
	
	    /**
	     * insert dans la table Utilisateur l' identifiant de l'Utilisateur, son login et son mot de passe.
	     * @param idUser l'identifiant de l'utilisateur
	     * @param userName le nouveau login de l'utilisateur
	     * @param password le nouveau mot de passe de l'utilisateur
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
					e.printStackTrace();
				}
				String nom ="";
				String prenom="";
				ajouterProfil(idUser,nom,prenom);
	    	 
	    };
	    
	    /**
	     * insert dans la table Admin l' identifiant du nouveau admin.
	     *  @param idUser l'identifiant de l'utilisateur
	     */
	    public void ajouterAdmin(int idUser) {
	    
	            Connection conn;
				try {
					conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
					Statement stmt;
					stmt = conn.createStatement();
					String rqt = "insert into Admin values (" + idUser +")";
					stmt.executeUpdate(rqt);
					stmt.close();
					commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    	 
	    };
	    
	  /**
	     * insert la relation entre idUser1 et idUser2 dans la base de donnÃ©es
	     * @param idUser1 reprÃ©sente l'identifiant du premier utilisateur
	     * @param idUser2 reprÃ©sente l'identifiant du second utilisateur
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
				e.printStackTrace();
			}
    	 
    };
    /**
     * Supprime la relation entre IdUser1 et IdUser2 de la base de donnÃ©es
     * @param idUser1 reprÃ©sente l'identifiant du premier utilisateur
     * @param idUser2 reprÃ©sente l'identifiant du second utilisateur
     */
    public void supprimerRelation(int idUser1 , int idUser2){
    	try {
	        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
	
	        Statement stmt = conn.createStatement();
	        String rqt = "delete FROM Relation WHERE idUser1=" + idUser1 + "and idUser2=" + idUser2 ;
	        stmt.executeUpdate(rqt);
	        System.out.println("delete relation");
	        stmt.close();
	        commit();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    };
	    
	    /**
	     * calcule l'incrémentation de idUser en executant une requÃªte qui cherche le dernier idUser dans la table Utilisateur. 
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
				e.printStackTrace();
			}
	    	return idUser;

	        
	    }
	    
	    
	    /**
	     * recherche l'idUser dans la base de donnÃ©es
	     * @param userName le login de l'utilisateur
	     * @return l'idUser de l'utilisateur ayant le login userName
	     */
	    public int rechercheUtilisateur(String userName){
	    	int idUser=0;
	        try {
		        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			    Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery("SELECT idUser FROM Utilisateur WHERE lower(username) = lower('" + userName + "')");
		        if (rs.next()){ 
		        	do {
		            	idUser = idUser = rs.getInt(1);
		            } while (rs.next());
		        }
		        stmt.close();
		        commit();
	        } catch (SQLException e) {
				e.printStackTrace();
			}
	        return idUser;
	      
	    }
	    
	    
	    
	     /**
	     * recherche l'idUser dans la table Admin
	     * @param userName le login de l'utilisateur
	     * @return l'idUser de l'utilisateur ayant le login userName et qui est un admin
	     */
	    public int rechercheAdmin(String userName){
	        int idUser=0;
	        try {
		        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			    Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery("SELECT idUser FROM Admin natural join Utilisateur WHERE lower(username) = lower('" + userName + "')");
		        if (rs.next()) {
		        	do {
		            	idUser = rs.getInt(1);
		            } while (rs.next());
		        }
		        stmt.close();
		        commit();
	        } catch (SQLException e) {
				e.printStackTrace();
			}
	        return idUser;
	      
	    }
	    
	    
	    /**
	     * affiche le profil de l'idUser donnÃ©
	     * @param idUser l'identifiant de l'utilisateur qu'on veut charger son profil
	     * @return un tableau avec les champs du profil et son contenu
	     */
	    public TreeMap<String, String> chargerProfil(int idUser) {
	    	TreeMap<String, String> tt = new TreeMap<String, String>();
	    	try {
		    	 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
				 Statement stmt = conn.createStatement();
				 Document doc = null;
				 doc =parser();
	
	 
				 Element racine = doc.getDocumentElement();
	
				 NodeList racineNoeuds = racine.getChildNodes();
		 	     int nbRacineNoeuds = racineNoeuds.getLength();
		 	    
			     for (int i = 0; i<nbRacineNoeuds; i++) {
			    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    		Element personne = (Element) racineNoeuds.item(i);
			    		
			 		     //Affichage d'une personne
			            String id = personne.getAttribute("id");
			            if (id.compareTo(Integer.toString(idUser))==0){
			    		    for(int j=0; j<personne.getChildNodes().getLength(); j++){
				    			Node noeudEnPlus = personne.getChildNodes().item(j);
				    			tt.put(noeudEnPlus.getNodeName(), noeudEnPlus.getTextContent());
				    		}	            	 
			            }
			    	}
			    }
			    stmt.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return tt;
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
				 doc = parser();
	
	 
		        Element racine = doc.getDocumentElement();
	
		        
		        racine.appendChild(getUser(doc, idUser, nom, prenom));
		        
	
		        save();
		 	   	stmt.close();
	    	} catch (SQLException e) {
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
	 * @param champ le champ a crÃ©er
	 * @param content la valeur du champ 
	 * @return la noeud créee
	 */
	private static Node getUserElements(Document doc, String champ, String content) {
	    Element node = doc.createElement(champ);
	    node.appendChild(doc.createTextNode(content));
	    return node;
	} 
	
	/**
	  *modifie le profil de l'idUser donnÃ© 
	  * @param idUser identifiant de l'utilisateur
	  */
	 public void modifierProfil(int idUser) {
		 try{
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
			Document doc = null;
			doc =parser();
	
		    Element racine = doc.getDocumentElement();
	
		    NodeList racineNoeuds = racine.getChildNodes();
		 	int nbRacineNoeuds = racineNoeuds.getLength();
		 	Scanner sc = new Scanner(System.in);   
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
				e.printStackTrace();
			}
	    }
	 
	  /**
	 *modifie le champ donnÃ© avec le contenu content de l'idUser donnÃ© 
	 * @param idUser l'identifiant de l'utilisateur
	 * @param champ le nom du champ
	 * @param content le contenu du champ
	 */
	 public void modifierProfilParChamp(int idUser, String champ, String content) {
		 try{
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
			Document doc = null;
			doc =parser();

		    Element racine = doc.getDocumentElement();

		    NodeList racineNoeuds = racine.getChildNodes();
		 	int nbRacineNoeuds = racineNoeuds.getLength();
		 	Scanner sc = new Scanner(System.in);
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
	 *modifie le mot de passe de l'idUser donnÃ©
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
				e.printStackTrace();
		 }
	 };
 
	/**
	 *supprime l'utilisateur de la base de donnÃ© et du fichier xml
	 * @param idUser l'identifiant de l'utilisateur
	 */
	public void supprimerUser(int idUser){
		try{   
		     Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
		
		     Statement stmt = conn.createStatement();
		     String rqt = "delete from Utilisateur WHERE idUser=" + idUser ;
		     String rqt2 = "delete from Relation WHERE idUser1=" + idUser+ " or idUser2 = "+ idUser;
		     String rqt3 = "delete from Admin WHERE idUser=" + idUser ;
		     stmt.executeUpdate(rqt2);
		     stmt.executeUpdate(rqt3);
		     stmt.executeUpdate(rqt);
		     Document doc = null;
			 doc =parser();
	
	
	
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
				e.printStackTrace();
		}
	 }

	/**
	 *supprime l'admin de la table Admin
	 * @param idUser l'identifiant de l'admin
	 */
	public void supprimerAdmin(int idUser){
		 try {
		        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
		
		        Statement stmt = conn.createStatement();
		        String rqt = "delete FROM Admin WHERE idUser=" + idUser  ;
		        stmt.executeUpdate(rqt);
		        stmt.close();
		        commit();
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    };
	 
	/**
	 * Ajoute champ avec le contenu content pour l'idUser donnÃ©
	 * @param champ le champ a ajouter
	 * @param idUser l'identifiant de l'utilisateur
	 * @param content le contenu du champ
	 */
	 public void ajouterChamp(String champ, int idUser, String content) {
		 try{
			 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			 Statement stmt = conn.createStatement();
			 Document doc = null;
			 doc =parser();
	
	
	
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
			 doc =parser();
		      
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
				e.printStackTrace();
			}
	    }
	 
    
   	/**
   	 *Supprime champ dans le fichier xml pour l'idUser donnÃ© 
   	 * @param champ le champ supprimÃ©
   	 * @param idUser l'identifiant de l'utilisateur
   	 */	
   	 public void supprimerChamp(String champ, int idUser) {
   		 try {
	   		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();	
			Document doc = null;
			 doc =parser();
	
	
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
   		 		e.printStackTrace();
   		 	}
	    }
	
	 /**
   	 * Supprime champ dans le fichier xml pour tous les utilisateurs
   	 * @param champ le champ supprimÃ©
   	 */  
   	public void supprimerChamp(String champ) {
   		try{
	   		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();	
			Document doc = null;
			 doc = parser();
	
	
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
	private void save() {
   			TransformerFactory transformerFactory = TransformerFactory.newInstance();
   	        Transformer transformer;
   			try {
   				transformer = transformerFactory.newTransformer();			
   		        DOMSource source = new DOMSource(doc);
   		
   		        StreamResult result = new StreamResult(chemin + nomFichier);
   		        transformer.transform(source, result);
   			} catch (TransformerException e) {
   				e.printStackTrace();
   			}		
   		}
   	
	 /**
   	 * parse le fichier donnÃ©
   	 * @param file le nom du fichier
   	 * @return document
   	 */
   	public static Document parser(){
   		
        try {
            // analyse du document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder p = dbFactory.newDocumentBuilder();
            File monFichier = new File(chemin+nomFichier); 
			if(monFichier.exists()){	
	            // récupération de la structure objet du document
	           doc = p.parse(chemin+nomFichier);
			}else{
				doc =  p.newDocument();
				Element users = doc.createElement("users");
				doc.appendChild(users);
			}
        } catch (Exception e) {
        	System.out.println(e);
        }
		
		return doc;
   	}
	
}	


