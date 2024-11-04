package app.routes;

import app.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
//    private RoomRoutes roomRoutes;
    private SecurityRoutes securityRoutes;

    public Routes(EntityManagerFactory emf) {
//        hotelRoutes = new HotelRoutes(emf);
        securityRoutes = new SecurityRoutes(emf);
    }

    public EndpointGroup getApiRoutes() {
        return () -> {
//            path("/hotel", hotelRoutes.getHotelRoutes());
//            path("/room", roomRoutes.getRoomRoutes());
            path("/", securityRoutes.getSecurityRoutes());
        };
    }
}

