package com.desafio.acessoapi.domain.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.acessoapi.domain.exception.EmailJaUtilizadoException;
import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Transactional
	public Usuario cadastrar(Usuario usuario) {
		verificaEmailJaEmUso(usuario.getEmail());
		usuario.setLastLogin(LocalDateTime.now());
		return usuarioRepository.save(usuario);
	}

	private void verificaEmailJaEmUso(String email) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
		if(usuarioExistente.isPresent())
			throw new EmailJaUtilizadoException();
	}
}
