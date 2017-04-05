package ua.epam.spring.hometask.discount.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.discount.Strategy;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**  Birthday strategy - give 5% if user has birthday within 5 days of air date */
@Component("bithdayStrategy")
public class BithdayStrategy implements Strategy {

    /** birthday discount is activated several days before the birthday day
     * and is finished several days after */
    @Value("${bithday.days}")
    private long BITHDAY_DAYS;

    @Value("${bithday.discount}")
    private byte bithdayDiscount;

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {
        if (null == user) {
            return 0;
        }
        if (null == user.getBithday()) {
            return 0;
        }
        int yearOfEvent = airDateTime.getYear();
        LocalDate bithdayInThisYear = user.getBithday().withYear(yearOfEvent);
        LocalDate from = bithdayInThisYear.minusDays(BITHDAY_DAYS);
        LocalDate to = bithdayInThisYear.plusDays(BITHDAY_DAYS);
        LocalDate dateOfEvent = airDateTime.toLocalDate();
        if (dateOfEvent.isAfter(from) && dateOfEvent.isBefore(to)) {
            return bithdayDiscount;
        } else {
            return 0;
        }
    }
}
