/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock;

import edenrock.controllers.AccountDrawerNotificationsContentController;
import edenrock.controllers.AddNewDrawerContentController;
import edenrock.controllers.CartContentController;
import edenrock.controllers.DebtorsReportController;
import edenrock.controllers.InventoryFormController;
import edenrock.controllers.InventoryReportController;
import edenrock.controllers.LoginController;
import edenrock.controllers.MainLayoutController;
import edenrock.controllers.NotificationsPaneController;
import edenrock.controllers.ProductDetailsController;
import edenrock.controllers.ProductsListController;
import edenrock.controllers.SalesReportController;
import edenrock.controllers.SelectReportController;
import edenrock.controllers.TodaysReportController;
import edenrock.controllers.TransactionPageController;
import edenrock.models.User;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Chewa
 */
public class MainApp extends Application implements LayoutInterface {
    
    private User user;
    private FXMLLoader loginFXMLLoader;
    private Layout layout;
    private Stage loginStage;

   
    
    private final String inventoryForm = "/resources/InventoryForm.fxml";
    private final String selectReport = "/resources/SelectReport.fxml";
    
    private final String inventoryReport = "/resources/InventoryReport.fxml";
    private final String salesReport = "/resources/SalesReport.fxml";
    private final String debtorsReport = "/resources/DebtorsReport.fxml";
    private final String todaysReport = "/resources/TodaysReport.fxml";
    
    private final String productDetails = "/resources/ProductDetails.fxml";
    private final String productsList = "/resources/ProductsList.fxml";
    
    private final String transactionPage = "/resources/TransactionPage.fxml";
    
//    private final String notificationsPage = "/resources/AccountDrawerNotificationsContent.fxml";
    
    private static AnchorPane inventoryFormAnchorPane;
    private static AnchorPane inventoryReportAnchorPane;
    private static AnchorPane salesReportAnchorPane;
    private static AnchorPane debtorsReportAnchorPane;
    private static AnchorPane todaysReportAnchorPane;
    private static AnchorPane selectReportAnchorPane;
    private static AnchorPane productDetailsAnchorPane;
    private static AnchorPane productsListAnchorPane;
    private static AnchorPane transactionPageAnchorPane;
    
//    private static AnchorPane notificationsPageAnchorPane;
    
    
    private LoginController loginController;
    private MainLayoutController mainLayoutController;
    
    private SelectReportController selectReportController;
    private InventoryFormController inventoryFormController;
    private InventoryReportController inventoryReportController;
    
    private SalesReportController salesReportController;
    private DebtorsReportController debtorsReportController;
    private TodaysReportController todaysReportController;
    
    private ProductDetailsController productDetailsController;
    private ProductsListController productsListController;
    private AddNewDrawerContentController addNewDrawerContentController;
    
    private TransactionPageController transactionPageController;
    
    private AccountDrawerNotificationsContentController accountDrawerNotificationsContentController;
    private NotificationsPaneController notificationsPaneController;
    private CartContentController cartContentController;
    
    
    public MainApp() {
        this.mainLayoutController = new MainLayoutController();
        this.loginController = new LoginController();
        
        this.inventoryFormController = new InventoryFormController();
        this.inventoryReportController = new InventoryReportController();
        
        this.salesReportController = new SalesReportController();
        this.debtorsReportController = new DebtorsReportController();
        this.todaysReportController = new TodaysReportController();
        
        
        this.selectReportController = new SelectReportController();
        this.productDetailsController = new ProductDetailsController();
        this.productsListController = new ProductsListController();
        this.transactionPageController = new TransactionPageController();
        this.addNewDrawerContentController = new AddNewDrawerContentController();
        this.accountDrawerNotificationsContentController = new AccountDrawerNotificationsContentController();
        this.notificationsPaneController = new NotificationsPaneController();
        this.cartContentController = new CartContentController();
       
    }
       
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
       DbConnection.connect();
       this.loginStage = primaryStage;
       setLayout(layout);
       
       
    }
    
    @Override
    public void setLayout(Layout layout) {
        layout = new Layout();
        this.layout = layout;
        
        
        
        layout.initLoginLayout(loginStage, "/resources/Login.fxml");
        
        loadLayouts();
    }
    
     public Stage getLoginStage() {
        return loginStage;
    }

     public void loadLayouts()
    {
        
        
        inventoryFormAnchorPane = layout.loadPage(inventoryForm);
        inventoryReportAnchorPane = layout.loadPage(inventoryReport);
        
        
        
        selectReportAnchorPane = layout.loadPage(selectReport);
        productDetailsAnchorPane = layout.loadPage(productDetails);
        productsListAnchorPane = layout.loadPage(productsList);
        transactionPageAnchorPane = layout.loadPage(transactionPage);
        salesReportAnchorPane = layout.loadPage(salesReport);
        debtorsReportAnchorPane = layout.loadPage(debtorsReport);
        todaysReportAnchorPane = layout.loadPage(todaysReport);

        mainLayoutController.setInventoryFormAnchorPane(inventoryFormAnchorPane);
        mainLayoutController.setSelectReportAnchorPane(selectReportAnchorPane);
        mainLayoutController.setProductDetailsAnchorPane(productDetailsAnchorPane);
        mainLayoutController.setProductsListAnchorPane(productsListAnchorPane);
        mainLayoutController.setTransactionPageAnchorPane(transactionPageAnchorPane);
        mainLayoutController.setDebtorsReportAnchorPane(debtorsReportAnchorPane);
        mainLayoutController.setTodaysReportAnchorPane(todaysReportAnchorPane);
        
        
        
        loginController.setProductsListAnchorPane(productsListAnchorPane);
        loginController.setTransactionPageAnchorPane(transactionPageAnchorPane);
        
        
        transactionPageController.setProductsListAnchorPane(productsListAnchorPane);
        productsListController.setProductDetailsAnchorPane(productDetailsAnchorPane);
        productDetailsController.setProductsListAnchorPane(productsListAnchorPane);
        productDetailsController.setTransactionPageAnchorPane(transactionPageAnchorPane);
        
        addNewDrawerContentController.setInventoryFormAnchorPane(inventoryFormAnchorPane);
        addNewDrawerContentController.setInventoryReportAnchorPane(inventoryReportAnchorPane);
        
        selectReportController.setInventoryReportAnchorPane(inventoryReportAnchorPane);
        selectReportController.setSalesReportAnchorPane(salesReportAnchorPane);
        selectReportController.setTransactionPageAnchorPane(transactionPageAnchorPane);
        selectReportController.setProductsListAnchorPane(productsListAnchorPane);
        selectReportController.setDebtorsReportAnchorPane(debtorsReportAnchorPane);
        selectReportController.setTodaysReportAnchorPane(todaysReportAnchorPane);
        
        
//        layout.setProductDetailsController(productDetailsController);
        layout.setNotificationsPaneController(notificationsPaneController);
        
        
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }



    
}
