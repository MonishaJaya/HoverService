package demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import demo.enums.ServiceErrorCodes;
import demo.exception.ValidationException;
import demo.model.response.ErrorResponse;

@ControllerAdvice
public class HooverExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> validationException(ValidationException validationException) {
		ErrorResponse errorResponse = new ErrorResponse(validationException.getErrorcode(),
				validationException.getDescription());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
				.body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exception(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse(ServiceErrorCodes.INTERNAL_ERROR.toString(),
				"Internal Server Error");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
				.body(errorResponse);
	}

}
