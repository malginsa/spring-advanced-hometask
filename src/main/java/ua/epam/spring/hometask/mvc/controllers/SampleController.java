package ua.epam.spring.hometask.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

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
        throw new Exception("Sample exception");
    }

    // TODO sample from lecture
//    @RequestMapping(value = "/form", method = RequestMethod.POST)
//    public String handleFormUpload(@RequestParam String name,
//                                   @RequestParam MultipartFile file) throws IOException {
//        if (!file.isEmpty()) {
//            byte[] bytes = file.getBytes();
//            // store the bytes somewhere
//            return "redirect:uploadSuccess";
//        }
//        return "redirect:uploadFailure";
//    }
}
