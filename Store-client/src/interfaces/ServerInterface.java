/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author sylar
 */
public interface ServerInterface extends Remote{
    public boolean sessionBegin(String name, String pass) throws RemoteException;
    public void clientRegistry(ClientInterface client, String name) throws RemoteException;
    public void clientUnregistry(ClientInterface client, String name) throws RemoteException;
}
