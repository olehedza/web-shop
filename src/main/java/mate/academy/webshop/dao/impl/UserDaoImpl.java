package mate.academy.webshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.webshop.dao.UserDao;
import mate.academy.webshop.db.Storage;
import mate.academy.webshop.lib.Dao;
import mate.academy.webshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(i -> user.getId().equals(Storage.users.get(i).getId()))
                .forEach(i -> Storage.users.set(i, user));
        return user;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.users.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Storage.users.stream()
                .filter(s -> s.getUsername().equals(login))
                .findFirst();
    }
}
