package app;

import app.config.AppConfig;
import app.config.HibernateConfig;
import app.populator.Populator;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = HibernateConfig.getEntityManagerFactory("guided_trips");
        AppConfig.startServer(emf);

//        //Populate DB
//        Populator populator = new Populator(emf);
//        populator.populateDb();
    }
}