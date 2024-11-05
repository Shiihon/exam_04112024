package app.dtos;

import app.entities.Guide;
import app.entities.Trip;
import app.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "trip_id",
        "trip_name",
        "trip_price",
        "trip_category",
        "start_time",
        "end_time",
        "guide"
}) // specifies the order of the fields in the serialized JSON
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
    private String tripStartTime;
    @JsonProperty("end_time")
    private String tripEndTime;
    private GuideInfo guide;

    public TripWithGuideInfoDTO(Trip trip, Guide guide) {
        this.tripId = trip.getId();
        this.tripName = trip.getName();
        this.tripPrice = trip.getPrice();
        this.tripCategory = trip.getCategory();
        this.tripStartTime = trip.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.tripEndTime = trip.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.guide = new GuideInfo(guide);
    }

    @Data
    public static class GuideInfo {
        private Long guideId;
        private String firstName;
        private String lastName;
        private String phone;
        private String email;
        private int yearsOfExperience;

        public GuideInfo(Guide guide) {
            this.guideId = guide.getId();
            this.firstName = guide.getFirstName();
            this.lastName = guide.getLastName();
            this.phone = guide.getPhone();
            this.email = guide.getEmail();
            this.yearsOfExperience = guide.getYearsOfExperience();
        }
    }
}
