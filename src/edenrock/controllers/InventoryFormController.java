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
import edenrock.models.Category;
import edenrock.models.Product;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Chewa
 */
public class InventoryFormController implements Initializable, LayoutInterface, DataManagementInterface {
    private Layout layout;
    private ObservableList<String> checkBoxItems = FXCollections.observableArrayList();
    
   @FXML
    private AnchorPane inventoryFormAnchorPane;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private ComboBox<String> cbCategory;
    @FXML
    private TextField txtPrice;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private TextField txtQuantity;
    private DataManagement dataManagement;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLayout(layout);
        setDataManagement(dataManagement);
        
        
    }    
    

    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
        layout.setInventoryFormController(this);
        generateCategoryComboBox();
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
        
        clearTextBoxes();
    }

    @FXML
    private void btnSubmitAction(ActionEvent event) {
        
        saveNewProduct();
        
        layout.getInventoryReportController().loadProductsTable();
    
    }
    
    protected void generateCategoryComboBox(){
        
        checkBoxItems.removeAll(checkBoxItems);
        List<Category> categoryList = Category.findAll();
        for(Category c:categoryList){
            checkBoxItems.add(c.getString("title"));
            
        }
        
        cbCategory.setItems(checkBoxItems);
        
    }
    
    private void saveNewProduct(){
        if ( (txtName.getText() == null) 
        || (txtName.getText().isEmpty()) 
                
        || (txtDescription.getText() == null) 
        || (txtDescription.getText().isEmpty()) 
                
        || (cbCategory.getSelectionModel().isEmpty()) 
                
        || (txtPrice.getText() == null) 
        || (txtPrice.getText().isEmpty())
                
        || (txtQuantity.getText().isEmpty())
        || (txtQuantity.getText() == null) )
        
        {
            
            layout.showNotification("/resources/smallinfo.png", "", "Ensure all fields are not empty");
            
        
        }
        else
        {
            
            if (dataManagement.validateNumber(txtPrice) && dataManagement.validateNumber(txtQuantity))
            {
                Product newProduct = new Product();
            newProduct.set("title", txtName.getText());
                  newProduct.set("description", txtDescription.getText());
                  Category selectedCategory = Category.findFirst("title = ?", cbCategory.getSelectionModel().getSelectedItem());
                    newProduct.set("category_id", selectedCategory.getInteger("category_id"))
                    .set("price", Double.parseDouble(txtPrice.getText()))
                    .set("quantity", Double.parseDouble(txtQuantity.getText())).saveIt();
                    layout.showNotification("/resources/smalltick.png", "Complete!", "Product has been added");
                    clearTextBoxes();
            }
            else{
                layout.showNotification("/resources/smallinfo.png", "", "Enter the value of a correct format."); 
            }
            
        }
    
    }
    
    private void clearTextBoxes(){
        txtName.clear();
        txtDescription.clear();
        cbCategory.getSelectionModel().clearSelection();
        txtPrice.clear();
        txtQuantity.clear();
    }

    @Override
    public void setDataManagement(DataManagement dataManagement) {
        dataManagement = new DataManagement();
        this.dataManagement = dataManagement;
    }
}
