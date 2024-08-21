package demo.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import demo.exception.ValidationException;
import demo.model.request.HooverRequest;

@Component
public class Validator {
	/**
	 * Validates the room size coordinates , patches, and instructions in the
	 * HooverRequest.
	 * 
	 * @param hooverRequest the request containing room size, initial coordinates,
	 *                      patches and instructions
	 * @throws ValidationException if any validation rule is violated
	 */
	public void validateRoomSize(HooverRequest hooverRequest) {
		if (hooverRequest.getRoomSize() == null) {
			throw new ValidationException("Room size is required");
		}

		if (hooverRequest.getRoomSize().length != 2) {
			throw new ValidationException("Room size must have 2 values only");
		}

		if (hooverRequest.getCoords() == null) {
			throw new ValidationException("Coordinates are required");
		}

		if (hooverRequest.getCoords().length != 2) {
			throw new ValidationException("Coordinates must have 2 values only");
		}

		if (hooverRequest.getPatches() != null) {
			for (int[] patch : hooverRequest.getPatches()) {
				if (patch.length != 2) {
					throw new ValidationException("Patches coordinate must have 2 values");
				}
			}
		}

		if (StringUtils.isNotBlank(hooverRequest.getInstructions())
				&& !hooverRequest.getInstructions().matches("[NSWE]*")) {
			throw new ValidationException("Instructions must contain only 'N', 'S', 'W', and 'E'");
		}

		isValidCoordinates(hooverRequest.getCoords(), hooverRequest.getRoomSize());
	}

	/**
	 * Validates the coordinates to ensure they are within the room size
	 * 
	 * @param coords   the current coordinates of the hoover
	 * @param roomSize the size of the room
	 * @throws ValidationException if the coordinates are out of the room bounds.
	 */
	public void isValidCoordinates(int[] coords, int[] roomSize) {
		if (coords[0] < 0 || coords[0] >= roomSize[0] || coords[1] < 0 || coords[1] >= roomSize[1]) {
			throw new ValidationException(
					"Invalid coordinates, the hoover is out of the room probably given invalid instructions.");
		}
	}
}
