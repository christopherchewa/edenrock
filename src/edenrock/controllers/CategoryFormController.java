/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.models.Category;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class CategoryFormController implements Initializable, LayoutInterface {

    @FXML
    private TextField txtCategoryTitle;
    @FXML
    private JFXButton btnCategorySubmit;
    private Layout layout;
    @FXML
    private JFXButton btnCloseDialog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
    }    
    
     @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
    }


    @FXML
    private void btnCategorySubmitAction(ActionEvent event) {
        
        if(!txtCategoryTitle.getText().isEmpty() && txtCategoryTitle.getText() !=null)
        {
            Category newCategory = new Category();
        newCategory.set("title", txtCategoryTitle.getText()).saveIt();
        layout.getTransactionPageController().generateCategoryButtons();
        layout.getInventoryFormController().generateCategoryComboBox();
        ((Node)event.getSource()).getScene().getWindow().hide();
        layout.showNotification("/resources/smalltick.png", "Complete!", "Category has been added");
        }
        else{
            layout.showNotification("/resources/smallinfo.png", "", "Please enter a category.");
            
        }
        
        
    }

   
    @FXML
    private void btnCloseDialogAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
}
