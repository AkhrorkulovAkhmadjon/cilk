package canturoler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repo.CardRepo;

import java.io.IOException;

@WebServlet(urlPatterns = "/cabinet/admin/edit_card_status")
public class EditStatusCard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        String cardId = request.getParameter("cardId");
        CardRepo.changeStatus(status,cardId);
        request.getRequestDispatcher("/cabinet/admin/question.jsp").forward(request, response);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
