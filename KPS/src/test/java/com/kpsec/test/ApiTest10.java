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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest10 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void apiTest() throws Exception {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("strDate", "2019-01-01");
		param.add("endDate", "2019-12-31");
		
		mockMvc.perform(get("/test/getSortedUserListByAmountInPeriod").params(param))
		.andExpect(status().isOk())
		.andDo(print());
		
	}

}
