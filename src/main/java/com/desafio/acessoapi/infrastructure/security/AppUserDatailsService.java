package com.desafio.acessoapi.infrastructure.security;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.repository.UsuarioRepository;

@Service
public class AppUserDatailsService implements UserDetailsService {

	private UsuarioRepository repository;

	@Autowired
	public AppUserDatailsService(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<Usuario> usuarioByEmail = repository.findByEmail(email);
		Usuario usuario = usuarioByEmail
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos"));
		
		return new User(email, usuario.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_SISTEMA")));
	}

//	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
//		return usuario.getPermissoes().stream()
//				.map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase()))
//				.collect(Collectors.toSet());
//	}

}
