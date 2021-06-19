package com.grupo4.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo4.demo.models.entity.Trabajador;
import com.grupo4.demo.models.entity.service.ITrabajadorService;


@Controller
public class LoginController {
	
	@Autowired
	private ITrabajadorService trabajadorService;

	@RequestMapping("/")
	public String index(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value="logout", required = false) String logout ,
			Model model,Principal principal, RedirectAttributes flash) {
		if(principal!=null) {
			flash.addFlashAttribute("info","ya ha iniciado sesion");
			return "redirect:/principal/";
		}
		if(error != null) {
			model.addAttribute("error","Error usuario/contraseña incorrecta");
		}
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		return "login/index";
	}
	
	@RequestMapping(value = "login/peticion", method = RequestMethod.GET )
	public String recuperar() {
		System.out.println("llegó acá");
		return "login/peticion";
	}
	
	@RequestMapping(value = "/login/peticion",method = RequestMethod.POST)
	public String recuperar(@RequestParam("email") String Email, @RequestParam("dni") String Dni, Model model,RedirectAttributes flash) {
		Trabajador trabajador = trabajadorService.findByEmailAndDni(Email, Dni);
		if(trabajador != null) {
			System.out.println("aqui entró");
			flash.addFlashAttribute("success","peticion enviada al administrador");
			return "redirect:/";
		}else {
			System.out.println("aqui tambien entró");
			model.addAttribute("error", "no se encontraron datos relacionados o los datos son incorrectos");
		}
		return "login/peticion";
	}
}
