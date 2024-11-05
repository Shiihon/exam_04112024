package app.config;

import app.security.controllers.AccessController;
import app.security.routes.SecurityRoutes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import app.controllers.ExceptionController;
import app.exceptions.ApiException;
import app.routes.Routes;
import app.util.ApiProps;
import app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppConfig {

    private static Routes routes;
    private static final ExceptionController exceptionController = new ExceptionController();
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(AppConfig.class); //logger ansvarlig for appconfig filen.
    private static Javalin app;
    private static SecurityRoutes securityRoutes;

    private static void configuration(JavalinConfig config) {
        //Server
        config.router.contextPath = ApiProps.API_CONTEXT; // Base path for all routes.

        //Plugin
        config.bundledPlugins.enableRouteOverview("/routes");
        config.bundledPlugins.enableDevLogging();

        // Routes
        config.router.apiBuilder(routes.getApiRoutes());

        // Security
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
    }

    //Exceptions
    private static void exceptionContext(Javalin app) {
        app.exception(ApiException.class, exceptionController::apiExceptionHandler);

        app.exception(Exception.class, exceptionController::exceptionHandler);
    }

    public static Javalin startServer(EntityManagerFactory emf) {
        logger.info("Starting the Javalin server...");
        routes = new Routes(emf);
        securityRoutes = new SecurityRoutes(emf);

        app = Javalin.create(AppConfig::configuration);

        AccessController accessController = new AccessController(emf);

        app.beforeMatched(accessController::accessHandler); // metoden håndtere access.
        app.start(ApiProps.PORT);
        exceptionContext(app);
        return app;
    }

    public static void stopServer() {
        app.stop();
    }
}
