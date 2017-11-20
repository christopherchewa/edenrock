/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import edenrock.Layout;
import edenrock.LayoutInterface;
import java.net.URL;
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
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class CartContentController implements Initializable, LayoutInterface {

    @FXML
    private JFXButton btnCloseDialog;
    @FXML
    private ListView<HBox> cartProductsListView;
    @FXML
    private Label lblCustomerInfo;
    @FXML
    private Label lblCartTotal;
    @FXML
    private Label lblBalance;

    private ObservableList<HBox> cartProductsList = FXCollections.observableArrayList();
    private Layout layout;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLayout(layout);
    }    
    
    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        layout.setCartContentController(this);
        cartProductsListView.setItems(layout.getSalesReportController().getCartProductsList());
        this.layout = layout;
        lblCustomerInfo.setText(layout.getSalesReportController().getCustomerInfo());
        lblCartTotal.setText(layout.getSalesReportController().getCartTotal().toString());
        lblBalance.setText(layout.getSalesReportController().getCartBalance().toString());
        
    }
    @FXML
    private void btnCloseDialogAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    
    public ListView<HBox> getCartProductsListView() {
        return cartProductsListView;
    }

    
    
    
    
    
}
