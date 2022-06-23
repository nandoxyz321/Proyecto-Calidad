package com.grupo4.demo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo4.demo.models.entity.Trabajador;
import com.grupo4.demo.models.entity.DAO.ITrabajadorDAO;
import com.grupo4.demo.models.entity.service.ITrabajadorService;

@Controller
@RequestMapping("/trabajadores")
public class TrabajadorController {
	
	@Autowired
	private ITrabajadorDAO trabajadordao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ITrabajadorService trabajadorservice; 
	
	@Secured({"ROLE_ADMIN","ROLE_SUPER"})
	@RequestMapping(value="/listado")
	public String listar(Model model) {
		model.addAttribute("titulo", "trabajadores registrados");
		model.addAttribute("trabajadores", trabajadorservice.findAll());
		return "trabajadores/listado";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String crear(Model model) {
		Trabajador trabajador = new Trabajador();
		model.addAttribute("titulo", "formulario trabajadores");
		//mostrar contraseña = 1 y ocultar contraseña = 0
		model.addAttribute("pass", 1 );
		model.addAttribute("trabajador", trabajador);
		return "trabajadores/formulario";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/form",method = RequestMethod.POST)
	public String guardar(@Valid Trabajador trabajador, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario trabajadores");
			model.addAttribute("pass", 1 );
			return "trabajadores/formulario";
		}
		String number1= trabajador.getDni();
		int cifra=Integer.parseInt(number1);
		int cifra1=cifra%1000;
		int cifra3= (trabajador.getTelefono())%1000;
		Trabajador trabajadorDNiBuscar = trabajadorservice.findByDni(trabajador.getDni());
		String username= String.valueOf(cifra1).concat(String.valueOf(cifra3));
		if(trabajador.getIdTrabajador() == null) {
			if(trabajadorDNiBuscar != null) {
				model.addAttribute("error", "el dni ya ha sido registrado");
				model.addAttribute("titulo", "formulario trabajadores");
				model.addAttribute("pass", 1 );
				return "trabajadores/formulario";
			}
			trabajador.setUsername(username);
			String contrasenaEncript = passwordEncoder.encode(trabajador.getPassword());
			String recuperarPass = trabajadorservice.codificacionEncrypt(trabajador.getPassword());
			trabajador.setRecuperar(recuperarPass);
			trabajador.setPassword(contrasenaEncript);
			trabajadorservice.save(trabajador);
			return "redirect:/trabajadores/listado";
		}else {
			if(trabajadordao.findByDni(trabajador.getDni()).isPresent()) {
				if (trabajadorDNiBuscar.getIdTrabajador().equals(trabajador.getIdTrabajador())) {
					trabajador.setPassword(trabajadorDNiBuscar.getPassword());
					trabajadorservice.save(trabajador);
					return "redirect:/trabajadores/listado";
				}
				model.addAttribute("error", "el dni ya ha sido registrado");
				model.addAttribute("titulo", "Editar trabajador");
				model.addAttribute("pass", 0 );
				return "trabajadores/formulario";
			}else{
				trabajador.setUsername(username);
				Trabajador trabajadorEncontrado = trabajadorservice.findOne(trabajador.getIdTrabajador());
				trabajador.setPassword(trabajadorEncontrado.getPassword());
				trabajadorservice.save(trabajador);
			}
		}
		return "redirect:/trabajadores/listado";
	}
	
	@Secured( "ROLE_ADMIN")
	@RequestMapping(value="/form/{idTrabajador}")
	public String editar(@PathVariable(value="idTrabajador") Long idTrabajador, Model model){
		Trabajador trabajador = null;
		if(idTrabajador > 0) {
			trabajador = trabajadorservice.findOne(idTrabajador);
			model.addAttribute("pass", 0);
		}else {
			return "redirect:/trabajadores/listado";
		}
		model.addAttribute("titulo", "Editar trabajador");
		model.addAttribute("trabajador", trabajador);
		return "trabajadores/formulario";
	}
	
	@Secured( "ROLE_ADMIN")
	@RequestMapping("/eliminar/{idTrabajador}")
	public String eliminar(@PathVariable(value="idTrabajador") Long idTrabajador, RedirectAttributes flash) {
		if(idTrabajador > 0) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			Trabajador exepcion = trabajadordao.findByUsername(userDetail.getUsername());
			Trabajador buscado = trabajadorservice.findOne(idTrabajador);
			if(exepcion != null) {
				if(exepcion.getIdTrabajador().equals(buscado.getIdTrabajador()) ) {
					flash.addFlashAttribute("error","no se puede eliminar a si mismo");
				}else{
					trabajadorservice.delete(idTrabajador);
				}
			}else {
				flash.addFlashAttribute("error","inicie sesión con su nuevo username");
			}
		}
		return "redirect:/trabajadores/listado";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_SUPER"})
	@RequestMapping("/ver/{idTrabajador}")
	public String ver(@PathVariable(value="idTrabajador") Long idTrabajador, Model model) {
		Trabajador trabajador= null;
		if(idTrabajador > 0 ) {
			trabajador = trabajadorservice.findOne(idTrabajador);
		}else {
			return "redirect:/trabajadores/listado";
		}
		model.addAttribute("titulo", "guias generadas por el trabajador: "+trabajador.getNombre()+" "+trabajador.getApellido());
		model.addAttribute("trabajador", trabajador);
		return "trabajadores/ver";
	}
	
	
	
	
}
