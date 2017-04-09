package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

//@Aspect
@Component("discountAspect")
public class DiscountAspect {

    // key - strategy class name
    private CounterDao strategyDao;
    // key - user id as String
    private CounterDao userDao;

    @Autowired
    public DiscountAspect(
            @Qualifier("counterDiscountStrategyDao") CounterDao strategyDao,
            @Qualifier("counterDiscountUserDao") CounterDao userDao) {
        this.strategyDao = strategyDao;
        this.userDao = userDao;
    }

    public int getCountByStrategy(String name) {
        return strategyDao.getCounter(name);
    }

    public int getCountByUser(Long id) {
        return userDao.getCounter(id.toString());
    }

    @Before("execution(* *..getDiscount(..))" +
            " && args(user, event, airDateTime, numberOfTickets)")
    public void getDiscountMethod(JoinPoint jp, User user, Event event,
                                  LocalDateTime airDateTime,
                                  long numberOfTickets) {
        String strategyName = jp.getTarget().getClass().getSimpleName();
        strategyDao.incCounter(strategyName);
        userDao.incCounter(user.getId().toString());
    }
}
