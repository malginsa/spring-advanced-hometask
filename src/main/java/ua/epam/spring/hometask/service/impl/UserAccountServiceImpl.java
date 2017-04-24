package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.service.UserAccountService;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;

@Component("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private UserService userService;

    public UserAccountServiceImpl(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @Override
    public User refill(@Nonnull User user, @Nonnull Float amount) {
        User persistentUser = userService.getById(user.getId());
        UserAccount account = persistentUser.getAccount();
        account.setAmount(account.getAmount() + amount);
        return userService.save(persistentUser);
    }

    @Override
    public User withdraw(@Nonnull User user, @Nonnull double amount) {
        User persistentUser = userService.getById(user.getId());
        UserAccount account = persistentUser.getAccount();
        double accountAmount = account.getAmount();
        if (accountAmount < amount) {
            throw new IllegalArgumentException("not enough money for withdrawal");
        }
        account.setAmount(accountAmount - amount);
        return persistentUser;
    }

    @Override
    public double getAmount(@Nonnull User user) {
        User persistentUser = userService.getById(user.getId());
        return persistentUser.getAccount().getAmount();
    }
}
