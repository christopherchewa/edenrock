/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class SalesReportController implements Initializable, LayoutInterface {
    
     private Layout layout;
    private ObservableList<Cart> salesDataList = FXCollections.observableArrayList();
    private ObservableList<HBox> cartProductsList = FXCollections.observableArrayList();
    private FilteredList<Cart> filteredData = new FilteredList<>(salesDataList, e->true);
    private Cart selectedTransaction;
    private String customerInfo;
    private Double cartTotal;
    private Double cartBalance;
    
    @FXML
    private AnchorPane salesReportAnchorPane;
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
    private TextField txtSearch;
    @FXML
    private JFXButton btnReceipt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
        layout.setSalesReportController(this);
        customerCol.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        subtotalCol.setCellValueFactory(cellData -> cellData.getValue().subtotalProperty().asObject());
        balanceCol.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().cartDateProperty());
        loadSalesTable();
        addTableListener();
    }    
    
    public void loadSalesTable()
    {
      
      salesDataList.removeAll(salesDataList);
      List<Cart> salesData = Cart.findAll();
      
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

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
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
    
       private void addTableListener(){
          
           salesTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
           
           {
               if (salesTable.getSelectionModel().getSelectedItem() !=  null){
                   
                   cartProductsList.removeAll(cartProductsList);
                   selectedTransaction = Cart.findById(salesTable.getSelectionModel().getSelectedItem().cartIdProperty().get());
                   
                   customerInfo = selectedTransaction.getString("customer");
                   cartTotal = selectedTransaction.getDouble("subtotal");
                   cartBalance = selectedTransaction.getDouble("balance");
                   
                    
                    
                    
                    
                   List<CartProduct> transactionProducts = CartProduct.where("cart_id = ?", selectedTransaction.getInteger("cart_id"));
                  
                  
                  for(CartProduct cp: transactionProducts)
                   {
                       
                       HBox tempHBox = new HBox();
                       tempHBox.setStyle("-fx-background-color:#b2e4d0;-fx-background-radius:5.0;");
                       tempHBox.setPadding(new Insets(10, 0, 10, 10));
                       
                       Product tempProduct = Product.findFirst("product_id=?", cp.getInteger("product_id"));
                       
                       
                       Label tempProductTitle = new Label(tempProduct.getString("title"));
                       
                       tempProductTitle.setMinWidth(230);
                       tempProductTitle.setMaxWidth(230);
                       tempProductTitle.setPrefWidth(230);
                       
                       Label tempProductQuantity = new Label(cp.getDouble("multiplier").toString());
                       tempProductQuantity.setMinWidth(100);
                       tempProductQuantity.setMaxWidth(100);
                       tempProductQuantity.setPrefWidth(100);
                       tempProductQuantity.textAlignmentProperty().set(TextAlignment.JUSTIFY);
                       
                       
                       Label tempProductTotal = new Label(cp.getDouble("total").toString());
                       tempProductTotal.setMinWidth(100);
                       tempProductTotal.setMaxWidth(100);
                       tempProductTotal.setPrefWidth(100);
                       
                       
                       
                       tempHBox.getChildren().addAll(tempProductTitle, tempProductQuantity, tempProductTotal);
                       cartProductsList.add(tempHBox);
                       
                   }

                       
               }
               
          
           });
      }

    @FXML
    private void btnReceiptAction(ActionEvent event) {
        
        if (salesTable.getSelectionModel().getSelectedItem() !=  null){
            layout.showModal("/resources/CartContent.fxml", event);
        }
        else
        {
                layout.showNotification("/resources/smallinfo.png", "", "Ensure you have selected a transaction.");
        }
                 
        
        
    }
    
    public ObservableList<HBox> getCartProductsList() {
        return cartProductsList;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public Double getCartTotal() {
        return cartTotal;
    }

    public Double getCartBalance() {
        return cartBalance;
    }
    
    
}
