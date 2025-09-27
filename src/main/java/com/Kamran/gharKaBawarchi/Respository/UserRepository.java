package com.Kamran.gharKaBawarchi.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kamran.gharKaBawarchi.Entity.Users;



public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUserEmailIgnoreCase(String userEmail);
    

}
