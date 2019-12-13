package com.desafio.acessoapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public LoginController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	Usuario login(@RequestBody UsuarioLogin usuarioLogin) {
		Usuario usuarioLogado = usuarioService.logar(usuarioLogin.getEmail(), usuarioLogin.getPassword());
		return usuarioLogado;
	}
}
