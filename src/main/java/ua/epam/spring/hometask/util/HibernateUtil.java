package ua.epam.spring.hometask.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    // TODO make it tread-safe singleton

    private static final Logger LOG = LogManager.getLogger(HibernateUtil.class.getSimpleName());

    private static final EntityManagerFactory jPAsessionFactory =
            Persistence.createEntityManagerFactory("mysqlUnit");

    public static EntityManager getEntityManager() {
        return jPAsessionFactory.createEntityManager();
    }

    public static void closeSessionFactory() {
        jPAsessionFactory.close();
    }

}
