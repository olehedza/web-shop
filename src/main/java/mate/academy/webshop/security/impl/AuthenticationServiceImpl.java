package mate.academy.webshop.security.impl;

import mate.academy.webshop.exception.AuthenticationException;
import mate.academy.webshop.lib.Inject;
import mate.academy.webshop.lib.Service;
import mate.academy.webshop.model.User;
import mate.academy.webshop.security.AuthenticationService;
import mate.academy.webshop.service.UserService;
import mate.academy.webshop.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password)
            throws AuthenticationException {
        User userFromDB = userService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect username or password"));

        if (HashUtil.getPasswordDigest(password, userFromDB.getSalt())
                .equals(userFromDB.getPassword())) {
            return userFromDB;
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
