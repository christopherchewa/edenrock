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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class TodaysReportController implements Initializable, LayoutInterface {

    private Layout layout;
    private ObservableList<Cart> todaysProductsList = FXCollections.observableArrayList();
    private FilteredList<Cart> filteredData = new FilteredList(todaysProductsList, e-> true);
    private ObservableList<VBox> todaysProductsListVBox = FXCollections.observableArrayList();
    private Cart transactionItem;
    
    
    @FXML
    private AnchorPane todaysReportAnchorPane;
    @FXML
    private TableView<Cart> todaysReportTable;
    @FXML
    private TableColumn<Cart, String> customerCol;
    @FXML
    private TableColumn<Cart, Double> subtotalCol;
    @FXML
    private TableColumn<Cart, Double> balanceCol;
    @FXML
    private ListView<VBox> todaysProductsListView;
    @FXML
    private Label lblNoTransactionSelected;
    @FXML
    private Label lblSubtotal;
    @FXML
    private Label lblTodaysTotal;
    
    private Double subtotal;
    private Double todaysTotal;
    private Double todaysBalance;
    private Double todaysPaid;
    
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblTodaysBalance;
    @FXML
    private Label lblTodaysPaid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
        layout.setTodaysReportController(this);
        customerCol.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        subtotalCol.setCellValueFactory(cellData -> cellData.getValue().subtotalProperty().asObject());
        balanceCol.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        loadSalesTable();
        addTableListener();
        lblNoTransactionSelected.setVisible(true);
        // TODO
        
    }    

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }
    
    public void loadSalesTable()
    {
      
        todaysTotal = 0.0;
        todaysBalance = 0.0;
      todaysProductsList.removeAll(todaysProductsList);
      Date todaysDate = Calendar.getInstance().getTime();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     
      
      List<Cart> salesData = Cart.where("created_at = ?", sdf.format(todaysDate));

      
      salesData.forEach((s) -> {
          todaysProductsList.add(new Cart(s.getInteger("cart_id").toString(),
                  s.getString("customer"),
                  s.getDouble("subtotal"), 
                  s.getDouble("balance"),
                  s.getDate("created_at"),
                  s.getDate("due_date")
                  
          ));
          
            todaysTotal = todaysTotal + s.getDouble("subtotal");
            todaysBalance = todaysBalance + s.getDouble("balance");
            todaysPaid = todaysTotal - todaysBalance;
            lblTodaysTotal.setText(todaysTotal.toString());
            lblTodaysBalance.setText(todaysBalance.toString());
            lblTodaysPaid.setText(todaysPaid.toString());
            
        });
              
              todaysReportTable.setItems(todaysProductsList);
            
          
   }
    private void addTableListener(){
          
           todaysReportTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
           
           {
               if (todaysReportTable.getSelectionModel().getSelectedItem() ==  null){
                   lblNoTransactionSelected.setVisible(true);
                   todaysProductsListVBox.removeAll(todaysProductsListVBox);
                   lblSubtotal.setText("");
               }
               else{
                  
                subtotal = 0.0;
                todaysProductsListVBox.removeAll(todaysProductsListVBox);

            lblNoTransactionSelected.setVisible(false);
            transactionItem = Cart.findById(todaysReportTable.getSelectionModel().getSelectedItem().cartIdProperty().get());    
            List<CartProduct> transactionProducts = CartProduct.where("cart_id = ?", transactionItem.getInteger("cart_id"));
        
            for(CartProduct p:transactionProducts){
                
                
                VBox tempVBox = new VBox();
                tempVBox.setStyle("-fx-background-color:#b2e4d0;-fx-background-radius:5.0;");
                tempVBox.setPadding(new Insets(5, 0, 5, 5));
                Product tempProduct = Product.findById(p.getInteger("product_id"));
                
                Label productTitleLabel = new Label(tempProduct.getString("title"));
                Label productQuantityLabel = new Label("Units: " + p.getDouble("multiplier").toString());
                Label productSubtotalLabel = new Label("Product Total: "  + p.getDouble("total").toString());
                
                
                
                tempVBox.getChildren().add(productTitleLabel);
                tempVBox.getChildren().add(productQuantityLabel);
                tempVBox.getChildren().add(productSubtotalLabel);
                
                todaysProductsListVBox.add(tempVBox);
                subtotal = subtotal + p.getDouble("total");
                lblSubtotal.setText(subtotal.toString());
                
                
            }
        
        todaysProductsListView.setItems(todaysProductsListVBox);
        

               }

          
           });
      }

    @FXML
    private void txtSearchAction(KeyEvent event) {
     
          txtSearch.textProperty().addListener((observableValue, oldValue, newValue)->{
           filteredData.setPredicate(cart-> {
           
               if (newValue == null || newValue.isEmpty()){
                   
                   return true;
               }
               
               String lowerCaseFilter = newValue.toLowerCase();
               if ( cart.customerProperty().get().contains(newValue)
                       ||
                     cart.customerProperty().get().toLowerCase().contains(lowerCaseFilter)
                     
                       )
               {
                   return true;
               }
               
           return false;
           
           });
            
        
        
        });
        
        SortedList<Cart> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(todaysReportTable.comparatorProperty());
        todaysReportTable.setItems(sortedData);
        
    }
    

    
    
}
