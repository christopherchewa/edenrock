/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Chewa
 */
@Table("products")
@IdName("product_id")
@Many2Many(other=Cart.class, join="cart_products", sourceFKName="product_id", targetFKName="cart_id")
public class Product extends Model {
    
    private final StringProperty productId;
    private final StringProperty title;
    private final StringProperty description;
    private final DoubleProperty price;
    private final DoubleProperty quantity;
    
    public Product()
    {
        this(null, null, null, 0.0, 0.0);
    }
    
    public Product(String productId, String title, String description, Double price, Double quantity){
       
        this.productId = new SimpleStringProperty(productId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleDoubleProperty(quantity);
        
        
    }
    
    
    public StringProperty productIdProperty()
       {
           return productId;
       }
      
       
    public StringProperty titleProperty() {
        return title;
    }
    
    public StringProperty descriptionProperty() {
        return description;
    }
    
   
    public DoubleProperty priceProperty() {
        return price;
    }
    
    public DoubleProperty quantityProperty() {
        return quantity;
    }
    
    
}
