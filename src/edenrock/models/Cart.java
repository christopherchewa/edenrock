/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Chewa
 */
@Table("cart")
@IdName("cart_id")
public class Cart extends Model{
    
    private final StringProperty cartId;
    private final StringProperty customer;
    private final DoubleProperty subtotal;
    private final DoubleProperty balance;
    private final ObjectProperty cartDate;
    private final ObjectProperty dueDate;
    
     public Cart()
    {
        this(null, null, 0.0, 0.0, null, null);
    }
    
    public Cart(String cartId, String customer, Double subtotal, Double balance, Date cartDate, Date dueDate){
       
        this.cartId = new SimpleStringProperty(cartId);
        this.customer = new SimpleStringProperty(customer);
        this.subtotal = new SimpleDoubleProperty(subtotal);
        this.balance = new SimpleDoubleProperty(balance);
        this.cartDate = new SimpleObjectProperty(cartDate);
        this.dueDate = new SimpleObjectProperty(dueDate);
        
        
    }
    
     public StringProperty cartIdProperty()
       {
           return cartId;
       }
      
       
    public StringProperty customerProperty() {
        return customer;
    }
    
   
    public DoubleProperty subtotalProperty() {
        return subtotal;
    }
    
    public DoubleProperty balanceProperty() {
        return balance;
    }
    
        
    public ObjectProperty cartDateProperty() {
        return cartDate;
    }
    
    public ObjectProperty dueDateProperty() {
        return dueDate;
    }
    
    
}
