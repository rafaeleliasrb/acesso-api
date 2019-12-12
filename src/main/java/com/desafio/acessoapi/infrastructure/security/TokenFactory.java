package com.desafio.acessoapi.infrastructure.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenFactory {

	private AuthenticationManager authenticationManager;
	
	@Autowired
	public TokenFactory(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	// https://dev.to/keysh/spring-security-with-jwt-3j76
	public String newToken() {
		Authentication authentication = attemptAuthentication();
		User user = ((User) authentication.getPrincipal());

		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

		return Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE).setIssuer(SecurityConstants.TOKEN_ISSUER)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE).setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 864000000)).claim("rol", roles).compact();
	}

	private Authentication attemptAuthentication() {
		String username = "user";
		String password = "password";
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authenticationToken);
	}
}
