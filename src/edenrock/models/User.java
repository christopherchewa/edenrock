/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock.models;



import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Chewa
 */

 

@Table("users")
@IdName("user_id")
public class User extends Model {
    
   
    private final StringProperty username;
    private final StringProperty password;
    
    
   
    public User(String username, String password)
    {
        
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        
    }
    
    
    
    public User()
    {  
        
            this(null, null);
    }
    
     
 

    public StringProperty usernameProperty()
    {
        return username;
    }
    public StringProperty passwordProperty()
    {
        return password;
    }
}