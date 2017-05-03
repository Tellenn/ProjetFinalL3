import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.Element;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.*;

public class ChatUI {
	private ChatClient client;
	private ChatServerInt server;
	private static final String ip = "152.77.82.32";
	private TreeMap<Integer, Integer> placesUsersDansListe = new TreeMap<Integer, Integer>();
	private TreeMap<Integer, Integer> placesSalonsDansListe = new TreeMap<Integer, Integer>();

	public void doConnect() {
		if (connect.getText().equals("Connexion")) {
			if (login.getText().length() < 2) {
				// il faut que le nom ait plus de 2 caractères
				JOptionPane.showMessageDialog(frame, "Format du login impossible");
				return;
			}
			if (mdp.getText().length() < 2) {
				// il faut que le champ ne soit pas vide
				JOptionPane.showMessageDialog(frame, "Format du mot de passe impossible");
				return;
			}
			try {
				client = new ChatClient(login.getText());
				client.setGUI(this);
				server = (ChatServerInt) Naming.lookup("rmi://" + ip + "/myabc");

				int id = server.login(client, login.getText(), mdp.getText());
				if (id != -1) {
					updateUsers(server.getConnected());
					System.out.println("Mon id client est " + id);
					client.setId(id);
					connect.setText("Déconnexion");
					//chargement des salons accessibles
					server.uploadSalon(client);
					updateSalonsListe();
					
				} else {
					JOptionPane.showMessageDialog(frame,
							"Identification impossible, Veuillez écrire un login ou mot de passe correct");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "ERROR, Connexion impossible....");
			}//Integer
		} else {
			updateUsers(null);
			if (connect.getText().equals("Déconnexion")) {
				connect.setText("Connexion");
			}
			// connect.setText("Connecté ");
			// Better to implement Logout ....
		}
	}

	public void sendText() {
		if (connect.getText().equals("Connexion")) {
			JOptionPane.showMessageDialog(frame, "You need to connect first.");
			return;
		}

		String st = tf.getText();
		// st = "[" + login.getText() + "] " + st;

		// Remove if you are going to implement for remote invocation
		if (client.getIdConversationPrivee() != -1) {
			try {
				server.publishPrivate(st, client.getId(), client.getIdConversationPrivee());
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			if (client.getNumSalon() != -1) {
				try {
					server.publish(st, client.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("[System] ERROR: Pas normal d'être là");
				// erreur
			}
		}
		tf.setText("");

	}

	public void writeMsg(String st) {
		tx.setText(tx.getText() + "\n" + st);
	}
// ================================================================================================== //
// Mise à jour
	public void updateUsers(TreeMap<Integer, ChatClientInt> v) {
		DefaultListModel listModel = new DefaultListModel();
		if (v != null){
			for (ChatClientInt item : v.values()) {
				try {
					listModel.addElement(item.getName());
					placesUsersDansListe.put(listModel.getSize() - 1, item.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		lst.setModel(listModel);
	}
	
	protected void updateSalonsListe() throws RemoteException {
	/*	TreeMap<Integer, String> pl = new TreeMap<Integer, String>(client.getSalons());
		DefaultListModel listModel = new DefaultListModel();
		for(int i =0; i<client.getNbSalons(); i++){	
				try {
					listModel.addElement(client.getName());
					placesUsersDansListe.put(listModel.getSize() - 1, item.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			lst.setModel(listModel);
		}*/
	}
// ================================================================================================== //
// ============================================== MAIN ============================================== //
// ================================================================================================== //
	public static void main(String[] args) {
		System.out.println("[System] Chat on.");
		ChatUI c = new ChatUI();
	}
	//a.tell("Connexion acceptée. ");
	// User Interface code.
	public ChatUI() {
		frame = new JFrame("Groupe Chat");
		JPanel main = new JPanel();
		JPanel top = new JPanel();
		JPanel cn = new JPanel();
		JPanel bottom = new JPanel();

		mdp = new JPasswordField();
		tf = new JTextField();
		tf.setEnabled(false);
		login = new JTextField();
		tx = new JTextArea();

		connect = new JButton("Connexion");
		JButton bt = new JButton("Envoyer");
		bt.setEnabled(false);
		lst = new JList();
		lstSalon = new JList();
		main.setLayout(new BorderLayout(5, 5));
		top.setLayout(new GridLayout(1, 0, 5, 5));
		cn.setLayout(new BorderLayout(5, 5));
		bottom.setLayout(new BorderLayout(5, 5));
		top.add(new JLabel("Login: "));
		top.add(login);
		login.setText("charroan");
		mdp.setText("toto123");
		top.add(new JLabel("Mot de passe: "));
		top.add(mdp);
		top.add(connect);
		cn.add(new JScrollPane(tx), BorderLayout.CENTER);
		cn.add(lst, BorderLayout.WEST);
		bottom.add(tf, BorderLayout.CENTER);
		bottom.add(bt, BorderLayout.EAST);

		main.add(top, BorderLayout.NORTH);
		main.add(cn, BorderLayout.CENTER);
		main.add(bottom, BorderLayout.SOUTH);
		main.setBorder(new EmptyBorder(10, 10, 10, 10));
		// Events
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doConnect();
			}
		});
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendText();
			}
		});
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendText();
			}
		});

		lst.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				System.out.println("[CHAT UI] J'ai été appelé");
				if (!lst.getValueIsAdjusting()) {
					System.out.println("[CHAT UI] J'ai été appelé 2");
					try {
						tf.setEnabled(true);
						bt.setEnabled(true);
						client.setIdConversationPrivee(
								server.getClient(placesUsersDansListe.get(lst.getSelectedIndex())).getId());

						System.out.println("[CHAT UI]Chargement de la conversation de " + client.getName() + " id "
								+ client.getId() + " avec "
								+ server.getClient(placesUsersDansListe.get(lst.getSelectedIndex())).getName() + " id "
								+ server.getClient(placesUsersDansListe.get(lst.getSelectedIndex())).getId());

						tx.setText("");// reset
						uploadText(client.getIdConversationPrivee());
					} catch (RemoteException e) {}
				}
			}
		});
		lstSalon.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				System.out.println("[CHAT UI-SALON] J'ai été appelé");
				if (!lst.getValueIsAdjusting()) {
					System.out.println("[CHAT UI] J'ai été appelé 2");
					try {
						tf.setEnabled(true);
						bt.setEnabled(true);
						client.setNumSalon(
								server.getClient(placesUsersDansListe.get(lst.getSelectedIndex())).getId());

						System.out.println("[CHAT UI]Chargement du chat de "+ server.getClient(placesUsersDansListe.get(lst.getSelectedIndex())).getName() + " id "
								+ server.getClient(placesUsersDansListe.get(lst.getSelectedIndex())).getId());

						tx.setText("");// reset
						uploadText(client.getIdConversationPrivee());
					} catch (RemoteException e) {}
				}
			}
		});
		frame.setContentPane(main);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

	protected void uploadText(int idPersonne2) throws RemoteException {
		// tx.setText()
		server.uploadConversation(client, idPersonne2);// reset
	}

	
	JTextArea tx;
	JTextField tf, mdp, login;
	JButton connect;
	JList lst, lstSalon;
	JFrame frame;
}