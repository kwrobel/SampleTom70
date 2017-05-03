/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.MicroMarket;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.MicroMarket_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Customer;
import java.util.List;

/**
 *
 * @author kuw
 */
@Stateless
public class MicroMarketFacade extends AbstractFacade<MicroMarket> {

    @PersistenceContext(unitName = "SampleTom70PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MicroMarketFacade() {
        super(MicroMarket.class);
    }

    public boolean isCustomerListEmpty(MicroMarket entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<MicroMarket> microMarket = cq.from(MicroMarket.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(microMarket, entity), cb.isNotEmpty(microMarket.get(MicroMarket_.customerList)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public List<Customer> findCustomerList(MicroMarket entity) {
        return this.getMergedEntity(entity).getCustomerList();
    }
    
}
