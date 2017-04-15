package ua.epam.spring.hometask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.dao.EventServiceDao;
import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.dao.simple.CounterSimpleDao;
import ua.epam.spring.hometask.dao.simple.EventServiceSimpleDao;
import ua.epam.spring.hometask.dao.simple.UserServiceSimpleDao;

@Configuration
public class SimpleDaoConfig {

    @Bean
    public EventServiceDao eventServiceDao() {
        return new EventServiceSimpleDao();
    }

    @Bean
    public CounterDao counterGetByNameDao() {
        return new CounterSimpleDao();
    }

    @Bean
    public CounterDao counterGetByPriceDao() {
        return new CounterSimpleDao();
    }

    @Bean
    public CounterDao counterBookTicketsDao() {
        return new CounterSimpleDao();
    }

    @Bean
    public CounterDao counterDiscountStrategyDao() {
        return new CounterSimpleDao();
    }

    @Bean
    public CounterDao counterDiscountUserDao() {
        return new CounterSimpleDao();
    }
}
