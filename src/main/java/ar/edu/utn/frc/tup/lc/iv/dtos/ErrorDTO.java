package ar.edu.utn.frc.tup.lc.iv.dtos;

import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for error messages.
 */
@NoArgsConstructor
public class ErrorDTO {

    /**
     * Error message to be returned in the response.
     */
    private String message;

    /**
     * Constructor for creating an ErrorDTO with a message.
     *
     * @param messageText the error message
     */
    public ErrorDTO(String messageText) {
        this.message = messageText;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param messageText the error message
     */
    public void setMessage(String messageText) {
        this.message = messageText;
    }
}
