package com.grupo4.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.grupo4.demo.models.entity.Categoria;
import com.grupo4.demo.models.entity.DetalleGuia;
import com.grupo4.demo.models.entity.Guia;
import com.grupo4.demo.models.entity.Obra;
import com.grupo4.demo.models.entity.Proveedores;
import com.grupo4.demo.models.entity.Trabajador;
import com.grupo4.demo.models.entity.DAO.ITrabajadorDAO;
import com.grupo4.demo.models.entity.service.IArticuloService;
import com.grupo4.demo.models.entity.service.ICategoriaService;
import com.grupo4.demo.models.entity.service.IGuiaService;
import com.grupo4.demo.models.entity.service.IObraService;
import com.grupo4.demo.models.entity.service.IProveedoresService;


@Controller
@RequestMapping("/guias")
public class GuiaController {

	@Autowired
	private IObraService obraService;
	
	@Autowired
	private ITrabajadorDAO trabajadorDAO;
	
	@Autowired
	private IArticuloService articuloService;
	
	@Autowired
	private IProveedoresService proveedorService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IGuiaService guiaService;
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC","ROLE_OPERA"})
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
	
	@ModelAttribute("listarcategoria")
	public List<Categoria> categoria(Model model){
		return categoriaService.findAll();
	}
	
	@ModelAttribute("listarproveedor")
	public List<Proveedores> proveedores(Model model){
		return proveedorService.findall();
	}
	
	@GetMapping(value="/cargar-articulos/{term}/{prov}/{cate}", produces= {"application/json"})
	public @ResponseBody List<Articulo> cargarArticulos(@PathVariable String term, @PathVariable Long prov, @PathVariable Long cate ){
		System.out.println(prov);
		System.out.println(term);
		return articuloService.findbyNombre(term,prov,cate);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ALMAC","ROLE_OPERA"})
	@PostMapping(value="/form")
	public String guardar(@Valid Guia guia,BindingResult result,Model model,
			@RequestParam(name="detalle_idArticulo[]",required = false) Long[] detalleID,
			@RequestParam(name="cantidad[]", required = false) Integer[] cantidad) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "formulario guia");
			return "guias/formulario";
		}
		
		if (detalleID == null || detalleID.length == 0) {
			model.addAttribute("titulo", "formulario guia");
			model.addAttribute("error", "Error: La factura NO puede estár sin productos");
			if(guia.getFechallegada().isBefore(guia.getFechaSalida())) {
				model.addAttribute("error2", "ingrese las fechas correctas");
			}
			return "guias/formulario";
		}
		
		if(guia.getFechallegada().isBefore(guia.getFechaSalida())) {
			model.addAttribute("titulo", "formulario guia");
			model.addAttribute("error2", "ingrese las fechas correctas");
			return "guias/formulario";
		}
		
		Obra obra = obraService.findOne(guia.getObra().getIdObra());
		guia.setObra(obra);
		
		//obteniendo al ususario en sesion
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		
		Trabajador trabajador = trabajadorDAO.findByUsername(userDetail.getUsername());
		
		guia.setTrabajador(trabajador);
		
		String codigo = guia.getDestino().substring(1, 2).concat(guia.getFechaSalida().toString().substring(8,10)
						.concat(guia.getFechallegada().toString().substring(8,10)))
						.concat(obra.getNombreProyecto().substring(8)
						.concat(String.valueOf(trabajador.getIdTrabajador())));
		
		guia.setCodigoGuia(codigo);
		//Trabajador trabajadores = trabajadorService.findOne(trabajador.getIdTrabajador());
		guia.setTrabajador(trabajador);
		
		for(int i=0;i<detalleID.length;i++) {
			Articulo articulo = articuloService.findOne(detalleID[i]);
			DetalleGuia detalleguia = new DetalleGuia();
			detalleguia.setCantidad(cantidad[i]);
			detalleguia.setArticulo(articulo);
			articulo.setStock(articulo.getStock()-detalleguia.getCantidad());
			if(articulo.getStock()>12) {
				articulo.setEstado("en stock");
			}else if(articulo.getStock()>1){
				articulo.setEstado("pocas unidades");
			}else {
				articulo.setEstado("agotado");
			}
			guia.addDetalleGuia(detalleguia);
		}
		
		
		guiaService.save(guia);
		
		return "redirect:../obras/ver/"+guia.getObra().getIdObra();
		
	}
	
	
}
