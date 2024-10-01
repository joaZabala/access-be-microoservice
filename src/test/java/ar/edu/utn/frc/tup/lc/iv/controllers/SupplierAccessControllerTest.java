package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.SupplierAccessService;
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

@WebMvcTest(SupplierAccessController.class)
class SupplierAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierAccessService supplierAccessService;


    @Test
    void getSupplierAccesses() throws Exception {
        Long supplierId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();

        List<WorkerAccessDTO> mockResponse = List.of(
                new WorkerAccessDTO(1L, LocalDate.now().atStartOfDay(), LocalDate.now().atStartOfDay(), "Auto color rojo",
                        "ABC123", "Descripcion Ejemplo", "Comentario de ejemplo"));

        when(supplierAccessService.getSupplierAccessList("Proveedor", supplierId, dateFrom, dateTo))
                .thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/supplier-access")
                .param("supplier_id", String.valueOf(supplierId))
                .param("date_from", dateFrom.toString())
                .param("date_to", dateTo.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].external_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].car_description").value("Auto color rojo"));
    }
}