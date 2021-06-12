package com.grupo4.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.grupo4.demo.models.entity.Articulo;
import com.grupo4.demo.models.entity.Categoria;
import com.grupo4.demo.models.entity.Proveedores;
import com.grupo4.demo.models.entity.service.IArticuloService;
import com.grupo4.demo.models.entity.service.ICategoriaService;
import com.grupo4.demo.models.entity.service.IProveedoresService;

@Controller
@RequestMapping("/articulos")
public class ArticuloController {

	@Autowired
	private IArticuloService articuloService;
	
	@Autowired
	private IProveedoresService proveedoresService;
	
	@Autowired
	private ICategoriaService categoriaservice;
	
	@RequestMapping(value="/listado")
	public String listarArticulo(Model model) {
		model.addAttribute("titulo", "Artículos registrados");
		model.addAttribute("articulos", articuloService.findall());
		return "articulos/listado";
	}
	
	@RequestMapping(value="/form",method = RequestMethod.GET)
	public String crear(Model model) {
		Articulo articulo = new Articulo();
		model.addAttribute("titulo", "formulario artículos");
		model.addAttribute("articulo", articulo);
		return "articulos/formulario";
	}
	
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guardar(@Valid Articulo articulo,BindingResult result ,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario articulos");
			return "articulos/formulario";
		}
		articuloService.save(articulo);
		return "redirect:/articulos/listado";
	}
	
	@RequestMapping(value = "/form/{idArticulo}")
	public String editar(@PathVariable(value = "idArticulo") Long idArticulo, Model model) {
		Articulo articulo = null;
		if(idArticulo > 0 ) {
			articulo = articuloService.findOne(idArticulo);
		}else {
			return "redirect:/articulos/listado";
		}
		model.addAttribute("titulo", "Editar artículo");
		model.addAttribute("articulo", articulo);
		return "articulos/formulario";
	}
	
	@RequestMapping("/eliminar/{idArticulo}")
	public String eliminar(@PathVariable(value = "idArticulo")Long idArticulo) {
		if(idArticulo>0) {
			articuloService.delete(idArticulo);
		}
		return "redirect:/articulos/listado";
	}
	
	@ModelAttribute("listarcategoria")
	public List<Categoria> categoria(){
		return categoriaservice.findAll();
	}
	
	@ModelAttribute("listarproveedor")
	public List<Proveedores> proveedor(){
		return proveedoresService.findall();
	}
	
}
