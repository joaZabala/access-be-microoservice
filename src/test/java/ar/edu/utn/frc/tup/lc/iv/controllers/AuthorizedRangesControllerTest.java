package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.models.AuthorizedRanges;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthorizedRangesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthorizedRangesController.class)
class AuthorizedRangesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizedRangesService authorizedRangesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerAuthorizedRange() throws Exception {
        RegisterAuthorizationRangesDTO rangeDto = new RegisterAuthorizationRangesDTO();
     //   rangeDto.setAuthTypeId(1L);
        rangeDto.setVisitorId(1L);
        //rangeDto.setExternalId(123L);
        rangeDto.setDateFrom(LocalDate.of(2024, 10, 1));
        rangeDto.setDateTo(LocalDate.of(2024, 10, 31));
        rangeDto.setHourFrom(LocalTime.of(8, 0));
        rangeDto.setHourTo(LocalTime.of(17, 0));
        rangeDto.setDayOfWeeks(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        rangeDto.setPlotId(2L);
        rangeDto.setComment("Entrada para visitante");

        AuthorizedRanges authorizedRanges = new AuthorizedRanges(
                1L,
                rangeDto.getVisitorId(),
              //  rangeDto.getExternalId(),
                rangeDto.getDateFrom(),
                rangeDto.getDateTo(),
                rangeDto.getHourFrom(),
                rangeDto.getHourTo(),
                String.join(",", rangeDto.getDayOfWeeks().stream().map(DayOfWeek::name).toArray(String[]::new)),
                rangeDto.getPlotId(),
                rangeDto.getComment(),
                true 
        );

        when(authorizedRangesService.registerAuthorizedRange(rangeDto)).thenReturn(authorizedRanges);

        mockMvc.perform(MockMvcRequestBuilders.post("/authorized-ranges/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rangeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.authRangeId").value(1L))
                .andExpect(jsonPath("$.authType").value(1L))
                .andExpect(jsonPath("$.visitorId").value(1L))
                .andExpect(jsonPath("$.externalId").value(123L))
                .andExpect(jsonPath("$.dateFrom").value("01-10-2024"))
                .andExpect(jsonPath("$.dateTo").value("31-10-2024"))
                .andExpect(jsonPath("$.hourFrom").value("08:00:00"))
                .andExpect(jsonPath("$.hourTo").value("17:00:00"))
                .andExpect(jsonPath("$.days").value("MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY"))
                .andExpect(jsonPath("$.plotId").value(2L))
                .andExpect(jsonPath("$.comment").value("Entrada para visitante"));
    }
}