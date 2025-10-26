package com.example.demo.sector;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class SectorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void shouldReturnOrderedSectors() throws Exception {
		mockMvc.perform(get("/api/sectors"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].name").value("Manufacturing"));
	}

	@Test
	void childShouldFollowParentInResponse() throws Exception {
		mockMvc.perform(get("/api/sectors"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[1].parentId").value(1));
	}
}
