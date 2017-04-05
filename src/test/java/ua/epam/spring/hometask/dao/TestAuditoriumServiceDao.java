package ua.epam.spring.hometask.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestAuditoriumServiceDao {

    private static AnnotationConfigApplicationContext ctx;
    private static AuditoriumServiceDao dao;
    private static Auditorium fakel;
    private static Auditorium balkany;
    private static Auditorium zvezda;

    @BeforeClass
    public static void init() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class
                , AppConfigForTesting.class
        );
        ctx.scan("ua.epam.spring.hometask");
        dao = (AuditoriumServiceDao) ctx.getBean("auditoriumServiceDao");
        fakel = (Auditorium) ctx.getBean("fakel");
        balkany = (Auditorium) ctx.getBean("balkany");
        zvezda = (Auditorium) ctx.getBean("zvezda");
    }

    @AfterClass
    public static void close() {
        ctx.close();
    }

    @Test
    public void testGetByName() {
        fakel = dao.save(fakel);
        assertEquals(
                dao.getByName("Fakel").get(),
                (Auditorium) ctx.getBean("fakel"));
        dao.remove(fakel);
    }

    @Test
    public void testGetAll() {
        if (dao.getByName("Zvezda").isPresent()) {
            dao.remove(zvezda);
        }
        int before = dao.getAll().size();
        zvezda = dao.save(zvezda);
        int after = dao.getAll().size();
        dao.remove(zvezda);
        assertEquals(before + 1, after);
    }

    @Test
    public void testRemove() {
        balkany = dao.save(balkany);
        dao.remove(balkany);
        assertFalse(dao.getByName(balkany.getName()).isPresent());
    }
}
