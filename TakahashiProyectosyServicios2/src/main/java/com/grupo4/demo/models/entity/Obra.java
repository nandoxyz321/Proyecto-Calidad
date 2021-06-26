package com.grupo4.demo.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "obra")
public class Obra implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idobra")
	private Long idObra;
	
	//@NotEmpty
	//@Size(min = 1,max = 6,message = "el código debe estar entre 1 y 6 caracteres")
	@Column(name = "codigoobra")
	private String codigoObra;
	
	@Size(min = 1,max = 46,message = "el nombre debe contener no mas de 25 caracteres")
	@Pattern(regexp = "^([a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ]+)(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)*$",message = "solo se admiten letras y números")
	@Column(name = "nombreproyecto")
	private String nombreProyecto;
	
	@NotEmpty
	@Size(min = 5,max =90, message = "se admite entre 5 y 90 caracteres")
	@Column(name = "direccion")
	private String direccion;
	
	@NotEmpty
	@Size(min = 1,max = 65, message = "el campo debe estar entre 1 y 65 caracteres")
	@Column(name = "razonsocialcliente")
	private String razonSocialCliente;
	
	@NotEmpty
	@Size(min = 1,max = 65, message = "el campo debe estar entre 1 y 65 caracteres")
	@Column(name = "nombrerepresentante")
	private String nombreRepresentante;
	
	
	@OneToMany(mappedBy = "obra",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Guia> guias;
	
	
	
	
	public Obra() {
		guias = new ArrayList<Guia>();
	}


	public Long getIdObra() {
		return idObra;
	}


	public void setIdObra(Long idObra) {
		this.idObra = idObra;
	}





	public String getCodigoObra() {
		return codigoObra;
	}





	public void setCodigoObra(String codigoObra) {
		this.codigoObra = codigoObra;
	}





	public String getNombreProyecto() {
		return nombreProyecto;
	}





	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}





	public String getDireccion() {
		return direccion;
	}





	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}





	public String getRazonSocialCliente() {
		return razonSocialCliente;
	}





	public void setRazonSocialCliente(String razonSocialCliente) {
		this.razonSocialCliente = razonSocialCliente;
	}





	public String getNombreRepresentante() {
		return nombreRepresentante;
	}





	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}


	public List<Guia> getGuias() {
		return guias;
	}


	public void setGuias(List<Guia> guias) {
		this.guias = guias;
	}

	
	public void addGuia(Guia guia) {
		guias.add(guia);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
	