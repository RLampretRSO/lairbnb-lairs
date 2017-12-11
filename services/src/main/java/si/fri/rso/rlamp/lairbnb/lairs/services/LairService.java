package si.fri.rso.rlamp.lairbnb.lairs.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rlamp.lairbnb.lairs.models.Lair;
import si.fri.rso.rlamp.lairbnb.lairs.models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequestScoped
public class LairService {

    @PersistenceContext
    private EntityManager em;

    public List<Lair> getAllLairs() {
        List<Lair> customers = em
                .createNamedQuery("Lair.findAll", Lair.class)
                .getResultList();

        return customers;
    }

    public List<Lair> getLairs(QueryParameters query) {
        List<Lair> customers = JPAUtils.queryEntities(em, Lair.class, query);
        return customers;
    }

    public Long getLairCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Lair.class, query);
        return count;
    }

    public Lair getLair(Integer lairId) {
        Lair lair = em.find(Lair.class, lairId);

//        if (lair != null) {
//            lair.setOwner(new User()); // TODO
//        }

        return lair;
    }

    @Transactional
    public Lair createLair(Lair lair) {
        if (lair == null) return null;
        em.persist(lair);

        return lair;
    }

    @Transactional
    public Lair putLair(Integer lairId, Lair lair) {
        if (lair == null) return null;

        Lair u = em.find(Lair.class, lairId);
        if (u == null) return null;

        lair.setId(u.getId());
        lair = em.merge(lair);

        return lair;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteLair(Integer lairId) {
        Lair lair = em.find(Lair.class, lairId);
        if (lair == null) return false;

        em.remove(lair);

        return true;
    }

}
