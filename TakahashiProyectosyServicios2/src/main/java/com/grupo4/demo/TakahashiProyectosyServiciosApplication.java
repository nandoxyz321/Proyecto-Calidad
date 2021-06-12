package com.grupo4.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TakahashiProyectosyServiciosApplication implements CommandLineRunner {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	public static void main(String[] args) {
		SpringApplication.run(TakahashiProyectosyServiciosApplication.class, args);
	}

	@Override
	public void run(String... args)throws Exception	{
		String password = "12345";
		for (int i=0;i<2;i++) {
			String bcryptPassword = passwordEncode.encode(password);
			System.out.println(bcryptPassword);
		}
	}
	
}
