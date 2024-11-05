package app.controllers;

import app.daos.TripDAO;
import app.dtos.TripDTO;
import app.dtos.TripWithGuideInfoDTO;
import app.enums.Category;
import app.exceptions.ApiException;
import app.service.TripService;
import io.javalin.http.Context;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class TripController implements Controller {
    private static TripDAO tripDAO;
    private static TripService tripService;

    public TripController(TripDAO dao, TripService service) {
        tripDAO = dao;
        this.tripService = service;
    }

    @Override
    public void getAll(Context ctx) {
        try {
            List<TripDTO> trips = tripDAO.getAll();

            if (trips.isEmpty()) {
                throw new EntityNotFoundException("No trips were found");
            } else {
                ctx.res().setStatus(200);
                ctx.json(trips);
            }

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));

            TripWithGuideInfoDTO tripWithGuide = tripService.getTripWithGuide(id);


            if (tripWithGuide == null) {
                ctx.res().setStatus(404);
                throw new EntityNotFoundException("trip with id " + id + " could not be found");
            }

            ctx.res().setStatus(200);
            ctx.json(tripWithGuide);

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getTripWithGuide(Context ctx) {
        try {
            Long tripId = Long.parseLong(ctx.pathParam("tripId"));
            TripWithGuideInfoDTO tripWithGuide = tripService.getTripWithGuide(tripId);

            if (tripWithGuide == null) {
                ctx.res().setStatus(404);
                throw new EntityNotFoundException("Trip with id " + tripId + " could not be found");
            }
            ctx.res().setStatus(200);
            ctx.json(tripWithGuide);

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Must be a number.");

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    public void addGuideToTrip(Context ctx) {
        try {
            Long tripId = Long.valueOf(ctx.queryParam("tripId"));
            Long guideId = Long.valueOf(ctx.queryParam("guideId"));

            tripDAO.addGuideToTrip(tripId, guideId);

            ctx.res().setStatus(200);
            ctx.json("Guide added to trip successfully.");

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void create(Context ctx) {
        try {
            TripDTO trip = ctx.bodyAsClass(TripDTO.class);
            TripDTO newTrip = tripDAO.create(trip);

            if (newTrip != null) {
                ctx.res().setStatus(201);
                ctx.json(newTrip);
            } else {
                ctx.res().setStatus(400);
                throw new IllegalArgumentException("trip could not be created");
            }
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TripDTO trip = ctx.bodyAsClass(TripDTO.class);

            trip.setId(id);
            TripDTO updatedTrip = tripDAO.update(trip);

            ctx.res().setStatus(200);
            ctx.json(updatedTrip);

        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Must be a number.");

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, "trip with the given id, could not be found");

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));

            TripDTO trip = new TripDTO();
            trip.setId(id);

            tripDAO.delete(id);
            ctx.res().setStatus(204);

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    public void getTripsByCategory(Context ctx) {
        try {
            String categoryParam = ctx.queryParam("category");
            if (categoryParam == null) {
                throw new IllegalArgumentException("Category parameter is required.");
            }
            Category category = Category.valueOf(categoryParam.toUpperCase());

            List<TripDTO> allTrips = tripDAO.getAll();
            List<TripDTO> filteredTrips = allTrips.stream().filter(t -> t.getCategory().equals(category))
                    .toList();

            if (filteredTrips.isEmpty()) {
                ctx.res().setStatus(404);
                throw new EntityNotFoundException("No trips found for category: " + category);
            }

            ctx.res().setStatus(200);
            ctx.json(filteredTrips);

        } catch (IllegalArgumentException e) {
            throw new ApiException(400, e.getMessage());

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }
}
