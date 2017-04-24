package ua.epam.spring.hometask.dao;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.AppConfigForTesting;

import static org.junit.Assert.assertEquals;

public class TestCounterDao {

//    @Test
    public void test() {
        AnnotationConfigApplicationContext ctx = new
                AnnotationConfigApplicationContext(
                AppConfig.class, AppConfigForTesting.class);
        ctx.scan("ua.epam.spring.hometask");
        CounterDao dao = (CounterDao) ctx.getBean("counterGetByNameDao");
        String name = "testName";
        int before = dao.getCounter(name);
        dao.incCounter(name);
        int after = dao.getCounter(name);
        assertEquals(before + 1, after);
        dao.incCounter(name);
        after = dao.getCounter(name);
        assertEquals(before + 2, after);
        ctx.close();
    }
}
