package com.Kamran.gharKaBawarchi.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Users;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUsers(Users user);
    List<Booking> findByCook(Cook cook);

}
