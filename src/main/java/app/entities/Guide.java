package app.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name = "guides")
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String phone;
    private String email;
    @Column(name = "years_of_experience")
    private int yearsOfExperience;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "guide_id")
    private List<Trip> trips;

    @Builder
    public Guide(Long id, String firstName, String lastName, String phone, String email, int yearsOfExperience, List<Trip> trips) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
        if(trips != null) {
            this.trips = new ArrayList<>();
            for(Trip t : trips) {
                addTrip(t);
            }
        } else {
            this.trips = new ArrayList<>();
        }
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }
}

