package canturoler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repo.CardRepo;
import java.io.IOException;


@WebServlet(name = "createCard", urlPatterns = "/cabinet/create_card")
public class CreateCard extends HttpServlet {
    CardRepo cardRepo= CardRepo.getInstance();
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cardRepo.addCardData(req, resp);
        req.getRequestDispatcher("/cabinet/welcome.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


}
