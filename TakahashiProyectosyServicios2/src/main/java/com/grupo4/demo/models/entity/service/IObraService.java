package com.grupo4.demo.models.entity.service;

import java.util.List;

import com.grupo4.demo.models.entity.Obra;

public interface IObraService {

	public List<Obra> findall();
	public void save(Obra obra);
	public Obra findOne(Long idObra);
	public void delete(Long idObra);
	
}
