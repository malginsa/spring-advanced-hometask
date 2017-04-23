package ua.epam.spring.hometask.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestUserServiceDao {

    private static final Logger LOG = LogManager.getLogger();

    private static AnnotationConfigApplicationContext ctx;
    private static UserServiceDao dao;
    private static User timothy;
    private static User uncleBob;
    private static BookingService bookingService;
    private static AuditoriumService auditoriumService;
    private static EventService eventService;

    @BeforeClass
    public static void init() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        dao = (UserServiceDao) ctx.getBean("userServiceDao");
        bookingService = (BookingService) ctx.getBean("bookingService");
        auditoriumService = (AuditoriumService) ctx.getBean("auditoriumService");
        eventService = (EventService) ctx.getBean("eventService");

        // timothy is stored into Persistance
        timothy = new User();
        timothy.setFirstName("Timothy");
        timothy.setLastName("Budd");
        timothy.setEmail("budd@eecs.oregonstate.edu");
        timothy.setBithday(LocalDate.of(1955, 3, 15));
        timothy = dao.save(timothy);

        // uncleBob is not stored into Persistance
        uncleBob = new User();
        uncleBob.setFirstName("Robert");
        uncleBob.setLastName("Martin");
        uncleBob.setBithday(LocalDate.of(1952, 6, 2));
        uncleBob.setEmail("rmartin@email.com");
    }

    @AfterClass
    public static void close() {
        User byId = dao.getById(timothy.getId());
//         cause error in testGetPurchasedTicketsForEvent
        dao.remove(byId);
        ctx.close();
    }

    @Test
    public void testSave() {
        uncleBob = dao.save(uncleBob);
        dao.remove(uncleBob);

        uncleBob = dao.save(uncleBob);
        dao.remove(uncleBob);

        User managed = dao.save(uncleBob);
        assertEquals(managed, uncleBob);
        dao.remove(managed);
    }

    @Test
    public void testRemove() {
        Long count = dao.getUsersCount();
        uncleBob = dao.save(uncleBob);
        dao.remove(uncleBob);
        assertEquals(dao.getUsersCount(), count);
    }

    @Test
    public void getUserByEmail() {
        User user = dao.getUserByEmail("budd@eecs.oregonstate.edu");
        assertEquals(timothy, user);
    }

    @Test
    public void testUpdate() {
        String newEmail = "actual@new.email";
        String oldEmail = timothy.getEmail();
        timothy.setEmail(newEmail);
        timothy = dao.save(timothy);
        assertEquals(
                dao.getById(timothy.getId()).getEmail(),
                newEmail);
        timothy.setEmail(oldEmail);
        timothy = dao.save(timothy);
    }

    @Test
    public void testGetById() {
        User user = dao.getById(timothy.getId());
        assertEquals(user, timothy);
    }

//    @Test
    public void testGetPurchasedTicketsForEvent() {
        Auditorium coliseum = auditoriumService.getByName("Coliseum").get();
        LOG.fatal(coliseum);
        Event mud = (new Event())
                .setName("Mud")
                .setRating(EventRating.MID)
                .setBasePrice(17.);
        LocalDateTime dt = LocalDateTime.of(2017, 1, 11, 18, 0);
        mud.addAirDateTime(dt, coliseum);
        mud = eventService.save(mud);
        Ticket ticket = (new Ticket())
                .setDateTime(dt)
                .setEvent(mud)
                .setUser(timothy);
        bookingService.bookTickets(new HashSet<Ticket>(){{add(ticket);}});
        assertEquals(dao.getPurchasedTicketsForEvent(mud, dt).iterator().next(), ticket);
        // TODO error during remove event
        // or user
//        eventService.remove(mud); // Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails (`movietheater`.`Ticket`, CONSTRAINT `FKsotv3qctce5ggaewd002dd71s` FOREIGN KEY (`event_id`) REFERENCES `Event` (`id`))
    }
}
