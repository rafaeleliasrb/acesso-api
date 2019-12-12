package com.desafio.acessoapi.domain.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.acessoapi.domain.exception.EmailJaUtilizadoException;
import com.desafio.acessoapi.domain.exception.UsuarioOuSenhaInvalidoException;
import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.repository.UsuarioRepository;
import com.desafio.acessoapi.infrastructure.security.TokenFactory;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private TokenFactory tokenFactory;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, TokenFactory tokenFactory,
			PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.tokenFactory = tokenFactory;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public Usuario cadastrar(Usuario usuario) {
		verificaEmailJaEmUso(usuario.getEmail());
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setLastLogin(LocalDateTime.now());
		usuario.setToken(tokenFactory.newToken());
		return usuarioRepository.save(usuario);
	}
	
	public Usuario logar(String email, String password) {
		Usuario usuario = usuarioRepository.findByEmail(email)
			.orElseThrow(() -> new UsuarioOuSenhaInvalidoException());
		validaSenha(usuario, password);
		return usuario;
	}

	private void validaSenha(Usuario usuario, String password) {
		if(!usuario.getPassword().equals(passwordEncoder.encode(password)))
			throw new UsuarioOuSenhaInvalidoException();
	}

	private void verificaEmailJaEmUso(String email) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
		if(usuarioExistente.isPresent())
			throw new EmailJaUtilizadoException();
	}
}
