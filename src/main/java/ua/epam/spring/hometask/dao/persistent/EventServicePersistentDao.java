package ua.epam.spring.hometask.dao.persistent;

import org.apache.log4j.Logger;
import ua.epam.spring.hometask.util.HibernateUtil;
import ua.epam.spring.hometask.dao.EventServiceDao;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EventServicePersistentDao implements EventServiceDao {

    private static final Logger LOG =
            Logger.getLogger(EventServicePersistentDao.class);

    @Override
    public Event save(Event event) {
        Event managed = null;
        EntityManager manager = HibernateUtil.getEntityManager();
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
        // TODO ? rafactor it, look AuditoriumServicePersistentDao.remove() as template
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.merge(event));
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public Event getById(Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Event event = entityManager.find(Event.class, id);
        entityManager.close();
        return event;
    }

    @Override
    public List<Event> getAll() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        List<Event> resultList = entityManager.createQuery(
                "from Event", Event.class).getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public Optional<Event> getByName(String name) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
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

//    TODO replace method above with this. Test it
//    @Override
//    public Optional<Event> getByName(String name) {
//        EntityManager entityManager = HibernateUtil.getEntityManager();
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
        EntityManager manager = HibernateUtil.getEntityManager();
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
