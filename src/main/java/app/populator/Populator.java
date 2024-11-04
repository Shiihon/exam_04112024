package app.populator;

import app.enums.Category;
import app.entities.Guide;
import app.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalTime;
import java.util.List;

public class Populator {
    private EntityManagerFactory emf;


    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Trip> listOfTrips() {
        return List.of(
                new Trip(
                        null,
                        "Mountain safari",
                        500.95,
                        Category.FOREST,
                        LocalTime.of(9, 30, 0),
                        LocalTime.of(15, 0, 0),
                        86.9250,
                        27.9881
                        ),

                new Trip(
                        null,
                        "Sunrise at Mount Fuji",
                        350.75,
                        Category.FOREST,
                        LocalTime.of(4, 30, 0),
                        LocalTime.of(9, 0, 0),
                        138.7274,
                        35.3606
                        ),

                new Trip(
                        null,
                        "Kilimanjaro Wildlife Trek",
                        450.50,
                        Category.FOREST,
                        LocalTime.of(7, 0, 0),
                        LocalTime.of(14, 0, 0),
                        37.3556,
                        -3.0674
                        ),

                new Trip(
                        null,
                        "Denali Base Camp Adventure",
                        620.99,
                        Category.SNOW,
                        LocalTime.of(6, 15, 0),
                        LocalTime.of(18, 30, 0),
                        -151.0074,
                        63.0695
                        ),

                new Trip(
                        null,
                        "Patagonian Peaks Expedition",
                        775.25,
                        Category.FOREST,
                        LocalTime.of(8, 0, 0),
                        LocalTime.of(19, 0, 0),
                        -73.158,
                        -49.273
                        )
        );
    }

    public List<Guide> listOfGuides() {
        return List.of(
                new Guide(
                        null,
                        "John",
                        "Doe",
                        "+1234567890",
                        "john.doe@example.com",
                        10,
                        List.of(listOfTrips().get(0))),

                new Guide(
                        null,
                        "Ava",
                        "Smith",
                        "+1987654321",
                        "ava.smith@example.com",
                        5,
                        List.of(listOfTrips().get(1))),

                new Guide(
                        null,
                        "Liam",
                        "Brown",
                        "+1012345678",
                        "liam.brown@example.com",
                        7,
                        List.of(listOfTrips().get(2))),

                new Guide(
                        null,
                        "Sophia",
                        "Johnson",
                        "+1123456789",
                        "sophia.johnson@example.com",
                        12,
                        List.of(listOfTrips().get(4))),

                new Guide(
                        null,
                        "Oliver",
                        "Wilson",
                        "+1098765432",
                        "oliver.wilson@example.com",
                        15,
                        List.of(listOfTrips().get(3)))

        );
    }

    public void persist(List<?> entities) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            entities.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    public void cleanup(Class<?> entityClass) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM " + entityClass.getSimpleName()).executeUpdate();
            em.getTransaction().commit();
        }
    }
}
