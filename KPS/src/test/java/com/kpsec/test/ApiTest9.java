package com.kpsec.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest9 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void apiTest() throws Exception {
		String year = "2019";
		
		mockMvc.perform(get("/test/getSumAmountByYear").param("year", year))
		.andExpect(status().isOk())
		.andDo(print());
		
	}

}
