package com.grupo4.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.grupo4.demo.models.entity.Categoria;
import com.grupo4.demo.models.entity.service.ICategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@RequestMapping(value="/listado")
	public String listarCategoria(Model model) {
		model.addAttribute("titulo", "Categorias registradas");
		model.addAttribute("categorias", categoriaService.findAll());
		return "categorias/listado";
	}
	
	@RequestMapping(value="/form",method = RequestMethod.GET)
	public String crear(Model model) {
		Categoria categoria= new Categoria();
		model.addAttribute("titulo", "formulario categoria");
		model.addAttribute("categoria", categoria);
		return "categorias/formulario";
	}
	
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guardar(@Valid Categoria categoria, BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario categoria");
			return "categorias/formulario";
		}
		categoriaService.save(categoria);
		return "redirect:/categorias/listado";
	}
	
	@RequestMapping(value = "/form/{idCategoria}")
	public String editar(@PathVariable(value = "idCategoria") Long idCategoria, Model model) {
		Categoria categoria = null;
		if(idCategoria > 0 ) {
			categoria = categoriaService.findOne(idCategoria);
		}else {
			return "redirect:/categorias/listado";
		}
		model.addAttribute("titulo", "Editar categoria");
		model.addAttribute("categoria", categoria);
		return "categorias/formulario";
	}
	
	@RequestMapping("/eliminar/{idCategoria}")
	public String eliminar(@PathVariable(value = "idCategoria")Long idCategoria) {
		if(idCategoria>0) {
			categoriaService.delete(idCategoria);
		}
		return "redirect:/categorias/listado";
	}
	
	
}
