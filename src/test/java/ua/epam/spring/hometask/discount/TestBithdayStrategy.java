package ua.epam.spring.hometask.discount;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class TestBithdayStrategy {

    @Test
    public void testGetDiscount() {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        Strategy strategy = (Strategy) ctx.getBean("bithdayStrategy");
        AuditoriumService auditoriumService = (AuditoriumService)
                ctx.getBean("auditoriumService");
        User marko = (User) ctx.getBean("marko");

        LocalDateTime dateTimeEvent = LocalDateTime.of(2017, 1, 16, 15, 30);

        Event event = (Event) ctx.getBean("jPoint");
        event.setAirDates(new TreeSet<LocalDateTime>() {{
            add(dateTimeEvent);
        }});

        byte discount = strategy.getDiscount(marko, event, dateTimeEvent, 1);
        assertEquals(discount, 0);

        marko.setBithday(LocalDate.of(1254, 1, 17));
        discount = strategy.getDiscount(marko, event, dateTimeEvent, 1);
        assertEquals(discount, 5);
    }
}
