/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementations;

import entities.User;
import interfaces.UserInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpacontrollers.UserJpaController;

/**
 *
 * @author sylar
 */
public class ImplUserInterface extends UnicastRemoteObject {

    UserJpaController userJpaController;
            
    public ImplUserInterface() throws RemoteException{
        super();
    }
    public void createUser(String role, String companyName, String userEmail, ArrayList<String> Address){
        User user = new User();
        //Asignar atributos resividos al objeto "user"
        try {
            userJpaController.create(user);
        } catch (Exception ex) {
            Logger.getLogger(ImplUserInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
