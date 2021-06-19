package com.grupo4.demo.models.entity.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo4.demo.models.entity.Proveedores;

public interface IProveedorDAO extends CrudRepository<Proveedores, Long>{

	public Optional<Proveedores> findByRUC(String ruc);
	
	
}
