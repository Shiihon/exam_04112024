package app.controllers;

import app.config.AppConfig;
import app.config.HibernateConfig;
import app.daos.TripDAO;
import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.populator.Populator;
import app.security.daos.SecurityDAO;
import app.security.entities.Role;
import app.security.entities.User;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class TripControllerTest {
    private static Javalin app;
    private static EntityManagerFactory emf;

    private static TripDAO tripDAO;
    private static Populator populator;

    private static List<TripDTO> listOfTrips;
    private static List<GuideDTO> listOfGuides;

    private final String BASE_URL = "http://localhost:7000/api";

    @BeforeAll
    static void init() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = AppConfig.startServer(emf);
        populator = new Populator(emf);
        tripDAO = new TripDAO(emf);
    }

    @BeforeEach
    void setUp() {
        Populator populator = new Populator(emf);
        List<Guide> entityListOfGuides = populator.listOfGuides();
        populator.persist(entityListOfGuides);
        List<Trip> entityListOfTrips = populator.listOfTrips();

        // Convert entities to DTOs after persisting
        listOfGuides = entityListOfGuides.stream().map(GuideDTO::new).toList();
        listOfTrips = entityListOfTrips.stream().map(TripDTO::new).toList();
    }

    @AfterEach
    void tearDown() {
        populator.cleanup(Trip.class);
        populator.cleanup(Guide.class);
    }

    @AfterAll
    static void closeDown() {
        AppConfig.stopServer();
    }

    @Test
    void getAll() {
        TripDTO[] trips = given()
                .when()
                .get(BASE_URL + "/trips")
                .then()
                .statusCode(200)
                .extract()
                .as(TripDTO[].class);

        assertThat(trips, arrayWithSize(5));
    }

    @Test
    void getById() {
    }

    @Test
    void addGuideToTrip() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getTripsByCategory() {
    }
}