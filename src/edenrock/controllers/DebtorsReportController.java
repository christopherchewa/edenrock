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
import edenrock.models.Product;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class DebtorsReportController implements Initializable, LayoutInterface, DataManagementInterface {
    
    private Cart selectedCart;
    
    @FXML
    private AnchorPane debtorsReportAnchorPane;
    @FXML
    private TableView<Cart> salesTable;
    @FXML
    private TableColumn<Cart, String> customerCol;
    @FXML
    private TableColumn<Cart, Double> subtotalCol;
    @FXML
    private TableColumn<Cart, Double> balanceCol;
    @FXML
    private TableColumn<Cart, Date> dateCol;
    
    @FXML
    private TextField txtAmountPaid;
    @FXML
    private JFXButton btnSubmitPayment;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblBalance;
    
    private Layout layout;
    private ObservableList<Cart> salesDataList = FXCollections.observableArrayList();
    private FilteredList<Cart> filteredData = new FilteredList<>(salesDataList, e->true);
    @FXML
    private TableColumn<Cart, Date> dueDateCol;
    private DataManagement dataManagement;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
        setDataManagement(dataManagement);
        
        layout.setDebtorsReportController(this);
        customerCol.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        subtotalCol.setCellValueFactory(cellData -> cellData.getValue().subtotalProperty().asObject());
        balanceCol.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().cartDateProperty());
        dueDateCol.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());
        loadSalesTable();
        addTableListener();
        
    }    

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }
    
      public void loadSalesTable()
    {
      
      salesDataList.removeAll(salesDataList);
      List<Cart> salesData = Cart.where("balance > ?", 0.0);
      
      salesData.forEach((s) -> {
          salesDataList.add(new Cart(s.getInteger("cart_id").toString(),
                  s.getString("customer"),
                  s.getDouble("subtotal"), 
                  s.getDouble("balance"),
                  s.getDate("created_at"),
                  s.getDate("due_date")
          ));
        });
              
              salesTable.setItems(salesDataList);
            
          
   }
   
      private void addTableListener(){
          
           salesTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
           
           {
               if (salesTable.getSelectionModel().getSelectedItem() !=  null){
                   selectedCart = Cart.findById(salesTable.getSelectionModel().getSelectedItem().cartIdProperty().get());   
                   lblBalance.setText(selectedCart.getDouble("balance").toString());
                   
                   
               }
                   else{
                   clearDetails();
               }
                   

          
           });
      }
     

    @FXML
    private void btnSubmitPaymentAction(ActionEvent event) {
        
        
        
        if( (salesTable.getSelectionModel().getSelectedItem() != null ) && (!(txtAmountPaid.getText().isEmpty())) && (txtAmountPaid.getText()!=null) ){
            
            if(!dataManagement.validateNumber(txtAmountPaid))
            {
                 layout.showNotification("/resources/smallinfo.png", "", "Enter the value of a correct format."); 
            }
            else{
                    Double amountPaid = Double.parseDouble(txtAmountPaid.getText());
            Double itemBalance = selectedCart.getDouble("balance");
            
            if(amountPaid > itemBalance){
               
                layout.showNotification("/resources/smallinfo.png", "", "Cannot deduct more than balance.");
                
                
            }
            else{
                
                
                    Double newItemBalance = itemBalance - amountPaid;
                selectedCart.set("balance", newItemBalance).saveIt();
                loadSalesTable();
                layout.getSalesReportController().loadSalesTable();
                layout.getTodaysReportController().loadSalesTable();
                clearDetails();
                layout.showNotification("/resources/smalltick.png", "Complete!", "Transaction updated.");
                
                
            }
        }
            }
            
            
        
        else
        {
            layout.showNotification("/resources/smallinfo.png", "", "Ensure you have selected a transaction and entered amount.");
            
            
            
        }
        
        
        
        
    }

    @FXML
    private void txtSearchAction(KeyEvent event) {
        
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue)->{
           filteredData.setPredicate(cart-> {
           
               if (newValue == null || newValue.isEmpty()){
                   
                   return true;
               }
               
               String lowerCaseFilter = newValue.toLowerCase();
               if ( cart.customerProperty().get().toLowerCase().contains(newValue)
                       ||
                     cart.customerProperty().get().toLowerCase().contains(lowerCaseFilter)
                         ||
                     cart.cartDateProperty().asString().get().contains(newValue)
                     
                       )
               {
                   return true;
               }
               
           return false;
           
           });
            
        
        
        });
        
        SortedList<Cart> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(salesTable.comparatorProperty());
        salesTable.setItems(sortedData);
    }
    
    protected void clearDetails()
    {
        lblBalance.setText("");
        txtAmountPaid.clear();
    }

    @Override
    public void setDataManagement(DataManagement dataManagement) {
        dataManagement = new DataManagement();
        this.dataManagement = dataManagement;
    }
    
}
