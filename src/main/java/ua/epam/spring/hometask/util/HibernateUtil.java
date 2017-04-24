package ua.epam.spring.hometask.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateUtil {

    // TODO make it tread-safe singleton

    private static final Logger LOG = LogManager.getLogger();

    public static EntityManagerFactory entityManagerFactory;

    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        HibernateUtil.entityManagerFactory = entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
