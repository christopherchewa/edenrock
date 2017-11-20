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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class AddNewDrawerContentController implements Initializable, LayoutInterface {

    private static AnchorPane inventoryFormAnchorPane;
    private static AnchorPane inventoryReportAnchorPane;

    @FXML
    private JFXButton btnCategory;
    @FXML
    private JFXButton btnProduct;
    private Layout layout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLayout(layout);
        
    }    

    @FXML
    private void btnCategoryAction(ActionEvent event) {
        layout.showModal("/resources/CategoryForm.fxml", event);
        layout.getMainLayoutController().getAddNewDrawer().close();
    }

    @FXML
    private void btnProductAction(ActionEvent event) {
        
        layout.getMainBorderPane().setLeft(inventoryFormAnchorPane);
        layout.getMainBorderPane().setRight(inventoryReportAnchorPane);
        layout.getMainLayoutController().getAddNewDrawer().close();

        
    }

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }

    public void setInventoryFormAnchorPane(AnchorPane inventoryFormAnchorPane) {
        AddNewDrawerContentController.inventoryFormAnchorPane = inventoryFormAnchorPane;
    }
    
    public void setInventoryReportAnchorPane(AnchorPane inventoryReportAnchorPane) {
        AddNewDrawerContentController.inventoryReportAnchorPane = inventoryReportAnchorPane;
    }
    
}
