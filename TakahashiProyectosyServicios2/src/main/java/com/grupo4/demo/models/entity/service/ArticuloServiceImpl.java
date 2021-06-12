package com.grupo4.demo.models.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo4.demo.models.entity.Articulo;
import com.grupo4.demo.models.entity.Guia;
import com.grupo4.demo.models.entity.DAO.IArticuloDAO;
import com.grupo4.demo.models.entity.DAO.IGuiaDao;

@Service
public class ArticuloServiceImpl implements IArticuloService{

	@Autowired
	private IArticuloDAO articuloDAO;
	
	@Autowired
	private IGuiaDao guiaDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findall() {
		// TODO Auto-generated method stub
		return (List<Articulo>) articuloDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Articulo articulo) {
		// TODO Auto-generated method stub
		articuloDAO.save(articulo);
	}

	@Override
	@Transactional(readOnly = true)
	public Articulo findOne(Long idArticulo) {
		// TODO Auto-generated method stub
		return articuloDAO.findById(idArticulo).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long idArticulo) {
		// TODO Auto-generated method stub
		articuloDAO.deleteById(idArticulo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findbyNombre(String term) {
		// TODO Auto-generated method stub
		return articuloDAO.findbyNombre(term);
	}

	@Override
	@Transactional
	public void saveGuia(Guia guia) {
		// TODO Auto-generated method stub
		guiaDAO.save(guia);
	}
	
}
