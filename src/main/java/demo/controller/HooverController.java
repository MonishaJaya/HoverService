package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.model.request.HooverRequest;
import demo.model.response.ErrorResponse;
import demo.model.response.HooverResponse;
import demo.service.HooverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RequestMapping("/hoover/service")
@RestController
public class HooverController {

	@Autowired
	private HooverService hooverService;

	@PostMapping(value = "/clean", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Service that navigates an imaginary robotic hoover", description = "Final coordinates of the hoover and patches is the number of cleaned patches.", extensions = {
			@Extension(properties = { @ExtensionProperty(name = "x-lifecycleStatus", value = "ACTIVE"),
					@ExtensionProperty(name = "x-retirementDate", value = "") }) }, responses = {
							@ApiResponse(responseCode = "200", description = "Request received", content = @Content(schema = @Schema(implementation = String.class))),
							@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<HooverResponse> cleanService(@RequestBody HooverRequest hooverRequest) {
		return hooverService.cleanService(hooverRequest);
	}

}
