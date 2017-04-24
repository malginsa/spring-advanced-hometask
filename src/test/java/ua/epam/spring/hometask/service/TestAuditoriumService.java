package ua.epam.spring.hometask.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestAuditoriumService {

    private static AnnotationConfigApplicationContext ctx;
    private static AuditoriumService service;

    @BeforeClass
    public static void initAuditoriumService() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class,
                AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        service = (AuditoriumService) ctx.getBean("auditoriumService");
    }

    @AfterClass
    public static void close() {
        ctx.close();
    }

    @Test
    public void testGetAll() {
        assertNotEquals(service.getAll().size(), 0);
    }

    @Test
    public void testGetByName() {
        Auditorium planeta = new Auditorium("Planeta-test", 10,
                new HashSet<Long>() {{ add(1L); add(2L); }});
        service.add(planeta);
        assertTrue(service.getByName("Planeta-test").isPresent());
        assertFalse(service.getByName("Ijeyqkcnnhyrn").isPresent());
    }

    @Test
    public void testAdd() {
        Auditorium kosmos = new Auditorium(
                "Kosmos", 10, new HashSet<Long>(){{add(1L); add(2L);}});
        service.add(kosmos);
        assertTrue(service.getByName("Kosmos").isPresent());
        Auditorium kosmosDupe = new Auditorium(
                "Kosmos", 20, new HashSet<Long>(){{add(1L); add(2L);}});
        service.add(kosmosDupe);
        Optional<Auditorium> auditorium = service.getByName("Kosmos");
        assertTrue(auditorium.isPresent());
        assertEquals(auditorium.get().getNumberOfSeats(), 20);
    }
}
