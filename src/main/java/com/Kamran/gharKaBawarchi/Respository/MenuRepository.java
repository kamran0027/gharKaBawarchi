package com.Kamran.gharKaBawarchi.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Menu;

@Repository
public interface MenuRepository extends
JpaRepository<Menu,Long>{
    List<Menu> findByCook(Cook cook);

}
