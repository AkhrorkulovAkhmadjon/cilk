package canturoler;

import dataBase.DB;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/auth/checkEmail")
public class CheckEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cabinet/welcome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String usersId = req.getParameter("id");
        Boolean b = DB.checkEmailCode(code, usersId);
        if (b) {
            Optional<Users> userById = DB.getUserById(usersId);
            if (userById.isPresent()) {
                Cookie cookie = new Cookie("userId", usersId);
                cookie.setMaxAge(24 * 60 * 60);
                cookie.setPath("/");
                userById.get().setStatus(true);
                resp.addCookie(cookie);
                req.getRequestDispatcher("/cabinet/welcome.jsp").forward(req, resp);
                return;
            }
        }else {
            req.getRequestDispatcher("/").forward(req, resp);
        }

    }
}
