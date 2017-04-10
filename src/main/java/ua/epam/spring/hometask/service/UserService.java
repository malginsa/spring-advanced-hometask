package ua.epam.spring.hometask.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.User;

import java.util.Collection;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email);

    /**
     * Finding users by firstName
     *
     * @param firstName
     *            firstName of the user
     * @return found users Collection
     */
    public Collection<User> getUsersByName(@Nonnull String firstName);

}
