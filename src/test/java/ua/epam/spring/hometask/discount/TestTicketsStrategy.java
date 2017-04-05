package ua.epam.spring.hometask.discount;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class TestTicketsStrategy {

    @Test
    public void testGetDiscount() {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        Strategy strategy = (Strategy) ctx.getBean("ticketsStrategy");
        User marko = (User) ctx.getBean("marko");

        LocalDateTime dateTimeEvent = LocalDateTime.of(2017, 1, 16, 15, 30);

        Event event = (Event) ctx.getBean("jPoint");
        event.setAirDates(new TreeSet<LocalDateTime>() {{
            add(dateTimeEvent);
        }});

        byte discount = strategy.getDiscount(marko, event, dateTimeEvent, 7);
        assertEquals(discount, 0);

        discount = strategy.getDiscount(marko, event, dateTimeEvent, 10);
        assertEquals(discount, 5);

        NavigableSet<Ticket> tickets = new TreeSet<>();
        for (int i = 1; i < 8; i++) {
             tickets.add(new Ticket(marko, event, dateTimeEvent, i));
        }
        marko.setTickets(tickets);
        discount = strategy.getDiscount(marko, event, dateTimeEvent, 10);
        assertEquals(discount, 5);
    }
}
