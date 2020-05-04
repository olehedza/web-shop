package mate.academy.webshop.security;

import mate.academy.webshop.exceptions.AuthenticationException;
import mate.academy.webshop.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
