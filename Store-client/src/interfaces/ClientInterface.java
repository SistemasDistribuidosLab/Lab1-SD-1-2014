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
public interface ClientInterface extends Remote{
    public void notify(String message) throws RemoteException;
    public void seDesconecto(String message) throws RemoteException;
    public void recibirMensaje(String emisor, String receptor, String mensaje) throws RemoteException;
}
