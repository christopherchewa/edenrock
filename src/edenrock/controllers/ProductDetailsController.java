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
import edenrock.models.Product;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class ProductDetailsController implements Initializable, LayoutInterface {

    private static AnchorPane productsListAnchorPane;
    private static AnchorPane transactionPageAnchorPane;
    private static Product selectedProductDetail;

  
    private Double multiplier;
    

    
    
    
    
    private Layout layout;
    @FXML
    private AnchorPane productDetailsAnchorPane;
    @FXML
    private JFXButton productCanelButton;
    @FXML
    private JFXButton btnAddToCart;
    @FXML
    private TextField txtQuantity;
    private ListView checkoutListView;
    @FXML
    private Label lblProductQuantity;
    @FXML
    private Label lblProductTitle;

   
    @FXML
    private Label lblProductDescription;
    @FXML
    private Label lblProductPrice;
    @FXML
    private Label lblProductTitle1;

    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
        // TODO
        layout.setProductDetailsController(this);
        
        
        
        
    }    

   

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }

    @FXML
    private void productCanelButtonAction(ActionEvent event) {
        
        
        layout.getMainBorderPane().setLeft(productsListAnchorPane);
        
    }

    @FXML
    private void btnAddToCartAction(ActionEvent event) {
        
        HBox transactionHBox = (HBox)transactionPageAnchorPane.getChildren().get(0);
        VBox rightVBox = (VBox)transactionHBox.getChildren().get(1);
        checkoutListView = layout.getTransactionPageController().getCheckoutListView(); 
       
        int selectedCartItemId = selectedProductDetail.getInteger("product_id");
        String selectedCartItemIdString = selectedProductDetail.getInteger("product_id").toString();
        
        String productTitle = selectedProductDetail.getString("title");
        double productPrice = selectedProductDetail.getDouble("price");
        
        String txtQuantityText = txtQuantity.getText();
       
        multiplier = Double.parseDouble(txtQuantityText);
        
        Double selectedCartItemQuantity = selectedProductDetail.getDouble("quantity");
        
        if(selectedCartItemQuantity < multiplier){
            layout.showNotification("/resources/smallinfo.png", "", "Cannot purchase more than current product quantity");
            
        }
        else{
        selectedCartItemQuantity = selectedCartItemQuantity - multiplier;
        selectedProductDetail.set("quantity",selectedCartItemQuantity).saveIt();

        layout.getInventoryReportController().loadProductsTable();
        double total = multiplier * productPrice;
        Double totalD = total;
        
        Double subtotal;
        subtotal = layout.getTransactionPageController().getSubtotal();
        subtotal = subtotal + totalD;
        
        layout.getTransactionPageController().setSubtotal(subtotal);
        
        Label productTitleLabel = new Label(productTitle);
        productTitleLabel.setMaxWidth(210);
        productTitleLabel.setMinWidth(210);
        productTitleLabel.setPrefWidth(210);
       
        Label productQuantityLabel = new Label(txtQuantity.getText());
        
        productQuantityLabel.setMaxWidth(80);
        productQuantityLabel.setMinWidth(80);
        productQuantityLabel.setPrefWidth(80);
        Label productTotalLabel = new Label(totalD.toString());
          productTotalLabel.setMaxWidth(80);
        productTotalLabel.setMinWidth(80);
        productTotalLabel.setPrefWidth(80);
        
        Label cartItemLabel = new Label(selectedCartItemIdString);
        cartItemLabel.setVisible(false);
        
        HBox cartItemHBox = new HBox();
        cartItemHBox.setPadding(new Insets(10, 0, 10, 10));
        
        cartItemHBox.setSpacing(20);
        cartItemHBox.setStyle("-fx-background-color:#b2e4d0;-fx-background-radius:5.0;");
        cartItemHBox.getChildren().add(productTitleLabel);
        cartItemHBox.getChildren().add(productQuantityLabel);
        cartItemHBox.getChildren().add(productTotalLabel);
        
        
        cartItemHBox.getChildren().add(cartItemLabel);
        
        layout.getTransactionPageController().getCheckoutList().add(cartItemHBox);
        
        checkoutListView.setItems(layout.getTransactionPageController().getCheckoutList());
        
        layout.getMainBorderPane().setLeft(productsListAnchorPane);
        layout.getTransactionPageController().getLblTotal().setText(subtotal.toString());
        
        }
        
        
        
    }
    
     private void addCheckoutListener(){
          
           checkoutListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
           
           {
               if (checkoutListView.getSelectionModel() ==  null){
                   
                   
               }

          
           });
      }
    
    
     public void setProductsListAnchorPane(AnchorPane productsListAnchorPane) {
        ProductDetailsController.productsListAnchorPane = productsListAnchorPane;
    }
     
    public void setTransactionPageAnchorPane(AnchorPane transactionPageAnchorPane) {
        ProductDetailsController.transactionPageAnchorPane = transactionPageAnchorPane;
    }

    public void setSelectedProductDetail(Product selectedProductDetail) {
        ProductDetailsController.selectedProductDetail = selectedProductDetail;
    }
    
    public Product getSelectedProductDetail() {
        return selectedProductDetail;
    }
    
     public Label getLblProductQuantity() {
        return lblProductQuantity;
    }
     
      public Label getLblProductTitle() {
        return lblProductTitle;
    }

    public Label getLblProductDescription() {
        return lblProductDescription;
    }
    
    public Label getLblProductPrice() {
        return lblProductPrice;
    }

      public Double getMultiplier() {
        return multiplier;
    }
  
   
}
