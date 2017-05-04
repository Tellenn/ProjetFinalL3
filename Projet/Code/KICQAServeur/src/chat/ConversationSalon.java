package chat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConversationSalon {
	private Document doc;
	private final String chemin = "xml/chat/conversationSalon/";
	private String nomFichier;

	public ConversationSalon(int idSalon) {
		System.out.println("Begin conversation salon");
		nomFichier = idSalon + ".xml";
		try {
			// analyse du document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder p = dbFactory.newDocumentBuilder();
			File monFichier = new File(chemin + nomFichier);
			if (monFichier.exists()) {
				// récupération de la structure objet du document
				doc = p.parse(chemin + nomFichier);
				System.out.println("[INFO] Nous avons " + doc.getElementsByTagName("message").getLength()
						+ " message(s) pour cette conversation");
			} else {
				System.out.println("[System]Bug " + monFichier.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ConversationSalon(int idSalon, String nomSalon) {
		// TODO: créer un salon
		System.out.println("Begin conversation salon");
		nomFichier = idSalon + ".xml";
		try {
			// analyse du document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder p = dbFactory.newDocumentBuilder();
			File monFichier = new File(chemin + nomFichier);
			if (monFichier.exists()) {
				// récupération de la structure objet du document
				doc = p.parse(chemin + nomFichier);
				System.out.println("[INFO] Nous avons " + doc.getElementsByTagName("message").getLength()
						+ " message(s) pour cette conversation");
			} else {
				System.out.println("else" + monFichier.getAbsolutePath());
				doc = p.newDocument();
				Element chat = doc.createElement("chat");
				doc.appendChild(chat);
			}
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
			e.printStackTrace();
		}
	}

	// ajoute un message au document doc
	public void ajouterMessage(int i, String text) {
		Element message = doc.createElement("message");
		message.setAttribute("id", Integer.toString(doc.getElementsByTagName("message").getLength() + 1));
		message.setAttribute("emetteur", Integer.toString(i));
		Date aujourdhui = new Date();
		message.setAttribute("date", new SimpleDateFormat("dd/MM/yyyy").format(aujourdhui));
		message.setAttribute("heure", new SimpleDateFormat("HH:mm:ss").format(aujourdhui));
		message.setTextContent(text);
		doc.getFirstChild().appendChild(message);
		save();
	}

	public String toString() {
		String conversation = "";
		NodeList messages = doc.getElementsByTagName("message");
		for (int i = 0; i < messages.getLength(); i++) {
			conversation += messages.item(i).getTextContent() + "\n";
		}
		return conversation;
	}

	// on retourne un treemap, au moins le développeur aura le choix de faire
	// ce qu'il veut avec toutes les infos
	public TreeMap<String, String> getMessage(int i) {
		TreeMap<String, String> t = new TreeMap<String, String>();
		NodeList messages = doc.getElementsByTagName("message");
		Element message = (Element) messages.item(i - 1);

		t.put("id", message.getAttribute("id"));
		t.put("emetteur", message.getAttribute("emetteur"));
		t.put("date", message.getAttribute("date"));
		t.put("heure", message.getAttribute("heure"));
		t.put("text", message.getTextContent());
		return t;
	}

	public List<TreeMap<String, String>> getMessages() {
		List<TreeMap<String, String>> tt = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> t;
		NodeList messages = doc.getElementsByTagName("message");

		for (int i = 0; i < messages.getLength(); i++) {
			t = new TreeMap<String, String>();
			Element message = (Element) messages.item(i);

			t.put("id", message.getAttribute("id"));
			t.put("emetteur", message.getAttribute("emetteur"));
			t.put("date", message.getAttribute("date"));
			t.put("heure", message.getAttribute("heure"));
			t.put("text", message.getTextContent());
			tt.add(t);
		}
		return tt;
	}

	// on retourne un treemap, au moins le développeur aura le choix de faire
	// ce qu'il veut avec toutes les infos
	public TreeMap<String, String> getLastMessage() {
		NodeList messages = doc.getElementsByTagName("message");
		return new TreeMap<String, String>(getMessage(messages.getLength()));
	}

	public String getNameSalon() {
		Element message = (Element) doc.getElementsByTagName("chat");
		return message.getAttribute("name");
	}
}
