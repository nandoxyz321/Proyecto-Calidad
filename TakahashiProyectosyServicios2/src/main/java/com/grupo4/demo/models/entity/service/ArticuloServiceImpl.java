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
		return (List<Articulo>) articuloDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Articulo articulo) {
		articuloDAO.save(articulo);
	}

	@Override
	@Transactional(readOnly = true)
	public Articulo findOne(Long idArticulo) {
		return articuloDAO.findById(idArticulo).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long idArticulo) {
		articuloDAO.deleteById(idArticulo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findbyNombre(String term,Long prov, Long cate) {
		return articuloDAO.findbyNombre(term,prov,cate);
	}

	@Override
	@Transactional
	public void saveGuia(Guia guia) {
		guiaDAO.save(guia);
	}
	
}
