/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aminca
 */
public class MdpController extends ControllerPere implements Initializable {
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
    private Button valider;
    @FXML
    private Button annuler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    @FXML
    private void validerButtonAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();        
         Parent root = new Pane();
         if(event.getSource()==valider){
             stage = (Stage) valider.getScene().getWindow();
             root = FXMLLoader.load(getClass().getResource("/view/Accueil.fxml"));
         }        
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show(); 
    }

    @FXML
    private void annulerButtonAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();        
         Parent root = new Pane();
         if(event.getSource()==annuler){
             stage = (Stage) annuler.getScene().getWindow();
             root = FXMLLoader.load(getClass().getResource("/view/User.fxml"));
         }        
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show(); 
    }
   
    
}
