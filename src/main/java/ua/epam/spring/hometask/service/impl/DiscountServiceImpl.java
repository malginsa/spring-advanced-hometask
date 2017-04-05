package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.discount.Strategy;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

@Component("discountService")
public class DiscountServiceImpl implements DiscountService {

    List<Strategy> strategyList;

    @Autowired
    public DiscountServiceImpl(List<Strategy> strategyList) {
        this.strategyList = strategyList;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {
        byte maxDiscount = 0;
        for (Strategy strategy : strategyList) {
            byte discount = strategy.getDiscount(user, event, airDateTime, numberOfTickets);
            if (discount > maxDiscount) {
                maxDiscount = discount;
            }
        }
        return maxDiscount;
    }
}
