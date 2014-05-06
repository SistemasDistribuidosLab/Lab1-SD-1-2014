/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import entities.Role;
import entities.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List; 

/**
 *
 * @author sylar
 */
public interface RoleInterface extends Remote { 
    public void createRole(Role role) throws Exception, RemoteException;
    public void editRole(Role role) throws Exception, RemoteException;
    public void destroyRole(Role role) throws Exception, RemoteException;
    public List<User> getRoleList() throws RemoteException;
    public User findRole(Integer idRole) throws RemoteException;
}
