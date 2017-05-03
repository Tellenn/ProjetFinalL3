/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;

/**
 * FXML Controller class
 *
 * @author aminca
 */
public class CalendrierController implements Initializable {
    @FXML
    private Button chat;
    @FXML
    private Button ged;
    @FXML
    private MenuButton moncompte;
    @FXML
    private Button nvevent;
    @FXML
    private Button modifevent;
    @FXML
    private Button infoevent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
}
