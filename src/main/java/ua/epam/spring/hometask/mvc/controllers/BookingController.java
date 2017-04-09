package ua.epam.spring.hometask.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.config.PopulateTestData;
import ua.epam.spring.hometask.dao.EventServiceDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/book")
public class BookingController {

//    @Autowired
//    private BookingService bookingService;

//    @Autowired
//    private EventService eventService;

    // sample: http://localhost:8080/book/getTickets?eventName=Mud?dateTime=2017-1-11'T'18:00
    @RequestMapping("/getTickets")
    public ModelAndView getTickets(
            @RequestParam String eventName,
//            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateTime)
            @RequestParam String dateTime)
            throws Exception {

        System.out.println(dateTime);

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ctx.scan("ua.epam.spring.hometask");
        BookingService bookingService = (BookingService) ctx.getBean("bookingService");
        EventService eventService = (EventService) ctx.getBean("eventService");

        new PopulateTestData();
        Optional<Event> eventOptional = eventService.getByName(eventName);
        if (!eventOptional.isPresent()) {
            throw new Exception("Non-existing event");
        }
//        Set<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(
//                eventOptional.get(), dateTime);
        Set<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(
                eventService.getByName("Mud").get(),
                LocalDateTime.of(2017, 1, 11, 18, 0));
        ModelAndView mav = new ModelAndView("tickets");
        mav.addObject("tickets", tickets);
        return mav;
    }

}
