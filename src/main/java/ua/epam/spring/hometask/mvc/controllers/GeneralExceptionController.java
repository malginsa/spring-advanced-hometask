package ua.epam.spring.hometask.mvc.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GeneralExceptionController {

    private static final Logger LOG =
        Logger.getLogger(GeneralExceptionController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView onServerException(Exception e) {
        LOG.error("Internal server error while processing request", e);
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("message", HttpStatus.INTERNAL_SERVER_ERROR.toString()
                                 + "\n" + e.getMessage());
        return mav;
    }
}
