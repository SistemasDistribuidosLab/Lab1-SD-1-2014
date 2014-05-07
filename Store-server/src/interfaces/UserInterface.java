/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import entities.Company;
import entities.Role;
import entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author sylar
 */
public interface UserInterface extends Remote{
    public void createUser(User user, Role role) throws Exception, RemoteException;
    public void createUser(User user, Company company, Role role) throws Exception, RemoteException;
    public void editUser(User user) throws Exception, RemoteException;
    public void destroyUser(User user) throws Exception, RemoteException;
    public List<User> getUserList() throws RemoteException;
    public User findUser(Integer idUser) throws RemoteException;
}
