/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpacontrollers;

import entities.Catalog;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Company;
import entities.Category;
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
public class CatalogJpaController implements Serializable {

    public CatalogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Catalog catalog) throws PreexistingEntityException, Exception {
        if (catalog.getCategoryList() == null) {
            catalog.setCategoryList(new ArrayList<Category>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Company companyId = catalog.getCompanyId();
            if (companyId != null) {
                companyId = em.getReference(companyId.getClass(), companyId.getCompanyId());
                catalog.setCompanyId(companyId);
            }
            List<Category> attachedCategoryList = new ArrayList<Category>();
            for (Category categoryListCategoryToAttach : catalog.getCategoryList()) {
                categoryListCategoryToAttach = em.getReference(categoryListCategoryToAttach.getClass(), categoryListCategoryToAttach.getCategoryId());
                attachedCategoryList.add(categoryListCategoryToAttach);
            }
            catalog.setCategoryList(attachedCategoryList);
            em.persist(catalog);
            if (companyId != null) {
                companyId.getCatalogList().add(catalog);
                companyId = em.merge(companyId);
            }
            for (Category categoryListCategory : catalog.getCategoryList()) {
                Catalog oldCatalogIdOfCategoryListCategory = categoryListCategory.getCatalogId();
                categoryListCategory.setCatalogId(catalog);
                categoryListCategory = em.merge(categoryListCategory);
                if (oldCatalogIdOfCategoryListCategory != null) {
                    oldCatalogIdOfCategoryListCategory.getCategoryList().remove(categoryListCategory);
                    oldCatalogIdOfCategoryListCategory = em.merge(oldCatalogIdOfCategoryListCategory);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCatalog(catalog.getCatalogId()) != null) {
                throw new PreexistingEntityException("Catalog " + catalog + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Catalog catalog) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalog persistentCatalog = em.find(Catalog.class, catalog.getCatalogId());
            Company companyIdOld = persistentCatalog.getCompanyId();
            Company companyIdNew = catalog.getCompanyId();
            List<Category> categoryListOld = persistentCatalog.getCategoryList();
            List<Category> categoryListNew = catalog.getCategoryList();
            List<String> illegalOrphanMessages = null;
            for (Category categoryListOldCategory : categoryListOld) {
                if (!categoryListNew.contains(categoryListOldCategory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Category " + categoryListOldCategory + " since its catalogId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (companyIdNew != null) {
                companyIdNew = em.getReference(companyIdNew.getClass(), companyIdNew.getCompanyId());
                catalog.setCompanyId(companyIdNew);
            }
            List<Category> attachedCategoryListNew = new ArrayList<Category>();
            for (Category categoryListNewCategoryToAttach : categoryListNew) {
                categoryListNewCategoryToAttach = em.getReference(categoryListNewCategoryToAttach.getClass(), categoryListNewCategoryToAttach.getCategoryId());
                attachedCategoryListNew.add(categoryListNewCategoryToAttach);
            }
            categoryListNew = attachedCategoryListNew;
            catalog.setCategoryList(categoryListNew);
            catalog = em.merge(catalog);
            if (companyIdOld != null && !companyIdOld.equals(companyIdNew)) {
                companyIdOld.getCatalogList().remove(catalog);
                companyIdOld = em.merge(companyIdOld);
            }
            if (companyIdNew != null && !companyIdNew.equals(companyIdOld)) {
                companyIdNew.getCatalogList().add(catalog);
                companyIdNew = em.merge(companyIdNew);
            }
            for (Category categoryListNewCategory : categoryListNew) {
                if (!categoryListOld.contains(categoryListNewCategory)) {
                    Catalog oldCatalogIdOfCategoryListNewCategory = categoryListNewCategory.getCatalogId();
                    categoryListNewCategory.setCatalogId(catalog);
                    categoryListNewCategory = em.merge(categoryListNewCategory);
                    if (oldCatalogIdOfCategoryListNewCategory != null && !oldCatalogIdOfCategoryListNewCategory.equals(catalog)) {
                        oldCatalogIdOfCategoryListNewCategory.getCategoryList().remove(categoryListNewCategory);
                        oldCatalogIdOfCategoryListNewCategory = em.merge(oldCatalogIdOfCategoryListNewCategory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catalog.getCatalogId();
                if (findCatalog(id) == null) {
                    throw new NonexistentEntityException("The catalog with id " + id + " no longer exists.");
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
            Catalog catalog;
            try {
                catalog = em.getReference(Catalog.class, id);
                catalog.getCatalogId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catalog with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Category> categoryListOrphanCheck = catalog.getCategoryList();
            for (Category categoryListOrphanCheckCategory : categoryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Catalog (" + catalog + ") cannot be destroyed since the Category " + categoryListOrphanCheckCategory + " in its categoryList field has a non-nullable catalogId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Company companyId = catalog.getCompanyId();
            if (companyId != null) {
                companyId.getCatalogList().remove(catalog);
                companyId = em.merge(companyId);
            }
            em.remove(catalog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Catalog> findCatalogEntities() {
        return findCatalogEntities(true, -1, -1);
    }

    public List<Catalog> findCatalogEntities(int maxResults, int firstResult) {
        return findCatalogEntities(false, maxResults, firstResult);
    }

    private List<Catalog> findCatalogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Catalog.class));
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

    public Catalog findCatalog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Catalog.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatalogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Catalog> rt = cq.from(Catalog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
