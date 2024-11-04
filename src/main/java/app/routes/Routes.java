package app.routes;

import app.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private TripRoutes tripRoutes;
    private SecurityRoutes securityRoutes;

    public Routes(EntityManagerFactory emf) {
        securityRoutes = new SecurityRoutes(emf);
        tripRoutes = new TripRoutes(emf);
    }

    public EndpointGroup getApiRoutes() {
        return () -> {
            path("/trips", tripRoutes.getTripRoutes());
            path("/", securityRoutes.getSecurityRoutes());
        };
    }
}

