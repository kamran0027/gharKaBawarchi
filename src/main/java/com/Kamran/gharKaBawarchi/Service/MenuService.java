package com.Kamran.gharKaBawarchi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.MenuDto;
import com.Kamran.gharKaBawarchi.Entity.Menu;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    
    @Autowired
    private CookRepository cookRepository;

    public List<Menu> getMenuByCook(Long cookId) {
        return menuRepository.findByCookCookId(cookId);
        
    }
    

    public List<Menu> getAllMenuItems() {
        return menuRepository.findByCookIsNull();
    }

    public boolean saveMenu(MenuDto menuDto){
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            Menu menu;
            System.out.println("*************");
            System.out.println(menuDto.getMenuId());
            // menu=menuRepository.findById(menuDto.getMenuId()).orElse(new Menu());
            if (menuDto.getMenuId()==null) {
                menu=new Menu();
                menu.setCook(cookRepository.findByCookEmail(username).get());
            }
            else{
                menu=menuRepository.findById(menuDto.getMenuId()).get();
            }
            
            
            System.out.println("************************");
            System.out.println(menuDto.getMenuItemName());
            System.out.println(menuDto.getPrice());
            
            menu.setMenuName(menuDto.getMenuItemName());
            menu.setPrice(menuDto.getPrice());
        

            menuRepository.save(menu);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteMenu(Long menuId){
        try {
            menuRepository.deleteById(menuId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
