package com.Kamran.gharKaBawarchi.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Menu {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long menuId;

    private String menuName;

    private double price;

    @ManyToMany(mappedBy = "menuItems")
    private List<Cook> cooks;

    @ManyToMany(mappedBy = "menuItems")
    private List<Booking> booking;

}
