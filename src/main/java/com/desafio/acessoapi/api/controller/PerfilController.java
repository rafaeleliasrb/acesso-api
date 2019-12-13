package com.desafio.acessoapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.acessoapi.domain.exception.NaoAutorizadoException;
import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/perfil")
public class PerfilController {

	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public PerfilController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping(value = "/{idUsuario}")
	Usuario buscarPerfil(@PathVariable Long idUsuario) {
		return usuarioRepository.findById(idUsuario)
				.orElseThrow(NaoAutorizadoException::new);
	}
}
