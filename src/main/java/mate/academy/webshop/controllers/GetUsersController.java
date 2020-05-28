package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.service.UserService;

public class GetUsersController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("users", userService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/users/allUsers.jsp")
                .forward(req, resp);
    }
}
