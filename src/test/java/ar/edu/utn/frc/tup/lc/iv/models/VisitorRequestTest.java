package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitorRequestTest {

    private final ObjectMapper objectMapper;

    public VisitorRequestTest() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
    }

    @Test
    public void testSerialization() throws Exception {
        VisitorRequest request = new VisitorRequest();
        request.setBirthDate(LocalDate.of(2024, 9, 18));

        String json = objectMapper.writeValueAsString(request);
        assertEquals("{\"birth_date\":\"18-09-2024\"}", json);
    }

    @Test
    public void testDeserialization() throws Exception {
        String json = "{\"birth_date\":\"18-09-2024\"}";

        VisitorRequest request = objectMapper.readValue(json, VisitorRequest.class);
        assertEquals(LocalDate.of(2024, 9, 18), request.getBirthDate());
    }
}