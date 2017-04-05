package ua.epam.spring.hometask.discount.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.discount.Strategy;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Component("ticketsStrategy")
public class TicketsStrategy implements Strategy {

    @Value("${tickets.in.wholesale}")
    private int ticketsInWholesale;

    @Value("${discount.for.wholesale}")
    private int discount;

    /** amount of discounted tickets */
    private long discounted(long numberOfTickets) {
        return numberOfTickets / ticketsInWholesale;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {
        if (null == user) {
            return 0;
        }
        int purchased = user.getTickets().size();
        long inOrder = discounted(purchased + numberOfTickets)
                - discounted(purchased);
        return (byte) ((inOrder * discount) /  numberOfTickets);
    }
}
