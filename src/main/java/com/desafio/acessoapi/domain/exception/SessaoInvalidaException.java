package com.desafio.acessoapi.domain.exception;

public class SessaoInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SessaoInvalidaException() {
		super("Sess�o inv�lida");
	}

	public SessaoInvalidaException(String message) {
		super(message);
	}
}
