package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.OwnerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.OwnerAccessService;
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

@WebMvcTest(OwnerAccessController.class)
class OwnerAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerAccessService ownerAccessService;


    @Test
    void getOwnerAccesses() throws Exception {
        Long plotId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();

        List<OwnerAccessDTO> mockResponse = List.of(
                new OwnerAccessDTO("Armando", "Eboli", LocalDate.now().atStartOfDay(), LocalDate.now().atStartOfDay(),
                        "Sin comentarios"));

        when(ownerAccessService.getOwnerAcessList(plotId, dateFrom, dateTo)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/owner-access")
                .param("plot_id", String.valueOf(plotId))
                .param("date_from", dateFrom.toString())
                .param("date_to", dateTo.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].first_name").value("Armando"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].last_name").value("Eboli"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].comments").value("Sin comentarios"));
    }
}