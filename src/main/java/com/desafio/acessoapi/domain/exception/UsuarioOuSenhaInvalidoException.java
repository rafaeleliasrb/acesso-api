package com.desafio.acessoapi.domain.exception;

public class UsuarioOuSenhaInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioOuSenhaInvalidoException() {
		super("Usuário e/ou senha inválidos");
	}

	public UsuarioOuSenhaInvalidoException(String mensagem) {
		super(mensagem);
	}

	
}
