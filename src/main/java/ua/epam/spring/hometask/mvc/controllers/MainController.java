package ua.epam.spring.hometask.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class MainController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditoriumService auditoriumService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/getTicketsForm")
    public String getTicketsForm() {
        return "getTicketsForm";
    }

    @RequestMapping("/getTicketsInPdfForm")
    public String getTicketsInPdfForm() {
        return "getTicketsInPdfForm";
    }

    @RequestMapping("/getUserByEmailForm")
    public String getUserByEmailForm() {
        return "getUserByEmailForm";
    }

    @RequestMapping("/getUserByNameForm")
    public String getUserByNameForm() {
        return "getUserByNameForm";
    }

    @RequestMapping("/uploadForm")
    public String uploadForm() {
        return "uploadForm";
    }

    @RequestMapping(value = "/doUploadMultipartFile",
        method = RequestMethod.POST)
    public String doUploadMultipartFile(
        @RequestParam("users")
            MultipartFile usersFile,
        @RequestParam("events")
            MultipartFile eventsFile) {

        String name = "usersCopy";
        try {
            usersFile.transferTo(new File(name));
        } catch (IOException e) {
            System.out.println("can't store to file " + name);
        }

        name = "eventsCopy";
        try {
            eventsFile.transferTo(new File(name));
        } catch (IOException e) {
            System.out.println("can't store to file " + name);
        }

        return "index";
    }

    // tests data for SpringMVC facility
    // TODO delete before releasing
    @RequestMapping("/populateTestData")
    private String populateTestDataForMVC() {
        Event mud = (new Event())
            .setName("Mud")
            .setRating(EventRating.MID)
            .setBasePrice(17.50);
        mud.addAirDateTime(
            LocalDateTime.of(2017, 1, 11, 18, 0),
            auditoriumService.getByName("Aurora").get());
        mud = eventService.save(mud);
        User timothy = new User()
            .setFirstName("Timothy")
            .setLastName("Budd")
            .setEmail("budd@eecs.oregonstate.edu")
            .setBithday(LocalDate.of(1955, 3, 15));
        timothy = userService.save(timothy);
        User timothy2 = new User()
            .setFirstName("Timothy")
            .setLastName("The Second")
            .setEmail("budd@eecs.oregonstate.edu")
            .setBithday(LocalDate.of(1999, 9, 19));
        timothy2 = userService.save(timothy2);
        return "index";
    }
}
