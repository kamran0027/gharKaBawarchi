package com.Kamran.gharKaBawarchi.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Service.CookService;

@Controller
@RequestMapping("/admin/cooks")
public class AdminCookController {

    @Autowired
    private CookService cookService;

    @GetMapping("")
    public String showCookManagementPage(@RequestParam(defaultValue = "0") int page, Model model) {

        Page<Cook> cookPage = cookService.getCookByPage(page);
        model.addAttribute("page", page);
        model.addAttribute("cooks", cookPage.getContent());
        model.addAttribute("totalPage",cookPage.getTotalPages());
        return "admin_cook";
    }
}
