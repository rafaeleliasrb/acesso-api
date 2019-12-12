//package com.desafio.acessoapi.infrastructure.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//@Configuration
//@EnableWebSecurity
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//	private UserDetailsService userDetailsService;
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	public ResourceServerConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//		this.userDetailsService = userDetailsService;
//		this.passwordEncoder = passwordEncoder;
//	}
//
//	@Autowired
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//	}
//	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/categorias").permitAll()
//			.anyRequest().authenticated()
//			.and()
//		.cors().and()
//		.csrf().disable();
//	}
//	
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		resources.stateless(true);
//	}
//}
