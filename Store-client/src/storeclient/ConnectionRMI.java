/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storeclient;

import implementations.InterfaceClientImpl;
import interfaces.ClientInterface;
import interfaces.ServerInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author sylar
 */
public class ConnectionRMI {
    private static Registry registry;    
    private static ServerInterface server;
    private static ClientInterface client;

    public boolean beginRegistry() throws RemoteException{
        try{
            //Se inicia RMIREGISTRY para el registro de objetos
            java.security.AllPermission a = new java.security.AllPermission();
            System.setProperty("java.security.policy", "rmi.policy");
            //startRegistry(direccion del registry,puerto de comunicación);
            startRegistry("127.0.0.1",1099);
            //Vamos al Registry y miramos el Objeto "Implementacion" para poder usarlo.
            server = (ServerInterface)registry.lookup("Implementation");
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private static void startRegistry(String host, int port) throws RemoteException{
        try{
            registry = LocateRegistry.getRegistry(host, port);
            registry.list();
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }
    //Con esto no es necesario hacer un lookup al objeto remoto cada vez que deseemos usarlo
    //basta con llamar a la instancia de la interfaz que fue llamada la primera vez.
    public ServerInterface getServer(){
        return server;
    }
    public void clientRegistry(String name) throws RemoteException{
        client = (ClientInterface) new InterfaceClientImpl();
        server.clientRegistry(client, name);
    }
}
