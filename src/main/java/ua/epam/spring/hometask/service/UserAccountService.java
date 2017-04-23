package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;

/**
 * @author Sergei_Malgin
 */
public interface UserAccountService {

    /**
     * Refill user's account
     *
     * @param user user, whose account are to be refilled
     * @param amount how much money should be deposit to account
     * @return persistent user after refilling
     */
    public User refill(@Nonnull User user,
                       @Nonnull Float amount);

    /**
     * Withdraw money from user's account
     * @param user user, whose account should be withdrawal from
     * @param amount how much money should be withdrawal from account
     */
    public User withdraw(@Nonnull User user,
                         @Nonnull Float amount)
            throws IllegalArgumentException;

    /**
     * Get amount of money available for withdrawal
     * @param user user with his account
     * @return amount of money
     */
    public Float getAmount(@Nonnull User user);
}
