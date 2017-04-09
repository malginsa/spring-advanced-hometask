package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

public class PopulateTestData {

    @Autowired
    AuditoriumService auditoriumService;

    @Autowired
    BookingService bookingService;

//    @Autowired
//    UserServiceDao userServiceDao;

    public PopulateTestData() {
        Auditorium galeria = new Auditorium( "Galeria", 77,
                new HashSet<Long>() {{
                    add(3L);
                }});
        Event mud = (new Event())
                .setName("Mud")
                .setRating(EventRating.MID)
                .setBasePrice(17.);
        User timothy = new User()
                .setFirstName("Timothy")
                .setLastName("Budd")
                .setEmail("budd@eecs.oregonstate.edu")
                .setBithday(LocalDate.of(1955, 3, 15));
//        timothy = userServiceDao.save(timothy);

        LocalDateTime dt = LocalDateTime.of(2017, 1, 11, 18, 0);
        mud.addAirDateTime(dt, galeria);
        Ticket ticket = (new Ticket())
                .setDateTime(dt)
                .setEvent(mud)
                .setUser(timothy);
        bookingService.bookTickets(new HashSet<Ticket>(){{add(ticket);}});
    }
}
