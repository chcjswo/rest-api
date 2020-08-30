package com.mocadev.restapi.events;

import static org.hamcrest.Matchers.not;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocadev.restapi.common.RestDocsConfiguration;
import com.mocadev.restapi.common.TestDescription;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	EventRepository eventRepository;

	@Test
	@TestDescription("정상적인 이벤트 생성")
	public void createEvent() throws Exception {
		EventDto event = EventDto.builder()
			.name("tony")
			.description("rest api")
			.beginEnrollmentDatetime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.closeEnrollmentDateTime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.beginEventDateTime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.endEventDateTime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.basePrice(100)
			.maxPrice(200)
			.limitOfEnrollment(100)
			.location("교보 사거리")
			.build();

//		when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaTypes.HAL_JSON)
			.content(objectMapper.writeValueAsString(event)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(header().exists("Location"))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(jsonPath("id").value(not(100)))
			.andExpect(jsonPath("free").value(not(true)))
			.andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
		;
	}

	@Test
	public void createEvent_bad_request() throws Exception {
		Event event = Event.builder()
			.id(100)
			.name("tony")
			.description("rest api")
			.beginEnrollmentDatetime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.closeEnrollmentDateTime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.beginEventDateTime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.endEventDateTime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.basePrice(100)
			.maxPrice(200)
			.limitOfEnrollment(100)
			.location("교보 사거리")
			.free(true)
			.build();

		mockMvc.perform(post("/api/events")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaTypes.HAL_JSON)
			.content(objectMapper.writeValueAsString(event)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andDo(document("create-event"))
		;
	}

	@Test
	@TestDescription("입력값이 없는 경우")
	public void createEvent_Bad_Request_Empty_Input() throws Exception {
		EventDto eventDto = EventDto.builder().build();

		this.mockMvc.perform(post("/api/events")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(eventDto)))
			.andExpect(status().isBadRequest())
			.andDo(document("create-event"))
		;
	}

	@Test
	@DisplayName("입력값이 이상한 경우")
	public void createEvent_Bad_Request_Wrong_Input() throws Exception {
		EventDto eventDto = EventDto.builder()
			.name("tony")
			.description("rest api")
			.beginEnrollmentDatetime(LocalDateTime.of(2019, 11, 13, 12, 32, 22))
			.closeEnrollmentDateTime(LocalDateTime.of(2019, 11, 12, 12, 32, 22))
			.beginEventDateTime(LocalDateTime.of(2019, 11, 11, 12, 32, 22))
			.endEventDateTime(LocalDateTime.of(2019, 11, 10, 12, 32, 22))
			.basePrice(10000)
			.maxPrice(200)
			.limitOfEnrollment(100)
			.location("교보 사거리")
			.build();

		mockMvc.perform(post("/api/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(eventDto)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$[0].objectName").exists())
//			.andExpect(jsonPath("$[0].filed").exists())
			.andExpect(jsonPath("$[0].defaultMessage").exists())
			.andExpect(jsonPath("$[0].code").exists())
//			.andExpect(jsonPath("$[0].rejectValue").exists())
			.andDo(print())
		;
	}

}