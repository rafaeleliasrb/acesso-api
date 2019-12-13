package com.desafio.acessoapi.api.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.acessoapi.api.model.UsuarioCadastro;
import com.desafio.acessoapi.domain.model.Phone;
import com.desafio.acessoapi.domain.model.Usuario;
import com.desafio.acessoapi.domain.repository.UsuarioRepository;
import com.desafio.acessoapi.domain.service.UsuarioService;
import com.desafio.acessoapi.infrastructure.security.TokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
//@WebMvcTest(CadastroController.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastroControllerTest {

	private static final ObjectMapper om = new ObjectMapper();
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenFactory tokenFactory;
	
	@MockBean
    private UsuarioRepository mockRepository;
	
	@MockBean
    private UsuarioService mockService;
	
	@Test
	public void criaUsuarioOk() throws Exception {
		String name = "João da Silva";
		String email = "joao@silva.org";
		String password = "hunter2";
		List<Phone> phones = Arrays.asList(new Phone("987654321", "21"));
		String token = tokenFactory.newToken();
		LocalDateTime now = LocalDateTime.now();

		UsuarioCadastro usuarioCadastro = new UsuarioCadastro(name, email, password, phones);
		Usuario usuario = new Usuario(1L, name, email, passwordEncoder.encode(password), phones, 
				now, now, now, token);
		
		when(mockService.cadastrar(any(Usuario.class))).thenReturn(usuario);
		
		mockMvc.perform(post("/cadastro")
                .content(om.writeValueAsString(usuarioCadastro))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.phones", hasSize(1)))
                .andExpect(jsonPath("$.phones[0].ddd", is(phones.get(0).getDdd())))
                .andExpect(jsonPath("$.phones[0].number", is(phones.get(0).getNumber())))
                .andExpect(jsonPath("$.token", is(token)));

        verify(mockService, times(1)).cadastrar(any(Usuario.class));

	}
}
