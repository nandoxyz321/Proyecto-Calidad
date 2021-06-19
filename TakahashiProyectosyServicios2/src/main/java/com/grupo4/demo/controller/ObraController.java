package com.grupo4.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.grupo4.demo.models.entity.Guia;
import com.grupo4.demo.models.entity.Obra;
import com.grupo4.demo.models.entity.DAO.IObraDAO;
import com.grupo4.demo.models.entity.service.IGuiaService;
import com.grupo4.demo.models.entity.service.IObraService;

@Controller
@RequestMapping("/obras")
public class ObraController {

	@Autowired
	private IObraDAO obraDAO;
	
	@Autowired
	private IObraService obraService;
	
	@Autowired
	private IGuiaService guiaService;
	
	
	@RequestMapping(value="/listado")
	public String listarObra(Model model) {
		model.addAttribute("titulo", "Obras registrados");
		model.addAttribute("obras", obraService.findall());
		return "obras/listado";
	}
	
	@RequestMapping(value="/form",method = RequestMethod.GET)
	public String crear(Model model) {
		Obra obra = new Obra();
		model.addAttribute("titulo", "formulario obras");
		model.addAttribute("obra", obra);
		return "obras/formulario";
	}
	
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guardar(@Valid Obra obra ,BindingResult result ,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario obras");
			return "obras/formulario";
		}
		int codigo = obra.getNombreProyecto().hashCode();
		if(codigo<0) {
			codigo=codigo*-1;
		}
		if(obra.getIdObra()==null) {
			if(obraDAO.findByCodigoObra(String.valueOf(codigo)).isPresent()) {
				model.addAttribute("titulo", "formulario obras");
				model.addAttribute("error", "la obra ya ha sido registrada");
				return "obras/formulario";
			}
			
			obra.setCodigoObra(String.valueOf(codigo));
			obraService.save(obra);
			
		}else {
			if(obraDAO.findByCodigoObra(String.valueOf(codigo)).isPresent()) {
				
				if (obraDAO.findByCodigoObra(String.valueOf(codigo)).orElse(null).getIdObra() == obra.getIdObra()) {
					obraService.save(obra);
					return "redirect:/obras/listado";
				}
				
				model.addAttribute("titulo", "Editar obra");
				model.addAttribute("error", "la obra ya ha sido registrada");
				return "obras/formulario";
				
			}else {
				obra.setCodigoObra(String.valueOf(codigo));
				obraService.save(obra);
			}
		}
		
		return "redirect:/obras/listado";
	}
	
	@RequestMapping(value = "/form/{idObra}")
	public String editar(@PathVariable(value = "idObra") Long idObra, Model model) {
		Obra obra = null;
		if(idObra > 0 ) {
			obra = obraService.findOne(idObra);
		}else {
			return "redirect:/obras/listado";
		}
		model.addAttribute("titulo", "Editar obra");
		model.addAttribute("obra", obra);
		return "obras/formulario";
	}
	
	@RequestMapping("/eliminar/{idObra}")
	public String eliminar(@PathVariable(value = "idObra")Long idObra) {
		if(idObra>0) {
			obraService.delete(idObra);
		}
		return "redirect:/obras/listado";
	}
	
	@RequestMapping("/ver/{idObra}")
	public String ver(@PathVariable(value="idObra") Long idObra, Model model) {
		Obra obra = null;
		if(idObra > 0 ) {
			obra = obraService.findOne(idObra);
		}else {
			return "redirect:/obras/listado";
		}
		model.addAttribute("titulo", "detalles de la obra "+obra.getNombreProyecto());
		model.addAttribute("tituloGuia", "guias registradas de la obra "+obra.getNombreProyecto());
		model.addAttribute("obra", obra);
		return "obras/ver";
	}
	

	@RequestMapping("/verGuia/{idGuia}")
	public String verGuia(@PathVariable(value="idGuia") Long idGuia, Model model) {
		Guia guia = null;
		if(idGuia >0) {
			guia = guiaService.findOne(idGuia);
		}else {
			return "obras/ver";
		}
		model.addAttribute("titulo", "detalles de la guia");
		model.addAttribute("tituloDetalles", "artículos de la guia");
		model.addAttribute("guia", guia);
		return "obras/verGuia";
	}
	
	@RequestMapping(value = "/eliminarGuia/{idGuia}",method = RequestMethod.GET)
	public String eliminarGuia(@PathVariable(value = "idGuia")Long idGuia,Model model) {
		Guia guia = guiaService.findOne(idGuia);
		Obra obra = guia.getObra();
		if(guia!=null) {
			guiaService.delete(idGuia);
		}
		return "redirect:/obras/ver/"+ obra.getIdObra();
		//return "redirect:/obras/listado";
		
	}
	
}
