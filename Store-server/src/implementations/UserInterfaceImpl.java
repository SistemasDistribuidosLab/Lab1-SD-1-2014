/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementations;

import entities.Company;
import entities.Role;
import entities.User;
import interfaces.UserInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontrollers.UserJpaController;

/**
 *
 * @author sylar
 */
public class UserInterfaceImpl extends UnicastRemoteObject implements UserInterface {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Store-serverPU");
    EntityManager em = emf.createEntityManager();
    UserJpaController userJpaController = new UserJpaController(emf); //create an instance of your jpa controller and pass in the injected emf 
                
    public UserInterfaceImpl() throws RemoteException{
        super();
    }
    
    public void createUser(User user, Company company, Role role){
        user.setCompanyId(company);
        user.setRoleId(role);
        userJpaController.create(user); //persist the entity                   
    }
    
    public void editUser(User user) throws Exception {
        userJpaController.edit(user);
    }
    
    public void destroyUser(User user) throws Exception {
        userJpaController.destroy(user.getUserId());
    }
    
    public List<User> getUserList() {
        return userJpaController.findUserEntities();
    } 

    public User findUser(Integer idUser) {
        return userJpaController.findUser(idUser);
    }
}
