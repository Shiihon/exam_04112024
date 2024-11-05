package app.dtos;

import app.enums.Category;
import app.entities.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private String name;
    private Double price;
    private Category category;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
    private Double longitude;
    private Double latitude;

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.startTime = trip.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = trip.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
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
                .startTime(LocalTime.parse(this.startTime, DateTimeFormatter.ofPattern("HH:mm")))
                .endTime(LocalTime.parse(this.endTime, DateTimeFormatter.ofPattern("HH:mm")))
                .longitude(this.longitude)
                .latitude(this.latitude)
                .build();
    }
}
