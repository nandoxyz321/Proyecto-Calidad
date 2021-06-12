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

@Entity
@Table(name = "detalleguia")
public class DetalleGuia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iddetalleguia")
	private Long idDetalleGuia;
	
	@Column(name = "cantidad")
	private int cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idarticulo")
	private Articulo articulo;
	
		
	
	public Long getIdDetalleGuia() {
		return idDetalleGuia;
	}



	public void setIdDetalleGuia(Long idDetalleGuia) {
		this.idDetalleGuia = idDetalleGuia;
	}



	public int getCantidad() {
		return cantidad;
	}



	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public Articulo getArticulo() {
		return articulo;
	}



	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
}
