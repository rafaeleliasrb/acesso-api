package com.desafio.acessoapi.domain.exception;

public class NaoAutorizadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NaoAutorizadoException() {
		super("N�o autorizado");
	}

	public NaoAutorizadoException(String mensagem) {
		super(mensagem);
	}
	
}
