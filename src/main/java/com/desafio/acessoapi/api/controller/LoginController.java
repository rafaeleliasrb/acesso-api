package com.desafio.acessoapi.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.acessoapi.api.model.UsuarioLogin;
import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.service.UsuarioService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private UsuarioService usuarioService;
	
	public LoginController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	@PostMapping
	ResponseEntity<Object> login(@RequestBody UsuarioLogin usuarioLogin) {
		Usuario usuarioLogado = usuarioService.logar(usuarioLogin.getEmail(), usuarioLogin.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioLogado);
	}
}
