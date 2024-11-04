package ar.edu.utn.frc.tup.lc.iv.advice;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Api exception handler.
 */
@RestControllerAdvice
@Getter
@NoArgsConstructor
public class ApiExceptionHandler {

    /**
     * Date formater string.
     */
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    /**
     * Handles unhandled exceptions and returns a structured error response.
     *
     * @param ex the exception that provides the error message for the response.
     * @return a {@link ResponseEntity} with an {@link ErrorApi} object,
     *         status {@code 500 Internal Server Error},
     *         and includes a timestamp, status, error name, and message.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApi> handleAllExceptions(Exception ex) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ErrorApi error = ErrorApi.builder()
                .timestamp(timeStamp)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation errors for method arguments.
     *
     * @param ex the exception with validation errors.
     * @return a {@link ResponseEntity} with an {@link ErrorApi} object,
     *         status {@code 400 Bad Request}, and validation error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApi> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER));

        String error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation error occurred");

        ErrorApi errorApi = ErrorApi.builder()
                .timestamp(timestamp)
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .error(error)
                .build();

        return new ResponseEntity<>(errorApi, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors for method arguments.
     *
     * @param ex the exception with validation errors.
     * @return a {@link ResponseEntity} with an {@link ErrorApi} object,
     *         status {@code 404 Not Found}, and validation error details.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorApi> handleNotFoundExceptiob(EntityNotFoundException ex) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ErrorApi error = ErrorApi.builder()
                .timestamp(timeStamp)
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
