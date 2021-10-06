package com.mocadev.restapi.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

	@ParameterizedTest
//	@CsvSource(value = {
//		"0, 0, true",
//		"100, 0, false",
//		"0, 100, false"
//	})
	@MethodSource("paramsForTestFree")
	public void testFree(int basePrice, int maxPrice, boolean isFree) {
		// Given
		Event event = Event.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();

		// When
		event.update();

		// Then
		assertThat(event.isFree()).isEqualTo(isFree);
	}

	private static Object[] paramsForTestFree() {
		return new Object[] {
			new Object[] {0, 0, true},
			new Object[] {100, 0, false},
			new Object[] {0, 100, false},
		};
	}

	@Test
	public void testOffline() {
		// Given
		Event event = Event.builder()
			.location("논현동")
			.build();

		// When
		event.update();

		// Then
		assertThat(event.isOffline()).isTrue();

		// Given
		event = Event.builder()
			.build();

		// When
		event.update();

		// Then
		assertThat(event.isOffline()).isFalse();
	}

}
