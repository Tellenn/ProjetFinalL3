package chat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConversationPrivee {
	private Document doc;
	private final String chemin = "xml/chat/conversationPrivee/";
	private String nomFichier;
	
	public ConversationPrivee(int id1 , int id2) {
			System.out.println("Begin");
			nomFichier = id1 + "-" + id2 + ".xml";
			System.out.println("111111");		
		        try {
		            // analyse du document
		            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder p = dbFactory.newDocumentBuilder();
		            System.out.println("22222222222");
		            File monFichier = new File(chemin+nomFichier); 
		            System.out.println("3333333333");
					if(monFichier.exists()){	
			            // récupération de la structure objet du document
			           doc = p.parse(chemin+nomFichier);
			           System.out.println("[INFO] Nous avons " + doc.getElementsByTagName("message").getLength() + " message(s) pour cette conversation");
					}else{
						System.out.println("else" + monFichier.getAbsolutePath());
						doc =  p.newDocument();
						Element chat = doc.createElement("chat");
						doc.appendChild(chat);
					}
		           //System.out.println("[INFO] Nous avons " + doc.getElementsByTagName("message").getLength() + " message(s) pour cette conversation");
	
		           // ajouterMessage(1, "test 1 vers 2");
		            
		            //*********************************************************************
		            // 2- Le troisème acteur du deuxième film
		            // Récupération du deuxième film
		            /*Element el = (Element) doc.getElementsByTagName("film").item(1);
		            System.out.println(el.getElementsByTagName("acteur").item(2));
	
		            // 3- Titres des films dont la fiche est en anglais:
		            // Récupération de la liste des films
		            NodeList lesFilms = doc.getElementsByTagName("film");
		            for (int i = 0; i < lesFilms.getLength(); i++) {
		                Element film = (Element) lesFilms.item(i);
	//	            for (Node n: lesFilms) { // Ne fonctionne pas !
	//	                Element film = (Element) n;
		                String langue = film.getAttribute("lang");
		                if (langue.equals("en")) {
		                    System.out.println(film.getElementsByTagName("titre").item(0).getTextContent());
		                }
		            }
	
		            // 4- Le nom du personnage dont l'identifiant est lukemonfils
		            // On récupère tous les personnages
		            NodeList lesActeurs = doc.getElementsByTagName("acteur");
		            // On recherche celui dont l'id est lukemonfils
		            String luke = "lukemonfils";
		            int nb = 0;
		            boolean trouvé = false;
		            while ((nb < lesActeurs.getLength()) && (trouvé == false)) {
		                Element acteur = (Element) lesActeurs.item(nb);
		                if (acteur.getAttribute("id").equals(luke)) {
		                    trouvé = true;
		                }
		                nb++;
		            }
		            if (trouvé) {
		                Element acteur = (Element) lesActeurs.item(nb - 1);
		                System.out.println(acteur.getAttribute("personnage"));
		            }
		            // Sinon, on utilise XPath...
		            String xpathExpression = "//acteur[@id='lukemonfils']";
		            XPath xpath = XPathFactory.newInstance().newXPath();
		            NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
		            for (int i = 0; i < nodes.getLength(); i++) {
		                Element perso = (Element) nodes.item(i);
		                System.out.println(perso.getAttribute("personnage"));
		            }
	
		            // 5- Le titre des films sortis en 1981
		            // On récupère les films
		            System.out.println("Les films sortis en 1981");
		            NodeList theFilms = doc.getElementsByTagName("film");
		            for (int i = 0; i < theFilms.getLength(); i++) {
		                Element film = (Element) theFilms.item(i);
		                Element annee = (Element) film.getElementsByTagName("année").item(0);
		                String year = annee.getTextContent();
		                if (year.equals("1981")) {
		                    Element titre = (Element) film.getElementsByTagName("titre").item(0);
		                    System.out.println(titre.getTextContent());
		                }
		            }
	
		            // 6- Le nombre de références faites au personnage dont l'identifiant est indy
		            // On récupère tous les noeuds ref
		            int count = 0;
		            NodeList references = doc.getElementsByTagName("perso");
		            for (int i = 0; i < references.getLength(); i++) {
		                Element ref = (Element) references.item(i);
		                if (ref.getAttribute("ref").equals("indy")) {
		                    System.out.println("Un indy trouvé !");
		                    count++;
		                }
		            }
		            System.out.println("Il y a " + count + " références au personnage indy.");
	
		            // 7- Les films sans acteurs
		            for (int i = 0; i < theFilms.getLength(); i++) {
		                Element film = (Element) theFilms.item(i);
		                if (film.getElementsByTagName("acteur").getLength() == 0) {
		                    System.out.println("Le film " + film.getElementsByTagName("titre").item(0).getTextContent() + " n'a pas d'acteur");
		                }
		            }
		             */
			        } catch (Exception e) {
			        	System.out.println(e);
			        }
	}

	// Enregistre le document doc
	private void save() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();			
	        DOMSource source = new DOMSource(doc);
	
	        StreamResult result = new StreamResult(chemin + nomFichier);
	        transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// ajoute un message au document doc
	public void ajouterMessage(int i, String text) {
		Element message = doc.createElement("message");
		message.setAttribute("id", Integer.toString(doc.getElementsByTagName("message").getLength()+1));
		message.setAttribute("emetteur", Integer.toString(i));
		Date aujourdhui = new Date();
		
		message.setAttribute("date", new SimpleDateFormat("dd/MM/yyyy").format(aujourdhui));
		message.setAttribute("heure", new SimpleDateFormat("HH:mm:ss").format(aujourdhui));
		message.setTextContent(text);
	    doc.getFirstChild().appendChild(message);
	    save();
	}
}
