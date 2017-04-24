package ua.epam.spring.hometask.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.impl.InsufficientMoneyException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class TestLuckyWinnerAspect {

//    @Test
    public void testLuckyWinnerAspect() throws InsufficientMoneyException {

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        BookingService bookingService = (BookingService)
                ctx.getBean("bookingService");
        LuckyWinnerAspect aspect = (LuckyWinnerAspect)
                ctx.getBean("luckyWinnerAspect");

        User marko = (User) ctx.getBean("marko");
        Auditorium fakel = (Auditorium) ctx.getBean("fakel");
        Event event = (Event) ctx.getBean("jPoint");
        LocalDateTime now = LocalDateTime.now();
        event.addAirDateTime(now, fakel);
        Ticket ticket = new Ticket(marko, event, now, 1L);

        aspect.setLuckiness(0.000001d);
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        double price = ticket.getPrice();
        System.out.println("1 price = " + price);
        assertTrue( Math.abs(price) > Double.MIN_VALUE);

        aspect.setLuckiness(1d);
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        price = ticket.getPrice();
        System.out.println("2 price = " + price);
        assertTrue( Math.abs(price) < Double.MIN_VALUE);

        ctx.close();
    }

}
