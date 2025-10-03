package com.Kamran.gharKaBawarchi.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.BookingStatus;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Users;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUsers(Users user);
    List<Booking> findByCook(Cook cook);
    List<Booking> findByStatus(BookingStatus status);

    @Query("SELECT DISTINCT b FROM Booking b LEFT JOIN FETCH b.menuItems WHERE b.users.userId = :userId")
    List<Booking> findBookingsByUserId(Long userId);

    @Query("SELECT DISTINCT b FROM Booking b LEFT JOIN FETCH b.menuItems WHERE b.cook.cookId = :cookId")
    List<Booking> findBookingsByCookId(Long cookId);



}
