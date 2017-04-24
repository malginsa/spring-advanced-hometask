package ua.epam.spring.hometask.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    // TODO make it tread-safe singleton

    private static final Logger LOG = LogManager.getLogger();

    public static EntityManagerFactory entityManagerFactory;


//    private static final EntityManagerFactory entityManagerFactory =
//            Persistence.createEntityManagerFactory("mysqlUnit");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeSessionFactory() {
        entityManagerFactory.close();
    }

    public HibernateUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        HibernateUtil.entityManagerFactory = entityManagerFactory;
    }
}
