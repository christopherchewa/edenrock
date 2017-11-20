/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import edenrock.DataManagement;
import edenrock.DataManagementInterface;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.models.Cart;
import edenrock.models.CartProduct;
import edenrock.models.Product;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class CreditPaymentModalController implements Initializable, LayoutInterface, DataManagementInterface {

    @FXML
    private JFXButton btnCloseDialog;
    @FXML
    private Label lblCustomerDetails;
    
    @FXML
    private ListView<HBox> creditPaymentListView;
    @FXML
    private Label lblExpectedAmount;
    @FXML
    private TextField txtAmountPaid;
    @FXML
    private Label lblBalance;
    @FXML
    private JFXButton btnCompleteTransaction;
    private Layout layout;
    
    private Double amountExpected;
    private Double amountPaid;
    private Double balance;
    private DataManagement dataManagement;
    
    private ObservableList<HBox> productHBoxList = FXCollections.observableArrayList();
    private ObservableList<HBox> productList = FXCollections.observableArrayList();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setLayout(layout);
        setDataManagement(dataManagement);
        layout.setCreditPaymentModalController(this);
        dataManagement.generateProductList(layout, productHBoxList, productList, creditPaymentListView);
        addBalanceListener();
        
    }    

    @FXML
    private void btnCloseDialogAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    

    @FXML
    private void btnCompleteTransactionAction(ActionEvent event) {
        
      if (!txtAmountPaid.getText().isEmpty()
          &&
          txtAmountPaid.getText() != null
          
          )
      {
          
          if (!dataManagement.validateNumber(txtAmountPaid))
          {
              layout.showNotification("/resources/smallinfo.png", "", "Enter the value of a correct format."); 
          }
          else{
              if (Double.parseDouble(txtAmountPaid.getText()) > Double.parseDouble(lblExpectedAmount.getText()))
          {
              layout.showNotification("/resources/smallinfo.png", "", "Cannot pay more than expected amount.");
              txtAmountPaid.setText("0.0");
          }
          else
          {
              dataManagement.completeTransaction(layout, lblExpectedAmount, txtAmountPaid);
          ((Node)event.getSource()).getScene().getWindow().hide();
          layout.showNotification("/resources/smalltick.png", "Complete!", "Transaction details saved.");
          }
          }
          
      }
      else
      {
          layout.showNotification("/resources/smallinfo.png", "", "Please enter the amount paid.");
          
      }
     
        
    }

    @Override
    public void setDataManagement(DataManagement dataManagement) {
        dataManagement = new DataManagement();
        this.dataManagement = dataManagement;
    }
    
    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }
    
    private void addBalanceListener(){
          
           txtAmountPaid.textProperty().addListener((observableValue, oldValue, newValue) -> 
           
           {
               amountExpected = Double.parseDouble(lblExpectedAmount.getText());
               
               if (!txtAmountPaid.getText().isEmpty()
                       &&
                   txtAmountPaid.getText() != null
                       )
               {
                   
                   if (!dataManagement.validateNumber(txtAmountPaid))
                   {
                       layout.showNotification("/resources/smallinfo.png", "", "Enter the value of a correct format."); 
                   }
                   else
                   {
               
               amountPaid = Double.parseDouble(txtAmountPaid.getText());
               balance = amountExpected - amountPaid;
               lblBalance.setText(balance.toString());
                   }
                   
                   
                   
               }
               else{
                lblBalance.setText(amountExpected.toString());
               }
               
               

          
           });
      }
    
    public Label getlblCustomerDetails() {
        return this.lblCustomerDetails;
    }
    
    public Label getlblExpectedAmount() {
        return this.lblExpectedAmount;
    }
    
     public Label getlblBalance() {
        return this.lblBalance;
    }

    

    
}
