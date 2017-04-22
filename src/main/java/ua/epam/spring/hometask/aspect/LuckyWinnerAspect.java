package ua.epam.spring.hometask.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.util.Random;
import java.util.Set;

@Aspect
@Component("luckyWinnerAspect")
public class LuckyWinnerAspect {

    private static final Logger LOG = LogManager.getLogger(DiscountAspect.class.getSimpleName());

    private Random random;
    @Value("${luckiness}")
    private double luckiness; // probability 0..1

    public LuckyWinnerAspect() {
        random = new Random();
    }

    // it's an ugly development spike for testing purpose
    public void setLuckiness(double luckiness) {
        this.luckiness = luckiness;
    }

    private boolean tryLuckiness() {
        final int LIMIT = 1_000_000;
        int nextInt = random.nextInt(LIMIT);
        return ( luckiness * LIMIT ) > nextInt;
    }

    @Around("execution(* *.bookTickets(..)) && args(tickets)")
    public void aroundBookTicketsMethod(ProceedingJoinPoint jp,
                                        Set<Ticket> tickets) throws Throwable {
        jp.proceed(new Object[]{tickets});
        User user = tickets.iterator().next().getUser();
        if (tryLuckiness()) {
            for (Ticket ticket : tickets) {
                ticket.setPrice(0d);
                LOG.info("BINGO! user " + user + " is lucky today," +
                        " got zero price for ticket " + ticket);
            }
        }
    }
}
