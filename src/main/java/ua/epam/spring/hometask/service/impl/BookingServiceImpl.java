package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.EventServiceDao;
import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component("bookingService")
public class BookingServiceImpl implements BookingService {

    /** VIP seat costs more than ordinary in how many */
    public static final double VIP_FACTOR = 2;

    /** price for HIGH rated event costs more than ordinary in how many */
    public static final double HIGH_RATED_FACTOR = 1.2;

    private DiscountService discountService;
    private UserServiceDao userServiceDao;

    @Autowired
    public BookingServiceImpl(
            @Qualifier("discountService") DiscountService discountService,
            @Qualifier("userServiceDao") UserServiceDao userServiceDao) {
        this.discountService = discountService;
        this.userServiceDao = userServiceDao;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Long> seats) {

        double basePrice = event.getBasePrice();
        int numberOfSeats = seats.size();
        double baseSumm = basePrice * numberOfSeats;
        Auditorium auditorium = event.getAuditorium(dateTime);

        baseSumm += auditorium.countVipSeats(seats) * basePrice * (VIP_FACTOR - 1);

        if (event.getRating() == EventRating.HIGH) {
            baseSumm *= HIGH_RATED_FACTOR;
        }

        byte discount = discountService.getDiscount(user, event, dateTime, numberOfSeats);
        baseSumm *= (100 - discount);

        return baseSumm;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (null == ticket) {
                continue;
            }
            User user = ticket.getUser();
            double price = getTicketsPrice(ticket.getEvent(),
                ticket.getDateTime(), user,
                new HashSet<Long>() {{ add(1L); }});
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
