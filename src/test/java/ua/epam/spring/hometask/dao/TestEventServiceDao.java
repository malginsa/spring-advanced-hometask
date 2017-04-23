package ua.epam.spring.hometask.dao;

import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class TestEventServiceDao {

    private static AnnotationConfigApplicationContext ctx;
    private static EventServiceDao eventServiceDao;
    private static AuditoriumServiceDao auditoriumServiceDao;
    private static Event jPoint;
    private static Event millenium;
    private static Auditorium fakel;

    @BeforeClass
    public static void before() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class,
                AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        eventServiceDao = (EventServiceDao) ctx.getBean("eventServiceDao");
        auditoriumServiceDao = (AuditoriumServiceDao) ctx.getBean("auditoriumServiceDao");
        jPoint = (Event) ctx.getBean("jPoint");
        millenium = (Event) ctx.getBean("millenium");
    }

    @AfterClass
    public static void after() {
        ctx.close();
    }

    @Test
    public void testSaveRemove() {
        jPoint = eventServiceDao.save(jPoint);
        assertTrue(eventServiceDao.getByName(jPoint.getName()).isPresent());
        eventServiceDao.remove(jPoint);
        assertFalse(eventServiceDao.getByName(jPoint.getName()).isPresent()); // error
    }

    @Test
    public void testGetById() {
        jPoint = eventServiceDao.save(jPoint);
        Event duplicate = eventServiceDao.getById(jPoint.getId());
        assertEquals(duplicate, jPoint);
        eventServiceDao.remove(jPoint);
    }

    @Test
    public void testGetAll() {
        int before = eventServiceDao.getAll().size();
        jPoint = eventServiceDao.save(jPoint);
        int after = eventServiceDao.getAll().size();
        eventServiceDao.remove(jPoint);
        assertEquals(before + 1, after);
    }

    @Test
    public void testGetForDateRange() {
        fakel = (Auditorium) ctx.getBean("fakel");
        fakel = auditoriumServiceDao.save(fakel);
        LocalDate begin = LocalDate.now();
        LocalDate end = begin.plusDays(2);
        int before = eventServiceDao.getForDateRange(begin, end).size();
        LocalDate inside = begin.plusDays(1);
        jPoint.addAirDateTime(
                LocalDateTime.of(inside, LocalTime.NOON), fakel);
        jPoint = eventServiceDao.save(jPoint);
        int after = eventServiceDao.getForDateRange(begin, end).size();
        assertEquals(before + 1, after); // error

        before = after;
        LocalDate outside = begin.plusDays(3);
        millenium.addAirDateTime(
                LocalDateTime.of(outside, LocalTime.NOON), fakel);
        millenium = eventServiceDao.save(millenium);
        after = eventServiceDao.getForDateRange(begin, end).size();
        eventServiceDao.remove(jPoint);
        eventServiceDao.remove(millenium);
        assertEquals(before, after);
    }
}
