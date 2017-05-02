import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.*;

public class UserUI {
	private 				UserClient 		client;
	private 				UserServerInt 	server;
	private static final 	String 			ip 		= "152.77.82.30";
	
	public void doConnect() {
		if (connect.getText().equals("Connexion")) {
			if (login.getText().length() < 2) {
				// il faut que le nom ait plus de 2 caractères
				JOptionPane.showMessageDialog(frame, "Format du mot de login impossible");
				return;
			}
			if (mdp.getText().length() < 2) {
				//il faut que le champ ne soit pas vide
				JOptionPane.showMessageDialog(frame, "Format du mot de passe impossible");
				return;
			}
			try {
				//ip.getText()
				client = new UserClient(login.getText());
				client.setGUI(this);
				server = (UserServerInt) Naming.lookup("rmi://" + ip + "/myabc");

				if(server.login(client)){
					updateUsers(server.getConnected());
					connect.setText("Déconnexion");
				}else{
					System.out.println("else");
					JOptionPane.showMessageDialog(frame, "Identification impossible, Veuillez écrire un login ou mot de passe correct");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "ERROR, Connexion impossible....");
			}
		}		
		else {	updateUsers(null);
			if (connect.getText().equals("Déconnexion")) {
				connect.setText("Connexion");
				
			}
			//connect.setText("Connecté ");
			// Better to implement Logout ....
		}
	}

	public void sendText() {
		if (connect.getText().equals("Connexion")) {
			JOptionPane.showMessageDialog(frame, "You need to connect first.");
			return;
		}
		String st = tf.getText();
		st = "[" + login.getText() + "] " + st;
		
		// Remove if you are going to implement for remote invocation
		try {
			server.publish(st,3);
			tf.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeMsg(String st) {
		tx.setText(tx.getText() + "\n" + st);
	}

	public void updateUsers(Vector v) {
		DefaultListModel listModel = new DefaultListModel();
		if (v != null)
			for (int i = 0; i < v.size(); i++) {
				try {
					String tmp = ((UserClientInt) v.get(i)).getName();
					listModel.addElement(tmp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		lst.setModel(listModel);
	}

	public static void main(String[] args) {
		System.out.println("[System] Chat on.");
		UserUI c = new UserUI();
	}

	// User Interface code.
	public UserUI() {
		frame = new JFrame("Groupe Chat");
		JPanel main = new JPanel();
		JPanel top = new JPanel();
		JPanel cn = new JPanel();
		JPanel bottom = new JPanel();
		//	JList list = new JList(new DefaultListModel<String>());
	//		west.add(new JScrollPane(tx), BorderLayout.WEST);
	//	DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
	//	model.addElement("Antoine");
	//	model.addElement("Carine");
	//	list.setModel(model);
		
		
		mdp = new JPasswordField();
		tf = new JTextField();
		login = new JTextField();
		tx = new JTextArea();
		connect = new JButton("Connexion");
		JButton bt = new JButton("Envoyer");
		lst = new JList();
		main.setLayout(new BorderLayout(5, 5));
		top.setLayout(new GridLayout(1, 0, 5, 5));
		cn.setLayout(new BorderLayout(5, 5));
		bottom.setLayout(new BorderLayout(5, 5));
		top.add(new JLabel("Login: "));
		login.setText("charroan");
		mdp.setText("toto123");
		top.add(login);
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

		frame.setContentPane(main);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

	JTextArea tx;
	JTextField tf, mdp, login;
	JButton connect;
	JList lst;
	JFrame frame;
}