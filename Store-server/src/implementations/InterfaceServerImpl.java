
package implementations;

import interfaces.*;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;

/**
 *
 * @author sylar
 */
public class InterfaceServerImpl extends UnicastRemoteObject implements ServerInterface{
    private ArrayList clients = new ArrayList();

    public InterfaceServerImpl() throws RemoteException{
        super();
    }
    //Autentificación por implementar de manera descente xD
    public boolean sessionBegin(String name, String pass) throws RemoteException {
        if (name.equals("juan") && pass.equals("juan")){
            return true;
        }
        else if(name.equals("pedro") && pass.equals("pedro")){
            return true;
        }
        else if (name.equals("jose") && pass.equals("jose")){
            return true;
        }
        return false;
    }
    //Este método registra clientes que se conectan
    public synchronized void clientRegistry(ClientInterface client, String name) throws RemoteException{
        if (!(clients.contains(client))) {
            clients.add(client);
            //clientesNombre.addElement(Nombre);
            for (int i=0;i<clients.size();i++){
                ClientInterface nextClient = (ClientInterface)clients.get(i);
                if (!client.toString().equals(nextClient.toString())){
                    //Mando la notificacion de que se conecto otro usuario
                    nextClient.notify(name+" has connected");
                }
            }
        }
    }
    //Este método elimina clientes y notifica la desconexion de alguno.
    public synchronized void clientUnregistry(ClientInterface client, String name) throws RemoteException{
        if (clients.remove(client)) {
            //clientesNombre.removeElement(Nombre);
            for (int i=0;i<clients.size();i++){
                ClientInterface nextClient = (ClientInterface)clients.get(i);
                //Mando la notificacion de que se conecto otro usuario
                nextClient.notify(name+" has disconected.");
            }
        }
        else{
            System.out.print("Cliente no estaba registrado");
        }
    }
}
