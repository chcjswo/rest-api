package com.mocadev.restapi.events;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {

	public void validate(EventDto eventDto, Errors errors) {
		if (eventDto.getBasePrice() > eventDto.getMaxPrice() &&
			eventDto.getMaxPrice() != 0) {
			errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong.");
			errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong.");
		}

		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		if (endEventDateTime.isBefore(eventDto.getBeginEnrollmentDatetime())) {
			errors.rejectValue("endEventDateTime", "wrongValue", "EndEventDateTime is wrong.");
		}
	}
}
