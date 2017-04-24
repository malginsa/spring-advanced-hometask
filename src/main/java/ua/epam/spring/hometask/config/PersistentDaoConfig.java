package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.dao.persistent.CounterPersistentDao;
import ua.epam.spring.hometask.domain.CounterType;

import javax.persistence.EntityManagerFactory;

@Configuration
public class PersistentDaoConfig {

//    public EntityManagerFactory getEntityManagerFactory() {
//        return entityManagerFactory;
//    }
//
//    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
//        this.entityManagerFactory = entityManagerFactory;
//    }
//
//    @Autowired
//    TODO isn't autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public CounterDao counterGetByNameDao() {
        return new CounterPersistentDao(CounterType.GET_BY_NAME, entityManagerFactory);
    }

    @Bean
    public CounterDao counterGetByPriceDao() {
        return new CounterPersistentDao(CounterType.GET_BY_PRICE, entityManagerFactory);
    }

    @Bean
    public CounterDao counterBookTicketsDao() {
        return new CounterPersistentDao(CounterType.BOOK_TICKETS, entityManagerFactory);
    }

    @Bean
    public CounterDao counterDiscountStrategyDao() {
        return new CounterPersistentDao(CounterType.DISCOUNT_STRATEGY, entityManagerFactory);
    }

    @Bean
    public CounterDao counterDiscountUserDao() {
        return new CounterPersistentDao(CounterType.DISCOUNT_USER, entityManagerFactory);
    }
}
