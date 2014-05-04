/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpacontrollers;

import entities.Address;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.User;
import entities.City;
import entities.Company;
import entities.Item;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.IllegalOrphanException;
import jpacontrollers.exceptions.NonexistentEntityException;

/**
 *
 * @author sylar
 */
public class AddressJpaController implements Serializable {

    public AddressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Address address) {
        if (address.getItemList() == null) {
            address.setItemList(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = address.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                address.setUserId(userId);
            }
            City cityId = address.getCityId();
            if (cityId != null) {
                cityId = em.getReference(cityId.getClass(), cityId.getCityId());
                address.setCityId(cityId);
            }
            Company companyId = address.getCompanyId();
            if (companyId != null) {
                companyId = em.getReference(companyId.getClass(), companyId.getCompanyId());
                address.setCompanyId(companyId);
            }
            List<Item> attachedItemList = new ArrayList<Item>();
            for (Item itemListItemToAttach : address.getItemList()) {
                itemListItemToAttach = em.getReference(itemListItemToAttach.getClass(), itemListItemToAttach.getItemId());
                attachedItemList.add(itemListItemToAttach);
            }
            address.setItemList(attachedItemList);
            em.persist(address);
            if (userId != null) {
                userId.getAddressList().add(address);
                userId = em.merge(userId);
            }
            if (cityId != null) {
                cityId.getAddressList().add(address);
                cityId = em.merge(cityId);
            }
            if (companyId != null) {
                companyId.getAddressList().add(address);
                companyId = em.merge(companyId);
            }
            for (Item itemListItem : address.getItemList()) {
                Address oldAddressIdOfItemListItem = itemListItem.getAddressId();
                itemListItem.setAddressId(address);
                itemListItem = em.merge(itemListItem);
                if (oldAddressIdOfItemListItem != null) {
                    oldAddressIdOfItemListItem.getItemList().remove(itemListItem);
                    oldAddressIdOfItemListItem = em.merge(oldAddressIdOfItemListItem);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Address address) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address persistentAddress = em.find(Address.class, address.getAddressId());
            User userIdOld = persistentAddress.getUserId();
            User userIdNew = address.getUserId();
            City cityIdOld = persistentAddress.getCityId();
            City cityIdNew = address.getCityId();
            Company companyIdOld = persistentAddress.getCompanyId();
            Company companyIdNew = address.getCompanyId();
            List<Item> itemListOld = persistentAddress.getItemList();
            List<Item> itemListNew = address.getItemList();
            List<String> illegalOrphanMessages = null;
            for (Item itemListOldItem : itemListOld) {
                if (!itemListNew.contains(itemListOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemListOldItem + " since its addressId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                address.setUserId(userIdNew);
            }
            if (cityIdNew != null) {
                cityIdNew = em.getReference(cityIdNew.getClass(), cityIdNew.getCityId());
                address.setCityId(cityIdNew);
            }
            if (companyIdNew != null) {
                companyIdNew = em.getReference(companyIdNew.getClass(), companyIdNew.getCompanyId());
                address.setCompanyId(companyIdNew);
            }
            List<Item> attachedItemListNew = new ArrayList<Item>();
            for (Item itemListNewItemToAttach : itemListNew) {
                itemListNewItemToAttach = em.getReference(itemListNewItemToAttach.getClass(), itemListNewItemToAttach.getItemId());
                attachedItemListNew.add(itemListNewItemToAttach);
            }
            itemListNew = attachedItemListNew;
            address.setItemList(itemListNew);
            address = em.merge(address);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getAddressList().remove(address);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getAddressList().add(address);
                userIdNew = em.merge(userIdNew);
            }
            if (cityIdOld != null && !cityIdOld.equals(cityIdNew)) {
                cityIdOld.getAddressList().remove(address);
                cityIdOld = em.merge(cityIdOld);
            }
            if (cityIdNew != null && !cityIdNew.equals(cityIdOld)) {
                cityIdNew.getAddressList().add(address);
                cityIdNew = em.merge(cityIdNew);
            }
            if (companyIdOld != null && !companyIdOld.equals(companyIdNew)) {
                companyIdOld.getAddressList().remove(address);
                companyIdOld = em.merge(companyIdOld);
            }
            if (companyIdNew != null && !companyIdNew.equals(companyIdOld)) {
                companyIdNew.getAddressList().add(address);
                companyIdNew = em.merge(companyIdNew);
            }
            for (Item itemListNewItem : itemListNew) {
                if (!itemListOld.contains(itemListNewItem)) {
                    Address oldAddressIdOfItemListNewItem = itemListNewItem.getAddressId();
                    itemListNewItem.setAddressId(address);
                    itemListNewItem = em.merge(itemListNewItem);
                    if (oldAddressIdOfItemListNewItem != null && !oldAddressIdOfItemListNewItem.equals(address)) {
                        oldAddressIdOfItemListNewItem.getItemList().remove(itemListNewItem);
                        oldAddressIdOfItemListNewItem = em.merge(oldAddressIdOfItemListNewItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = address.getAddressId();
                if (findAddress(id) == null) {
                    throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
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
            Address address;
            try {
                address = em.getReference(Address.class, id);
                address.getAddressId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Item> itemListOrphanCheck = address.getItemList();
            for (Item itemListOrphanCheckItem : itemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the Item " + itemListOrphanCheckItem + " in its itemList field has a non-nullable addressId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userId = address.getUserId();
            if (userId != null) {
                userId.getAddressList().remove(address);
                userId = em.merge(userId);
            }
            City cityId = address.getCityId();
            if (cityId != null) {
                cityId.getAddressList().remove(address);
                cityId = em.merge(cityId);
            }
            Company companyId = address.getCompanyId();
            if (companyId != null) {
                companyId.getAddressList().remove(address);
                companyId = em.merge(companyId);
            }
            em.remove(address);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Address> findAddressEntities() {
        return findAddressEntities(true, -1, -1);
    }

    public List<Address> findAddressEntities(int maxResults, int firstResult) {
        return findAddressEntities(false, maxResults, firstResult);
    }

    private List<Address> findAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Address.class));
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

    public Address findAddress(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Address.class, id);
        } finally {
            em.close();
        }
    }

    public int getAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Address> rt = cq.from(Address.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
