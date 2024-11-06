package app.routes;

import app.config.AppConfig;
import app.config.HibernateConfig;
import app.daos.GuideDAO;
import app.daos.TripDAO;
import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.enums.Category;
import app.populator.Populator;
import app.service.TripService;
import io.javalin.Javalin;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class TripRoutesTest {
    private static Javalin app;
    private static EntityManagerFactory emf;

    private static TripDAO tripDAO;
    private static GuideDAO guideDAO;
    private static Populator populator;
    private static TripService tripService;

    private static List<TripDTO> listOfTrips;
    private static List<GuideDTO> listOfGuides;

    private final String BASE_URL = "http://localhost:7000/api";

    @BeforeAll
    static void init() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = AppConfig.startServer(emf);
        tripService = new TripService(emf);
        populator = new Populator(emf);
        guideDAO = new GuideDAO(emf);
        tripDAO = new TripDAO(emf);
    }

    @BeforeEach
    void setUp() {
        List<Guide> entityListOfGuides = populator.listOfGuides();
        List<Trip> entityListOfTrips = populator.listOfTrips();

        populator.persist(entityListOfTrips);
        populator.persist(entityListOfGuides);

        //Convert entities to DTOs after persisting
        listOfGuides = entityListOfGuides.stream().map(GuideDTO::new).toList();
        listOfTrips = entityListOfTrips.stream().map(TripDTO::new).toList();

        System.out.println(listOfTrips);
        System.out.println(listOfGuides);
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

        assertThat(trips, arrayWithSize(10)); //(with the guides)
    }

    @Test
    void getById() {
//        Long expectedId = listOfTrips.get(0).getId();
//
//            TripDTO actual = given()
//                            .when()
//                            .get(BASE_URL + "/trips/{tripId}", expectedId)
//                            .then()
//                            .log().all()
//                            .statusCode(200)
//                            .extract()
//                            .as(TripDTO.class);
//            assertThat(actual.getId(), is(equalTo(expectedId)));
    }

    @Test
    void addGuideToTrip() {
        Long tripId = listOfTrips.get(0).getId();
        Long guideId = listOfGuides.get(0).getId();


        given()
                .when()
                .put(BASE_URL + "/trips/{tripId}/guides/{guideId}", tripId, guideId)
                .then()
                .statusCode(200)
                .body(equalTo("Guide added to trip successfully."));

        GuideDTO updatedGuide = guideDAO.getById(guideId);
        assertNotNull(updatedGuide);
        assertTrue(updatedGuide.getTripsDTO().stream().anyMatch(t -> t.getId().equals(tripId)));
    }

    @Test
    void create() {
        TripDTO expected = new TripDTO(
                null,
                "Test name",
                500.00,
                Category.SNOW,
                "10:00",
                "20:00",
                2.00,
                3.00
        );
        TripDTO actual = given()
                .contentType(ContentType.JSON)
                .body(expected)
                .when()
                .post(BASE_URL + ("/trips/"))
                .then()
                .statusCode(201)
                .extract()
                .as(TripDTO.class);

        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getCategory(), is(expected.getCategory()));
        assertThat(actual.getStartTime(), is(expected.getStartTime()));
    }

    @Test
    void update() {
        TripDTO expected = listOfTrips.get(2);
        expected.setName("New testName");

        tripDAO.update(expected);

        TripDTO actual = given()
                .when()
                .contentType(ContentType.JSON)
                .body(expected)
                .put(BASE_URL + "/trips/{id}", expected.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(TripDTO.class);

        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getCategory(), is(expected.getCategory()));
    }

    @Test
    void delete() {
        //OBS problemer pga. TripService.
    }

    @Test
    void getTripsByCategory() {
        TripDTO expected = listOfTrips.get(0);
        String category = expected.getCategory().name();

        TripDTO[] actual = given()
                .when()
                .get(BASE_URL + "/trips/category/{category}", category)
                .then()
                .statusCode(200)
                .extract()
                .as(TripDTO[].class);

        assertThat(actual, arrayWithSize(greaterThan(0))); // Check if the array is not empty
        assertThat(actual[0].getCategory().name(), is(category));
    }
}