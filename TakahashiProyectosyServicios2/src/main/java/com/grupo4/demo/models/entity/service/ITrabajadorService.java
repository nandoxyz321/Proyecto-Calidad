package com.grupo4.demo.models.entity.service;

import java.util.List;

import com.grupo4.demo.models.entity.Trabajador;

public interface ITrabajadorService {
	public List<Trabajador> findAll();
	public void save(Trabajador trabajador);
	public Trabajador findOne(Long idTrabajador);
	public Trabajador findByDni(String dni);
	public void delete (Long idTrabajador);
	public Trabajador findByEmailAndDni(String email, String dni);
	public String codificacionEncrypt(String password);
	public String codificacionDencrypt(String password);
	public void recuperarPass(String username, String nombre, String apellido, String recuperado);
}
