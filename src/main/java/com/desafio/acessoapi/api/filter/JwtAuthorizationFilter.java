package com.desafio.acessoapi.api.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.desafio.acessoapi.domain.exception.NaoAutorizadoException;
import com.desafio.acessoapi.domain.exception.SessaoInvalidaException;
import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.repository.UsuarioRepository;
import com.desafio.acessoapi.infrastructure.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    
    private UsuarioRepository usuarioRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
    		UsuarioRepository usuarioRepository) {
        super(authenticationManager);
		this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    		FilterChain filterChain) throws IOException, ServletException {
    	
    	UsernamePasswordAuthenticationToken authentication = null;
//    	try {
    		authentication = getAuthentication(request);
//    	} catch (NaoAutorizadoException e) {
//    		int codigo = HttpStatus.FORBIDDEN.ordinal();
//    		adicionaErroNoResponse(response, e, codigo);
//    		throw new NaoAutorizadoException();
//    	}

    	if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
    	
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

//	private void adicionaErroNoResponse(HttpServletResponse response, Exception exception, int codigo) {
//		ObjectMapper mapper = new ObjectMapper();
//		Problema problema = Problema.builder()
//				.mensagem(exception.getMessage())
//				.build();
//		response.setStatus(codigo);
//		response.setContentType("application/json");
//		try {
//			response.getWriter().write(mapper.writeValueAsString(problema));
//		} catch (IOException e) {
//			log.error("Erro ao adicionar erro ao Response");
//		}
//	}

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.hasLength(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace("Bearer ", ""));
                
                validaTokenUsuario(request, token.replace("Bearer ", ""));
                
                String username = parsedToken.getBody().getSubject();
                List<GrantedAuthority> authorities = getAuthorities(parsedToken);
                if (StringUtils.hasLength(username)) {
                    return new UsernamePasswordAuthenticationToken(username, 
                    		Arrays.asList(new SimpleGrantedAuthority("ROLE_SISTEMA")), authorities);
                }
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
            		SignatureException | IllegalArgumentException exception) {
                throw new NaoAutorizadoException();
            }
        }

        return null;
    }

	private void validaTokenUsuario(HttpServletRequest request, String token) {
		Long id = getIdDaURL(request);
		Usuario usuario = usuarioRepository.findById(id)
			.orElseThrow(NaoAutorizadoException::new);
		if(!usuario.getToken().equals(token))
			throw new NaoAutorizadoException();
		boolean isTokenTemMaisDe30Minutos = LocalDateTime.now().isAfter(usuario.getLastLogin().plusMinutes(30));
		if(isTokenTemMaisDe30Minutos)
			throw new SessaoInvalidaException();
	}

	private Long getIdDaURL(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String[] urlParts = url.split("/");
		return Long.valueOf(urlParts[urlParts.length-1]);
	}

	private List<GrantedAuthority> getAuthorities(Jws<Claims> parsedToken) {
		return ((List<?>) parsedToken.getBody()
		    .get("rol")).stream()
		    .map(authority -> new SimpleGrantedAuthority((String) authority))
		    .collect(Collectors.toList());
	}
}
