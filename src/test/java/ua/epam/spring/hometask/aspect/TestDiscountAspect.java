package ua.epam.spring.hometask.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.discount.Strategy;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class TestDiscountAspect {

//    @Test
    public void testDiscountAspect() {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        Strategy ticketsStrategy = (Strategy) ctx.getBean("ticketsStrategy");
        DiscountAspect discountAspect = (DiscountAspect)
                ctx.getBean("discountAspect");
        User marko = (User) ctx.getBean("marko");

        LocalDateTime dateTimeEvent = LocalDateTime.of(2017, 1, 16, 15, 30);
        Event event = (Event) ctx.getBean("jPoint");
        event.setAirDates(new TreeSet<LocalDateTime>() {{
            add(dateTimeEvent);
        }});

        int strategyBefore = discountAspect
                .getCountByStrategy("TicketsStrategy");
        int userBefore = discountAspect.getCountByUser(marko.getId());

        byte discount = ticketsStrategy
                .getDiscount(marko, event, dateTimeEvent, 7);

        int strategyAfter = discountAspect
                .getCountByStrategy("TicketsStrategy");
        int userAfter = discountAspect.getCountByUser(marko.getId());

        assertEquals(strategyBefore + 1, strategyAfter);
        assertEquals(userBefore + 1, userAfter);

        ctx.close();
    }
}
