package ua.epam.spring.hometask.dao.persistent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.util.HibernateUtil;
import ua.epam.spring.hometask.dao.EventServiceDao;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component("eventServiceDao")
public class EventServicePersistentDao implements EventServiceDao {

    private static final Logger LOG = LogManager.getLogger();

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public EventServicePersistentDao(@Qualifier("entityManagerFactory")
                                                 EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Event save(Event event) {
        Event managed = null;
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            managed = manager.merge(event); // event will be persisted if it's in a transient state
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            LOG.error(e);
        } finally {
            manager.close();
        }
        return managed;
    }

    @Override
    public void remove(Event event) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        Event retrieved = manager.find(Event.class, event.getId());
        if (manager.contains(retrieved)) {
            manager.remove(retrieved);
        }
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public Event getById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Event event = entityManager.find(Event.class, id);
        entityManager.close();
        return event;
    }

    @Override
    public List<Event> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Event> resultList = entityManager.createQuery(
                "from Event", Event.class).getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public Optional<Event> getByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Event> resultList = entityManager.createQuery(
                    "select e from Event e where e.name = :name", Event.class)
                    .setParameter("name", name)
                    .getResultList();
            if (resultList.size() == 0) {
                return Optional.empty();
            }
            if (resultList.size() > 1) {
                LOG.warn("Multiple events with name = "
                        + name + " found in DB:");
                resultList.stream().forEach(System.out::println);
            }
            return Optional.of(resultList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

//    TODO replace method above with this. And test it
//    @Override
//    public Optional<Event> getByName(String name) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Optional<Event> event = entityManager
//                .unwrap(Session.class)
//                .byNaturalId(Event.class)
//                .using("name", name)
//                .loadOptional();
//        entityManager.close();
//        return event;
//    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        Set<Event> events = manager.createQuery(
                "select e from Event e " +
                        "join e.airDates ad " +
                        "where airDates between :from and :to", Event.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList()
                .stream()
                .collect(Collectors.toSet());
        manager.close();
        return events;
    }
}
