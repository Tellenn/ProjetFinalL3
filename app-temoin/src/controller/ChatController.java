/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import Client.*;
import java.util.TreeMap;


/**
 * FXML Controller class
 *
 * @author aminca
 */
public class ChatController extends ControllerPere implements Initializable {
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
    private Button send;
   
    @FXML
    private AnchorPane tabConversationPrivee;
    @FXML
    private ListView salon;
    
    protected ListProperty<String> salonProperty = new SimpleListProperty<>();
    protected List<String> connectedCurrencyList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Client c = new Client();
        /*try {
            Problème de connexion ...
           c.connectChat(ip);
            TreeMap<Integer, ChatClientInt> t = new TreeMap<>();
            t = c.getConnected();
            for (ChatClientInt item : t.values()) {
                //connectedCurrencyList.add(item.getName());
                 System.out.println("--- " + item.getName());
            }
        } catch (RemoteException e) {
            System.out.print(e);
        }*/


        salon.itemsProperty().bind(salonProperty);

        //This does not work, you can not directly add to a ListProperty
        //listProperty.addAll( asianCurrencyList );
        salonProperty.set(FXCollections.observableArrayList(connectedCurrencyList));
        salon.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
           //affichage des clients
        });
        
        
    }  
    
   
}
