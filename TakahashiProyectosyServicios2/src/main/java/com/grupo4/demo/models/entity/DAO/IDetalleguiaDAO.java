package com.grupo4.demo.models.entity.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.grupo4.demo.models.entity.DetalleGuia;

public interface IDetalleguiaDAO extends CrudRepository<DetalleGuia, Long>{
	
	@Query(value = "select * from detalleguia where idarticulo=?1",nativeQuery = true)
	List<DetalleGuia> findbydetaye(Long idArticulo);
}
