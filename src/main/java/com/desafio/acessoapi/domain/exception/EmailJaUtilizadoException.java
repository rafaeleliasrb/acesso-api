package com.desafio.acessoapi.domain.exception;

public class EmailJaUtilizadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailJaUtilizadoException(String mensagem) {
		super(mensagem);
	}
	
	public EmailJaUtilizadoException() {
		super("E-mail já existente");
	}
}
