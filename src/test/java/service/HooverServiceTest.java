package service;

import demo.exception.ValidationException;
import demo.model.request.HooverRequest;
import demo.model.response.HooverResponse;
import demo.service.HooverService;
import demo.validator.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class HooverServiceTest {

    @Mock
    private Validator validator;

    @InjectMocks
    private HooverService hooverService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void cleanService_withValidRequest_returnsCorrectResponse() {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setRoomSize(new int[]{5, 5});
        hooverRequest.setCoords(new int[]{1, 2});
        hooverRequest.setPatches(new int[][]{{1, 2}, {2, 3}});
        hooverRequest.setInstructions("NNESEESWNWW");

        doNothing().when(validator).validateRoomSize(hooverRequest);
        doNothing().when(validator).isValidCoordinates(any(int[].class), any(int[].class));

        ResponseEntity<HooverResponse> response = hooverService.cleanService(hooverRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getCoords()[0]);
        assertEquals(3, response.getBody().getCoords()[1]);
        assertEquals(1, response.getBody().getPatches());
    }

    @Test
    public void cleanService_withInvalidRoomSize_throwsValidationException() {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setRoomSize(new int[]{5});

        doThrow(new ValidationException("Invalid room size")).when(validator).validateRoomSize(hooverRequest);

        assertThrows(ValidationException.class, () -> hooverService.cleanService(hooverRequest));
    }

    @Test
    public void cleanService_withInvalidCoordinates_throwsValidationException() {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setRoomSize(new int[]{5, 5});
        hooverRequest.setCoords(new int[]{6, 6}); // Invalid coordinates
        hooverRequest.setPatches(new int[][]{{1, 2}, {2, 3}});

        doThrow(new ValidationException("Invalid coordinates")).when(validator).validateRoomSize(hooverRequest);

        assertThrows(ValidationException.class, () -> hooverService.cleanService(hooverRequest));
    }
}