package mate.academy.webshop.dao;

import java.util.Optional;
import mate.academy.webshop.model.User;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByLogin(String login);
}
