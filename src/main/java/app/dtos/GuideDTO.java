package app.dtos;

import app.entities.Guide;
import app.entities.Trip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO {
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String phone;
    private String email;
    @JsonProperty("years_of_experience")
    private int yearsOfExperience;
    private List<TripDTO> tripsDTO;

    public GuideDTO(Guide guide) {
        this.id = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.phone = guide.getPhone();
        this.email = guide.getEmail();
        this.yearsOfExperience = guide.getYearsOfExperience();
        this.tripsDTO = guide.getTrips().stream().map(TripDTO::new).collect(Collectors.toList());
    }

//    // Custom getter to return years with the appropriate string
//    @JsonProperty("years_of_experience")
//    public String getYearsOfExperienceString() {
//        return yearsOfExperience == 1 ? "1 year" : yearsOfExperience + " years";
//    }

    @JsonIgnore
    public Guide getAsEntity(){
        List<Trip> tripEntities;
        if(this.tripsDTO != null){
            tripEntities = this.tripsDTO.stream()
                    .map(TripDTO::getAsEntity)
                    .collect(Collectors.toList());
        } else {
            tripEntities = new ArrayList<>();
        }

        return Guide.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .phone(this.phone)
                .email(this.email)
                .yearsOfExperience(this.yearsOfExperience)
                .trips(tripEntities)
                .build();
    }
}
