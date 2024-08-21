package validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import demo.exception.ValidationException;
import demo.model.request.HooverRequest;

import demo.validator.Validator;

public class ValidatorTest {
	private Validator validator;
	private HooverRequest hooverRequest;

	@BeforeEach
	public void setUp() {
		validator = new Validator();
		hooverRequest = new HooverRequest();
	}

	
	@Test
	void validateRoomSize_withValidRoomSizeAndCoords_doesNotThrowException() {
		hooverRequest.setRoomSize(new int[] { 5, 5 });
		hooverRequest.setCoords(new int[] { 1, 2 });
		hooverRequest.setPatches(new int[][] { { 1, 2 }, { 2, 3 } });
		assertDoesNotThrow(() -> validator.validateRoomSize(hooverRequest));
	}

	@Test void validateRoomSize_withNullRoomSize_throwsValidationException() {
		hooverRequest.setRoomSize(null);
		hooverRequest.setCoords(new int[]{1, 2}); 
		assertThrows(ValidationException.class, () -> validator.validateRoomSize(hooverRequest)); }

	@Test
	void validateRoomSize_withInvalidRoomSizeLength_throwsValidationException() {
		hooverRequest.setRoomSize(new int[] { 5 });
		hooverRequest.setCoords(new int[] { 1, 2 });
		assertThrows(ValidationException.class, () -> validator.validateRoomSize(hooverRequest));
	}

	@Test
	void validateRoomSize_withNullCoords_throwsValidationException() {
		hooverRequest.setRoomSize(new int[] { 5, 5 });
		hooverRequest.setCoords(null);
		assertThrows(ValidationException.class, () -> validator.validateRoomSize(hooverRequest));
	}

	@Test void validateRoomSize_withInvalidCoordsLength_throwsValidationException() { 
		hooverRequest.setRoomSize(new int[]{5, 5});
		hooverRequest.setCoords(new int[]{1}); 
		assertThrows(ValidationException.class, () -> validator.validateRoomSize(hooverRequest)); }

	@Test
	void validateRoomSize_withInvalidPatchLength_throwsValidationException() {
		hooverRequest.setRoomSize(new int[] { 5, 5 });
		hooverRequest.setCoords(new int[] { 1, 2 });
		hooverRequest.setPatches(new int[][] { { 1, 2 }, { 2 } });
		assertThrows(ValidationException.class, () -> validator.validateRoomSize(hooverRequest));
	}

	@Test
	void isValideCoordinates_withValidCoordinates_doesNotThrowException() {
		int[] coords = { 1, 2 };
		int[] roomSize = { 5, 5 };
		assertDoesNotThrow(() -> validator.isValidCoordinates(coords, roomSize));
	}

	@Test void isValideCoordinates_withInvalidCoordinates_throwsValidationException() {
		int[] coords = {6, 2};   int[] roomSize = {5, 5};
		assertThrows(ValidationException.class, () -> validator.isValidCoordinates(coords, roomSize)); }

	@Test
	void validateRoomSize_withInvalidInstructions_throwsValidationException() {
		hooverRequest.setRoomSize(new int[] { 5, 5 });
		hooverRequest.setCoords(new int[] { 1, 2 });
		hooverRequest.setPatches(new int[][] { { 1, 2 }, { 2, 3 } });
		hooverRequest.setInstructions("NNESEESWNWWX");
		assertThrows(ValidationException.class, () -> validator.validateRoomSize(hooverRequest));
	}

	@Test void validateRoomSize_withValidInstructions_doesNotThrowException() { 
		hooverRequest.setRoomSize(new int[]{5, 5});   
		hooverRequest.setCoords(new int[]{1, 2}); 
		hooverRequest.setPatches(new int[][]{{1, 2}, {2, 3}}); 
		hooverRequest.setInstructions("NNESEESWNWW");   
		assertDoesNotThrow(() -> validator.validateRoomSize(hooverRequest));
		}
}