package ua.epam.spring.hometask.dao.persistent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.util.HibernateUtil;
import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component("userServiceDao")
public class UserServicePersistentDao implements UserServiceDao {

    private static final Logger LOG = LogManager.getLogger();

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserServicePersistentDao(@Qualifier("predefined_users") List<User> users,
                                    @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        for (User user : users) {
            LOG.info("supposed to add user: "+ user);
            // TODO why it isn't correct?
//            Optional<User> equivalent = getEquivalent(user);
//            if (!equivalent.isPresent()) {
//            userByEmail - it's a development spike
            User userByEmail = getUserByEmail(user.getEmail());
            if (null == userByEmail) {
                save(user);
            } else {
                LOG.warn("equivalent user is found in Persistence Context: " + user);
            }
        }
    }

    @Override
    public User save(User user) {
        EntityManager manager = entityManagerFactory.createEntityManager();;
        EntityTransaction tx = manager.getTransaction();
        User managed = null;
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

    private Optional<User> getEquivalent(User user) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        List<User> resultList = manager.createQuery(
                "from User u "
                        + "where u.firstName = :firstName"
                        + " and u.lastName = :lastName"
                        + " and u.email = :email"
                        + " and u.bithday = :bithday"
                , User.class)
                .setParameter("firstName", user.getFirstName())
                .setParameter("lastName", user.getLastName())
                .setParameter("email", user.getEmail())
                .setParameter("bithday", user.getBithday())
                .getResultList();
        manager.close();
        if (resultList.size() > 1) {
            LOG.warn("Multiple equivalent users found in DB " + user);
        }
        if (resultList.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(resultList.get(0));
    }

    @Override
    public User getUserByEmail(String email) {
        EntityManager manager = entityManagerFactory.createEntityManager();
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
            LOG.warn("Multiple users with email = " + email + " found in DB ");
        }
        return resultList.get(0);
    }

    @Override
    public void remove(User user) {
        // TODO ? rafactor it, look AuditoriumServicePersistentDao.remove() as template
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.merge(user));
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public User getById(Long id) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        User user = manager.find(User.class, id);
        manager.close();
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        List<User> resultList = manager.createQuery(
                "from User", User.class).getResultList();
        manager.close();
        return resultList;
    }

    @Override
    public Long getUsersCount() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        Long size = (Long) manager.createQuery("select count(*) from User")
                .getSingleResult();
        manager.close();
        return size;
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        EntityManager manager = entityManagerFactory.createEntityManager();
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

    @Override
    public Collection<User> getUsersByName(String firstName) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        List<User> resultList = manager.createQuery(
                "select u from User u " +
                        "where u.firstName = :firstName",
                User.class)
                .setParameter("firstName", firstName)
                .getResultList();
        manager.close();
        return resultList;
    }
}
