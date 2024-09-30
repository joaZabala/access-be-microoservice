package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.WorkerAccessService;
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

@WebMvcTest(WorkerAccessController.class)
class WorkerAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkerAccessService workerAccessService;


    @Test
    void getWorkerAccessWithoutWorkerId() throws Exception {
        Long plotId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();

        List<WorkerAccessDTO> mockResponse = List.of(
                new WorkerAccessDTO(1L, LocalDate.now().atStartOfDay(), LocalDate.now().atStartOfDay(), "Auto Rojo",
                        "ABC123", "Descripcion Ejemplo", "Comentario de ejemplo"));

        when(workerAccessService.getWorkerAccessListByPlot("Trabajador", plotId, dateFrom, dateTo))
                .thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/worker-access")
                .param("plot_id", String.valueOf(plotId))
                .param("date_from", dateFrom.toString())
                .param("date_to", dateTo.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].external_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].car_description").value("Auto Rojo"));
    }

    @Test
    void getWorkerAccessWithWorkerId() throws Exception {
        Long workerId = 1L;
        Long plotId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();

        List<WorkerAccessDTO> mockResponse = List.of(
                new WorkerAccessDTO(workerId, LocalDate.now().atStartOfDay(), LocalDate.now().atStartOfDay(), "Auto Rojo",
                        "ABC123", "Descripcion Ejemplo", "Comentario de ejemplo"));

        when(workerAccessService.getWorkerAccessListById("Trabajador", workerId, dateFrom, dateTo))
                .thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/worker-access")
                .param("worker_id", String.valueOf(workerId))
                .param("plot_id", String.valueOf(plotId))
                .param("date_from", dateFrom.toString())
                .param("date_to", dateTo.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].external_id").value(workerId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].car_description").value("Auto Rojo"));
    }
}