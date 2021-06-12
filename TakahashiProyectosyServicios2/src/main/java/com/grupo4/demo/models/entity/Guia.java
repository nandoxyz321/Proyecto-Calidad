package com.grupo4.demo.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "guia")
public class Guia implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idguia")
	private Long idGuia;
	
	@NotEmpty
	@Size(min = 1, max = 6, message = "el código debe estar entre 1 y 6 caracteres ")
	@Column(name = "codigoguia")
	private String codigoGuia;
	
	@NotEmpty
	@Size(min = 1, max = 50, message = "el destino debe estar entre 1 y 50 caracteres ")
	@Column(name = "destino")
	private String destino;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechasalida")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaSalida;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechallegada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechallegada;
	
	@JoinColumn(name = "idobra")
	@ManyToOne(fetch = FetchType.LAZY)
	private Obra obra;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "idguia")
	private List<DetalleGuia> detalles;
	
	public Long getIdGuia() {
		return idGuia;
	}


	public Guia() {
		this.detalles = new ArrayList<DetalleGuia>();
	}






	public void setIdGuia(Long idGuia) {
		this.idGuia = idGuia;
	}




	public String getCodigoGuia() {
		return codigoGuia;
	}




	public void setCodigoGuia(String codigoGuia) {
		this.codigoGuia = codigoGuia;
	}




	public String getDestino() {
		return destino;
	}




	public void setDestino(String destino) {
		this.destino = destino;
	}




	public Date getFechaSalida() {
		return fechaSalida;
	}




	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}




	public Date getFechallegada() {
		return fechallegada;
	}




	public void setFechallegada(Date fechallegada) {
		this.fechallegada = fechallegada;
	}


	public Obra getObra() {
		return obra;
	}




	public void setObra(Obra obra) {
		this.obra = obra;
	}


	

	public List<DetalleGuia> getDetalles() {
		return detalles;
	}




	public void setDetalles(List<DetalleGuia> detalles) {
		this.detalles = detalles;
	}


	public void addDetalleGuia(DetalleGuia detalle) {
		this.detalles.add(detalle);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
}
