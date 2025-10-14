package com.Kamran.gharKaBawarchi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.Menu;
import com.Kamran.gharKaBawarchi.Respository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenuByCook(Long cookId) {
        return menuRepository.findMenusByCookId(cookId);
        
    }

    public List<Menu> getAllMenuItems() {
        return menuRepository.findAll();
    }
}
