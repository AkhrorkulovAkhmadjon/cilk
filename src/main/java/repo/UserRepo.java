package repo;

import dataBase.DB;
import entity.Users;
import enums.Role;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import static convenience.email.Email.sendMessage;

public class UserRepo {
    public static void signUp(HttpServletRequest request, HttpServletResponse response) throws MessagingException, ServletException, IOException {
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        System.out.println("email bu  " + email);
        Users users = Users.builder()
                .name(username)
                .email(email)
                .password(password)
                .code(randomCode())
                .role(Role.USER)
                .checkAccount(true)
                .build();
        if (DB.checkEmailUniq(email)) {
            DB.addUser(users);
            sendMessage(users.getCode(), email);
            request.setAttribute("Id", users.getUser_id());
            request.getRequestDispatcher("/auth/check.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "This email has been sent");
            request.getRequestDispatcher("/auth/check.jsp").forward(request, response);
        }
    }


    private static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            str.append(digit);
        }
        return str.toString();
    }
    public static UserRepo userRepo;
    private static UserRepo getInstance() {
        if (userRepo == null) {
            userRepo = new UserRepo();
        }
        return userRepo;
    }

    private UserRepo() {
    }

    public static void signIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<Users>resUser=DB.getUserByEmailAndPassword(email,password);
        if (resUser.isPresent()) {
            var cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userId")) {
                        cookie.setValue(String.valueOf(resUser.get().getUser_id()));
                        cookie.setMaxAge(24 * 60 * 60);
                        cookie.setPath("/");
                        resUser.get().setStatus(true);
                        resp.addCookie(cookie);
                        req.getRequestDispatcher("/cabinet/welcome.jsp").forward(req, resp);
                       return;
                    }
                }
                Cookie cookie = new Cookie("userId", resUser.get().getUser_id().toString());
                cookie.setMaxAge(24 * 60 * 60);
                cookie.setPath("/");
                resUser.get().setStatus(true);
                resp.addCookie(cookie);
                req.getRequestDispatcher("/cabinet/welcome.jsp").forward(req, resp);
                return;
            }
        }
        req.setAttribute("message", "Checking email or password");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
