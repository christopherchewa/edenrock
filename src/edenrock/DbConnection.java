/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edenrock;

import edenrock.models.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Chewa
 */
public class DbConnection {
    
    private static User user;
    
    public static void connect()
    {
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:posdb.db3", "", "");
        user = User.findById(1);


    }
    public static void disconnect()
    {
        Base.close();
    }
    
    

}
