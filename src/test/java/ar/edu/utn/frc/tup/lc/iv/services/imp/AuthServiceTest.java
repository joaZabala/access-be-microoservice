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
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @MockBean(name = "modelMapper")  // Especificamos el nombre del bean
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
        // Configurar VisitorDTO
        visitorDTO = new VisitorDTO();
        visitorDTO.setVisitorId(1L);
        visitorDTO.setName("Catalina");
        visitorDTO.setLastName("Pisoni");
        visitorDTO.setDocNumber(123456L);
        visitorDTO.setDocumentType(DocumentType.DNI);
        visitorDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        visitorDTO.setActive(true);

        // Configurar VisitorEntity
        visitorEntity = new VisitorEntity();
        visitorEntity.setVisitorId(1L);
        visitorEntity.setName("Maria");
        visitorEntity.setLastName("Pipino");
        visitorEntity.setDocNumber(123456L);
        visitorEntity.setDocumentType(DocumentType.DNI);
        visitorEntity.setActive(true);

        // Configurar AuthEntity
        authEntity = new AuthEntity();
        authEntity.setAuthId(1L);
        authEntity.setVisitor(visitorEntity);
        authEntity.setVisitorType(VisitorType.OWNER);
        authEntity.setActive(true);
        authEntity.setPlotId(1L);

        // Configurar AuthRangeDTO
        AuthRangeDTO authRangeDTO = new AuthRangeDTO();
        authRangeDTO.setDateFrom(LocalDate.of(2022, 1, 1));
        authRangeDTO.setDateTo(LocalDate.of(2022, 1, 31));
        //authRangeDTO.setDays("MONDAY,TUESDAY,WEDNESDAY");
        authRangeDTO.setActive(true);

        authRangeDTOs = new ArrayList<>();
        authRangeDTOs.add(authRangeDTO);

        authEntities = new ArrayList<>();
        authEntities.add(authEntity);

        // Configurar mock AuthDTO
        mockAuthDTO = new AuthDTO();
        mockAuthDTO.setAuthId(1L);
        mockAuthDTO.setVisitorType(VisitorType.OWNER);
        mockAuthDTO.setActive(true);
        mockAuthDTO.setPlotId(1L);
        mockAuthDTO.setVisitor(visitorDTO);
        mockAuthDTO.setAuthRanges(authRangeDTOs);

        // Configurar comportamiento básico del modelMapper
        when(modelMapper.map(any(AuthEntity.class), eq(AuthDTO.class))).thenReturn(mockAuthDTO);
    }

    @Test
    void getAuthsByDocNumberTest() {
        // Configurar comportamiento de los mocks
        when(visitorRepository.findByDocNumber(123456L)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any())).thenReturn(authRangeDTOs);

        // Ejecutar el método
        List<AuthDTO> result = authService.getAuthsByDocNumber(123456L);

        // Verificaciones
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(VisitorType.OWNER, result.get(0).getVisitorType());
        assertEquals(1L, result.get(0).getAuthId());

        // Verificar que se llamaron los métodos necesarios
        verify(visitorRepository).findByDocNumber(123456L);
        verify(authRepository).findByVisitor(visitorEntity);
        verify(authRangeService).getAuthRangesByAuth(any());
    }

    @Test
    void getAuthsByTypeTest() {
        // Configurar comportamiento de los mocks
        when(authRepository.findByVisitorType(VisitorType.OWNER)).thenReturn(authEntities);
        when(authRangeService.getAuthRangesByAuth(any())).thenReturn(authRangeDTOs);

        // Ejecutar el método
        List<AuthDTO> result = authService.getAuthsByType(VisitorType.OWNER);

        // Verificaciones
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(VisitorType.OWNER, result.get(0).getVisitorType());

        // Verificar que se llamaron los métodos necesarios
        verify(authRepository).findByVisitorType(VisitorType.OWNER);
        verify(authRangeService).getAuthRangesByAuth(any());
    }

    @Test
    void getAuthsByTypeAndExternalIdTest() {
        // Configurar comportamiento de los mocks
        when(authRepository.findByVisitorTypeAndExternalIDAndPlotId(
                VisitorType.OWNER, 1L, 1L)).thenReturn(authEntities);

        // Ejecutar el método
        List<AuthDTO> result = authService.getAuthsByTypeAndExternalId(VisitorType.OWNER, 1L, 1L);

        // Verificaciones
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(VisitorType.OWNER, result.get(0).getVisitorType());

        // Verificar que se llamaron los métodos necesarios
        verify(authRepository).findByVisitorTypeAndExternalIDAndPlotId(VisitorType.OWNER, 1L, 1L);
    }
}

