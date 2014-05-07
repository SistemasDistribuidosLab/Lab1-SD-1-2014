/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import entities.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author sylar
 */
public interface ServerInterface extends Remote{
    public boolean sessionBegin(String name, String pass) throws RemoteException;
    public void clientRegistry(ClientInterface client, String name) throws RemoteException;
    public void clientUnregistry(ClientInterface client, String name) throws RemoteException;
    
    /*  Métodos correspondientes al CRUD de ROLE*/
    public void createRole(Role role) throws RemoteException;
    public void editRole(Role role) throws RemoteException, Exception;
    public void destroyRole(Role role) throws RemoteException, Exception;
    public List<Role> getRoleList() throws RemoteException;
    public Role findRole(Integer idRole) throws RemoteException;
    
    /*  Métodos correspondientes al CRUD de USER*/
    public void createUser(User user, Role role) throws RemoteException;
    public void createUser(User user, Company company, Role role) throws RemoteException;    
    public void editUser(User user) throws RemoteException, Exception;
    public void destroyUser(User user) throws RemoteException, Exception;
    public List<User> getUserList() throws RemoteException;
    public User findUser(Integer idUser) throws RemoteException;
}
