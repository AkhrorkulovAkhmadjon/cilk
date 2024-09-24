package canturoler;

import dataBase.DB;
import entity.Users;
import enums.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import repo.UserRepo;

import java.io.IOException;
import java.util.Random;

import static convenience.email.Email.sendMessage;

@WebServlet(name = "sign_up", urlPatterns = "/auth/sign_up")
public class SignUp extends HttpServlet {
    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepo.signUp(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/auth/signup.jsp").forward(request, response);
    }


}
