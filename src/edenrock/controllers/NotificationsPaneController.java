/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.controllers;

import com.jfoenix.controls.JFXButton;
import edenrock.Layout;
import edenrock.LayoutInterface;
import edenrock.NotificationValues;
import edenrock.models.Cart;
import edenrock.models.Product;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
public class NotificationsPaneController implements Initializable, LayoutInterface {

    private Layout layout;
    
    @FXML
    private ListView<HBox> inventoryListView;
    @FXML
    private ListView<VBox> debtorsListView;

   
    
    private ObservableList<HBox> inventoryList = FXCollections.observableArrayList();
    
    
    private ObservableList<VBox> debtorsList= FXCollections.observableArrayList();
    @FXML
    private JFXButton btnCloseDialog;

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
        layout.setNotificationsPaneController(this);
        this.layout = layout;
        generateNotifications();
    }
    
    @FXML
    private void btnCloseDialogAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    public void generateNotifications(){
        
        inventoryNotification();
        debtorsNotifications();
       
        
    }
    
    private void inventoryNotification()
    {
        List<Product> refillProducts = Product.where("quantity < ?", NotificationValues.REFILL_INVENTORY_QUANTITY);
        
        for(Product p : refillProducts){
            
            HBox invHBox = new HBox();
            
            Label productTitleLabel = new Label(p.getString("title"));
            Label productQuantityLabel = new Label(p.getDouble("quantity").toString());
            
             productTitleLabel.setMaxWidth(150);
                productTitleLabel.setMinWidth(150);
                productTitleLabel.setPrefWidth(150);
            
            invHBox.getChildren().add(productTitleLabel);
            invHBox.getChildren().add(productQuantityLabel);
            
            inventoryList.add(invHBox);
            
           
        }
        
        int inventoryListCount = refillProducts.size();
        inventoryListView.setItems(inventoryList);
    }
    
    private void debtorsNotifications()
    {
        List<Cart> debtors = Cart.where("balance > ?", 0.0);
        
        System.out.println(debtors);
        
        for(Cart c : debtors){
            
            VBox debVBox = new VBox();
            
            debVBox.setStyle("-fx-background-color:#b2e4d0;-fx-background-radius:5.0;");
            debVBox.setPadding(new Insets(5, 0, 5, 5));
            
            Label productTitleLabel = new Label("Customer:" + "  " + c.getString("customer"));
            Label productBalanceLabel = new Label("Balance:" + "  " + c.getDouble("balance").toString());
            Label productDueDateLabel = new Label("Date Due:" + "  " + c.getDate("due_date").toString());
            
            Date todaysDate = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.format(todaysDate);
            
            
            
            
            debVBox.getChildren().add(productTitleLabel);
            debVBox.getChildren().add(productBalanceLabel);
            debVBox.getChildren().add(productDueDateLabel);
            
            debtorsList.add(debVBox);
            
           
        }
        
        int debtorsListCount = debtors.size();
        debtorsListView.setItems(debtorsList);
    }
    
    
     public ListView<HBox> getInventoryListView() {
        return inventoryListView;
    }

    public ListView<VBox> getDebtorsListView() {
        return debtorsListView;
    }

    public ObservableList<HBox> getInventoryList() {
        return inventoryList;
    }

    public ObservableList<VBox> getDebtorsList() {
        return debtorsList;
    }

    public void setInventoryListView(ListView<HBox> tempListView) {
        inventoryListView = tempListView;
    }

    public void setInventoryList(ObservableList inventoryList) {
        this.inventoryList = inventoryList;
    }

    
}
