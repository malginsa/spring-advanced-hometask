package ua.epam.spring.hometask.dao.persistent;

import org.apache.log4j.Logger;
import ua.epam.spring.hometask.util.HibernateUtil;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.CounterType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterPersistentDao implements CounterDao {

    private static final Logger LOG =
            Logger.getLogger(CounterPersistentDao.class);

    private AtomicInteger countValue;
    private CounterType counterType;

    public CounterPersistentDao(CounterType counterType) {
        this.counterType = counterType;
        countValue = new AtomicInteger();
    }

    @Override
    public int getCounter(String name) {
        EntityManager manager = HibernateUtil.getEntityManager();
        List<Counter> resultList = manager.createQuery(
                "from Counter C where C.name = :name " +
                        "and C.counterType = :counterType",
                Counter.class)
                .setParameter("name", name)
                .setParameter("counterType", counterType)
                .getResultList();
        if (resultList.size() == 0) {
            return 0;
        }
        if (resultList.size() > 1) {
            LOG.warn("Not one counter with name " + name
                    + " extracted from DB (counter type = "
                    + counterType + ")");
        }
        manager.close();
        return resultList.get(0).getValue();
    }

    @Override
    public void incCounter(String name) {
        int currentValue = getCounter(name);
        if (0 == currentValue) { // a new counter
            EntityManager manager = HibernateUtil.getEntityManager();
            manager.getTransaction().begin();
            manager.persist(
                    (new Counter())
                            .setName(name)
                            .setCounterType(counterType)
                            .setValue(1));
            manager.getTransaction().commit();
            manager.close();
            return;
        }
        int newValue = currentValue + 1;
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery(
                "update Counter c " +
                        "set c.value = :newValue " +
                        "where c.name = :name " +
                        "and c.counterType = :counterType")
                .setParameter("newValue", newValue)
                .setParameter("name", name)
                .setParameter("counterType", counterType);
        int rowsAffected = query.executeUpdate();
        manager.getTransaction().commit();
        if (1 != rowsAffected) {
            LOG.error(rowsAffected + " affected while trying " +
                    "to increase/insert value of counter " + name);
        }
        manager.close();
    }
}
