package com.Kamran.gharKaBawarchi.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.TimeSlot;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.TimeSlotRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SlotGeneratorService {

    @Autowired
    private final CookRepository cookRepo;
    @Autowired
    private final TimeSlotRepository slotRepo;

    public SlotGeneratorService(CookRepository cookRepo, TimeSlotRepository slotRepo) {
        this.cookRepo = cookRepo;
        this.slotRepo = slotRepo;
    }


    @PostConstruct
    public void initOnStartUp(){
        System.out.println("intializing slot geneartion on stsart up");

        List<Cook> cooks=cookRepo.findAll();
        for(Cook cook: cooks){
            generateSlotsForCook(cook);
        }
    }

    /**
     * Generate time slots for all cooks for the next 1 month.
     * Runs every 15 days automatically.
     */
    @Scheduled(cron = "0 0 0 1,16 * ?") // at midnight on the 1st and 16th of every month
    public void autoGenerateSlots() {
        List<Cook> cooks = cookRepo.findAll();
        for (Cook cook : cooks) {
            generateSlotsForCook(cook);
        }
    }

    /**
     * Generate time slots for a single cook for the next month.
     */
    public void generateSlotsForCook(Cook cook) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);

        // existing dates to avoid duplicates
        List<TimeSlot> existing = slotRepo.findByCookAndDateBetweenOrderByDateAscStartTimeAsc(cook, startDate, endDate);
        Set<LocalDate> existingDates = new HashSet<>();
        for (TimeSlot slot : existing) {
            existingDates.add(slot.getDate());
        }

        List<TimeSlot> newSlots = new ArrayList<>();
        LocalTime start = LocalTime.of(6, 0);
        LocalTime end = LocalTime.of(22, 0); // 10 PM

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (!existingDates.contains(date)) {
                LocalTime time = start;
                while (time.isBefore(end)) {
                    LocalTime next = time.plusHours(2);
                    TimeSlot slot = new TimeSlot();
                    slot.setDate(date);
                    slot.setStartTime(time);
                    slot.setEndTime(next);
                    slot.setCook(cook);
                    newSlots.add(slot);
                    time = next;
                }
            }
        }

        if (!newSlots.isEmpty()) {
            slotRepo.saveAll(newSlots);
            System.out.println("Generated " + newSlots.size() + " new slots for cook: " + cook.getCookName());
        }
    }
}
