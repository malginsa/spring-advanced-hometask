package ua.epam.spring.hometask.dao.persistent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.epam.spring.hometask.util.HibernateUtil;
import ua.epam.spring.hometask.dao.AuditoriumServiceDao;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AuditoriumServicePersistentDao implements AuditoriumServiceDao {

    private static final Logger LOG = LogManager.getLogger();

    public AuditoriumServicePersistentDao(List<Auditorium> auditoriums) {
        for (Auditorium auditorium : auditoriums) {
            save(auditorium);
        }
    }

    @Override
    public Auditorium save(Auditorium auditorium) {

        Optional<Auditorium> byName = getByName(auditorium.getName());
        if (byName.isPresent()) {
            // not to save auditorium with the same name, according to equality contract
            return byName.get();
        }

        EntityManager manager = HibernateUtil.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        Auditorium managed = null;
        tx.begin();
        try {
            managed = manager.merge(auditorium); // auditorium will be persisted if it's in a transient state
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
    public Optional<Auditorium> getByName(String name) {
        EntityManager manager = HibernateUtil.getEntityManager();
        List<Auditorium> resultList = manager.createQuery(
                "from Auditorium A where A.name = :name",
                Auditorium.class)
                .setParameter("name", name)
                .getResultList();
        manager.close();
        if (resultList.size() == 0) {
            return Optional.empty();
        }
        if (resultList.size() > 1) {
            LOG.warn("Multiple auditoriums with name = "
                    + name + " found in DB ");
        }
        return Optional.of(resultList.get(0));
    }

    @Override
    public Set<Auditorium> getAll() {
        EntityManager manager = HibernateUtil.getEntityManager();
        TypedQuery<Auditorium> query = manager.createQuery(
                "from Auditorium", Auditorium.class);
        List<Auditorium> list = query.getResultList();
        manager.close();
        return new HashSet<>(list);
    }

    @Override
    public boolean remove(Auditorium auditorium) {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        Auditorium retrieved = manager.find(Auditorium.class, auditorium.getId());
        if (manager.contains(retrieved)) {
            manager.remove(retrieved);
        }
        manager.getTransaction().commit();
        manager.close();
        return true;
    }
}
