package com.grupo4.demo.models.entity.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grupo4.demo.models.entity.Articulo;

public interface IArticuloDAO extends CrudRepository<Articulo,Long>{

	@Query("select a from Articulo a where a.nombre like %?1%")
	public List<Articulo> findbyNombre(String term);

}
