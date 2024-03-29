package user;
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
	
	private Document doc;
	private final String chemin = "../";
	private String nomFichier;

	
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
     * @throws SQLException 
     */
    public static void commit() throws SQLException {
       
    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
        Statement st = conn.createStatement();
        st.executeQuery("commit");
        st.close();
       
    };
   
	
	    /**
	     * insert dans la table Utilisateur l' identifiant de l'Utilisateur, son nom et son prénom.
	     * @throws SQLException 
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
	     * insert dans la table Utilisateur l' identifiant de l'Utilisateur, son nom et son prénom.
	     * @throws SQLException 
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String nom ="";
				String prenom="";
				ajouterProfil(idUser,nom,prenom);
	    	 
	    };
	    
	    
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
	     * @throws SQLException 
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
	     * recherche le login
	     * @throws SQLException 
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return idUser;
	      
	    }
	    
	    
	    
	    /**
	     * recherche le login
	     * @throws SQLException 
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return idUser;
	      
	    }
	    
	    
	    
	    public TreeMap<String, String> chargerProfil(int idUser) {
	    	TreeMap<String, String> tt = new TreeMap<String, String>();
	    	try {
		    	 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
				 Statement stmt = conn.createStatement();
				 Document doc = null;
				 doc =parser("Exemple.xml", doc);
	
	 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return tt;
	    }
	    
	    
	    
	    public void ajouterProfil(int idUser, String nom, String prenom) {
	    	try{
		    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
				Statement stmt = conn.createStatement();
				 Document doc = null;
				 doc = parser("Exemple.xml", doc);
	
	 
		        Element racine = doc.getDocumentElement();
	
		        
		        racine.appendChild(getUser(doc, idUser, nom, prenom));
		        
	
		        save(doc);
		 	   	stmt.close();
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	 public void modifierProfil(int idUser) {
		 try{
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
			Document doc = null;
			doc =parser("Exemple.xml", doc);
	
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
	
			    save(doc);
		 	   stmt.close();
		 	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	 
	 public void modifierProfilParChamp(int idUser, String champ, String content) {
		 try{
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();
			Document doc = null;
			doc =parser("Exemple.xml", doc);

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

			save(doc);
			stmt.close();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	 }
	 
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
			 doc =parser("Exemple.xml", doc);
	
	
	
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
	
	        save(doc);
		 	stmt.close();
		 	commit();
		 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	 }

	 public void supprimerAdmin(int idUser){
		 try {
		        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
		
		        Statement stmt = conn.createStatement();
		        String rqt = "delete FROM Admin WHERE idUser=" + idUser  ;
		        stmt.executeUpdate(rqt);
		        stmt.close();
		        commit();
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    };
	 
	 //Ajouter champ pour un user
	 public void ajouterChamp(String champ, int idUser, String content) {
		 try{
			 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			 Statement stmt = conn.createStatement();
			 Document doc = null;
			 doc =parser("Exemple.xml", doc);
	
	
	
		        Element racine = doc.getDocumentElement();
	
		        NodeList racineNoeuds = racine.getChildNodes();
		 	    int nbRacineNoeuds = racineNoeuds.getLength();
		 	    
			    for (int i = 0; i<nbRacineNoeuds; i++) {
			    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    		Element personne = (Element) racineNoeuds.item(i);
			            String id = personne.getAttribute("id");
			            if (id.compareTo(Integer.toString(idUser))==0){
			            	personne.appendChild(ajoutDansUserElements(doc,champ,content));
			            }	        
			    	}
			    }
			        
			   save(doc);
		 	   stmt.close();
		 	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 	}
	    }
	 
	 
	 //Ajouter champ pour tous les users
	 public void ajouterChamp(String champ){
		 try{
			 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			 Statement stmt = conn.createStatement();
			 Document doc = null;
			 doc =parser("Exemple.xml", doc);
		      
			 Element racine = doc.getDocumentElement();
		     NodeList racineNoeuds = racine.getChildNodes();
		 	 int nbRacineNoeuds = racineNoeuds.getLength();
			    for (int i = 0; i<nbRacineNoeuds; i++) {
			    	if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
			    		Element personne = (Element) racineNoeuds.item(i);
			    		personne.appendChild(ajoutDansUserElements(doc,champ,""));   	        
			    	}
			    }
			        
			   save(doc);
		 	   stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	 
		// utility method to create text node
    	private static Node ajoutDansUserElements(Document doc, String champ, String content) {
    	    Element node = doc.createElement(champ);
    	    node.appendChild(doc.createTextNode(content));
    	    return node;
    	} 
    	
    
   	 //Supprimer champ pour un user	
   	 public void supprimerChamp(String champ, int idUser) {
   		 try {
	   		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();	
			Document doc = null;
			 doc =parser("Exemple.xml", doc);
	
	
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
	
		        save(doc);
			 	stmt.close();
			 	commit();
   		 	} catch (SQLException e) {
				// TODO Auto-generated catch block
   		 		e.printStackTrace();
   		 	}
	    }
	 //Supprimer champ pour tous les users 
   	public void supprimerChamp(String champ) {
   		try{
	   		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "charroan", "Aclf2016");
			Statement stmt = conn.createStatement();	
			Document doc = null;
			 doc = parser("Exemple.xml", doc);
	
	
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
	
			 save(doc);
			 stmt.close();
			 commit();
   		} catch (SQLException e) {
   			e.printStackTrace();
		}
	 }
   	 
   	 public static void save(Document doc){
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
   	 }
   	 
   	public static Document parser(String file, Document doc){

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


