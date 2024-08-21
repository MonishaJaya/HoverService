package com.robotic.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.robotic.demo.model.request.HooverRequest;
import com.robotic.demo.model.response.HooverResponse;
import com.robotic.demo.service.HooverService;

public class HooverControllerTest {
	
	@Mock
	private HooverService hooverService;
	
	@InjectMocks
	private HooverController hooverController;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void retriveWithValidRequest_returnsCorrectResponse() {
		HooverRequest hooverRequest = new HooverRequest();
		hooverRequest.setRoomSize(new int[] {5,5});
		hooverRequest.setCoords(new int[] {1,2});
		hooverRequest.setPatches(new int[][]{ {1,2},{2,3}});
		hooverRequest.setInstructions("NNESEESWNWW");
		
		HooverResponse hooverResponse = new HooverResponse();
		hooverResponse.setCoords(new int[] {1,3});
		hooverResponse.setPatches(1);
		
		when(hooverService.cleanService(hooverRequest)).thenReturn(ResponseEntity.ok(hooverResponse));
		ResponseEntity<HooverResponse> response = hooverController.cleanService(hooverRequest);
		assertEquals(200,response.getStatusCodeValue());
		assertEquals(1,response.getBody().getCoords()[0]);
		assertEquals(3,response.getBody().getCoords()[1]);
		assertEquals(1,response.getBody().getPatches());
	}
	
	@Test
	void retrieveWithEmptyInstructions_returnsInitialCoordinates() {
		HooverRequest hooverRequest = new HooverRequest();
		hooverRequest.setRoomSize(new int[] {5,5});
		hooverRequest.setCoords(new int[] {1,2});
		hooverRequest.setPatches(new int[][]{ {1,2},{2,3}});
		hooverRequest.setInstructions("NNESEESWNWW");
		
		HooverResponse hooverResponse = new HooverResponse();
		hooverResponse.setCoords(new int[] {1,2});
		hooverResponse.setPatches(1);
		

		when(hooverService.cleanService(hooverRequest)).thenReturn(ResponseEntity.ok(hooverResponse));
		ResponseEntity<HooverResponse> response = hooverController.cleanService(hooverRequest);
		assertEquals(200,response.getStatusCodeValue());
		assertEquals(1,response.getBody().getCoords()[0]);
		assertEquals(2,response.getBody().getCoords()[1]);
		assertEquals(1,response.getBody().getPatches());
	}

}
