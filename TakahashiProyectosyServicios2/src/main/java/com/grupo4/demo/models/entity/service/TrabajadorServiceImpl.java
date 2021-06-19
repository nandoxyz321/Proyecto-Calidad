package com.grupo4.demo.models.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupo4.demo.models.entity.Trabajador;
import com.grupo4.demo.models.entity.DAO.ITrabajadorDAO;

@Service
public class TrabajadorServiceImpl implements ITrabajadorService{
	
	@Autowired
	private ITrabajadorDAO trabajadorDao;	
	
	@Override
	@Transactional(readOnly = true)
	public List<Trabajador> findAll() {
		// TODO Auto-generated method stub
		return (List<Trabajador>) trabajadorDao.findAll();
	}

	@Override
	@Transactional
	public void save(Trabajador trabajador) {
		// TODO Auto-generated method stub
		trabajadorDao.save(trabajador);
	}

	@Override
	@Transactional(readOnly = true)
	public Trabajador findOne(Long idTrabajador) {
		// TODO Auto-generated method stub
		return trabajadorDao.findById(idTrabajador).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long idTrabajador) {
		// TODO Auto-generated method stub
		trabajadorDao.deleteById(idTrabajador);
	}

	@Override
	@Transactional(readOnly = true)
	public Trabajador findByEmailAndDni(String email, String dni) {
		// TODO Auto-generated method stub
		return trabajadorDao.findByEmailAndDni(email, dni).orElse(null);
	}	

}
