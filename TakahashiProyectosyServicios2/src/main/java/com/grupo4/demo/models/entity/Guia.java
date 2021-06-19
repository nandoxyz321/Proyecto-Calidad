package com.grupo4.demo.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "guia")
public class Guia implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idguia")
	private Long idGuia;
	
	//@NotEmpty
	//@Size(min = 1, max = 6, message = "el código debe estar entre 1 y 6 caracteres ")
	@Column(name = "codigoguia")
	private String codigoGuia;
	
	@NotEmpty
	@Size(min = 1, max = 50, message = "el destino debe estar entre 1 y 50 caracteres ")
	@Column(name = "destino")
	private String destino;
	
	
	@Column(name = "fechasalida")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	@FutureOrPresent(message = "selecciona una fecha válida")
	private LocalDate fechaSalida;
	
	
	@Column(name = "fechallegada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	@FutureOrPresent(message = "selecciona una fecha válida")
	private LocalDate fechallegada;
	
	@JoinColumn(name = "idobra")
	@ManyToOne(fetch = FetchType.LAZY)
	private Obra obra;
	
	@JoinColumn(name = "idtrabajador")
	@ManyToOne(fetch = FetchType.LAZY)
	private Trabajador trabajador;
	
	
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

	
	public Obra getObra() {
		return obra;
	}

	


	public LocalDate getFechaSalida() {
		return fechaSalida;
	}


	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}


	public LocalDate getFechallegada() {
		return fechallegada;
	}


	public void setFechallegada(LocalDate fechallegada) {
		this.fechallegada = fechallegada;
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
	

	public Trabajador getTrabajador() {
		return trabajador;
	}


	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
}
