package ua.epam.spring.hometask;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.JpaConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDbEntityManager {

    private static AnnotationConfigApplicationContext ctx;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void init() {
        ctx = new AnnotationConfigApplicationContext(
                JpaConfig.class,
                AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        entityManagerFactory = (EntityManagerFactory) ctx.getBean("entityManagerFactory");
    }

    @AfterClass
    public static void finish() {
        ctx.close();
    }

//    @Test
    public void testPersistAndObtain() {
        Auditorium breslau = new Auditorium("Breslau", 10,
                new HashSet<Long>() {{ add(1L); add(2L); }});
        EntityManager entityManager = HibernateUtil.getEntityManager();
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int before = entityManager.createQuery("from Auditorium", Auditorium.class)
                .getResultList()
                .size();
        entityManager.persist(breslau);
        int after = entityManager.createQuery("from Auditorium", Auditorium.class)
                .getResultList()
                .size();
        entityManager.getTransaction().commit();
        entityManager.close();
        assertEquals(before + 1, after);
    }

//    @Test
    public void testObtain() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        List<Auditorium> result = entityManager
                .createQuery( "from Auditorium", Auditorium.class )
                .getResultList();
        result.stream().forEach(System.out::println);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

//    @Test
    public void testPersistEvent() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        Event event = new Event();
        event.setName("weekend");
        event.setRating(EventRating.LOW);
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
