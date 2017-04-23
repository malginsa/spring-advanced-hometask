package ua.epam.spring.hometask.service;

import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class TestEventService {

    static AnnotationConfigApplicationContext ctx;
    static EventService service;

    @BeforeClass
    public static void init() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        service = (EventService)
                ctx.getBean("eventService");
    }

    @AfterClass
    public static void close() {
        ctx.close();
    }

    @Test
    public void testGetForDateRange() {
        LocalDate from = LocalDate.of(2017, 1, 16);
        LocalDate to = LocalDate.of(2017, 1, 26);

        // no events at all
        assertEquals(service.getForDateRange(from, to).size(), 0);

        Event jPoint = (Event) ctx.getBean("jPoint");
        jPoint.setAirDates(new TreeSet<LocalDateTime>() {{
            // the airDate is not in the range
            add(LocalDateTime.of(2017, 1, 1, 15, 30));
        }});
        jPoint = service.save(jPoint);
        assertEquals(service.getForDateRange(from, to).size(), 0);

        // the airDate is in the range
        jPoint.addAirDateTime(LocalDateTime.of(2017, 1, 17, 18, 00));
        jPoint = service.save(jPoint);
        assertEquals(service.getForDateRange(from, to).size(), 1);

        // two airDates of the same event in the range
        jPoint.addAirDateTime(LocalDateTime.of(2017, 1, 18, 18, 00));
        jPoint = service.save(jPoint);
        assertEquals(service.getForDateRange(from, to).size(), 1);

        // the second event
        Event millenium = (Event) ctx.getBean("millenium");
        millenium.setAirDates(new TreeSet<LocalDateTime>() {{
            // its airDate is in the range
            add(LocalDateTime.of(2017, 1, 19, 15, 30));
        }});
        millenium = service.save(millenium);
        assertEquals(service.getForDateRange(from, to).size(), 2);
        service.remove(jPoint);
        service.remove(millenium);
    }
}
