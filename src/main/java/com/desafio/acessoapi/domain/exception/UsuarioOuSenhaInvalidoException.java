package com.desafio.acessoapi.domain.exception;

public class UsuarioOuSenhaInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioOuSenhaInvalidoException() {
		super("Usu�rio e/ou senha inv�lidos");
	}

	public UsuarioOuSenhaInvalidoException(String mensagem) {
		super(mensagem);
	}

	
}
