package ar.edu.utn.frc.tup.lc.iv.advice;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @BeforeEach
    void setUp() {
        apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void handleAllExceptionsShouldReturnInternalServerError() {
        Exception exception = new RuntimeException("Test error message");

        ResponseEntity<ErrorApi> response = apiExceptionHandler.handleAllExceptions(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorApi errorApi = response.getBody();
        assertNotNull(errorApi);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorApi.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorApi.getError());
        assertEquals("Test error message", errorApi.getMessage());

        assertDoesNotThrow(() ->
                LocalDateTime.parse(errorApi.getTimestamp(),
                        DateTimeFormatter.ofPattern(DATE_PATTERN))
        );
    }

    @Test
    void handleMethodArgumentNotValidWithFieldErrorShouldReturnBadRequest() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "Custom error message");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<ErrorApi> response = apiExceptionHandler.handleMethodArgumentNotValid(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApi errorApi = response.getBody();
        assertNotNull(errorApi);
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorApi.getStatus());
        assertEquals("Custom error message", errorApi.getError());
        assertEquals("Validation failed", errorApi.getMessage());

        assertDoesNotThrow(() ->
                LocalDateTime.parse(errorApi.getTimestamp(),
                        DateTimeFormatter.ofPattern(DATE_PATTERN))
        );
    }

    @Test
    void handleMethodArgumentNotValidWithoutFieldErrorShouldReturnDefaultMessage() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());

        ResponseEntity<ErrorApi> response = apiExceptionHandler.handleMethodArgumentNotValid(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorApi errorApi = response.getBody();
        assertNotNull(errorApi);
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorApi.getStatus());
        assertEquals("Validation error occurred", errorApi.getError());
        assertEquals("Validation failed", errorApi.getMessage());
    }

    @Test
    void handleEntityNotFoundException_ShouldReturnNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

        ResponseEntity<ErrorApi> response = apiExceptionHandler.handleNotFoundExceptiob(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorApi errorApi = response.getBody();
        assertNotNull(errorApi);
        assertEquals(HttpStatus.NOT_FOUND.value(), errorApi.getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), errorApi.getError());
        assertEquals("Entity not found", errorApi.getMessage());

        assertDoesNotThrow(() ->
                LocalDateTime.parse(errorApi.getTimestamp(),
                        DateTimeFormatter.ofPattern(DATE_PATTERN))
        );
    }
}