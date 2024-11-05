package app.daos;

import app.dtos.TripDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.enums.Category;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {
    private static EntityManagerFactory emf;

    public TripDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public TripDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);

            if (trip == null) {
                throw new EntityNotFoundException("Trip with id " + id + " not found");
            }
            return new TripDTO(trip);
        }
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);

            return query.getResultList().stream().map(TripDTO::new).collect(Collectors.toList());

        } catch (RollbackException e) {
            throw new RollbackException("Could not get all trips", e);
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        Trip trip = tripDTO.getAsEntity();
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();

            return new TripDTO(trip);

        } catch (EntityExistsException e) { // added more suitable exception.
            throw new EntityExistsException("Trip already exists: " + e.getMessage(), e);

        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating trip : " + e.getMessage(), e);
        }
    }

    @Override
    public TripDTO update(TripDTO tripDTO) {

        Trip trip = tripDTO.getAsEntity();

        try (EntityManager em = emf.createEntityManager()) {
            Trip existingTrip = em.find(Trip.class, trip.getId());

            if (existingTrip == null) {
                throw new EntityNotFoundException("Trip not found");
            }
            em.getTransaction().begin();

            if (trip.getName() != null) {
                existingTrip.setName(trip.getName());
            }
            if (trip.getCategory() != null) {
                existingTrip.setCategory(trip.getCategory());
            }
            if (trip.getCategory() != null) {
                existingTrip.setCategory(trip.getCategory());
            }
            if (trip.getLongitude() != null) {
                existingTrip.setLongitude(trip.getLongitude());
            }
            if (trip.getLatitude() != null) {
                existingTrip.setLatitude(trip.getLatitude());
            }
            if (trip.getPrice() != null) {
                existingTrip.setPrice(trip.getPrice());
            }
            if (trip.getStartTime() != null) {
                existingTrip.setStartTime(trip.getStartTime());
            }
            if (trip.getEndTime() != null) {
                existingTrip.setEndTime(trip.getEndTime());
            }

            em.getTransaction().commit();
            return new TripDTO(existingTrip);

        } catch (RollbackException e) {
            throw new RollbackException(String.format("Unable to update trip, with id: %d : %s", tripDTO.getId(), e.getMessage()));
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {

            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new EntityNotFoundException("Trip with id " + id + " not found");
            }
            em.getTransaction().begin();
            em.remove(trip);
            em.getTransaction().commit();
        }
    }

    @Override //Forgot em.trans.begin
    public void addGuideToTrip(Long tripId, Long guideId) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            if (trip == null) {
                throw new EntityNotFoundException("Trip with id " + tripId + " not found");
            }
            Guide guide = em.find(Guide.class, guideId);
            if (guide == null) {
                throw new EntityNotFoundException("Guide with id " + guideId + " not found");
            }
            guide.addTrip(trip);
            em.merge(guide);
            em.getTransaction().commit();

        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Failed to add trip to guide " + tripId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(Long guideId) {
        try (EntityManager em = emf.createEntityManager()) {

            Guide guide = em.find(Guide.class, guideId);
            if (guide == null) {
                throw new EntityNotFoundException("Guide with id " + guideId + " not found");
            }
            List<Trip> trips = guide.getTrips();

            return trips.stream().map(TripDTO::new).collect(Collectors.toSet());

        } catch (RuntimeException e) {
            throw new RuntimeException("Error fetching trips for guide ID: " + guideId + " - " + e.getMessage(), e);
        }
    }

    //FIXED!
    public List<TripDTO> getTripsByCategory(Category category) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            List<Trip> trips = em.createQuery("SELECT t FROM Trip t WHERE t.category = :category", Trip.class)
                    .setParameter("category", category)
                    .getResultList();

            List<TripDTO> filteredTrips = trips.stream().map(TripDTO::new).collect(Collectors.toList());
            em.getTransaction().commit();
            return filteredTrips;
        }
    }
}
