/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import entities.Role;
import entities.User;
import java.rmi.*;
import java.util.List;

/**
 *
 * @author sylar
 */
public interface RoleInterface extends Remote{
    public void createRole(String roleName, String roleDescription) throws RemoteException;
    public void editRole(Role role) throws RemoteException, Exception;
    public void destroyRole(Role user) throws RemoteException, Exception;
    public List<Role> getRoleList() throws RemoteException;    
}
