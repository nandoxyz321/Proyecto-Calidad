package com.grupo4.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/principal")
public class InicioController {

	@RequestMapping("")
	public String index() {
		return "principal/index";
	}
}
