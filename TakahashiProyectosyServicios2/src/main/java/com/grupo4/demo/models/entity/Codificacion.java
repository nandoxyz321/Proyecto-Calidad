package com.grupo4.demo.models.entity;

import org.jasypt.util.text.BasicTextEncryptor;

public class Codificacion {

	private static final String MYCRYPTOKPI = "asd1235wq3";

	BasicTextEncryptor encrypnator;
	
	public Codificacion (){
		encrypnator = new BasicTextEncryptor();
		encrypnator.setPassword(MYCRYPTOKPI);
	}
	
	public String encrpt(String pass) {
		return encrypnator.encrypt(pass);
	}
	
	public String dencrpt(String pass) {
		return encrypnator.decrypt(pass);
	}
	
}
