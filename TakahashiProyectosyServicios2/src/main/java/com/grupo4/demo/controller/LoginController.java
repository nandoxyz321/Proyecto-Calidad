package com.grupo4.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@RequestMapping("")
	public String index(@RequestParam(value = "error", required = false) String error,
			Model model,Principal principal, RedirectAttributes flash) {
		if(principal!=null) {
			flash.addFlashAttribute("info","ya ha iniciado sesion");
			return "redirect:/principal/";
		}
		if(error != null) {
			model.addAttribute("error","Error usuario/contraseña incorrecta");
		}
		return "login/index";
	}
}
