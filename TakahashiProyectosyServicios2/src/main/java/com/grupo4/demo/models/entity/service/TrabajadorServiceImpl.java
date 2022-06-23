package com.grupo4.demo.models.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo4.demo.models.entity.Codificacion;
import com.grupo4.demo.models.entity.Trabajador;
import com.grupo4.demo.models.entity.DAO.ITrabajadorDAO;

@Service
public class TrabajadorServiceImpl implements ITrabajadorService{
	
	@Autowired
	private ITrabajadorDAO trabajadorDao;	
	
	private Codificacion codigo;

	@Autowired
	private JavaMailSender mailsender;
	
	@Override
	@Transactional(readOnly = true)
	public List<Trabajador> findAll() {
		return (List<Trabajador>) trabajadorDao.findAll();
	}

	@Override
	@Transactional
	public void save(Trabajador trabajador) {
		trabajadorDao.save(trabajador);
	}

	@Override
	@Transactional(readOnly = true)
	public Trabajador findOne(Long idTrabajador) {
		return trabajadorDao.findById(idTrabajador).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long idTrabajador) {
		trabajadorDao.deleteById(idTrabajador);
	}

	@Override
	@Transactional(readOnly = true)
	public Trabajador findByEmailAndDni(String email, String dni) {
		return trabajadorDao.findByEmailAndDni(email, dni).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Trabajador findByDni(String dni) {
		return trabajadorDao.findByDni(dni).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public String codificacionEncrypt(String password) {
		codigo = new Codificacion();
		codigo.encrpt(password);
		return codigo.encrpt(password);
	}

	@Override
	@Transactional(readOnly = true)
	public String codificacionDencrypt(String password) {
		codigo = new Codificacion();
		return codigo.dencrpt(password);
	}

	@Override
	@Transactional
	public void recuperarPass(String username, String nombre, String apellido, String recuperado) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("luislolimott@gmail.com");
		message.setTo("luislolimott@gmail.com");
		message.setSubject("petición del usuario: "+nombre+" "+apellido);
		message.setText("el usuario es :"+username+"\nPassword:"+recuperado);
		mailsender.send(message);
	}

}
