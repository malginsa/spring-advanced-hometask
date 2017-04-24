package ua.epam.spring.hometask.aspect;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestCounterAspect {

    private AnnotationConfigApplicationContext ctx;
    private CounterAspect counterAspect;
    private EventService eventService;
    private BookingService bookingService;

    @Before
    public void before() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class,
                AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        counterAspect = (CounterAspect) ctx.getBean("counterAspect");
        eventService = (EventService) ctx.getBean("eventService");
        bookingService = (BookingService) ctx.getBean("bookingService");
    }

    @After
    public void after() {
        ctx.close();
    }

//    @Test
    public void testGetByName() {
        Event event = (Event) ctx.getBean("jPoint");
        String name = event.getName();
        int before = counterAspect.getCounterByName(name);
        eventService.save(event);
        eventService.getByName(name);
        int after = counterAspect.getCounterByName(name);
        eventService.remove(event);
        assertEquals(before + 1, after);
    }

//    @Test
    public void testGetBasePrice() {
        Event event = (Event) ctx.getBean("jPoint");
        int before = counterAspect.getCounterByPrice(event.getName());
        event.getTicketPrice();
        int after = counterAspect.getCounterByPrice(event.getName());
        assertEquals(before + 1, after);
    }

//    @Test
    public void testBookTickets() {
        Auditorium fakel = (Auditorium) ctx.getBean("fakel");
        User marko = (User) ctx.getBean("marko");
        Event event = (Event) ctx.getBean("jPoint");

        LocalDateTime now = LocalDateTime.now();
        event.addAirDateTime(now, fakel);

        Ticket ticket = new Ticket(marko, event, now, 1L);
        int before = counterAspect.getCounterBookTickets(event.getName());
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        int after = counterAspect.getCounterBookTickets(event.getName());
        assertEquals(before + 1, after);
    }
}
