/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.models.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class ProductsListController implements Initializable, LayoutInterface {

    private static AnchorPane productDetailsAnchorPane;
    
    private Layout layout;
    
    @FXML
    private Label lblCategoryTitle;
    @FXML
    private AnchorPane productsListAnchorPane;
    @FXML
    private ListView<HBox> categoryProductsListView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLayout(layout);
    }    
    
    
    public Label getCategoryTitle(){
        
        return lblCategoryTitle;
    }

    @FXML
    private void categoryProductsListView(MouseEvent event) {
        
        //get selected product id from invisible label with productId
        if(categoryProductsListView.getSelectionModel().isEmpty())
        {
            System.out.println("No products in this category");
        }
        else
        {
        
        Label productIdLabel = (Label)categoryProductsListView.getSelectionModel().getSelectedItem().getChildren().get(2);
        int selectedProductId = Integer.parseInt(productIdLabel.getText());
        
        
        
        layout.getMainBorderPane().setLeft(productDetailsAnchorPane);
        
        //get product title/description/price label/cancel button
//        VBox productDetailsVBox = (VBox)productDetailsAnchorPane.getChildren().get(0);
//        HBox productTitleHBox = (HBox)productDetailsVBox.getChildren().get(1);
//        Label productTitleLabel = (Label)productTitleHBox.getChildren().get(0);
//        
//        Label productDescriptionLabel = (Label)productDetailsVBox.getChildren().get(3);
//        Label productPriceLabel = (Label)productDetailsVBox.getChildren().get(4);
//        
        //get product information
        Product selectedProductDetail = Product.findById(selectedProductId);
        
        layout.getProductDetailsController().setSelectedProductDetail(selectedProductDetail);
        
        
        String productTitle = selectedProductDetail.getString("title");
        String productDescription = selectedProductDetail.getString("description");
        String productQuantity = selectedProductDetail.getDouble("quantity").toString();
        String productPrice = selectedProductDetail.getDouble("price").toString(); 
        
        layout.getProductDetailsController().getLblProductTitle().setText(productTitle);
        layout.getProductDetailsController().getLblProductDescription().setText(productDescription);
        layout.getProductDetailsController().getLblProductQuantity().setText(productQuantity);
        layout.getProductDetailsController().getLblProductPrice().setText(productPrice);
        
        }
       
        
    }

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }

    public void setProductDetailsAnchorPane(AnchorPane productDetailsAnchorPane) {
        ProductsListController.productDetailsAnchorPane = productDetailsAnchorPane;
    }
}
