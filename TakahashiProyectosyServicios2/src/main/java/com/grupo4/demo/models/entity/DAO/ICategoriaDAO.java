package com.grupo4.demo.models.entity.DAO;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo4.demo.models.entity.Categoria;

public interface ICategoriaDAO extends CrudRepository<Categoria, Long>{

	Optional<Categoria> findByCodigo(String codigo);
}
