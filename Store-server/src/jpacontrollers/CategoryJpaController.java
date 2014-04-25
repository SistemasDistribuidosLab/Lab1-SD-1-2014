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
import entities.Category;
import entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontrollers.exceptions.NonexistentEntityException;
import jpacontrollers.exceptions.PreexistingEntityException;

/**
 *
 * @author sylar
 */
public class CategoryJpaController implements Serializable {

    public CategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Category category) throws PreexistingEntityException, Exception {
        if (category.getProductList() == null) {
            category.setProductList(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalog catalogId = category.getCatalogId();
            if (catalogId != null) {
                catalogId = em.getReference(catalogId.getClass(), catalogId.getCatalogId());
                category.setCatalogId(catalogId);
            }
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : category.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getProductId());
                attachedProductList.add(productListProductToAttach);
            }
            category.setProductList(attachedProductList);
            em.persist(category);
            if (catalogId != null) {
                catalogId.getCategoryList().add(category);
                catalogId = em.merge(catalogId);
            }
            for (Product productListProduct : category.getProductList()) {
                Category oldCategoryIdOfProductListProduct = productListProduct.getCategoryId();
                productListProduct.setCategoryId(category);
                productListProduct = em.merge(productListProduct);
                if (oldCategoryIdOfProductListProduct != null) {
                    oldCategoryIdOfProductListProduct.getProductList().remove(productListProduct);
                    oldCategoryIdOfProductListProduct = em.merge(oldCategoryIdOfProductListProduct);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategory(category.getCategoryId()) != null) {
                throw new PreexistingEntityException("Category " + category + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Category category) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category persistentCategory = em.find(Category.class, category.getCategoryId());
            Catalog catalogIdOld = persistentCategory.getCatalogId();
            Catalog catalogIdNew = category.getCatalogId();
            List<Product> productListOld = persistentCategory.getProductList();
            List<Product> productListNew = category.getProductList();
            if (catalogIdNew != null) {
                catalogIdNew = em.getReference(catalogIdNew.getClass(), catalogIdNew.getCatalogId());
                category.setCatalogId(catalogIdNew);
            }
            List<Product> attachedProductListNew = new ArrayList<Product>();
            for (Product productListNewProductToAttach : productListNew) {
                productListNewProductToAttach = em.getReference(productListNewProductToAttach.getClass(), productListNewProductToAttach.getProductId());
                attachedProductListNew.add(productListNewProductToAttach);
            }
            productListNew = attachedProductListNew;
            category.setProductList(productListNew);
            category = em.merge(category);
            if (catalogIdOld != null && !catalogIdOld.equals(catalogIdNew)) {
                catalogIdOld.getCategoryList().remove(category);
                catalogIdOld = em.merge(catalogIdOld);
            }
            if (catalogIdNew != null && !catalogIdNew.equals(catalogIdOld)) {
                catalogIdNew.getCategoryList().add(category);
                catalogIdNew = em.merge(catalogIdNew);
            }
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    productListOldProduct.setCategoryId(null);
                    productListOldProduct = em.merge(productListOldProduct);
                }
            }
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Category oldCategoryIdOfProductListNewProduct = productListNewProduct.getCategoryId();
                    productListNewProduct.setCategoryId(category);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldCategoryIdOfProductListNewProduct != null && !oldCategoryIdOfProductListNewProduct.equals(category)) {
                        oldCategoryIdOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldCategoryIdOfProductListNewProduct = em.merge(oldCategoryIdOfProductListNewProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = category.getCategoryId();
                if (findCategory(id) == null) {
                    throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category category;
            try {
                category = em.getReference(Category.class, id);
                category.getCategoryId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
            }
            Catalog catalogId = category.getCatalogId();
            if (catalogId != null) {
                catalogId.getCategoryList().remove(category);
                catalogId = em.merge(catalogId);
            }
            List<Product> productList = category.getProductList();
            for (Product productListProduct : productList) {
                productListProduct.setCategoryId(null);
                productListProduct = em.merge(productListProduct);
            }
            em.remove(category);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Category> findCategoryEntities() {
        return findCategoryEntities(true, -1, -1);
    }

    public List<Category> findCategoryEntities(int maxResults, int firstResult) {
        return findCategoryEntities(false, maxResults, firstResult);
    }

    private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Category.class));
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

    public Category findCategory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Category> rt = cq.from(Category.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
