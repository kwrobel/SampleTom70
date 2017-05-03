/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Warehouse;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.Warehouse_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Product;
import java.util.List;

/**
 *
 * @author kuw
 */
@Stateless
public class WarehouseFacade extends AbstractFacade<Warehouse> {

    @PersistenceContext(unitName = "SampleTom70PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WarehouseFacade() {
        super(Warehouse.class);
    }

    public boolean isProductListEmpty(Warehouse entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Warehouse> warehouse = cq.from(Warehouse.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(warehouse, entity), cb.isNotEmpty(warehouse.get(Warehouse_.productList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Product> findProductList(Warehouse entity) {
        return this.getMergedEntity(entity).getProductList();
    }
    
}
