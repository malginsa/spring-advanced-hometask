package ua.epam.spring.hometask.dao.persistent;

import org.apache.log4j.Logger;
import ua.epam.spring.hometask.HibernateUtil;
import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServicePersistentDao implements UserServiceDao {

    private static final Logger LOG =
            Logger.getLogger(UserServicePersistentDao.class);

    @Override
    public User getUserByEmail(String email) {
        EntityManager manager = HibernateUtil.getEntityManager();
        List<User> resultList = manager.createQuery(
                "select u from User u " +
                        "where u.email = :email",
                User.class)
                .setParameter("email", email)
                .getResultList();
        manager.close();
        if (resultList.size() == 0) {
            return null;
        }
        if (resultList.size() > 1) {
            LOG.warn("Multiple users with email = "
                    + email + " found in DB ");
        }
        return resultList.get(0);
    }

    @Override
    public User save(User user) {
        User managed = null;
        EntityManager manager = HibernateUtil.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            managed = manager.merge(user); // user will be persisted if it's in a transient state
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
    public void remove(User user) {
        // TODO ? rafactor it, look AuditoriumServicePersistentDao.remove() as template
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.merge(user));
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public User getById(Long id) {
        EntityManager manager = HibernateUtil.getEntityManager();
        User user = manager.find(User.class, id);
        manager.close();
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        EntityManager manager = HibernateUtil.getEntityManager();
        List<User> resultList = manager.createQuery(
                "from User", User.class).getResultList();
        manager.close();
        return resultList;
    }

    @Override
    public Long getUsersCount() {
        EntityManager manager = HibernateUtil.getEntityManager();
        Long size = (Long) manager.createQuery("select count(*) from User")
                .getSingleResult();
        manager.close();
        return size;
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        EntityManager manager = HibernateUtil.getEntityManager();
        Set<Ticket> tickets = manager.createQuery(
                "select t from Ticket t "
                        + "join t.event e "
                        + "join e.airDates ads "
                        + "where :dateTime member of ads"
                , Ticket.class)
                .setParameter("dateTime", dateTime)
                .getResultList()
                .stream()
                .collect(Collectors.toSet());
        manager.close();
        return tickets;
    }
}
