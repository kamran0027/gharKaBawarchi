package com.Kamran.gharKaBawarchi.Entity;

import java.util.ArrayList;
import java.util.List;

import com.Kamran.gharKaBawarchi.Entity.Enum.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private String cookEmail;
    private String cookPassword;
    private String contactInfo;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "cook")
    private List<Booking> bookings;

    @OneToMany(mappedBy ="cook", cascade = CascadeType.ALL, orphanRemoval =true)
    private List<TimeSlot> timeSlots=new ArrayList<>();
    


    @ManyToMany
    @JoinTable(
        name = "cook_menu",
        joinColumns = @JoinColumn(name = "cook_id"),
        inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<Menu> menuItems;


    public String getRoleString(){
        return role!=null?role.name():null;
    }


}
