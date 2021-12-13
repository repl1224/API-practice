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
public class ApiTest1 {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void apiTest() throws Exception {
		String param = "[{userId:1000,userName:test1000,userAge:25,jionDate:2020-12-11},{userId:1001,userName:test1001,userAge:26,jionDate:2020-12-12},{userId:1002,userName:test1002,userAge:27,jionDate:2020-12-13}]";
		
		mockMvc.perform(get("/test/addUser").param("userInfo", param))
			.andExpect(status().isOk())
			.andDo(print());
		
	}

}
