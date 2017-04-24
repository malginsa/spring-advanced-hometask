package ua.epam.spring.hometask.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    // TODO make it tread-safe singleton

    private static final Logger LOG = LogManager.getLogger();

    private static EntityManagerFactory entityManagerFactory;
//            = Persistence.createEntityManagerFactory("mysqlUnit");

    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        HibernateUtil.entityManagerFactory = entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
