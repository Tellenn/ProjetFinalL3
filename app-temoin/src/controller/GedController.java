/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Client.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * FXML Controller class
 *
 * @author aminca
 */
public class GedController extends ControllerPere implements Initializable {
    @FXML
    private Button chat;
    @FXML
    private Button calendrier;
    @FXML
    private Button ged;
    @FXML
    private MenuButton moncompte;
    @FXML
    private MenuItem printProfil;
    @FXML
    private MenuItem deconnexion;
    @FXML
    private TableView<Fichier> tabGed;
    @FXML
    private TableColumn<Fichier,String> nom;
    @FXML
    private Button ouvrir;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button share;
    @FXML
    private Button refresh;
    private GEDServeurInt server = null;
    
    /**
     * Initializes the controller class.
     */ 
    @Override
   public void initialize(URL url, ResourceBundle rb) {
       /* try {
            System.out.println("Connection au serveur");
            server = Client.connectGED("127.0.0.1");
             System.out.println("Lecture des fils à la racine");
            Vector<Integer> ids = Client.getFilsFolder(server, 1);
            String filename;
            ObservableList<Fichier> files = FXCollections.observableArrayList();
             System.out.println("Association dans un ObservableList");
            for(int i=0;i<ids.size();i++){
                filename = server.getDocName(ids.get(i));
                files.add(new Fichier(filename));
            }
             System.out.println("Ajout a la TableView");
            tabGed.setItems(files);
            nom = new TableColumn("NomFichier");
             System.out.println("Association des noms");
            nom.setCellValueFactory(new PropertyValueFactory<Fichier,String>("nom"));
             System.out.println("Assciation de la collone a la tableview");
            tabGed.getColumns().setAll(nom);
         } catch (Exception ex) {
            Logger.getLogger(GedController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }   


    @FXML
    private void openButtonAction(ActionEvent event) throws IOException {
        if(event.getSource()==ouvrir){
            System.out.println("Open !");
        }        

    }   
    
    @FXML
    private void refreshButtonAction(ActionEvent event) throws IOException {
        if(event.getSource()==refresh){
            System.out.println("Refresh !");
        }        

    }
    
    @FXML
    private void addButtonAction(ActionEvent event) throws IOException {
        if(event.getSource()==add){
            System.out.println("Add !");
        }        

    }
    
        @FXML
    private void deleteButtonAction(ActionEvent event) throws IOException {
        if(event.getSource()==delete){
            System.out.println("Delete !");
        }        

    }
    
        @FXML
    private void shareButtonAction(ActionEvent event) throws IOException {
        if(event.getSource()==share){
            System.out.println("Share !");
        }        

    }
    
}
