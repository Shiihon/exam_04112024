package app.dtos;

import app.entities.Guide;
import app.entities.Trip;
import app.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripWithGuideInfoDTO {
    @JsonProperty("trip_id")
    private Long tripId;
    @JsonProperty("trip_name")
    private String tripName;
    @JsonProperty("trip_price")
    private Double tripPrice;
    @JsonProperty("trip_category")
    private Category tripCategory;
    @JsonProperty("start_time")
    private LocalTime tripStartTime;
    @JsonProperty("end_time")
    private LocalTime tripEndTime;

    @JsonProperty("guide_id")
    private Long guideId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String phone;
    private String email;
    @JsonProperty("years_of_experience")
    private int yearsOfExperience;

    public TripWithGuideInfoDTO(Trip trip, Guide guide) {
        this.tripId = trip.getId();
        this.tripName = trip.getName();
        this.tripPrice = trip.getPrice();
        this.tripCategory = trip.getCategory();
        this.tripStartTime = trip.getStartTime();
        this.tripEndTime = trip.getEndTime();
        this.guideId = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.phone = guide.getPhone();
        this.email = guide.getEmail();
        this.yearsOfExperience = guide.getYearsOfExperience();
    }

    public TripWithGuideInfoDTO(Long tripId, String tripName, Double tripPrice, Category tripCategory, LocalTime tripStartTime, LocalTime tripEndTime, Long guideId, String firstName, String lastName, String phone, String email, int yearsOfExperience) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripPrice = tripPrice;
        this.tripCategory = tripCategory;
        this.tripStartTime = tripStartTime;
        this.tripEndTime = tripEndTime;
        this.guideId = guideId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
    }
}
