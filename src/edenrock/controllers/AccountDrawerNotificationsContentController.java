/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class AccountDrawerNotificationsContentController implements Initializable, LayoutInterface {
    
    private Layout layout;
    private Stage loginStage;
    
    @FXML
    private JFXButton btnNotifications;
    @FXML
    private JFXButton btnSignOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
    }    

    @FXML
    private void btnNotificationsAction(ActionEvent event) {
         layout.showModal("/resources/NotificationsPane.fxml", event);
//         System.out.println(layout.getNotificationsPaneController().getInventoryList());
        layout.getMainLayoutController().getAccountDrawer().close();
    }

    @FXML
    private void btnSignOutAction(ActionEvent event) {
        
        
        layout.getLoginStage().show();
        ((Node)event.getSource()).getScene().getWindow().hide();
    
    }

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }
    
}
