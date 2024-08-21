package com.robotic.demo.exception;

import com.robotic.demo.enums.ServiceErrorCodes;

import lombok.Getter;
import lombok.Setter;

public class ValidationException extends RuntimeException {

	private String description;
	
	private String errorcode;
	
	public ValidationException(String description) {
		this.description= description;
		errorcode = ServiceErrorCodes.VALIDATION_ERROR.toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

}
