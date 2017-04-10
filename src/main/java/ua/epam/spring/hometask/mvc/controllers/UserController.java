package ua.epam.spring.hometask.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // sample: http://localhost:8080/user/getByEmail?email=budd@eecs.oregonstate.edu
    @RequestMapping("/getByEmail")
    public ModelAndView getByEmail(@RequestParam String email) throws Exception {
        User user = userService.getUserByEmail(email);
        ModelAndView mav;
        if (null == user) {
            mav = new ModelAndView("simplePage");
            mav.addObject("title", "No users with this eMail");
            mav.addObject("message", "");
        } else {
            List<User> users = Arrays.asList(user);
            mav = new ModelAndView("users");
            mav.addObject("users", users);
        }
        return mav;
    }

    // sample: http://localhost:8080/user/getByName?firstName=Timothy
    @RequestMapping("/getByName")
    public ModelAndView getByName(@RequestParam String firstName) {
        Collection<User> users = userService.getUsersByName(firstName);
        ModelAndView mav;
        if (users.size() > 0) {
            mav = new ModelAndView("users");
            mav.addObject("users", users);
        } else {
            mav = new ModelAndView("simplePage");
            mav.addObject("title", "No users with this firstName");
            mav.addObject("message", "");
        }
        return mav;
    }
}
