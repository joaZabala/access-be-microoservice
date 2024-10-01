package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.VisitAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.VisitAccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest (VisitAccessController.class)
class VisitAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitAccessService visitAccessService;

    @Test
    void getVisitAccessesTest() throws Exception {
        List<VisitAccessDTO> accessesList = new ArrayList<>();
        VisitAccessDTO visitAccessDTO = new VisitAccessDTO();
        visitAccessDTO.setName("Pedro");
        visitAccessDTO.setLastName("Casona");
        visitAccessDTO.setDocNumber(29568854L);
        accessesList.add(visitAccessDTO);
        when(visitAccessService.getVisitAccesses(1001L,
                LocalDate.of(2024,1,1),
                LocalDate.of (2024,12,31))).thenReturn(accessesList);
        this.mockMvc.perform(get("/visit-access?plot=1001&date_from=2024-01-01&date_to=2024-12-31&visitor_id="))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].last_name").value("Casona"));
        when(visitAccessService.getVisitAccessesByID(1001L, 1L,
                LocalDate.of(2024,1,1),
                LocalDate.of (2024,12,31))).thenReturn(accessesList);
        this.mockMvc.perform(get("/visit-access?plot=1001&date_from=2024-01-01&date_to=2024-12-31&visitor_id=1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].last_name").value("Casona"));
    }
}