package ua.epam.spring.hometask.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.service.impl.InsufficientMoneyException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @RequestMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("bookForm");
    }

    // sample: http://localhost:8080/book/doBookTickets?userId=1&eventName=Mud&localDateTime=2017-01-11.18:00&seat=7&seat=8
    @RequestMapping("/doBookTickets")
    public ModelAndView doBookTickets(
            @RequestParam Long userId,
            @RequestParam String eventName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm")
                    LocalDateTime localDateTime,
            @RequestParam("seat") Long[] seats)
            throws Exception {

        User user = userService.getById(userId);
        if (null == user) {
            throw new Exception("Non-existing user Id");
        }
        Optional<Event> eventOptional = eventService.getByName(eventName);
        if (!eventOptional.isPresent()) {
            throw new Exception("Non-existing event");
        }
        Event event = eventOptional.get();
        Set<Ticket> tickets = Arrays.stream(seats)
                .map(s -> (new Ticket())
                        .setUser(user)
                        .setEvent(event)
                        .setDateTime(localDateTime)
                        .setSeat(s))
                .collect(Collectors.toSet());
        ModelAndView mav = new ModelAndView("simplePage");
        try {
            bookingService.bookTickets(tickets);
            mav.addObject("title", "Tickets are successfully booked");
            mav.addObject("message", "");
        } catch (InsufficientMoneyException e) {
            mav.addObject("title", "Tickets are NOT successfully booked");
            mav.addObject("message", e.getMessage());
        }
        return mav;
    }

    // sample: http://localhost:8080/book/getTickets?eventName=Mud&localDateTime=2017-01-11.18:00
    @RequestMapping("/getTickets")
    public ModelAndView getTickets(
            @RequestParam String eventName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm")
                    LocalDateTime localDateTime)
            throws Exception {
        Optional<Event> eventOptional = eventService.getByName(eventName);
        if (!eventOptional.isPresent()) {
            throw new Exception("Non-existing event");
        }
        Collection<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(
                eventOptional.get(), localDateTime);
        ModelAndView mav;
        if (tickets.size() > 0) {
            mav = new ModelAndView("tickets");
            Ticket ticket = tickets.iterator().next();
            mav.addObject("userName", ticket.getUser().getFirstName());
            mav.addObject("eventName", ticket.getEvent().getName());
            mav.addObject("localDateTime", ticket.getDateTime().toString());
            mav.addObject("tickets", tickets);
        } else {
            mav = new ModelAndView("simplePage");
            mav.addObject("title", "No booked tickets");
            mav.addObject("message", "");
        }
        return mav;
    }

    // It returns the same result as above method except only in PDF document
    @RequestMapping(value = "/getTicketsInPdf", headers="Accept=application/pdf")
    public ModelAndView getTicketsInPdf(
            @ModelAttribute("model") ModelMap model,
            @RequestParam String eventName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm")
                    LocalDateTime localDateTime)
            throws Exception {
        Optional<Event> eventOptional = eventService.getByName(eventName);
        if (!eventOptional.isPresent()) {
            throw new Exception("Non-existing event");
        }
        Collection<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(
                eventOptional.get(), localDateTime);
        ModelAndView mav;
        if (tickets.size() > 0) {
            mav = new ModelAndView("pdf", "tickets", tickets);
        } else {
            mav = new ModelAndView("simplePage");
            mav.addObject("title", "No booked tickets");
            mav.addObject("message", "");
        }
        return mav;
    }
}
