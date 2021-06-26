package com.grupo4.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo4.demo.models.entity.Proveedores;
import com.grupo4.demo.models.entity.DAO.IArticuloDAO;
import com.grupo4.demo.models.entity.DAO.IProveedorDAO;
import com.grupo4.demo.models.entity.service.IProveedoresService;

@Controller
@RequestMapping("/proveedores")
public class ProveedoresController {
	
	@Autowired
	private IArticuloDAO articuloDAO;
	
	@Autowired
	private IProveedorDAO proveedorDAO;

	@Autowired
	private IProveedoresService proveedoresService;
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC","ROLE_SUPER"})
	@RequestMapping(value="/listado")
	public String listar(Model model) {
		model.addAttribute("titulo", "Proveedores registrados");
		model.addAttribute("proveedores", proveedoresService.findall());
		return "proveedores/listado";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String crear(Model model) {
		Proveedores proveedores= new Proveedores();
		model.addAttribute("titulo", "formulario Proveedores");
		model.addAttribute("proveedores", proveedores);
		return "proveedores/formulario";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
	@RequestMapping(value="/form",method = RequestMethod.POST)
	public String guardar(@Valid Proveedores proveedores,BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario Proveedores");
			return "proveedores/formulario";
		}
		
		//proveedor por crearse
		if(proveedores.getIdProveedores() == null) {
			
			if(proveedorDAO.findByRUC(proveedores.getRUC()).isPresent()) {
				model.addAttribute("error", "el RUC ya ha sido registrado");
				model.addAttribute("titulo", "formulario Proveedores");
				return "proveedores/formulario";
			}
			
			proveedoresService.save(proveedores);
		}
		//proveedor ya existente
		else {
			if(proveedorDAO.findByRUC(proveedores.getRUC()).isPresent()) {
				System.out.println("es null o khe ?");
				if (proveedorDAO.findByRUC(proveedores.getRUC()).orElse(null).getIdProveedores() == proveedores.getIdProveedores()) {
					System.out.println("es null o khe ?");
					proveedoresService.save(proveedores);
					return "redirect:/proveedores/listado";
				}
				model.addAttribute("error", "el RUC ya ha sido registrado");
				model.addAttribute("titulo", "Editar proveedor");
				return "proveedores/formulario";
			}else{
				proveedoresService.save(proveedores);
			}
			
		}
		
		return "redirect:/proveedores/listado";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
	@RequestMapping(value="/form/{idProveedores}")
	public String editar(@PathVariable(value="idProveedores") Long idProveedores, Model model){
		Proveedores proveedores = null;
		if(idProveedores > 0) {
			proveedores = proveedoresService.findOne(idProveedores);
		}else {
			return "redirect:/proveedores/listado";
		}
		model.addAttribute("titulo", "Editar proveedor");
		model.addAttribute("proveedores", proveedores);
		return "proveedores/formulario";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
	@RequestMapping("/eliminar/{idProveedores}")
	public String eliminar(@PathVariable(value="idProveedores") Long idProveedores, RedirectAttributes flash) {
		if(idProveedores > 0) {
			if(articuloDAO.findByprovedor(idProveedores).isEmpty()) {
				proveedoresService.delete(idProveedores);
			}else{
				flash.addFlashAttribute("error","el proveedor tiene productos relacionados");
			}
		}
		return "redirect:/proveedores/listado";
	}
	
}
