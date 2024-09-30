package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.EmployeeAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.EmployeeAccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EmployeeAccessController.class)
class EmployeeAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeAccessService employeeAccessService;

    @Test
    void getEmployeeAccesses() throws Exception {
        EmployeeAccessDTO access1 = new EmployeeAccessDTO(
                LocalDateTime.of(2024, 9, 30, 8, 0),
                LocalDateTime.of(2024, 9, 30, 17, 0),
                "Comentario 1");
        EmployeeAccessDTO access2 = new EmployeeAccessDTO(
                LocalDateTime.of(2024, 10, 1, 8, 0),
                LocalDateTime.of(2024, 10, 1, 17, 0),
                "Comentario 2");

        when(employeeAccessService.getEmployeeAccessList("Empleado", 1L, LocalDate.of(2024, 9, 30),
                LocalDate.of(2024, 10, 1)))
                .thenReturn(List.of(access1, access2));

        mockMvc.perform(MockMvcRequestBuilders.get("/employee-access")
                .param("employee_id", "1")
                .param("date_from", "2024-09-30")
                .param("date_to", "2024-10-01")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].entry_date_time").value("2024-09-30T08:00:00"))
                .andExpect(jsonPath("$[0].exit_date_time").value("2024-09-30T17:00:00"))
                .andExpect(jsonPath("$[0].comments").value("Comentario 1"))
                .andExpect(jsonPath("$[1].entry_date_time").value("2024-10-01T08:00:00"))
                .andExpect(jsonPath("$[1].exit_date_time").value("2024-10-01T17:00:00"))
                .andExpect(jsonPath("$[1].comments").value("Comentario 2"));
    }
}