/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.models.Cart;
import edenrock.models.Category;
import edenrock.models.Product;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class TransactionPageController implements Initializable, LayoutInterface {
    
    private List<Category> categories;
    private Layout layout;
    private static AnchorPane productsListAnchorPane;
    private ObservableList<JFXButton> categoryButtons = FXCollections.observableArrayList();
    private ObservableList <HBox> HBoxContent = FXCollections.observableArrayList();
        private ObservableList <HBox> checkoutList = FXCollections.observableArrayList();

    
    private ObservableList <HBox> cartContent = FXCollections.observableArrayList();
    private ListView <HBox> categoryProductsListView;
    
    private Label selectedItemId;
    private Double subtotal = 0.0;

   
    
    
    @FXML
    private JFXButton btnInventoryReport;
    private GridPane productGridPane;
    @FXML
    private AnchorPane transactionPageAnchorPane;
    @FXML
    private ListView<JFXButton> categoryButtonsListView;
    @FXML
    private Label lblTotal;

    @FXML
    private ListView<HBox> checkoutListView;
    @FXML
    private JFXButton btnCreditSale;
    @FXML
    private JFXButton btnPay;
    @FXML
    private TextField txtCustomerDetails;

  
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnCancelTransaction;

   
    
    
    /**
     * Initializes the controller class.class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLayout(layout);
        layout.setTransactionPageController(this);
        
       generateCategoryButtons();
        categoryButtonsListView.setItems(categoryButtons);

        
    }    
    
     @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }

    
    //generate category buttons/product list for a category
    public void generateCategoryButtons(){
         
        categoryButtons.removeAll(categoryButtons);
        categories = Category.findAll();
        for(Category c: categories){  
        
        JFXButton b = new JFXButton(c.getString("title"));
        categoryButtons.add(b);
        b.setStyle("-fx-background-color:white;-fx-border-style:solid solid solid solid;-fx-border-insets:5px;");
        b.setMinSize(200.0, 40.0);
        b.setMaxSize(200.0, 40.0);
        b.setRipplerFill(Paint.valueOf("#ffffff"));
        
        b.addEventHandler(MouseEvent.MOUSE_CLICKED, 
            (event -> {
                layout.getMainBorderPane().setLeft(productsListAnchorPane);
                VBox myBox = (VBox)productsListAnchorPane.getChildren().get(0);
                
                Label categoryLabel = (Label)myBox.getChildren().get(0);
                categoryLabel.setText(c.getString("title"));
                
                
                List<Product> products = c.getAll(Product.class);
                
                
                categoryProductsListView = (ListView)myBox.getChildren().get(3);
                categoryProductsListView.getItems().removeAll(HBoxContent);
                
                
                products.forEach((p) -> {  
                HBox newHBox = new HBox();
                newHBox.setPadding(new Insets(10, 0, 10, 10));
                newHBox.setSpacing(20);
                newHBox.setStyle("-fx-background-color:#b2e4d0;-fx-background-radius:5.0;");
                
                String productId = p.getInteger("product_id").toString();
                String productTitle = p.getString("title");
                String productDescription = p.getString("description");
                String productInfo = productTitle + " \n" + productDescription;
//                String productQuantity = p.getInteger("quantity").toString();
                String productPrice = p.getDouble("price").toString();
                
                Label productInfoLabel = new Label(productInfo);
                productInfoLabel.setMaxWidth(280);
                productInfoLabel.setMinWidth(280);
                productInfoLabel.setPrefWidth(280);
                productInfoLabel.setWrapText(true);
                
                
                
                Label productPriceLabel = new Label(productPrice);
                productPriceLabel.setMinHeight(50);
                productPriceLabel.setMaxHeight(50);
                productPriceLabel.setPrefHeight(50);
                
                Label productIdLabel = new Label(productId);
                productIdLabel.setVisible(false);
                newHBox.getChildren().add(productInfoLabel);

                newHBox.getChildren().add(productPriceLabel);
                newHBox.getChildren().add(productIdLabel);
                
                
                HBoxContent.add(newHBox);
                
                //end for loop for products
                });
                categoryProductsListView.setItems(HBoxContent);
                
              //end for loop for category  
            }
        
                ));//end event
    
        }
        
    }
    
  
    @FXML
    private void btnInventoryReportAction(ActionEvent event) {
        
    }

    
    
    @FXML
    private void btnCreditSaleAction(ActionEvent event) {
        
        if( (txtCustomerDetails.getText().isEmpty()) || (checkoutListView.getItems().isEmpty()) ){
            
            layout.showNotification("/resources/smallinfo.png", "", "Ensure cart and customer details are not empty");
            
        }
        else{
        layout.showModal("/resources/CreditPaymentModal.fxml", event);
        layout.getCreditPaymentModalController().getlblCustomerDetails().setText(txtCustomerDetails.getText());
        layout.getCreditPaymentModalController().getlblExpectedAmount().setText(lblTotal.getText());
        layout.getCreditPaymentModalController().getlblBalance().setText(lblTotal.getText());
        }
        
    
    }

    @FXML
    private void btnPayAction(ActionEvent event) {
           if( checkoutListView.getItems().isEmpty() ){
            
               layout.showNotification("/resources/smallinfo.png", "", "Ensure cart is not empty.");
            
        }
        else{
        layout.showModal("/resources/FullPaymentModal.fxml", event);
        layout.getFullPaymentModalController().getlblCustomerDetails().setText(txtCustomerDetails.getText());
        layout.getFullPaymentModalController().getlblExpectedAmount().setText(lblTotal.getText());
           }
        
    }

        @FXML
    private void checkoutListViewAction(MouseEvent event) {
        
        
        
    }

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        
        
        if(checkoutListView.getSelectionModel().isEmpty()){
            
            layout.showNotification("/resources/smallinfo.png", "", "You have not selected any item.");
            
            
        }
        else{
            
        Label productIdLabel = (Label)checkoutListView.getSelectionModel().getSelectedItem().getChildren().get(3);
        Label productTotalLabel = (Label)checkoutListView.getSelectionModel().getSelectedItem().getChildren().get(2);
        Label productQuantityLabel = (Label)checkoutListView.getSelectionModel().getSelectedItem().getChildren().get(1);
        Double selectedProductQuantity = Double.parseDouble(productQuantityLabel.getText());
        int selectedProductId = Integer.parseInt(productIdLabel.getText());
        

        Double selectedProductTotal = Double.parseDouble(productTotalLabel.getText());

        
         System.out.println(subtotal);
         
         subtotal = subtotal - selectedProductTotal;
         Product tempProduct = Product.findById(selectedProductId);
         
       tempProduct.set("quantity", (tempProduct.getDouble("quantity") + selectedProductQuantity)).saveIt();
        
        layout.getMainBorderPane().setLeft(productsListAnchorPane);
         lblTotal.setText(subtotal.toString());
         
        checkoutList.remove(checkoutListView.getSelectionModel().getSelectedItem());
               
            
        }
        
        
        
    }
    
    
    @FXML
    private void btnCancelTransactionAction(ActionEvent event) {
        
        if(checkoutListView.getItems().isEmpty()){
            layout.showNotification("/resources/smallinfo.png", "", "Cart is already empty.");
        }
        cancelCheckout();
    }
    
   
    
    
    
    public void cancelCheckout(){
        if (!checkoutListView.getItems().isEmpty())
        {
            
            for(HBox hb: checkoutList){
                
                Label productIdLabel = (Label)hb.getChildren().get(3);
                Label productQuantityLabel = (Label)hb.getChildren().get(1);
                Double selectedProductQuantity = Double.parseDouble(productQuantityLabel.getText());
                int selectedProductId = Integer.parseInt(productIdLabel.getText());
                  
                 Product tempProduct = Product.findById(selectedProductId);
         
                tempProduct.set("quantity", (tempProduct.getDouble("quantity") + selectedProductQuantity)).saveIt();

                layout.getMainBorderPane().setLeft(productsListAnchorPane);
                 lblTotal.setText(subtotal.toString());
                
                
                
            }
            
            checkoutList.removeAll(checkoutList);
            subtotal = 0.0;
            lblTotal.setText(subtotal.toString());
            txtCustomerDetails.clear();
            layout.showNotification("/resources/smallinfo.png", "", "Transaction has been cancelled.");
            
        }
    }
    
    
    public void setProductsListAnchorPane(AnchorPane productsListAnchorPane) {
        TransactionPageController.productsListAnchorPane = productsListAnchorPane;
    }
    
    public ListView getCheckoutListView(){
        return checkoutListView;
    }
    
    public TextField getCustomerDetailsTextField(){
        return txtCustomerDetails;
    }
    
     public Label getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(Label lblTotal) {
        this.lblTotal = lblTotal;
    }

   
     public ObservableList<HBox> getCheckoutList() {
        return checkoutList;
    }

    public void setCheckoutList(ObservableList<HBox> checkoutList) {
        this.checkoutList = checkoutList;
    }
    
     public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
     public Double getSubtotal() {
        return this.subtotal;
    }
    
       public TextField getTxtCustomerDetails() {
        return txtCustomerDetails;
    }

    public void setTxtCustomerDetails(TextField txtCustomerDetails) {
        this.txtCustomerDetails = txtCustomerDetails;
    }

    
    
    
}
