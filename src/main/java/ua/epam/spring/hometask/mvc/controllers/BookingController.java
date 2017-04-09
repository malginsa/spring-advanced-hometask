package ua.epam.spring.hometask.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditoriumService auditoriumService;

    // sample: http://localhost:8080/book/getTickets?eventName=Mud&localDateTime=2017-01-11.18:00
    @RequestMapping("/getTickets")
    public ModelAndView getTickets(
            @RequestParam String eventName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime localDateTime)
            throws Exception {
        populateTestData();
        Optional<Event> eventOptional = eventService.getByName(eventName);
        if (!eventOptional.isPresent()) {
            throw new Exception("Non-existing event");
        }
        Collection<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(
                eventOptional.get(), localDateTime);
        ModelAndView mav;
        if (tickets.size() > 0) {
            mav = new ModelAndView("tickets");
            mav.addObject("tickets", tickets);
        } else {
            mav = new ModelAndView("noTickets");
        }
        return mav;
    }

    // TODO delete before releasing
    private void populateTestData() {
        Auditorium galeria = new Auditorium( "Galeria", 77,
                new HashSet<Long>() {{
                    add(3L);
                }});
        auditoriumService.add(galeria);
        Event mud = (new Event())
                .setName("Mud")
                .setRating(EventRating.MID)
                .setBasePrice(17.50);
        mud = eventService.save(mud);
        User timothy = new User()
                .setFirstName("Timothy")
                .setLastName("Budd")
                .setEmail("budd@eecs.oregonstate.edu")
                .setBithday(LocalDate.of(1955, 3, 15));
        timothy = userService.save(timothy);

        LocalDateTime dt = LocalDateTime.of(2017, 1, 11, 18, 0);
        mud.addAirDateTime(dt, galeria);
        Ticket ticket = (new Ticket())
                .setUser(timothy)
                .setEvent(mud)
                .setDateTime(dt)
                .setSeat(2);
        bookingService.bookTickets(new HashSet<Ticket>(){{add(ticket);}});
    }
}