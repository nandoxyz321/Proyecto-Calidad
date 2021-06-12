package com.grupo4.demo.models.entity.service;

import java.util.List;

import com.grupo4.demo.models.entity.Proveedores;

public interface IProveedoresService {
	public List<Proveedores> findall();
	public void save(Proveedores proveedores);
	public Proveedores findOne(Long idProveedores);
	public void delete(Long idProveedores);
}
