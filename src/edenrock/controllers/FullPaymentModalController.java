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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class FullPaymentModalController implements Initializable, LayoutInterface, DataManagementInterface {

    @FXML
    private JFXButton btnCloseDialog;
    @FXML
    private Label lblCustomerDetails;
    @FXML
    private ListView<HBox> fullPaymentListView;
    @FXML
    private Label lblExpectedAmount;
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
        // TODO
        setLayout(layout);
        setDataManagement(dataManagement);
        layout.setFullPaymentModalController(this);
        dataManagement.generateProductList(layout, productHBoxList, productList, fullPaymentListView);
    }    

    @FXML
    private void btnCloseDialogAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void btnCompleteTransactionAction(ActionEvent event) {
        

        dataManagement.completeTransaction(layout, lblExpectedAmount, null);
        ((Node)event.getSource()).getScene().getWindow().hide();
        layout.showNotification("/resources/smalltick.png", "Complete!", "Transaction details saved.");
        layout.getTransactionPageController().getTxtCustomerDetails().clear();
    }
    
  
    
     public Label getlblCustomerDetails() {
        return this.lblCustomerDetails;
    }
    
    public Label getlblExpectedAmount() {
        return this.lblExpectedAmount;
    }
    
    public ListView getCreditPaymentListView(){
        return this.fullPaymentListView;
}

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }

    @Override
    public void setDataManagement(DataManagement dataManagement) {
        dataManagement = new DataManagement();
        this.dataManagement = dataManagement;
    }
    
    
    
    
}
