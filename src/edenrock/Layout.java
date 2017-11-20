/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock;

import com.jfoenix.controls.JFXDrawer;
import edenrock.controllers.CartContentController;
import edenrock.controllers.CategoryFormController;
import edenrock.controllers.CreditPaymentModalController;
import edenrock.controllers.DebtorsReportController;
import edenrock.controllers.FullPaymentModalController;
import edenrock.controllers.InventoryFormController;
import edenrock.controllers.InventoryReportController;
import edenrock.controllers.MainLayoutController;
import edenrock.controllers.NotificationsPaneController;
import edenrock.controllers.ProductDetailsController;
import edenrock.controllers.ProductsListController;
import edenrock.controllers.SalesReportController;
import edenrock.controllers.SelectReportController;
import edenrock.controllers.TodaysReportController;
import edenrock.controllers.TransactionPageController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;



/**
 *
 * @author Chewa
 */
public class Layout {
    
    private static FXMLLoader loginFxmlLoader; 
    private static FXMLLoader mainFxmlLoader;
    private static FXMLLoader smallFxmlLoader;
    private static FXMLLoader dialogFxmlLoader;
    private static Stage mainStage;
    private static Stage dialogStage;
    
    private static BorderPane mainBorderPane;
    private static AnchorPane dialogAnchorPane;
    private static ProductsListController productsListController;
    private static ProductDetailsController productDetailsController;
    private static TransactionPageController transactionPageController;
    private static CreditPaymentModalController creditPaymentModalController;
    private static FullPaymentModalController fullPaymentModalController;
    private static InventoryReportController inventoryReportController;
    private static SalesReportController salesReportController;
    private static DebtorsReportController debtorsReportController;
    private static TodaysReportController todaysReportController;
    private static MainLayoutController mainLayoutController;
    private static SelectReportController selectReportController;
    private static NotificationsPaneController notificationsPaneController;
    private static CategoryFormController categoryFormController;
    private static InventoryFormController inventoryFormController;
    private static CartContentController cartContentController;
    
    private static Stage loginStage;
    
    
    private Notifications notificationBuilder;
    private Image img;

   
    
    
    public void initLoginLayout(Stage loginStage,String resource){
                       
        try {
        loginFxmlLoader = new FXMLLoader();
        Parent root = loginFxmlLoader.load(getClass().getResource("/resources/Login.fxml"));
        Scene scene = new Scene(root);
        
        loginStage.setTitle("WELCOME");
        loginStage.setScene(scene);
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.show();
        Layout.loginStage = loginStage;
        } catch (IOException ex) {
              ex.printStackTrace();
        }   
        
    }
    
    public void initMainLayout(){
        
        try {         
            mainStage = new Stage();
            mainFxmlLoader = new FXMLLoader();
            mainFxmlLoader.setLocation(getClass().getResource("/resources/MainLayout.fxml"));
            mainBorderPane= (BorderPane) mainFxmlLoader.load();  
            Scene scene = new Scene(mainBorderPane);
            scene.getStylesheets().add(getClass().getResource("/resources/list-view-style.css").toExternalForm());
            
            mainStage.setTitle("EDEN ROCK");
//            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.setScene(scene);
            mainStage.show();

      }
      catch (IOException ex) 
      {
      ex.printStackTrace();
      }
       
        
    }
    
    public AnchorPane loadPage(String resource)
    {
        AnchorPane smallAnchorPane = null;
        
        try {
             smallFxmlLoader = new FXMLLoader();
            smallFxmlLoader.setLocation(getClass().getResource(resource)); 
            smallAnchorPane = (AnchorPane) smallFxmlLoader.load();
           
        } catch (IOException ex) {
            Logger.getLogger(Layout.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    
      return smallAnchorPane;  
    }
    
    public boolean showModal(String resource, ActionEvent event)  {
    
         try {
         dialogStage = new Stage();
         dialogFxmlLoader = new FXMLLoader();
        dialogFxmlLoader.setLocation(getClass().getResource(resource));
        dialogAnchorPane = (AnchorPane) dialogFxmlLoader.load();
        Scene scene = new Scene(dialogAnchorPane);
        dialogStage.setTitle("EDEN ROCK");
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(
        ((Node)event.getSource()).getScene().getWindow() );
        dialogStage.toFront();
        dialogStage.centerOnScreen();
        dialogStage.show();
                                    
            
        } catch (IOException ex) {
            Logger.getLogger(Layout.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     return true;
}
    public void initPopup(JFXDrawer drawer, String drawerResource)
    {
        
        try {
           FXMLLoader l = new FXMLLoader();
           l.setLocation(getClass().getResource(drawerResource));
           AnchorPane a = (AnchorPane)l.load();
           drawer.setSidePane(a);
          
            
            
        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void showPopup(JFXDrawer myDrawer)
    {
        if (!myDrawer.isShown())
        {
        myDrawer.toFront();
        myDrawer.open();
        }
        else
        {
            myDrawer.close();
        }
        
    }
    
    public Notifications getNotifcationTray(String resource, String notificationText, String notificationTitle)
    {
        
           img = new Image(resource);
           notificationBuilder = Notifications.create()
                    .title(notificationText)
                    .text(notificationTitle)
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT);
           notificationBuilder.darkStyle();
           
           return notificationBuilder;
           
    }
    
    public void showNotification(String resource, String message, String title)
    {
    
        getNotifcationTray(resource, message, title).show();
    }
    
     public BorderPane getMainBorderPane()
    {
        return Layout.mainBorderPane;
    }
     
     public ProductsListController getProductsListController(){
         return Layout.productsListController;
     }
     public void setProductsListController(ProductsListController productsListController){
         Layout.productsListController = productsListController;
     }

    public ProductDetailsController getProductDetailsController() {
        return Layout.productDetailsController;
    }
    
    public void setProductDetailsController(ProductDetailsController productDetailsController) {
        Layout.productDetailsController = productDetailsController;
    }

    public TransactionPageController getTransactionPageController() {
        return Layout.transactionPageController;
    }

    public void setTransactionPageController(TransactionPageController transactionPageController) {
        Layout.transactionPageController = transactionPageController;
    }

    public CreditPaymentModalController getCreditPaymentModalController() {
        return Layout.creditPaymentModalController;
    }
    
    public void setCreditPaymentModalController(CreditPaymentModalController creditPaymentModalController) {
        Layout.creditPaymentModalController = creditPaymentModalController;
    }

    public void setFullPaymentModalController(FullPaymentModalController fullPaymentModalController) {
        Layout.fullPaymentModalController = fullPaymentModalController;
    }
    
    public FullPaymentModalController getFullPaymentModalController() {
        return Layout.fullPaymentModalController;
    }

    public InventoryReportController getInventoryReportController() {
        return Layout.inventoryReportController;
    }

    public SalesReportController getSalesReportController() {
        return Layout.salesReportController;
    }
    
    public void setInventoryReportController(InventoryReportController inventoryReportController) {
        Layout.inventoryReportController = inventoryReportController;
    }

    public void setSalesReportController(SalesReportController salesReportController) {
        Layout.salesReportController = salesReportController;
    }

    public void setDebtorsReportController(DebtorsReportController debtorsReportController) {
        Layout.debtorsReportController = debtorsReportController;
    }
    
        public void setTodaysReportController(TodaysReportController todaysReportController) {
        Layout.todaysReportController = todaysReportController;
    }

    public MainLayoutController getMainLayoutController() {
        return Layout.mainLayoutController;
    }

    public void setMainLayoutController(MainLayoutController mainLayoutController) {
        Layout.mainLayoutController = mainLayoutController;
    }

    public TodaysReportController getTodaysReportController() {
        return Layout.todaysReportController;
    }
    
    public DebtorsReportController getDebtorsReportController() {
        return Layout.debtorsReportController;
    }
    
    public SelectReportController getSelectReportController() {
        return Layout.selectReportController;
    }

    public void setSelectReportAnchorPane(SelectReportController selectReportController) {
        Layout.selectReportController = selectReportController;
    }

    public void setNotificationsPaneController(NotificationsPaneController notificationsPaneController) {
        Layout.notificationsPaneController = notificationsPaneController;
    }
    
    public NotificationsPaneController getNotificationsPaneController() {
        return Layout.notificationsPaneController;
    }

    public CategoryFormController getCategoryFormController() {
        
        return Layout.categoryFormController;
    }
    
    public void setCategoryFormController(CategoryFormController categoryFormController) {
        
        Layout.categoryFormController = categoryFormController;
    }
    
    public InventoryFormController getInventoryFormController() {
        
        return Layout.inventoryFormController;
    }
    
    public void setInventoryFormController(InventoryFormController inventoryFormController) {
        
        Layout.inventoryFormController = inventoryFormController;
    }
    
     public Stage getLoginStage() {
        return loginStage;
    }

    public void setLoginStage(Stage loginStage) {
        Layout.loginStage = loginStage;
    }

    public CartContentController getCartContentController()
    {
        return Layout.cartContentController;
    }
    
    public void setCartContentController(CartContentController cartContentController)
    {
        Layout.cartContentController = cartContentController;
    }
}

