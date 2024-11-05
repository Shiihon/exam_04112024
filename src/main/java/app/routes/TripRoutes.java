package app.routes;

import app.controllers.TripController;
import app.daos.TripDAO;
import app.security.enums.Role;
import app.service.TripService;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {
    private static TripController tripController;
    private static TripDAO tripDAO;
    private static TripService tripService;

    public TripRoutes (EntityManagerFactory emf){
        tripDAO = new TripDAO(emf);
        tripService = new TripService(emf);
        tripController = new TripController(tripDAO, tripService);
    }

    public EndpointGroup getTripRoutes() {
        return () -> {
            get("/", tripController::getAll, Role.ANYONE);
            get("/{id}", tripController::getById, Role.USER, Role.ANYONE);
            get("/category/{category}", tripController::getTripsByCategory, Role.USER);
            post("/", tripController::create, Role.ADMIN);
            put("/{id}", tripController::update, Role.ADMIN);
            delete("/{id}", tripController::delete, Role.ADMIN);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip, Role.ADMIN);
        };
    }

}
