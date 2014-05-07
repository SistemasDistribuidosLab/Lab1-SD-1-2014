
package implementations;

import entities.Company;
import entities.Role;
import entities.User;
import interfaces.*;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontrollers.*;

/**
 *
 * @author sylar
 */
public class ServerInterfaceImpl extends UnicastRemoteObject implements ServerInterface{
    private ArrayList clients = new ArrayList();
    /*  Persistencia    */
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Store-serverPU");
    EntityManager em = emf.createEntityManager();
    /*  Controladores   */
    RoleJpaController roleJpaController = new RoleJpaController(emf); //create an instance of your jpa controller and pass in the injected emf 
    UserJpaController userJpaController = new UserJpaController(emf); //create an instance of your jpa controller and pass in the injected emf 
     

    public ServerInterfaceImpl() throws RemoteException{
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
    
    /*  Métodos correspondientes al CRUD de ROLE*/
    public void createRole(Role role) throws RemoteException {
        roleJpaController.create(role); //persist the entity                   
    }
    
    public void editRole(Role role) throws RemoteException, Exception {
        roleJpaController.edit(role);
    }
    
    public void destroyRole(Role role) throws RemoteException, Exception {
        roleJpaController.destroy(role.getRoleId());
    }
    
    public List<Role> getRoleList() throws RemoteException {
        return roleJpaController.findRoleEntities();
    } 
    
    public Role findRole(Integer idRole) throws RemoteException {
        return roleJpaController.findRole(idRole);
    }
    
    /*  Métodos correspondientes al CRUD de USER*/
    public void createUser(User user, Role role) throws RemoteException {
        user.setRoleId(role);
        userJpaController.create(user); //persist the entity                   
    }
    
    public void createUser(User user, Company company, Role role) throws RemoteException {
        user.setCompanyId(company);
        user.setRoleId(role);
        userJpaController.create(user); //persist the entity                   
    }
    
    public void editUser(User user) throws RemoteException, Exception {
        userJpaController.edit(user);
    }
    
    public void destroyUser(User user) throws RemoteException, Exception {
        userJpaController.destroy(user.getUserId());
    }
    
    public List<User> getUserList() throws RemoteException {
        return userJpaController.findUserEntities();
    } 

    public User findUser(Integer idUser) throws RemoteException {
        return userJpaController.findUser(idUser);
    }
}
