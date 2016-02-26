package br.com.restapi;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.restapi.Application;
import br.com.restapi.controllers.ExceptionHandlerController;
import br.com.restapi.controllers.UserController;
import br.com.restapi.vo.User;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(value = Application.class)
public class UserControllerTest {

	private User user = null;
	private MockMvc mockMvc;
	HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private UserController userController;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setControllerAdvice(new ExceptionHandlerController())
				//.alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.build();
		MockitoAnnotations.initMocks(this);
		
		setAuthentication(headers);
		
		user = new User();
		user.setName("Adriano");
		user.setLastName("Bonfiglio");
		user.setBio("ok");
	}

	@Test
	public void testSaveUser() throws Exception {
		mockMvc.perform(post("/users")
				.headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name").value(user.getName()));
	}
	
	@Test
	public void testSaveUser_withErrors() throws Exception {
		user.setLastName(null);
		mockMvc.perform(post("/users")
				.headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isUnprocessableEntity())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.code").value(HttpStatus.UNPROCESSABLE_ENTITY.toString()))
			.andExpect(jsonPath("$.errors[0].field").value("lastName"));
	}	
	
	@Test
	public void testFind_AllUsers() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andReturn();
		assertTrue(result.getResponse().containsHeader("X-Total-Count"));
	}

	@Test
	public void testFind_OneUser() throws Exception {
		mockMvc.perform(get("/users/1")
				.headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name").exists());
	}
	
	@Test
	public void testFind_NotFoundUser() throws Exception {
		mockMvc.perform(get("/users/2")
				.headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
			.andExpect(jsonPath("$.message").value("Resource not Found"));
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		user.setName("test");
		mockMvc.perform(put("/users/1")
				.headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name").value("test"));
	}
	
	@Test
	public void testUpdateUser_withErrors() throws Exception {
		user.setName(null);
		mockMvc.perform(put("/users/1")
				.headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isUnprocessableEntity())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.code").value(HttpStatus.UNPROCESSABLE_ENTITY.toString()))
		.andExpect(jsonPath("$.errors[0].field").value("name"));
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(delete("/users/1")
				.headers(headers).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
	}	

	/**
	 * @param headers
	 */
	public void setAuthentication(HttpHeaders headers) {
		String login = "admin:admin";
		byte[] encodeAuth = Base64.encodeBase64(login.getBytes());
		headers.add(HttpHeaders.AUTHORIZATION, "Basic " + new String(encodeAuth));
	}
}
