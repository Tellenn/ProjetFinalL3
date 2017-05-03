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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aminca
 */
public class ConnectionController implements Initializable {
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
        Stage stage = new Stage();        
        Parent root = new Pane();
        System.out.println("****atest");
        if(event.getSource()==connexion){
            System.out.println("****aaazd");
            stage = (Stage) connexion.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/User.fxml"));
        }        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
}
