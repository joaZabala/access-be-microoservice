package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport.EntryReport;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.dashboard.DashboardDTO;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccessControllerTest {
    @Mock
    private IAccessesService accessesService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AccessController accessController;

    private AccessDTO accessDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        accessDTO = new AccessDTO();
        accessDTO.setDocNumber(123456L);
        accessDTO.setAction(ActionTypes.ENTRY);
    }

    @Test
    void authorizeVisitorSuccess() {
        Long userId = 1L;
        when(authService.authorizeVisitor(any(AccessDTO.class), eq(userId)))
                .thenReturn(accessDTO);

        ResponseEntity<AccessDTO> response = accessController.authorizeVisitor(accessDTO, userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accessDTO, response.getBody());
        verify(authService).authorizeVisitor(accessDTO, userId);
    }

    @Test
    void authorizeVisitorNotFound() {
        Long userId = 1L;
        when(authService.authorizeVisitor(any(AccessDTO.class), eq(userId)))
                .thenReturn(null);

        ResponseEntity<AccessDTO> response = accessController.authorizeVisitor(accessDTO, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(authService).authorizeVisitor(accessDTO, userId);
    }

    @Test
    void getAllAccessSuccess() {
        AccessesFilter filter = new AccessesFilter();
        int page = 0;
        int size = 10;
        List<AccessDTO> accessList = Arrays.asList(accessDTO);
        PaginatedResponse<AccessDTO> expectedResponse = new PaginatedResponse<>();
        expectedResponse.setItems(accessList);
        expectedResponse.setTotalElements(1L);

        when(accessesService.getAllAccess(filter, page, size))
                .thenReturn(expectedResponse);

        PaginatedResponse<AccessDTO> response = accessController.getAllAccess(filter, page, size);

        assertNotNull(response);
        assertEquals(expectedResponse.getTotalElements(), response.getTotalElements());
        assertEquals(expectedResponse.getItems(), response.getItems());
        verify(accessesService).getAllAccess(filter, page, size);
    }

    @Test
    void getAllEntriesSuccess() {
        List<AccessDTO> expectedEntries = Arrays.asList(accessDTO);
        when(accessesService.getAllEntries()).thenReturn(expectedEntries);

        List<AccessDTO> response = accessController.getAllEntries();

        assertNotNull(response);
        assertEquals(expectedEntries, response);
        verify(accessesService).getAllEntries();
    }

    @Test
    void getAllExitsSuccess() {
        AccessDTO exitAccess = new AccessDTO();
        exitAccess.setAction(ActionTypes.EXIT);
        List<AccessDTO> expectedExits = Arrays.asList(exitAccess);
        when(accessesService.getAllExits()).thenReturn(expectedExits);

        List<AccessDTO> response = accessController.getAllExits();

        assertNotNull(response);
        assertEquals(expectedExits, response);
        verify(accessesService).getAllExits();
    }

    @Test
    void checkAccessSuccess() {
        String carPlate = "ABC123";
        ActionTypes action = ActionTypes.ENTRY;
        when(accessesService.canDoAction(carPlate, action)).thenReturn(true);

        Boolean response = accessController.checkAccess(carPlate, action);

        assertTrue(response);
        verify(accessesService).canDoAction(carPlate, action);
    }

    @Test
    void checkAccessDenied() {
        String carPlate = "ABC123";
        ActionTypes action = ActionTypes.EXIT;
        when(accessesService.canDoAction(carPlate, action)).thenReturn(false);

        Boolean response = accessController.checkAccess(carPlate, action);

        assertFalse(response);
        verify(accessesService).canDoAction(carPlate, action);
    }

    @Test
    void getHourlyAccessesSuccess() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 2);
        List<DashboardDTO> expectedData = Arrays.asList(
                new DashboardDTO("00:00", 5L, 2L),
                new DashboardDTO("01:00", 3L, 1L)
        );

        when(accessesService.getHourlyInfo(
                from.atStartOfDay(),
                to.atTime(LocalTime.MAX)
        )).thenReturn(expectedData);

        List<DashboardDTO> result = accessController.getHourlyAccesses(from, to);

        assertNotNull(result);
        assertEquals(expectedData, result);
        assertEquals(2L, result.get(0).getSecondaryValue());
        verify(accessesService).getHourlyInfo(
                from.atStartOfDay(),
                to.atTime(LocalTime.MAX)
        );
    }

    @Test
    void getDayOfWeekAccessesSuccess() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 7);
        List<DashboardDTO> expectedData = Arrays.asList(
                new DashboardDTO("MONDAY", 10L, 5L),
                new DashboardDTO("TUESDAY", 8L, 4L)
        );

        when(accessesService.getDayOfWeekInfo(
                from.atStartOfDay(),
                to.atTime(LocalTime.MAX)
        )).thenReturn(expectedData);

        List<DashboardDTO> result = accessController.getDayOfWeekAccesses(from, to);

        assertNotNull(result);
        assertEquals(expectedData, result);
        assertEquals(5L, result.get(0).getSecondaryValue());
        assertEquals(4L, result.get(1).getSecondaryValue());
        verify(accessesService).getDayOfWeekInfo(
                from.atStartOfDay(),
                to.atTime(LocalTime.MAX)
        );
    }

    @Test
    void getAccessByDateSuccess() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 31);
        EntryReport expectedReport = new EntryReport();
        expectedReport.setEntryCount(100L);
        expectedReport.setExitCount(90L);

        when(accessesService.getAccessByDate(from, to)).thenReturn(expectedReport);

        EntryReport result = accessController.getAccessByDate(from, to);

        assertNotNull(result);
        assertEquals(expectedReport, result);
        verify(accessesService).getAccessByDate(from, to);
    }

    @Test
    void getAccessByDateInvalidDateRange() {
        LocalDate from = LocalDate.of(2024, 2, 1);
        LocalDate to = LocalDate.of(2024, 1, 1); // from is after to

        assertThrows(IllegalArgumentException.class, () ->
                accessController.getAccessByDate(from, to)
        );
    }

    @Test
    void getAccessesVisitorTypeSuccess() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 31, 23, 59, 59, 999999999);
        List<DashboardDTO> expectedData = Arrays.asList(
                new DashboardDTO("RESIDENT", 50L, 25L),
                new DashboardDTO("VISITOR", 30L, 15L)
        );

        when(accessesService.getAccessesByVisitor(from, to)).thenReturn(expectedData);

        List<DashboardDTO> result = accessController.getAccessesVisitorType(from, to);

        assertNotNull(result);
        assertEquals(expectedData, result);
        assertEquals(25L, result.get(0).getSecondaryValue());
        assertEquals(15L, result.get(1).getSecondaryValue());
        verify(accessesService).getAccessesByVisitor(from, to);
    }

    @Test
    void getAccessesVisitorTypeInvalidDateRange() {
        LocalDateTime from = LocalDateTime.of(2024, 2, 1, 0, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 1, 23, 59, 59, 999999999);// from is after to

        assertThrows(IllegalArgumentException.class, () ->
                accessController.getAccessesVisitorType(from, to)
        );
    }

    @Test
    void getAllAccessEmptyResponse() {
        AccessesFilter filter = new AccessesFilter();
        PaginatedResponse<AccessDTO> expectedResponse = new PaginatedResponse<>();
        expectedResponse.setItems(Collections.emptyList());
        expectedResponse.setTotalElements(0L);

        when(accessesService.getAllAccess(filter, 0, 10))
                .thenReturn(expectedResponse);

        PaginatedResponse<AccessDTO> response = accessController.getAllAccess(filter, 0, 10);

        assertNotNull(response);
        assertTrue(response.getItems().isEmpty());
        assertEquals(0L, response.getTotalElements());
    }

    @Test
    void getAllEntriesEmptyList() {
        when(accessesService.getAllEntries()).thenReturn(Collections.emptyList());

        List<AccessDTO> response = accessController.getAllEntries();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void getAllExitsEmptyList() {
        when(accessesService.getAllExits()).thenReturn(Collections.emptyList());

        List<AccessDTO> response = accessController.getAllExits();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void getHourlyAccessesEmptyResult() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 2);

        when(accessesService.getHourlyInfo(
                from.atStartOfDay(),
                to.atTime(LocalTime.MAX)
        )).thenReturn(Collections.emptyList());

        List<DashboardDTO> result = accessController.getHourlyAccesses(from, to);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getDayOfWeekAccessesEmptyResult() {
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 7);

        when(accessesService.getDayOfWeekInfo(
                from.atStartOfDay(),
                to.atTime(LocalTime.MAX)
        )).thenReturn(Collections.emptyList());

        List<DashboardDTO> result = accessController.getDayOfWeekAccesses(from, to);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}