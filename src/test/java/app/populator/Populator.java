package app.populator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Plant;
import org.example.entities.Reseller;
import org.example.enums.PlantType;

import java.util.HashSet;
import java.util.List;

public class Populator {

    private final EntityManagerFactory emf;

    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Plant> create5Plants() {
        return List.of(
                Plant.builder()
                        .plantType(PlantType.ROSE)
                        .name("Albertine")
                        .maxHeight(400)
                        .price(199.50)
                        .build(),
                Plant.builder()
                        .plantType(PlantType.BUSH)
                        .name("Aronia")
                        .maxHeight(200)
                        .price(169.50)
                        .build(),
                Plant.builder()
                        .plantType(PlantType.FRUITANDBERRIES)
                        .name("AromaApple")
                        .maxHeight(350)
                        .price(399.50)
                        .build(),
                Plant.builder()
                        .plantType(PlantType.RHODODENDRON)
                        .name("Astrid")
                        .maxHeight(40)
                        .price(269.50)
                        .build(),
                Plant.builder()
                        .plantType(PlantType.ROSE)
                        .name("The DarkLady")
                        .maxHeight(100)
                        .price(199.50)
                        .build()
        );
    }

    public List<Reseller> create3Resellers() {
        return List.of(
                Reseller.builder()
                        .name("Lyngby Plantecenter")
                        .address("Firskovvej 18")
                        .phone("33212334")
                        .listOfPlants(new HashSet<>())
                        .build(),
                Reseller.builder()
                        .name("Glostrup Planter")
                        .address("Tværvej 35")
                        .phone("32233232")
                        .listOfPlants(new HashSet<>())
                        .build(),
                Reseller.builder()
                        .name("Holbæk Planteskole")
                        .address("Stenhusvej 49")
                        .phone("59430945")
                        .listOfPlants(new HashSet<>())
                        .build()
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
