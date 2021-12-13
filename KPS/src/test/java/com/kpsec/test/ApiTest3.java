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
public class ApiTest3 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void apiTest() throws Exception {
		String param = "[{userId:1000,accountNo:1000-51},{userId:1001,accountNo:1000-52},{userId:1002,accountNo:1000-53}]";
		
		mockMvc.perform(get("/test/addAccount").param("accountInfo", param))
		.andExpect(status().isOk())
		.andDo(print());
		
	}

}
