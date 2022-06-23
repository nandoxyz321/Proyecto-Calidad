package com.grupo4.demo.models.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//mapeando a la base de datos
@Entity
@Table(name="trabajador")
public class Trabajador implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtrabajador")
	private Long idTrabajador;
	
	//@NotEmpty
	//@Size(min = 1,max = 6,message = "el código debe estar entre 1 y 6 caracteres")
	@Column(name = "username")
	private String username;
	
	@NotEmpty
	@Size(min = 1,max = 25,message = "el nombre debe contener no mas de 25 caracteres")
	@Pattern(regexp = "^([a-zA-ZñÑáéíóúÁÉÍÓÚ]+)(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)*$",message = "solo se admiten letras")
	private String nombre;
	
	@NotEmpty
	@Size(min = 1,max = 25,message = "el apellido debe contener no mas de 25 caracteres")
	@Pattern(regexp = "^([a-zA-Z]+)(\\s[a-zA-Z]+)*$",message = "solo se admiten letras")
	private String apellido;
	
	@Min(value = 900000000, message = "el teléfono debe tener 9 dígitos")
	@Max(value = 999999999, message = "el telefono no debe pasar los 9 dígitos")
	public int telefono;
	
	@NotEmpty
	@Size(min = 8, max = 8,message = "el dni debe tener 8 dígitos")
	@Pattern(regexp = "[0-9]+",message = "solo se admiten números")
	private String dni;
	
	@NotEmpty(message = "el campo no puede estar vacio")
	@Column(name = "correo")
	private String email;
	
	@NotEmpty
	@Size(min = 5,max =45, message = "se admite entre 5 y 45 caracteres")
	private String direccion;
	
	@NotEmpty
	private String rol;
	
	//@NotEmpty
	//@Size(min = 1, max = 10, message = "la contraseña no debe pasar los 8 caracteres")
	@Column(name="contraseña")
	private String password;
	
	@Column(name="recuperar")
	private String recuperar;
	
	public String getRecuperar() {
		return recuperar;
	}

	public void setRecuperar(String recuperar) {
		this.recuperar = recuperar;
	}

	@OneToMany(mappedBy = "trabajador",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Guia> guias;
		
	private static final long serialVersionUID = 1L;

	public Long getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Long idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	

	
	
	
	
}
