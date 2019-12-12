package com.desafio.acessoapi.api.controller;

import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/perfil")
public class PerfilController {

	@GetMapping(value = "/{idUsuario}")
	ResponseEntity<Object> buscarPerfil(@PathParam("idUsuario") Long idUsuario, @RequestHeader Map<String, String> headers) {
		System.out.println("idUsuario:" + idUsuario);
		
		headers.forEach((key, value) -> {
	        System.out.println(String.format("Header '%s' = %s", key, value));
	    });
		
		return null;
	}
}
