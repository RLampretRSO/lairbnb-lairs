package si.fri.rso.rlamp.lairbnb.lairs.services;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rlamp.lairbnb.lairs.models.Lair;
import si.fri.rso.rlamp.lairbnb.lairs.models.Reservation;
import si.fri.rso.rlamp.lairbnb.lairs.models.User;
import si.fri.rso.rlamp.lairbnb.lairs.services.config.LairConfig;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class LairService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private LairConfig lairConfig;

    @Inject
    @DiscoverService(value = "lairbnb-users", version = "*", environment = "dev")
    private Optional<String> usersBaseUrl;

    @Inject
    @DiscoverService(value = "lairbnb-reservations", version = "*", environment = "dev")
    private Optional<String> reservationsBaseUrl;

    private Client httpClient = ClientBuilder.newClient();;

    public List<Lair> getAllLairs() {
        List<Lair> lairs = em
                .createNamedQuery("Lair.findAll", Lair.class)
                .getResultList();

        return lairs;
    }

    public List<Lair> getLairs(QueryParameters query) {
        List<Lair> lairs = JPAUtils.queryEntities(em, Lair.class, query);
        return lairs;
    }

    public Long getLairCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Lair.class, query);
        return count;
    }

    public Lair getLair(Integer lairId) {
        Lair lair = em.find(Lair.class, lairId);

        if (lair != null) {
            lair.setOwner(this.getUser(lair.getOwnerUserId()));
            lair.setReservations(this.getReservations(lair.getId()));
        }

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

    public User getUser(Integer userId) {
        // Calling with filter rather than users/{id} since this call
        // will make additional calls to other service
        if (lairConfig.isUserServiceEnabled() && usersBaseUrl.isPresent()) {
            List<User> result = httpClient.target(usersBaseUrl.get() +
                    "/v1/users?filter=id:EQ:" + userId.toString())
                    .request().get(new GenericType<List<User>>() {
                    });

            if (!result.isEmpty()) {
                return result.get(0);
            }
        }
        return null;
    }

    public List<Reservation> getReservations(Integer lairId) {
        // Calling with filter rather than lairs/{id} since this call
        // will make additional calls to other service

        if (lairConfig.isReservationServiceEnabled() && reservationsBaseUrl.isPresent()) {
            return httpClient.target(reservationsBaseUrl.get() +
                    "/v1/reservations?filter=lairId:EQ:" + lairId.toString())
                    .request().get(new GenericType<List<Reservation>>() {});
        }
        return new ArrayList<Reservation>();
    }
}
