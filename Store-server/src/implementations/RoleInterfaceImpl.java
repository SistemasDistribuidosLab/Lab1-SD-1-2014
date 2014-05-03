/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package implementations;

import entities.Role;
import interfaces.RoleInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontrollers.RoleJpaController;

/**
 *
 * @author sylar
 */
public class RoleInterfaceImpl extends UnicastRemoteObject implements RoleInterface{
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Store-serverPU");
    EntityManager em = emf.createEntityManager();
    RoleJpaController roleJpaController = new RoleJpaController(emf); //create an instance of your jpa controller and pass in the injected emf 
    
    public RoleInterfaceImpl() throws RemoteException{
        super();
    }
    
    public void createRole(String roleName, String roleDescription) {
        
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDescription(roleDescription);
        
        em.getTransaction().begin();
        List<Role> listRoles = roleJpaController.findRoleEntities();
        Integer idLastRole = listRoles.get(listRoles.size()-1).getRoleId();
        role.setRoleId(idLastRole+1);        
        try {            
            roleJpaController.create(role); //persist the entity                   
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        em.getTransaction().commit();
        
    }
    
    public void editRole(Role role) throws Exception {
        roleJpaController.edit(role);
    }
    
    public void destroyRole(Role role) throws Exception {
        roleJpaController.destroy(role.getRoleId());
    }
    
    public List<Role> getRoleList() {
        return roleJpaController.findRoleEntities();
    } 

}
