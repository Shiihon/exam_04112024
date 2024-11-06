package app.service;

import app.dtos.TripWithGuideInfoDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.exceptions.ApiException;
import jakarta.persistence.*;

public class TripService {
    private static EntityManagerFactory emf;

    public TripService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TripWithGuideInfoDTO getTripWithGuide(Long tripId) {
        try (EntityManager em = emf.createEntityManager()) {
            //query fetches the guide based on their trip id in their list.
            String jpql = "SELECT g FROM Guide g JOIN g.trips t WHERE t.id = :tripId";
            // jpql (Java Persistence Query Language), operates on object instead of directly on db schema.
            Guide guide = em.createQuery(jpql, Guide.class)
                    .setParameter("tripId", tripId)
                    .getSingleResult();

            // Gets the specific trip details for the matching trip ID
            Trip trip = guide.getTrips().stream()
                    .filter(t -> t.getId().equals(tripId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

            // mapping to DTO.
            return new TripWithGuideInfoDTO(trip, guide);
        } catch (NoResultException e) {
            throw new ApiException(404, "No guide found for the given trip ID.");

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, "Trip not found in the guide's list.");

        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid trip ID format.");

        } catch (Exception e) {
            throw new ApiException(500, "Internal server error: " + e.getMessage());
        }
    }
}
