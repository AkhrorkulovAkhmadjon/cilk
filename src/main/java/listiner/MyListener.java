package listiner;

import dataBase.DB;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDateTime;
import java.util.UUID;

@WebListener
public class MyListener implements ServletContextListener {
    UUID id = UUID.randomUUID();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("korkad");
        DB.entityManagerFactory = Persistence.createEntityManagerFactory("DATA_BASE");
        DB.entityManager=DB.entityManagerFactory.createEntityManager();
        DB.checkUsers();
        ServletContextListener.super.contextInitialized(sce);
    }
}
