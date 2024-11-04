package app.daos;

import app.dtos.GuideDTO;
import app.entities.Guide;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

public class GuideDAO implements IDAO<GuideDTO> {
    private static EntityManagerFactory emf;

    public GuideDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public GuideDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);

            if (guide == null) {
                throw new EntityNotFoundException("guide with id " + id + " not found");
            }
            return new GuideDTO(guide);
        }
    }

    @Override
    public List<GuideDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);

            return query.getResultList().stream().map(guide -> new GuideDTO(guide)).collect(Collectors.toList());

        } catch (RollbackException e) {
            throw new RollbackException("Could not get all guides", e);
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        Guide guide = guideDTO.getAsEntity();

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            em.persist(guide);
            em.getTransaction().commit();

            return new GuideDTO(guide);

        } catch (RuntimeException e) {
            throw new RuntimeException("Error creating guide : " + e.getMessage(), e);
        }
    }

    @Override
    public GuideDTO update(GuideDTO guideDTO) {
        Guide guide = guideDTO.getAsEntity();
        try (EntityManager em = emf.createEntityManager()) {
            Guide existingGuide = em.find(Guide.class, guide.getId());

            if (existingGuide == null) {
                throw new EntityNotFoundException("Guide not found");
            }
            em.getTransaction().begin();

            if(guide.getFirstName() != null){
                existingGuide.setFirstName(guide.getFirstName());
            }
            if(guide.getLastName() != null){
                existingGuide.setLastName(guide.getLastName());
            }
            if(guide.getEmail() != null){
                existingGuide.setEmail(guide.getEmail());
            }
            if(guide.getPhone() != null){
                existingGuide.setPhone(guide.getPhone());
            }
            if(guide.getYearsOfExperience() != 0){
                existingGuide.setYearsOfExperience(guide.getYearsOfExperience());
            }
            if(guide.getTrips() != null){
                existingGuide.setTrips(guide.getTrips());
            }

            em.getTransaction().commit();
            return new GuideDTO(existingGuide);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            if (guide == null) {
                throw new EntityNotFoundException("guide with id " + id + " not found");
            }
            em.getTransaction().begin();
            em.remove(guide);
            em.getTransaction().commit();
        }
    }
}
