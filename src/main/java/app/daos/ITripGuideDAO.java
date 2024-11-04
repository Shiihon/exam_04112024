package app.daos;

import app.dtos.TripDTO;

import java.util.Set;

public interface ITripGuideDAO {
    void addGuideToTrip(Long tripId, Long guideId);
    Set<TripDTO> getTripsByGuide(Long guideId);
}
