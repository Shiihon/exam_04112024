package app.routes;

import app.controllers.TripController;
import app.daos.TripDAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {
    private static TripController tripController;
    private static TripDAO tripDAO;

    public TripRoutes (EntityManagerFactory emf){
        tripDAO = new TripDAO(emf);
        tripController = new TripController(tripDAO);
    }

    public EndpointGroup getTripRoutes() {
        return () -> {
            get("/", tripController::getAll);
            get("/{id}", tripController::getById);
            get("/category", tripController::getTripsByCategory);
            post("/", tripController::create);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip);
        };
    }

}
