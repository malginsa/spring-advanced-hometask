package ua.epam.spring.hometask.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.UserAccountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component("bookingService")
public class BookingServiceImpl implements BookingService {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * VIP seat costs more than ordinary in how many
     */
    public static final double VIP_FACTOR = 2;

    /**
     * price for HIGH rated event costs more than ordinary in how many
     */
    public static final double HIGH_RATED_FACTOR = 1.2;

    private DiscountService discountService;
    private UserServiceDao userServiceDao;
    private UserAccountService userAccountService;

    @Autowired
    public BookingServiceImpl(
            @Qualifier("discountService") DiscountService discountService,
            @Qualifier("userServiceDao") UserServiceDao userServiceDao,
            @Qualifier("userAccountService") UserAccountService userAccountService) {
        this.discountService = discountService;
        this.userServiceDao = userServiceDao;
        this.userAccountService = userAccountService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Long> seats) {

        double basePrice = event.getTicketPrice();
        int numberOfSeats = seats.size();
        double baseSumm = basePrice * numberOfSeats;
        Auditorium auditorium = event.getAuditorium(dateTime);

        baseSumm += auditorium.countVipSeats(seats) * basePrice * (VIP_FACTOR - 1);

        if (event.getRating() == EventRating.HIGH) {
            baseSumm *= HIGH_RATED_FACTOR;
        }

        byte discount = discountService.getDiscount(user, event, dateTime, numberOfSeats);
        baseSumm *= (100 - discount) / 100;

        return baseSumm;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = InsufficientMoneyException.class)
//    Either all the Tickets will be booked or no one.

    public void bookTickets(@Nonnull Set<Ticket> tickets)
            throws InsufficientMoneyException {

        for (Ticket ticket : tickets) { // TODO refactor it using stream
            if (null == ticket) {
                continue;
            }
            User user = ticket.getUser();
            double price = getTicketsPrice(ticket.getEvent(),
                    ticket.getDateTime(), user,
                    new HashSet<Long>() {{
                        add(1L);
                    }});
            double availableAmount = userAccountService.getAmount(user);
            if (availableAmount < price) {
                LOG.error("unsufficient(" + availableAmount + ") money for the price(" + price + ")" +
                        "  user:" + user + "  ticket:" + ticket);
                throw new InsufficientMoneyException();
            }
            user = userAccountService.withdraw(user, price);
            ticket.setPrice(price);
            user.addTicket(ticket);
            userServiceDao.save(user);
        }
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event,
                                                   @Nonnull LocalDateTime dateTime) {
        return userServiceDao.getPurchasedTicketsForEvent(event, dateTime);
    }
}
