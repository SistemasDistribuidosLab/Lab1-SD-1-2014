/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementations;

import interfaces.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import views.Dashboard;

/**
 *
 * @author sylar
 */
public class InterfaceClientImpl extends UnicastRemoteObject implements ClientInterface {
    Dashboard MyDashboard;
    
    public InterfaceClientImpl(Dashboard vie) throws RemoteException{
        super();
        this.MyDashboard = vie;
    }
    @Override
    public void notify(String message) throws RemoteException {
        MyDashboard.warning(message);
        System.out.println("se conecto uno nuevo: "+message);
        MyDashboard.AgregarConectado(message);
    }
    public void AbreElChat(String emisor) throws RemoteException{
        //MySecondView.AbrirChat(emisor, "");
    }
    @Override
    public void recibirMensaje(String emisor, String receptor, String mensaje) throws RemoteException{
        System.out.println("soy " + receptor + " y Recibi un mensaje de " + emisor + " ( "+mensaje+" )");
        try {
            MyDashboard.AbrirChat(receptor, emisor, mensaje);
        } catch (BadLocationException ex) {
            Logger.getLogger(InterfaceClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void seDesconecto(String message) throws RemoteException{
        System.out.println("se desconecto: "+message);
        try {
            MyDashboard.seDesconecto(message);
        } catch (BadLocationException ex) {
            Logger.getLogger(InterfaceClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
