package com.Kamran.gharKaBawarchi.Entity;

import java.time.LocalDateTime;
import java.util.List;

import com.Kamran.gharKaBawarchi.Entity.Enum.BookingStatus;

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
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    private Double totalAmount;
    private String customerName;
    private int numberOfPeople;

    private LocalDateTime bookingTIme;

    @ManyToMany()
    @JoinTable(
        name = "booking_food_item",
        joinColumns = @JoinColumn(name="booking_id"),
        inverseJoinColumns = @JoinColumn(name="menu_id")
    )
    private List<Menu> menuItems;

    @ManyToOne()
    @JoinColumn(name = "cook_id")
    private Cook cook;

    @OneToOne()
    @JoinColumn(name = "slot_id")
    private TimeSlot timeSlot;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users users;



}
