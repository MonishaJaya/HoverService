package demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import demo.model.request.HooverRequest;
import demo.model.response.HooverResponse;
import demo.validator.Validator;

@Service
public class HooverService {

	@Autowired
	private Validator validator;

	/**
	 * Cleans the room based on the provided HoverRequest.
	 * 
	 * @param hooverRequest the request containing room size, initial coordinates,
	 *                      patches, and instructions
	 * @return ResponseEntity containing the final coordinates and the number of
	 *         cleaned patches
	 */
	public ResponseEntity<HooverResponse> cleanService(HooverRequest hooverRequest) {

		// Map to store the cardinal direction for each instruction
		Map<Character, int[]> directionMap = new HashMap<>();
		directionMap.put('N', new int[] { 0, 1 });
		directionMap.put('S', new int[] { 0, -1 });
		directionMap.put('E', new int[] { 1, 0 });
		directionMap.put('W', new int[] { -1, 0 });

		// validate the room size in the request
		validator.validateRoomSize(hooverRequest);

		// Initialize current coordinates and patches list
		int[] currentCoordinates = hooverRequest.getCoords();
		List<int[]> patches = new ArrayList<>(hooverRequest.getPatches() == null ? Collections.EMPTY_LIST
				: Arrays.asList(hooverRequest.getPatches()));
		AtomicInteger patchesCleaned = new AtomicInteger();

		// Process instruction if they are not blank
		if (org.apache.commons.lang3.StringUtils.isNotBlank(hooverRequest.getInstructions())) {
			hooverRequest.getInstructions().chars().forEach(instruction -> {

				// Update current coordinate based on the direction
				int[] direction = directionMap.get((char) instruction);
				currentCoordinates[0] += direction[0];
				currentCoordinates[1] += direction[1];

				// Validate the new coordinates
				validator.isValidCoordinates(currentCoordinates, hooverRequest.getRoomSize());

				// check if the current coordinates match any patch and clean it
				if (patches.stream().anyMatch(patch -> Arrays.equals(patch, currentCoordinates))) {
					patchesCleaned.getAndIncrement();
					patches.removeIf(patch -> Arrays.equals(patch, currentCoordinates));
				}
			});
		} else {
			// If no instruction , check if the initial coordinates match any patch and
			// clean it
			if (patches.stream().anyMatch(patch -> Arrays.equals(patch, currentCoordinates))) {
				patchesCleaned.getAndIncrement();
			}
		}

		// Create the response with final coordinates and number of cleaned patches
		HooverResponse hooverResponse = new HooverResponse();
		hooverResponse.setCoords(currentCoordinates);
		hooverResponse.setPatches(patchesCleaned.get());

		return ResponseEntity.ok().body(hooverResponse);
	}
}