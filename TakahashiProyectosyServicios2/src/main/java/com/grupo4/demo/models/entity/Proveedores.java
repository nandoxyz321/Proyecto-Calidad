	package com.grupo4.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name= "proveedores")
public class Proveedores implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idproveedor")
	private Long idProveedores;
	
	@NotEmpty
	@Size(min = 1,max = 6,message = "el código debe estar entre 1 y 6 caracteres")
	@Column(name="codigoproveedor")
	private String codigoProveedor;
	
	@Size(min = 1,max = 45,message = "el campo debe tener entre 1 y 45 caracteres")
	@Column(name = "razonsocial")
	private String razonSocial;
	
	@NotEmpty
	@Size(min = 1,max = 11, message = "el ruc debe estár entre 1 y 11 numeros")
	@Pattern(regexp = "[0-9]+",message = "solo se admiten números")
	@Column(name = "ruc")
	private String RUC;
	
	
	
	public Long getIdProveedores() {
		return idProveedores;
	}








	public void setIdProveedores(Long idProveedores) {
		this.idProveedores = idProveedores;
	}








	public String getCodigoProveedor() {
		return codigoProveedor;
	}








	public void setCodigoProveedor(String codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}








	public String getRazonSocial() {
		return razonSocial;
	}








	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}









	public String getRUC() {
		return RUC;
	}








	public void setRUC(String rUC) {
		RUC = rUC;
	}








	private static final long serialVersionUID = 1L;

}
