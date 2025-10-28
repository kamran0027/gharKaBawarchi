package com.Kamran.gharKaBawarchi.Respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.TimeSlot;


public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long>{

    List<TimeSlot> findByCookAndDateBetweenOrderByDateAscStartTimeAsc(Cook cook,LocalDate start,LocalDate end);
}
