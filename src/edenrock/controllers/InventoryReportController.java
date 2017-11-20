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
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
public class InventoryReportController implements Initializable, LayoutInterface, DataManagementInterface {
    
    private Product selectedProduct;
    
        @FXML
    private AnchorPane inventoryReportAnchorPane;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> titleCol;

    @FXML
    private TableColumn<Product, String> descriptionCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private TableColumn<Product, Double> quantityCol;
    @FXML
    private Label lblNumberOfProducts;
    
    private Layout layout;
    private ObservableList<Product> productDataList = FXCollections.observableArrayList();
    private FilteredList<Product> filteredData = new  FilteredList<>(productDataList, e-> true);
    
    
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;
    @FXML
    private JFXButton btnSubmit;
    private DataManagement dataManagement;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setLayout(layout);
        setDataManagement(dataManagement);
        layout.setInventoryReportController(this);
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        loadProductsTable();
        addTableListener();
   
    }    
    
    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }
    
    @FXML
    private void btnSubmitAction(ActionEvent event) {
      
        if (  (!(txtPrice.getText().isEmpty())) 
                
                && (txtPrice.getText()!= null )
                
                && (!(txtQuantity.getText().isEmpty())) 
                
                && (txtQuantity.getText()!= null )
                
                && (productsTable.getSelectionModel().getSelectedItem() != null)
                
                
           ){
            
            if(!dataManagement.validateNumber(txtPrice) || !dataManagement.validateNumber(txtQuantity))
            {
                layout.showNotification("/resources/smallinfo.png", "", "Enter the value of a correct format."); 
            }
            else{
                 
                 selectedProduct.set("price", Double.parseDouble(txtPrice.getText()))
                    .set("quantity",Double.parseDouble(txtQuantity.getText()))
                    .saveIt();
                    
                    loadProductsTable();
                    clearFields();
                    layout.showNotification("/resources/smalltick.png", "Complete!", "Product details updated."); 
                 
            }
           
            
        }
        else{
            layout.showNotification("/resources/smallinfo.png", "", "Ensure you have selected a product" + "\n" + "and fields are not empty");
            
        }
        
    }
    
    
    
    
    @FXML
    private void txtSearchAction(KeyEvent event) {
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue)->{
           filteredData.setPredicate(product-> {
           
               if (newValue == null || newValue.isEmpty()){
                   
                   return true;
               }
               
               String lowerCaseFilter = newValue.toLowerCase();
               if ( product.titleProperty().get().contains(newValue)
                       ||
                     product.titleProperty().get().toLowerCase().contains(lowerCaseFilter)
                       ||
                     product.quantityProperty().asString().get().contains(newValue)
                       )
               {
                   return true;
               }
               
           return false;
           
           });
            
        
        
        });
        
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productsTable.comparatorProperty());
        productsTable.setItems(sortedData);
        
    }
    
    public void loadProductsTable()
    {
      
      productDataList.removeAll(productDataList);
      List<Product> productData = Product.findAll();
      
      productData.forEach((p) -> {
          productDataList.add(new Product(p.getInteger("product_id").toString(),
                  p.getString("title"),
                  p.getString("description"), 
                  p.getDouble("price"),
                  p.getDouble("quantity")
          ));
        });
              
              productsTable.setItems(productDataList);
              lblNumberOfProducts.setText(String.valueOf(productDataList.size()));
              
              
          
   }

    private void addTableListener(){
          
           productsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
           
           {
               if (productsTable.getSelectionModel().getSelectedItem() !=  null){
                        
                   selectedProduct = Product.findById(productsTable.getSelectionModel().getSelectedItem().productIdProperty().get());    
              
              txtPrice.setText(selectedProduct.getDouble("price").toString());
              txtQuantity.setText(selectedProduct.getDouble("quantity").toString());
               
                       
               }
               
          
           });
      }
    
    protected void clearFields(){
        
        txtPrice.clear();
        txtQuantity.clear();
    }

    @Override
    public void setDataManagement(DataManagement dataManagement) {
        dataManagement = new DataManagement();
        this.dataManagement = dataManagement;
    }

    

    
}
