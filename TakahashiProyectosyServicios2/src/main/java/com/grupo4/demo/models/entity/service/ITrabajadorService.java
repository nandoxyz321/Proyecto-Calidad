package com.grupo4.demo.models.entity.service;

import java.util.List;

import com.grupo4.demo.models.entity.Trabajador;

public interface ITrabajadorService {
	public List<Trabajador> findAll();
	public void save(Trabajador trabajador);
	public Trabajador findOne(Long idTrabajador);
	public void delete (Long idTrabajador);
}
