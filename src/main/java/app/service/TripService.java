package app.service;

import app.dtos.TripWithGuideInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class TripService {
    private static EntityManagerFactory emf;

    public TripService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TripWithGuideInfoDTO getTripWithGuide(Long tripId) {
        try (EntityManager em = emf.createEntityManager()) {
            // JPQL query using entity names and field names
            String sql = "SELECT t.id, t.name, t.price, " +
                    "t.category, t.startTime, " +
                    "t.endTime, g.id, g.firstName, " +
                    "g.lastName, g.phone, g.email, g.yearsOfExperience " +
                    "FROM Trip t " + // Make sure this table name is correct
                    "INNER JOIN Guide g ON t.guideId = g.id " + // Make sure this column name is correct
                    "WHERE t.id = :tripId";

            TypedQuery<TripWithGuideInfoDTO> query = em.createQuery(sql, TripWithGuideInfoDTO.class);
            query.setParameter("tripId", tripId);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle no result case
        }

    }
}
