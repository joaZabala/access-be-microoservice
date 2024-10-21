package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthorizedRangesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorizedRangesServiceTest {

    @MockBean
    private AuthorizedRangesRepository authorizedRangesRepository;

    @MockBean
    private AuthRepository authRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
     * ¨
     * 
     * 
     * 
     * @Test
     * public void testSaveAuthorizedRangeWithDaysNull() {
     * RegisterAuthorizationRangesDTO dto = new RegisterAuthorizationRangesDTO();
     * dto.setAuthEntityId(1L);
     * dto.setVisitorId(1L);
     * dto.setDateFrom(LocalDate.of(2023, 10, 1));
     * dto.setDateTo(LocalDate.of(2024, 10, 1));
     * dto.setHourFrom(LocalTime.of(9, 0));
     * dto.setHourTo(LocalTime.of(17, 0));
     * dto.setDayOfWeeks(null);
     * 
     * AuthEntity authEntity = new AuthEntity();
     * authEntity.setAuthId(1L);
     * 
     * AuthRangeEntity authRangeEntity = new AuthRangeEntity();
     * authRangeEntity.setAuthId(authEntity);
     * authRangeEntity.setActive(true);
     * authRangeEntity.setDays(null);
     * 
     * when(authRepository.findById(1L)).thenReturn(Optional.of(authEntity));
     * when(authorizedRangesRepository.save(any(AuthRangeEntity.class))).thenReturn(
     * authRangeEntity);
     * 
     * // Ejecutar el método bajo prueba
     * AuthorizedRanges result =
     * authorizedRangesService.registerAuthorizedRange(dto);
     * 
     * // Verificar interacciones con los mocks
     * verify(authRepository).findById(1L);
     * verify(authorizedRangesRepository).save(any(AuthRangeEntity.class));
     * 
     * 
     * // Verificar que los resultados sean los esperados
     * assertNotNull(result);
     * assertNull(result.getDays());
     * assertTrue(result.isActive());
     * }
     * 
     * 
     * 
     * @Test
     * public void testSaveAuthorizedRange() {
     * RegisterAuthorizationRangesDTO authorizedRangeDTO = new
     * RegisterAuthorizationRangesDTO();
     * authorizedRangeDTO.setVisitorId(2L);
     * authorizedRangeDTO.setDateFrom(LocalDate.now());
     * authorizedRangeDTO.setDateTo(LocalDate.now().plusDays(7));
     * authorizedRangeDTO.setHourFrom(LocalTime.of(9, 0));
     * authorizedRangeDTO.setHourTo(LocalTime.of(17, 0));
     * authorizedRangeDTO.setDayOfWeeks(Arrays.asList(DayOfWeek.MONDAY,
     * DayOfWeek.FRIDAY));
     * 
     * // Configurar la entidad AuthEntity y AuthorizedRangesEntity
     * VisitorEntity visitor = new VisitorEntity();
     * visitor.setVisitorId(2L);
     * 
     * AuthEntity authEntity = new AuthEntity();
     * authEntity.setAuthId(1L);
     * authEntity.setVisitor(visitor);
     * 
     * AuthRangeEntity authRangeEntity = new AuthRangeEntity();
     * authRangeEntity.setDateFrom(LocalDate.now());
     * authRangeEntity.setDateTo(LocalDate.now().plusDays(7));
     * authRangeEntity.setHourFrom(LocalTime.of(9, 0));
     * authRangeEntity.setHourTo(LocalTime.of(17, 0));
     * authRangeEntity.setDays("MONDAY-FRIDAY");
     * authRangeEntity.setAuthId(authEntity);
     * authRangeEntity.setActive(true);
     * 
     * // Configurar mocks para simular el comportamiento de la base de datos
     * when(authRepository.findById(1L)).thenReturn(Optional.of(authEntity));
     * when(authorizedRangesRepository.save(any(AuthRangeEntity.class))).thenReturn(
     * authRangeEntity);
     * 
     * // Ejecutar el método bajo prueba
     * AuthorizedRanges result =
     * authorizedRangesService.registerAuthorizedRange(authorizedRangeDTO);
     * 
     * // Verificaciones
     * assertNotNull(result);
     * assertEquals("MONDAY-FRIDAY", result.getDays());
     * assertTrue(result.isActive());
     * assertEquals(LocalTime.of(9, 0), result.getHourFrom());
     * assertEquals(LocalTime.of(17, 0), result.getHourTo());
     * assertEquals(LocalDate.now(), result.getDateFrom());
     * assertEquals(LocalDate.now().plusDays(7), result.getDateTo());
     * }
     * 
     * @Test
     * public void testSaveAuthorizedRange_NullDTO_ShouldThrowException() {
     * RegisterAuthorizationRangesDTO authorizedRangeDTO = null;
     * IllegalArgumentException exception =
     * assertThrows(IllegalArgumentException.class, () -> {
     * authorizedRangesService.registerAuthorizedRange(authorizedRangeDTO);
     * });
     * 
     * assertEquals("AuthorizedRangeDTO must not be null", exception.getMessage());
     * }
     * 
     * @Test
     * public void hasNotInvitationTest() {
     * 
     * LocalDate localDate = LocalDate.of(2022, 1, 1);
     * LocalTime localTime = LocalTime.of(9, 0);
     * DayOfWeek dayOfWeek = localDate.getDayOfWeek();
     * 
     * when(authorizedRangesRepository.hasInvitation(localDate, localTime,
     * 12345678L, dayOfWeek.name()))
     * .thenReturn(false);
     * 
     * assertFalse(authorizedRangesService.hasInvitation(12345678L));
     * }
     * 
     * @Test
     * public void hasInvitationTest() {
     * when(authorizedRangesRepository.hasInvitation(any(LocalDate.class),
     * any(LocalTime.class), any(Long.class),
     * any(String.class))).thenReturn(true);
     * assertTrue(authorizedRangesService.hasInvitation(12345678L));
     * }
     * 
     */
}
