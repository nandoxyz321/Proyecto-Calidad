package com.grupo4.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.grupo4.demo.models.entity.Articulo;
import com.grupo4.demo.models.entity.DetalleGuia;
import com.grupo4.demo.models.entity.Guia;
import com.grupo4.demo.models.entity.Obra;
import com.grupo4.demo.models.entity.service.IArticuloService;
import com.grupo4.demo.models.entity.service.IGuiaService;
import com.grupo4.demo.models.entity.service.IObraService;

@Controller
@RequestMapping("/guias")
public class GuiaController {

	@Autowired
	private IObraService obraService;
	
	@Autowired
	private IArticuloService articuloService;
	
	@Autowired
	private IGuiaService guiaService;
	
	@RequestMapping(value="/form",method = RequestMethod.GET)
	public String crear(Model model) {
		Guia guia = new Guia();
		model.addAttribute("titulo", "formulario guia");
		model.addAttribute("guia", guia);
		return "guias/formulario";
	}
	
	@ModelAttribute("listarobra")
	public List<Obra> obra(Model model){
		return obraService.findall();
	}
	
	@GetMapping(value="/cargar-articulos/{term}", produces= {"application/json"})
	public @ResponseBody List<Articulo> cargarArticulos(@PathVariable String term){
		return articuloService.findbyNombre(term);
	}
	
	@PostMapping(value="/form")
	public String guardar(@Valid Guia guia,BindingResult result,Model model,
			@RequestParam(name="detalle_idArticulo[]",required = false) Long[] detalleID,
			@RequestParam(name="cantidad[]", required = false) Integer[] cantidad) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario guia");
			return "guias/formulario";
		}
		
		Obra obra = obraService.findOne(guia.getObra().getIdObra());
		guia.setObra(obra);
		
		for(int i=0;i<detalleID.length;i++) {
			Articulo articulo = articuloService.findOne(detalleID[i]);
			DetalleGuia detalleguia = new DetalleGuia();
			detalleguia.setCantidad(cantidad[i]);
			detalleguia.setArticulo(articulo);
			guia.addDetalleGuia(detalleguia);
		}
		
		guiaService.save(guia);
		
		return "redirect:../obras/ver/"+guia.getObra().getIdObra();
		
	}
	
	
}
