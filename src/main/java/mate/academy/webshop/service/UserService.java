package mate.academy.webshop.service;

import java.util.Optional;
import mate.academy.webshop.model.User;

public interface UserService extends GenericService<User> {

    Optional<User> findByLogin(String login);
}
