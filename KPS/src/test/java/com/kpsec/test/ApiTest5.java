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
public class ApiTest5 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void apiTest() throws Exception {
		String param = "[{accountNo:1000-51,statement:Y,amount:500000,depositDate:2021-12-11},{accountNo:1000-52,statement:N,amount:400000,depositDate:2021-12-12},{accountNo:1000-53,statement:Y,amount:300000,depositDate:2021-12-13}]";
		
		mockMvc.perform(get("/test/addAccountDtl").param("accountDtlInfo", param))
		.andExpect(status().isOk())
		.andDo(print());
		
	}

}
