package repo;

import convenience.Message;
import dataBase.DB;
import convenience.email.Email;
import entity.Card;
import entity.Users;
import enums.CardStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;

public class CardRepo {
    private static CardRepo cardRepo;

    @SneakyThrows
    public static void changeStatus(String status, String cardId) {
        var cardById = DB.getByCardNumber(cardId);
        if (status.equals("information is scarce")){
            DB.deleteCard(cardById.get());
            Email.sendMessage(Message.removeCard(DB.getUserForCard(cardById.get().getId()).getName()),DB.getUserForCard(cardById.get().getId()).getEmail());
        }
        if (cardById.isPresent()) {
            System.out.println(cardById.get().getCardNumber());
            cardById.get().setCardStatus(CardStatus.valueOf(status));
            DB.mergeCard(cardById.get());
        }
    }

    public void addCardData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String date = req.getParameter("date");
            String tel = req.getParameter("tel");
            String passport = req.getParameter("passport_number");
            String address = req.getParameter("address");
            String balance = req.getParameter("balance");
            String password = req.getParameter("password");
            String cardName = req.getParameter("cardName");
            Optional<Users> userByCookies = DB.getUserByCookies(req);
            if (userByCookies.isPresent()) {
                Users user = userByCookies.get();
                user.setAddress(address);
                user.setPassportNumber(passport);
                user.setTelNumber(tel);
                user.setBirthday(date);
                Card card = Card.builder()
                        .balance(Double.parseDouble(balance))
                        .cardNumber(cardRepo.createCardNumber())
                        .cardStatus(CardStatus.waiting)
                        .cardData(cardData())
                        .cardName(cardName)
                        .passwordCard(password)
                        .build();
                DB.addCard(card);
                if (user.getCards()== null) {
                    List<Card> cards = new ArrayList<>();
                    cards.add(card);
                    user.setCards(cards);
                } else {
                    user.getCards().add(card);
                }
                DB.updateUsers(user);
                Email.sendMessage(Message.informationReceived(user.getName()), user.getEmail());
            }
        } catch (Exception e) {
            System.out.println(e);
            req.setAttribute("mes", "Something went wrong");
            req.getRequestDispatcher("/errorpage.jsp").forward(req, resp);
        }
    }

    private String createCardNumber() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            str.append(digit);
        }
        if (DB.checkUniqCardNumber(str.toString())) {
            return str.toString();
        } else {
            createCardNumber();
        }
        return "";
    }

    private String cardData() {
        Random rand = new Random();
        StringBuilder str = new StringBuilder();
        str.append(rand.nextInt(12) + "/" + rand.nextInt(24, 30));
        return str.toString();
    }

    public static CardRepo getInstance() {
        if (cardRepo == null) {
            return cardRepo = new CardRepo();
        }
        return cardRepo;
    }

    private CardRepo() {
    }
}
