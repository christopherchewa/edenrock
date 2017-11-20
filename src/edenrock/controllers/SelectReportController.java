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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class SelectReportController implements Initializable, LayoutInterface {

    private static AnchorPane inventoryReportAnchorPane;
    private static AnchorPane salesReportAnchorPane;
    private static AnchorPane transactionPageAnchorPane;
    private static AnchorPane productsListAnchorPane;
    private static AnchorPane debtorsReportAnchorPane;
    private static AnchorPane todaysReportAnchorPane;

    @FXML
    private JFXButton btnInventoryReport;
    @FXML
    private JFXButton btnDebtorsReport;
    @FXML
    private JFXButton btnSalesReport;
    private Layout layout;
    @FXML
    private AnchorPane selectReportAnchorPane;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnTodaysReport;
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLayout(layout);
        layout.setSelectReportAnchorPane(this);
    }    

    @FXML
    private void btnInventoryReportAction(ActionEvent event) {
        layout.getMainBorderPane().setRight(inventoryReportAnchorPane);
        layout.getInventoryReportController().loadProductsTable();
        layout.getInventoryReportController().clearFields();
    }

    @FXML
    private void btnDebtorsReportAction(ActionEvent event) {
        
        layout.getMainBorderPane().setRight(debtorsReportAnchorPane);
        layout.getDebtorsReportController().clearDetails();
    }

    @FXML
    private void btnSalesReportAction(ActionEvent event) {
        layout.getMainBorderPane().setRight(salesReportAnchorPane);
        
    }
    
    @FXML
    private void btnTodaysReportAction(ActionEvent event) {
        layout.getMainBorderPane().setRight(todaysReportAnchorPane);
        layout.getTodaysReportController().loadSalesTable();
        
    }

   

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }

     

    @FXML
    private void btnCloseAction(ActionEvent event) {
        
        layout.getMainBorderPane().setRight(transactionPageAnchorPane);
        layout.getMainBorderPane().setLeft(productsListAnchorPane);
        
    }

    public void setTransactionPageAnchorPane(AnchorPane transactionPageAnchorPane) {
        SelectReportController.transactionPageAnchorPane = transactionPageAnchorPane;
    }
    
    public void setInventoryReportAnchorPane(AnchorPane inventoryReportAnchorPane) {
        SelectReportController.inventoryReportAnchorPane = inventoryReportAnchorPane;
    }
     
    public void setSalesReportAnchorPane(AnchorPane salesReportAnchorPane) {
        SelectReportController.salesReportAnchorPane = salesReportAnchorPane;
    }

    public void setProductsListAnchorPane(AnchorPane productsListAnchorPane) {
        SelectReportController.productsListAnchorPane = productsListAnchorPane;
    }
    
     public AnchorPane getProductsListAnchorPane() {
        return SelectReportController.productsListAnchorPane;
    }

    public void setDebtorsReportAnchorPane(AnchorPane debtorsReportAnchorPane) {
        SelectReportController.debtorsReportAnchorPane = debtorsReportAnchorPane;
    }

    public void setTodaysReportAnchorPane(AnchorPane todaysReportAnchorPane) {
        SelectReportController.todaysReportAnchorPane = todaysReportAnchorPane;
    }


    
}
