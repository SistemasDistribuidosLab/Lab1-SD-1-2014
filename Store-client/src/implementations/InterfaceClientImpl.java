/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementations;

import interfaces.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import views.SecondView;

/**
 *
 * @author sylar
 */
public class InterfaceClientImpl extends UnicastRemoteObject implements ClientInterface {
    public InterfaceClientImpl() throws RemoteException{
        super();
    }
    public void notify(String message) throws RemoteException {
        SecondView.getViewInstance().warning(message);
    }
}
