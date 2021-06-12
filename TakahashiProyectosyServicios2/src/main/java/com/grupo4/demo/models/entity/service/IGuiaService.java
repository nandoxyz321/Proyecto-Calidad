package com.grupo4.demo.models.entity.service;

import java.util.List;


import com.grupo4.demo.models.entity.Guia;

public interface IGuiaService {
	public List<Guia> findAll();
	public void save (Guia guia);
	public Guia findOne(Long idGuia);
	public void delete(Long idGuia);
}
