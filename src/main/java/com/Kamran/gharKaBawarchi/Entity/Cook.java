package com.Kamran.gharKaBawarchi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Cook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cookId;
    private String cookName;
    private String specialization;
    private int experienceYears;
    private double rating;
    private String contactInfo;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;


}
