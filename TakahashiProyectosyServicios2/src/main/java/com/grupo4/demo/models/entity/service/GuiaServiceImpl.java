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
		// TODO Auto-generated method stub
		return (List<Guia>) guiaDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Guia guia) {
		// TODO Auto-generated method stub
		guiaDAO.save(guia);
	}

	@Override
	@Transactional(readOnly = true)
	public Guia findOne(Long idGuia) {
		// TODO Auto-generated method stub
		return guiaDAO.findById(idGuia).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long idGuia) {
		// TODO Auto-generated method stub
		guiaDAO.deleteById(idGuia);
	}
	
	

}
