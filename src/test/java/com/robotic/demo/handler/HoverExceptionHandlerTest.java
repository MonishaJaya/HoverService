package com.robotic.demo.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.robotic.demo.enums.ServiceErrorCodes;
import com.robotic.demo.exception.ValidationException;
import com.robotic.demo.model.response.ErrorResponse;

public class HoverExceptionHandlerTest {
	
	@InjectMocks
	private HooverExceptionHandler hooverExceptionHandler;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void exception_withValidationException_returnsUnprocessableEntity() {
		ValidationException validationException = new ValidationException("Invalid input");
		ResponseEntity<ErrorResponse> response = hooverExceptionHandler.validationException(validationException);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals("VALIDATION_ERROR", response.getBody().getCode());
		assertEquals("Invalid input", response.getBody().getMessage());
	}
	
	@Test
	void exception_withGenericException_returnsInternalServerError() {
		Exception exception = new Exception("Some error");
		ResponseEntity<ErrorResponse> response = hooverExceptionHandler.exception(exception);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());
		assertEquals(ServiceErrorCodes.INTERNAL_ERROR.toString(),response.getBody().getCode());
		assertEquals("Internal Server Error", response.getBody().getMessage());
	}

}
