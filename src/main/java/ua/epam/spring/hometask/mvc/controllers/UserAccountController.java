package ua.epam.spring.hometask.mvc.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserAccountService;
import ua.epam.spring.hometask.service.UserService;

@Controller
@RequestMapping("/account")
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserService userService;

    private static final Logger LOG = LogManager.getLogger();

    @RequestMapping("/refill")
    public ModelAndView refill(@RequestParam double amount) {

        String username = ((UserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()).getUsername();

        User user = userService.getUsersByName(username).iterator().next();
        user = userAccountService.refill(user, amount);
        double currentAmount = userAccountService.getAmount(user);

        ModelAndView mav = new ModelAndView("simplePage");
        mav.addObject("title", "Your account is successfully refilled");
        mav.addObject("message", "Balance of your account = " + currentAmount );
        return mav;
    }
}
