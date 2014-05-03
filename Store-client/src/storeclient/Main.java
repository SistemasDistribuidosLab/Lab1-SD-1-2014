/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storeclient;

import entities.User;
import interfaces.UserInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import views.MainView;

/**
 *
 * @author sylar
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
        private static UserInterface userLocal;
        
    public static void main(String[] args) throws Exception {
        User user = new User("mosheman", "sylarim@gmail.com", "asd123");
        user.setUserName("mosheman");
        user.setUserEmail("sylarim@gmail.com");
        user.setUserPassword("asd123");
        
        userLocal.create(user);
        
        new MainView().setVisible(true);
    }
    
}
