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

import com.grupo4.demo.models.entity.Categoria;
import com.grupo4.demo.models.entity.DAO.IArticuloDAO;
import com.grupo4.demo.models.entity.DAO.ICategoriaDAO;
import com.grupo4.demo.models.entity.service.ICategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private IArticuloDAO articuloDAO;
	
	@Autowired
	private ICategoriaDAO categoriaDAO;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC","ROLE_SUPER"})
	@RequestMapping(value="/listado")
	public String listarCategoria(Model model) {
		model.addAttribute("titulo", "Categorias registradas");
		model.addAttribute("categorias", categoriaService.findAll());
		return "categorias/listado";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
	@RequestMapping(value="/form",method = RequestMethod.GET)
	public String crear(Model model) {
		Categoria categoria= new Categoria();
		model.addAttribute("titulo", "formulario categoria");
		model.addAttribute("categoria", categoria);
		return "categorias/formulario";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guardar(@Valid Categoria categoria, BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario categoria");
			return "categorias/formulario";
		}
		int valor = categoria.getNombre().hashCode();
		if(valor<0) {
			valor=valor*-1;
		}
		String valore = String.valueOf(valor);
		if(categoria.getIdCategoria() == null) {
			
			if(categoriaDAO.findByCodigo(valore).isPresent()) {
				model.addAttribute("titulo", "formulario categoria");
				model.addAttribute("error", "la categoria ya ha sido registrado");
				return "categorias/formulario";
			}
			categoria.setCodigo(valore);
			categoriaService.save(categoria);
			
		}else {
			
			if(categoriaDAO.findByCodigo(valore).isPresent()) {
				
				if (categoriaDAO.findByCodigo(valore).orElse(null).getIdCategoria() == categoria.getIdCategoria()) {
					categoriaService.save(categoria);
					return "redirect:/categorias/listado";
				}
				
				model.addAttribute("titulo", "Editar categoria");
				model.addAttribute("error", "la categoria ya ha sido registrado");
				return "categorias/formulario";
				
			}else {
				categoriaService.save(categoria);
			}
			
		}
		return "redirect:/categorias/listado";
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
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
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC"})
	@RequestMapping("/eliminar/{idCategoria}")
	public String eliminar(@PathVariable(value = "idCategoria")Long idCategoria, RedirectAttributes flash) {
		if(idCategoria>0) {
			if(articuloDAO.findBycate(idCategoria).isEmpty()) {
				categoriaService.delete(idCategoria);
			}else{
				flash.addFlashAttribute("error","la categoria tiene productos relacionados");
			}
			
		}
		return "redirect:/categorias/listado";
	}
	
	
}
