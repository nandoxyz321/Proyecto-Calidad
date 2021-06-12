package com.grupo4.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "articulo")
public class Articulo implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idarticulo")
	private Long idArticulo;
	
	@NotEmpty
	@Size(min = 1,max = 6,message = "el código debe estar entre 1 y 6 caracteres")
	@Column(name = "codigoarticulo")
	private String codigoArticulo;
	
	@Size(min = 1,max = 40 ,message = "el nombre debe contener no mas de 40 caracteres")
	@Pattern(regexp = "^([a-zA-Z0-9]+)(\\s[a-zA-Z]+)*$",message = "solo se admiten letras y números")
	@Column(name = "nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;
	
	@Min(value = 1,message = "el stock mínimo es 1")
	@Max(value=100, message = "el stock máximo es 100")
	@Column(name = "stock")
	private int stock;
	
	@NotEmpty
	@Column(name = "estado")
	private String estado;
	
	@JoinColumn(name = "idcategoria")
	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;
	
	@JoinColumn(name = "idproveedor")
	@ManyToOne(fetch = FetchType.LAZY)
	private Proveedores proveedores;

	public Long getIdArticulo() {
		return idArticulo;
	}



	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}



	public String getCodigoArticulo() {
		return codigoArticulo;
	}



	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
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


	public int getStock() {
		return stock;
	}



	public void setStock(int stock) {
		this.stock = stock;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	

	public Proveedores getProveedores() {
		return proveedores;
	}



	public void setProveedores(Proveedores proveedores) {
		this.proveedores = proveedores;
	}

	


	private static final long serialVersionUID = 1L;

}
