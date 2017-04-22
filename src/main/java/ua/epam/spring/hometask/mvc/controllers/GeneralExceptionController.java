package ua.epam.spring.hometask.mvc.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GeneralExceptionController {

private static final Logger LOG = LogManager.getLogger(GeneralExceptionController.class.getSimpleName());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView onServerException(Exception e) {
        LOG.error("Internal server error while processing request", e);
        ModelAndView mav = new ModelAndView("simplePage");
        mav.addObject("title", "Something went wrong");
        mav.addObject("message", e.getMessage());
        return mav;
    }
}
