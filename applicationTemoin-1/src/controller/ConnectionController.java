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
    private Button connexion;
    
    @FXML
    private TextField username;
    
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
        if(event.getSource()==connexion){
            stage = (Stage) connexion.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/User.fxml"));
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //username.setPromptText("username");
        //password.setPromptText("password");
        
       /* String login = username.getText();
        String mdp = password.getText();
        connexion.setText("connexion");*/
    }
    
}
