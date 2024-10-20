package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.services.IVisitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVisitorService visitorService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createVisitor() throws Exception {
        VisitorRequest visitorRequest = new VisitorRequest();
        //visitorRequest.setOwnerId(1L);
        visitorRequest.setName("Mario");
        visitorRequest.setLastName("Cenna");
        visitorRequest.setDocNumber(12345678L);
        visitorRequest.setBirthDate(LocalDate.of(1990, 1, 1));

        VisitorDTO visitorResponseDto =
                new VisitorDTO(1L, "Mario", "Cenna", DocumentType.PASSPORT, 12345678L, LocalDate.of(1990, 1, 1), true);

        when(visitorService.saveOrUpdateVisitor(visitorRequest , null)).thenReturn(visitorResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/visitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.visitor_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mario"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Cenna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doc_number").value(12345678L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth_date").value("01-01-1990"));
    }

    @Test
    void getAllVisitors() throws Exception {
        VisitorDTO visitor1 = new VisitorDTO(1L, "Mario", "Cenna", DocumentType.CUIL,12345678L, LocalDate.of(1990, 1, 1), true);
        VisitorDTO visitor2 = new VisitorDTO(2L, "Mary", "Jane", DocumentType.CUIT,87654321L, LocalDate.of(1985, 5, 20), false);

        PaginatedResponse<VisitorDTO> paginatedResponse = new PaginatedResponse<>(List.of(visitor1, visitor2), 2);
        when(visitorService.getAllVisitors(0, 10, "", "", "")).thenReturn(paginatedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/visitors")
                        .param("page", "0")
                        .param("size", "10")
                        .param("name", "")
                        .param("lastName", "")
                        .param("filter", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].visitor_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].name").value("Mario"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].last_name").value("Cenna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].doc_number").value(12345678L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].birth_date").value("01-01-1990"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].is_active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].visitor_id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].name").value("Mary"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].last_name").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].doc_number").value(87654321L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].birth_date").value("20-05-1985"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[1].is_active").value(false));
    }

    @Test
    void getVisitorByDocNumberTest() throws Exception {
        //DTO de respuesta
        VisitorDTO visitorDto =
                new VisitorDTO(1L, "Mario", "Cenna",DocumentType.CUIT, 12345678L, LocalDate.of(1990, 1, 1), true);

        // Simulo la respuesta del servicio
        when(visitorService.getVisitorByDocNumber(12345678L)).thenReturn(visitorDto);

        // hago la solicitud GET con el número de documento
        mockMvc.perform(MockMvcRequestBuilders.get("/visitors/by-doc-number/12345678")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verifica el contenido del JSON devuelto
                .andExpect(MockMvcResultMatchers.jsonPath("$.visitor_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mario"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Cenna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doc_number").value(12345678L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth_date").value("01-01-1990"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_active").value(true));
    }

    @Test
    void getVisitorByIdTest() throws Exception {
        //DTO de respuesta
        VisitorDTO visitorDto =
                new VisitorDTO(1L, "Mario", "Cenna", DocumentType.CUIL,12345678L, LocalDate.of(1990, 1, 1), true);
        // Simulo la respuesta del servicio
        when(visitorService.getVisitorById(1L)).thenReturn(visitorDto);

        // hago la solicitud GET con el número de documento
        mockMvc.perform(MockMvcRequestBuilders.get("/visitors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(status().isOk())
                // Verifica el contenido del JSON devuelto
                .andExpect(MockMvcResultMatchers.jsonPath("$.visitor_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mario"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Cenna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doc_number").value(12345678L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth_date").value("01-01-1990"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_active").value(true));
    }
    @Test
    void deleteVisitorTest() throws Exception {
        VisitorDTO visitorDTO =
                new VisitorDTO(1L, "Mario", "Cenna", DocumentType.PASSPORT,12345678L, LocalDate.of(1990, 1, 1),false);

        when(visitorService.deleteVisitor(12345678L)).thenReturn(visitorDTO);

        // hago la petición DELETE
        mockMvc.perform(MockMvcRequestBuilders.delete("/visitors/12345678")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.visitor_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mario"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("Cenna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doc_number").value(12345678L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth_date").value("01-01-1990"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_active").value(false));
    }
}
