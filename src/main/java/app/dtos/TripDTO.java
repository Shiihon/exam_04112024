package app.dtos;

import app.enums.Category;
import app.entities.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private String name;
    private Double price;
    private Category category;
    @JsonProperty("start_time")
    private LocalTime startTime;
    @JsonProperty("end_time")
    private LocalTime endTime;
    private Double longitude;
    private Double latitude;

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.longitude = trip.getLongitude();
        this.latitude = trip.getLatitude();
    }

    @JsonIgnore
    public Trip getAsEntity(){
        return Trip.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .build();
    }
}
