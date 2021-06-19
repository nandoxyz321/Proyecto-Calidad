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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grupo4.demo.models.entity.Articulo;
import com.grupo4.demo.models.entity.Categoria;
import com.grupo4.demo.models.entity.Proveedores;
import com.grupo4.demo.models.entity.DAO.IArticuloDAO;
import com.grupo4.demo.models.entity.DAO.IDetalleguiaDAO;
import com.grupo4.demo.models.entity.service.IArticuloService;
import com.grupo4.demo.models.entity.service.ICategoriaService;
import com.grupo4.demo.models.entity.service.IProveedoresService;

@Controller
@RequestMapping("/articulos")
public class ArticuloController {
	
	@Autowired
	private IDetalleguiaDAO detalleguiaDAO;

	@Autowired
	private IArticuloDAO articuloDAO;
	
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
		
		int valor = articulo.getNombre().hashCode();
		if(valor<0) {
			valor=valor*-1;
		}
		String valore = String.valueOf(valor);
		char valorA=categoriaservice.findOne(articulo.getCategoria().getIdCategoria()).getNombre().charAt(0);
		
		char valorB = proveedoresService.findOne(articulo.getProveedores().getIdProveedores()).getRazonSocial().charAt(0);
		
		String codigoGenerado = valore.concat(String.valueOf(valorA).concat(String.valueOf(valorB)));
		
		if(articulo.getIdArticulo() == null) {
			
			if(articuloDAO.findByCodigoArticulo(codigoGenerado).isPresent()) {
				model.addAttribute("error", "el artículo ya ha sido registrado");
				model.addAttribute("titulo", "formulario articulos");
				return "articulos/formulario";
			}
			
			if(articulo.getStock()>12) {
				articulo.setEstado("en stock");
			}else if(articulo.getStock()>=1){
				articulo.setEstado("pocas unidades");
			}else {
				articulo.setEstado("agotado");
			}
			
			articulo.setCodigoArticulo(codigoGenerado);
			articuloService.save(articulo);
			
		}else {
			
			if(articulo.getStock()>12) {
				articulo.setEstado("en stock");
			}else if(articulo.getStock()>1){
				articulo.setEstado("pocas unidades");
			}else {
				articulo.setEstado("agotado");
			}
			
			if(articuloDAO.findByCodigoArticulo(codigoGenerado).isPresent()) {
				
				if (articuloDAO.findByCodigoArticulo(codigoGenerado).orElse(null).getIdArticulo() == articulo.getIdArticulo()) {
					articuloService.save(articulo);
					return "redirect:/articulos/listado";
				}
				
				model.addAttribute("error", "el artículo ya ha sido registrado");
				model.addAttribute("titulo", "Editar artículo");
				return "articulos/formulario";
				
			}else {
				articulo.setCodigoArticulo(codigoGenerado);
				articuloService.save(articulo);
			}
		}
		
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
	public String eliminar(@PathVariable(value = "idArticulo")Long idArticulo, RedirectAttributes flash) {
		if(idArticulo>0) {
			if(detalleguiaDAO.findbydetaye(idArticulo).isEmpty()) {
				articuloService.delete(idArticulo);
			}else{
				flash.addFlashAttribute("error","el producto está siendo usado en las guias");
			}
			
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
