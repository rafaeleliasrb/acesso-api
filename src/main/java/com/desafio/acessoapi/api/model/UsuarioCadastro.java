package com.desafio.acessoapi.api.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.desafio.acessoapi.domain.model.Phone;
import com.desafio.acessoapi.domain.model.Usuario;

import lombok.Data;

@Data
public class UsuarioCadastro {

	private Long id;
	private String name;
	private String email;
	private String password;
	private List<Phone> phones;
	private LocalDateTime created;
	private LocalDateTime modified;
	private LocalDateTime lastLogin;
	private String token;
	
	public UsuarioCadastro(Usuario usuario, String token) {
		BeanUtils.copyProperties(usuario, this);
		this.token = token;
	}
}
