/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock;

import edenrock.models.Cart;
import edenrock.models.CartProduct;
import edenrock.models.Product;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


/**
 *
 * @author Chewa
 */
public class DataManagement {
    
    private Double amountExpected;
    private Double amountPaid;
    private Double balance;
    
     public void completeTransaction(Layout layout, Label lblExpectedAmount, TextField txtAmountPaid){
        
        Cart cart = new Cart();
        cart.manageTime(false);
        
        
        
        
        amountExpected = Double.parseDouble(lblExpectedAmount.getText());
    
        if (txtAmountPaid == null
              ){
            amountPaid = amountExpected;
        }
        else{
            if(!txtAmountPaid.getText().isEmpty()
                &&
            txtAmountPaid.getText() != null
                ){
            amountPaid = Double.parseDouble(txtAmountPaid.getText());
        }
            
        }
        
        
         
        
         balance = amountExpected - amountPaid;
        
            
        
        cart.set("balance", balance)
        .set("subtotal", amountExpected);
        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cart.set("created_at", sdf.format(todaysDate));
        
        if (!layout.getTransactionPageController().getTxtCustomerDetails().getText().isEmpty()
                &&
            layout.getTransactionPageController().getTxtCustomerDetails().getText() != null
                ){
          cart.set("customer", layout.getTransactionPageController().getTxtCustomerDetails().getText());   
           Calendar myCal = Calendar.getInstance();
        myCal.setTime(cart.getDate("created_at"));    
        myCal.add(Calendar.MONTH, +2);    
        SimpleDateFormat sdfDue = new SimpleDateFormat("yyyy-MM-dd");
        cart.set("due_date", sdfDue.format(myCal.getTime())).saveIt(); 
        }
        else
        {
            cart.set("due_date", sdf.format(todaysDate)).saveIt(); 
        }
        

        
        
        ObservableList<HBox> tempList = layout.getTransactionPageController().getCheckoutListView().getItems();
        for(HBox tempHBox: tempList) {
            
            Label idTempLabel = (Label)tempHBox.getChildren().get(3);
            Label totalTempLabel = (Label)tempHBox.getChildren().get(2);
            Label multiplierTempLabel = (Label)tempHBox.getChildren().get(1);
            
            int tempId = Integer.parseInt(idTempLabel.getText());
            Product tempProduct = Product.findById(tempId);
            System.out.println(tempProduct);
            
            int tempMultiplier = Integer.parseInt(multiplierTempLabel.getText());
            double tempTotal = Double.parseDouble(totalTempLabel.getText());
     
         
            CartProduct newCartProduct = new CartProduct();
        newCartProduct.set("product_id",tempProduct.getInteger("product_id"))
                .set("cart_id", cart.getInteger("cart_id"))
                .set("multiplier",tempMultiplier)
                .set("total",tempTotal).saveIt();
            

        }
        layout.getTodaysReportController().loadSalesTable();
        layout.getDebtorsReportController().loadSalesTable();
        layout.getSalesReportController().loadSalesTable();
        
        layout.getTransactionPageController().getCheckoutList().removeAll(layout.getTransactionPageController().getCheckoutList());
        layout.getTransactionPageController().getLblTotal().setText("0.0");
        layout.getTransactionPageController().getTxtCustomerDetails().clear();
        layout.getTransactionPageController().setSubtotal(0.0);
        
        

    }
     
     
     public void generateProductList(Layout layout, ObservableList<HBox> productHBoxList, ObservableList<HBox> productList, ListView paymentListView){

       productHBoxList.setAll(layout.getTransactionPageController().getCheckoutListView().getItems());
       for(HBox hb: productHBoxList){
           HBox tempCreditHBox = new HBox();
            tempCreditHBox.setStyle("-fx-background-color:#b2e4d0;-fx-background-radius:5.0;");
            tempCreditHBox.setPadding(new Insets(10, 0, 10, 10));
            Label titleLabel = (Label)hb.getChildren().get(0);
            Label quantityLabel = (Label)hb.getChildren().get(1);
            Label subtotalLabel = (Label)hb.getChildren().get(2);

            Label tempTitleLabel = new Label(titleLabel.getText());
            tempTitleLabel.setMaxWidth(175);
            tempTitleLabel.setMinWidth(175);
            tempTitleLabel.setPrefWidth(175);
            
            Label tempQuantityLabel = new Label(quantityLabel.getText());
            tempQuantityLabel.setMaxWidth(100);
            tempQuantityLabel.setMinWidth(100);
            tempQuantityLabel.setPrefWidth(100);


            Label tempSubtotalLabel = new Label(subtotalLabel.getText());
            tempSubtotalLabel.setMaxWidth(80);
            tempSubtotalLabel.setMinWidth(80);
            tempSubtotalLabel.setPrefWidth(80);

            tempCreditHBox.getChildren().add(tempTitleLabel);
            tempCreditHBox.getChildren().add(tempQuantityLabel);
            tempCreditHBox.getChildren().add(tempSubtotalLabel);
            
            productList.add(tempCreditHBox);
            
            
           
       }
            
        paymentListView.setItems(productList);
    }
     
     
     public boolean validateNumber(TextField txtField)
     {
         Pattern p = Pattern.compile("[0-9]+");
         Matcher m = p.matcher(txtField.getText());
         
         if(m.find() && m.group().equals(txtField.getText()))
         {
             return true;
         }
         else{
              return false;
         }
     }
}
