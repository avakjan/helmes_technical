package com.example.demo.submission;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb2;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class SubmissionControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void shouldValidateAndPersistAndRefill() throws Exception {
		// initial GET me is empty
		mockMvc.perform(get("/api/submissions/me"))
			.andExpect(status().isOk());

		// invalid payload -> 400
		mockMvc.perform(post("/api/submissions")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\":\"\",\"sectorIds\":[],\"agreeToTerms\":false}"))
			.andExpect(status().isBadRequest());

		// valid payload -> 200 and returns saved submission
		MockHttpSession session = new MockHttpSession();
		mockMvc.perform(post("/api/submissions").session(session)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\":\"Alice\",\"sectorIds\":[1,18],\"agreeToTerms\":true}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Alice"))
			.andExpect(jsonPath("$.sectorIds[0]").value(1));

		// subsequent GET me -> refilled
		mockMvc.perform(get("/api/submissions/me").session(session))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Alice"));
	}
}
