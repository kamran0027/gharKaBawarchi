package com.Kamran.gharKaBawarchi.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long cityId;

    @Column(unique =true, nullable = false)
    String cityName;

    @OneToMany(mappedBy = "city")
    private List<Users> users;

    @OneToMany(mappedBy = "city")
    private List<Cook> cooks;

}
