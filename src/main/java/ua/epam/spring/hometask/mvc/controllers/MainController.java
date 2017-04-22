package ua.epam.spring.hometask.mvc.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserRole;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private static final Logger LOG = LogManager.getLogger(MainController.class.getSimpleName());

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditoriumService auditoriumService;

    @RequestMapping("/")
    public String root() {
        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) Boolean error,
                        Model model) {
        if (null == error) {
            model.addAttribute("error", false);
            return "login";
        }
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping("/mainMenu")
    public String mainMenu() {
        return "mainMenu";
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

    @RequestMapping("/loggedUserInfo")
    private ModelAndView loggedUserInfo(
    ) {
        org.springframework.security.core.userdetails.UserDetails principal =
                (org.springframework.security.core.userdetails.UserDetails)
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getPrincipal();
        ModelAndView mav = new ModelAndView("simplePage");
        mav.addObject("title", "Logged user info");
        String auth = principal
                .getAuthorities()
                .stream()
                .collect(Collectors.toList())
                .toString();
        mav.addObject("message",
                "username: " + principal.getUsername() + "  auth: " + auth);
        return mav;
    }

    @RequestMapping(value = "/doUploadMultipartFile", method = RequestMethod.POST)
    public String doUploadMultipartFile(
            @RequestParam("users") MultipartFile usersFile,
            @RequestParam("events") MultipartFile eventsFile) {
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
                .setBithday(LocalDate.of(1955, 3, 15))
                .setPassword("848d465b509f3c18fd577475294859e4175e42dbd5c90e695f6e5b0226a76c103bd3a96bbf52903e")
                .addRole(UserRole.ROLE_REGISTERED_USER);
        timothy = userService.save(timothy);

        User timothy2 = new User()
                .setFirstName("Timothy")
                .setLastName("The Second")
                .setEmail("budd@eecs.oregonstate.edu")
                .setBithday(LocalDate.of(1999, 9, 19))
                .setPassword("591da941e5af5a7bd0314d343f5762bcf101efa9ca9b4c79a26da61bc3ffec26a96c2a1d5890da43")
                .addRole(UserRole.ROLE_REGISTERED_USER);
        timothy2 = userService.save(timothy2);

        return "index";
    }
}
