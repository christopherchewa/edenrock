package edenrock.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.models.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Chewa
 */
public class LoginController implements Initializable, LayoutInterface {
    
    private Layout layout;
    private static AnchorPane inventoryFormAnchorPane;
    private static AnchorPane selectReportAnchorPane;
    
    private static AnchorPane productsListAnchorPane;
    private static AnchorPane productDetailsAnchorPane;
    
    
    private static AnchorPane transactionPageAnchorPane;
    
   @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private JFXButton btnSignIn;
    @FXML
    private JFXButton btnClose;
    @FXML
    private Label lblInvalid;

   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
    }    
    
     @FXML
    private void btnSignInAction(ActionEvent event) {
        
        if(validateLogin())
        {
            layout.initMainLayout();
//        layout.getMainBorderPane().setLeft(productsListAnchorPane);
        layout.getMainBorderPane().setRight(transactionPageAnchorPane);
        ((Node)event.getSource()).getScene().getWindow().hide();
        lblInvalid.setVisible(false);
        txtUsername.clear();
        txtPassword.clear();
        }
        else
        {
            lblInvalid.setVisible(true);
        }
        
        
    }

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
        
    }
    
    private boolean validateLogin()
    {
        
        User user = User.findFirst("username = ? and password = ?", txtUsername.getText(), txtPassword.getText());
        
        if (user != null)
        {
            return true;
        }
        else{
            
            return false;
        }
        
        
    }

   
     public void setInventoryFormAnchorPane(AnchorPane inventoryFormAnchorPane)
    {
        LoginController.inventoryFormAnchorPane = inventoryFormAnchorPane;
    }
     
    public AnchorPane getInventoryFormAnchorPane()
    {
        return inventoryFormAnchorPane;
    }
    
    public void setSelectReportAnchorPane(AnchorPane selectReportAnchorPane)
    {
        LoginController.selectReportAnchorPane = selectReportAnchorPane;
    }
     
    public AnchorPane getSelectReportAnchorPane()
    {
        return selectReportAnchorPane;
    }

    @FXML
    private void btnCloseAction(ActionEvent event) {
        System.exit(0);
    }

    public void setProductsListAnchorPane(AnchorPane productsListAnchorPane) {
        LoginController.productsListAnchorPane = productsListAnchorPane;
    }

    public void setTransactionPageAnchorPane(AnchorPane transactionPageAnchorPane) {
        LoginController.transactionPageAnchorPane = transactionPageAnchorPane;
    }
    
}
