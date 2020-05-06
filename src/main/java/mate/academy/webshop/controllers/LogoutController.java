package mate.academy.webshop.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class LogoutController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("User with id %s logout",
                    req.getSession().getAttribute("user_id")));
        }
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
