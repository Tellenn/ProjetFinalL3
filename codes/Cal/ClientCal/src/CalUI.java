import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class CalUI {
	private 				Evenement 		event;
	private 				CalServerInt 	server;
	private static final 	String 			ip 		= "localhost";
	
	
	/**
	* Envoi l’ordre au serveur de créer un nouvel événement
	* @param dateDebut	date de début de l’événement
	* @param dateFin	date de fin de l’événement
	* @param libelle	libelle de l’événement
	*/
	public void createEventOrder(String libelle, Date dateDebut, Date dateFin) throws RemoteException{
		event = new Evenement(libelle, dateDebut, dateFin);
		try {
			server = (CalServerInt) Naming.lookup("rmi://" + ip + "/myabc");
			server.createEvent(event);
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	* Envoi l’ordre au serveur de modifier l’événement identifié par idEvent
	* @param idEvent	id de l’événement à mettre à jour
	* @param dateDebut	nouvelle date de début de l’événement
	* @param dateFin	nouvelle date de fin de l’événement
	* @param libelle	nouveau libellé de l’événement
	*/
	void updateEventOrder(int idEvent, Date dateDebut, Date dateFin, String libelle){
		
	}
	
//	public void doConnect() {
//		if (connect.getText().equals("Connexion")) {
//			if (login.getText().length() < 2) {
//				// il faut que le nom ait plus de 2 caractères
//				JOptionPane.showMessageDialog(frame, "Format du mot de login impossible");
//				return;
//			}
//			if (mdp.getText().length() < 2) {
//				//il faut que le champ ne soit pas vide
//				JOptionPane.showMessageDialog(frame, "Format du mot de passe impossible");
//				return;
//			}
//			try {
//				//ip.getText()
//				client = new CalClient(login.getText());
//				client.setGUI(this);
//				server = (CalServerInt) Naming.lookup("rmi://" + ip + "/myabc");
//
//				if(server.login(client)){
//					updateUsers(server.getConnected());
//					connect.setText("Déconnexion");
//				}else{
//					System.out.println("else");
//					JOptionPane.showMessageDialog(frame, "Identification impossible, Veuillez écrire un login ou mot de passe correct");
//					return;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(frame, "ERROR, Connexion impossible....");
//			}
//		}		
//		else {	updateUsers(null);
//			if (connect.getText().equals("Déconnexion")) {
//				connect.setText("Connexion");
//				
//			}
//			//connect.setText("Connecté ");
//			// Better to implement Logout ....
//		}
//	}
//
//	public void sendText() {
//		if (connect.getText().equals("Connexion")) {
//			JOptionPane.showMessageDialog(frame, "You need to connect first.");
//			return;
//		}
//		String st = tf.getText();
//		st = "[" + login.getText() + "] " + st;
//		
//		// Remove if you are going to implement for remote invocation
//		try {
//			server.publish(st,1);
//			tf.setText("");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void writeMsg(String st) {
//		tx.setText(tx.getText() + "\n" + st);
//	}
//
//	public void updateUsers(Vector v) {
//		DefaultListModel listModel = new DefaultListModel();
//		if (v != null)
//			for (int i = 0; i < v.size(); i++) {
//				try {
//					String tmp = ((CalClientInt) v.get(i)).getName();
//					listModel.addElement(tmp);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		lst.setModel(listModel);
//	}
//
	public static void main(String[] args) {
		System.out.println("Hello World !");
		
	}
//
//	// User Interface code.
//	public CalUI() {
//		frame = new JFrame("Groupe Chat");
//		JPanel main = new JPanel();
//		JPanel top = new JPanel();
//		JPanel cn = new JPanel();
//		JPanel bottom = new JPanel();
////		mdp = new JPasswordField();
//		tf = new JTextField();
////		login = new JTextField();
//		tx = new JTextArea();
//		connect = new JButton("Connexion");
//		JButton bt = new JButton("Envoyer");
//		lst = new JList();
//		
//		JTextField dateDebut = new JTextField();
//		JTextField dateFin = new JTextField();
//		JTextField libelle = new JTextField();
//		
//		main.setLayout(new BorderLayout(5, 5));
//		top.setLayout(new GridLayout(1, 0, 5, 5));
//		cn.setLayout(new BorderLayout(5, 5));
//		bottom.setLayout(new BorderLayout(5, 5));
////		top.add(new JLabel("Login: "));
////		top.add(login);
////		top.add(new JLabel("Mot de passe: "));
////		top.add(mdp);
////		top.add(connect);
//		
//		top.add(new JLabel("Date de debut: "));
//		top.add(dateDebut);
//		top.add(new JLabel("Date de fin: "));
//		top.add(dateFin);
//		top.add(new JLabel("Libelle: "));
//		top.add(libelle);
//		
//		cn.add(new JScrollPane(tx), BorderLayout.CENTER);
//		cn.add(lst, BorderLayout.EAST);
//		bottom.add(tf, BorderLayout.CENTER);
//		bottom.add(bt, BorderLayout.EAST);
//		main.add(top, BorderLayout.NORTH);
//		main.add(cn, BorderLayout.CENTER);
//		main.add(bottom, BorderLayout.SOUTH);
//		main.setBorder(new EmptyBorder(10, 10, 10, 10));
//		// Events
//		connect.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				doConnect();
//			}
//		});
//		bt.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sendText();
//			}
//		});
//		tf.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				sendText();
//			}
//		});
//
//		frame.setContentPane(main);
//		frame.setSize(600, 600);
//		frame.setVisible(true);
//	}
//
//	JTextArea tx;
//	JTextField tf, mdp, login;
//	JButton connect;
//	JList lst;
//	JFrame frame;
}