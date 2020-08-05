package com.mocadev.restapi.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void createEvent() throws Exception {
		Event event = Event.builder()
						.name("tony")
						.description("rest api")
						.beginEnrollmentDatetime(LocalDateTime.of(2019, 11, 12, 12, 32,22,3333))
						.closeEnrollmentDateTime(LocalDateTime.of(2019, 11, 12, 12, 32,22,3333))
						.beginEventDateTime(LocalDateTime.of(2019, 11, 12, 12, 32,22,3333))
						.endEventDateTime(LocalDateTime.of(2019, 11, 12, 12, 32,22,3333))
						.basePrice(100)
						.maxPrice(200)
						.limitOfEnrollment(100)
						.location("교보 사거리")
						.build();

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists());
	}

}