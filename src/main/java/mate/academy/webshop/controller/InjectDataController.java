package mate.academy.webshop.controller;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Role;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartDao cartDao = (ShoppingCartDao) INJECTOR
            .getInstance(ShoppingCartDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User user = new User("mockdude", "dude@gmail.com",
                "Jhon", "Doe", "1234");
        user.setRoles(Set.of(Role.of("USER")));
        userService.create(user);
        User admin = new User("admin", "admin@gmail.com",
                "Admin", "Admin", "1234");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);
        ShoppingCart cart = new ShoppingCart(user);
        cartDao.create(cart);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
