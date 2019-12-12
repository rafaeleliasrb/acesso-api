package com.desafio.acessoapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/cadastro")
public class CadastroController {

	private UsuarioService usuarioService;
	
	@Autowired
	public CadastroController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	ResponseEntity<Object> cadastrar(@RequestBody Usuario usuario) {
		Usuario usuarioNovo = usuarioService.cadastrar(usuario);
//		URI usuarioUri = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}").buildAndExpand(usuarioNovo.getId()).toUri();
//		return ResponseEntity.created(usuarioUri).body(usuarioNovo);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNovo);
	}
	
}
