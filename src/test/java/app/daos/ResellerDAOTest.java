package app.daos;

import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.dtos.PlantDTO;
import org.example.dtos.ResellerDTO;
import org.example.entities.Plant;
import org.example.entities.Reseller;
import org.example.populator.Populator;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

class ResellerDAOTest {

    private static EntityManagerFactory emfTest;
    private static List<PlantDTO> listOfPlants;
    private static List<ResellerDTO> listOfResellers;
    private static ResellerDAO resellerDAO;
    private static Populator populator;

    @BeforeAll
    static void setUpBeforeClass() {
        emfTest = HibernateConfig.getEntityManagerFactoryForTest();
        populator = new Populator(emfTest);
        resellerDAO = new ResellerDAO(emfTest);
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
    void getAll(){
        List<ResellerDTO> expected = new ArrayList(listOfResellers);
        List<ResellerDTO> actual = resellerDAO.getAll().stream().toList();

        assertThat(actual, hasSize(expected.size()));
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void getById() {
        ResellerDTO expected = listOfResellers.get(0);
        ResellerDTO actual = resellerDAO.getById(expected.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create() {
        ResellerDTO resellerDTO = new ResellerDTO(null,"Test name", "Test address", "phoneNumber", Set.of());

        ResellerDTO expected = resellerDAO.create(resellerDTO);
        ResellerDTO actual = resellerDAO.getById(expected.getId());

        Assertions.assertEquals(expected, actual);
    }
}