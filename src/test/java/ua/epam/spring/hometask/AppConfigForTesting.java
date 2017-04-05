package ua.epam.spring.hometask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDate;
import java.util.HashSet;

@Configuration
public class AppConfigForTesting {

    @Bean
    public User marko() {
        User user = new User();
        user.setFirstName("Marko");
        user.setLastName("Polo");
        user.setEmail("Marko_Polo@gmail.com");
        user.setBithday(LocalDate.of(1254, 9, 15));
//        if (null == user.getId()) {
//            user.setId(8520730L);
//        }
        return user;
    }

    @Bean
    public Auditorium fakel() {
        return new Auditorium("Fakel", 10,
                new HashSet<Long>() {{
                    add(1L);
                    add(2L);
                }});
    }

    @Bean
    public Auditorium balkany() {
        return new Auditorium("Balkany", 20,
                new HashSet<Long>() {{
                    add(1L);
                    add(3L);
                }});
    }

    @Bean
    public Auditorium zvezda() {
        return new Auditorium("Zvezda", 40,
                new HashSet<Long>() {{
                    add(11L);
                    add(12L);
                }});
    }

    @Bean
    public Event jPoint() {
        Event event = new Event();
        event.setName("JPoint");
        event.setBasePrice(19d);
        event.setRating(EventRating.HIGH);
        // TODO
//        if (null == event.getId()) {
//            event.setId(383695739L);
//        }
        return event;
    }

    @Bean
    public Event millenium() {
        Event event = new Event();
        event.setName("Millenium");
        event.setBasePrice(10d);
        event.setRating(EventRating.MID);
        // TODO
//        if (null == event.getId()) {
//            event.setId(9752569L);
//        }
        return event;
    }
}
