package com.Kamran.gharKaBawarchi.Respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Kamran.gharKaBawarchi.Entity.FavouriteCook;
import com.Kamran.gharKaBawarchi.Entity.Users;

@Repository
public interface FavouriteCookRepository extends JpaRepository<FavouriteCook,Long>{

    // Find all FavouriteCook entries by user
    List<FavouriteCook> findByUser(Users user);

    // Or, if you only want the cookId list:
    @Query("SELECT f.cookId FROM FavouriteCook f WHERE f.user = :user")
    List<Long> findCookIdsByUser(@Param("user") Users user);


    Optional<FavouriteCook> findByUserAndCookId(Users user, Long cookId);

}
