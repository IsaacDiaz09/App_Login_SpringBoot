package com.isaac.login_app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.isaac.login_app.model.User;
import com.isaac.login_app.repository.UserRepository;
import com.isaac.login_app.service.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserServiceImpl userServiceImpl;

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
		 * Vwrifica que el usuario no haya inicado sesion para que le envie al
		 * login,sino, directamente dentro a la aplicaciok
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

	@PostMapping(path = "/register", consumes = "application/x-www-form-urlencoded")
	public String validaRegistro(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
		// Si se incumple alguna validacion regresa al formulario para que lo intente de
		// nuevo
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		} else {
			try {
				userServiceImpl.createUser(user);
			} catch (Exception e) {
				model.addAttribute("user", user);
				model.addAttribute("validationError", e.getMessage());
				return "register";
			}
		}
		return "register_success";
	}

}
