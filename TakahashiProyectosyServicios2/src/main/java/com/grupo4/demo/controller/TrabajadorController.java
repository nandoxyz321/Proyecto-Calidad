package com.grupo4.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.grupo4.demo.models.entity.Trabajador;
import com.grupo4.demo.models.entity.service.ITrabajadorService;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadorController {

	//para encriptar
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//contiene los metodos crud∟
	@Autowired
	private ITrabajadorService trabajadorservice;
	
	@RequestMapping(value="/listado")
	public String listar(Model model) {
		model.addAttribute("titulo", "trabajadores registrados");
		model.addAttribute("trabajadores", trabajadorservice.findAll());
		return "trabajadores/listado";
	}
	
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String crear(Model model) {
		Trabajador trabajador = new Trabajador();
		model.addAttribute("titulo", "formulario trabajadores");
		model.addAttribute("trabajador", trabajador);
		return "trabajadores/formulario";
	}
	
	@RequestMapping(value="/form",method = RequestMethod.POST)
	public String guardar(@Valid Trabajador trabajador, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario trabajadores");
			return "trabajadores/formulario";
		}
		
		
		//pasando a char para sacar dígitos
		String number1= trabajador.getDni();
		int cifra=Integer.parseInt(number1);
		int cifra1=cifra%1000;
		int cifra3= (trabajador.getTelefono())%1000;
		
		//código generado
		trabajador.setUsername(String.valueOf(cifra1).concat(String.valueOf(cifra3)));
		
		/*contraseña generada
		String contraseñaGenerada= trabajador.getCorreo().substring(0, 2).concat(trabajador.getDireccion().substring(0, 2)).concat(trabajador.getUsername().substring(0, 2).concat(trabajador.getApellido().substring(0, 1).concat(String.valueOf(cifra3).substring(0, 2))));
		*/
		
		//encriptando contraseña
		String contraseñaEncript = passwordEncoder.encode(trabajador.getPassword());
		
		//guardando contraseña
		trabajador.setPassword(contraseñaEncript);
		
		trabajadorservice.save(trabajador);
		
		return "redirect:/trabajadores/listado";
	}
	
	@RequestMapping(value="/form/{idTrabajador}")
	public String editar(@PathVariable(value="idTrabajador") Long idTrabajador, Model model){
		Trabajador trabajador = null;
		if(idTrabajador > 0) {
			trabajador = trabajadorservice.findOne(idTrabajador);
		}else {
			return "redirect:/trabajadores/listado";
		}
		model.addAttribute("titulo", "Editar trabajador");
		model.addAttribute("trabajador", trabajador);
		return "trabajadores/formulario";
	}
	
	@RequestMapping("/eliminar/{idTrabajador}")
	public String eliminar(@PathVariable(value="idTrabajador") Long idTrabajador) {
		if(idTrabajador > 0) {
			trabajadorservice.delete(idTrabajador);
		}
		return "redirect:/trabajadores/listado";
	}
	
	
	
	
}
