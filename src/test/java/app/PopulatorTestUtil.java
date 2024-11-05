package app;

import app.entities.Guide;
import app.entities.Trip;
import app.security.entities.Role;
import app.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Set;

public class PopulatorTestUtil {

    private final EntityManagerFactory emf;
    private static List<Trip> listOfTrips;

    public PopulatorTestUtil(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<User> createUsers(List<Role> roles) {
        return List.of(
                new User(
                        "User1",
                        "1234",
                        Set.of(roles.get(0))
                ),
                new User(
                        "User2",
                        "1234",
                        Set.of(roles.get(0))
                ),
                new User(
                        "Admin1",
                        "1234",
                        Set.of(roles.get(1))
                )
        );
    }

    public List<Role> createRoles() {
        return List.of(
                new Role("user"),
                new Role("admin")
        );
    }

    public static List<Guide> listOfGuides() {
        List<Trip> trips = listOfTrips;
        return List.of(
                new Guide(null, "John", "Doe", "+1234567890", "john.doe@example.com", 10,
                        List.of(trips.get(0), trips.get(1))), // Associate with specific trips
                new Guide(null, "Ava", "Smith", "+1987654321", "ava.smith@example.com", 5,
                        List.of(trips.get(1))), // Ensure this guide has a matching trip
                new Guide(null, "Liam", "Brown", "+1012345678", "liam.brown@example.com", 7,
                        List.of(trips.get(2))),
                new Guide(null, "Sophia", "Johnson", "+1123456789", "sophia.johnson@example.com", 12,
                        List.of(trips.get(4))),
                new Guide(null, "Oliver", "Wilson", "+1098765432", "oliver.wilson@example.com", 15,
                        List.of(trips.get(3)))
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