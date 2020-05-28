package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.webshop.lib.Injector;
import mate.academy.webshop.service.UserService;

public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.webshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String userId = req.getParameter("id");
        userService.delete(Long.valueOf(userId));

        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
