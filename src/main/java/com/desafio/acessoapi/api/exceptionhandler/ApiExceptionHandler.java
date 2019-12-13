package com.desafio.acessoapi.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.desafio.acessoapi.domain.exception.EmailJaUtilizadoException;
import com.desafio.acessoapi.domain.exception.NaoAutorizadoException;
import com.desafio.acessoapi.domain.exception.UsuarioOuSenhaInvalidoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmailJaUtilizadoException.class)
	public ResponseEntity<Object> tratarEntidadeNaoEncontradaException(
			EmailJaUtilizadoException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), 
				HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(UsuarioOuSenhaInvalidoException.class)
	public ResponseEntity<Object> tratarUsuarioOuSenhaInvalidoException(
			UsuarioOuSenhaInvalidoException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), 
				HttpStatus.UNAUTHORIZED, request);
	}
	
	@ExceptionHandler(NaoAutorizadoException.class)
	public ResponseEntity<Object> tratarNaoAutorizadoException(
			NaoAutorizadoException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), 
				HttpStatus.UNAUTHORIZED, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problema.builder()
					.mensagem(status.getReasonPhrase())
					.build();
		} else if (body instanceof String) {
			body = Problema.builder()
					.mensagem((String) body)
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}
