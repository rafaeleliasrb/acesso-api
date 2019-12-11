package com.desafio.acessoapi.domain.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Phone {
	
	private String number;
	private String ddd;
}
