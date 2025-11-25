package com.Kamran.gharKaBawarchi.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kamran.gharKaBawarchi.Entity.Menu;

@Repository
public interface MenuRepository extends
JpaRepository<Menu,Long>{
    // List<Menu> findByCooks(Cook cook);
    // List<Menu> findByCook_cookId(Long id);

    // @Query("SELECT m FROM Menu m JOIN m.cooks c WHERE c.id = :cookId")
    // List<Menu> findMenusByCookId(Long cookId);

    List<Menu> findByCookCookId(Long cookId);

    List<Menu> findByCookIsNull();

}
