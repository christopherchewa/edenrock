/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.NotificationValues;
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
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class MainLayoutController implements Initializable, LayoutInterface {
    
    private static AnchorPane inventoryFormAnchorPane;
    private static AnchorPane selectReportAnchorPane;
    
    private static AnchorPane inventoryReportAnchorPane;
    
    private static AnchorPane productDetailsAnchorPane;
    private static AnchorPane productsListAnchorPane;
    
    private static AnchorPane transactionPageAnchorPane;
    private static AnchorPane debtorsReportAnchorPane;
    private static AnchorPane todaysReportAnchorPane;
    


    @FXML
    private JFXButton btnReports;


    @FXML
    private AnchorPane leftAnchorPane;

    @FXML
    private AnchorPane rightAnchorPane;
    @FXML
    private JFXDrawer addNewDrawer;

   
    private Layout layout;
    
    
    
    @FXML
    private JFXButton btnAddNew;
    @FXML
    private JFXDrawer accountDrawer;
    @FXML
    private JFXButton btnAccount;
    @FXML
    private JFXButton btnHome;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLayout(layout);
        layout.setMainLayoutController(this);
        
       
        
        
   
        
    }  

   
    
    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }


    private void btnCreditAction(ActionEvent event) {
        
        layout.getTransactionPageController().cancelCheckout();
    }

   
    
    @FXML
    private void btnAddNewAction(ActionEvent event) {
        layout.getTransactionPageController().cancelCheckout();
        layout.initPopup(addNewDrawer, "/resources/AddNewDrawerContent.fxml");
        layout.showPopup(addNewDrawer); 
        

    }
    
     @FXML
    private void btnAccountAction(ActionEvent event) {
        
         layout.getTransactionPageController().cancelCheckout();
         layout.initPopup(accountDrawer, "/resources/AccountDrawerNotificationsContent.fxml");
         layout.showPopup(accountDrawer);
        
         
    }
    
    @FXML
    private void btnReportsAction(ActionEvent event) {
        
        layout.getTransactionPageController().cancelCheckout();
        layout.getMainBorderPane().setLeft(selectReportAnchorPane);
    }
    
     @FXML
    private void btnHomeAction(ActionEvent event) {
        layout.getTransactionPageController().cancelCheckout();
        layout.getMainBorderPane().setRight(transactionPageAnchorPane);
        layout.getMainBorderPane().setLeft(productsListAnchorPane);
    }

    public void generateNotifications(){
        
        List<Product> refillProducts = Product.where("quantity < ?", NotificationValues.REFILL_INVENTORY_QUANTITY);
        
        for(Product p : refillProducts){
            
            HBox invHBox = new HBox();
            
            Label productTitleLabel = new Label(p.getString("title"));
            Label productQuantityLabel = new Label(p.getDouble("quantity").toString());
            
            invHBox.getChildren().add(productTitleLabel);
            invHBox.getChildren().add(productQuantityLabel);
            
            layout.getNotificationsPaneController().getInventoryList().add(invHBox);
            
           
        }
        System.out.println(layout.getNotificationsPaneController().getInventoryList());
       
        
    }
    
     
    public AnchorPane getInventoryFormAnchorPane()
    {
        return inventoryFormAnchorPane;
    }
     public JFXDrawer getAddNewDrawer() {
        return addNewDrawer;
    }
     
     public JFXDrawer getAccountDrawer() {
        return accountDrawer;
    }
     public void setAddNewDrawer(JFXDrawer addNewDrawer) {
        this.addNewDrawer = addNewDrawer;
    }
     
     public void setInventoryFormAnchorPane(AnchorPane inventoryFormAnchorPane)
    {
        MainLayoutController.inventoryFormAnchorPane = inventoryFormAnchorPane;
    }
     
    

    public void setSelectReportAnchorPane(AnchorPane selectReportAnchorPane) {
        MainLayoutController.selectReportAnchorPane = selectReportAnchorPane;
    }
    
    public void setProductDetailsAnchorPane(AnchorPane productDetailsAnchorPane) {
        MainLayoutController.productDetailsAnchorPane = productDetailsAnchorPane;
    }
    
    public void setProductsListAnchorPane(AnchorPane productsListAnchorPane) {
        MainLayoutController.productsListAnchorPane = productsListAnchorPane;
    }

    public void setTransactionPageAnchorPane(AnchorPane transactionPageAnchorPane) {
        MainLayoutController.transactionPageAnchorPane = transactionPageAnchorPane;
    }

    public void setDebtorsReportAnchorPane(AnchorPane debtorsReportAnchorPane) {
       MainLayoutController.debtorsReportAnchorPane = debtorsReportAnchorPane;
    }

    public void setTodaysReportAnchorPane(AnchorPane todaysReportAnchorPane) {
        MainLayoutController.todaysReportAnchorPane = todaysReportAnchorPane;
    }

    

    
      
    
}
