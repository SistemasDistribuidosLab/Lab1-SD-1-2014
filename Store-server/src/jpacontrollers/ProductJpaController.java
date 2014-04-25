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
import entities.Category;
import entities.Item;
import entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.IllegalOrphanException;
import jpacontrollers.exceptions.NonexistentEntityException;
import jpacontrollers.exceptions.PreexistingEntityException;

/**
 *
 * @author sylar
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, Exception {
        if (product.getItemList() == null) {
            product.setItemList(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getCategoryId());
                product.setCategoryId(categoryId);
            }
            List<Item> attachedItemList = new ArrayList<Item>();
            for (Item itemListItemToAttach : product.getItemList()) {
                itemListItemToAttach = em.getReference(itemListItemToAttach.getClass(), itemListItemToAttach.getItemId());
                attachedItemList.add(itemListItemToAttach);
            }
            product.setItemList(attachedItemList);
            em.persist(product);
            if (categoryId != null) {
                categoryId.getProductList().add(product);
                categoryId = em.merge(categoryId);
            }
            for (Item itemListItem : product.getItemList()) {
                Product oldProductIdOfItemListItem = itemListItem.getProductId();
                itemListItem.setProductId(product);
                itemListItem = em.merge(itemListItem);
                if (oldProductIdOfItemListItem != null) {
                    oldProductIdOfItemListItem.getItemList().remove(itemListItem);
                    oldProductIdOfItemListItem = em.merge(oldProductIdOfItemListItem);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProduct(product.getProductId()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProductId());
            Category categoryIdOld = persistentProduct.getCategoryId();
            Category categoryIdNew = product.getCategoryId();
            List<Item> itemListOld = persistentProduct.getItemList();
            List<Item> itemListNew = product.getItemList();
            List<String> illegalOrphanMessages = null;
            for (Item itemListOldItem : itemListOld) {
                if (!itemListNew.contains(itemListOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemListOldItem + " since its productId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getCategoryId());
                product.setCategoryId(categoryIdNew);
            }
            List<Item> attachedItemListNew = new ArrayList<Item>();
            for (Item itemListNewItemToAttach : itemListNew) {
                itemListNewItemToAttach = em.getReference(itemListNewItemToAttach.getClass(), itemListNewItemToAttach.getItemId());
                attachedItemListNew.add(itemListNewItemToAttach);
            }
            itemListNew = attachedItemListNew;
            product.setItemList(itemListNew);
            product = em.merge(product);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getProductList().remove(product);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getProductList().add(product);
                categoryIdNew = em.merge(categoryIdNew);
            }
            for (Item itemListNewItem : itemListNew) {
                if (!itemListOld.contains(itemListNewItem)) {
                    Product oldProductIdOfItemListNewItem = itemListNewItem.getProductId();
                    itemListNewItem.setProductId(product);
                    itemListNewItem = em.merge(itemListNewItem);
                    if (oldProductIdOfItemListNewItem != null && !oldProductIdOfItemListNewItem.equals(product)) {
                        oldProductIdOfItemListNewItem.getItemList().remove(itemListNewItem);
                        oldProductIdOfItemListNewItem = em.merge(oldProductIdOfItemListNewItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = product.getProductId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Item> itemListOrphanCheck = product.getItemList();
            for (Item itemListOrphanCheckItem : itemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Item " + itemListOrphanCheckItem + " in its itemList field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId.getProductList().remove(product);
                categoryId = em.merge(categoryId);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
