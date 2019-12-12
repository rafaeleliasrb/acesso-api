package com.desafio.acessoapi.api.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.desafio.acessoapi.domain.exception.EmailJaUtilizadoException;
import com.desafio.acessoapi.domain.exception.UsuarioOuSenhaInvalidoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmailJaUtilizadoException.class)
	public ResponseEntity<Object> tratarEntidadeNaoEncontradaException(EmailJaUtilizadoException e) {
		Problema problema = Problema.builder()
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(UsuarioOuSenhaInvalidoException.class)
	public ResponseEntity<Object> tratarUsuarioOuSenhaInvalidoException(EmailJaUtilizadoException e) {
		Problema problema = Problema.builder()
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problema);
	}
	
//	@ExceptionHandler(NegocioException.class)
//	public ResponseEntity<Object> tratarNegocioException(NegocioException e) {
//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem(e.getMessage())
//				.build();
//		
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
//	}
}
