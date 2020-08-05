package com.mocadev.restapi.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EventsTest {
	@Test
	public void builder() {
		Event event = Event.builder()
			.name("Rest API")
			.description("rest api description")
			.build();
		assertThat(event).isNotNull();
	}

	@Test
	public void javaBean() {
		// Given
		String name = "Event";
		String desc = "Spring";

		// When
		Event event = new Event();
		event.setName(name);
		event.setDescription(desc);

		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(desc);
	}

}