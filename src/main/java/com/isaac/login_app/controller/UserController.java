package com.isaac.login_app.controller;

import java.util.List;

import javax.validation.Valid;

import com.isaac.login_app.model.User;
import com.isaac.login_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        /**
         * Vwrifica que el usuario no haya inicado sesion para que le envie al login,sino, 
         * directamente dentro a la aplicaciok
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/list_users";
    }

    @GetMapping("/list_users")
    public String viewUsers(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping(path = "register/save", consumes = "application/x-www-form-urlencoded")
    public String validaRegistro(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        return "register_success";
    }

}
