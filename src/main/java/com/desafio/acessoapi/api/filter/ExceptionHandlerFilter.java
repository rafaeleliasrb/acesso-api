package com.desafio.acessoapi.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.desafio.acessoapi.api.exceptionhandler.Problema;
import com.desafio.acessoapi.domain.exception.NaoAutorizadoException;
import com.desafio.acessoapi.domain.exception.SessaoInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (NaoAutorizadoException e) {
			Problema problema = Problema.builder().mensagem(e.getMessage()).build();
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(convertObjectToJson(problema));
		} catch (SessaoInvalidaException e) {
			Problema problema = Problema.builder().mensagem(e.getMessage()).build();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(convertObjectToJson(problema));
		}
}

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
