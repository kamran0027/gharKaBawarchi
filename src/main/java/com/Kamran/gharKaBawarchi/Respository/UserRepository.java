package com.Kamran.gharKaBawarchi.Respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kamran.gharKaBawarchi.Entity.Users;


public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByUserEmail(String userEmail);
    

}
