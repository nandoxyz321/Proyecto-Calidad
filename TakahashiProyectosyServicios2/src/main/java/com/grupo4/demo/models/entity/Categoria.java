package com.grupo4.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcategoria")
	private Long idCategoria;
	
	//@NotEmpty
	//@Size(min = 1,max = 6,message = "el código debe estar entre 1 y 6 caracteres")
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "nombre")
	@NotEmpty
	@Size(min = 1,max = 35,message = "el nombre debe contener no mas de 35 caracteres")
	@Pattern(regexp = "^([a-zA-ZñÑáéíóúÁÉÍÓÚ]+)(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)*$",message = "solo se admiten letras y acentos")
	private String nombre;
	
	@NotEmpty
	@NotBlank(message = "el nombre no puede estar en blanco")
	@Column(name = "descripcion")
	private String descripcion;
	
	
	public Long getIdCategoria() {
		return idCategoria;
	}







	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}







	public String getCodigo() {
		return codigo;
	}







	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}







	public String getNombre() {
		return nombre;
	}







	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}







	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}














	private static final long serialVersionUID = 1L;
	
	
}
