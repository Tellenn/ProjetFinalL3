/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Client.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aminca
 */
public class ConnectionController  extends ControllerPere implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private Button connexion;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void submitButtonAction(ActionEvent event) throws IOException {
       //Client c = new Client();
        Client c = new Client();
        Stage stage = new Stage();        
        Parent root = new Pane();
        if(event.getSource()==connexion){
            /*if(*/
            c.doConnect(username.getText(),password.getText());
            /*)==1;){*/
                System.out.println("if");
                stage = (Stage) connexion.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/view/accueil.fxml"));
            /*} else {
                System.out.println("else");
                 Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Erreur de connection");
                alert.setHeaderText("Erreur de connection");
                alert.setContentText("Vos identiant ne sont pas valides");

                alert.showAndWait();
            }*/
        } 
        System.out.println("fin if");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
  
    }
    
}
