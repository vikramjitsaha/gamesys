package com.vikram.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vikram.demo.controller.UserServiceController;
import com.vikram.demo.entity.UserEntity;
import com.vikram.demo.service.UserService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = UserServiceController.class, secure = false)
public class UserServiceControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@MockBean
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	@Test
	public void testFindAll() throws Exception {

		Mockito.when(this.userService.findAll())
		.thenReturn(Arrays.asList(new UserEntity("Demo", "Demo", "Demo", "Demo")));

		this.mockMvc.perform(get("/alluser"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].userName").value("Demo"))
			.andExpect(jsonPath("$[0].password").value("Demo"))
			.andExpect(jsonPath("$[0].dob").value("Demo"))
			.andExpect(jsonPath("$[0].ssn").value("Demo"));
	}

	
}
