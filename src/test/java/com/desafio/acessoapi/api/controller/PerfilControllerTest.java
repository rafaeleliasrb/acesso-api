package com.desafio.acessoapi.api.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.desafio.acessoapi.api.filter.ExceptionHandlerFilter;
import com.desafio.acessoapi.api.filter.JwtAuthorizationFilter;
import com.desafio.acessoapi.api.model.UsuarioCadastro;
import com.desafio.acessoapi.domain.model.Phone;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PerfilControllerTest {

	private static final ObjectMapper om = new ObjectMapper();
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;
	
	@Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
			.addFilter(exceptionHandlerFilter)
			.addFilter(jwtAuthorizationFilter)
			.build();
    }
	
	@Test
	public void perfilUsuarioOk() throws Exception {
		String name = "João da Silva";
		String email = "joao@silva.org";
		String password = "hunter2";
		List<Phone> phones = Arrays.asList(new Phone("987654321", "21"));
		String accessToken = obtainAccessToken(name, password, email, phones);
		
	    mockMvc.perform(get("/perfil/1")
	      .header("Authorization", "Bearer " + accessToken)
	      .contentType("application/json;charset=UTF-8")
	      .accept("application/json;charset=UTF-8"))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.id", is(1)))
          .andExpect(jsonPath("$.name", is(name)))
          .andExpect(jsonPath("$.email", is(email)))
          .andExpect(jsonPath("$.phones", hasSize(1)))
          .andExpect(jsonPath("$.phones[0].ddd", is(phones.get(0).getDdd())))
          .andExpect(jsonPath("$.phones[0].number", is(phones.get(0).getNumber())))
          .andExpect(jsonPath("$.token", is(accessToken)));

	}
	
	@Test
	public void perfilSemBearerToken() throws Exception {
		
	    mockMvc.perform(get("/perfil/1")
			.contentType("application/json;charset=UTF-8")
			.accept("application/json;charset=UTF-8"))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.mensagem", is("N�o autorizado")));
	}
	
	@Test
	public void tokenErrado() throws Exception {
		String name = "João da Silva";
		String email = "joao@silva.org";
		String password = "hunter2";
		List<Phone> phones = Arrays.asList(new Phone("987654321", "21"));
		obtainAccessToken(name, password, email, phones);
		
		mockMvc.perform(get("/perfil/1")
			.header("Authorization", "Bearer " + "outroToken")
			.contentType("application/json;charset=UTF-8")
			.accept("application/json;charset=UTF-8"))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.mensagem", is("N�o autorizado")));

	}
	
	private String obtainAccessToken(String name, String password, String email, List<Phone> phones) throws Exception {
		  
		UsuarioCadastro usuarioCadastro = new UsuarioCadastro(name, email, password, phones);
		
		ResultActions result = mockMvc.perform(post("/cadastro")
                .content(om.writeValueAsString(usuarioCadastro))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.phones", hasSize(1)))
                .andExpect(jsonPath("$.phones[0].ddd", is(phones.get(0).getDdd())))
                .andExpect(jsonPath("$.phones[0].number", is(phones.get(0).getNumber())));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("token").toString();
	}
	
}
