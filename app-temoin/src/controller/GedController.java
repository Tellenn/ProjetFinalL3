/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private TableView<String> tabGed;
    @FXML
    private TableColumn<String,String> nom;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //nom.setText("image.jpg");
        tabGed.setItems("Bonjour");
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
