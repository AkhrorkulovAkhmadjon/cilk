package dataBase;

import entity.Card;
import entity.History;
import entity.UserCard;
import entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import repo.UserRepo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DB {
    public static EntityManagerFactory entityManagerFactory;
    public static EntityManager entityManager;

    public static boolean checkEmailUniq(String email) {
        entityManager.getTransaction().begin();
        List<Users> selectCFromUsersC = entityManager.createQuery("select c from Users c").getResultList();
        entityManager.getTransaction().commit();
        Optional<Users> first = Optional.empty();
        if (selectCFromUsersC.size() != 0) {
            first = selectCFromUsersC.stream().filter(user -> email.equals(user.getEmail())).findFirst();
        }
        return first.isEmpty();
    }

    @SneakyThrows
    public static void checkUsers() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true) {
                entityManager.getTransaction().begin();
                int deletedCount = entityManager.createQuery("DELETE FROM Users u WHERE u.status IS NULL")
                        .executeUpdate();
                entityManager.getTransaction().commit();
                System.out.println("Deleted " + deletedCount + " users.");
                try {
                    Thread.sleep(6 * 10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        executor.shutdown();
    }

    public static Boolean checkEmailCode(String code, String usersId) {
        UUID id = UUID.fromString(usersId);
        entityManager.getTransaction().begin();
        try {
            List<Users> resUsers = entityManager.createQuery("SELECT u FROM Users u WHERE u.user_id = :usersId", Users.class)
                    .setParameter("usersId", id)
                    .getResultList();
            entityManager.getTransaction().commit();
            if (resUsers.isEmpty()) {
                return false;
            }
            return resUsers.get(0).getCode().equals(code);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public static Optional<Users> getUserById(String usersId) {
        UUID id = UUID.fromString(usersId);
        entityManager.getTransaction().begin();
        Users resUser = entityManager.find(Users.class, id);
        entityManager.getTransaction().commit();
        if (resUser == null) {
            return Optional.empty();
        }
        return Optional.of(resUser);
    }

    public static Optional<Users> getUserByCookies(HttpServletRequest req) {
        Optional<Cookie> userId = Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals("userId")).findFirst();
        if (userId.isPresent()) {
            String id = userId.get().getValue();
            Optional<Users> userById = getUserById(id);
            if (userById.isPresent()) {
                return userById;
            }
        }
        return Optional.empty();
    }

    public static boolean checkUniqCardNumber(String num) {
        entityManager.getTransaction().begin();
        boolean isUnique = false;
        try {
            Long count = (Long) entityManager.createQuery("SELECT COUNT(t) FROM Card t WHERE t.cardNumber = :num")
                    .setParameter("num", num)
                    .getSingleResult();
            isUnique = (count == 0);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return isUnique;
    }

    public static void updateUsers(Users user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public static void addCard(Card card) {
        entityManager.getTransaction().begin();
        entityManager.persist(card);
        entityManager.getTransaction().commit();
    }


    public static Optional<Card> getCardByCardNumber(HttpServletRequest req, HttpServletResponse resp, String cardNumber, String password) throws ServletException, IOException {
        entityManager.getTransaction().begin();
        List<Card> resultList = entityManager.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :cardNumber", Card.class)
                .setParameter("cardNumber", cardNumber).getResultList();
        entityManager.getTransaction().commit();
        if (resultList.isEmpty()) {
            req.setAttribute("mes", "Karta topilmadi");
            req.getRequestDispatcher("/errorpage.jsp").forward(req, resp);
            return Optional.empty();
        }
        Card card = resultList.get(0);
        boolean equals = Objects.equals(card.getPasswordCard(), password);
        if (equals) {
            boolean res = Objects.equals("working", card.getCardStatus().name());
            if (res) {
                return Optional.of(card);
            }
            req.setAttribute("mes", "Kartada cheklov mavjud");
            req.getRequestDispatcher("/errorpage.jsp").forward(req, resp);
        }
        return Optional.empty();
    }

    public static Optional<List<Card>> getCardById(UUID userId) {
        entityManager.getTransaction().begin();
        List<Card> cards = entityManager.createQuery("SELECT c FROM Card c WHERE c.id = :userId", Card.class)
                .setParameter("userId", userId)
                .getResultList();
        entityManager.getTransaction().commit();
        if (cards.size() == 0) {
            return Optional.empty();
        }
        List<Card> resultList = new ArrayList<>();
        for (Card card : cards) {
            if (card.getCardStatus().name().equals("working")) {
                resultList.add(card);
            }
        }
        if (resultList.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(resultList);
    }

    public static Optional<List<Users>> getAllUsers() {
        entityManager.getTransaction().begin();
        List<Users> selectUFromUsersU = entityManager.createQuery("SELECT u FROM Users u", Users.class).getResultList();
        entityManager.getTransaction().commit();
        if (selectUFromUsersU.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(selectUFromUsersU);
    }

    public static void addUser(Users users) {
        entityManager.getTransaction().begin();
        entityManager.persist(users);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public static Optional<Users> getUserByEmailAndPassword(String email, String password) {
        entityManager.getTransaction().begin();
        List<Users> resultList = entityManager.createQuery("select c from Users c where c.email = :email and c.password = :password ", Users.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
        entityManager.getTransaction().commit();
        if (resultList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(resultList.get(0));

    }

    public static Optional<Card> getByCardNumber(String cardNumber) {
        entityManager.getTransaction().begin();
        var cardList = entityManager.createQuery("select c from Card c where c.cardNumber = :cardNumber", Card.class)
                .setParameter("cardNumber", cardNumber)
                .getResultList();
        entityManager.getTransaction().commit();
        if (cardList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cardList.get(0));
    }

    public static boolean addHistory(String forCardNumber, String toCardNumber, String sum) {
        boolean result = false;
        var forCard = DB.getByCardNumber(forCardNumber);
        var toCard = DB.getByCardNumber(toCardNumber);
        if (forCard.isPresent() && toCard.isPresent()) {
            Double resSum = Double.valueOf(sum);
            if (forCard.get().getBalance() - resSum >= 0) {
                forCard.get().setBalance(forCard.get().getBalance() - resSum);
                toCard.get().setBalance(toCard.get().getBalance() + resSum);
                result = true;
            }
        }
        if (result) {
            mergeCard(forCard.get());
            mergeCard(toCard.get());
        }
        createHistoryTable(forCard.get(), toCard.get(), sum, result);
        return result;

    }

    private static void createHistoryTable(Card card, Card card1, String sum, boolean result) {
        Double resSum = Double.valueOf(sum);
        String str = "Not successful";
        if (result) {
            str = "successful";
        }
        History history = new History(str, card.getCardNumber(),
                card1.getCardNumber(), resSum, card.getBalance());
        entityManager.getTransaction().begin();
        entityManager.persist(history);
        entityManager.getTransaction().commit();
    }

    private static String forCardNumber(String cardNumber) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cardNumber.length(); i++) {
            if (i < 5 || 12 < i) {
                builder.append(cardNumber.charAt(i));
            }
            builder.append('*');
        }
        return builder.toString();
    }

    public static void mergeCard(Card card) {
        entityManager.getTransaction().begin();
        entityManager.merge(card);
        entityManager.getTransaction().commit();
    }

    public static List<History> getCardHistoryByCardNumber(String cardNumber) {
        entityManager.getTransaction().begin();
        var cardNumber1 = entityManager.createQuery("select h from History h where h.forCard = :cardNumber", History.class)
                .setParameter("cardNumber", cardNumber)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        var resultList = entityManager.createQuery("select h from History h where h.toCard = :cardNumber", History.class)
                .setParameter("cardNumber", cardNumber)
                .getResultList();
        entityManager.getTransaction().commit();
        List<History> historyList = new ArrayList<>();
        historyList.addAll(cardNumber1);
        historyList.addAll(resultList);
        return historyList;
    }

    public static Optional<List<Card>> getCardByStatus() {
        entityManager.getTransaction().begin();
        var cardList = entityManager.createQuery("select c from Card c where c.cardStatus = 'waiting'", Card.class)
                .getResultList();
        entityManager.getTransaction().commit();
        if (cardList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cardList);

    }

    public static Users getUserForCard(UUID cardId) {
        Optional<List<Users>> allUsers = getAllUsers();
        if (allUsers.isPresent()) {
            var users = allUsers.get();
            for (Users user : users) {
                for (Card card : user.getCards()) {
                    if (card.getId().equals(cardId)) {
                        return user;
                    }
                }
            }
        }
        return null;
    }

    public static void deleteCard(Card card) {
        entityManager.getTransaction().begin();
        entityManager.remove(card);
        entityManager.getTransaction().commit();
    }
}


