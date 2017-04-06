package archive;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

public class TestDBSessionManager {

    AnnotationConfigApplicationContext ctx;
    AuditoriumService auditoriumService;
    Auditorium fakel;
    Auditorium balkany;

    @Before
    public void init() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        auditoriumService = (AuditoriumService)
                ctx.getBean("auditoriumService");
        fakel = (Auditorium) ctx.getBean("fakel");
        balkany = (Auditorium) ctx.getBean("balkany");
    }

//    @Test
//    public void testConnection() {
//        Session session = HibernateUtil.getSession();
//        int before = session.createQuery("From Auditorium").list().size();
//        session.beginTransaction();
//        session.save(fakel);
//        session.save(balkany);
//        session.getTransaction().commit();
//
//        int after = session.createQuery("From Auditorium").list().size();
//        assertEquals(before + 2, after);
//
//        before = session.createQuery("From Auditorium").list().size();
//        session.beginTransaction();
//        session.delete(balkany);
//        session.getTransaction().commit();
//        after = session.createQuery("From Auditorium").list().size();
//        assertEquals(before - 1, after);
//        session.delete(fakel);
//        session.close();
//    }

//    @Test
//    public void testSessionEntityManager() {
//        Session session = HibernateUtil.getSession();
//        session.beginTransaction();
//        session.save(fakel);
//        session.getTransaction().commit();
//        session.close();
//
//        session = HibernateUtil.getSession();
//        session.beginTransaction();
//        session.delete(fakel);
//        session.getTransaction().commit();
//        session.close();
//
//        session = HibernateUtil.getSession();
//        EntityManager entityManager = session.getEntityManagerFactory().createEntityManager();
//
//        List<Auditorium> resultList = entityManager.createQuery(
//                "select p from Auditorium p where numberOfSeats > 90", Auditorium.class)
//                .getResultList();
//        for (Auditorium auditorium : resultList) {
//            System.out.println("next auditorium: " + auditorium);
//        }
//        session.close();
//    }

    @After
    public void close() {
        ctx.close();
    }
}
