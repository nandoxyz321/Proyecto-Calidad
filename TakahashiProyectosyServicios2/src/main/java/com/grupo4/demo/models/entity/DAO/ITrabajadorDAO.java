package com.grupo4.demo.models.entity.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo4.demo.models.entity.Trabajador;

public interface ITrabajadorDAO extends CrudRepository<Trabajador, Long>{
	
	public Trabajador findByUsername(String username);
	
	public Optional<Trabajador> findByEmailAndDni(String email, String dni);
	
	public Optional<Trabajador> findByDni(String dni);
}
