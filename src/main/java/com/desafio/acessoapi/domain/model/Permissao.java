package com.desafio.acessoapi.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Permissao {

	@Id
	private Long id;
	
	private String descricao;
}