package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;
import ar.edu.utn.frc.tup.lc.iv.services.VisitorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitorService visitorService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createVisitor() throws Exception {
        VisitorRequestDto visitorRequestDto = new VisitorRequestDto();
        visitorRequestDto.setOwnerId(1L);
        visitorRequestDto.setName("Mario");
        visitorRequestDto.setLastname("Cenna");
        visitorRequestDto.setDocNumber(12345678L);
        visitorRequestDto.setBirthDate(LocalDate.of(1990, 1, 1));

        VisitorDTO visitorResponseDto =
                new VisitorDTO(1L, 1L, "Mario", "Cenna", 12345678L, LocalDate.of(1990, 1, 1), true);

        when(visitorService.saveOrUpdateVisitor(visitorRequestDto)).thenReturn(visitorResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/visitor/upsert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.visitorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mario"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Cenna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.docNumber").value(12345678L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("01-01-1990"));
    }

    @Test
    void getAllVisitors() throws Exception {
        VisitorDTO visitor1 = new VisitorDTO(1L, 1L, "Mario", "Cenna", 12345678L, LocalDate.of(1990, 1, 1), true);
        VisitorDTO visitor2 = new VisitorDTO(2L, 1L, "Mary", "Jane", 87654321L, LocalDate.of(1985, 5, 20), false);

        when(visitorService.getAllVisitors(0, 10)).thenReturn(List.of(visitor1, visitor2));

        mockMvc.perform(MockMvcRequestBuilders.get("/visitor/getAll")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].visitorId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ownerId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Mario"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Cenna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].docNumber").value(12345678L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthDate").value("01-01-1990"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].visitorId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ownerId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Mary"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].docNumber").value(87654321L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].birthDate").value("20-05-1985"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].active").value(false));
    }
}
