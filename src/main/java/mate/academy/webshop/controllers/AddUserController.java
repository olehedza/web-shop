package mate.academy.webshop.controllers;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.dao.ShoppingCartDao;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.model.Role;
import mate.academy.webshop.model.ShoppingCart;
import mate.academy.webshop.model.User;
import mate.academy.webshop.service.UserService;

public class AddUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final UserService userService = (UserService) INJECTOR
            .getInstance(UserService.class);
    private final ShoppingCartDao cartDao = (ShoppingCartDao) INJECTOR
            .getInstance(ShoppingCartDao.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String password = req.getParameter("pwd");
        String repeatPassword = req.getParameter("pwd-confirm");

        if (password.equals(repeatPassword) && username.matches("^[\\w]{4,12}$")) {
            User user = new User(username, email, firstName, lastName, password);
            user.setRoles(Set.of(Role.of("USER")));
            userService.create(user);
            cartDao.create(new ShoppingCart(user));
            resp.sendRedirect(req.getContextPath() + "/users/all");
        } else {
            req.setAttribute("message", "Your password or username are not valid!");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp")
                    .forward(req, resp);
        }

    }
}
