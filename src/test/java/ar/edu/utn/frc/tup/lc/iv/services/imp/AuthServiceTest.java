package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDetailDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.h2.mvstore.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {

    @MockBean
    private VisitorService visitorService;

    @MockBean
    private AuthRepository authRepository;

    @MockBean
    private AuthRangeRepository authRangeRepository;

    @MockBean
    private VisitorRepository visitorRepository;

    @MockBean
    private AuthRangeService authRangeService;

    @MockBean
    private AccessesService accessesService;

    @MockBean
    private UserRestClient userRestClient;

    @MockBean(name = "modelMapper")
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    private VisitorEntity visitorEntity;
    private AuthEntity authEntity;
    private VisitorDTO visitorDTO;
    private List<AuthRangeDTO> authRangeDTOs;
    private List<AuthEntity> authEntities;
    private AuthDTO mockAuthDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        visitorDTO = new VisitorDTO();
        visitorDTO.setVisitorId(1L);
        visitorDTO.setName("Catalina");
        visitorDTO.setLastName("Pisoni");
        visitorDTO.setDocNumber(123456L);
        visitorDTO.setDocumentType(DocumentType.DNI);
        visitorDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        visitorDTO.setActive(true);

        visitorEntity = new VisitorEntity();
        visitorEntity.setVisitorId(1L);
        visitorEntity.setName("Maria");
        visitorEntity.setLastName("Pipino");
        visitorEntity.setDocNumber(123456L);
        visitorEntity.setDocumentType(DocumentType.DNI);
        visitorEntity.setActive(true);

        authEntity = new AuthEntity();
        authEntity.setAuthId(1L);
        authEntity.setVisitor(visitorEntity);
        authEntity.setVisitorType(VisitorType.OWNER);
        authEntity.setActive(true);
        authEntity.setPlotId(1L);

        AuthRangeDTO authRangeDTO = new AuthRangeDTO();
        authRangeDTO.setDateFrom(LocalDate.of(2022, 1, 1));
        authRangeDTO.setDateTo(LocalDate.of(2022, 1, 31));
        //authRangeDTO.setDays("MONDAY,TUESDAY,WEDNESDAY");
        authRangeDTO.setActive(true);

        authRangeDTOs = new ArrayList<>();
        authRangeDTOs.add(authRangeDTO);

        authEntities = new ArrayList<>();
        authEntities.add(authEntity);

        mockAuthDTO = new AuthDTO();
        mockAuthDTO.setAuthId(1L);
        mockAuthDTO.setVisitorType(VisitorType.OWNER);
        mockAuthDTO.setActive(true);
        mockAuthDTO.setPlotId(1L);
        mockAuthDTO.setVisitor(visitorDTO);
        mockAuthDTO.setAuthRanges(authRangeDTOs);

        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);
        when(modelMapper.map(any(AuthDTO.class), eq(AuthEntity.class))).thenReturn(authEntity);
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class)))
                .thenAnswer(invocation -> {
                    AuthEntity source = invocation.getArgument(0);
                    AuthDTO target = new AuthDTO();
                    target.setVisitorType(source.getVisitorType());
                    target.setExternalID(source.getExternalID());
                    target.setPlotId(source.getPlotId());
                    return target;
                });
    }

    @Test
    void getAuthsByDocNumberTest() {
        // Setup
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setVisitorId(1L);
        List<AuthEntity> authEntities = Collections.singletonList(authEntity);
        List<AuthRangeDTO> authRangeDTOs = Collections.singletonList(new AuthRangeDTO());

        when(visitorRepository.findByDocNumber(123456L)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any(AuthEntity.class))).thenReturn(authRangeDTOs);
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);

        // Execute
        List<AuthDTO> result = authService.getAuthsByDocNumber(123456L);

        // Verify
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(visitorRepository).findByDocNumber(123456L);
        verify(authRepository).findByVisitor(visitorEntity);
    }

    @Test
    void getAuthsByTypeTest() {
        when(authRepository.findByVisitorType(VisitorType.OWNER)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any())).thenReturn(authRangeDTOs);

        List<AuthDTO> result = authService.getAuthsByType(VisitorType.OWNER);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(VisitorType.OWNER, result.get(0).getVisitorType());

        verify(authRepository).findByVisitorType(VisitorType.OWNER);
        verify(authRangeService).getAuthRangesByAuth(any());
    }

    @Test
    void getAuthsByTypeAndExternalIdTest() {
        when(authRepository.findByVisitorTypeAndExternalIDAndPlotId(
                VisitorType.OWNER, 1L, 1L)).thenReturn(authEntities);

        List<AuthDTO> result = authService.getAuthsByTypeAndExternalId(VisitorType.OWNER, 1L, 1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(VisitorType.OWNER, result.get(0).getVisitorType());

        verify(authRepository).findByVisitorTypeAndExternalIDAndPlotId(VisitorType.OWNER, 1L, 1L);
    }

//    @Test
//    void updateAuthorizationTest(){
//
//        AuthDTO authDTO = new AuthDTO();
//        authDTO.setAuthId(1L);
//        authDTO.setVisitorType(VisitorType.OWNER);
//        authDTO.setVisitor(new VisitorDTO());
//        authDTO.setAuthRanges(new ArrayList<>());
//
//    }
    @Test
    void getAuthsByTypeAndExternalIdShouldReturnAuthDTOsList() {
        VisitorType visitorType = VisitorType.OWNER;
        Long externalID = 1L;
        Long plotId = 2L;

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitorType(visitorType);
        authEntity.setExternalID(externalID);
        authEntity.setPlotId(plotId);

        when(authRepository.findByVisitorTypeAndExternalIDAndPlotId(visitorType, externalID, plotId))
                .thenReturn(List.of(authEntity));
        when(modelMapper.map(authEntity, AuthDTO.class)).thenReturn(new AuthDTO());

        List<AuthDTO> result = authService.getAuthsByTypeAndExternalId(visitorType, externalID, plotId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void createNewAuthorizationShouldReturnNewAuthDTO() {
        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(12345L);

        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);
        visitorAuthRequest.setPlotId(2L);
        visitorAuthRequest.setExternalID(1L);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitorType(VisitorType.OWNER);
        authEntity.setExternalID(1L);

        when(authRepository.save(any(AuthEntity.class))).thenReturn(authEntity);
        when(modelMapper.map(authEntity, AuthDTO.class)).thenReturn(new AuthDTO());

        AuthDTO result = authService.createNewAuthorization(visitorDTO, visitorAuthRequest);

        assertNotNull(result);
        assertEquals(VisitorType.OWNER, result.getVisitorType());
    }


    @Test
    void testAuthorizeVisitor_NoValidAuths() {
        // Arrange
        AccessDTO accessDTO = new AccessDTO();
        accessDTO.setDocNumber(12345L);

        when(authService.getValidAuthsByDocNumber(12345L)).thenReturn(Collections.emptyList());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            authService.authorizeVisitor(accessDTO, 1L);
        });

        assertEquals("No existen autorizaciones validas para el documento 12345", thrown.getMessage());
    }


    @Test
    void isAuthorizedTest_False() {
        // Arrange
        when(authService.getValidAuthsByDocNumber(12345L)).thenReturn(Collections.emptyList());

        // Act
        boolean result = authService.isAuthorized(12345L);

        // Assert
        assertFalse(result);
    }
    @Test
    void getAllAuthsTest() {
        // Arrange
        AuthFilter filter = new AuthFilter();
        int page = 0;
        int size = 10;

        List<AuthEntity> authEntities = new ArrayList<>();
        authEntities.add(authEntity);

        PageImpl<AuthEntity> authPage = new PageImpl<>(authEntities);

        when(authRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(authPage);
        when(userRestClient.getUsersByIds(anyList())).thenReturn(Collections.emptyList());

        // Act
        List<AuthDTO> result = authService.getAllAuths(filter, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

//    @Test
//    void getValidAuthsByDocNumberTest() {
//        // Setup
//        List<AuthDTO> mockAuths = Collections.singletonList(mockAuthDTO);
//        List<AuthRangeDTO> validRanges = Collections.singletonList(new AuthRangeDTO());
//
//        when(authService.getAuthsByDocNumber(123456L)).thenReturn(mockAuths);
//        when(authRangeService.getValidAuthRanges(anyList(), any(LocalDate.class), any(LocalTime.class)))
//                .thenReturn(validRanges);
//
//        // Execute
//        List<AuthDTO> result = authService.getValidAuthsByDocNumber(123456L);
//
//        // Verify
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(authRangeService).getValidAuthRanges(anyList(), any(LocalDate.class), any(LocalTime.class));
//    }

    @Test
    void updateAuthorizationTest() {
        // Arrange
        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setAuthRangeRequest(new ArrayList<>());

        when(authRangeService.registerAuthRanges(anyList(), any(AuthEntity.class), any(VisitorDTO.class)))
                .thenReturn(new ArrayList<>());

        // Act
        AuthDTO result = authService.updateAuthorization(mockAuthDTO, visitorDTO, visitorAuthRequest);

        // Assert
        assertNotNull(result);
        assertEquals(mockAuthDTO.getAuthId(), result.getAuthId());
    }

//    @Test
//    void createAuthorizationTest() {
//        VisitorAuthRequest request = new VisitorAuthRequest();
//        VisitorRequest visitorRequest = new VisitorRequest();
//        visitorRequest.setDocNumber(123456L);
//        request.setVisitorRequest(visitorRequest);
//        request.setVisitorType(VisitorType.OWNER);
//        request.setPlotId(1L);
//        request.setAuthRangeRequest(new ArrayList<>());
//
//        when(visitorService.getVisitorByDocNumber(123456L)).thenReturn(null);
//        when(visitorService.saveOrUpdateVisitor(any(), any())).thenReturn(visitorDTO);
//        //when(authService.findExistingAuthorization(any())).thenReturn(Optional.empty());
//        when(authService.createNewAuthorization(any(), any())).thenReturn(mockAuthDTO);
//
//        AuthDTO result = authService.createAuthorization(request, 1L);
//
//        assertNotNull(result);
//        verify(visitorService).getVisitorByDocNumber(123456L);
//        verify(visitorService).saveOrUpdateVisitor(any(), any());
//        verify(authService).findExistingAuthorization(any());
//    }

//    @Test
//    void authorizeVisitorTest() {
//        AccessDTO accessDTO = new AccessDTO();
//        accessDTO.setDocNumber(123456L);
//        accessDTO.setAction(ActionTypes.ENTRY);
//
//        AuthDTO authDTO = new AuthDTO();
//        authDTO.setAuthId(1L);
//        authDTO.setPlotId(1L);
//
//        when(authService.getValidAuthsByDocNumber(123456L)).thenReturn(Collections.singletonList(authDTO));
//        when(authRepository.findById(1L)).thenReturn(Optional.of(authEntity));
//        when(accessesService.registerAccess(any(AccessEntity.class))).thenReturn(new AccessDTO());
//
//        AccessDTO result = authService.authorizeVisitor(accessDTO, 1L);
//
//        assertNotNull(result);
//    }


    @Test
    void deleteAuthorizationTest() {
        // Arrange
        AuthEntity authEntity = new AuthEntity();
        authEntity.setAuthId(1L);

        when(authRepository.findByAuthId(1L)).thenReturn(authEntity);

        // Act
        AuthDTO result = authService.deleteAuthorization(1L);

        // Assert
        assertNull(result);
        assertFalse(authEntity.isActive());
        verify(authRepository).save(authEntity);
    }

    @Test
    void activateAuthorizationTest() {
        // Arrange
        AuthEntity authEntity = new AuthEntity();
        authEntity.setAuthId(1L);

        when(authRepository.findByAuthId(1L)).thenReturn(authEntity);

        // Act
        AuthDTO result = authService.activateAuthorization(1L);

        // Assert
        assertNull(result);
        assertTrue(authEntity.isActive());
        verify(authRepository).save(authEntity);
    }

    @Test
    void updateAuthorization2Test() {
        // Setup
        VisitorAuthRequest request = new VisitorAuthRequest();
        request.setAuthRangeRequest(new ArrayList<>());
        List<AuthRange> authRanges = new ArrayList<>();

        when(authRangeService.registerAuthRanges(anyList(), any(), any())).thenReturn(authRanges);

        // Execute
        AuthDTO result = authService.updateAuthorization(mockAuthDTO, visitorDTO, request);

        // Verify
        assertNotNull(result);
        verify(authRangeService).registerAuthRanges(anyList(), any(), any());
    }

    @Test
    void findExistingAuthorizationTest() {
        // Setup
        VisitorAuthRequest request = new VisitorAuthRequest();
        VisitorRequest visitorRequest = new VisitorRequest();
        visitorRequest.setDocNumber(123456L);
        request.setVisitorRequest(visitorRequest);
        request.setVisitorType(VisitorType.OWNER);
        request.setPlotId(1L);

        // Mock las dependencias subyacentes en lugar del método getAuthsByDocNumber
        VisitorEntity visitorEntity = new VisitorEntity();
        List<AuthEntity> authEntities = Collections.singletonList(authEntity);
        List<AuthRangeDTO> authRangeDTOs = Collections.singletonList(new AuthRangeDTO());

        when(visitorRepository.findByDocNumber(123456L)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any(AuthEntity.class))).thenReturn(authRangeDTOs);

        // Configurar el modelMapper para devolver el mockAuthDTO que ya está configurado en setUp()
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);
        mockAuthDTO.setVisitorType(VisitorType.OWNER);
        mockAuthDTO.setPlotId(1L);

        // Execute
        Optional<AuthDTO> result = authService.findExistingAuthorization(request);

        // Verify
        assertTrue(result.isPresent());
        assertEquals(VisitorType.OWNER, result.get().getVisitorType());
        assertEquals(1L, result.get().getPlotId());

        // Verify las llamadas a los métodos mockeados
        verify(visitorRepository).findByDocNumber(123456L);
        verify(authRepository).findByVisitor(visitorEntity);
        verify(authRangeService).getAuthRangesByAuth(any(AuthEntity.class));
    }

    @Test
    void findExistingAuthorizationTest_NoMatch() {
        // Setup
        VisitorAuthRequest request = new VisitorAuthRequest();
        VisitorRequest visitorRequest = new VisitorRequest();
        visitorRequest.setDocNumber(123456L);
        request.setVisitorRequest(visitorRequest);
        request.setVisitorType(VisitorType.OWNER);
        request.setPlotId(2L); // Diferente plotId para que no haya coincidencia

        VisitorEntity visitorEntity = new VisitorEntity();
        List<AuthEntity> authEntities = Collections.singletonList(authEntity);
        List<AuthRangeDTO> authRangeDTOs = Collections.singletonList(new AuthRangeDTO());

        when(visitorRepository.findByDocNumber(123456L)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any(AuthEntity.class))).thenReturn(authRangeDTOs);

        // Configurar el modelMapper para devolver un DTO con diferentes valores
        AuthDTO differentAuthDTO = new AuthDTO();
        differentAuthDTO.setVisitorType(VisitorType.OWNER);
        differentAuthDTO.setPlotId(1L); // Diferente del plotId en el request
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(differentAuthDTO);

        // Execute
        Optional<AuthDTO> result = authService.findExistingAuthorization(request);

        // Verify
        assertFalse(result.isPresent());
    }

}

