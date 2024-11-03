package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
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
    void getAllAccessWithCustomPagination() {
        AccessesFilter filter = new AccessesFilter();
        int page = 2;
        int size = 5;
        PaginatedResponse<AccessDTO> expectedResponse = new PaginatedResponse<>();
        expectedResponse.setItems(Collections.emptyList());
        expectedResponse.setTotalElements(0L);

        when(accessesService.getAllAccess(filter, page, size))
                .thenReturn(expectedResponse);

        PaginatedResponse<AccessDTO> response = accessController.getAllAccess(filter, page, size);

        assertNotNull(response);
        assertEquals(expectedResponse.getTotalElements(), response.getTotalElements());
        assertEquals(expectedResponse.getItems(), response.getItems());
        verify(accessesService).getAllAccess(filter, page, size);
    }

    @Test
    void getAllAccess_WithFilter() {
        AccessesFilter filter = new AccessesFilter();
        //filter.setDocNumber(123456L);
        filter.setActionType(ActionTypes.ENTRY);

        PaginatedResponse<AccessDTO> expectedResponse = new PaginatedResponse<>();
        expectedResponse.setItems(Arrays.asList(accessDTO));
        expectedResponse.setTotalElements(1L);

        when(accessesService.getAllAccess(filter, 0, 10))
                .thenReturn(expectedResponse);

        PaginatedResponse<AccessDTO> response = accessController.getAllAccess(filter, 0, 10);
        
        assertNotNull(response);
        assertEquals(expectedResponse.getTotalElements(), response.getTotalElements());
        assertEquals(expectedResponse.getItems(), response.getItems());
        verify(accessesService).getAllAccess(filter, 0, 10);
    }
}