package com.desafio.acessoapi.domain.exception;

public class SessaoInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SessaoInvalidaException() {
		super("Sessão inválida");
	}

	public SessaoInvalidaException(String message) {
		super(message);
	}
}
