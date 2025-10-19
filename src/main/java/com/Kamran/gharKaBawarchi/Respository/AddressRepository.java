package com.Kamran.gharKaBawarchi.Respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Kamran.gharKaBawarchi.Entity.Address;
import com.Kamran.gharKaBawarchi.Entity.Users;


public interface AddressRepository extends JpaRepository<Address,Long> {

    Optional<Address> findByUser(Users user);

}
