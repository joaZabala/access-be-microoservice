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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setVisitorId(1L);
        List<AuthEntity> authEntities = Collections.singletonList(authEntity);
        List<AuthRangeDTO> authRangeDTOs = Collections.singletonList(new AuthRangeDTO());

        when(visitorRepository.findByDocNumber(123456L)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any(AuthEntity.class))).thenReturn(authRangeDTOs);
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);

        List<AuthDTO> result = authService.getAuthsByDocNumber(123456L);

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
    void createNewAuthorizationPROVIDER() {
        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(12345L);

        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.PROVIDER);
        visitorAuthRequest.setPlotId(2L);
        visitorAuthRequest.setExternalID(1L);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitorType(VisitorType.PROVIDER);
        authEntity.setExternalID(1L);

        when(authRepository.save(any(AuthEntity.class))).thenReturn(authEntity);
        when(modelMapper.map(authEntity, AuthDTO.class)).thenReturn(new AuthDTO());

        AuthDTO result = authService.createNewAuthorization(visitorDTO, visitorAuthRequest);

        assertNotNull(result);
        assertEquals(VisitorType.PROVIDER, result.getVisitorType());
    }

    @Test
    void testAuthorizeVisitorNoValidAuths() {
        Long documentNumber = 12345L;
        AccessDTO accessDTO = new AccessDTO();
        accessDTO.setDocNumber(documentNumber);

        VisitorEntity mockVisitor = new VisitorEntity();
        when(visitorRepository.findByDocNumber(documentNumber)).thenReturn(mockVisitor);

        when(authRepository.findByVisitor(mockVisitor)).thenReturn(Collections.emptyList());

        when(authService.getValidAuthsByDocNumber(documentNumber)).thenReturn(Collections.emptyList());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            authService.authorizeVisitor(accessDTO, 1L);
        });

        assertEquals("No existen autorizaciones validas para el documento 12345", thrown.getMessage(),
                "Exception message does not match expected text");
    }

    @Test
    void isAuthorizedTestFalse() {
        Long documentNumber = 12345L;

        VisitorEntity mockVisitor = new VisitorEntity();
        when(visitorRepository.findByDocNumber(documentNumber)).thenReturn(mockVisitor);

        when(authRepository.findByVisitor(mockVisitor)).thenReturn(Collections.emptyList());

        when(authService.getValidAuthsByDocNumber(documentNumber)).thenReturn(Collections.emptyList());

        boolean result = authService.isAuthorized(documentNumber);

        assertFalse(result, "Expected isAuthorized to return false when no valid authorizations are found");
    }

    @Test
    void getAllAuthsTest() {
        AuthFilter filter = new AuthFilter();
        int page = 0;
        int size = 10;

        List<AuthEntity> authEntities = new ArrayList<>();
        authEntities.add(authEntity);

        PageImpl<AuthEntity> authPage = new PageImpl<>(authEntities);

        when(authRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(authPage);
        when(userRestClient.getUsersByIds(anyList())).thenReturn(Collections.emptyList());

        List<AuthDTO> result = authService.getAllAuths(filter, page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void updateAuthorizationTest() {
        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();

        visitorAuthRequest.setAuthRangeRequest(new ArrayList<>());

        when(authRangeService.registerAuthRanges(anyList(), any(AuthEntity.class), any(VisitorDTO.class)))
                .thenReturn(new ArrayList<>());

        AuthDTO result = authService.updateAuthorization(mockAuthDTO, visitorDTO, visitorAuthRequest);

        assertNotNull(result);
        assertEquals(mockAuthDTO.getAuthId(), result.getAuthId());
    }

    @Test
    void deleteAuthorizationTest() {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setAuthId(1L);

        when(authRepository.findByAuthId(1L)).thenReturn(authEntity);

        AuthDTO result = authService.deleteAuthorization(1L);

        assertNotNull(result);
        assertFalse(authEntity.isActive());
        verify(authRepository).save(authEntity);
    }

    @Test
    void activateAuthorizationTest() {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setAuthId(1L);

        when(authRepository.findByAuthId(1L)).thenReturn(authEntity);

        AuthDTO result = authService.activateAuthorization(1L);

        assertNull(result);
        assertTrue(authEntity.isActive());
        verify(authRepository).save(authEntity);
    }

    @Test
    void updateAuthorization2Test() {
        VisitorAuthRequest request = new VisitorAuthRequest();
        request.setAuthRangeRequest(new ArrayList<>());
        List<AuthRange> authRanges = new ArrayList<>();

        AuthRange authRange1 = new AuthRange(
                1L,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31),
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                101L,
                "Access",
                true);

        AuthRange authRange2 = new AuthRange(
                2L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                102L,
                "Access",
                false);
        authRanges.add(authRange1);
        authRanges.add(authRange2);
        when(authRangeService.registerAuthRanges(anyList(), any(), any())).thenReturn(authRanges);

        AuthDTO result = authService.updateAuthorization(mockAuthDTO, visitorDTO, request);

        assertNotNull(result);
        verify(authRangeService).registerAuthRanges(anyList(), any(), any());
    }

    @Test
    void findExistingAuthorizationTest() {
        VisitorAuthRequest request = new VisitorAuthRequest();
        VisitorRequest visitorRequest = new VisitorRequest();
        visitorRequest.setDocNumber(123456L);
        request.setVisitorRequest(visitorRequest);
        request.setVisitorType(VisitorType.OWNER);
        request.setPlotId(1L);

        VisitorEntity visitorEntity = new VisitorEntity();
        List<AuthEntity> authEntities = Collections.singletonList(authEntity);
        List<AuthRangeDTO> authRangeDTOs = Collections.singletonList(new AuthRangeDTO());

        when(visitorRepository.findByDocNumber(123456L)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any(AuthEntity.class))).thenReturn(authRangeDTOs);

        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);
        mockAuthDTO.setVisitorType(VisitorType.OWNER);
        mockAuthDTO.setPlotId(1L);

        Optional<AuthDTO> result = authService.findExistingAuthorization(request);

        assertTrue(result.isPresent());
        assertEquals(VisitorType.OWNER, result.get().getVisitorType());
        assertEquals(1L, result.get().getPlotId());

        verify(visitorRepository).findByDocNumber(123456L);
        verify(authRepository).findByVisitor(visitorEntity);
        verify(authRangeService).getAuthRangesByAuth(any(AuthEntity.class));
    }

    @Test
    void findExistingAuthorizationTestNoMatch() {
        VisitorAuthRequest request = new VisitorAuthRequest();
        VisitorRequest visitorRequest = new VisitorRequest();
        visitorRequest.setDocNumber(123456L);
        request.setVisitorRequest(visitorRequest);
        request.setVisitorType(VisitorType.OWNER);
        request.setPlotId(2L);

        VisitorEntity visitorEntity = new VisitorEntity();
        List<AuthEntity> authEntities = Collections.singletonList(authEntity);
        List<AuthRangeDTO> authRangeDTOs = Collections.singletonList(new AuthRangeDTO());

        when(visitorRepository.findByDocNumber(123456L)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any(AuthEntity.class))).thenReturn(authRangeDTOs);

        AuthDTO differentAuthDTO = new AuthDTO();
        differentAuthDTO.setVisitorType(VisitorType.OWNER);
        differentAuthDTO.setPlotId(1L);
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(differentAuthDTO);

        Optional<AuthDTO> result = authService.findExistingAuthorization(request);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetValidAuthsByDocNumber() {
        Long docNumber = 123456L;
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setDocNumber(docNumber);

        List<AuthEntity> authEntities = Collections.singletonList(authEntity);
        List<AuthRangeDTO> validRanges = Collections.singletonList(new AuthRangeDTO());

        when(visitorRepository.findByDocNumber(docNumber)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getValidAuthRanges(anyList(), any(LocalDate.class), any(LocalTime.class)))
                .thenReturn(validRanges);
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);
        when(authRangeService.getAuthRangesByAuth(any(AuthEntity.class))).thenReturn(validRanges);

        List<AuthDTO> result = authService.getValidAuthsByDocNumber(docNumber);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void createAuthorizationTestNewVisitor() {
        Long docNumber = 123456L;
        VisitorEntity visitorEntity = new VisitorEntity();
        visitorEntity.setDocNumber(docNumber);

        VisitorAuthRequest request = new VisitorAuthRequest();
        VisitorRequest visitorRequest = new VisitorRequest();
        visitorRequest.setDocNumber(docNumber);
        request.setVisitorRequest(visitorRequest);
        request.setVisitorType(VisitorType.OWNER);
        request.setPlotId(1L);
        request.setAuthRangeRequest(new ArrayList<>());

        when(visitorRepository.findByDocNumber(docNumber)).thenReturn(visitorEntity);
        when(visitorService.getVisitorByDocNumber(docNumber)).thenReturn(null);
        when(visitorService.saveOrUpdateVisitor(any(), eq(null))).thenReturn(visitorDTO);
        when(authRepository.save(any(AuthEntity.class))).thenReturn(authEntity);
        when(authRangeService.registerAuthRanges(anyList(), any(AuthEntity.class), any(VisitorDTO.class)))
                .thenReturn(new ArrayList<>());

        AuthDTO result = authService.createAuthorization(request);

        assertNotNull(result);
        verify(visitorService).saveOrUpdateVisitor(any(), eq(null));
        verify(authRepository).save(any(AuthEntity.class));
    }

    @Test
    void updateAuthorizationByAuthidTest() {
        VisitorAuthRequest request = new VisitorAuthRequest();
        request.setAuthId(1L);
        request.setVisitorType(VisitorType.OWNER);
        request.setActive(true);
        request.setPlotId(1L);
        request.setExternalID(1L);
        request.setAuthRangeRequest(new ArrayList<>());

        AuthEntity existingAuth = new AuthEntity();
        existingAuth.setAuthId(1L);
        existingAuth.setVisitor(visitorEntity);

        AuthRangeDTO[] rangeDTOs = new AuthRangeDTO[]{new AuthRangeDTO()};

        when(authRepository.findByAuthId(1L)).thenReturn(existingAuth);
        when(authRepository.save(any(AuthEntity.class))).thenReturn(existingAuth);
        when(authRangeService.updateAuthRangeByAuthId(eq(1L), anyList())).thenReturn(rangeDTOs);
        when(modelMapper.map(any(VisitorEntity.class), eq(VisitorDTO.class))).thenReturn(visitorDTO);

        AuthDTO result = authService.updateAuthorizationByAuthid(request);

        assertNotNull(result);
        assertEquals(request.getVisitorType(), result.getVisitorType());
        assertEquals(request.getPlotId(), result.getPlotId());
        verify(authRepository).save(any(AuthEntity.class));
    }
}
