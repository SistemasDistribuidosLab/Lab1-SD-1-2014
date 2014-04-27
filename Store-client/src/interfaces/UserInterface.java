/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author sylar
 */
public interface UserInterface extends Remote{
    public void create(User user) throws Exception, RemoteException;
    public void edit(User user) throws Exception, RemoteException;
    public void destroy(Integer id) throws Exception, RemoteException;
    public List<User> findUserEntities() throws RemoteException;
}
