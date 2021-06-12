package com.grupo4.demo.models.entity.service;

import java.util.List;

import com.grupo4.demo.models.entity.Articulo;
import com.grupo4.demo.models.entity.Guia;

public interface IArticuloService {
	
	public List<Articulo> findall();
	public void save(Articulo articulo);
	public Articulo findOne(Long idArticulo);
	public void delete(Long idArticulo);
	public List<Articulo> findbyNombre(String term);
	public void saveGuia(Guia guia);
	
	
	
}
