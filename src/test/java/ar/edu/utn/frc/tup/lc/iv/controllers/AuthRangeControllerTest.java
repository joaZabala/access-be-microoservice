package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthRangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthRangeControllerTest {

    @Mock
    private AuthRangeService authRangeService;

    @InjectMocks
    private AuthRangeController authRangeController;

    private AuthRangeDTO authRangeDTO;
    private AuthRangeRequestDTO authRangeRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authRangeDTO = new AuthRangeDTO();
        authRangeDTO.setAuthRangeId(1L);
        authRangeDTO.setDateFrom(LocalDate.now());
        authRangeDTO.setDateTo(LocalDate.now().plusDays(7));
        authRangeDTO.setHourFrom(LocalTime.of(9, 0));
        authRangeDTO.setHourTo(LocalTime.of(18, 0));
        authRangeDTO.setActive(true);

        authRangeRequestDTO = new AuthRangeRequestDTO();
        authRangeRequestDTO.setDateFrom(LocalDate.now());
        authRangeRequestDTO.setDateTo(LocalDate.now().plusDays(7));
        authRangeRequestDTO.setHourFrom(LocalTime.of(9, 0));
        authRangeRequestDTO.setHourTo(LocalTime.of(18, 0));
    }

    @Test
    void getAuthRanges_Success() {
        VisitorType visitorType = VisitorType.OWNER;
        Long docNumber = 123456L;
        Long plotId = 1L;
        List<AuthRangeDTO> expectedRanges = Arrays.asList(authRangeDTO);

        when(authRangeService.getAuthRanges(visitorType, docNumber, plotId))
                .thenReturn(expectedRanges);

        List<AuthRangeDTO> response = authRangeController.getAuthRanges(visitorType, docNumber, plotId);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(expectedRanges.size(), response.size());
        assertEquals(expectedRanges.get(0), response.get(0));
        verify(authRangeService).getAuthRanges(visitorType, docNumber, plotId);
    }

    @Test
    void getAuthRanges_EmptyList() {
        VisitorType visitorType = VisitorType.OWNER;
        Long docNumber = 123456L;
        Long plotId = 1L;

        when(authRangeService.getAuthRanges(visitorType, docNumber, plotId))
                .thenReturn(Collections.emptyList());

        List<AuthRangeDTO> response = authRangeController.getAuthRanges(visitorType, docNumber, plotId);

        assertNotNull(response);
        assertTrue(response.isEmpty());
        verify(authRangeService).getAuthRanges(visitorType, docNumber, plotId);
    }

    @Test
    void deleteAuthRange_Success() {
        Long rangeId = 1L;
        Long userId = 1L;

        when(authRangeService.deleteAuthRange(rangeId)).thenReturn(authRangeDTO);

        ResponseEntity<AuthRangeDTO> response = authRangeController.deleteAuthRange(rangeId, userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authRangeDTO, response.getBody());
        verify(authRangeService).deleteAuthRange(rangeId);
    }

    @Test
    void deleteAuthRange_ReturnsNull() {
        Long rangeId = 1L;
        Long userId = 1L;

        when(authRangeService.deleteAuthRange(rangeId)).thenReturn(null);

        ResponseEntity<AuthRangeDTO> response = authRangeController.deleteAuthRange(rangeId, userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(authRangeService).deleteAuthRange(rangeId);
    }

    @Test
    void updateAuthRange_Success() {
        Long rangeId = 1L;
        Long userId = 1L;

        when(authRangeService.updateAuthRange(eq(rangeId), any(AuthRangeRequestDTO.class)))
                .thenReturn(authRangeDTO);

        ResponseEntity<AuthRangeDTO> response = authRangeController.updateAuthRange(rangeId, authRangeRequestDTO, userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authRangeDTO, response.getBody());
        verify(authRangeService).updateAuthRange(rangeId, authRangeRequestDTO);
    }

    @Test
    void updateAuthRange_ReturnsNull() {
        Long rangeId = 1L;
        Long userId = 1L;

        when(authRangeService.updateAuthRange(eq(rangeId), any(AuthRangeRequestDTO.class)))
                .thenReturn(null);

        ResponseEntity<AuthRangeDTO> response = authRangeController.updateAuthRange(rangeId, authRangeRequestDTO, userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(authRangeService).updateAuthRange(rangeId, authRangeRequestDTO);
    }
}