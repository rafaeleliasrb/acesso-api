package com.desafio.acessoapi.api.model;

import java.util.List;

import com.desafio.acessoapi.domain.model.Phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastro {

	private String name;
	private String email;
	private String password;
	private List<Phone> phones;
}
