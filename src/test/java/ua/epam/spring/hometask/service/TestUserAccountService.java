package ua.epam.spring.hometask.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.AppConfigForTesting;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;

import static org.junit.Assert.assertEquals;

public class TestUserAccountService {

    private static final Logger LOG = LogManager.getLogger();

    private static AnnotationConfigApplicationContext ctx;
    private static UserService userService;
    private static UserAccountService userAccountService;
    private static User marko;

    @BeforeClass
    public static void init() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        userAccountService = (UserAccountService) ctx.getBean("userAccountService");
        userService = (UserService) ctx.getBean("userService");
        marko = (User) ctx.getBean("marko");
        marko = userService.save(marko);
    }

    @AfterClass
    public static void close() {
        // TODO clear persistance context: delete marko
        ctx.close();
    }

    @Test
    public void testRefill() {
        Float before = userAccountService.getAmount(marko);
        marko = userAccountService.refill(marko, 100f);
        Float after = userAccountService.getAmount(marko);
        assertEquals(after - before, 100f, 0.000001);
    }

    @Test
    public void testWithdraw() {
        marko = userAccountService.refill(marko, 100f);
        Float before = userAccountService.getAmount(marko);
        marko = userAccountService.withdraw(marko, 7f);
        marko = userService.save(marko);
        Float after = userAccountService.getAmount(marko);
        assertEquals(before - after, 7f, 0.000001);
    }
}
