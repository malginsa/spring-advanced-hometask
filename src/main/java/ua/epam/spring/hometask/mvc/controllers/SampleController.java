package ua.epam.spring.hometask.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {

    // http://localhost:8080/capitalizeMe?param=GettingBetter
    @RequestMapping("/capitalizeMe")
    public ModelAndView say( @RequestParam String param ) {
        ModelAndView mav = new ModelAndView("simplePage");
        mav.addObject("title", "Capitalized parameter");
        mav.addObject("message", param.toUpperCase() );
        return mav;
    }

    @RequestMapping("/exception")
    public ModelAndView invokeException() throws Exception {
        throw new Exception("Test exception");
    }
}
