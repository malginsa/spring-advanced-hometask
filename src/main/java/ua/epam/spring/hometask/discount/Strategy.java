package ua.epam.spring.hometask.discount;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface Strategy {

    /**
     * @return <code>amount</code> of discount in percent (between 0 and 100)
     */

    public byte getDiscount(@Nullable User user, @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets);
}
