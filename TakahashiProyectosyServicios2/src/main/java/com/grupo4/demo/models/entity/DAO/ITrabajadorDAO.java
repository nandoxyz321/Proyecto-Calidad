package com.grupo4.demo.models.entity.DAO;

import org.springframework.data.repository.CrudRepository;

import com.grupo4.demo.models.entity.Trabajador;

public interface ITrabajadorDAO extends CrudRepository<Trabajador, Long>{
	
	public Trabajador findByUsername(String username);
}
