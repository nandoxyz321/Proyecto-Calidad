package com.grupo4.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
		model.addAttribute("pass", 1 );
		model.addAttribute("trabajador", trabajador);
		return "trabajadores/formulario";
	}
	
	@RequestMapping(value="/form",method = RequestMethod.POST)
	public String guardar(@Valid Trabajador trabajador, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario trabajadores");
			model.addAttribute("pass", 1 );
			return "trabajadores/formulario";
		}
		
		//pasando a char para sacar dígitos
		String number1= trabajador.getDni();
		int cifra=Integer.parseInt(number1);
		int cifra1=cifra%1000;
		int cifra3= (trabajador.getTelefono())%1000;
		String username= String.valueOf(cifra1).concat(String.valueOf(cifra3));
				
		//usuario recien creado
		if(trabajador.getIdTrabajador() == null) {
			
			if(trabajadordao.findByDni(trabajador.getDni()).isPresent()) {
				model.addAttribute("error", "el dni ya ha sido registrado");
				model.addAttribute("titulo", "formulario trabajadores");
				model.addAttribute("pass", 1 );
				return "trabajadores/formulario";
			}
			
			//código generado
			trabajador.setUsername(username);
			
			//encriptando contraseña
			String contraseñaEncript = passwordEncoder.encode(trabajador.getPassword());
			
			//guardando contraseña
			trabajador.setPassword(contraseñaEncript);
			
			trabajadorservice.save(trabajador);
		}
		//usuario ya existente
		else {
			if(trabajadordao.findByDni(trabajador.getDni()).isPresent()) {
				System.out.println("es null ?");
				//ojo con ese orelse == "PELIGRO !!!!"
				//genera una excepcion, se debe manejar con try catch
				if (trabajadordao.findByDni(trabajador.getDni()).orElse(null).getIdTrabajador() == trabajador.getIdTrabajador()) {
					System.out.println("llegó aquí");
					trabajadorservice.save(trabajador);
					return "redirect:/trabajadores/listado";
				}
				model.addAttribute("error", "el dni ya ha sido registrado");
				model.addAttribute("titulo", "Editar trabajador");
				model.addAttribute("pass", 0 );
				return "trabajadores/formulario";
				
			}else{
				//código generado
				trabajador.setUsername(username);
				System.out.println("al fin!!");
				trabajadorservice.save(trabajador);
			}
		}
		
		
		return "redirect:/trabajadores/listado";
	}
	
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
	
	@RequestMapping("/eliminar/{idTrabajador}")
	public String eliminar(@PathVariable(value="idTrabajador") Long idTrabajador, RedirectAttributes flash) {
		if(idTrabajador > 0) {
			//obteniendo al ususario en sesion
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			if(trabajadordao.findByUsername(userDetail.getUsername()).getIdTrabajador() == trabajadorservice.findOne(idTrabajador).getIdTrabajador()) {
				flash.addFlashAttribute("error","no se puede eliminar a si mismo");
			}else{
				trabajadorservice.delete(idTrabajador);
			}
		}
		return "redirect:/trabajadores/listado";
	}
	
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
