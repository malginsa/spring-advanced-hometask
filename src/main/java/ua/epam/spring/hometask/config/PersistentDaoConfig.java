package ua.epam.spring.hometask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.dao.persistent.CounterPersistentDao;
import ua.epam.spring.hometask.domain.CounterType;

@Configuration
public class PersistentDaoConfig {

    @Bean
    public CounterDao counterGetByNameDao() {
        return new CounterPersistentDao(CounterType.GET_BY_NAME);
    }

    @Bean
    public CounterDao counterGetByPriceDao() {
        return new CounterPersistentDao(CounterType.GET_BY_PRICE);
    }

    @Bean
    public CounterDao counterBookTicketsDao() {
        return new CounterPersistentDao(CounterType.BOOK_TICKETS);
    }

    @Bean
    public CounterDao counterDiscountStrategyDao() {
        return new CounterPersistentDao(CounterType.DISCOUNT_STRATEGY);
    }

    @Bean
    public CounterDao counterDiscountUserDao() {
        return new CounterPersistentDao(CounterType.DISCOUNT_USER);
    }
}
