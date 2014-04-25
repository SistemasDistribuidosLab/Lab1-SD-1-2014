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
import entities.Catalog;
import java.util.ArrayList;
import java.util.List;
import entities.Address;
import entities.User;
import entities.Client;
import entities.Company;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.IllegalOrphanException;
import jpacontrollers.exceptions.NonexistentEntityException;
import jpacontrollers.exceptions.PreexistingEntityException;

/**
 *
 * @author sylar
 */
public class CompanyJpaController implements Serializable {

    public CompanyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Company company) throws PreexistingEntityException, Exception {
        if (company.getCatalogList() == null) {
            company.setCatalogList(new ArrayList<Catalog>());
        }
        if (company.getAddressList() == null) {
            company.setAddressList(new ArrayList<Address>());
        }
        if (company.getUserList() == null) {
            company.setUserList(new ArrayList<User>());
        }
        if (company.getClientList() == null) {
            company.setClientList(new ArrayList<Client>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Catalog> attachedCatalogList = new ArrayList<Catalog>();
            for (Catalog catalogListCatalogToAttach : company.getCatalogList()) {
                catalogListCatalogToAttach = em.getReference(catalogListCatalogToAttach.getClass(), catalogListCatalogToAttach.getCatalogId());
                attachedCatalogList.add(catalogListCatalogToAttach);
            }
            company.setCatalogList(attachedCatalogList);
            List<Address> attachedAddressList = new ArrayList<Address>();
            for (Address addressListAddressToAttach : company.getAddressList()) {
                addressListAddressToAttach = em.getReference(addressListAddressToAttach.getClass(), addressListAddressToAttach.getAddressId());
                attachedAddressList.add(addressListAddressToAttach);
            }
            company.setAddressList(attachedAddressList);
            List<User> attachedUserList = new ArrayList<User>();
            for (User userListUserToAttach : company.getUserList()) {
                userListUserToAttach = em.getReference(userListUserToAttach.getClass(), userListUserToAttach.getUserId());
                attachedUserList.add(userListUserToAttach);
            }
            company.setUserList(attachedUserList);
            List<Client> attachedClientList = new ArrayList<Client>();
            for (Client clientListClientToAttach : company.getClientList()) {
                clientListClientToAttach = em.getReference(clientListClientToAttach.getClass(), clientListClientToAttach.getClientId());
                attachedClientList.add(clientListClientToAttach);
            }
            company.setClientList(attachedClientList);
            em.persist(company);
            for (Catalog catalogListCatalog : company.getCatalogList()) {
                Company oldCompanyIdOfCatalogListCatalog = catalogListCatalog.getCompanyId();
                catalogListCatalog.setCompanyId(company);
                catalogListCatalog = em.merge(catalogListCatalog);
                if (oldCompanyIdOfCatalogListCatalog != null) {
                    oldCompanyIdOfCatalogListCatalog.getCatalogList().remove(catalogListCatalog);
                    oldCompanyIdOfCatalogListCatalog = em.merge(oldCompanyIdOfCatalogListCatalog);
                }
            }
            for (Address addressListAddress : company.getAddressList()) {
                Company oldCompanyIdOfAddressListAddress = addressListAddress.getCompanyId();
                addressListAddress.setCompanyId(company);
                addressListAddress = em.merge(addressListAddress);
                if (oldCompanyIdOfAddressListAddress != null) {
                    oldCompanyIdOfAddressListAddress.getAddressList().remove(addressListAddress);
                    oldCompanyIdOfAddressListAddress = em.merge(oldCompanyIdOfAddressListAddress);
                }
            }
            for (User userListUser : company.getUserList()) {
                Company oldCompanyIdOfUserListUser = userListUser.getCompanyId();
                userListUser.setCompanyId(company);
                userListUser = em.merge(userListUser);
                if (oldCompanyIdOfUserListUser != null) {
                    oldCompanyIdOfUserListUser.getUserList().remove(userListUser);
                    oldCompanyIdOfUserListUser = em.merge(oldCompanyIdOfUserListUser);
                }
            }
            for (Client clientListClient : company.getClientList()) {
                Company oldCompanyIdOfClientListClient = clientListClient.getCompanyId();
                clientListClient.setCompanyId(company);
                clientListClient = em.merge(clientListClient);
                if (oldCompanyIdOfClientListClient != null) {
                    oldCompanyIdOfClientListClient.getClientList().remove(clientListClient);
                    oldCompanyIdOfClientListClient = em.merge(oldCompanyIdOfClientListClient);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCompany(company.getCompanyId()) != null) {
                throw new PreexistingEntityException("Company " + company + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Company company) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Company persistentCompany = em.find(Company.class, company.getCompanyId());
            List<Catalog> catalogListOld = persistentCompany.getCatalogList();
            List<Catalog> catalogListNew = company.getCatalogList();
            List<Address> addressListOld = persistentCompany.getAddressList();
            List<Address> addressListNew = company.getAddressList();
            List<User> userListOld = persistentCompany.getUserList();
            List<User> userListNew = company.getUserList();
            List<Client> clientListOld = persistentCompany.getClientList();
            List<Client> clientListNew = company.getClientList();
            List<String> illegalOrphanMessages = null;
            for (Client clientListOldClient : clientListOld) {
                if (!clientListNew.contains(clientListOldClient)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Client " + clientListOldClient + " since its companyId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Catalog> attachedCatalogListNew = new ArrayList<Catalog>();
            for (Catalog catalogListNewCatalogToAttach : catalogListNew) {
                catalogListNewCatalogToAttach = em.getReference(catalogListNewCatalogToAttach.getClass(), catalogListNewCatalogToAttach.getCatalogId());
                attachedCatalogListNew.add(catalogListNewCatalogToAttach);
            }
            catalogListNew = attachedCatalogListNew;
            company.setCatalogList(catalogListNew);
            List<Address> attachedAddressListNew = new ArrayList<Address>();
            for (Address addressListNewAddressToAttach : addressListNew) {
                addressListNewAddressToAttach = em.getReference(addressListNewAddressToAttach.getClass(), addressListNewAddressToAttach.getAddressId());
                attachedAddressListNew.add(addressListNewAddressToAttach);
            }
            addressListNew = attachedAddressListNew;
            company.setAddressList(addressListNew);
            List<User> attachedUserListNew = new ArrayList<User>();
            for (User userListNewUserToAttach : userListNew) {
                userListNewUserToAttach = em.getReference(userListNewUserToAttach.getClass(), userListNewUserToAttach.getUserId());
                attachedUserListNew.add(userListNewUserToAttach);
            }
            userListNew = attachedUserListNew;
            company.setUserList(userListNew);
            List<Client> attachedClientListNew = new ArrayList<Client>();
            for (Client clientListNewClientToAttach : clientListNew) {
                clientListNewClientToAttach = em.getReference(clientListNewClientToAttach.getClass(), clientListNewClientToAttach.getClientId());
                attachedClientListNew.add(clientListNewClientToAttach);
            }
            clientListNew = attachedClientListNew;
            company.setClientList(clientListNew);
            company = em.merge(company);
            for (Catalog catalogListOldCatalog : catalogListOld) {
                if (!catalogListNew.contains(catalogListOldCatalog)) {
                    catalogListOldCatalog.setCompanyId(null);
                    catalogListOldCatalog = em.merge(catalogListOldCatalog);
                }
            }
            for (Catalog catalogListNewCatalog : catalogListNew) {
                if (!catalogListOld.contains(catalogListNewCatalog)) {
                    Company oldCompanyIdOfCatalogListNewCatalog = catalogListNewCatalog.getCompanyId();
                    catalogListNewCatalog.setCompanyId(company);
                    catalogListNewCatalog = em.merge(catalogListNewCatalog);
                    if (oldCompanyIdOfCatalogListNewCatalog != null && !oldCompanyIdOfCatalogListNewCatalog.equals(company)) {
                        oldCompanyIdOfCatalogListNewCatalog.getCatalogList().remove(catalogListNewCatalog);
                        oldCompanyIdOfCatalogListNewCatalog = em.merge(oldCompanyIdOfCatalogListNewCatalog);
                    }
                }
            }
            for (Address addressListOldAddress : addressListOld) {
                if (!addressListNew.contains(addressListOldAddress)) {
                    addressListOldAddress.setCompanyId(null);
                    addressListOldAddress = em.merge(addressListOldAddress);
                }
            }
            for (Address addressListNewAddress : addressListNew) {
                if (!addressListOld.contains(addressListNewAddress)) {
                    Company oldCompanyIdOfAddressListNewAddress = addressListNewAddress.getCompanyId();
                    addressListNewAddress.setCompanyId(company);
                    addressListNewAddress = em.merge(addressListNewAddress);
                    if (oldCompanyIdOfAddressListNewAddress != null && !oldCompanyIdOfAddressListNewAddress.equals(company)) {
                        oldCompanyIdOfAddressListNewAddress.getAddressList().remove(addressListNewAddress);
                        oldCompanyIdOfAddressListNewAddress = em.merge(oldCompanyIdOfAddressListNewAddress);
                    }
                }
            }
            for (User userListOldUser : userListOld) {
                if (!userListNew.contains(userListOldUser)) {
                    userListOldUser.setCompanyId(null);
                    userListOldUser = em.merge(userListOldUser);
                }
            }
            for (User userListNewUser : userListNew) {
                if (!userListOld.contains(userListNewUser)) {
                    Company oldCompanyIdOfUserListNewUser = userListNewUser.getCompanyId();
                    userListNewUser.setCompanyId(company);
                    userListNewUser = em.merge(userListNewUser);
                    if (oldCompanyIdOfUserListNewUser != null && !oldCompanyIdOfUserListNewUser.equals(company)) {
                        oldCompanyIdOfUserListNewUser.getUserList().remove(userListNewUser);
                        oldCompanyIdOfUserListNewUser = em.merge(oldCompanyIdOfUserListNewUser);
                    }
                }
            }
            for (Client clientListNewClient : clientListNew) {
                if (!clientListOld.contains(clientListNewClient)) {
                    Company oldCompanyIdOfClientListNewClient = clientListNewClient.getCompanyId();
                    clientListNewClient.setCompanyId(company);
                    clientListNewClient = em.merge(clientListNewClient);
                    if (oldCompanyIdOfClientListNewClient != null && !oldCompanyIdOfClientListNewClient.equals(company)) {
                        oldCompanyIdOfClientListNewClient.getClientList().remove(clientListNewClient);
                        oldCompanyIdOfClientListNewClient = em.merge(oldCompanyIdOfClientListNewClient);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = company.getCompanyId();
                if (findCompany(id) == null) {
                    throw new NonexistentEntityException("The company with id " + id + " no longer exists.");
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
            Company company;
            try {
                company = em.getReference(Company.class, id);
                company.getCompanyId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The company with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Client> clientListOrphanCheck = company.getClientList();
            for (Client clientListOrphanCheckClient : clientListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Company (" + company + ") cannot be destroyed since the Client " + clientListOrphanCheckClient + " in its clientList field has a non-nullable companyId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Catalog> catalogList = company.getCatalogList();
            for (Catalog catalogListCatalog : catalogList) {
                catalogListCatalog.setCompanyId(null);
                catalogListCatalog = em.merge(catalogListCatalog);
            }
            List<Address> addressList = company.getAddressList();
            for (Address addressListAddress : addressList) {
                addressListAddress.setCompanyId(null);
                addressListAddress = em.merge(addressListAddress);
            }
            List<User> userList = company.getUserList();
            for (User userListUser : userList) {
                userListUser.setCompanyId(null);
                userListUser = em.merge(userListUser);
            }
            em.remove(company);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Company> findCompanyEntities() {
        return findCompanyEntities(true, -1, -1);
    }

    public List<Company> findCompanyEntities(int maxResults, int firstResult) {
        return findCompanyEntities(false, maxResults, firstResult);
    }

    private List<Company> findCompanyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Company.class));
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

    public Company findCompany(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Company.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompanyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Company> rt = cq.from(Company.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
