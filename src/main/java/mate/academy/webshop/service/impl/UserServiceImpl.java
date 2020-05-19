package mate.academy.webshop.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.webshop.dao.UserDao;
import mate.academy.webshop.lib.Inject;
import mate.academy.webshop.lib.Service;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.UserService;
import mate.academy.webshop.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil
                .getPasswordDigest(user.getPassword(), user.getSalt()));
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
