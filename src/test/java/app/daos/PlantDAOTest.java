package app.daos;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.example.config.HibernateConfig;
import org.example.dtos.PlantDTO;
import org.example.dtos.ResellerDTO;
import org.example.entities.Plant;
import org.example.entities.Reseller;
import org.example.enums.PlantType;
import org.example.populator.Populator;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

class PlantDAOTest {
    private static EntityManagerFactory emfTest;
    private static List<PlantDTO> listOfPlants;
    private static List<ResellerDTO> listOfResellers;
    private static PlantDAO plantDAO;
    private static Populator populator;

    @BeforeAll
    static void setUpBeforeClass() {
        emfTest = HibernateConfig.getEntityManagerFactoryForTest();
        populator = new Populator(emfTest);
        plantDAO = new PlantDAO(emfTest);
    }

    @BeforeEach
    void setUp() {
        List<Plant> entityListOfPlants = populator.create5Plants();
        List<Reseller> entityListOfResellers = populator.create3Resellers();

        entityListOfResellers.get(0).setListOfPlants(Set.of(entityListOfPlants.get(2), entityListOfPlants.get(3), entityListOfPlants.get(1)));
        entityListOfResellers.get(1).setListOfPlants(Set.of(entityListOfPlants.get(1), entityListOfPlants.get(4), entityListOfPlants.get(2)));
        entityListOfResellers.get(2).setListOfPlants(Set.of(entityListOfPlants.get(1), entityListOfPlants.get(3), entityListOfPlants.get(4)));

        populator.persist(entityListOfPlants);
        populator.persist(entityListOfResellers);

        //Fra entitet til DTO.
        listOfPlants = entityListOfPlants.stream().map(plant -> new PlantDTO(plant)).toList();
        listOfResellers = entityListOfResellers.stream().map(reseller -> new ResellerDTO(reseller)).toList();
    }

    @AfterEach
    void tearDown() {
        populator.cleanup(Reseller.class);
        populator.cleanup(Plant.class);
    }

    @Test
    void getAll() {
        List<PlantDTO> expected = new ArrayList<>(listOfPlants);
        List<PlantDTO> actual = plantDAO.getAll().stream().toList();

        assertThat(actual, hasSize(expected.size()));
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void getById() {
        PlantDTO expected = listOfPlants.get(0);
        PlantDTO actual = plantDAO.getById(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByType() {
        PlantType plantType = PlantType.ROSE;

        List<PlantDTO> expected = listOfPlants.stream().filter(plantDTO -> plantDTO.getPlantType().equals(plantType)).toList();
        List<PlantDTO> actual = plantDAO.getByType(plantType).stream().toList();

        assertThat(actual, hasSize(expected.size()));
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void create() {
        PlantDTO plantDTO = new PlantDTO(null, PlantType.BUSH, "Bushy", 50, 200.0);
        PlantDTO expected = plantDAO.create(plantDTO);
        PlantDTO actual = plantDAO.getById(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deletePlant() {
        PlantDTO expected = listOfPlants.get(0);
        plantDAO.deletePlant(expected.getId());

        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> plantDAO.getById(expected.getId()));
    }

    @Test
    void addPlantToReseller() {
        PlantDTO plant = listOfPlants.get(0);
        ResellerDTO reseller = listOfResellers.get(0);

        ResellerDTO updatedReseller = plantDAO.addPlantToReseller(reseller.getId(), plant.getId());

        Assertions.assertTrue(updatedReseller.getPlants().contains(plant));
    }

    @Test
    void getPlantsByReseller() {
        ResellerDTO reseller = listOfResellers.get(0);

        Set<PlantDTO> expectedPlants = reseller.getPlants();
        Set<PlantDTO> actualPlants = plantDAO.getPlantsByReseller(reseller.getId());

        Assertions.assertEquals(expectedPlants.size(), actualPlants.size());
        Assertions.assertTrue(actualPlants.containsAll(expectedPlants));
    }
}