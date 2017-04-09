package ua.epam.spring.hometask.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.Set;

//@Aspect
@Component("counterAspect")
public class CounterAspect {

    private CounterDao counterGetByNameDao;
    private CounterDao counterGetByPriceDao;
    private CounterDao counterBookTicketsDao;

    @Autowired
    public CounterAspect(
            @Qualifier("counterGetByNameDao") CounterDao counterGetByNameDao,
            @Qualifier("counterGetByPriceDao") CounterDao counterGetByPriceDao,
            @Qualifier("counterBookTicketsDao") CounterDao counterBookTicketsDao) {
        this.counterGetByNameDao = counterGetByNameDao;
        this.counterGetByPriceDao = counterGetByPriceDao;
        this.counterBookTicketsDao = counterBookTicketsDao;
    }

    public int getCounterByName(String name) {
        return counterGetByNameDao.getCounter(name);
    }

    public int getCounterByPrice(String name) {
        return counterGetByPriceDao.getCounter(name);
    }

    public int getCounterBookTickets(String name) {
        return counterBookTicketsDao.getCounter(name);
    }


    @Pointcut("execution(* *.getByName(..)) && within(*..EventServiceImpl)")
    public void getByNameMethod() {
    }

    @Around("getByNameMethod() && args(arg1)")
    public void aroundGetByNameMethod(ProceedingJoinPoint jp, Object arg1)
            throws Throwable {
        String name = (String) arg1;
        counterGetByNameDao.incCounter(name);
        jp.proceed(new Object[]{name});
    }


    @Before("execution(* *.getBasePrice()) && within(*..Event)")
    public void countGetBasePriceMethod(JoinPoint jp) {
        Event event = (Event) jp.getTarget();
        String name = event.getName();
        counterGetByPriceDao.incCounter(name);
    }


    @Pointcut("execution(* *.bookTickets(..)) && within(*..BookingServiceImpl)")
    public void bookTicketsMethod() {
    }

    @Around("bookTicketsMethod() && args(arg1)")
    public void aroundBookTicketsMethod(ProceedingJoinPoint jp, Object arg1)
            throws Throwable {
        Set<Ticket> tickets = (Set<Ticket>) arg1;
        for (Ticket ticket : tickets) {
            String name = ticket.getEvent().getName();
            counterBookTicketsDao.incCounter(name);
        }
        jp.proceed(new Object[]{arg1});
    }


}
