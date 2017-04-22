package ua.epam.spring.hometask.mvc.controllers;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LogManager.getLogger(UserController.class.getSimpleName());

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

    // uploading and parsing multipart-file containing users
    // with format: <firstName>,<lastName>,<eMail>,<yyyy-MM-dd>
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView usersUpload(
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
            LOG.error("can't store multiPartFile:" + multiPartFile + " to localFile:" + localFile + " with userList");
            e.printStackTrace();
            throw new Exception("Uploading service unavailable, please contact administrator");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (FileReader fileReader = new FileReader(localFile);
               CSVReader csvReader = new CSVReader(fileReader)) {
            while (csvReader.readNext() != null) {
                String[] line = csvReader.readNext();
                User user = (new User())
                        .setFirstName(line[0])
                        .setLastName(line[1])
                        .setEmail(line[2])
                        .setBithday(LocalDate.parse(line[3], dateTimeFormatter));
                userService.save(user);
            }
        } catch (IOException e) {
            LOG.error("error occurred while parsing userList from localFile:" + localFile);
            e.printStackTrace();
            throw new Exception("Uploading service corrupted, please contact administrator");
        }
        ModelAndView mav = new ModelAndView("simplePage");
        mav.addObject("title", "Ok");
        mav.addObject("message", "All users have been imported from file");
        return mav;
    }
}
