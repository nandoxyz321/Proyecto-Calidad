package com.grupo4.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.grupo4.demo.models.entity.Proveedores;
import com.grupo4.demo.models.entity.service.IProveedoresService;

@Controller
@RequestMapping("/proveedores")
public class ProveedoresController {

	@Autowired
	private IProveedoresService proveedoresService;
	
	@RequestMapping(value="/listado")
	public String listar(Model model) {
		model.addAttribute("titulo", "Proveedores registrados");
		model.addAttribute("proveedores", proveedoresService.findall());
		return "proveedores/listado";
	}
	
	@RequestMapping(value="/form", method = RequestMethod.GET)
	public String crear(Model model) {
		Proveedores proveedores= new Proveedores();
		model.addAttribute("titulo", "formulario Proveedores");
		model.addAttribute("proveedores", proveedores);
		return "proveedores/formulario";
	}
	
	@RequestMapping(value="/form",method = RequestMethod.POST)
	public String guardar(@Valid Proveedores proveedores,BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario Proveedores");
			return "proveedores/formulario";
		}
		proveedoresService.save(proveedores);
		
		return "redirect:/proveedores/listado";
	}
	
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
	
	@RequestMapping("/eliminar/{idProveedores}")
	public String eliminar(@PathVariable(value="idProveedores") Long idProveedores) {
		if(idProveedores > 0) {
			proveedoresService.delete(idProveedores);
		}
		return "redirect:/proveedores/listado";
	}
	
}
