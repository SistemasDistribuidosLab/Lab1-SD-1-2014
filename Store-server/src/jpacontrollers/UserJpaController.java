/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpacontrollers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Role;
import entities.Company;
import entities.Address;
import java.util.ArrayList;
import java.util.List;
import entities.Client;
import entities.Log;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.IllegalOrphanException;
import jpacontrollers.exceptions.NonexistentEntityException;

/**
 *
 * @author sylar
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getAddressList() == null) {
            user.setAddressList(new ArrayList<Address>());
        }
        if (user.getClientList() == null) {
            user.setClientList(new ArrayList<Client>());
        }
        if (user.getLogList() == null) {
            user.setLogList(new ArrayList<Log>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Role roleId = user.getRoleId();
            if (roleId != null) {
                roleId = em.getReference(roleId.getClass(), roleId.getRoleId());
                user.setRoleId(roleId);
            }
            Company companyId = user.getCompanyId();
            if (companyId != null) {
                companyId = em.getReference(companyId.getClass(), companyId.getCompanyId());
                user.setCompanyId(companyId);
            }
            List<Address> attachedAddressList = new ArrayList<Address>();
            for (Address addressListAddressToAttach : user.getAddressList()) {
                addressListAddressToAttach = em.getReference(addressListAddressToAttach.getClass(), addressListAddressToAttach.getAddressId());
                attachedAddressList.add(addressListAddressToAttach);
            }
            user.setAddressList(attachedAddressList);
            List<Client> attachedClientList = new ArrayList<Client>();
            for (Client clientListClientToAttach : user.getClientList()) {
                clientListClientToAttach = em.getReference(clientListClientToAttach.getClass(), clientListClientToAttach.getClientId());
                attachedClientList.add(clientListClientToAttach);
            }
            user.setClientList(attachedClientList);
            List<Log> attachedLogList = new ArrayList<Log>();
            for (Log logListLogToAttach : user.getLogList()) {
                logListLogToAttach = em.getReference(logListLogToAttach.getClass(), logListLogToAttach.getIdLog());
                attachedLogList.add(logListLogToAttach);
            }
            user.setLogList(attachedLogList);
            em.persist(user);
            if (roleId != null) {
                roleId.getUserList().add(user);
                roleId = em.merge(roleId);
            }
            if (companyId != null) {
                companyId.getUserList().add(user);
                companyId = em.merge(companyId);
            }
            for (Address addressListAddress : user.getAddressList()) {
                User oldUserIdOfAddressListAddress = addressListAddress.getUserId();
                addressListAddress.setUserId(user);
                addressListAddress = em.merge(addressListAddress);
                if (oldUserIdOfAddressListAddress != null) {
                    oldUserIdOfAddressListAddress.getAddressList().remove(addressListAddress);
                    oldUserIdOfAddressListAddress = em.merge(oldUserIdOfAddressListAddress);
                }
            }
            for (Client clientListClient : user.getClientList()) {
                User oldUserIdOfClientListClient = clientListClient.getUserId();
                clientListClient.setUserId(user);
                clientListClient = em.merge(clientListClient);
                if (oldUserIdOfClientListClient != null) {
                    oldUserIdOfClientListClient.getClientList().remove(clientListClient);
                    oldUserIdOfClientListClient = em.merge(oldUserIdOfClientListClient);
                }
            }
            for (Log logListLog : user.getLogList()) {
                User oldUserIdOfLogListLog = logListLog.getUserId();
                logListLog.setUserId(user);
                logListLog = em.merge(logListLog);
                if (oldUserIdOfLogListLog != null) {
                    oldUserIdOfLogListLog.getLogList().remove(logListLog);
                    oldUserIdOfLogListLog = em.merge(oldUserIdOfLogListLog);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            Role roleIdOld = persistentUser.getRoleId();
            Role roleIdNew = user.getRoleId();
            Company companyIdOld = persistentUser.getCompanyId();
            Company companyIdNew = user.getCompanyId();
            List<Address> addressListOld = persistentUser.getAddressList();
            List<Address> addressListNew = user.getAddressList();
            List<Client> clientListOld = persistentUser.getClientList();
            List<Client> clientListNew = user.getClientList();
            List<Log> logListOld = persistentUser.getLogList();
            List<Log> logListNew = user.getLogList();
            List<String> illegalOrphanMessages = null;
            for (Client clientListOldClient : clientListOld) {
                if (!clientListNew.contains(clientListOldClient)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Client " + clientListOldClient + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (roleIdNew != null) {
                roleIdNew = em.getReference(roleIdNew.getClass(), roleIdNew.getRoleId());
                user.setRoleId(roleIdNew);
            }
            if (companyIdNew != null) {
                companyIdNew = em.getReference(companyIdNew.getClass(), companyIdNew.getCompanyId());
                user.setCompanyId(companyIdNew);
            }
            List<Address> attachedAddressListNew = new ArrayList<Address>();
            for (Address addressListNewAddressToAttach : addressListNew) {
                addressListNewAddressToAttach = em.getReference(addressListNewAddressToAttach.getClass(), addressListNewAddressToAttach.getAddressId());
                attachedAddressListNew.add(addressListNewAddressToAttach);
            }
            addressListNew = attachedAddressListNew;
            user.setAddressList(addressListNew);
            List<Client> attachedClientListNew = new ArrayList<Client>();
            for (Client clientListNewClientToAttach : clientListNew) {
                clientListNewClientToAttach = em.getReference(clientListNewClientToAttach.getClass(), clientListNewClientToAttach.getClientId());
                attachedClientListNew.add(clientListNewClientToAttach);
            }
            clientListNew = attachedClientListNew;
            user.setClientList(clientListNew);
            List<Log> attachedLogListNew = new ArrayList<Log>();
            for (Log logListNewLogToAttach : logListNew) {
                logListNewLogToAttach = em.getReference(logListNewLogToAttach.getClass(), logListNewLogToAttach.getIdLog());
                attachedLogListNew.add(logListNewLogToAttach);
            }
            logListNew = attachedLogListNew;
            user.setLogList(logListNew);
            user = em.merge(user);
            if (roleIdOld != null && !roleIdOld.equals(roleIdNew)) {
                roleIdOld.getUserList().remove(user);
                roleIdOld = em.merge(roleIdOld);
            }
            if (roleIdNew != null && !roleIdNew.equals(roleIdOld)) {
                roleIdNew.getUserList().add(user);
                roleIdNew = em.merge(roleIdNew);
            }
            if (companyIdOld != null && !companyIdOld.equals(companyIdNew)) {
                companyIdOld.getUserList().remove(user);
                companyIdOld = em.merge(companyIdOld);
            }
            if (companyIdNew != null && !companyIdNew.equals(companyIdOld)) {
                companyIdNew.getUserList().add(user);
                companyIdNew = em.merge(companyIdNew);
            }
            for (Address addressListOldAddress : addressListOld) {
                if (!addressListNew.contains(addressListOldAddress)) {
                    addressListOldAddress.setUserId(null);
                    addressListOldAddress = em.merge(addressListOldAddress);
                }
            }
            for (Address addressListNewAddress : addressListNew) {
                if (!addressListOld.contains(addressListNewAddress)) {
                    User oldUserIdOfAddressListNewAddress = addressListNewAddress.getUserId();
                    addressListNewAddress.setUserId(user);
                    addressListNewAddress = em.merge(addressListNewAddress);
                    if (oldUserIdOfAddressListNewAddress != null && !oldUserIdOfAddressListNewAddress.equals(user)) {
                        oldUserIdOfAddressListNewAddress.getAddressList().remove(addressListNewAddress);
                        oldUserIdOfAddressListNewAddress = em.merge(oldUserIdOfAddressListNewAddress);
                    }
                }
            }
            for (Client clientListNewClient : clientListNew) {
                if (!clientListOld.contains(clientListNewClient)) {
                    User oldUserIdOfClientListNewClient = clientListNewClient.getUserId();
                    clientListNewClient.setUserId(user);
                    clientListNewClient = em.merge(clientListNewClient);
                    if (oldUserIdOfClientListNewClient != null && !oldUserIdOfClientListNewClient.equals(user)) {
                        oldUserIdOfClientListNewClient.getClientList().remove(clientListNewClient);
                        oldUserIdOfClientListNewClient = em.merge(oldUserIdOfClientListNewClient);
                    }
                }
            }
            for (Log logListOldLog : logListOld) {
                if (!logListNew.contains(logListOldLog)) {
                    logListOldLog.setUserId(null);
                    logListOldLog = em.merge(logListOldLog);
                }
            }
            for (Log logListNewLog : logListNew) {
                if (!logListOld.contains(logListNewLog)) {
                    User oldUserIdOfLogListNewLog = logListNewLog.getUserId();
                    logListNewLog.setUserId(user);
                    logListNewLog = em.merge(logListNewLog);
                    if (oldUserIdOfLogListNewLog != null && !oldUserIdOfLogListNewLog.equals(user)) {
                        oldUserIdOfLogListNewLog.getLogList().remove(logListNewLog);
                        oldUserIdOfLogListNewLog = em.merge(oldUserIdOfLogListNewLog);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Client> clientListOrphanCheck = user.getClientList();
            for (Client clientListOrphanCheckClient : clientListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Client " + clientListOrphanCheckClient + " in its clientList field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Role roleId = user.getRoleId();
            if (roleId != null) {
                roleId.getUserList().remove(user);
                roleId = em.merge(roleId);
            }
            Company companyId = user.getCompanyId();
            if (companyId != null) {
                companyId.getUserList().remove(user);
                companyId = em.merge(companyId);
            }
            List<Address> addressList = user.getAddressList();
            for (Address addressListAddress : addressList) {
                addressListAddress.setUserId(null);
                addressListAddress = em.merge(addressListAddress);
            }
            List<Log> logList = user.getLogList();
            for (Log logListLog : logList) {
                logListLog.setUserId(null);
                logListLog = em.merge(logListLog);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
