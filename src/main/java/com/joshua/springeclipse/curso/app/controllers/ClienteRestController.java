package com.joshua.springeclipse.curso.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joshua.springeclipse.curso.app.models.services.IClienteService;
import com.joshua.springeclipse.curso.app.view.xml.ClienteList;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {


	@Autowired
	private IClienteService  clienteService;
	
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/listar")
	public ClienteList index() {
	return  new ClienteList( clienteService.findAll());
	
	}
	
}
