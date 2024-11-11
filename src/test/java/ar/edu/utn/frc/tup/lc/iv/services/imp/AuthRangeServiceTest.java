package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthRangeServiceTest {

    @Mock
    private AuthRangeRepository authRangeRepository;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthRangeService authRangeService;

    private AuthRangeEntity authRangeEntity;
    private AuthRangeDTO authRangeDTO;
    private AuthEntity authEntity;
    private LocalDate currentDate;
    private LocalTime currentTime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        currentDate = LocalDate.now();
        currentTime = LocalTime.now();

        authRangeEntity = new AuthRangeEntity();
        authRangeEntity.setActive(true);
        authRangeEntity.setDaysOfWeek("MONDAY,TUESDAY");

        authRangeDTO = new AuthRangeDTO();
        authRangeDTO.setActive(true);
        authRangeDTO.setDaysOfWeek(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        authRangeDTO.setDateFrom(currentDate.minusDays(1));
        authRangeDTO.setDateTo(currentDate.plusDays(1));
        authRangeDTO.setHourFrom(currentTime.minusHours(1));
        authRangeDTO.setHourTo(currentTime.plusHours(1));

        authEntity = new AuthEntity();
        authEntity.setAuthId(1L);
        authEntity.setExternalID(1L);
        authEntity.setPlotId(1L);
    }

    @Test
    void getAllAuthRanges() {
        List<AuthRangeEntity> entities = Arrays.asList(authRangeEntity);
        when(authRangeRepository.findAll()).thenReturn(entities);
        when(modelMapper.map(any(AuthRangeEntity.class), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        List<AuthRangeDTO> result = authRangeService.getAllAuthRanges();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(authRangeRepository).findAll();
    }

    @Test
    void getAuthRangesByAuth() {
        List<AuthRangeEntity> entities = Arrays.asList(authRangeEntity);
        when(authRangeRepository.findByAuthId(any(AuthEntity.class))).thenReturn(entities);
        when(modelMapper.map(any(AuthRangeEntity.class), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        List<AuthRangeDTO> result = authRangeService.getAuthRangesByAuth(authEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(authRangeRepository).findByAuthId(authEntity);
    }

    @Test
    void getAuthRangesByAuthWithNullDaysOfWeek() {
        authRangeEntity.setDaysOfWeek(null);
        List<AuthRangeEntity> entities = Arrays.asList(authRangeEntity);
        when(authRangeRepository.findByAuthId(any(AuthEntity.class))).thenReturn(entities);
        when(modelMapper.map(any(AuthRangeEntity.class), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        List<AuthRangeDTO> result = authRangeService.getAuthRangesByAuth(authEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAuthRangesByAuthExternalIdAndPlot() {
        List<AuthRangeEntity> entities = Arrays.asList(authRangeEntity);
        when(authRangeRepository.findByAuthIdExternalIDAndAuthIdPlotId(anyLong(), anyLong())).thenReturn(entities);
        when(modelMapper.map(any(AuthRangeEntity.class), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        List<AuthRangeDTO> result = authRangeService.getAuthRangesByAuthExternalIdAndPlot(authEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAuthRangesByAuthExternalID() {
        List<AuthRangeEntity> entities = Arrays.asList(authRangeEntity);
        when(authRangeRepository.findByAuthIdExternalID(anyLong())).thenReturn(entities);
        when(modelMapper.map(any(AuthRangeEntity.class), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        List<AuthRangeDTO> result = authRangeService.getAuthRangesByAuthExternalID(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void registerAuthRanges() {
        AuthRangeRequestDTO request = new AuthRangeRequestDTO();
        request.setDaysOfWeek(Arrays.asList(DayOfWeek.MONDAY));

        RegisterAuthorizationRangesDTO registerDTO = new RegisterAuthorizationRangesDTO();
        registerDTO.setAuthEntityId(1L);
        registerDTO.setDaysOfWeek(Arrays.asList(DayOfWeek.MONDAY));

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setVisitorId(1L);

        when(modelMapper.map(any(AuthRangeRequestDTO.class), eq(RegisterAuthorizationRangesDTO.class)))
                .thenReturn(registerDTO);
        when(modelMapper.map(any(RegisterAuthorizationRangesDTO.class), eq(AuthRangeEntity.class)))
                .thenReturn(authRangeEntity);
        when(authRepository.findById(anyLong())).thenReturn(Optional.of(authEntity));
        when(authRangeRepository.save(any())).thenReturn(authRangeEntity);

        List<AuthRange> result = authRangeService.registerAuthRanges(
                Collections.singletonList(request), authEntity, visitorDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void registerAuthRangesWithNullDaysOfWeek() {
        AuthRangeRequestDTO request = new AuthRangeRequestDTO();
        RegisterAuthorizationRangesDTO registerDTO = new RegisterAuthorizationRangesDTO();
        registerDTO.setAuthEntityId(1L);
        registerDTO.setDaysOfWeek(null);

        VisitorDTO visitorDTO = new VisitorDTO();

        when(modelMapper.map(any(AuthRangeRequestDTO.class), eq(RegisterAuthorizationRangesDTO.class)))
                .thenReturn(registerDTO);
        when(modelMapper.map(any(RegisterAuthorizationRangesDTO.class), eq(AuthRangeEntity.class)))
                .thenReturn(authRangeEntity);
        when(authRepository.findById(anyLong())).thenReturn(Optional.of(authEntity));
        when(authRangeRepository.save(any())).thenReturn(authRangeEntity);

        List<AuthRange> result = authRangeService.registerAuthRanges(
                Collections.singletonList(request), authEntity, visitorDTO);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAuthRangesSuccess() {
        VisitorEntity visitor = new VisitorEntity();
        when(visitorRepository.findByDocNumber(anyLong())).thenReturn(visitor);
        when(authRepository.findByVisitorAndVisitorTypeAndPlotId(any(), any(), anyLong())).thenReturn(authEntity);
        when(authRangeRepository.findByAuthId(any())).thenReturn(Collections.singletonList(authRangeEntity));
        when(modelMapper.map(any(), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        List<AuthRangeDTO> result = authRangeService.getAuthRanges(VisitorType.EMPLOYEE, 12345678L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deleteAuthRangeSuccess() {
        when(authRangeRepository.findById(anyLong())).thenReturn(Optional.of(authRangeEntity));
        when(authRangeRepository.save(any())).thenReturn(authRangeEntity);
        when(modelMapper.map(any(), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        AuthRangeDTO result = authRangeService.deleteAuthRange(1L);

        assertNotNull(result);
        assertFalse(authRangeEntity.isActive());
        verify(authRangeRepository).save(authRangeEntity);
    }

    @Test
    void deleteAuthRangeNotFound() {
        when(authRangeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authRangeService.deleteAuthRange(1L));
    }

    @Test
    void updateAuthRangeSuccess() {
        AuthRangeRequestDTO request = new AuthRangeRequestDTO();
        request.setDaysOfWeek(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        request.setDateFrom(LocalDate.now());
        request.setDateTo(LocalDate.now().plusDays(7));
        request.setHourFrom(LocalTime.of(9, 0));
        request.setHourTo(LocalTime.of(17, 0));

        when(authRangeRepository.findById(anyLong())).thenReturn(Optional.of(authRangeEntity));
        when(authRangeRepository.save(any())).thenReturn(authRangeEntity);
        when(modelMapper.map(any(), eq(AuthRangeDTO.class))).thenReturn(authRangeDTO);

        AuthRangeDTO result = authRangeService.updateAuthRange(1L, request);

        assertNotNull(result);
        verify(authRangeRepository).save(any(AuthRangeEntity.class));
    }

    @Test
    void updateAuthRangeNotFound() {
        AuthRangeRequestDTO request = new AuthRangeRequestDTO();
        when(authRangeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authRangeService.updateAuthRange(1L, request));
    }

    @Test
    void isValidAuthRangeWithNullFields() {
        AuthRangeDTO dto = new AuthRangeDTO();
        dto.setActive(true);
        dto.setDaysOfWeek(new ArrayList<>());

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.of(12, 0);

        boolean result = authRangeService.isValidAuthRange(dto, currentDate, currentTime);

        assertTrue(result);
    }

    @Test
    void isValidAuthRangeWithSpecificDay() {
        AuthRangeDTO dto = new AuthRangeDTO();
        dto.setActive(true);
        dto.setDaysOfWeek(Arrays.asList(LocalDate.now().getDayOfWeek()));

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.of(12, 0);

        boolean result = authRangeService.isValidAuthRange(dto, currentDate, currentTime);

        assertTrue(result);
    }

    @Test
    void isValidAuthRangeWithDateRange() {
        AuthRangeDTO dto = new AuthRangeDTO();
        dto.setActive(true);
        dto.setDaysOfWeek(new ArrayList<>());
        dto.setDateFrom(LocalDate.now().minusDays(1));
        dto.setDateTo(LocalDate.now().plusDays(1));

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.of(12, 0);

        boolean result = authRangeService.isValidAuthRange(dto, currentDate, currentTime);

        assertTrue(result);
    }

    @Test
    void isValidAuthRangeWithTimeRange() {
        AuthRangeDTO dto = new AuthRangeDTO();
        dto.setActive(true);
        dto.setDaysOfWeek(new ArrayList<>());
        dto.setHourFrom(LocalTime.of(8, 0));
        dto.setHourTo(LocalTime.of(18, 0));

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.of(12, 0);

        boolean result = authRangeService.isValidAuthRange(dto, currentDate, currentTime);

        assertTrue(result);
    }

    @Test
    void getValidAuthRangesTest() {
        AuthRangeDTO validRange = new AuthRangeDTO();
        validRange.setActive(true);
        validRange.setDaysOfWeek(new ArrayList<>());

        AuthRangeDTO invalidRange = new AuthRangeDTO();
        invalidRange.setActive(false);
        invalidRange.setDaysOfWeek(new ArrayList<>());

        List<AuthRangeDTO> authRanges = Arrays.asList(validRange, invalidRange);

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.of(12, 0);

        List<AuthRangeDTO> result = authRangeService.getValidAuthRanges(authRanges, currentDate, currentTime);

        assertTrue(result.get(0).isActive());
    }

    @Test
    void updateAuthRangeByAuthIdTest() {
        Long authId = 1L;
        AuthRangeRequestDTO request = new AuthRangeRequestDTO();
        request.setAuthRangeId(1L);
        request.setDaysOfWeek(Arrays.asList(DayOfWeek.MONDAY));
        request.setActive(true);
        request.setComment("Test comment");

        AuthEntity auth = new AuthEntity();
        VisitorEntity visitor = new VisitorEntity();
        visitor.setVisitorId(1L);
        auth.setVisitor(visitor);

        AuthRangeEntity existingRange = new AuthRangeEntity();
        AuthRangeDTO expectedDto = new AuthRangeDTO();

        when(authRepository.findByAuthId(authId)).thenReturn(auth);
        when(authRangeRepository.findById(1L)).thenReturn(Optional.of(existingRange));
        when(authRangeRepository.save(any(AuthRangeEntity.class))).thenReturn(existingRange);
        when(modelMapper.map(any(), eq(AuthRangeDTO[].class))).thenReturn(new AuthRangeDTO[]{expectedDto});

        AuthRangeDTO[] result = authRangeService.updateAuthRangeByAuthId(authId, Arrays.asList(request));

        assertNotNull(result);
        assertEquals(1, result.length);
        verify(authRangeRepository).save(any(AuthRangeEntity.class));
    }
}