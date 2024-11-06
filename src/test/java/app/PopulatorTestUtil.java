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