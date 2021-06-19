package com.grupo4.demo.models.entity.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo4.demo.models.entity.Obra;

public interface IObraDAO extends CrudRepository<Obra, Long>{

	public Optional<Obra> findByCodigoObra(String codigoObra);
	
}
