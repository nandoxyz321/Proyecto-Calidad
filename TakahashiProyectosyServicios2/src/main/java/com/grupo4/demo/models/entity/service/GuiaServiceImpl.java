package com.grupo4.demo.models.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo4.demo.models.entity.Guia;
import com.grupo4.demo.models.entity.DAO.IGuiaDao;

@Service
public class GuiaServiceImpl implements IGuiaService{
	
	@Autowired
	private IGuiaDao guiaDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Guia> findAll() {
		return (List<Guia>) guiaDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Guia guia) {
		guiaDAO.save(guia);
	}

	@Override
	@Transactional(readOnly = true)
	public Guia findOne(Long idGuia) {
		return guiaDAO.findById(idGuia).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long idGuia) {
		guiaDAO.deleteById(idGuia);
	}
	
	

}
