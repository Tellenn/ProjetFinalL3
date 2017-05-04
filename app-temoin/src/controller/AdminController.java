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
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author Antoine
 */
public class AdminController extends ControllerPere implements Initializable {
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
    private Button addUser;
    @FXML
    private Button suprrUser;
    @FXML
    private Button addRelation;
    @FXML
    private Button supprRelation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addUserButtonAction(ActionEvent event) {
    }

    @FXML
    private void suprrUserButtonAction(ActionEvent event) {
    }

    @FXML
    private void addRelationButtonAction(ActionEvent event) {
    }

    @FXML
    private void supprRelationButtonAction(ActionEvent event) {
    }
    
}
