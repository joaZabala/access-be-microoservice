package ar.edu.utn.frc.tup.lc.iv.dtos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorDTOTest {

    @Test
    void testNoArgsConstructor() {
        ErrorDTO errorDTO = new ErrorDTO();

        assertNotNull(errorDTO);
        assertNull(errorDTO.getMessage());
    }

    @Test
    void testParameterizedConstructor() {
        String errorMessage = "Test error message";
        ErrorDTO errorDTO = new ErrorDTO(errorMessage);

        assertEquals(errorMessage, errorDTO.getMessage());
    }

    @Test
    void testGetterAndSetter() {
        ErrorDTO errorDTO = new ErrorDTO();
        String errorMessage = "Test error message";

        errorDTO.setMessage(errorMessage);
        assertEquals(errorMessage, errorDTO.getMessage());

        errorDTO.setMessage(null);
        assertNull(errorDTO.getMessage());
    }

    @Test
    void testSetterWithDifferentMessage() {
        ErrorDTO errorDTO = new ErrorDTO("Initial message");
        String newMessage = "Updated message";

        errorDTO.setMessage(newMessage);
        assertEquals(newMessage, errorDTO.getMessage());
    }
}