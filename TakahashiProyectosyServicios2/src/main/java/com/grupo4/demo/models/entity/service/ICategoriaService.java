package com.grupo4.demo.models.entity.service;

import java.util.List;

import com.grupo4.demo.models.entity.Categoria;

public interface ICategoriaService {
	public List<Categoria> findAll();
	public void save (Categoria categoria);
	public Categoria findOne(Long idCategoria);
	public void delete(Long idCategoria);
}
