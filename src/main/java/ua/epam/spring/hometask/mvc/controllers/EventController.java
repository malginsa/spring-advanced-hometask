package ua.epam.spring.hometask.mvc.controllers;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.service.EventService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/event")
public class EventController {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private EventService eventService;

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event getEventById(@PathVariable Long id, HttpServletResponse response) {
        Event event = eventService.getById(id);
        if (null == event) {
            response.setStatus(404);
        } else {
            response.setStatus(201);
        }
        return event;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Event create(@RequestBody
                       final Event event) {
        Event saved = eventService.save(event);
        return saved;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView delete(@PathVariable Long id, HttpServletResponse response) {
        Event event = eventService.getById(id);
        ModelAndView mav = new ModelAndView("simplePage");
        if (null == event) {
            response.setStatus(404);
            mav.addObject("title", "No events with this eMail");
            mav.addObject("message", "");
        } else {
            eventService.remove(event); // TODO return boolean
            mav.addObject("title", "event founded, event is obviously removed. Get it by id to check");
            mav.addObject("message", "");
            response.setStatus(202);
        }
        return mav;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Event update(@RequestBody final Event event) {
        Event saved = eventService.save(event);
        return saved;
    }




    // TODO refactor it with UserController.usersUploadMultipart() to get rid of code duplication
    // uploading and parsing multipart-file containing event
    // with format: <name>,<Set-of-airDates>,<basePrice>,<rating>,<Map(key:dateTime;value:auditoriumName:)>
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView eventsUpload(
            @RequestParam("file") MultipartFile multiPartFile,
            HttpServletRequest request) throws Exception {

        if (multiPartFile.isEmpty()) {
            ModelAndView mav = new ModelAndView("simplePage");
            mav.addObject("title", "Error");
            mav.addObject("message", "The uploaded file is empty or no file has been chosen");
            return mav;
        }

        File localFile = new File(multiPartFile.getName());
        try {
            multiPartFile.transferTo(localFile);
        } catch (IOException e) {
            LOG.error("can't store multiPartFile:" + multiPartFile + " to localFile:" + localFile + " with eventList");
            e.printStackTrace();
            throw new Exception("Uploading service unavailable, please contact administrator");
        }

        try (FileReader fileReader = new FileReader(localFile);
             CSVReader csvReader = new CSVReader(fileReader)) {
            while (csvReader.readNext() != null) {
                String[] line = csvReader.readNext();
                Event event = (new Event())
                        .setName(line[0])
                        .setTicketPrice(Double.parseDouble(line[2]))
                        .setRating(EventRating.valueOf(line[3]));
                eventService.save(event);
            }
        } catch (IOException e) {
            LOG.error("error occurred while parsing eventList from localFile:" + localFile);
            e.printStackTrace();
            throw new Exception("Uploading service corrupted, please contact administrator");
        }
        ModelAndView mav = new ModelAndView("simplePage");
        mav.addObject("title", "Ok");
        mav.addObject("message", "All events have been imported from file");
        return mav;
    }
}
