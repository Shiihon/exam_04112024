package app;

import app.config.AppConfig;
import app.config.HibernateConfig;
import app.daos.GuideDAO;
import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.populator.Populator;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static EntityManagerFactory emf;
    private static List<TripDTO> listOfTrips;
    private static List<GuideDTO> listOfGuides;

    public static void main(String[] args) {
        emf = HibernateConfig.getEntityManagerFactory("guided_trips");
        AppConfig.startServer(emf);

//        //populating db
//        Populator populator = new Populator(emf);
//        List<Guide> entityListOfGuides = populator.listOfGuides();
//        populator.persist(entityListOfGuides);
//        List<Trip> entityListOfTrips = populator.listOfTrips();
//
//        // Convert entities to DTOs after persisting
//        listOfGuides = entityListOfGuides.stream().map(GuideDTO::new).toList();
//        listOfTrips = entityListOfTrips.stream().map(TripDTO::new).toList();

    }

}