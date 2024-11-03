package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerExceptionHandlerTest {

    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new ControllerExceptionHandler();
    }

    @Test
    void handleError_Exception() {
        // Arrange
        String errorMessage = "Test general error";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<ErrorApi> response = exceptionHandler.handleError(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertErrorApiFields(response.getBody(),
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @Test
    void handleError_HttpClientErrorException() {
        // Arrange
        String errorMessage = "Bad request error";
        HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                null,
                null,
                null);

        // Act
        ResponseEntity<ErrorApi> response = exceptionHandler.handleError(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertErrorApiFields(response.getBody(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Test
    void handleError_MethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        String errorMessage = "Validation failed";
        when(exception.getMessage()).thenReturn(errorMessage);

        // Act
        ResponseEntity<ErrorApi> response = exceptionHandler.handleError(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertErrorApiFields(response.getBody(),
                errorMessage,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Test
    void handleError_ResponseStatusException() {
        // Arrange
        String reason = "Custom status error";
        ResponseStatusException exception = new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                reason);

        // Act
        ResponseEntity<ErrorApi> response = exceptionHandler.handleError(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertErrorApiFields(response.getBody(),
                reason,
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    @Test
    void handleError_EntityNotFoundException() {
        // Arrange
        String errorMessage = "Entity not found";
        EntityNotFoundException exception = new EntityNotFoundException(errorMessage);

        // Act
        ResponseEntity<ErrorApi> response = exceptionHandler.handleError(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertErrorApiFields(response.getBody(),
                errorMessage,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @Test
    void noArgsConstructor_CreatesInstance() {
        // Act
        ControllerExceptionHandler handler = new ControllerExceptionHandler();

        // Assert
        assertNotNull(handler);
    }

    private void assertErrorApiFields(ErrorApi errorApi,
                                      String expectedMessage,
                                      int expectedStatus,
                                      String expectedError) {
        assertNotNull(errorApi);
        assertEquals(expectedMessage, errorApi.getMessage());
        assertEquals(expectedStatus, errorApi.getStatus());
        assertEquals(expectedError, errorApi.getError());

        // Verificar que el timestamp es una fecha válida
        assertNotNull(errorApi.getTimestamp());
        assertDoesNotThrow(() -> {
            ZonedDateTime.parse(errorApi.getTimestamp().replace(" ", "T") + "Z");
        });
    }
}