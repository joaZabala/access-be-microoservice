package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedTypesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthorizedRanges;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthorizedRangesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorizedRangesServiceTest {
    @InjectMocks
    private AuthorizedRangesService authorizedRangesService;

    @Mock
    private AuthorizedRangesRepository authorizedRangesRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAuthorizedRangeWithDaysNull() {
        RegisterAuthorizationRangesDTO authorizedRangeDTO = new RegisterAuthorizationRangesDTO();
        authorizedRangeDTO.setAuthTypeId(1L);
        authorizedRangeDTO.setVisitorId(2L);
        authorizedRangeDTO.setExternalId(3L);
        authorizedRangeDTO.setDateFrom(LocalDate.now());
        authorizedRangeDTO.setDateTo(LocalDate.now().plusDays(7));
        authorizedRangeDTO.setHourFrom(LocalTime.of(9, 0));
        authorizedRangeDTO.setHourTo(LocalTime.of(17, 0));
        authorizedRangeDTO.setDayOfWeeks(null);

        AuthorizedRangesEntity authorizedRangeEntity = new AuthorizedRangesEntity();
        AuthorizedTypesEntity authorizedType = new AuthorizedTypesEntity();
        authorizedType.setAuthTypeId(1L);
        authorizedRangeEntity.setAuthType(authorizedType);
        VisitorEntity visitor = new VisitorEntity();
        visitor.setVisitorId(2L);
        authorizedRangeEntity.setVisitorId(visitor);
        authorizedRangeEntity.setExternalId(3L);
        authorizedRangeEntity.setDateFrom(LocalDate.now());
        authorizedRangeEntity.setDateTo(LocalDate.now().plusDays(7));
        authorizedRangeEntity.setHourFrom(LocalTime.of(9, 0));
        authorizedRangeEntity.setHourTo(LocalTime.of(17, 0));
        authorizedRangeEntity.setDays(null);
        authorizedRangeEntity.setActive(true);

        AuthorizedRanges authorizedRanges = new AuthorizedRanges(
                authorizedRangeEntity.getAuthRangeId(),
                authorizedRangeEntity.getAuthType().getAuthTypeId(),
                authorizedRangeEntity.getVisitorId().getVisitorId(),
                authorizedRangeEntity.getExternalId(),
                authorizedRangeEntity.getDateFrom(),
                authorizedRangeEntity.getDateTo(),
                authorizedRangeEntity.getHourFrom(),
                authorizedRangeEntity.getHourTo(),
                authorizedRangeEntity.getDays(),
                authorizedRangeEntity.getPlotId(),
                authorizedRangeEntity.getComment(),
                authorizedRangeEntity.isActive()
        );
        when(modelMapper.map(authorizedRangeDTO, AuthorizedRangesEntity.class)).thenReturn(authorizedRangeEntity);
        when(authorizedRangesRepository.save(any(AuthorizedRangesEntity.class))).thenReturn(authorizedRangeEntity);
        when(modelMapper.map(authorizedRangeEntity, AuthorizedRanges.class)).thenReturn(authorizedRanges);
        authorizedRangesService.registerAuthorizedRange(authorizedRangeDTO);

        verify(authorizedRangesRepository, times(1)).save(any(AuthorizedRangesEntity.class));
        assertEquals(1L, authorizedRangeEntity.getAuthType().getAuthTypeId());
        assertEquals(2L, authorizedRangeEntity.getVisitorId().getVisitorId());
        assertNull(authorizedRangeEntity.getDays());
    }

    @Test
    public void testSaveAuthorizedRange() {
        RegisterAuthorizationRangesDTO authorizedRangeDTO = new RegisterAuthorizationRangesDTO();
        authorizedRangeDTO.setAuthTypeId(1L);
        authorizedRangeDTO.setVisitorId(2L);
        authorizedRangeDTO.setExternalId(3L);
        authorizedRangeDTO.setDateFrom(LocalDate.now());
        authorizedRangeDTO.setDateTo(LocalDate.now().plusDays(7));
        authorizedRangeDTO.setHourFrom(LocalTime.of(9, 0));
        authorizedRangeDTO.setHourTo(LocalTime.of(17, 0));
        authorizedRangeDTO.setDayOfWeeks(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));

        AuthorizedRangesEntity authorizedRangeEntity = new AuthorizedRangesEntity();
        AuthorizedTypesEntity authorizedType = new AuthorizedTypesEntity();
        authorizedType.setAuthTypeId(1L);
        authorizedRangeEntity.setAuthType(authorizedType);
        VisitorEntity visitor = new VisitorEntity();
        visitor.setVisitorId(2L);
        authorizedRangeEntity.setVisitorId(visitor);
        authorizedRangeEntity.setExternalId(3L);
        authorizedRangeEntity.setDateFrom(LocalDate.now());
        authorizedRangeEntity.setDateTo(LocalDate.now().plusDays(7));
        authorizedRangeEntity.setHourFrom(LocalTime.of(9, 0));
        authorizedRangeEntity.setHourTo(LocalTime.of(17, 0));
        authorizedRangeEntity.setDays("MONDAY-FRIDAY");
        authorizedRangeEntity.setActive(true);

        AuthorizedRanges authorizedRanges = new AuthorizedRanges(
                authorizedRangeEntity.getAuthRangeId(),
                authorizedRangeEntity.getAuthType().getAuthTypeId(),
                authorizedRangeEntity.getVisitorId().getVisitorId(),
                authorizedRangeEntity.getExternalId(),
                authorizedRangeEntity.getDateFrom(),
                authorizedRangeEntity.getDateTo(),
                authorizedRangeEntity.getHourFrom(),
                authorizedRangeEntity.getHourTo(),
                authorizedRangeEntity.getDays(),
                authorizedRangeEntity.getPlotId(),
                authorizedRangeEntity.getComment(),
                authorizedRangeEntity.isActive()
        );
        when(modelMapper.map(authorizedRangeDTO, AuthorizedRangesEntity.class)).thenReturn(authorizedRangeEntity);
        when(authorizedRangesRepository.save(any(AuthorizedRangesEntity.class))).thenReturn(authorizedRangeEntity);
        when(modelMapper.map(authorizedRangeEntity, AuthorizedRanges.class)).thenReturn(authorizedRanges);
        authorizedRangesService.registerAuthorizedRange(authorizedRangeDTO);

        verify(authorizedRangesRepository, times(1)).save(any(AuthorizedRangesEntity.class));
        assertEquals(1L, authorizedRangeEntity.getAuthType().getAuthTypeId());
        assertEquals(2L, authorizedRangeEntity.getVisitorId().getVisitorId());
    }

    @Test
    public void testSaveAuthorizedRange_NullDTO_ShouldThrowException() {
        RegisterAuthorizationRangesDTO authorizedRangeDTO = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorizedRangesService.registerAuthorizedRange(authorizedRangeDTO);
        });

        assertEquals("AuthorizedRangeDTO must not be null", exception.getMessage());
    }

}
