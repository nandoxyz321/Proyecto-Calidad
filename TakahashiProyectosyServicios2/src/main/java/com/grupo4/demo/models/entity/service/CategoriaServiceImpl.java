package com.grupo4.demo.models.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo4.demo.models.entity.Categoria;
import com.grupo4.demo.models.entity.DAO.ICategoriaDAO;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

	@Autowired
	private ICategoriaDAO categoriaDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findAll() {
		return  (List<Categoria>) categoriaDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Categoria categoria) {
		categoriaDAO.save(categoria);
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria findOne(Long idCategoria) {
		return categoriaDAO.findById(idCategoria).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long idCategoria) {
		categoriaDAO.deleteById(idCategoria);
	}
	
		
	
}
